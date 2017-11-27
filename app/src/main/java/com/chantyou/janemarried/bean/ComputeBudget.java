package com.chantyou.janemarried.bean;

import java.util.List;

/**
 * Created by jianhun on 2017/3/13.
 */
public class ComputeBudget {

    /**
     * msg : 查询成功
     * data : [{"money":"17401.39","name":"婚宴总预算","id":1},{"money":"870.07","name":"每桌预算","id":2},{"money":"2122.97","name":"婚纱&礼服","id":3},{"money":"3654.29","name":"婚戒","id":4},{"money":"348.03","name":"新人饰品","id":5},{"money":"5220.42","name":"摄影&摄像","id":6},{"money":"382.83","name":"乐队/司仪","id":7},{"money":"139.21","name":"捧花&花艺","id":8},{"money":"156.61","name":"婚礼蛋糕","id":9},{"money":"87.01","name":"请帖/婚礼卡片","id":10},{"money":"208.82","name":"喜糖回礼","id":11},{"money":"278.42","name":"其他(用车)","id":12}]
     * return_code : 0
     */
    private String msg;
    private List<DataEntity> data;
    private String return_code;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getMsg() {
        return msg;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public String getReturn_code() {
        return return_code;
    }

    public static class DataEntity {
        /**
         * money : 17401.39
         * name : 婚宴总预算
         * id : 1
         */
        private String money;
        private String name;
        private int id;

        public void setMoney(String money) {
            this.money = money;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMoney() {
            return money;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }
    }
}
