package com.chantyou.janemarried.model.Shop;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Triones on 2016/9/9 22:18.
 */
public class ShopDemoInfoBean implements Serializable {


    private String status;
    private String info;


    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private long createTim;
        private String createUser;
        private int delFlag;
        private String descs;
        private int fversion;
        private int id;
        private String imageUrl;
        private int infoType;
        private String note;
        private int shopCaseId;
        private int shopId;
        private String updateUser;

        public long getCreateTim() {
            return createTim;
        }

        public void setCreateTim(long createTim) {
            this.createTim = createTim;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public String getDescs() {
            return descs;
        }

        public void setDescs(String descs) {
            this.descs = descs;
        }

        public int getFversion() {
            return fversion;
        }

        public void setFversion(int fversion) {
            this.fversion = fversion;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public int getInfoType() {
            return infoType;
        }

        public void setInfoType(int infoType) {
            this.infoType = infoType;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public int getShopCaseId() {
            return shopCaseId;
        }

        public void setShopCaseId(int shopCaseId) {
            this.shopCaseId = shopCaseId;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }
    }
}
