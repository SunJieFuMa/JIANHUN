package com.chantyou.janemarried.model.Shop;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/5.
 */
public class ShopPackageInfoBean implements Serializable{


    /**
     * status : 1
     * data : [{"createTim":1,"createUser":"啥时说的","delFlag":1,"descs":"212","fversion":1,"id":1,"imageUrl":"http://101.201.209.200/upload/topic45375523876087607799122569468358351.png","infoType":1,"note":"","shopId":1,"shopProductId":1,"updateUser":"1"}]
     * size : 1
     */

    private String status;
    private int size;
    /**
     * createTim : 1
     * createUser : 啥时说的
     * delFlag : 1
     * descs : 212
     * fversion : 1
     * id : 1
     * imageUrl : http://101.201.209.200/upload/topic45375523876087607799122569468358351.png
     * infoType : 1
     * note :
     * shopId : 1
     * shopProductId : 1
     * updateUser : 1
     */

    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int createTim;
        private String createUser;
        private int delFlag;
        private String descs;
        private int fversion;
        private int id;
        private String imageUrl;
        private int infoType;
        private String note;
        private int shopId;
        private int shopProductId;
        private String updateUser;

        public int getCreateTim() {
            return createTim;
        }

        public void setCreateTim(int createTim) {
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

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public int getShopProductId() {
            return shopProductId;
        }

        public void setShopProductId(int shopProductId) {
            this.shopProductId = shopProductId;
        }

        public String getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }
    }
}

