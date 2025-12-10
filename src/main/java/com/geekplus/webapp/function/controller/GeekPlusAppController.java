package com.geekplus.webapp.function.controller;

import com.geekplus.common.constant.Constant;
import com.geekplus.common.core.visit.VisitCounter;
import com.geekplus.common.enums.OperatorType;
import com.geekplus.common.util.encrypt.SignatureUtil;
import com.geekplus.common.util.html.ArticleUtil;
import com.geekplus.common.util.http.HttpClientUtil;
import com.geekplus.common.util.html.HtmlUtil;
import com.geekplus.common.util.json.JsonObjectUtil;
import com.geekplus.webapp.common.service.ChatGPTService;
import com.geekplus.webapp.common.service.IPRecordService;
import com.geekplus.webapp.function.entity.*;
import com.geekplus.webapp.function.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.geekplus.common.annotation.Log;
import com.geekplus.common.config.WebAppConfig;
import com.geekplus.common.core.controller.BaseController;
import com.geekplus.common.domain.Result;
import com.geekplus.common.page.PageDataInfo;
import com.geekplus.common.enums.BusinessType;
import com.geekplus.common.util.datetime.DateUtil;
import com.geekplus.common.util.file.FileUploadUtils;
import com.geekplus.common.util.file.FileUtils;
import com.geekplus.common.util.uuid.IdUtils;
import com.geekplus.framework.config.ServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * author     : geekplus
 * date       : 3/12/23 01:54
 * description:
 */
@Slf4j
@RestController
@RequestMapping("/geekplusapp")
public class GeekPlusAppController extends BaseController {
    //设置上传文件限制格式，列表中为可以上传的类型
    List<String> supportFileFormats =new ArrayList<>(Arrays.asList("doc,docx,xls,xlsx,ppt,pptx,pdf,jpg,jpeg,png,gif,JPG,JPEG,PNG,GIF,bmp,BMP,txt,wmv,mp4".split(",")));

    @Resource
    private ChatGPTService chatGPTService;
    @Resource
    private IGpCarouselService gpCarouselService;
    @Resource
    private IGpFriendlyLinkService gpFriendlyLinkService;
    @Resource
    private IGpUserCommentService gpUserCommentService;
    @Resource
    private IGpArticleCommentService gpArticleCommentService;
    @Resource
    private IGpArticlesService gpArticlesService;
    @Resource
    private IGpArticleTagsService gpArticleTagsService;
    @Resource
    private IGpArticleMapTagService gpArticleMapTagService;
    @Resource
    private IGpArticleCategoryService gpArticleCategoryService;
    @Resource
    private IGpAboutWebService gpAboutWebService;
    @Resource
    private IGpNoticeService gpNoticeService;
    @Resource
    private GpMusicService gpMusicService;
    @Resource
    private ServerConfig serverConfig;
    @Resource
    private VisitCounter visitCounter;
    //@Resource
    //private IPRecordService ipRecordService;
    @Autowired
    private SignatureUtil signer;
    // demo: 实例化，生产请通过配置注入 / KMS 获取 secretKey
    //private final SignatureUtil signer = new SignatureUtil("geek-plus-admin-123456", "v1");

    @GetMapping("/signed-url")
    public Map<String, String> signedUrl(@RequestParam String path) {

        long ts = System.currentTimeMillis() / 1000;
        //long exp = 300;

        String sign = signer.genSignature(path, ts);

        String url = path + "?ts=" + ts + "&sign=" + sign;

        Map<String, String> map = new HashMap<>();
        map.put("url", url);
        return map;
    }

