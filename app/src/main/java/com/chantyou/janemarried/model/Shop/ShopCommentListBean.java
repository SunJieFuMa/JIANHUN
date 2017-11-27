package com.chantyou.janemarried.model.Shop;

import java.io.Serializable;
import java.util.List;


public class ShopCommentListBean implements Serializable{


    /**
     * status : 1
     * data : [{"comments":"哈哈","createTim":1476323461886,"id":1,"imageUrl1":"","imageUrl2":"","imageUrl3":"","nickname":"","photo":"","reply":"","replyTime":0,"shopId":155,"shopName":"金夫人婚纱摄影（济南）","username":"757"}]
     * size : 1
     * msg : 查询评论成功
     */

    private String status;
    private int size;
    private String msg;
    /**
     * comments : 哈哈
     * createTim : 1476323461886
     * id : 1
     * imageUrl1 :
     * imageUrl2 :
     * imageUrl3 :
     * nickname :
     * photo :
     * reply :
     * replyTime : 0
     * shopId : 155
     * shopName : 金夫人婚纱摄影（济南）
     * username : 757
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
        private String comments;
        private long createTim;
        private int id;
        private String imageUrl1;
        private String imageUrl2;
        private String imageUrl3;
        private String nickname;
        private String photo;
        private String reply;
        private int replyTime;
        private int shopId;
        private String shopName;
        private String username;

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public long getCreateTim() {
            return createTim;
        }

        public void setCreateTim(long createTim) {
            this.createTim = createTim;
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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }

        public int getReplyTime() {
            return replyTime;
        }

        public void setReplyTime(int replyTime) {
            this.replyTime = replyTime;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}

