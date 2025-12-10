package com.geekplus.webapp.function.service.impl;

import com.geekplus.common.constant.Constant;
import com.geekplus.common.util.RegexUtil;
import com.geekplus.common.util.html.ArticleUtil;
import com.geekplus.webapp.function.mapper.GpArticlesMapper;
import com.geekplus.common.util.datetime.DateUtil;
import com.geekplus.webapp.function.entity.GpArticles;
import com.geekplus.webapp.function.service.IGpArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章Service业务层处理
 *
 * @author 佚名
 * @date 2023-03-12
 */
@Service
public class GpArticlesServiceImpl implements IGpArticlesService
{
    @Autowired
    private GpArticlesMapper gpArticlesMapper;

    /**
     * 查询文章
     *
     * @param id 文章ID
     * @return 文章
     */
    @Override
    public GpArticles selectGpArticlesById(Long id)
    {
        GpArticles gpArticles=gpArticlesMapper.selectGpArticlesById(id);
        //gpArticles.setOldArticleContent(gpArticles.getArticleContent());
        return gpArticles;
    }

    /**
      * @Author geekplus
      * @Description //前端网站查询文章详情
      * @Param
      * @Throws
      * @Return {@link }
      */
    @Override
    public GpArticles selectGpArticlesByIdForUser(String isDisplay, Long id) {
        return gpArticlesMapper.selectGpArticlesByIdForUser(isDisplay,id);
    }

    /**
      * @Author geekplus
      * @Description //前端网站查询文章详情
      * @Param
      * @Throws
      * @Return {@link }
      */
    @Override
    public GpArticles selectGpArticlesByIdForWeb(Long id) {
        return gpArticlesMapper.selectGpArticlesByIdForWeb(id);
    }

    /**
      * @Author geekplus
      * @Description //默认查询每个目录浏览量最多的前几条，如果没有参数则默认为10，可以传入具体的limit
      * @Param
      * @Throws
      * @Return {@link }
      */
    @Override
    public List<GpArticles> selectMostViewedGpArticlesListByLimit(Integer limitCount) {
        if(limitCount == null){
            limitCount = 10;
        }
        return gpArticlesMapper.selectMostViewedGpArticlesListByLimit(limitCount);
    }

    /**
      * @Author geekplus
      * @Description //动态条件查询某目录时间最新排序前几条
      * @Param
      * @Throws
      * @Return {@link }
      */
    @Override
    public List<GpArticles> selectGpArticlesListByCategoryLimit(String pathName, Integer limitCount) {
        return gpArticlesMapper.selectGpArticlesListByCategoryLimit(pathName,limitCount);
    }

    /**
      * @Author geekplus
      * @Description //查询包含在id数组中的所有文章列表
      * @Param
      * @Throws
      * @Return {@link }
      */
    @Override
    public int selectGpArticlesListByIdList(Long[] ids) {
        return gpArticlesMapper.selectGpArticlesListByIdList(ids);
    }

    //根据目录ID动态查询分页的文章
    @Override
    public List<GpArticles> selectGpArticlesListByCategory(String pathName) {
        return gpArticlesMapper.selectGpArticlesListByCategory(pathName);
    }

    @Override
    public List<GpArticles> selectGpArticlesListByKeyWords(String articleTitle) {
        return gpArticlesMapper.selectGpArticlesListByKeyWords(articleTitle);
    }

    //根据目录ID查询首页大屏的一篇文章
    @Override
    public GpArticles selectOneArticleByCategory(String pathName) {
        return gpArticlesMapper.selectOneArticleByCategory(pathName);
    }

    //根据目录ID查询首页大屏的四篇文章
    @Override
    public List<GpArticles> selectFourArticleByCategory(String pathName) {
        return gpArticlesMapper.selectFourArticleByCategory(pathName);
    }

