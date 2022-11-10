package com.serialgroup.serial.model;

import java.util.Date;

public class ImageMeta {
    private Long id;

    private String groupId;

    private String fileHash;

    private Date gmtCreate;

    private Date gmtModified;

    public ImageMeta(Long id, String groupId, String fileHash, Date gmtCreate, Date gmtModified) {
        this.id = id;
        this.groupId = groupId;
        this.fileHash = fileHash;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
    }

    public ImageMeta() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash == null ? null : fileHash.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}