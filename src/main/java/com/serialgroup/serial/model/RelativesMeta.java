package com.serialgroup.serial.model;

import java.util.Date;

public class RelativesMeta {
    private Long id;

    private String groupId;

    private String name;

    private String fileHash;

    private Date gmtCreate;

    private Date gmtModified;

    private Boolean isCompleted;

    private Integer priority;

    private Integer anonymousId;

    public RelativesMeta(Long id, String groupId, String name, String fileHash, Date gmtCreate, Date gmtModified, Boolean isCompleted, Integer priority, Integer anonymousId) {
        this.id = id;
        this.groupId = groupId;
        this.name = name;
        this.fileHash = fileHash;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.isCompleted = isCompleted;
        this.priority = priority;
        this.anonymousId = anonymousId;
    }

    public RelativesMeta() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getAnonymousId() {
        return anonymousId;
    }

    public void setAnonymousId(Integer anonymousId) {
        this.anonymousId = anonymousId;
    }
}