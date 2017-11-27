package com.chantyou.janemarried.model.Shop;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/5.
 */
public class ShopPackageListBean implements Serializable{


    /**
     * status : 1
     * data : [{"createTim":1472994863689,"createUser":"1","delFlag":0,"descs":"sdfsdfsdff","fversion":0,"hotelName":12,"id":1,"imageUrl":"http://101.201.209.200/upload/topic45375523876087607799122569468358351.png","label":"水电费水电费","name":"12","note":"2","price":2,"shopId":1,"updateUser":""}]
     * size : 1
     */

    private String status;
    private int size;
    /**
     * createTim : 1472994863689
     * createUser : 1
     * delFlag : 0
     * descs : sdfsdfsdff
     * fversion : 0
     * hotelName : 12
     * id : 1
     * imageUrl : http://101.201.209.200/upload/topic45375523876087607799122569468358351.png
     * label : 水电费水电费
     * name : 12
     * note : 2
     * price : 2
     * shopId : 1
     * updateUser :
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
        private long createTim;
        private String createUser;
        private int delFlag;
        private String descs;
        private int fversion;
        private String hotelName;
        private int id;
        private String imageUrl;
        private String label;
        private String name;
        private String note;
        private int price;
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

        public String getHotelName() {
            return hotelName;
        }

        public void setHotelName(String hotelName) {
            this.hotelName = hotelName;
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

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
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

