package com.geekplus.webapp.function.entity;

import com.geekplus.common.annotation.Excel;
import com.geekplus.common.domain.BaseEntity;
import com.geekplus.common.util.ThumbnailUtil;
import com.geekplus.common.util.encrypt.SignatureUtil;
import com.geekplus.common.util.html.ArticleUtil;
import com.geekplus.common.util.image.ThumbnailUtils;
import com.geekplus.common.util.string.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 文章对象 gp_articles
 *
 * @author 佚名
 * @date 2023-03-12
 */
public class GpArticles extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文章表ID */
    private Long id;

    /** 作者名称，用户名 */
    @Excel(name = "作者名称，用户名")
    private String authorName;

    /** 作者ID，用户ID */
    @Excel(name = "作者ID，用户ID")
    private Long authorId;

    /** 文章标题 */
    @Excel(name = "文章标题")
    private String articleTitle;

    /** 文章内容 */
    @Excel(name = "文章内容")
    private String articleContent;

    /** 文章类型 */
    @Excel(name = "文章类型")
    private Long articleCategory;

    /** 文章点赞数量 */
    @Excel(name = "文章点赞数量")
    private Long likeCount;

    /** 文章浏览数量 */
    @Excel(name = "文章浏览数量")
    private Long viewCount;

    /**
     * 是否展示文章
     */
    private String isDisplay;

    /**
     * 文章阅览权限(PUBLIC, AUTHENTICATED, PRIVATE)
     */
    private String viewPerms;

    /**
     * 文章状态(草稿0，正式发布1)
     */
    private Integer status;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 文章索引缩略图
     */
    private String indexPicture;

    /**
     * 文章摘要
     */
    private String abstractText;

    /**
     * 缩略图
     */
    private String thumbnailImg;

    /**
     * 路径URL
     */
    private String pathName;

    private List<GpArticleTags> tags;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public String getViewPerms() {
        return viewPerms;
    }

    public void setViewPerms(String viewPerms) {
        this.viewPerms = viewPerms;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public void setAuthorName(String authorName)
    {
        this.authorName = authorName;
    }

    public String getAuthorName()
    {
        return authorName;
    }
    public void setAuthorId(Long authorId)
    {
        this.authorId = authorId;
    }

    public Long getAuthorId()
    {
        return authorId;
    }
    public void setArticleTitle(String articleTitle)
    {
        this.articleTitle = articleTitle;
    }

    public String getArticleTitle()
    {
        return articleTitle;
    }
    public void setArticleContent(String articleContent)
    {
        //这里分别动态处理提取文章内容的首张缩略图和摘要内容
        //this.abstractText = ArticleUtil.getArticleAbstract(articleContent);
        //setIndexPicture(articleContent);
        this.articleContent = articleContent;
    }

    public String getArticleContent()
    {
        return articleContent;
    }
    public void setArticleCategory(Long articleCategory)
    {
        this.articleCategory = articleCategory;
    }

    public Long getArticleCategory()
    {
        return articleCategory;
    }
    public void setLikeCount(Long likeCount)
    {
        this.likeCount = likeCount;
    }

    public Long getLikeCount()
    {
        return likeCount;
    }
    public void setViewCount(Long viewCount)
    {
        this.viewCount = viewCount;
    }

    public Long getViewCount()
    {
        return viewCount;
    }

    public String getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(String isDisplay) {
        this.isDisplay = isDisplay;
    }

    public String getIndexPicture() {
        return indexPicture;
    }

    public void setIndexPicture(String indexPicture) {
//        String base64Thumbnail="";
//        Set<String> fp = ArticleUtil.getImgStr(indexPicture);
//        String[] fps = fp.toArray(new String[0]);
//        if (!fp.isEmpty()) {
//            Random random=new Random();
//            int imgIndex=random.nextInt(fps.length);
//            //System.out.println("文章第一张图片image："+fp.iterator().next());//获取第一张
//            base64Thumbnail = ThumbnailUtils.getThumbnailBase64Image(fps[imgIndex]);
//        }
//        this.indexPicture = base64Thumbnail;
        this.indexPicture=indexPicture;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public String getThumbnailImg() {
        return thumbnailImg;
    }

    public void setThumbnailImg(String thumbnailImg) {
        this.thumbnailImg = thumbnailImg;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public List<GpArticleTags> getTags() {
        return tags;
    }

    public void setTags(List<GpArticleTags> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("authorName", getAuthorName())
            .append("authorId", getAuthorId())
            .append("articleTitle", getArticleTitle())
            .append("articleContent", getArticleContent())
            .append("articleCategory", getArticleCategory())
            .append("likeCount", getLikeCount())
            .append("viewCount", getViewCount())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
