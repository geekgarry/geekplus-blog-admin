package com.geekplus.webapp.common.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geekplus.common.domain.ChatPrompt;
import com.geekplus.common.util.datetime.DateTimeUtils;
import com.geekplus.common.util.http.ServletUtil;
import com.geekplus.common.util.base64.Base64Util;
import com.geekplus.common.util.file.FileUploadUtils;
import com.geekplus.common.util.file.FileUtils;
import com.geekplus.common.util.google.GeminiUtils;
import com.geekplus.common.util.http.IPUtils;
import com.geekplus.common.util.openai.GetClientName;
import com.geekplus.common.util.string.StringUtils;
import com.geekplus.webapp.function.entity.ChatAILog;
import com.geekplus.webapp.function.service.IChatAILogService;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * author     : geekplus
 * date       : 6/28/24 7:46 AM
 * description: 做什么的？
 */
@Slf4j
@Service
public class GeminiChatService {
    @Value("${google.gemini.api-key:}")
    private String geminiApiKey;

    @Resource
    private IChatAILogService chatgptLogService;

    private final StringRedisTemplate stringRedisTemplate;

    private HttpServletRequest httpServletRequest;
    private String ip;
    private UserAgent userAgent;
    int userAId=0;
    String osName=null;
    String browserName=null;

    private final WebClient webClient;

