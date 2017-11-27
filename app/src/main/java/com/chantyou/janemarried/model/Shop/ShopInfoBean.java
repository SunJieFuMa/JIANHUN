package com.chantyou.janemarried.model.Shop;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/5.
 */
public class ShopInfoBean implements Serializable{


    /**
     * status : 1
     * data : [{"address":"","affiche":"","alipay":"","alipayRealName":"","areaPoint":"","backGroundUrl":"","bisDescs":"","city":"","collectCount":0,"county":"","createTim":123,"createUser":"","delFlag":1,"descs":"","egCount":0,"fversion":1,"geocode":"","id":1,"imageUrl":"","isBill":0,"isLock":1,"isNewOpen":0,"isSpread":0,"isWork":1,"label":"","lat":0,"lng":0,"name":"dasf","note":"","orderCounts":0,"orgId":0,"password":"123","phone":"123","productCount":0,"province":"","score":0,"shopOwner":"sdf","shopType":0,"tenpay":"","tenpayopenId":"","updateUser":"","username":"123","videoUrl":""}]
     * msg : ????????
     */

    private String status;
    private String msg;
    /**
     * address :
     * affiche :
     * alipay :
     * alipayRealName :
     * areaPoint :
     * backGroundUrl :
     * bisDescs :
     * city :
     * collectCount : 0
     * county :
     * createTim : 123
     * createUser :
     * delFlag : 1
     * descs :
     * egCount : 0
     * fversion : 1
     * geocode :
     * id : 1
     * imageUrl :
     * isBill : 0
     * isLock : 1
     * isNewOpen : 0
     * isSpread : 0
     * isWork : 1
     * label :
     * lat : 0
     * lng : 0
     * name : dasf
     * note :
     * orderCounts : 0
     * orgId : 0
     * password : 123
     * phone : 123
     * productCount : 0
     * province :
     * score : 0
     * shopOwner : sdf
     * shopType : 0
     * tenpay :
     * tenpayopenId :
     * updateUser :
     * username : 123
     * videoUrl :
     */

    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "isCollect=" + isCollect +
                    ", address='" + address + '\'' +
                    ", affiche='" + affiche + '\'' +
                    ", alipay='" + alipay + '\'' +
                    ", alipayRealName='" + alipayRealName + '\'' +
                    ", areaPoint='" + areaPoint + '\'' +
                    ", backGroundUrl='" + backGroundUrl + '\'' +
                    ", bisDescs='" + bisDescs + '\'' +
                    ", city='" + city + '\'' +
                    ", collectCount=" + collectCount +
                    ", county='" + county + '\'' +
                    ", createTim=" + createTim +
                    ", createUser='" + createUser + '\'' +
                    ", delFlag=" + delFlag +
                    ", descs='" + descs + '\'' +
                    ", egCount=" + egCount +
                    ", fversion=" + fversion +
                    ", geocode='" + geocode + '\'' +
                    ", id=" + id +
                    ", imageUrl='" + imageUrl + '\'' +
                    ", isBill=" + isBill +
                    ", isLock=" + isLock +
                    ", isNewOpen=" + isNewOpen +
                    ", isSpread=" + isSpread +
                    ", isWork=" + isWork +
                    ", label='" + label + '\'' +
                    ", lat=" + lat +
                    ", lng=" + lng +
                    ", name='" + name + '\'' +
                    ", note='" + note + '\'' +
                    ", orderCounts=" + orderCounts +
                    ", orgId=" + orgId +
                    ", password='" + password + '\'' +
                    ", phone='" + phone + '\'' +
                    ", productCount=" + productCount +
                    ", province='" + province + '\'' +
                    ", score=" + score +
                    ", shopOwner='" + shopOwner + '\'' +
                    ", shopType=" + shopType +
                    ", tenpay='" + tenpay + '\'' +
                    ", tenpayopenId='" + tenpayopenId + '\'' +
                    ", updateUser='" + updateUser + '\'' +
                    ", username='" + username + '\'' +
                    ", videoUrl='" + videoUrl + '\'' +
                    ", judgeCount=" + judgeCount +
                    '}';
        }

        private int isCollect;
        private String address;
        private String affiche;
        private String alipay;
        private String alipayRealName;
        private String areaPoint;
        private String backGroundUrl;
        private String bisDescs;
        private String city;
        private int collectCount;
        private String county;
        private int createTim;
        private String createUser;
        private int delFlag;
        private String descs;
        private int egCount;
        private int fversion;
        private String geocode;
        private int id;
        private String imageUrl;
        private int isBill;
        private int isLock;
        private int isNewOpen;
        private int isSpread;
        private int isWork;
        private String label;
        private int lat;
        private int lng;
        private String name;
        private String note;
        private int orderCounts;
        private int orgId;
        private String password;
        private String phone;
        private int productCount;
        private String province;
        private int score;
        private String shopOwner;
        private int shopType;
        private String tenpay;
        private String tenpayopenId;
        private String updateUser;
        private String username;
        private String videoUrl;
        private int judgeCount;//评价数量

        public int getJudgeCount() {
            return judgeCount;
        }

        public void setJudgeCount(int judgeCount) {
            this.judgeCount = judgeCount;
        }


        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAffiche() {
            return affiche;
        }

        public void setAffiche(String affiche) {
            this.affiche = affiche;
        }

        public String getAlipay() {
            return alipay;
        }

        public void setAlipay(String alipay) {
            this.alipay = alipay;
        }

        public String getAlipayRealName() {
            return alipayRealName;
        }

        public void setAlipayRealName(String alipayRealName) {
            this.alipayRealName = alipayRealName;
        }

        public String getAreaPoint() {
            return areaPoint;
        }

        public void setAreaPoint(String areaPoint) {
            this.areaPoint = areaPoint;
        }

        public String getBackGroundUrl() {
            return backGroundUrl;
        }

        public void setBackGroundUrl(String backGroundUrl) {
            this.backGroundUrl = backGroundUrl;
        }

        public String getBisDescs() {
            return bisDescs;
        }

        public void setBisDescs(String bisDescs) {
            this.bisDescs = bisDescs;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getCollectCount() {
            return collectCount;
        }

        public void setCollectCount(int collectCount) {
            this.collectCount = collectCount;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

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

        public int getEgCount() {
            return egCount;
        }

        public void setEgCount(int egCount) {
            this.egCount = egCount;
        }

        public int getFversion() {
            return fversion;
        }

        public void setFversion(int fversion) {
            this.fversion = fversion;
        }

        public String getGeocode() {
            return geocode;
        }

        public void setGeocode(String geocode) {
            this.geocode = geocode;
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

        public int getIsBill() {
            return isBill;
        }

        public void setIsBill(int isBill) {
            this.isBill = isBill;
        }

        public int getIsLock() {
            return isLock;
        }

        public void setIsLock(int isLock) {
            this.isLock = isLock;
        }

        public int getIsNewOpen() {
            return isNewOpen;
        }

        public void setIsNewOpen(int isNewOpen) {
            this.isNewOpen = isNewOpen;
        }

        public int getIsSpread() {
            return isSpread;
        }

        public void setIsSpread(int isSpread) {
            this.isSpread = isSpread;
        }

        public int getIsWork() {
            return isWork;
        }

        public void setIsWork(int isWork) {
            this.isWork = isWork;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int getLat() {
            return lat;
        }

        public void setLat(int lat) {
            this.lat = lat;
        }

        public int getLng() {
            return lng;
        }

        public void setLng(int lng) {
            this.lng = lng;
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

        public int getOrderCounts() {
            return orderCounts;
        }

        public void setOrderCounts(int orderCounts) {
            this.orderCounts = orderCounts;
        }

        public int getOrgId() {
            return orgId;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getProductCount() {
            return productCount;
        }

        public void setProductCount(int productCount) {
            this.productCount = productCount;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getShopOwner() {
            return shopOwner;
        }

        public void setShopOwner(String shopOwner) {
            this.shopOwner = shopOwner;
        }

        public int getShopType() {
            return shopType;
        }

        public void setShopType(int shopType) {
            this.shopType = shopType;
        }

        public String getTenpay() {
            return tenpay;
        }

        public void setTenpay(String tenpay) {
            this.tenpay = tenpay;
        }

        public String getTenpayopenId() {
            return tenpayopenId;
        }

        public void setTenpayopenId(String tenpayopenId) {
            this.tenpayopenId = tenpayopenId;
        }

        public String getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public int getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(int isCollect) {
            this.isCollect = isCollect;
        }
    }
}

