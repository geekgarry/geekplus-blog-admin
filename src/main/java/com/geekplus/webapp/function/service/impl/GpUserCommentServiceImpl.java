package com.geekplus.webapp.function.service.impl;

import com.geekplus.common.util.datetime.DateUtil;
import com.geekplus.webapp.function.entity.GpUserComment;
import com.geekplus.webapp.function.mapper.GpUserCommentMapper;
import com.geekplus.webapp.function.service.IGpUserCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 用户评论回复留言Service业务层处理
 *
 * @author 佚名
 * @date 2023-03-12
 */
@Service
@Slf4j
public class GpUserCommentServiceImpl implements IGpUserCommentService
{
    @Autowired
    private GpUserCommentMapper gpUserCommentMapper;

    /**
     * 查询用户评论回复留言
     *
     * @param id 用户评论回复留言ID
     * @return 用户评论回复留言
     */
    @Override
    public GpUserComment selectGpUserCommentById(Long id)
    {
        return gpUserCommentMapper.selectGpUserCommentById(id);
    }

    /**
     * 查询用户评论回复留言列表
     *
     * @param gpUserComment 用户评论回复留言,查询列表list
     * @return 用户评论回复留言
     */
    @Override
    public List<GpUserComment> selectGpUserCommentList(GpUserComment gpUserComment)
    {
        List<GpUserComment> list=gpUserCommentMapper.selectGpUserCommentList(gpUserComment);
        return buildTreeList(list);
    }

    /**
      * @Author geekplus
      * @Description //网站用户评论留言，parentId为0，子查询
      * @Param
      * @Throws
      * @Return {@link }
      */
    @Override
    public List<GpUserComment> getUserComment(GpUserComment gpUserComment) {
        List<GpUserComment> list=gpUserCommentMapper.getUserComment(gpUserComment);
        //log.info(list.toString());
        return list;
    }

    /**
      * @Author geekplus
      * @Description //获取网站用户留言的数量
      * @Param
      * @Throws
      * @Return {@link }
      */
    @Override
    public int getUserCommentCount() {
        return gpUserCommentMapper.getUserCommentCount();
    }

    /**
      * @Author geekplus
      * @Description //获取网站用户评论的最新十条数据
      * @Param
      * @Throws
      * @Return {@link }
      */
    @Override
    public List<GpUserComment> getLatestUserComment() {
        return gpUserCommentMapper.getLatestUserComment();
    }

    /**
      * @Author geekplus
      * @Description //获取最热门的六条评论
      * @Param
      * @Throws
      * @Return {@link }
      */
    @Override
    public List<GpUserComment> getHotWebUserComment() {
        return gpUserCommentMapper.getHotWebUserComment();
    }

    /**
      * @Author geekplus
      * @Description // 网站用户留言评论回复
      * @Param
      * @Throws
      * @Return {@link }
      */
    @Override
    public int insertUserComment(GpUserComment gpUserComment) {
        gpUserComment.setCreateTime(DateUtil.getNowDate());
        return gpUserCommentMapper.insertUserComment(gpUserComment);
    }
    /**
     * 新增用户评论回复留言
     *
     * @param gpUserComment 用户评论回复留言
     * @return 结果
     */
    @Override
    public int insertGpUserComment(GpUserComment gpUserComment)
    {
        gpUserComment.setCreateTime(DateUtil.getNowDate());
        return gpUserCommentMapper.insertGpUserComment(gpUserComment);
    }

    /**
     * 修改用户评论回复留言
     *
     * @param gpUserComment 用户评论回复留言
     * @return 结果
     */
    @Override
    public int updateGpUserComment(GpUserComment gpUserComment)
    {
        return gpUserCommentMapper.updateGpUserComment(gpUserComment);
    }

    /**
     * 批量删除用户评论回复留言
     *
     * @param ids 需要删除的用户评论回复留言ID
     * @return 结果
     */
    @Override
    public int deleteGpUserCommentByIds(Long[] ids)
    {
        return gpUserCommentMapper.deleteGpUserCommentByIds(ids);
    }

    /**
     * 删除用户评论回复留言信息
     *
     * @param id 用户评论回复留言ID
     * @return 结果
     */
    @Override
    public int deleteGpUserCommentById(Long id)
    {
        return gpUserCommentMapper.deleteGpUserCommentById(id);
    }

    //构造二树形目录，利用两次sql查询，适用于查询二层树形结构
    public List<GpUserComment> buildTreeList(List<GpUserComment> listData){
        for (GpUserComment item:listData) {
            GpUserComment params = new GpUserComment();
            params.setParentId(item.getId());
            List<GpUserComment> childrenList = selectGpUserCommentList(params);
            item.setChildren(childrenList);
        }
        return listData;
    }

//    public List<GpUserComment> buildTreeUserComment(List<GpUserComment> gpUserCommentList, Long id){
//        List<GpUserComment> commentsList=new ArrayList<>();
//        List<GpUserComment> childrenList = new ArrayList<>();
//        for (GpUserComment item:gpUserCommentList) {
//            if(item.getParentId()==0){
//                commentsList.add(item);
//            }else if(item.getParentId().equals(id)) {
//                item.setChildren(buildTreeUserComment(gpUserCommentList,item.getId()));
//                childrenList.add(item);
//            }
//        }
//        return commentsList;
//    }

    //获取属性结构菜单目录
    public List<GpUserComment> buildTreeGpUserComment(List<GpUserComment> gpUserCommentList){
        List<GpUserComment> userCommentsList=new ArrayList<>();
        for (GpUserComment item:gpUserCommentList) {
            if(item.getParentId()==0){
                item.setChildren(getChildrenList(gpUserCommentList,item.getId()));
                userCommentsList.add(item);
            }
        }
        return userCommentsList;
    }

    //获取当前目录的子菜单
    private List<GpUserComment> getChildrenList(List<GpUserComment> gpUserCommentList, Long id) {
        List<GpUserComment> childrenList=new ArrayList<>();
        for(Iterator<GpUserComment> iterator = gpUserCommentList.iterator(); iterator.hasNext();){
            GpUserComment item = iterator.next();
            if(item.getParentId().equals(id)){
                item.setChildren(getChildrenList(gpUserCommentList,item.getId()));
                childrenList.add(item);
            }
        }
        return childrenList;
    }
}
