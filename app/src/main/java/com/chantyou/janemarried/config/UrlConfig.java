package com.chantyou.janemarried.config;

/**
 * Created by j_turn on 2016/1/18 20:23
 * Email：766082577@qq.com
 */
public class UrlConfig {

    public static final boolean DEBUG = true;

//    public static final String SERVER = "http://192.168.6.67"; //公司内网访问的
//    public static final String SERVER_TEST = "http://180.168.58.198"; //外网访问的
//
//    public static final String MEDIA_SERVER = "http://192.168.6.67";//媒体服务器
//    public static final String PORT = ":83";

    public static final String DEVICE_TYPE = "android";

    //    public static final String HOST_TEST = "http://192.168.1.102:8888";


        public static final String HOST = "http://101.201.209.200";//正式
//    public static final String HOST = "http://192.168.1.11:8080";//测试
    public static final String QingjianHost="http://101.201.209.200:1661/qingjian/action";//请柬接口的

    public static final String SERVER_PORT = HOST + "/MarryTrade";

    public static final String USER_LOGIN_URL = SERVER_PORT + "/interfaces/user!checkLogin.action";
    public static final String USER_SIGNOUT_URL = SERVER_PORT + "/interfaces/user!logout.action";
    public static final String USER_REG_URL = SERVER_PORT + "/interfaces/user!userReg.action";
    public static final String USER_FINDPWD_URL = SERVER_PORT + "/interfaces/user!userFindPwd.action";
    public static final String USER_EDIT = SERVER_PORT + "/interfaces/user!update.action";
    public static final String USER_INFO = SERVER_PORT + "/interfaces/user!findUser.action";
    public static final String USER_UPDATE_PUSHID = SERVER_PORT + "/interfaces/user!updatePushId.action";

    public static final String GET_SMS_CODE = SERVER_PORT + "/interfaces/user!getCode.action";

    public static final String USER_OAUTH_URL = SERVER_PORT + "/interfaces/user!oAuthLogin.action";

    public static final String MARRY_ADDPROCESS = SERVER_PORT + "/interfaces/marry-process!addProcess.action";
    public static final String MARRY_ALLPROCESS = SERVER_PORT + "/interfaces/marry-process!findAllProcess.action";
    public static final String MARRY_DELONEPROCESS = SERVER_PORT + "/interfaces/marry-process!delProcess.action";
    public static final String GIFTS_QUERYALL = SERVER_PORT + "/interfaces/cash-gift!findCashGift.action";
    public static final String GIFTS_ADDMARK = SERVER_PORT + "/interfaces/cash-gift!addCashGift.action";
    public static final String GIFTS_DELONE = SERVER_PORT + "/interfaces/cash-gift!deleteCashGift.action";

    /**
     * 首页接口
     **/
    public static final String HOME_PAGE = SERVER_PORT + "/interfaces/main-page!find.action";
    public static final String NEWS_DETAIL = SERVER_PORT + "/interfaces/main-page!getNewsDetail.action";
    /**  首页接口 **/

    /**
     * 采购清单
     **/
    public static final String PCHLIST_ALL = SERVER_PORT + "/interfaces/purchase-list!findPurchaseList.action";
    public static final String PCHLIST_ADD = SERVER_PORT + "/interfaces/purchase-list!addPurchaseList.action";
    public static final String PCHLIST_DEL = SERVER_PORT + "/interfaces/purchase-list!deletePurchaseList.action";
    /**  采购清单 **/

    /**
     * 结婚任务
     **/
    public static final String TASK_USERADDLIST = SERVER_PORT + "/interfaces/marry-task!userAddTaskList.action";
    public static final String TASK_USERADD = SERVER_PORT + "/interfaces/marry-task!saveUserTasks.action";
    public static final String TASK_USERDEL = SERVER_PORT + "/interfaces/marry-task!deleteUserTask.action";
    public static final String TASK_USERCOM = SERVER_PORT + "/interfaces/marry-task!completeUserTask.action";
    public static final String TASK_USEREDIT = SERVER_PORT + "/interfaces/marry-task!editUserTask.action";
    public static final String TASK_MY = SERVER_PORT + "/interfaces/marry-task!userTaskList.action";
    /**  结婚任务 **/

    /**
     * 发言稿
     **/
    public static final String SPEECH_ADD = SERVER_PORT + "/interfaces/speech!addSpeech.action";
    public static final String SPEECH_DEL = SERVER_PORT + "/interfaces/speech!deleteSpeech.action";
    public static final String SPEECH_LIST = SERVER_PORT + "/interfaces/speech!findSpeech.action";
    /**  发言稿 **/

    /**
     * 婚礼预算
     **/
    public static final String BUDGET_ALL = SERVER_PORT + "/interfaces/budget!allBudget.action";
    public static final String BUDGET_GET = SERVER_PORT + "/interfaces/budget!getBudget.action";
    public static final String BUDGET_EDIT = SERVER_PORT + "/interfaces/budget!editBudget.action";
    public static final String BUDGET_COMPUTE = SERVER_PORT + "/interfaces/budget!computeBudget.action";
    public static final String BUDGET_SAVE = SERVER_PORT + "/interfaces/budget!save2task.action";
    /**  婚礼预算 **/

    /**
     * 黄道吉日
     **/
    public static final String FORTUNE_FIND = SERVER_PORT + "/interfaces/ji-xiong!find.action";
    /**  黄道吉日 **/

