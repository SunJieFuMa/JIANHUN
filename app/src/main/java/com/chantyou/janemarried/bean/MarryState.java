package com.chantyou.janemarried.bean;

import java.util.List;

/**
 * Created by jianhun on 2017/3/22.
 */
public class MarryState {

    /**
     * msg : 查询成功
     * return_code : 0
     * phases : [{"attentionThings":"双方父母见面注意把控话题,着装正式。<br>结婚登记选择白色或者淡色的衣服。<br>土豪新人提前准备申请蜜月签证。<br>新人双方共同定制一份结婚时间表。<br>确定婚期的时候要注意双方的八字。<br>提前约定伴郎伴娘人选，身材尺码。","name":"前期准备","phaseId":1},{"attentionThings":"确认酒店当天有几家婚宴同时举行<br>酒店提供车位的数量及周边交通状况<br>化妆间大小、私密性、与迎宾和宴会厅的距离<br>签订酒店合同多次审核，看清定金与订金的区别<br>提前试菜调整并于酒店协商婚宴当天的菜品质量","name":"婚宴预约","phaseId":2},{"attentionThings":"提前挑选口碑信誉好的影楼和工作室<br>确定无额外消费，如路费、餐费、门票等<br>确定套系是否提供全部照片，精修照片有多少<br>套系外的精修和入册相框价格不菲，慎重选择<br>拍摄前一周注意皮肤的保养，同时进行剪发烫发<br>拍摄当天保持好心情，与摄影师沟通配合<br>不要轻易被影楼提出的首席摄影师迷惑","name":"婚纱摄影","phaseId":4},{"attentionThings":"研究婚庆公司的套餐，有疑问当面提出解决<br>婚礼所用的材料、花艺、路引及时确认<br>提前与婚庆公司预约实地考察，实地了解婚庆安排<br>了解整体流程，随时沟通防止意外发生","name":"婚庆预约","phaseId":6},{"attentionThings":"根据当地风俗购置婚礼用品，采购清单可查看备婚助手<br>与商家沟通商品的退换方式及时间要求<br>礼炮选择正规渠道购买，存放阴凉没有明火的地方<br>可食用婚品注意产地及来源，杜绝二次包装产品","name":"婚品购置","phaseId":7,"title":""},{"attentionThings":"了解司仪的主持风格，并确定婚礼的风格<br>提前试妆，选择自己没有过敏的化妆品品牌<br>摄影师所用机型及拍摄风格要提前落实<br>不是要价越高的摄影化妆主持就是最好的', '化妆、主持、摄像","name":"化妆、主持、摄像","phaseId":8},{"attentionThings":"蜜月旅行要时刻注意个人及财产安全<br>蜜月时间安排有弹性，享受旅行的乐趣<br>随身携带必备药品，晕车晕船创可贴等药物。","name":"蜜月旅行","phaseId":9}]
     */
    private String msg;
    private String return_code;
    private List<PhasesEntity> phases;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public void setPhases(List<PhasesEntity> phases) {
        this.phases = phases;
    }

    public String getMsg() {
        return msg;
    }

    public String getReturn_code() {
        return return_code;
    }

    public List<PhasesEntity> getPhases() {
        return phases;
    }

    public static class PhasesEntity {
        /**
         * attentionThings : 双方父母见面注意把控话题,着装正式。<br>结婚登记选择白色或者淡色的衣服。<br>土豪新人提前准备申请蜜月签证。<br>新人双方共同定制一份结婚时间表。<br>确定婚期的时候要注意双方的八字。<br>提前约定伴郎伴娘人选，身材尺码。
         * name : 前期准备
         * phaseId : 1
         */
        private String attentionThings;
        private String name;
        private int phaseId;

        public void setAttentionThings(String attentionThings) {
            this.attentionThings = attentionThings;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPhaseId(int phaseId) {
            this.phaseId = phaseId;
        }

        public String getAttentionThings() {
            return attentionThings;
        }

        public String getName() {
            return name;
        }

        public int getPhaseId() {
            return phaseId;
        }
    }
}