    //初始化redis操作类
    @Autowired
    public GeminiChatService(StringRedisTemplate stringRedisTemplate, WebClient webClient) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.webClient = webClient;
    }

    public Flux<Map<String, Object>> streamFromModel(ChatPrompt chatPrompt) throws IOException {
        List<Map<String,Object>> historyChatDataList = new ArrayList<>();
        if(null != chatPrompt.getHistoryChatData()) {
            //历史消息列表
            historyChatDataList = chatPrompt.getHistoryChatData();
        }
        //构造了最外层的一个包含所有Json的key:value的包裹Map
        Map<String,Object> finalChatPromptMap = new HashMap<>();
        //首先向包裹填装基本不变的安全设置Map
        finalChatPromptMap.putAll(GeminiUtils.createSafetySettingsMap());//put("safetySettings",null);
        // 判断消息提示中是否带有媒体文件的数据内容，为空测没有，表示是一次普通的文本消息请求，
        // 否则就是带有文件数据，具体再分析数据类型
        if(ObjectUtils.isEmpty(chatPrompt.getMediaData())){
            //为发送的消息构造一个消息内容Map
            Map<String,Object> commonMsgDataMap = GeminiUtils.createMsgPromptMap("user", chatPrompt.getChatMsg(),null,null);
            //因为是聊天模式，所以向历史消息列表添加这个新构造的消息Map
            historyChatDataList.add(commonMsgDataMap);
            //最后把消息主题内容添加到key为contents的Map
            finalChatPromptMap.put("contents",historyChatDataList);
            //最后把构造的finalChatPromptMap消息放入请求体，这里就相当于前端的json放入RequestBody
        }else{
            String mimeType= chatPrompt.getMediaMimeType();//Base64Util.getFileMimeType(chatPrompt.getMediaData().toString());
            //判断是否是字符串形式的数据类型，因为前端发送的是base64字符串编码后的文件
            if(chatPrompt.getMediaData() instanceof String || FileUtils.isStringType(chatPrompt.getMediaData())) {
                //因为发送消息携带base64文件，为发送的消息构造一个消息内容Map，里面再次添加inline_data等所需要的内容，这里重新设置一个新的消息Map，因为这个是携带媒体文件数据的消息
                Map<String,Object> msgMediaDataMap = GeminiUtils.createMsgPromptMap("user", chatPrompt.getChatMsg(), mimeType, Base64Util.getBase64Str(chatPrompt.getMediaData().toString()));
                //还是一样添加到所有聊天记录list
                historyChatDataList.add(msgMediaDataMap);
                //同样添加到最外的构造消息请求体的Map
                finalChatPromptMap.put("contents", historyChatDataList);
            }else {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(baos);
                objectOutputStream.writeObject(chatPrompt.getMediaData());
                objectOutputStream.flush();
                byte[] byteStream = baos.toByteArray();
//                MultipartBodyBuilder builder = new MultipartBodyBuilder();
//                builder.part("file",
//                        new InputStreamResource(inputStream) {
//                            @Override
//                            public String getFilename() {
//                                return fileName; // multipart 需要文件名，否则可能 400
//                            }
//                        })
//                        .header(HttpHeaders.CONTENT_DISPOSITION,
//                                "form-data; name=file; filename=" + fileName);
//                builder.part("file", byteStream);
//                builder.part("description", "这是测试文件");
                //这里与上面一样，就是类型变成MultiValueMap，这是上传大型文件
                MultiValueMap<String, Object> formChatPromptMap = new LinkedMultiValueMap<>();
                formChatPromptMap.setAll(GeminiUtils.createSafetySettingsMap());
                Map<String,Object> msgByteDataMap = GeminiUtils.createMsgPromptMap("user", chatPrompt.getChatMsg(), mimeType, byteStream);
                historyChatDataList.add(msgByteDataMap);
                formChatPromptMap.addAll("contents", historyChatDataList);
                // 1) 构造请求到后端 AI 的 body（依据后端文档）
                return webClient.post()
                        .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:streamGenerateContent?alt=sse") // 假设后端流式 endpoint
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header("x-goog-api-key", geminiApiKey)
                        .accept(MediaType.TEXT_EVENT_STREAM) // 或 MediaType.APPLICATION_NDJSON
                        .bodyValue(finalChatPromptMap)
                        .retrieve()
                        .bodyToFlux(DataBuffer.class) // 低层读取数据 buffer（最通用）
                        .flatMap(this::parseDataBufferToChunks)
                        .onBackpressureBuffer(1000, // 缓冲上限
                                dropped -> {
                                    // 可记录指标
                                });
            }
        }
        // 1) 构造请求到后端 AI 的 body（依据后端文档）
        return webClient.post()
                .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:streamGenerateContent?alt=sse") // 假设后端流式 endpoint
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-goog-api-key", geminiApiKey)
                .accept(MediaType.TEXT_EVENT_STREAM) // 或 MediaType.APPLICATION_NDJSON
                .bodyValue(finalChatPromptMap)
                .retrieve()
                .bodyToFlux(DataBuffer.class) // 低层读取数据 buffer（最通用）
                .flatMap(this::parseDataBufferToChunks)
                .onBackpressureBuffer(1000, // 缓冲上限
                        dropped -> {
                            // 可记录指标
                        });
    }

    private Flux<Map<String, Object>> parseDataBufferToChunks(DataBuffer dataBuffer) {
        // 把 DataBuffer 转成 String（追加拆行处理）
        // 简化实现：将 buffer 转成字符串，然后根据换行 / "data:" 前缀解析 NDJSON 或 SSE。
        String s = dataBuffer.toString(StandardCharsets.UTF_8);
        // 逻辑：如果 SSE 风格，解析 "data: {json}\n\n"；如果 NDJSON，每行 JSON。
        // 这里做个较宽松的实现：按行处理，忽略空行，尝试把行解析为 JSON 包含 content 字段，否则当作原始 text chunk。
        Flux<Map<String, Object>> flux = Flux.fromStream(Arrays.stream(s.split("\\r?\\n")))
                //.range(0, s.length())
                .delayElements(Duration.ofMillis(200)) // 每200ms推一个字
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .flatMap(line -> {
                    String payload = line;
                    if (payload.startsWith("data:")) {
                        payload = payload.substring(5).trim();
                        if ("".equals(payload)) {
                            return Flux.empty(); // 结束标志（视后端协议）
                        }
                    }
                    // 尝试解析 JSON
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode node = mapper.readTree(payload);
                        JsonNode content = node.get("candidates").get(0).get("content");
                        String text = content.has("parts") ? content.get("parts").get(0).get("text").asText() : "";
                        String role = content.has("role") ? content.get("role").asText() : "assistant";
                        Map<String, Object> chunk = new HashMap<>();
                        chunk.put("role", role);
                        chunk.put("text", text);
                        return Flux.just(chunk);
                    } catch (JsonProcessingException e) {
                        // 不是 JSON，直接作为文本片段
                        Map<String, Object> chunk = new HashMap<>();
                        chunk.put("role", "assistant");
                        chunk.put("text", payload);
                        return Flux.just(chunk);
                    }
                });
