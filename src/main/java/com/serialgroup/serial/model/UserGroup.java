package com.serialgroup.serial.model;

public class UserGroup {
    private Long id;

    private String name;

    private String nickName;

    private String sourceId;

    public UserGroup(Long id, String name, String nickName, String sourceId) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.sourceId = sourceId;
    }

    public UserGroup() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId == null ? null : sourceId.trim();
    }
}