package com.geekplus.webapp.function.entity;

import com.geekplus.common.annotation.Excel;
import com.geekplus.common.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 文章标签映射对象 gp_article_map_tag
 *
 * @author 佚名
 * @date 2023-03-12
 */
public class GpArticleMapTag extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文章ID */
    @Excel(name = "文章ID")
    private Long articleId;

    /** 文章标签ID */
    @Excel(name = "文章标签ID")
    private Long articleTag;

    public void setArticleId(Long articleId)
    {
        this.articleId = articleId;
    }

    public Long getArticleId()
    {
        return articleId;
    }
    public void setArticleTag(Long articleTag)
    {
        this.articleTag = articleTag;
    }

    public Long getArticleTag()
    {
        return articleTag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("articleId", getArticleId())
            .append("articleTag", getArticleTag())
            .toString();
    }
}
