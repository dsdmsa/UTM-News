package dsdmsa.utmnews.domain.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("date_gmt")
    @Expose
    public String dateGmt;
    @SerializedName("guid")
    @Expose
    public Guid guid;
    @SerializedName("modified")
    @Expose
    public String modified;
    @SerializedName("modified_gmt")
    @Expose
    public String modifiedGmt;
    @SerializedName("slug")
    @Expose
    public String slug;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("link")
    @Expose
    public String link;
    @SerializedName("title")
    @Expose
    public Title title;
    @SerializedName("content")
    @Expose
    public Content content;
    @SerializedName("excerpt")
    @Expose
    public Excerpt excerpt;
    @SerializedName("author")
    @Expose
    public Integer author;
    @SerializedName("featured_media")
    @Expose
    public Integer featuredMedia;
    @SerializedName("comment_status")
    @Expose
    public String commentStatus;
    @SerializedName("ping_status")
    @Expose
    public String pingStatus;
    @SerializedName("sticky")
    @Expose
    public Boolean sticky;
    @SerializedName("template")
    @Expose
    public String template;
    @SerializedName("format")
    @Expose
    public String format;
    @SerializedName("categories")
    @Expose
    public List<Integer> categories = null;
//    @SerializedName("tags")
//    @Expose
//    public List<Tag> tags = null;


    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateGmt() {
        return dateGmt;
    }

    public void setDateGmt(String dateGmt) {
        this.dateGmt = dateGmt;
    }

    public Guid getGuid() {
        return guid;
    }

    public void setGuid(Guid guid) {
        this.guid = guid;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getModifiedGmt() {
        return modifiedGmt;
    }

    public void setModifiedGmt(String modifiedGmt) {
        this.modifiedGmt = modifiedGmt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Excerpt getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(Excerpt excerpt) {
        this.excerpt = excerpt;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public Integer getFeaturedMedia() {
        return featuredMedia;
    }

    public void setFeaturedMedia(Integer featuredMedia) {
        this.featuredMedia = featuredMedia;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getPingStatus() {
        return pingStatus;
    }

    public void setPingStatus(String pingStatus) {
        this.pingStatus = pingStatus;
    }

    public Boolean getSticky() {
        return sticky;
    }

    public void setSticky(Boolean sticky) {
        this.sticky = sticky;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getCategory() {
        return getCategories().get(0);
    }
}