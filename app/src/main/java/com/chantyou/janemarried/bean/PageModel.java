package com.chantyou.janemarried.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/14.
 */
public class PageModel {

    /**
     * data : [{"commpents":[{"textColor":"","textContent":"","backgroundColor":"","imageUrl":"http://101.201.209.200:1661/qingjiannull","XT":0,"imageUrlTemp":"","width":779,"widthT":0,"createTim":14121212,"heightT":0,"type":1,"itemId":7,"id":39,"textSize":0,"height":1268,"style":1,"textAlign":1,"YT":0,"y":0,"x":0,"delFlag":1,"descs":"左组件","hId":"zujian"}],"backgroundColor":"","pageId":0,"width":779,"showImage":"http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/BANSHI/%E5%B7%A6.png","createTim":14888888888,"backgroundImgUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/BANSHI/%E5%B7%A6.png","id":7,"height":1268,"userTempleteId":0,"pageInfo":"","userId":0,"delFlag":1}]
     */
    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * commpents : [{"textColor":"","textContent":"","backgroundColor":"","imageUrl":"http://101.201.209.200:1661/qingjiannull","XT":0,"imageUrlTemp":"","width":779,"widthT":0,"createTim":14121212,"heightT":0,"type":1,"itemId":7,"id":39,"textSize":0,"height":1268,"style":1,"textAlign":1,"YT":0,"y":0,"x":0,"delFlag":1,"descs":"左组件","hId":"zujian"}]
         * backgroundColor :
         * pageId : 0
         * width : 779
         * showImage : http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/BANSHI/%E5%B7%A6.png
         * createTim : 14888888888
         * backgroundImgUrl : http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/BANSHI/%E5%B7%A6.png
         * id : 7
         * height : 1268
         * userTempleteId : 0
         * pageInfo :
         * userId : 0
         * delFlag : 1
         */
        private List<CommpentsEntity> commpents;
        private String backgroundColor;
        private int pageId;
        private int width;
        private String showImage;
        private long createTim;
        private String backgroundImgUrl;
        private int id;
        private int height;
        private int userTempleteId;
        private String pageInfo;
        private int userId;
        private int delFlag;

        public void setCommpents(List<CommpentsEntity> commpents) {
            this.commpents = commpents;
        }

        public void setBackgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public void setPageId(int pageId) {
            this.pageId = pageId;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public void setShowImage(String showImage) {
            this.showImage = showImage;
        }

        public void setCreateTim(long createTim) {
            this.createTim = createTim;
        }

        public void setBackgroundImgUrl(String backgroundImgUrl) {
            this.backgroundImgUrl = backgroundImgUrl;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public void setUserTempleteId(int userTempleteId) {
            this.userTempleteId = userTempleteId;
        }

        public void setPageInfo(String pageInfo) {
            this.pageInfo = pageInfo;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public List<CommpentsEntity> getCommpents() {
            return commpents;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }

        public int getPageId() {
            return pageId;
        }

        public int getWidth() {
            return width;
        }

        public String getShowImage() {
            return showImage;
        }

        public long getCreateTim() {
            return createTim;
        }

        public String getBackgroundImgUrl() {
            return backgroundImgUrl;
        }

        public int getId() {
            return id;
        }

        public int getHeight() {
            return height;
        }

        public int getUserTempleteId() {
            return userTempleteId;
        }

        public String getPageInfo() {
            return pageInfo;
        }

        public int getUserId() {
            return userId;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public static class CommpentsEntity {
            /**
             * textColor :
             * textContent :
             * backgroundColor :
             * imageUrl : http://101.201.209.200:1661/qingjiannull
             * XT : 0
             * imageUrlTemp :
             * width : 779
             * widthT : 0
             * createTim : 14121212
             * heightT : 0
             * type : 1
             * itemId : 7
             * id : 39
             * textSize : 0
             * height : 1268
             * style : 1
             * textAlign : 1
             * YT : 0
             * y : 0
             * x : 0
             * delFlag : 1
             * descs : 左组件
             * hId : zujian
             */
            private String textColor;
            private String textContent;
            private String backgroundColor;
            private String imageUrl;
            private int XT;
            private String imageUrlTemp;
            private int width;
            private int widthT;
            private int createTim;
            private int heightT;
            private int type;
            private int itemId;
            private int id;
            private int textSize;
            private int height;
            private int style;
            private int textAlign;
            private int YT;
            private int y;
            private int x;
            private int delFlag;
            private String descs;
            private String hId;

            public void setTextColor(String textColor) {
                this.textColor = textColor;
            }

            public void setTextContent(String textContent) {
                this.textContent = textContent;
            }

            public void setBackgroundColor(String backgroundColor) {
                this.backgroundColor = backgroundColor;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public void setXT(int XT) {
                this.XT = XT;
            }

            public void setImageUrlTemp(String imageUrlTemp) {
                this.imageUrlTemp = imageUrlTemp;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public void setWidthT(int widthT) {
                this.widthT = widthT;
            }

            public void setCreateTim(int createTim) {
                this.createTim = createTim;
            }

            public void setHeightT(int heightT) {
                this.heightT = heightT;
            }

            public void setType(int type) {
                this.type = type;
            }

            public void setItemId(int itemId) {
                this.itemId = itemId;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setTextSize(int textSize) {
                this.textSize = textSize;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public void setStyle(int style) {
                this.style = style;
            }

            public void setTextAlign(int textAlign) {
                this.textAlign = textAlign;
            }

            public void setYT(int YT) {
                this.YT = YT;
            }

            public void setY(int y) {
                this.y = y;
            }

            public void setX(int x) {
                this.x = x;
            }

            public void setDelFlag(int delFlag) {
                this.delFlag = delFlag;
            }

            public void setDescs(String descs) {
                this.descs = descs;
            }

            public void setHId(String hId) {
                this.hId = hId;
            }

            public String getTextColor() {
                return textColor;
            }

            public String getTextContent() {
                return textContent;
            }

            public String getBackgroundColor() {
                return backgroundColor;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public int getXT() {
                return XT;
            }

            public String getImageUrlTemp() {
                return imageUrlTemp;
            }

            public int getWidth() {
                return width;
            }

            public int getWidthT() {
                return widthT;
            }

            public int getCreateTim() {
                return createTim;
            }

            public int getHeightT() {
                return heightT;
            }

            public int getType() {
                return type;
            }

            public int getItemId() {
                return itemId;
            }

            public int getId() {
                return id;
            }

            public int getTextSize() {
                return textSize;
            }

            public int getHeight() {
                return height;
            }

            public int getStyle() {
                return style;
            }

            public int getTextAlign() {
                return textAlign;
            }

            public int getYT() {
                return YT;
            }

            public int getY() {
                return y;
            }

            public int getX() {
                return x;
            }

            public int getDelFlag() {
                return delFlag;
            }

            public String getDescs() {
                return descs;
            }

            public String getHId() {
                return hId;
            }
        }
    }
}
