package com.chantyou.janemarried.bean;

/**
 * Created by Administrator on 2017/2/7.
 */
public class UserSelectMusic {

    /**
     * error : 当前音乐已经被删除
     * status : 0
     * type : comError
     */
    private String error;
    private int status;
    private String type;

    public void setError(String error) {
        this.error = error;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }
}
