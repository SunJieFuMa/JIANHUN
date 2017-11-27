package com.chantyou.janemarried.model;

import java.io.Serializable;


public class CheckVersionBean implements Serializable {

    /**
     * status : 1
     * isUpdate : 1
     * updateUrl :
     */

    private int status;
    private int isUpdate;
    private String updateUrl;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }
}
