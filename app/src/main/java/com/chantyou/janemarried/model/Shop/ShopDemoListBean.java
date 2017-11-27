package com.chantyou.janemarried.model.Shop;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/5.
 */
public class ShopDemoListBean implements Serializable{


    /**
     * status : 1
     * data : [{"createTim":1472994863689,"createUser":"1","delFlag":0,"descs":"2","fversion":2,"hotelName":"wanasd","id":1,"imageUrl1":"http://101.201.209.200/upload/topic4236152259063931658392256656457923.png","imageUrl2":"http://101.201.209.200/upload/topic4236152259063931658392256656457923.png","imageUrl3":"http://101.201.209.200/upload/topic4236152259063931658392256656457923.png","imageUrl4":"http://101.201.209.200/upload/topic4236152259063931658392256656457923.png","imageUrl5":"http://101.201.209.200/upload/topic4236152259063931658392256656457923.png","imageUrl6":"http://101.201.209.200/upload/topic4236152259063931658392256656457923.png","label":"asd","note":"sd","price":3000,"shopId":1,"shopProductId":1,"updateUser":"sd "},{"createTim":1472994863689,"createUser":"1","delFlag":0,"descs":"2","fversion":2,"hotelName":"dsdsfdss","id":2,"imageUrl1":"http://101.201.209.200/upload/topic4236152259063931658392256656457923.png","imageUrl2":"http://101.201.209.200/upload/topic4236152259063931658392256656457923.png","imageUrl3":"http://101.201.209.200/upload/topic4236152259063931658392256656457923.png","imageUrl4":"http://101.201.209.200/upload/topic4236152259063931658392256656457923.png","imageUrl5":"http://101.201.209.200/upload/topic4236152259063931658392256656457923.png","imageUrl6":"http://101.201.209.200/upload/topic4236152259063931658392256656457923.png","label":"asd","note":"sd","price":3000,"shopId":1,"shopProductId":1,"updateUser":"sd "},{"createTim":1472994863689,"createUser":"1","delFlag":0,"descs":"2","fversion":2,"hotelName":"王宇豪庭","id":3,"imageUrl1":"http://101.201.209.200/upload/topic4236152259063931658392256656457923.png","imageUrl2":"http://101.201.209.200/upload/topic4236152259063931658392256656457923.png","imageUrl3":"http://101.201.209.200/upload/topic4236152259063931658392256656457923.png","imageUrl4":"http://101.201.209.200/upload/topic4236152259063931658392256656457923.png","imageUrl5":"http://101.201.209.200/upload/topic4236152259063931658392256656457923.png","imageUrl6":"http://101.201.209.200/upload/topic4236152259063931658392256656457923.png","label":"asd","note":"sd","price":3000,"shopId":1,"shopProductId":1,"updateUser":"sd "}]
     * size : 3
     */

    private String status;
    private int size;
    /**
     * createTim : 1472994863689
     * createUser : 1
     * delFlag : 0
     * descs : 2
     * fversion : 2
     * hotelName : wanasd
     * id : 1
     * imageUrl1 : http://101.201.209.200/upload/topic4236152259063931658392256656457923.png
     * imageUrl2 : http://101.201.209.200/upload/topic4236152259063931658392256656457923.png
     * imageUrl3 : http://101.201.209.200/upload/topic4236152259063931658392256656457923.png
     * imageUrl4 : http://101.201.209.200/upload/topic4236152259063931658392256656457923.png
     * imageUrl5 : http://101.201.209.200/upload/topic4236152259063931658392256656457923.png
     * imageUrl6 : http://101.201.209.200/upload/topic4236152259063931658392256656457923.png
     * label : asd
     * note : sd
     * price : 3000
     * shopId : 1
     * shopProductId : 1
     * updateUser : sd
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
        private String productName;
        private long createTim;
        private String createUser;
        private int delFlag;
        private String descs;
        private int fversion;
        private String hotel;
        private int id;
        private String imageUrl1;
        private String imageUrl2;
        private String imageUrl3;
        private String imageUrl4;
        private String imageUrl5;
        private String imageUrl6;
        private String label;
        private String note;
        private String price;
        private int shopId;
        private int shopProductId;
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

        public String getImageUrl1() {
            return imageUrl1;
        }

        public void setImageUrl1(String imageUrl1) {
            this.imageUrl1 = imageUrl1;
        }

        public String getImageUrl2() {
            return imageUrl2;
        }

        public void setImageUrl2(String imageUrl2) {
            this.imageUrl2 = imageUrl2;
        }

        public String getImageUrl3() {
            return imageUrl3;
        }

        public void setImageUrl3(String imageUrl3) {
            this.imageUrl3 = imageUrl3;
        }

        public String getImageUrl4() {
            return imageUrl4;
        }

        public void setImageUrl4(String imageUrl4) {
            this.imageUrl4 = imageUrl4;
        }

        public String getImageUrl5() {
            return imageUrl5;
        }

        public void setImageUrl5(String imageUrl5) {
            this.imageUrl5 = imageUrl5;
        }

        public String getImageUrl6() {
            return imageUrl6;
        }

        public void setImageUrl6(String imageUrl6) {
            this.imageUrl6 = imageUrl6;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getHotel() {
            return hotel;
        }

        public void setHotel(String hotel) {
            this.hotel = hotel;
        }
    }
}

