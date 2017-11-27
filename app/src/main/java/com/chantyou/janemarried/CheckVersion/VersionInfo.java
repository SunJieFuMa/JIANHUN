package com.chantyou.janemarried.CheckVersion;

/**
 * Created by zhangYB on 2016/6/20.
 */
public class VersionInfo {


    /**
     * status : 1
     * isUpdate : 1
     * updateUrl :
     */

    private int status;
    private int isUpdate;
    private String updateUrl;

    private String info;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "status:" + status + ",isUpdate:" + isUpdate + ",updateUrl:" + updateUrl + ",info:" + info;
    }
}