    /**
      * @Author geekplus
      * @Description //随机推荐文章
      * @Param
      * @Throws
      * @Return {@link }
      */
    @Override
    public List<GpArticles> selectGpArticlesByRandom(Long articleCount) {
        return gpArticlesMapper.selectGpArticlesByRandom(articleCount);
    }

    /**
     * 查询文章列表
     *
     * @param gpArticles 文章
     * @return 文章
     */
    @Override
    public List<GpArticles> selectGpArticlesList(GpArticles gpArticles)
    {
        return gpArticlesMapper.selectGpArticlesList(gpArticles);
    }

    /**
      * @Author geekplus
      * @Description //提供前端博客用户查询文章列表
      * @Param
      * @Throws
      * @Return {@link }
      */
    @Override
    public List<GpArticles> selectGpArticlesListForUser(GpArticles gpArticles) {
        return gpArticlesMapper.selectGpArticlesListForUser(gpArticles);
    }

    /**
     * 新增文章
     *
     * @param gpArticles 文章
     * @return 结果
     */
    @Override
    public int insertGpArticles(GpArticles gpArticles)
    {
        gpArticles.setCreateTime(DateUtil.getNowDate());
        gpArticles.setAbstractText(ArticleUtil.getArticleAbstract(gpArticles.getArticleContent()));
        gpArticles.setIndexPicture(ArticleUtil.getOneThumbnail(gpArticles.getArticleContent()));
        int size=gpArticlesMapper.insertGpArticles(gpArticles);
        //System.out.println("返回主键ID："+gpArticles.getId());
        return size;
    }

    /**
      * @Author geekplus
      * @Description //用户投稿文章
      * @Param
      * @Throws
      * @Return {@link }
      */
    @Override
    public int userWriteGpArticles(GpArticles gpArticles) {
        gpArticles.setCreateTime(DateUtil.getNowDate());
        gpArticles.setAbstractText(ArticleUtil.getArticleAbstract(gpArticles.getArticleContent()));
        gpArticles.setIndexPicture(ArticleUtil.getOneThumbnail(gpArticles.getArticleContent()));
        return gpArticlesMapper.userWriteGpArticles(gpArticles);
    }

    /**
     * 修改文章
     *
     * @param gpArticles 文章
     * @return 结果
     */
    @Override
    public int updateGpArticles(GpArticles gpArticles)
    {
        gpArticles.setUpdateTime(DateUtil.getNowDate());
        //首先对修改后的文章主内容进行摘要生成
        String newAbstractText="";
//        //然后对已有的旧文章摘要进行与新生成的摘要互相对比
//        boolean abstractComparer=(gpArticles.getAbstractText()!=null||!"".equals(gpArticles.getAbstractText()))
//                ? TextComparison.calculate(gpArticles.getAbstractText(),newAbstractText)!=0:true;
        //进行双重判断，文章内容是否为空和摘要比较是否为真，为真则修改，否则代表前后文章没有进行更新
        //判null + 判""：判""前要保证字符串不为null，否则调用String方法时会抛出空指针异常
        if(gpArticles.getArticleContent()!=null) {
            if(!"".equals(gpArticles.getArticleContent())) {
                newAbstractText=ArticleUtil.getArticleAbstract(gpArticles.getArticleContent());
                //int articleCompareVal=TextComparison.calculate(gpArticles.getOldArticleContent(),gpArticles.getArticleContent());
                //if(articleCompareVal!=0) {
                    gpArticles.setAbstractText(newAbstractText);
                    //判断封面图是否存在
                    boolean notHasIndexPicture=gpArticles.getIndexPicture()==null||"".equals(gpArticles.getIndexPicture());
                    if(!gpArticles.getArticleContent().contains("geekplus.xyz")||notHasIndexPicture) {
                        gpArticles.setIndexPicture(ArticleUtil.getOneThumbnail(gpArticles.getArticleContent()));
                    }
                //}
            }
        }
        return gpArticlesMapper.updateGpArticles(gpArticles);
    }

