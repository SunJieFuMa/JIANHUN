package com.chantyou.janemarried.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/7.
 */
public class UserGetMusic {

    /**
     * status : 1
     * data : []
     * size : 0
     */
    private int status;
    private List<?> data;
    private int size;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStatus() {
        return status;
    }

    public List<?> getData() {
        return data;
    }

    public int getSize() {
        return size;
    }
}
