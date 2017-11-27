package com.chantyou.janemarried.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/7.
 */
public class AllCard implements Serializable{

    /**
     * status : 1
     * data : [{"width":779,"showImage":"http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/1-4-1/%E5%B1%95%E7%A4%BA%E5%9B%BE.png","createTim":1488888888,"backgroundImgUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/1-4-1/%E8%83%8C%E6%99%AF%E5%9B%BE.png","endPageInfo":"","firstPageInfo":"","id":6,"prePage":"1","preViewPages":[],"height":1268,"endImage":"http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/%E6%9C%AA%E6%A0%87%E9%A2%98-2.png","firstPage":[{"textColor":"ffffff","textContent":"","backgroundColor":"","imageUrl":"http://101.201.209.200:1661/qingjiannull","XT":0,"imageUrlTemp":"","width":155,"widthT":0,"createTim":1481622281,"heightT":0,"type":3,"itemId":6,"id":30,"textSize":40,"height":45,"style":4,"textAlign":3,"YT":0,"y":602.2,"x":197.54,"delFlag":1,"descs":"新郎","hId":"xinlang"},{"textColor":"ffffff","textContent":"","backgroundColor":"","imageUrl":"http://101.201.209.200:1661/qingjiannull","XT":0,"imageUrlTemp":"","width":155,"widthT":0,"createTim":1481622281,"heightT":0,"type":3,"itemId":6,"id":30,"textSize":40,"height":45,"style":4,"textAlign":3,"YT":0,"y":602.2,"x":197.54,"delFlag":1,"descs":"新郎","hId":"xinlang"}],"delFlag":1,"isLock":1}]
     * size : 7
     */
    private int status;
    private List<DataEntity> data;
    private int size;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStatus() {
        return status;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public int getSize() {
        return size;
    }

    public static class DataEntity implements Serializable{
        /**
         * width : 779
         * showImage : http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/1-4-1/%E5%B1%95%E7%A4%BA%E5%9B%BE.png
         * createTim : 1488888888
         * backgroundImgUrl : http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/1-4-1/%E8%83%8C%E6%99%AF%E5%9B%BE.png
         * endPageInfo :
         * firstPageInfo :
         * id : 6
         * prePage : 1
         * preViewPages : []
         * height : 1268
         * endImage : http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/%E6%9C%AA%E6%A0%87%E9%A2%98-2.png
         * firstPage : [{"textColor":"ffffff","textContent":"","backgroundColor":"","imageUrl":"http://101.201.209.200:1661/qingjiannull","XT":0,"imageUrlTemp":"","width":155,"widthT":0,"createTim":1481622281,"heightT":0,"type":3,"itemId":6,"id":30,"textSize":40,"height":45,"style":4,"textAlign":3,"YT":0,"y":602.2,"x":197.54,"delFlag":1,"descs":"新郎","hId":"xinlang"},{"textColor":"ffffff","textContent":"","backgroundColor":"","imageUrl":"http://101.201.209.200:1661/qingjiannull","XT":0,"imageUrlTemp":"","width":155,"widthT":0,"createTim":1481622281,"heightT":0,"type":3,"itemId":6,"id":30,"textSize":40,"height":45,"style":4,"textAlign":3,"YT":0,"y":602.2,"x":197.54,"delFlag":1,"descs":"新郎","hId":"xinlang"}]
         * delFlag : 1
         * isLock : 1
         */
        private int width;
        private String showImage;
        private long createTim;
        private String backgroundImgUrl;
        private String endPageInfo;
        private String firstPageInfo;
        private int id;
        private String prePage;
        private List<?> preViewPages;
        private int height;
        private String endImage;
        private List<FirstPageEntity> firstPage;
        private int delFlag;
        private int isLock;

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

        public void setEndPageInfo(String endPageInfo) {
            this.endPageInfo = endPageInfo;
        }

        public void setFirstPageInfo(String firstPageInfo) {
            this.firstPageInfo = firstPageInfo;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setPrePage(String prePage) {
            this.prePage = prePage;
        }

        public void setPreViewPages(List<?> preViewPages) {
            this.preViewPages = preViewPages;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public void setEndImage(String endImage) {
            this.endImage = endImage;
        }

        public void setFirstPage(List<FirstPageEntity> firstPage) {
            this.firstPage = firstPage;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public void setIsLock(int isLock) {
            this.isLock = isLock;
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

        public String getEndPageInfo() {
            return endPageInfo;
        }

        public String getFirstPageInfo() {
            return firstPageInfo;
        }

        public int getId() {
            return id;
        }

        public String getPrePage() {
            return prePage;
        }

        public List<?> getPreViewPages() {
            return preViewPages;
        }

        public int getHeight() {
            return height;
        }

        public String getEndImage() {
            return endImage;
        }

        public List<FirstPageEntity> getFirstPage() {
            return firstPage;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public int getIsLock() {
            return isLock;
        }

        public static class FirstPageEntity implements Serializable{
            /**
             * textColor : ffffff
             * textContent :
             * backgroundColor :
             * imageUrl : http://101.201.209.200:1661/qingjiannull
             * XT : 0
             * imageUrlTemp :
             * width : 155
             * widthT : 0
             * createTim : 1481622281
             * heightT : 0
             * type : 3
             * itemId : 6
             * id : 30
             * textSize : 40
             * height : 45
             * style : 4
             * textAlign : 3
             * YT : 0
             * y : 602.2
             * x : 197.54
             * delFlag : 1
             * descs : 新郎
             * hId : xinlang
             */
            private String textColor;
            private String textContent;
            private String backgroundColor;
            private String imageUrl;
            private int XT;
            private String imageUrlTemp;
            private int width;
            private int widthT;
            private long createTim;
            private int heightT;
            private int type;
            private int itemId;
            private int id;
            private int textSize;
            private int height;
            private int style;
            private int textAlign;
            private int YT;
            private double y;
            private double x;
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

            public void setCreateTim(long createTim) {
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

            public void setY(double y) {
                this.y = y;
            }

            public void setX(double x) {
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

            public long getCreateTim() {
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

            public double getY() {
                return y;
            }

            public double getX() {
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
