package com.chantyou.janemarried.model.Shop;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Triones on 2016/9/10 10:59.
 */
public class ShopADListBean implements Serializable {


    /**
     * status : 1
     * size : 2
     * data : [{"adDesc":"简婚-01","adName":"广告名","adUrl":"http://101.201.209.200/files/pic/pic9/201604/apic20118.jpg","hrefUrl":"","isHref":0},{"adDesc":"简婚-02","adName":"广告名","adUrl":"http://101.201.209.200/files/pic/pic9/201512/apic17186.jpg","hrefUrl":"","isHref":0}]
     */

    private int status;
    private int size;
    /**
     * adDesc : 简婚-01
     * adName : 广告名
     * adUrl : http://101.201.209.200/files/pic/pic9/201604/apic20118.jpg
     * hrefUrl :
     * isHref : 0
     */

    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
        private String adDesc;
        private String adName;
        private String adUrl;
        private String hrefUrl;
        private int isHref;

        public String getAdDesc() {
            return adDesc;
        }

        public void setAdDesc(String adDesc) {
            this.adDesc = adDesc;
        }

        public String getAdName() {
            return adName;
        }

        public void setAdName(String adName) {
            this.adName = adName;
        }

        public String getAdUrl() {
            return adUrl;
        }

        public void setAdUrl(String adUrl) {
            this.adUrl = adUrl;
        }

        public String getHrefUrl() {
            return hrefUrl;
        }

        public void setHrefUrl(String hrefUrl) {
            this.hrefUrl = hrefUrl;
        }

        public int getIsHref() {
            return isHref;
        }

        public void setIsHref(int isHref) {
            this.isHref = isHref;
        }
    }
}