    /**
     * 查询首页跑马灯轮播图列表
     */
    //@PreAuthorize("@ss.hasPermi('function:carousel:list')")
    //@Log(title = "首页聚合数据", businessType = BusinessType.SELECT,operatorType = OperatorType.CLIENT)
    @PostMapping("/getHomeView")
    public Result getHomeViewData(@RequestParam Map<String,Object> map)
    {
        String sixArticleCategoryName=map.get("sixCategory").toString();
        Integer sixArticleCount=Integer.parseInt(map.get("sixArticleCount").toString());
        String oneArticle=map.get("oneArticle").toString();
        String fourArticles=map.get("fourArticles").toString();
        String articleTopic1=map.get("firstTopic").toString();
        String articleTopic2=map.get("secondTopic").toString();
        Integer articleTopicCount=Integer.parseInt(map.get("topicCount").toString());
        //List<GpCarousel> indexCarouselList = gpCarouselService.selectGpCarouselListImage();
        List<GpArticles> tenMostViewedArticleList=gpArticlesService.selectMostViewedGpArticlesListByLimit(null);
        List<GpArticles> sixArticleList=gpArticlesService.selectGpArticlesListByCategoryLimit(sixArticleCategoryName,sixArticleCount);
        GpArticles bigOneArticle=gpArticlesService.selectOneArticleByCategory(oneArticle);
        List<GpArticles> fourArticleList=gpArticlesService.selectFourArticleByCategory(fourArticles);
        List<GpArticles> subjectList1=gpArticlesService.selectGpArticlesListByCategoryLimit(articleTopic1,articleTopicCount);
        List<GpArticles> subjectList2=gpArticlesService.selectGpArticlesListByCategoryLimit(articleTopic2,articleTopicCount);
        List<GpArticleTags> tagArticleCount=gpArticleTagsService.selectTagArticleCount();
        GpNotice noticeInfo=gpNoticeService.selectGpNoticeNewOne();
        Result result= Result.success();
        //result.put("indexCarousel",indexCarouselList);
        result.put("indexTenMostViewedArticles",tenMostViewedArticleList);
        result.put("indexOneArticle", bigOneArticle);
        result.put("indexFourArticles", fourArticleList);
        result.put("indexSixArticles",sixArticleList);
        result.put("indexFirstTopic",subjectList1);
        result.put("indexSecondTopic",subjectList2);
        result.put("indexArticleTag",tagArticleCount);
        result.put("indexNotice",noticeInfo);
        return result;
    }

    @GetMapping("/getIndexView")
    public Result getIndexViewData()
    {
        Result result= Result.success();
        //List<GpArticles> sixMostViewedArticleList=gpArticlesService.selectMostViewedGpArticlesListByLimit(6);
        //GpNotice noticeInfo=gpNoticeService.selectGpNoticeNewOne();
        List<GpArticleTags> tagArticleCount=gpArticleTagsService.selectTagArticleCount();
        List<GpArticles> sixRandomList=gpArticlesService.selectGpArticlesByRandom(Long.parseLong("6"));
        result.put("sixHotArticleList",sixRandomList);
        //result.put("indexNotice",noticeInfo);
        result.put("articleTags",tagArticleCount);
        return result;
    }