//        Flux<Map<String, Object>> resultFlux = flux
//                .collectList()
//                .flatMapMany(list -> {
//                    StringBuilder sb = new StringBuilder();
//                    for (Map<String, Object> item : list) {
//                        sb.append(item.get("text"));
//                    }
//                    Map<String, Object> finalResult = new HashMap<>();
//                    finalResult.put("role", "assistant");
//                    finalResult.put("text", sb.toString());
//                    finalResult.put("finished", true);
//                    // 前面所有片段 + 最终完整消息
//                    return Flux.fromIterable(list).concatWithValues(finalResult);
//                });
        return flux;
    }

    /**
     * @Author geekplus
     * @Description // Google Gemini AI 无记忆聊天和携带历史聊天记录
     * @Param String messageContent, String preMessageJson, String fromUser
     * @Throws
     * @Return {@link }
     */
    public Map<String, Object> getGeminiContent(ChatPrompt chatPrompt) throws IOException {
        // 默认信息
        ChatAILog chatAILog =new ChatAILog();
        Map<String,Object> mapResponse = new HashMap();
        String aiReplyText=null;
        //把msgcontent和fromuser转换成md5作为rediskey
        long chatDate= new Date().getTime();
        //获取用户的IP和MAC地址
        httpServletRequest= ServletUtil.getRequest();
        ip= IPUtils.getIpAddr(httpServletRequest);
        userAgent=GetClientName.getBrowser(httpServletRequest);
        userAId=userAgent.getId();
        osName=userAgent.getOperatingSystem().getName();
        browserName=userAgent.getBrowser().getName();
        StringBuilder strPreKey = new StringBuilder("GeekPlus");
        //如果historyId不为空
        String md5Content = chatPrompt.getUsername() + ":" + chatPrompt.getHistoryId();
        chatAILog.setChatRecordId(chatPrompt.getHistoryId());
        mapResponse.put("recordId",chatPrompt.getHistoryId());
        //如果为空
        if(StringUtils.isEmpty(chatPrompt.getHistoryId()) && ("guest".equals(chatPrompt.getUsername()) || chatPrompt.getUsername().contains("guest"))){
            strPreKey.append("_").append(ip)
                    .append("_").append(osName)
                    .append("_").append(browserName)
                    .append("_").append(userAId);//没有重新赋值
            md5Content = chatPrompt.getUsername() + ":" + DigestUtils.md5DigestAsHex(strPreKey.toString().getBytes());
        }else if(StringUtils.isEmpty(chatPrompt.getHistoryId())) {
            strPreKey.append("_").append(System.currentTimeMillis())
                    .append("_").append(httpServletRequest.getRequestedSessionId());
            String chatRecordId = DigestUtils.md5DigestAsHex(strPreKey.append("_"+chatPrompt.getUsername()).toString().getBytes());
            md5Content = chatPrompt.getUsername() + ":" + chatRecordId;
            chatAILog.setChatRecordId(chatRecordId);
            mapResponse.put("recordId",chatRecordId);
        }
        //System.out.println("MAC地址："+mac);
//        log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//        log.info("MAC地址："+IPAndMACUtils.getAddressMac());
//        //System.out.println("消息内容："+messageContent);
//        log.info("消息内容："+messageContent);
//        System.out.println("加密的key："+md5Content);
        log.info("用户的临时tokenKey"+strPreKey);
        log.info("加密的key："+md5Content);
//        log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        String fileUrl = "";
        if(!ObjectUtils.isEmpty(chatPrompt.getMediaData())) {
            //&&当第一个表达式的值为false的时候，则不再计算第二个表达式,&则怎么样都执行
            //||当第一个表达式的值为true，就不再计算后面的表达式，｜则也会都执行
            if(FileUtils.isStringType(chatPrompt.getMediaData()) && Base64Util.isBase64(chatPrompt.getMediaData().toString())) {
                fileUrl = FileUploadUtils.base64StrToFile(chatPrompt.getMediaData().toString());
            }
            //chatPrompt.setMediaData(Base64Util.getBase64Str(chatPrompt.getMediaData().toString()));
        }
        //Gemini AI 返回内容
        if(null == chatPrompt.getHistoryChatData()) {//|| chatPrompt.getHistoryChatData().isEmpty()
            aiReplyText = GeminiUtils.postGemini(chatPrompt, geminiApiKey);
        }else {
            aiReplyText = GeminiUtils.postGeminiHistory(chatPrompt, geminiApiKey);
        }

        //存储对话记录
        Date chatReplyDate = DateTimeUtils.getCurrentDateTime();//DateTimeUtils.getCurrentDate(LocalDate.now());
        //if (aiReplyText != null){
        long chatReplySeconds = new Date().getTime();
        mapResponse.put("text",aiReplyText.trim());
        //消息类型，text(文本/富文本),image(图片),video(视频),audio(音频),file(文件)
        mapResponse.put("type","text");
        mapResponse.put("time",chatReplySeconds);
        HashMap<String,Object> msgMap1=new HashMap<>();
        //用户发送的消息
        msgMap1.put("align","right");
        msgMap1.put("text",chatPrompt.getChatMsg());
        msgMap1.put("link","");
        //消息类型，text(文本/富文本),image(图片),video(视频),audio(音频),file(文件)
        msgMap1.put("type","text");
        msgMap1.put("time",chatDate);
        if(fileUrl!="" || !"".equals(fileUrl)) {
            if(chatPrompt.getMediaMimeType().startsWith("image/")) {
                msgMap1.put("type", "image");
            }else if(chatPrompt.getMediaMimeType().startsWith("video/")) {
                msgMap1.put("type", "video");
            }else if(chatPrompt.getMediaMimeType().startsWith("audio/")) {
                msgMap1.put("type", "audio");
            }else {
                msgMap1.put("type", "file");
            }
            msgMap1.put("mediaData", fileUrl);
            msgMap1.put("mediaMimeType",chatPrompt.getMediaMimeType());
            msgMap1.put("mediaFileName", chatPrompt.getMediaFileName());
        }
        //msgMapList.add(msgMap1);
        stringRedisTemplate.opsForList().rightPush(md5Content, JSONObject.toJSONString(msgMap1));
        HashMap<String,Object> msgMap2=new HashMap<>();
        //用户接收的消息
        msgMap2.put("align","left");
        msgMap2.put("text",aiReplyText.trim());
        msgMap2.put("link","");
        msgMap2.put("type","text");
        msgMap2.put("time",chatReplySeconds);
        //msgMapList.add(msgMap2);
        //保存到redis里面 rightPush是从list列表尾部插入，先进后出
        //stringRedisTemplate.opsForHash().putAll(md5Content, msgMap2);
        stringRedisTemplate.opsForList().rightPush(md5Content, JSONObject.toJSONString(msgMap1), JSONObject.toJSONString(msgMap2));
        //}
        stringRedisTemplate.expire(md5Content, 24, TimeUnit.HOURS);
        chatAILog.setAskContent(JSONObject.toJSONString(msgMap1));//fileUrl+"\n"+chatPrompt.getChatMsg()
        chatAILog.setReplyContent(JSONObject.toJSONString(msgMap2));//replaceAll("\\s*","").replaceAll(" +"," ")
        chatAILog.setUsername(chatPrompt.getUsername());
        chatAILog.setUserIp(ip);
        chatAILog.setUserMac("2060-XX-XX");
        //Date logDate= DateTimeUtils.getCurrentDate(LocalDate.now());
        chatAILog.setCreateTime(chatReplyDate);
        chatgptLogService.insertChatAILog(chatAILog);
        return mapResponse;
    }

    /**
     * @Author geekplus
     * @Description // Google Gemini AI 聊天/测试
     * @Param
     * @Throws
     * @Return {@link }
     */
    public Object postGeminiTTS(String text, String voiceName) {
        try {
            return GeminiUtils.postGeminiTTS(null, text, voiceName, geminiApiKey);
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    //获取此前历史聊天记录，显示的聊天消息列表
    public List<String> getOneHistoryMsgList(String userName){
        List<String> msgList=new ArrayList<>();
        //String msg=null;
        StringBuilder strPreKey=new StringBuilder("GeekPlus");
        //String contentPre = userName;
        //获取用户的IP和MAC地址
        httpServletRequest=ServletUtil.getRequest();
        ip= IPUtils.getIpAddr(httpServletRequest);
        userAgent=GetClientName.getBrowser(httpServletRequest);
        userAId=userAgent.getId();
        osName=userAgent.getOperatingSystem().getName();
        browserName=userAgent.getBrowser().getName();
        String md5Content = null;
        if("guest".equals(userName) || userName.contains("guest")) {
            strPreKey.append("_").append(ip)
                    .append("_").append(osName)
                    .append("_").append(browserName)
                    .append("_").append(userAId);
            md5Content = userName+":"+ DigestUtils.md5DigestAsHex(strPreKey.toString().getBytes());
        }else {
            //md5Content = userName+":"+ DigestUtils.md5DigestAsHex(strPreKey.append("_"+userName).toString().getBytes());
            return msgList;
        }
        log.info("用户的临时tokenKey："+strPreKey.toString());
        // 如果存在key，拿出来
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(md5Content))) {
            msgList = stringRedisTemplate.opsForList().range(md5Content, 0, -1);//获取所有值
        }
        log.info("我的历史消息："+msgList.toString());
        return msgList;
    }

    //根据redisKey获取此前历史聊天记录，显示的聊天消息列表
    public List<String> getOneHistoryMsgListByKey(String redisKey){
        List<String> msgList=new ArrayList<>();
        // 如果存在key，拿出来
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(redisKey))) {
            msgList = stringRedisTemplate.opsForList().range(redisKey, 0, -1);//获取所有值
        }
        return msgList;
    }

    //获取所有聊天记录，保存每一个聊天消息列表的最后一条和其对应的聊天记录ID的组合Map
    public List<Map<String, Object>> getAllHistoryMsgList(String userName){
        //定义一个所有历史消息纪录的list，根据userName去查询所有的相关的，
        List<Map<String, Object>> allMsgList=new ArrayList<>();
        //String msg=null;
        StringBuilder strPreKey=new StringBuilder("GeekPlus");
        //String contentPre = userName;
        //获取用户的IP和MAC地址
        httpServletRequest=ServletUtil.getRequest();
        ip= IPUtils.getIpAddr(httpServletRequest);
        userAgent=GetClientName.getBrowser(httpServletRequest);
        userAId=userAgent.getId();
        osName=userAgent.getOperatingSystem().getName();
        browserName=userAgent.getBrowser().getName();
        if("guest".equals(userName) || userName.contains("guest")) {
            strPreKey.append("_").append(ip)
                    .append("_").append(osName)
                    .append("_").append(browserName)
                    .append("_").append(userAId);
            String md5Content = userName + ":" + DigestUtils.md5DigestAsHex(strPreKey.toString().getBytes());
            if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(md5Content))){
                Map<String, Object> latestHistoryMsg = new HashMap<>();
                //每一条历史消息记录，用Map包裹，里面有历史消息的redis的key，还有历史聊天记录的最新一条消息
                latestHistoryMsg.put("historyMsgKey", md5Content);
                latestHistoryMsg.put("historyMsg", stringRedisTemplate.opsForList().range(md5Content,-1, -1).get(0));
                allMsgList.add(latestHistoryMsg);
            }
        }else {
            Set<String> setKeys = queryChatListWithMutex(userName+":default", userName);
            if(!setKeys.isEmpty()&&setKeys.size()>0) {
                for (String key : setKeys) {
                    Map<String, Object> latestHistoryMsg = new HashMap<>();
                    //每一条历史消息记录，用Map包裹，里面有历史消息的redis的key，还有历史聊天记录的最新一条消息
                    latestHistoryMsg.put("historyMsgKey", key);
                    latestHistoryMsg.put("historyMsg", stringRedisTemplate.opsForList().range(key, -1, -1).get(0));
                    allMsgList.add(latestHistoryMsg);
                }
            }
        }
        log.info("用户的临时tokenKey：" + strPreKey.toString());
        return allMsgList;
    }

    //获取所有聊天记录，保存每一个聊天消息列表的最后一条和其对应的聊天记录ID的组合Map
    public void reGetAllHistoryMsgList(String userName){
        // 7.获取成功, 查询数据库
        List<String> chatRecordIds = chatgptLogService.selectAllChatRecordId(userName);
        // 8.判断数据库是否有数据
//            if(chatRecordIds.isEmpty()) {
//                // 9.无,则将空数据写入redis
//                stringRedisTemplate.opsForValue().set(key, "", RedisConstants.CACHE_NULL_TTL, TimeUnit.MINUTES);
//            }
        if (!chatRecordIds.isEmpty()) {
            for(String chatRecordId : chatRecordIds) {
                List<ChatAILog> chatAILogList =chatgptLogService.getChatAILogListByRecordId(chatRecordId);
                String redisKey = userName+":"+chatRecordId;
                stringRedisTemplate.delete(redisKey);
                chatAILogList.forEach(chatAILog -> stringRedisTemplate.opsForList().rightPushAll(redisKey, Arrays.asList(chatAILog.getAskContent(), chatAILog.getReplyContent())));
                stringRedisTemplate.expire(redisKey, 5, TimeUnit.DAYS);
            }
        }
    }

    //删除历史聊天记录
    public Boolean deleteHistoryMsgList(String msgKey){
        // 如果存在key，拿出来
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(msgKey))) {
            String chatRecordId = msgKey.split(":")[1];
            return chatgptLogService.deleteChatAILogByChatRecordId(chatRecordId) > 0 ? stringRedisTemplate.delete(msgKey) : false;
        }else {
            return false;
        }
    }

    public Object getAllGeminiModels() throws Exception {
        return GeminiUtils.getAllGeminiModels(geminiApiKey);
    }

    /**
     * 通过互斥锁机制查询该登录用户的所有聊天记录
     * @param key
     */
    private Set<String> queryChatListWithMutex(String key, String username) {
        // 1.查询缓存
        Set<String> historyKeys = batchFetchKeys(username+":*");
        // 2.判断缓存是否有数据
        if (historyKeys != null && !historyKeys.isEmpty() && historyKeys.size() > 0) {
            // 3.有,则返回
            return historyKeys;
        }
        // 4.无,则获取互斥锁
        String lockKey = "lock:" + key;
        Boolean isLock = tryLock(lockKey);
        // 5.判断获取锁是否成功
        try {
            if (!isLock) {
                // 6.获取失败, 休眠并重试
                Thread.sleep(100);
                return queryChatListWithMutex(key, username);
            }
            // 7.获取成功, 查询数据库
            List<String> chatRecordIds = chatgptLogService.selectAllChatRecordId(username);
            // 8.判断数据库是否有数据
//            if(chatRecordIds.isEmpty()) {
//                // 9.无,则将空数据写入redis
//                stringRedisTemplate.opsForValue().set(key, "", RedisConstants.CACHE_NULL_TTL, TimeUnit.MINUTES);
//            }
            if (!chatRecordIds.isEmpty()) {
                for(String chatRecordId : chatRecordIds) {
                    List<ChatAILog> chatAILogList =chatgptLogService.getChatAILogListByRecordId(chatRecordId);
                    String redisKey = username+":"+chatRecordId;
                    chatAILogList.forEach(chatAILog -> {
                        stringRedisTemplate.opsForList().rightPushAll(redisKey, Arrays.asList(chatAILog.getAskContent(), chatAILog.getReplyContent()));
                    });
                    stringRedisTemplate.expire(redisKey, 5, TimeUnit.DAYS);
                }
                historyKeys = chatRecordIds.stream().map(historyKey -> username + ":" +historyKey).collect(Collectors.toSet());
            }
            // 10.有,则将数据写入redis
            //stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(city), RedisConstants.CACHE_CITY_TTL, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 11.释放锁
            unLock(lockKey);
        }
        // 12.返回数据
        return historyKeys;
    }

    /**
     * 获取互斥锁
     * @return
     */
    private Boolean tryLock(String key) {
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
        return BooleanUtils.isTrue(flag);
    }

    /**
     * 释放锁
     * @param key
     */
    private void unLock(String key) {
        stringRedisTemplate.delete(key);
    }

    public Set<String> batchFetchKeys(String pattern) {
        Set<String> keys = new HashSet<>();
        ScanOptions options = ScanOptions.scanOptions().match(pattern).count(100).build();
        try {
            // do { } while (!cursor.equals("0"));
            // 执行scan命令，获取当前批次的key
            return stringRedisTemplate.execute((RedisCallback<Set<String>>) template -> {
                Cursor<byte[]> cursor = template.scan(options);
                while (cursor.hasNext()) {
                    keys.add(new String(cursor.next()));
                }
                return keys;
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

//    public Set<String> getScanSortedKeys(Strinng ) {
//        List<String> keys = new ArrayList<>();
//        ScanOptions options = ScanOptions.scanOptions().count(100).build(); // 每次扫描100个键
//        stringRedisTemplate.execute((RedisConnection connection) -> {
//            Cursor<byte[]> cursor = connection.scan(options);
//            while (cursor.hasNext()) {
//                keys.add(new String(cursor.next()));
//            }
//            return null;
//        });
//        Collections.sort(keys); // 排序键列表
//        return keys;
//    }
}
