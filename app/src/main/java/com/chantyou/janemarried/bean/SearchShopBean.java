package com.chantyou.janemarried.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */
public class SearchShopBean {

    /**
     * status : 1
     * data : [{"collectCount":87,"phone":"15853144007","judgeCount":0,"bisDescs":"","imageUrl":"http://jianhun-10070947.image.myqcloud.com/f845189c-f9da-4ee3-8322-19d90be2509b","videoUrl":"","affiche":"","isSpread":0,"backGroundUrl":"","label":"婚礼策划","city":"济南","id":132440,"username":"15853144007","shopType":3,"productCount":4,"shopOwner":"济南光韵文化传媒有限公司","address":"万达凯悦酒店5楼","county":"市中区","shopId":132440,"name":"新娘日记精致婚礼策划","province":"山东省","shopLevel":2,"egCount":31,"descs":"这是一群从韩国留学设计归来的团队，为了把设计理念融入到婚礼当中而不断努力。"}]
     * msg : 查询所有商家成功
     * size : 1
     */
    private String status;
    private List<DataEntity> data;
    private String msg;
    private int size;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public int getSize() {
        return size;
    }

    public static class DataEntity {
        /**
         * collectCount : 87
         * phone : 15853144007
         * judgeCount : 0
         * bisDescs :
         * imageUrl : http://jianhun-10070947.image.myqcloud.com/f845189c-f9da-4ee3-8322-19d90be2509b
         * videoUrl :
         * affiche :
         * isSpread : 0
         * backGroundUrl :
         * label : 婚礼策划
         * city : 济南
         * id : 132440
         * username : 15853144007
         * shopType : 3
         * productCount : 4
         * shopOwner : 济南光韵文化传媒有限公司
         * address : 万达凯悦酒店5楼
         * county : 市中区
         * shopId : 132440
         * name : 新娘日记精致婚礼策划
         * province : 山东省
         * shopLevel : 2
         * egCount : 31
         * descs : 这是一群从韩国留学设计归来的团队，为了把设计理念融入到婚礼当中而不断努力。
         */
        private int collectCount;
        private String phone;
        private int judgeCount;
        private String bisDescs;
        private String imageUrl;
        private String videoUrl;
        private String affiche;
        private int isSpread;
        private String backGroundUrl;
        private String label;
        private String city;
        private int id;
        private String username;
        private int shopType;
        private int productCount;
        private String shopOwner;
        private String address;
        private String county;
        private int shopId;
        private String name;
        private String province;
        private int shopLevel;
        private int egCount;
        private String descs;

        public void setCollectCount(int collectCount) {
            this.collectCount = collectCount;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setJudgeCount(int judgeCount) {
            this.judgeCount = judgeCount;
        }

        public void setBisDescs(String bisDescs) {
            this.bisDescs = bisDescs;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public void setAffiche(String affiche) {
            this.affiche = affiche;
        }

        public void setIsSpread(int isSpread) {
            this.isSpread = isSpread;
        }

        public void setBackGroundUrl(String backGroundUrl) {
            this.backGroundUrl = backGroundUrl;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setShopType(int shopType) {
            this.shopType = shopType;
        }

        public void setProductCount(int productCount) {
            this.productCount = productCount;
        }

        public void setShopOwner(String shopOwner) {
            this.shopOwner = shopOwner;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public void setShopLevel(int shopLevel) {
            this.shopLevel = shopLevel;
        }

        public void setEgCount(int egCount) {
            this.egCount = egCount;
        }

        public void setDescs(String descs) {
            this.descs = descs;
        }

        public int getCollectCount() {
            return collectCount;
        }

        public String getPhone() {
            return phone;
        }

        public int getJudgeCount() {
            return judgeCount;
        }

        public String getBisDescs() {
            return bisDescs;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public String getAffiche() {
            return affiche;
        }

        public int getIsSpread() {
            return isSpread;
        }

        public String getBackGroundUrl() {
            return backGroundUrl;
        }

        public String getLabel() {
            return label;
        }

        public String getCity() {
            return city;
        }

        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public int getShopType() {
            return shopType;
        }

        public int getProductCount() {
            return productCount;
        }

        public String getShopOwner() {
            return shopOwner;
        }

        public String getAddress() {
            return address;
        }

        public String getCounty() {
            return county;
        }

        public int getShopId() {
            return shopId;
        }

        public String getName() {
            return name;
        }

        public String getProvince() {
            return province;
        }

        public int getShopLevel() {
            return shopLevel;
        }

        public int getEgCount() {
            return egCount;
        }

        public String getDescs() {
            return descs;
        }
    }
}
