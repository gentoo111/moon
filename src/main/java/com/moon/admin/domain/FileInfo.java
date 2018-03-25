package com.moon.admin.domain;


/**
 * Created by szz on 2018/3/23 23:37.
 */
public class FileInfo extends BaseEntity<String>{
    private static final long serialVersionUID = -3436371123262041711L;
    private String contentType;
    private Long size;
    private String path;
    private String url;
    private String type;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