    /**
      * @Author geekplus
      * @Description //用于用户端更新浏览量和点赞数
      * @Param
      * @Throws
      * @Return {@link }
      */
    @Override
    public int updateGpArticlesForUser(GpArticles gpArticles) {
        return gpArticlesMapper.updateGpArticlesForUser(gpArticles);
    }

    /**
     * 批量删除文章
     *
     * @param ids 需要删除的文章ID
     * @return 结果
     */
    @Override
    public int deleteGpArticlesByIds(Long[] ids)
    {
        return gpArticlesMapper.deleteGpArticlesByIds(ids);
    }

    /**
     * 删除文章信息
     *
     * @param id 文章ID
     * @return 结果
     */
    @Override
    public int deleteGpArticlesById(Long id)
    {
        return gpArticlesMapper.deleteGpArticlesById(id);
    }

    /**
      * @Author geekplus
      * @Description //根据tagId查询tag的文章列表
      * @Param
      * @Throws
      * @Return {@link }
      */
    @Override
    public List<GpArticles> selectTagDeArticle(String tagName,Long tagId) {
        return gpArticlesMapper.selectTagDeArticle(tagName,tagId);
    }

    /**
      * @Author geekplus
      * @Description //查找当前文章的上一篇
      * @Param
      * @Throws
      * @Return {@link }
      */
    @Override
    public GpArticles selectPrevGpArticle(String pathName, Long articleId) {
        return gpArticlesMapper.selectPrevGpArticle(pathName,articleId);
    }

    /**
      * @Author geekplus
      * @Description //查找当前文章的下一篇
      * @Param
      * @Throws
      * @Return {@link }
      */
    @Override
    public GpArticles selectNextGpArticle(String pathName, Long articleId) {
        return gpArticlesMapper.selectNextGpArticle(pathName,articleId);
    }

    /**
     * @Author geekplus
     * @Description //查询所有文章替换所有Image的src代理服务/api,也可以自定义替换内容replaceMap
     * @Param
     * @Throws
     * @Return {@link }
     */
    @Override
    public Map<String,Object> replaceGpArticleImgSrc(Map<String,Object> replaceMap) {
        Map<String,Object> msgMap = new HashMap<>();
        int allArticles=0;
        int update = 0;
        if(replaceMap!=null && !replaceMap.isEmpty()){
            String replacedContent = null;
            if (RegexUtil.isHasReplaceStr(replaceMap.get("replaceContent").toString(), replaceMap.get("replaceStr").toString())) {
                replacedContent = RegexUtil.getReplaceRegexContent(replaceMap.get("replaceContent").toString(), replaceMap.get("replaceStr").toString(), replaceMap.get("replacement").toString());
            }
            msgMap.put("updateMsg","成功替换，返回替换后的内容！");
            msgMap.put("replacedContent", replacedContent);
        }else {
            List<GpArticles> articles = gpArticlesMapper.selectGpArticlesList(null);
            if (articles.size() > 0) {
                for (GpArticles article : articles) {
                    if (RegexUtil.isHasUrl1(article.getIndexPicture(), "https://www.geekplus.xyz")) {
                        allArticles += 1;
                        //replaceAll("(.)\\1+", "$1");//替换重复内容
                        article.setIndexPicture(article.getIndexPicture().replaceAll("https://www.geekplus.xyz/prod-api", "https://maike.geekplus.xyz"));
                        //article.setArticleContent(RegexUtil.getReplaceRegexContent(article.getArticleContent(), Constant.RESOURCE_PREFIX, Constant.RESOURCE_PREFIX));
                        if (gpArticlesMapper.updateGpArticles(article) > 0) {
                            update += 1;
                        }
                    }
                }
            }
            msgMap.put("updateMsg","一共有"+allArticles+"篇文章，成功修改"+update+"篇");
        }
        return msgMap;
    }
}