    /**
     * 微信请柬
     **/
    public static final String CARD_SAVE = SERVER_PORT + "/interfaces/invitation-card!saveCardInfo.action";
    public static final String CARD_INFO = SERVER_PORT + "/interfaces/invitation-card!findCardInfo.action";
    /**  微信请柬 **/

    /**
     * 宾客名单
     **/
    public static final String GUESTLIST_ALL = SERVER_PORT + "/interfaces/desk-arrange!findDesk.action";
    public static final String GUESTLIST_ADD = SERVER_PORT + "/interfaces/desk-arrange!addDeskArrange.action";
    public static final String GUESTLIST_DEL = SERVER_PORT + "/interfaces/desk-arrange!deleteDeskArrange.action";
    /**  宾客名单 **/

    /**
     * 优婚品
     **/
    public static final String PRODUCT_CATE = SERVER_PORT + "/interfaces/product!findAllCate.action";
    public static final String PRODUCT_BYTYPE = SERVER_PORT + "/interfaces/product!findAllInOneType.action";
    public static final String PRODUCT_FINDCOLLECT = SERVER_PORT + "/interfaces/product!findCollect.action";
    public static final String PRODUCT_FINDCOMMENT = SERVER_PORT + "/interfaces/product!findReviewList.action";
    public static final String PRODUCT_DETAIL = SERVER_PORT + "/interfaces/product!findDetail.action";
    public static final String PRODUCT_COMMENT = SERVER_PORT + "/interfaces/product!comment.action";
    public static final String PRODUCT_FAVORITE = SERVER_PORT + "/interfaces/product!collect.action";
    public static final String PRODUCT_DELFAVORITE = SERVER_PORT + "/interfaces/product!delCollect.action";
    /**  优婚品 **/

    /**
     * 结婚帮&话题
     **/
    public static final String TOPIC_ADD_EDIT = SERVER_PORT + "/interfaces/topic!addTopic.action";
    public static final String TOPIC_BEST = SERVER_PORT + "/interfaces/topic!findBest.action";//结婚帮里面的精选
    public static final String TOPIC_LATEST = SERVER_PORT + "/interfaces/topic!findNews.action";//结婚帮里面的最新
    public static final String TOPIC_INFO = SERVER_PORT + "/interfaces/topic!findDetail.action";
    public static final String TOPIC_COLLECT = SERVER_PORT + "/interfaces/topic!collect.action";
    public static final String TOPIC_DELCOLLECT = SERVER_PORT + "/interfaces/topic!delCollect.action";
    public static final String TOPIC_COMMENT = SERVER_PORT + "/interfaces/topic!comment.action";
    public static final String TOPIC_COMMENT_LIST = SERVER_PORT + "/interfaces/topic!findReviewList.action";
    /**  结婚帮&话题 **/

    /**
     * 婚礼阶段
     **/
    public static final String PHASES_ALL = SERVER_PORT + "/interfaces/marry-phase!findAllPhases.action";
    /**         婚礼阶段       **/

    /**
     * 标签
     **/
    public static final String TAGS_INPHASE = SERVER_PORT + "/interfaces/marry-phase!findAllTagsInOnePhase.action";
    public static final String TAGS_ADD = SERVER_PORT + "/interfaces/tag!addTag.action";
    /**         标签       **/

    /**
     * 我的
     **/
    public static final String TOPIC_MYALL = SERVER_PORT + "/interfaces/topic!findAllMyTopics.action";
    public static final String TOPIC_MYCOLLECT = SERVER_PORT + "/interfaces/topic!findCollect.action";
    public static final String SHOP_MY_COLLECT = SERVER_PORT + "/interfaces/jinyou/shop!collections.action";//我收藏的店铺
    /**
     * 我的
     **/


    // 商铺
    public static final String SHOP_LIST = SERVER_PORT + "/interfaces/jinyou/shop!list.action";//商铺列表
    public static final String SHOP_INFO = SERVER_PORT + "/interfaces/jinyou/shop!info.action";//商铺信息
    public static final String SHOP_AD_LIST = SERVER_PORT + "/interfaces/jinyou/advs!list.action";//轮播图
    public static final String SHOP_PACKAGE = SERVER_PORT + "/interfaces/jinyou/shop!listProducts01.action";//套餐
    public static final String SHOP_FOLLOW = SERVER_PORT + "/interfaces/jinyou/shop!follow.action";//加收藏
    public static final String SHOP_DIS_FOLLOW = SERVER_PORT + "/interfaces/jinyou/shop!disFollow.action";//取消收藏
    public static final String SHOP_DEMO = SERVER_PORT + "/interfaces/jinyou/shop!listCases01.action";//案例
    public static final String SHOP_PACKAGE_INFO = SERVER_PORT + "/interfaces/jinyou/shop!listProductTuwen.action";//套餐详情
    public static final String SHOP_DEMO_INFO = SERVER_PORT + "/interfaces/jinyou/shop!listCasesTuwen.action";//案例详情

    public static final String CITY_COUNTY = SERVER_PORT + "/interfaces/jinyou/chnarea!list.action";//根据城市获取区县
    public static final String SHOP_APPOINTMENT= SERVER_PORT + "/interfaces/jinyou/shop!appoint.action";//用户可以选择某一个商铺进行预约。

    //评论
    public static final String SHOP_COMMENT_LIST = SERVER_PORT + "/interfaces/jinyou/shop!comments.action";//评论列表
    public static final String SHOP_COMMENT_ADD = SERVER_PORT + "/interfaces/jinyou/shop!addComment.action";//发表评论

    //版本更新
    public static final String VERSION_CHECK = SERVER_PORT + "/interfaces/jinyou/version!info.action";//版本更新
}
