package com.chantyou.janemarried.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/10.
 */
public class MusicListBean {

    /**
     * status : 1
     * data : [{"id":1,"name":"鼓循环","createTim":1481622280,"musicUrl":"http://sxyd.sc.chinaz.com/files/download/sound1/201312/3851.wav","delFlag":1},{"id":3,"name":"A little love（冯曦妤）","createTim":1481622280,"musicUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/music/A%20little%20love%EF%BC%88%E5%86%AF%E6%9B%A6%E5%A6%A4%EF%BC%89.mp3","delFlag":1},{"id":4,"name":"Bryan Adams-Right Here Waiting","createTim":1481622280,"musicUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/music/Bryan%20Adams-Right%20Here%20Waiting.mp3","delFlag":1},{"id":5,"name":"Carly Rae Jepsen - I Really Like You","createTim":1481622280,"musicUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/music/Carly%20Rae%20Jepsen%20-%20I%20Really%20Like%20You.mp3","delFlag":1},{"id":6,"name":"Darin Zanyar - Can't Stop Love","createTim":1481622280,"musicUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/music/Darin%20Zanyar%20-%20Can%27t%20Stop%20Love.mp3","delFlag":1},{"id":7,"name":"I believe","createTim":1481622280,"musicUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/music/I%20believe%EF%BC%88%E9%92%A2%E7%90%B4%E6%9B%B2%EF%BC%89.mp3","delFlag":1},{"id":8,"name":"I know i loved you（Darren）","createTim":1481622280,"musicUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/music/I%20know%20i%20loved%20you%EF%BC%88Darren%EF%BC%89.mp3","delFlag":1},{"id":9,"name":"Joy、任瑟雍 - 이별을 배웠어 (Always In My Heart)","createTim":1481622280,"musicUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/music/Joy%E3%80%81%E4%BB%BB%E7%91%9F%E9%9B%8D%20-%20%EC%9D%B4%EB%B3%84%EC%9D%84%20%EB%B0%B0%EC%9B%A0%EC%96%B4%20%28Always%20In%20My%20Heart%29.mp3","delFlag":1},{"id":10,"name":"Maria Arredondo - I Do","createTim":1481622280,"musicUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/music/Maria%20Arredondo%20-%20I%20Do.mp3","delFlag":1},{"id":11,"name":"Maroon 5 - Sugar","createTim":1481622280,"musicUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/music/Maroon%205%20-%20Sugar.mp3","delFlag":1},{"id":12,"name":"Tommy Shane Steiner-What If She's An Angel","createTim":1481622280,"musicUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/music/Tommy%20Shane%20Steiner-What%20If%20She%27s%20An%20Angel.mp3","delFlag":1},{"id":13,"name":"Uptown Girl - westlife","createTim":1481622280,"musicUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/music/Westlife%20-%20Uptown%20Girl.mp3","delFlag":1},{"id":14,"name":"beautiful love-蔡健雅","createTim":1481622280,"musicUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/music/beautiful%20love-%E8%94%A1%E5%81%A5%E9%9B%85.mp3","delFlag":1},{"id":15,"name":"first love-宇多田光","createTim":1481622280,"musicUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/music/first%20love-%E5%AE%87%E5%A4%9A%E7%94%B0%E5%85%89.mp3","delFlag":1},{"id":16,"name":"今天你要嫁给我（蔡依林、陶喆）","createTim":1481622280,"musicUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/music/%E4%BB%8A%E5%A4%A9%E4%BD%A0%E8%A6%81%E5%AB%81%E7%BB%99%E6%88%91%EF%BC%88%E8%94%A1%E4%BE%9D%E6%9E%97%E3%80%81%E9%99%B6%E5%96%86%EF%BC%89.mp3","delFlag":1},{"id":17,"name":"浪漫伴奏","createTim":1481622280,"musicUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/music/%E5%94%AF%E7%BE%8E%E6%B5%AA%E6%BC%AB%E7%9A%84%E8%8B%B1%E6%96%87%E4%BC%B4%E5%A5%8F.mp3","delFlag":1},{"id":18,"name":"小星星变奏曲（钢琴曲）","createTim":1481622280,"musicUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/music/%E5%B0%8F%E6%98%9F%E6%98%9F%E5%8F%98%E5%A5%8F%E6%9B%B2%EF%BC%88%E9%92%A2%E7%90%B4%E6%9B%B2%EF%BC%89.mp3","delFlag":1},{"id":19,"name":"就是爱你","createTim":1481622280,"musicUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/music/%E5%B0%B1%E6%98%AF%E7%88%B1%E4%BD%A0.mp3","delFlag":1},{"id":20,"name":"我们结婚吧","createTim":1481622280,"musicUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/music/%E6%88%91%E4%BB%AC%E7%BB%93%E5%A9%9A%E5%90%A7.mp3","delFlag":1},{"id":21,"name":"最浪漫的事","createTim":1481622280,"musicUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/music/%E6%9C%80%E6%B5%AA%E6%BC%AB%E7%9A%84%E4%BA%8B%EF%BC%88%E8%B5%B5%E5%92%8F%E5%8D%8E%EF%BC%89.mp3","delFlag":1},{"id":22,"name":"郝云-结了","createTim":1481622280,"musicUrl":"http://jh-video.oss-cn-shanghai.aliyuncs.com/music/%E9%83%9D%E4%BA%91%20-%20%E7%BB%93%E4%BA%86.mp3","delFlag":1}]
     * size : 21
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

    public static class DataEntity {
        public DataEntity(String name) {
            this.name = name;
        }

        /**
         * id : 1
         * name : 鼓循环
         * createTim : 1481622280
         * musicUrl : http://sxyd.sc.chinaz.com/files/download/sound1/201312/3851.wav
         * delFlag : 1
         */
        private int id;
        private String name;
        private int createTim;
        private String musicUrl;
        private int delFlag;

        private boolean isSelected=false;//这个字段是我自己加上去的，是为了判断这个item是否被选中,默认为FALSE

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }


        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCreateTim(int createTim) {
            this.createTim = createTim;
        }

        public void setMusicUrl(String musicUrl) {
            this.musicUrl = musicUrl;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getCreateTim() {
            return createTim;
        }

        public String getMusicUrl() {
            return musicUrl;
        }

        public int getDelFlag() {
            return delFlag;
        }
    }
}