    /**
     * 每日名言API接口
     */
    @GetMapping("/getDailyFamousWords")
    public Result getDailyFamousWord()
    {
        Object object=null;
        try {
            Map jsonObject = JsonObjectUtil.jsonToMap(HttpClientUtil.get("https://api.xygeng.cn/one"));
            object= jsonObject.get("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success(object);
    }

    /**
     * 今日名言API接口
     */
    @GetMapping("/getTodayFamousWords")
    public Result getTodayFamousWord()
    {
        Object object=null;
        try {
            object= HtmlUtil.delHTMLTag(HttpClientUtil.get("https://v.api.aa1.cn/api/api-wenan-mingrenmingyan/index.php?aa1=text"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //log.info("每日一句名言："+object.toString());
        return Result.success(object);
    }

    /**
     * 首页在线音乐
     */
    @GetMapping("/getOnlineMusic")
    public Result getOnlineMusic()
    {
        GpMusic gpMusic=new GpMusic();
        gpMusic.setIsDisplay("1");
        List<GpMusic> list = gpMusicService.selectGpMusicList(gpMusic);
        list.stream().map(gpMusic1 -> {
            gpMusic1.setUrl(signer.signedUrl(gpMusic1.getUrl()));
            return gpMusic1;
        }).collect(Collectors.toList());
        return Result.success(list);
    }

    /**
     * 查询首页跑马灯轮播图列表
     */
    @Log(title = "首页轮播图", businessType = BusinessType.SELECT,operatorType = OperatorType.CLIENT)
    @GetMapping("/getCarousel")
    public Result getCarouselList()
    {
        List<GpCarousel> list = gpCarouselService.selectGpCarouselListImage().stream().map(gpCarousel -> {
            gpCarousel.setCarouselImg(signer.signedUrl(gpCarousel.getCarouselImg()));
            return gpCarousel;
        }).collect(Collectors.toList());
        // 替换
//        list.forEach(b -> {
//            b.setCarouselImg(signer.signedUrl(b.getCarouselImg()));
//        });
        return Result.success(list);
    }

    /**
     * @Author geekplus
     * @Description //TODO
     * @Param
     * @Throws
     * @Return {@link }
     */
    //@Log(title = "友情链接", businessType = BusinessType.SELECT,operatorType = OperatorType.CLIENT)
    @GetMapping("/displayFriendlyLink")
    public Result displayFriendlyLink(){
        List<GpFriendlyLink> list=gpFriendlyLinkService.displayGpFriendlyLink();
        return Result.success(list);
    }

    /**
     * @Author geekplus
     * @Description //用户申请友情链接
     * @Param
     * @Throws
     * @Return {@link }
     */
    @Log(title = "申请友情链接", businessType = BusinessType.INSERT,operatorType = OperatorType.CLIENT)
    @PostMapping("/userAppFriendlyLink")
    public Result userAppFriendlyLink(@RequestBody GpFriendlyLink gpFriendlyLink){
        int size=gpFriendlyLinkService.insertGpFriendlyLink(gpFriendlyLink);
        if(size>0){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    /**
      * @Author geekplus
      * @Description //我的网站用户评论留言
      * @Param
      * @Throws
      * @Return {@link }
      */
    //@Log(title = "博客留言回复", businessType = BusinessType.INSERT,operatorType = OperatorType.CLIENT)
    @PostMapping("/userCommentMessage")
    public Result insertUserComment(@RequestBody GpUserComment gpUserComment){
        int size=gpUserCommentService.insertUserComment(gpUserComment);
        if(size>0){
            return Result.success();
        }else {
            return Result.error();
        }
    }

    /**
     * @Author geekplus
     * @Description //我的网站用户文章评论留言
     * @Param
     * @Throws
     * @Return {@link }
     */
    @PostMapping("/articleCommentMessage")
    public Result insertArticleComment(@RequestBody GpUserComment gpUserComment){
        int size=gpArticleCommentService.insertArticleComment(gpUserComment);
        if(size>0){
            return Result.success();
        }else {
            return Result.error();
        }
    }

    /**
     * @Author geekplus
     * @Description //获取网站给我留言模块用户的留言信息
     * @Param
     * @Throws
     * @Return {@link }
     */
    @GetMapping("/getAllUserComment")
    public Result getAllUserComment(GpUserComment gpUserComment){
        startPage();
        List<GpUserComment> list=gpUserCommentService.getUserComment(gpUserComment);
        int count=gpUserCommentService.getUserCommentCount();
        Result result = Result.success();
        result.put("rows", list);
        result.put("total", new PageInfo(list).getTotal());
        result.put("count", count);
        return result;
    }

    /**
     * @Author geekplus
     * @Description //获取文章模块下面用户的留言信息
     * @Param
     * @Throws
     * @Return {@link }
     */
    @GetMapping("/getAllArticleComment")
    public Result getAllArticleComment(GpUserComment gpUserComment){
        startPage();
        List<GpUserComment> list=gpArticleCommentService.getArticleComment(gpUserComment);
        int count = gpArticleCommentService.getArticleCommentCount(gpUserComment.getTopicId());
        Result result = Result.success();
        result.put("rows", list);
        result.put("total", new PageInfo(list).getTotal());
        result.put("count", count);
        return result;
    }

//    public List<GpUserComment> getUserCommentListToList(GpUserComment gpUserComment){
//        List<GpUserComment> allList=new ArrayList<>();
//        List<GpUserComment> list=gpUserCommentService.getUserComment(gpUserComment);
//        for(GpUserComment userComment : list){
//            if(userComment.getReplyUserComment()!=null||userComment.getReplyUserComment().size()!=0){
//                GpUserComment gpUserComment1=new GpUserComment();
//                gpUserComment1.setParentId(userComment.getId());
//                getUserCommentListToList(gpUserComment1);
//                //allList.add(gpUserComment);
//            }
//            allList.add(userComment);
//        }
//        return allList;
//    }

    /**
     * @Author geekplus
     * @Description //获取网站用户的留言评论数量，给我留言
     * @Param
     * @Throws
     * @Return {@link }
     */
    @GetMapping("/getUserCommentCount")
    public Result getUserCommentCount(){
        int count=gpUserCommentService.getUserCommentCount();
        return Result.success(count);
    }

    /**
     * @Author geekplus
     * @Description //获取最新的十条用户的文章留言评论
     * @Param
     * @Throws
     * @Return {@link }
     */
    @Log(title = "获取热门文章留言评论", businessType = BusinessType.SELECT,operatorType = OperatorType.CLIENT)
    @GetMapping("/getLatestArticleUserComment")
    public Result getLatestArticleCommentCount(){
        List<GpUserComment> list=gpArticleCommentService.getLatestArticleComment();
        return Result.success(list);
    }

    /**
     * @Author geekplus
     * @Description //获取热门的六条用户的留言评论
     * @Param
     * @Throws
     * @Return {@link }
     */
    //@Log(title = "获取热门文章留言评论", businessType = BusinessType.SELECT,operatorType = OperatorType.CLIENT)
    @GetMapping("/getHotWebUserComment")
    public Result getHotUserCommentCount(){
        List<GpUserComment> list=gpUserCommentService.getHotWebUserComment();
        return Result.success(list);
    }

    /**
     * @Author geekplus
     * @Description //查询网站信息，浏览量，比如标题，网站介绍，网站底部备案信息等
     * @Param
     * @Throws
     * @Return {@link }
     */
    @GetMapping(value="/getGpWebTitleInfo")
    public Result getGpWebTitleInfo(Integer id){
        GpAboutWeb webInfo=gpAboutWebService.selectGpWebInfo(id);
        return Result.success(webInfo);
    }

    /**
      * @Author geekplus
      * @Description //统计页面访问量
      * @Param
      * @Throws
      * @Return {@link }
      */
    @GetMapping("/visitInfo")
    public Result getVisitInfo() {
        long visitCount = visitCounter.getCount();
        //List<String> ipList = ipRecordService.getAllIPs();

        Result ajax = Result.success();
        ajax.put("visitCount", visitCount);
        return ajax;
    }

    /**
     * @Author geekplus
     * @Description //查询关于我的信息，我的个人介绍等
     * @Param
     * @Throws
     * @Return {@link }
     */
    @Log(title = "关于我的信息", businessType = BusinessType.SELECT,operatorType = OperatorType.CLIENT)
    @GetMapping("/getAboutMyGpWeb/{id}")
    public Result getAboutMyGpWeb(@PathVariable("id") Integer id){
        GpAboutWeb aboutWeb=gpAboutWebService.selectAboutGpWeb(id);
        return Result.success(aboutWeb);
    }

    /**
     * @Author geekplus
     * @Description //固定浏览量最多前几条的不同目录文章，也可以传入一个limitCount
     * @Param
     * @Throws
     * @Return {@link }
     */
    @GetMapping("/getMostViewedArticle")
    public Result getMostViewedArticle(@RequestParam(value = "limitCount", required = false) Integer limitCount){
//        if(gpArticles.getIsDisplay()==null||"".equals(gpArticles.getIsDisplay())){
//            gpArticles.setIsDisplay("1");
//        }
        List<GpArticles> list=gpArticlesService.selectMostViewedGpArticlesListByLimit(limitCount);
        list.stream().map(gpArticles -> {
            gpArticles.setIndexPicture(signer.signedUrl(gpArticles.getIndexPicture()));
            return gpArticles;
        }).collect(Collectors.toList());
        return Result.success(list);
    }

    /**
     * @Author geekplus
     * @Description //动态条件查询某目录前几条
     * @Param
     * @Throws
     * @Return {@link }
     */
    @GetMapping("/getArticlesListByCategoryLimit")
    public Result selectGpArticlesListByCategoryLimit(String pathName, Integer limitCount){
        List<GpArticles> list=gpArticlesService.selectGpArticlesListByCategoryLimit(pathName,limitCount);
        list.stream().map(gpArticles -> {
            gpArticles.setIndexPicture(signer.signedUrl(gpArticles.getIndexPicture()));
            return gpArticles;
        }).collect(Collectors.toList());
        return Result.success(list);
    }

    /**
      * @Author geekplus
      * @Description //查询一个存在于id列表的文章
      * @Param
      * @Throws
      * @Return {@link }
      */
    @RequestMapping(value = "/getArticlesListByIds")
    public Result selectGpArticlesListByIdList(@RequestBody Long[] ids){
        return Result.success(gpArticlesService.selectGpArticlesListByIdList(ids));
    }

    /**
     * @Author geekplus
     * @Description //根据目录ID查询分页的文章,目录文章页面动态分页查询
     * @Param
     * @Throws
     * @Return {@link }
     */
    @Log(title = "文章分类查询", businessType = BusinessType.SELECT,operatorType = OperatorType.CLIENT)
    @GetMapping("/getGpArticlesListByCategory")
    public Result selectGpArticlesListByCategory(String pathName,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        //startPage();
        List<GpArticles> list=gpArticlesService.selectGpArticlesListByCategory(pathName);
        list.stream().map(gpArticles -> {
            gpArticles.setIndexPicture(signer.signedUrl(gpArticles.getIndexPicture()));
            return gpArticles;
        }).collect(Collectors.toList());
        PageInfo pageInfo=new PageInfo(list);
        Result ajax=Result.success();
        ajax.put("rows",list);
        ajax.put("total",pageInfo.getTotal());
        return ajax;
    }

    /**
     * @Author geekplus
     * @Description //根据搜索关键字查询分页的文章,目录文章页面动态查询分页
     * @Param
     * @Throws
     * @Return {@link }
     */
    @Log(title = "用户搜索文章", businessType = BusinessType.SELECT,operatorType = OperatorType.CLIENT)
    @GetMapping("/selectGpArticlesListByKeyWords")
    public Result selectGpArticlesListByKeyWords(String articleTitle,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        //startPage();
        List<GpArticles> list=gpArticlesService.selectGpArticlesListByKeyWords(articleTitle);list.stream().map(gpArticles -> {
            gpArticles.setIndexPicture(signer.signedUrl(gpArticles.getIndexPicture()));
            return gpArticles;
        }).collect(Collectors.toList());
        PageInfo pageInfo=new PageInfo(list);
        Result result=Result.success();
        result.put("rows",list);
        result.put("total",pageInfo.getTotal());
        return result;
    }

    /**
     * @Author geekplus
     * @Description //查询四加一个大屏显示特别推荐的文章
     * @Param
     * @Throws
     * @Return {@link }
     */
    @GetMapping("/getFourPlusOneArticles")
    public Result getOneRecommendArticle(String firstPathName,String secondPathName){
        GpArticles list=gpArticlesService.selectOneArticleByCategory(firstPathName);
        list.setIndexPicture(signer.signedUrl(list.getIndexPicture()));
        List<GpArticles> fourList=gpArticlesService.selectFourArticleByCategory(secondPathName);
        fourList.stream().map(gpArticles -> {
            gpArticles.setIndexPicture(signer.signedUrl(gpArticles.getIndexPicture()));
            return gpArticles;
        }).collect(Collectors.toList());
        Result ajax = Result.success();
        ajax.put("one", list);
        ajax.put("four", fourList);
        return ajax;
    }

    /**
     * @Author geekplus
     * @Description //查询随机特别推荐的文章
     * @Param
     * @Throws
     * @Return {@link }
     */
    @GetMapping("/getRandRecommendArticle")
    public Result getRandRecommendArticle(@RequestParam("articleCount") Long articleCount){
        List<GpArticles> list=gpArticlesService.selectGpArticlesByRandom(articleCount);
        list.stream().map(gpArticles -> {
            gpArticles.setIndexPicture(signer.signedUrl(gpArticles.getIndexPicture()));
            return gpArticles;
        }).collect(Collectors.toList());
        return Result.success(list);
    }

    /**
     * @Author geekplus
     * @Description //查询随机特别推荐的文章
     * @Param
     * @Throws
     * @Return {@link }
     */
    @GetMapping("/getRecommendArticle")
    public Result getRandRecommendArticle2(@RequestParam("articleCount") Long articleCount){
        List<GpArticles> list=gpArticlesService.selectGpArticlesByRandom(articleCount);
        list.stream().map(gpArticles -> {
            gpArticles.setIndexPicture(signer.signedUrl(gpArticles.getIndexPicture()));
            return gpArticles;
        }).collect(Collectors.toList());
        return Result.success(list);
    }

/*************************下面还未定义*********************/
    /**
     * @Author geekplus
     * @Description //查询四个大屏显示特别推荐的文章
     * @Param
     * @Throws
     * @Return {@link }
     */
    @GetMapping("/getFourRecommendArticle")
    public Result getFourRecommendArticle(GpArticles gpArticles){
        List<GpArticles> list=gpArticlesService.selectGpArticlesList(gpArticles);
        list.stream().map(gpArticles1 -> {
            gpArticles1.setIndexPicture(signer.signedUrl(gpArticles1.getIndexPicture()));
            return gpArticles1;
        }).collect(Collectors.toList());
        return Result.success(list);
    }
/***********************************************/

    /**
     * 获取父目录子菜单循环递归目录菜单，sql子查询方式
     */
    //@Log(title = "文章类型目录", businessType = BusinessType.DELETE)
    @GetMapping("/listAllCategoryApp")
    public Result listSubParentCategoryForAPP()
    {
        List<GpArticleCategory> list = gpArticleCategoryService.selectSubParentCategory();
        return Result.success(list.subList(2,3));
    }

    /**
     * 获取父目录子菜单循环递归目录菜单，sql子查询方式
     */
    //@Log(title = "文章类型目录", businessType = BusinessType.DELETE)
    @GetMapping("/listSubParentCategory")
    public Result listSubParentCategory()
    {
        return Result.success(gpArticleCategoryService.selectSubParentCategory());
    }

    /**
     * 获取所有子菜单
     */
    //@Log(title = "文章类型目录", businessType = BusinessType.DELETE)
    @GetMapping("/listSubCategory")
    public Result listSubCategory()
    {
        GpArticleCategory gpArticleCategory=new GpArticleCategory();
        gpArticleCategory.setMenuType("1");
        return Result.success(gpArticleCategoryService.selectArticleCategoryList(gpArticleCategory));
    }

    /**
     * @Author geekplus
     * @Description //查询各个标签的文章数量
     * @Param
     * @Throws
     * @Return {@link }
     */
    @GetMapping("/getTagArticleCount")
    public Result selectTagArticleCount(){
        return Result.success(gpArticleTagsService.selectTagArticleCount());
    }

    /**
     * @Author geekplus
     * @Description //根据标签的tagId查询每个标签的所有文章列表,带分页
     * @Param
     * @Throws
     * @Return {@link }
     */
    @GetMapping("/selectArticleListForTag")
    public Result selectTagDeArticle(String tagName,Long tagId,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<GpArticles> list=gpArticlesService.selectTagDeArticle(tagName,tagId);
        list.stream().map(gpArticles -> {
            gpArticles.setIndexPicture(signer.signedUrl(gpArticles.getIndexPicture()));
            return gpArticles;
        }).collect(Collectors.toList());
        PageInfo pageInfo=new PageInfo(list);
        Result ajax=Result.success();
        ajax.put("rows",list);
        ajax.put("total",pageInfo.getTotal());
        return ajax;
    }

    /**
      * @Author geekplus
      * @Description //查询最新的一条通知
      * @Param
      * @Throws
      * @Return {@link }
      */
    @RequestMapping(value = "/getGpNoticeNewOne",method = {RequestMethod.POST, RequestMethod.GET})
    public Result selectGpNoticeNewOne(){
        GpNotice gpNotice = gpNoticeService.selectGpNoticeNewOne();
        gpNotice.setNoticeImg(signer.signedUrl(gpNotice.getNoticeImg()));
        return Result.success(gpNotice);
    }

    /**
     * @Author geekplus
     * @Description //查询网站弹窗通知
     * @Param
     * @Throws
     * @Return {@link }
     */
    @RequestMapping(value = "/getWebPopUpNotice",method = {RequestMethod.POST, RequestMethod.GET})
    public Result selectWebPopUpGpNotice(Long id){
        GpNotice gpNotice = gpNoticeService.selectGpNoticeById(id);
        gpNotice.setNoticeImg(signer.signedUrl(gpNotice.getNoticeImg()));
        return Result.success(gpNotice);
    }

    /**
     * @Author geekplus
     * @Description //查询最新的五条通知
     * @Param
     * @Throws
     * @Return {@link }
     */
    @RequestMapping(value = "/getGpNoticeNewFive",method = {RequestMethod.POST, RequestMethod.GET})
    public Result selectGpNoticeNewFive(){
        List<GpNotice> list = gpNoticeService.selectGpNoticeNewFive();
        list.stream().map(gpNotice -> {
            gpNotice.setNoticeImg(signer.signedUrl(gpNotice.getNoticeImg()));
            return gpNotice;
        }).collect(Collectors.toList());
        return Result.success(list);
    }

    /**
     * @Author geekplus
     * @Description //查询网站通知消息
     * @Param
     * @Throws
     * @Return {@link }
     */
    @RequestMapping(value = "/getGpNoticeInfo",method = {RequestMethod.POST, RequestMethod.GET})
    public PageDataInfo selectGpNoticeInfo(GpNotice gpNotice){
        startPage();
        List<GpNotice> list = gpNoticeService.selectGpNoticeList(gpNotice);
        list.stream().map(xzz -> {
            gpNotice.setNoticeImg(signer.signedUrl(gpNotice.getNoticeImg()));
            return gpNotice;
        }).collect(Collectors.toList());
        return getDataTable(list);
    }

    /**
      * @Author geekplus
      * @Description //用户投稿文章
      * @Param
      * @Throws
      * @Return {@link }
      */
    //@Log(title = "用户投稿", businessType = BusinessType.INSERT)
    @PostMapping("/userWriteGpArticles")
    public Result userWriteGpArticles(@RequestBody GpArticles gpArticles){
        int size=gpArticlesService.userWriteGpArticles(gpArticles);
        return size > 0 ? Result.success() : Result.error();
    }

    /**
      * @Author geekplus
      * @Description //用户端根据文章ID查看文章详情页面,设置文章是否显示is_display
      * @Param
      * @Throws
      * @Return {@link }
      */
    @PostMapping(value = "/getArticle")
    public Result getArticleDetail(@RequestBody HashMap<String,String> map)
    {
        String isDisplay=map.get("isDisplay");
        if("".equals(map.get("isDisplay"))||map.get("isDisplay")==null) {
            isDisplay="1";
        }
        Long id=Long.parseLong(map.get("id"));
        GpArticles gpArticles=gpArticlesService.selectGpArticlesByIdForUser(isDisplay,id);
        gpArticles.setArticleContent(ArticleUtil.processRichText(gpArticles.getArticleContent()));
        Result ajax=Result.success(gpArticles);
        ajax.put("prevRow",gpArticlesService.selectPrevGpArticle(null,id));
        ajax.put("nextRow",gpArticlesService.selectNextGpArticle(null,id));
        return ajax;
    }

    /**
     * @Author geekplus
     * @Description //用户端根据文章ID查看文章详情页面,设置文章是否显示is_display
     * @Param
     * @Throws
     * @Return {@link }
     */
    @GetMapping(value = "/getArticleIsDisplay/{id}")
    public Result getArticleDetailIsDisplay(@PathVariable("id") Long id)
    {
        //String isDisplay="1";
        return Result.success(gpArticlesService.selectGpArticlesByIdForWeb(id));
    }

    /**
     * @Author geekplus
     * @Description //用户端更新文章缩略图
     * @Param
     * @Throws
     * @Return {@link }
     */
    @GetMapping(value = "/updateAllArticleThumbnail")
    public Result updateArticleThumbnail()
    {
        //String isDisplay="1";
        int size=0;
        List<GpArticles> gpArticlesList=gpArticlesService.selectGpArticlesList(null);
        for(GpArticles gpArticles:gpArticlesList){
            int upCount=gpArticlesService.updateGpArticles(gpArticles);
            size+=upCount;
        }
        return Result.success("成功了"+size+"条");
    }

    /**
     * @Author geekplus
     * @Description //用户端根据ID更新文章缩略图
     * @Param
     * @Throws
     * @Return {@link }
     */
    @GetMapping(value = "/updateArticleThumbnail/{articleId}")
    public Result updateArticleThumbnail(@PathVariable String articleId)
    {
        //String isDisplay="1";
        GpArticles gpArticleInfo=gpArticlesService.selectGpArticlesById(Long.parseLong(articleId));
        int upCount=gpArticlesService.updateGpArticles(gpArticleInfo);
        return toResult(upCount);
    }

    /**
      * @Author geekplus
      * @Description //提供前端博客用户查询文章列表,首页混合所有类型,分页查询功能
      * @Param
      * @Throws
      * @Return {@link }
      */
    @GetMapping(value = "/getArticleList")
    public PageDataInfo getArticleList(GpArticles gpArticles)
    {
//        GpArticles gpArticles=new GpArticles();
//        gpArticles.setArticleCategory(String.valueOf(categoryId));
        startPage();
        List<GpArticles> list=gpArticlesService.selectGpArticlesListForUser(gpArticles);
//        PageInfo pageInfo=new PageInfo(list);
//        Map map=new HashMap();
//        map.put("data",list);
//        map.put("total",pageInfo.getTotal());
//        map.put("code",200);
//        map.put("msg","成功查询数据！");
        return getDataTable(list);
    }

    /**
     * @Author geekplus
     * @Description //提供在写文章时查询所有标签
     * @Param
     * @Throws
     * @Return {@link }
     */
    @GetMapping(value = "/getArticleTagList")
    public Result getArticleTagList(GpArticleTags gpArticleTags)
    {
        return Result.success(gpArticleTagsService.selectGpArticleTagsList(gpArticleTags));
    }

    /**
     * @Author geekplus
     * @Description //在写文章时，需要插入文章的所属标签
     * @Param
     * @Throws
     * @Return {@link }
     */
    @PostMapping(value = "/insertArticleMapTag")
    public Result insertGpArticleMapTag(@RequestBody Map<String,Object> map)
    {
        return toResult(gpArticleTagsService.insertGpArticleMapTag(map));
        //return Result.success(gpArticleTagsService.insertGpArticleMapTag(map));
    }

    /**
     * 通用上传文件请求
     */
    @PostMapping("/uploadFile")
    public Result uploadFile(@RequestPart("file") MultipartFile file) throws Exception
    {
//        if(!checkFormats(file.getOriginalFilename())){
//            return Result.error("上传图片格式不是png,jpg或jpeg！");
//        }
        try
        {
            String realFilePath;
            if(FileUtils.isImageFile(file)){
                realFilePath=File.separator+"article"+File.separator+ DateUtil.datePath();
            }else if(FileUtils.isVideoFile(file)) {
                realFilePath=File.separator+"video"+File.separator+ DateUtil.datePath();
            }else if(FileUtils.isAudioFile(file)) {
                realFilePath=File.separator+"music"+File.separator+ DateUtil.datePath();
            }else {
                realFilePath=File.separator+"document"+File.separator+ DateUtil.datePath();
            }
            // 上传文件路径,加上以日期为路径的一个目录
            //String filePath = WebAppConfig.getUploadPath();

            String uploadDir= WebAppConfig.getProfile()+realFilePath;
            // 上传并获取文件名称
            String fileName = "";
            String originalName=file.getOriginalFilename();
            String extension = FileUploadUtils.getExtension(file);
            //String uuidFileName = UUID.randomUUID().toString() + ".png";
            //目标文件
            //File dest = new File(uploadDir + "head_img" ,uuidFileName);
            //保存文件
            //file.transferTo(dest);
            fileName = IdUtils.getSHAFileULID() + "." + extension;

            // 上传并返回新文件名称
            //String fileName = FileUploadUtils.upload(filePath, file);
            //File desc = new File(uploadDir + File.separator + fileName);
            File desc =FileUtils.getExistFileCategory(uploadDir + File.separator + fileName);
            file.transferTo(desc);
            //String pathFileName = getPathFileName(baseDir, fileName);
            String resultFileName= Constant.RESOURCE_PREFIX+realFilePath+"/"+fileName;
            String url = serverConfig.getUrl() + resultFileName;
            log.info("用户请求URL信息："+serverConfig.getUrl());
            Result ajax = Result.success();
            ajax.put("fileName", fileName);
            ajax.put("originalFileName", originalName);
            ajax.put("url", resultFileName);
            return ajax;
        }
        catch (Exception e)
        {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 查询文件里的所有图片，读取某个文件夹下的所有文件
     */
    @GetMapping("/getImageList")
    public Result listFileImage(String filePath)
    {
//        try{
//            List<String> listImage= FileUtils.readFileImage(WebAppConfig.getProfile(),File.separator+filePath);
//            return Result.success(listImage);
//        }catch(IOException e){
//            return Result.success(e.getMessage());
//        }
        File file=new File(WebAppConfig.getProfile()+ File.separator + filePath);
        List<String> list= new ArrayList<>();
        FileUtils.getDirectoryAllFile(file,list);
        list.stream().map(url -> url = signer.signedUrl(url)
        ).collect(Collectors.toList());
        return Result.success(list);
    }

    /**
     * 查询文件里的所有图片，删除某个图片文件
     */
    @Log(title = "删除文件夹里的图片文件", businessType = BusinessType.DELETE)
    @PostMapping("/deleteFile")
    public Result deleteFile(@RequestBody List<Map> filePaths)
    {
        int length=filePaths.size();
        for (int i = 0; i < filePaths.size(); i++) {
            String filePath=filePaths.get(i).get("filePath").toString();
            String profile= Constant.RESOURCE_PREFIX;//profile
            String allFilePath=WebAppConfig.getProfile()+filePath.replace(profile,"");
            int ds=FileUtils.deleteFileCategory(allFilePath);
            length-=ds;
        }
        if(length==0){
            return Result.success("删除文件成功！");
        }else{
            return Result.success("删除文件失败！");
        }
    }

    /**
      * @Author geekplus
      * @Description //用于用户端更新浏览量和点赞数
      * @Param
      * @Throws
      * @Return {@link }
      */
    @GetMapping("/updateArticleViewCount")
    public Result updateGpArticlesForUser(GpArticles gpArticles){
        return toResult(gpArticlesService.updateGpArticlesForUser(gpArticles));
    }

    /**
      * @Author geekplus
      * @Description //查找当前文章的上一篇
      * @Param
      * @Throws
      * @Return {@link }
      */
    @GetMapping("/getCurrentPrevArticle")
    public Result getPrevArticleInfo(String pathName,Long articleId){
        return Result.success(gpArticlesService.selectPrevGpArticle(pathName,articleId));
    }

    /**
      * @Author geekplus
      * @Description //查找当前文章的下一篇
      * @Param
      * @Throws
      * @Return {@link }
      */
    @GetMapping("/getCurrentNextArticle")
    public Result getNextArticleInfo(String pathName,Long articleId){
        return Result.success(gpArticlesService.selectNextGpArticle(pathName,articleId));
    }

    /**
     * 去掉指定字符串的开头的指定字符
     * @param stream 原始字符串
     * @param trim 要删除的字符串
     * @return
     */
    public static String StringStartTrim(String stream, String trim) {
        // null或者空字符串的时候不处理
        if (stream == null || stream.length() == 0 || trim == null || trim.length() == 0) {
            return stream;
        }
        // 要删除的字符串结束位置
        int end;
        // 正规表达式
        String regPattern = "[" + trim + "]*+";
        Pattern pattern = Pattern.compile(regPattern, Pattern.CASE_INSENSITIVE);
        // 去掉原始字符串开头位置的指定字符
        Matcher matcher = pattern.matcher(stream);
        if (matcher.lookingAt()) {
            end = matcher.end();
            stream = stream.substring(end);
        }
        // 返回处理后的字符串
        return stream;
    }

    //文件类型校验方法
    private boolean checkFormats(String fileFullName){
        String suffix = fileFullName.substring(fileFullName.lastIndexOf(".") + 1).toLowerCase();
        return supportFileFormats.stream().anyMatch(suffix::contains);
    }

}
