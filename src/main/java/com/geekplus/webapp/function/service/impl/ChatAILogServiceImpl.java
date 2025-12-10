package com.geekplus.webapp.function.service.impl;

import com.geekplus.webapp.function.entity.ChatAILog;
import com.geekplus.webapp.function.mapper.ChatAILogMapper;
import com.geekplus.common.util.datetime.DateUtil;
import com.geekplus.webapp.function.service.IChatAILogService;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ChatAI聊天记录日志
Service业务层处理
 *
 * @author 佚名
 * @date 2023-02-25
 */
@Service
public class ChatAILogServiceImpl implements IChatAILogService
{
    @Autowired
    private ChatAILogMapper chatAILogMapper;

    /**
     * 查询ChatAI聊天记录日志

     *
     * @param id ChatAI聊天记录日志
ID
     * @return ChatAI聊天记录日志

     */
    @Override
    public ChatAILog selectChatAILogById(Long id)
    {
        return chatAILogMapper.selectChatAILogById(id);
    }

    /**
     * 查询ChatAI聊天记录日志
列表
     *
     * @param chatAILog ChatAI聊天记录日志

     * @return ChatAI聊天记录日志

     */
    @Override
    public List<ChatAILog> selectChatAILogList(ChatAILog chatAILog)
    {
        return chatAILogMapper.selectChatAILogList(chatAILog);
    }

    public List<ChatAILog> getChatAILogListByRecordId(String chatRecordId) {
        return chatAILogMapper.getChatAILogListByRecordId(chatRecordId);
    }

    /**
     * 新增ChatAI聊天记录日志

     *
     * @param chatAILog ChatAI聊天记录日志

     * @return 结果
     */
    @Override
    public int insertChatAILog(ChatAILog chatAILog)
    {
        chatAILog.setCreateTime(DateUtil.getNowDate());
        return chatAILogMapper.insertChatAILog(chatAILog);
    }

    /**
     * 修改ChatAI聊天记录日志

     *
     * @param chatAILog ChatAI聊天记录日志

     * @return 结果
     */
    @Override
    public int updateChatAILog(ChatAILog chatAILog)
    {
        return chatAILogMapper.updateChatAILog(chatAILog);
    }

    /**
     * 批量删除ChatAI聊天记录日志

     *
     * @param ids 需要删除的ChatAI聊天记录日志
ID
     * @return 结果
     */
    @Override
    public int deleteChatAILogByIds(Long[] ids)
    {
        return chatAILogMapper.deleteChatAILogByIds(ids);
    }

    /**
     * 删除ChatAI聊天记录日志
信息
     *
     * @param id ChatAI聊天记录日志
ID
     * @return 结果
     */
    @Override
    public int deleteChatAILogById(Long id)
    {
        return chatAILogMapper.deleteChatAILogById(id);
    }

    @Override
    public int deleteChatAILogByChatRecordId(String chatRecordId) {
        return chatAILogMapper.deleteChatAILogByChatRecordId(chatRecordId);
    }

    /**
     * @Author geekplus
     * @Description //查询出所有所谓聊天记录ID
     * @Param
     * @Throws
     * @Return {@link }
     */
    @Override
    public List<String> selectAllChatRecordId(String username) {
        return chatAILogMapper.selectAllChatRecordId(username);
    }

    @Override
    public void deleteAll(){ chatAILogMapper.removeAll();}

    @Override
    public int deleteTempUserChatRecord() {
        return chatAILogMapper.removeTempUserChatRecord();
    }
}
