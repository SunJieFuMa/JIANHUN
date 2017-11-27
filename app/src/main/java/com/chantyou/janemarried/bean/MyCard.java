package com.chantyou.janemarried.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/7.
 */
public class MyCard implements Serializable {

    /**
     * status : 1
     * data : [{"width":779,"showImage":"http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/1-4-1/%E5%B1%95%E7%A4%BA%E5%9B%BE.png","pages":[{"commpents":[{"textColor":"","textContent":"","backgroundColor":"","imageUrl":"http://101.201.209.200:1661/qingjian","XT":0,"imageUrlTemp":"","width":779,"widthT":0,"createTim":14121212,"heightT":0,"type":1,"itemId":6,"id":38,"textSize":0,"height":1268,"style":1,"textAlign":1,"YT":0,"y":0,"x":0,"delFlag":1,"descs":"","hId":"zujian"}],"backgroundColor":"","pageId":6,"width":779,"showImage":"","createTim":1485274846579,"backgroundImgUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/281586c3-4d1d-475c-a5e3-fb1946201da1-2058.png","id":1485274846579,"height":1268,"userTempleteId":1485274817157,"pageInfo":"","userId":3861,"delFlag":1},{"commpents":[{"textColor":"","textContent":"","backgroundColor":"","imageUrl":"http://101.201.209.200:1661/qingjian","XT":0,"imageUrlTemp":"","width":779,"widthT":0,"createTim":14121212,"heightT":0,"type":1,"itemId":7,"id":39,"textSize":0,"height":1268,"style":1,"textAlign":1,"YT":0,"y":0,"x":0,"delFlag":1,"descs":"左组件","hId":"zujian"}],"backgroundColor":"","pageId":7,"width":779,"showImage":"","createTim":1485274878469,"backgroundImgUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/BANSHI/%E5%B7%A6.png","id":1485274878469,"height":1268,"userTempleteId":1485274817157,"pageInfo":"","userId":3861,"delFlag":1},{"commpents":[],"backgroundColor":"","pageId":5,"width":779,"showImage":"","createTim":1485274971360,"backgroundImgUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/1-4-1/%E5%B1%95%E7%A4%BA%E5%9B%BE1.png","id":1485274971360,"height":1268,"userTempleteId":1485274817157,"pageInfo":"","userId":3861,"delFlag":1},{"commpents":[],"backgroundColor":"","pageId":1,"width":779,"showImage":"","createTim":1485274980391,"backgroundImgUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/1-4-1/%E5%B1%95%E7%A4%BA%E5%9B%BE1.png","id":1485274980391,"height":1268,"userTempleteId":1485274817157,"pageInfo":"","userId":3861,"delFlag":1}],"createTim":1485274817157,"musicId":1,"musicUrl":"http://sxyd.sc.chinaz.com/files/download/sound1/201312/3851.wav","musicName":"鼓循环","lng":0,"backgroundImgUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/1-4-1/%E8%83%8C%E6%99%AF%E5%9B%BE.png","endPageInfo":"","templateId":6,"prePage":"","firstPageInfo":"","id":1485274817157,"preViewPages":[],"height":1268,"userId":3861,"endImage":"http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/%E6%9C%AA%E6%A0%87%E9%A2%98-2.png","firstPage":[{"textColor":"ffffff","textContent":"好","backgroundColor":"","imageUrl":"http://101.201.209.200:1661/qingjiannull","XT":0,"imageUrlTemp":"","width":155,"widthT":0,"createTim":1481622281,"heightT":0,"type":3,"itemId":6,"id":30,"textSize":40,"height":45,"style":4,"textAlign":3,"YT":0,"y":602.2,"x":197.54,"delFlag":1,"descs":"新郎","hId":"xinlang"},{"textColor":"ffffff","textContent":"在","backgroundColor":"","imageUrl":"http://101.201.209.200:1661/qingjiannull","XT":0,"imageUrlTemp":"","width":155,"widthT":0,"createTim":141222211,"heightT":0,"type":3,"itemId":6,"id":31,"textSize":40,"height":45,"style":4,"textAlign":1,"YT":0,"y":602.2,"x":422.54,"delFlag":1,"descs":"新娘","hId":"xinniang"},{"textColor":"ffffff","textContent":"2017-01-25 00:20","backgroundColor":"","imageUrl":"http://101.201.209.200:1661/qingjiannull","XT":0,"imageUrlTemp":"","width":418,"widthT":0,"createTim":14122212,"heightT":0,"type":3,"itemId":6,"id":32,"textSize":35,"height":40,"style":4,"textAlign":2,"YT":0,"y":697.19,"x":179.33,"delFlag":1,"descs":"时间","hId":"datetim"},{"textColor":"ffffff","textContent":"啊","backgroundColor":"","imageUrl":"http://101.201.209.200:1661/qingjiannull","XT":0,"imageUrlTemp":"","width":418,"widthT":0,"createTim":141222122,"heightT":0,"type":3,"itemId":6,"id":33,"textSize":35,"height":40,"style":4,"textAlign":2,"YT":0,"y":765.29,"x":179.33,"delFlag":1,"descs":"地点","hId":"address"}],"lat":0,"delFlag":1}]
     * size : 1
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
         * pages : [{"commpents":[{"textColor":"","textContent":"","backgroundColor":"","imageUrl":"http://101.201.209.200:1661/qingjian","XT":0,"imageUrlTemp":"","width":779,"widthT":0,"createTim":14121212,"heightT":0,"type":1,"itemId":6,"id":38,"textSize":0,"height":1268,"style":1,"textAlign":1,"YT":0,"y":0,"x":0,"delFlag":1,"descs":"","hId":"zujian"}],"backgroundColor":"","pageId":6,"width":779,"showImage":"","createTim":1485274846579,"backgroundImgUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/281586c3-4d1d-475c-a5e3-fb1946201da1-2058.png","id":1485274846579,"height":1268,"userTempleteId":1485274817157,"pageInfo":"","userId":3861,"delFlag":1},{"commpents":[{"textColor":"","textContent":"","backgroundColor":"","imageUrl":"http://101.201.209.200:1661/qingjian","XT":0,"imageUrlTemp":"","width":779,"widthT":0,"createTim":14121212,"heightT":0,"type":1,"itemId":7,"id":39,"textSize":0,"height":1268,"style":1,"textAlign":1,"YT":0,"y":0,"x":0,"delFlag":1,"descs":"左组件","hId":"zujian"}],"backgroundColor":"","pageId":7,"width":779,"showImage":"","createTim":1485274878469,"backgroundImgUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/BANSHI/%E5%B7%A6.png","id":1485274878469,"height":1268,"userTempleteId":1485274817157,"pageInfo":"","userId":3861,"delFlag":1},{"commpents":[],"backgroundColor":"","pageId":5,"width":779,"showImage":"","createTim":1485274971360,"backgroundImgUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/1-4-1/%E5%B1%95%E7%A4%BA%E5%9B%BE1.png","id":1485274971360,"height":1268,"userTempleteId":1485274817157,"pageInfo":"","userId":3861,"delFlag":1},{"commpents":[],"backgroundColor":"","pageId":1,"width":779,"showImage":"","createTim":1485274980391,"backgroundImgUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/1-4-1/%E5%B1%95%E7%A4%BA%E5%9B%BE1.png","id":1485274980391,"height":1268,"userTempleteId":1485274817157,"pageInfo":"","userId":3861,"delFlag":1}]
         * createTim : 1485274817157
         * musicId : 1
         * musicUrl : http://sxyd.sc.chinaz.com/files/download/sound1/201312/3851.wav
         * musicName : 鼓循环
         * lng : 0
         * backgroundImgUrl : http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/1-4-1/%E8%83%8C%E6%99%AF%E5%9B%BE.png
         * endPageInfo :
         * templateId : 6
         * prePage :
         * firstPageInfo :
         * id : 1485274817157
         * preViewPages : []
         * height : 1268
         * userId : 3861
         * endImage : http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/%E6%9C%AA%E6%A0%87%E9%A2%98-2.png
         * firstPage : [{"textColor":"ffffff","textContent":"好","backgroundColor":"","imageUrl":"http://101.201.209.200:1661/qingjiannull","XT":0,"imageUrlTemp":"","width":155,"widthT":0,"createTim":1481622281,"heightT":0,"type":3,"itemId":6,"id":30,"textSize":40,"height":45,"style":4,"textAlign":3,"YT":0,"y":602.2,"x":197.54,"delFlag":1,"descs":"新郎","hId":"xinlang"},{"textColor":"ffffff","textContent":"在","backgroundColor":"","imageUrl":"http://101.201.209.200:1661/qingjiannull","XT":0,"imageUrlTemp":"","width":155,"widthT":0,"createTim":141222211,"heightT":0,"type":3,"itemId":6,"id":31,"textSize":40,"height":45,"style":4,"textAlign":1,"YT":0,"y":602.2,"x":422.54,"delFlag":1,"descs":"新娘","hId":"xinniang"},{"textColor":"ffffff","textContent":"2017-01-25 00:20","backgroundColor":"","imageUrl":"http://101.201.209.200:1661/qingjiannull","XT":0,"imageUrlTemp":"","width":418,"widthT":0,"createTim":14122212,"heightT":0,"type":3,"itemId":6,"id":32,"textSize":35,"height":40,"style":4,"textAlign":2,"YT":0,"y":697.19,"x":179.33,"delFlag":1,"descs":"时间","hId":"datetim"},{"textColor":"ffffff","textContent":"啊","backgroundColor":"","imageUrl":"http://101.201.209.200:1661/qingjiannull","XT":0,"imageUrlTemp":"","width":418,"widthT":0,"createTim":141222122,"heightT":0,"type":3,"itemId":6,"id":33,"textSize":35,"height":40,"style":4,"textAlign":2,"YT":0,"y":765.29,"x":179.33,"delFlag":1,"descs":"地点","hId":"address"}]
         * lat : 0
         * delFlag : 1
         */
        private int width;
        private String showImage;
        private List<PagesEntity> pages;
        private long createTim;
        private int musicId;
        private String musicUrl;
        private String musicName;
        private int lng;
        private String backgroundImgUrl;
        private String endPageInfo;
        private int templateId;
        private String prePage;
        private String firstPageInfo;
        private long id;
        private List<?> preViewPages;
        private int height;
        private int userId;
        private String endImage;
        private List<FirstPageEntity> firstPage;
        private int lat;
        private int delFlag;

