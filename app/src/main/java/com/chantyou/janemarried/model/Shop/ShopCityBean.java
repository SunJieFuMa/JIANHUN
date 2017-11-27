package com.chantyou.janemarried.model.Shop;

import java.io.Serializable;
import java.util.List;

/**
 * add by zhuwx on 2016/9/5.
 */
public class ShopCityBean implements Serializable{

    /**
     * status : 1
     * size : 11
     * data : [{"area":"市辖区","areaID":"370101","father":"370100","id":1348},{"area":"历下区","areaID":"370102","father":"370100","id":1349},{"area":"市中区","areaID":"370103","father":"370100","id":1350},{"area":"槐荫区","areaID":"370104","father":"370100","id":1351},{"area":"天桥区","areaID":"370105","father":"370100","id":1352},{"area":"历城区","areaID":"370112","father":"370100","id":1353},{"area":"长清区","areaID":"370113","father":"370100","id":1354},{"area":"平阴县","areaID":"370124","father":"370100","id":1355},{"area":"济阳县","areaID":"370125","father":"370100","id":1356},{"area":"商河县","areaID":"370126","father":"370100","id":1357},{"area":"章丘市","areaID":"370181","father":"370100","id":1358}]
     */

    private int status;
    private int size;
    /**
     * area : 市辖区
     * areaID : 370101
     * father : 370100
     * id : 1348
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
        private String area;
        private String areaID;
        private String father;
        private int id;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAreaID() {
            return areaID;
        }

        public void setAreaID(String areaID) {
            this.areaID = areaID;
        }

        public String getFather() {
            return father;
        }

        public void setFather(String father) {
            this.father = father;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}

