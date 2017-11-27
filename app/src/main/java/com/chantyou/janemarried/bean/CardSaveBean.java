package com.chantyou.janemarried.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */
public class CardSaveBean {

    /**
     * datas : [{"id":1,"textContent":""}]
     * userPageId : 1
     * userTempleteId : 1
     * token :
     * type : 1
     */
    private List<DatasEntity> datas;
    private int userPageId;
    private long userTempleteId;
    private String userId;
    private int type;

    public void setDatas(List<DatasEntity> datas) {
        this.datas = datas;
    }

    public void setUserPageId(int userPageId) {
        this.userPageId = userPageId;
    }

    public void setUserTempleteId(long userTempleteId) {
        this.userTempleteId = userTempleteId;
    }


    public void setType(int type) {
        this.type = type;
    }

    public List<DatasEntity> getDatas() {
        return datas;
    }

    public int getUserPageId() {
        return userPageId;
    }

    public long getUserTempleteId() {
        return userTempleteId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public static class DatasEntity {
        /**
         * id : 1
         * textContent :
         */
        private int id;
        private String textContent;

        public DatasEntity(int id, String textContent) {
            this.id = id;
            this.textContent = textContent;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setTextContent(String textContent) {
            this.textContent = textContent;
        }

        public int getId() {
            return id;
        }

        public String getTextContent() {
            return textContent;
        }
    }
}