        public void setWidth(int width) {
            this.width = width;
        }

        public void setShowImage(String showImage) {
            this.showImage = showImage;
        }

        public void setPages(List<PagesEntity> pages) {
            this.pages = pages;
        }

        public void setCreateTim(long createTim) {
            this.createTim = createTim;
        }

        public void setMusicId(int musicId) {
            this.musicId = musicId;
        }

        public void setMusicUrl(String musicUrl) {
            this.musicUrl = musicUrl;
        }

        public void setMusicName(String musicName) {
            this.musicName = musicName;
        }

        public void setLng(int lng) {
            this.lng = lng;
        }

        public void setBackgroundImgUrl(String backgroundImgUrl) {
            this.backgroundImgUrl = backgroundImgUrl;
        }

        public void setEndPageInfo(String endPageInfo) {
            this.endPageInfo = endPageInfo;
        }

        public void setTemplateId(int templateId) {
            this.templateId = templateId;
        }

        public void setPrePage(String prePage) {
            this.prePage = prePage;
        }

        public void setFirstPageInfo(String firstPageInfo) {
            this.firstPageInfo = firstPageInfo;
        }

        public void setId(long id) {
            this.id = id;
        }

        public void setPreViewPages(List<?> preViewPages) {
            this.preViewPages = preViewPages;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public void setEndImage(String endImage) {
            this.endImage = endImage;
        }

        public void setFirstPage(List<FirstPageEntity> firstPage) {
            this.firstPage = firstPage;
        }

        public void setLat(int lat) {
            this.lat = lat;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public int getWidth() {
            return width;
        }

        public String getShowImage() {
            return showImage;
        }

        public List<PagesEntity> getPages() {
            return pages;
        }

        public long getCreateTim() {
            return createTim;
        }

        public int getMusicId() {
            return musicId;
        }

        public String getMusicUrl() {
            return musicUrl;
        }

        public String getMusicName() {
            return musicName;
        }

        public int getLng() {
            return lng;
        }

        public String getBackgroundImgUrl() {
            return backgroundImgUrl;
        }

        public String getEndPageInfo() {
            return endPageInfo;
        }

        public int getTemplateId() {
            return templateId;
        }

        public String getPrePage() {
            return prePage;
        }

        public String getFirstPageInfo() {
            return firstPageInfo;
        }

        public long getId() {
            return id;
        }

        public List<?> getPreViewPages() {
            return preViewPages;
        }

        public int getHeight() {
            return height;
        }

        public int getUserId() {
            return userId;
        }

        public String getEndImage() {
            return endImage;
        }

        public List<FirstPageEntity> getFirstPage() {
            return firstPage;
        }

        public int getLat() {
            return lat;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public static class PagesEntity implements Serializable{
            /**
             * commpents : [{"textColor":"","textContent":"","backgroundColor":"","imageUrl":"http://101.201.209.200:1661/qingjian","XT":0,"imageUrlTemp":"","width":779,"widthT":0,"createTim":14121212,"heightT":0,"type":1,"itemId":6,"id":38,"textSize":0,"height":1268,"style":1,"textAlign":1,"YT":0,"y":0,"x":0,"delFlag":1,"descs":"","hId":"zujian"}]
             * backgroundColor :
             * pageId : 6
             * width : 779
             * showImage :
             * createTim : 1485274846579
             * backgroundImgUrl : http://jh-video.oss-cn-shanghai.aliyuncs.com/qj/281586c3-4d1d-475c-a5e3-fb1946201da1-2058.png
             * id : 1485274846579
             * height : 1268
             * userTempleteId : 1485274817157
             * pageInfo :
             * userId : 3861
             * delFlag : 1
             */
            private List<CommpentsEntity> commpents;
            private String backgroundColor;
            private int pageId;
            private int width;
            private String showImage;
            private long createTim;
            private String backgroundImgUrl;
            private long id;
            private int height;
            private long userTempleteId;
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

            public void setId(long id) {
                this.id = id;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public void setUserTempleteId(long userTempleteId) {
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

            public long getId() {
                return id;
            }

            public int getHeight() {
                return height;
            }

            public long getUserTempleteId() {
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

            public static class CommpentsEntity implements Serializable{
                /**
                 * textColor :
                 * textContent :
                 * backgroundColor :
                 * imageUrl : http://101.201.209.200:1661/qingjian
                 * XT : 0
                 * imageUrlTemp :
                 * width : 779
                 * widthT : 0
                 * createTim : 14121212
                 * heightT : 0
                 * type : 1
                 * itemId : 6
                 * id : 38
                 * textSize : 0
                 * height : 1268
                 * style : 1
                 * textAlign : 1
                 * YT : 0
                 * y : 0
                 * x : 0
                 * delFlag : 1
                 * descs :
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

                @Override
                public String toString() {
                    return "CommpentsEntity{" +
                            "textColor='" + textColor + '\'' +
                            ", textContent='" + textContent + '\'' +
                            ", backgroundColor='" + backgroundColor + '\'' +
                            ", imageUrl='" + imageUrl + '\'' +
                            ", XT=" + XT +
                            ", imageUrlTemp='" + imageUrlTemp + '\'' +
                            ", width=" + width +
                            ", widthT=" + widthT +
                            ", createTim=" + createTim +
                            ", heightT=" + heightT +
                            ", type=" + type +
                            ", itemId=" + itemId +
                            ", id=" + id +
                            ", textSize=" + textSize +
                            ", height=" + height +
                            ", style=" + style +
                            ", textAlign=" + textAlign +
                            ", YT=" + YT +
                            ", y=" + y +
                            ", x=" + x +
                            ", delFlag=" + delFlag +
                            ", descs='" + descs + '\'' +
                            ", hId='" + hId + '\'' +
                            '}';
                }
            }
        }

        public static class FirstPageEntity implements Serializable{
            /**
             * textColor : ffffff
             * textContent : 好
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
