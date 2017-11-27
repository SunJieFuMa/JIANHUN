package com.chantyou.janemarried.model.Shop;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/5.
 */
public class ShopFavoriteListBean implements Serializable {


    /**
     * status : 1
     * data : [{"address":"山东省济南市历下区","affiche":"店家公告","backGroundUrl":"http://101.201.209.200/upload/topic43012707832917313464056611057211172.png","bisDescs":"庆典服务|婚礼定制|婚纱摄影|设备租赁|婚礼车队|婚宴喜宴","city":"济南","collectCount":3,"county":"历下区","descs":"定制婚礼指的是根据您对婚礼的期望与梦想，对花材的偏好与忌讳，以您的婚礼预算与酒店硬件设设施为约束条件，结合您的恋爱经历、职业、气质、性格、爱好、恋爱中有意义的细节等，设计婚礼风格和仪式流程，体现个性化与人性。","egCount":8,"id":1,"imageUrl":"http://101.201.209.200/upload/topic43012707832917313464056611057211172.png","isSpread":1,"judgeCount":0,"label":"婚庆专家","name":"安庆婚典","phone":"18658986551","productCount":6,"province":"","shopLevel":1,"shopOwner":"酰胺","shopType":1,"username":"null","videoUrl":"http://101.201.209.200/upload/topic43012707832917313464056611057211172.png","shopId":1},{"address":"","affiche":"店家公告","backGroundUrl":"http://101.201.209.200/upload/topic43012707832917313464056611057211172.png","bisDescs":"庆典服务|婚礼定制|婚纱摄影|设备租赁|婚礼车队|婚宴喜宴","city":"济南","collectCount":5,"county":"历下区","descs":"定制婚礼指的是根据您对婚礼的期望与梦想，对花材的偏好与忌讳，以您的婚礼预算与酒店硬件设设施为约束条件，结合您的恋爱经历、职业、气质、性格、爱好、恋爱中有意义的细节等，设计婚礼风格和仪式流程，体现个性化与人性。","egCount":8,"id":2,"imageUrl":"http://101.201.209.200/upload/topic43012707832917313464056611057211172.png","isSpread":1,"judgeCount":0,"label":"婚庆专家","name":"华丽婚典","phone":"18658986551","productCount":8,"province":"","shopLevel":2,"shopOwner":"华丽婚典","shopType":2,"username":"","videoUrl":"http://101.201.209.200/upload/topic43012707832917313464056611057211172.png","shopId":2},{"address":"","affiche":"店家公告","backGroundUrl":"","bisDescs":"庆典服务|婚礼定制|婚纱摄影|设备租赁|婚礼车队|婚宴喜宴","city":"济南","collectCount":5,"county":"","descs":"定制婚礼指的是根据您对婚礼的期望与梦想，对花材的偏好与忌讳，以您的婚礼预算与酒店硬件设设施为约束条件，结合您的恋爱经历、职业、气质、性格、爱好、恋爱中有意义的细节等，设计婚礼风格和仪式流程，体现个性化与人性。","egCount":10,"id":3,"imageUrl":"","isSpread":1,"judgeCount":0,"label":"婚庆专家","name":"喜悦婚典","phone":"18658986551","productCount":7,"province":"","shopLevel":3,"shopOwner":"喜悦","shopType":1,"username":"null","videoUrl":"http://101.201.209.200/http://jinyou.oss-cn-qingdao.aliyuncs.com/LG/lg.mp4","shopId":3},{"address":"","affiche":"店家公告","backGroundUrl":"","bisDescs":"庆典服务|婚礼定制|婚纱摄影|设备租赁|婚礼车队|婚宴喜宴","city":"济南","collectCount":7,"county":"","descs":"定制婚礼指的是根据您对婚礼的期望与梦想，对花材的偏好与忌讳，以您的婚礼预算与酒店硬件设设施为约束条件，结合您的恋爱经历、职业、气质、性格、爱好、恋爱中有意义的细节等，设计婚礼风格和仪式流程，体现个性化与人性。","egCount":10,"id":4,"imageUrl":"","isSpread":1,"judgeCount":0,"label":"婚庆专家","name":"晚安婚典","phone":"18658986551","productCount":4,"province":"","shopLevel":1,"shopOwner":"晚安","shopType":4,"username":"","videoUrl":"http://101.201.209.200/upload/topic43012707832917313464056611057211172.png","shopId":4}]
     * size : 4
     * msg : 查询所有商家成功
     */

    private String status;
    private int size;
    private String msg;
    /**
     * address : 山东省济南市历下区
     * affiche : 店家公告
     * backGroundUrl : http://101.201.209.200/upload/topic43012707832917313464056611057211172.png
     * bisDescs : 庆典服务|婚礼定制|婚纱摄影|设备租赁|婚礼车队|婚宴喜宴
     * city : 济南
     * collectCount : 3
     * county : 历下区
     * descs : 定制婚礼指的是根据您对婚礼的期望与梦想，对花材的偏好与忌讳，以您的婚礼预算与酒店硬件设设施为约束条件，结合您的恋爱经历、职业、气质、性格、爱好、恋爱中有意义的细节等，设计婚礼风格和仪式流程，体现个性化与人性。
     * egCount : 8
     * id : 1
     * imageUrl : http://101.201.209.200/upload/topic43012707832917313464056611057211172.png
     * isSpread : 1
     * judgeCount : 0
     * label : 婚庆专家
     * name : 安庆婚典
     * phone : 18658986551
     * productCount : 6
     * province :
     * shopLevel : 1
     * shopOwner : 酰胺
     * shopType : 1
     * username : null
     * videoUrl : http://101.201.209.200/upload/topic43012707832917313464056611057211172.png
     * shopId : 1
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
        private String address;
        private String affiche;
        private String backGroundUrl;
        private String bisDescs;
        private String city;
        private int collectCount;
        private String county;
        private String descs;
        private int egCount;
        private int id;
        private String imageUrl;
        private int isSpread;
        private int judgeCount;
        private String label;
        private String name;
        private String phone;
        private int productCount;
        private String province;
        private int shopLevel;
        private String shopOwner;
        private int shopType;
        private String username;
        private String videoUrl;
        private int shopId;

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

        public int getIsSpread() {
            return isSpread;
        }

        public void setIsSpread(int isSpread) {
            this.isSpread = isSpread;
        }

        public int getJudgeCount() {
            return judgeCount;
        }

        public void setJudgeCount(int judgeCount) {
            this.judgeCount = judgeCount;
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

        public int getShopLevel() {
            return shopLevel;
        }

        public void setShopLevel(int shopLevel) {
            this.shopLevel = shopLevel;
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

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }
    }
}

