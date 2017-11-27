package com.chantyou.janemarried.framework;


import com.chantyou.janemarried.R;

/**
 * Created by j_turn on 2016/01/16.
 */
public interface XEventCode {

    public static final int EVENT_MAINMENU = R.id.id_main_menu;

    public static int SMS_CODE_EVENT = R.id.ev_sms;              //短信验证码
    public static int EVENT_MENU_SELECT = R.id.menu_select;      // 测试页面类型选择
    public static int EVENT_USER_LOGIN = R.id.login_back;
    public static int EVENT_CITY_ALL = R.id.city_all;
    int EVENT_RUNCODE = R.id.event_runcode;
    int EVENT_SWIP_CALENAR = R.id.event_swip_cal;
    int ASYN_CALENAR = R.id.syn_cal;
    int EVENT_AUTH_CODE = R.id.evevnt_auth_code;
    int HTTP_AUTH_WECHAT = R.id.auth_wechat;
    int HTTP_USER_AOUTH = R.id.user_aouth;

    public static int HTTP_GET_ACCESS_TOKEN = R.id.get_atoken;
    public static int HTTP_USER_LOGIN = R.id.user_login;
    public static int HTTP_USER_SIGNOUT = R.id.user_signout;
    public static int HTTP_USER_REG = R.id.user_reg;
    public static int HTTP_USER_REG2 = R.id.user_reg2;
    public static int HTTP_USER_INFO = R.id.user_info;
    public static int HTTP_USER_UPDATE_PUSH = R.id.user_update_push;
    public static int HTTP_USER_EDIT = R.id.user_edit;
    public static int HTTP_USER_FINDPWD = R.id.user_findpwd;
    public static int HTTP_MARRY_DAY_EDIT = R.id.marry_day_edit;

    public static int HTTP_MARRY_ADDPROCESS = R.id.marry_addprocess;
    public static int HTTP_MARRY_QUERYALLPRO = R.id.marry_allprocess;
    public static int HTTP_MARRY_DELONEPRO = R.id.marry_deloneprocess;
    public static int HTTP_GIFTS_QUERYALL = R.id.gifts_queryall;
    public static int HTTP_GIFTS_ADDONE = R.id.gifts_addone;
    public static int HTTP_GIFTS_DELONE = R.id.gifts_delone;
    int HTTP_PCHLIST_ALL = R.id.pchlist_all;
    int HTTP_PCHLIST_ADD = R.id.pchlist_add;
    int HTTP_PCHLIST_DEL = R.id.pchlist_del;

    int HTTP_NEWS_DETAIL = R.id.news_detail;

    int HTTP_TASK_USERADDLIST = R.id.task_useraddlist;
    int HTTP_TASK_USERADD = R.id.task_useradd;
    int HTTP_TASK_USERDEL = R.id.task_userdel;
    int HTTP_TASK_USEREDIT = R.id.task_useredit;
    int HTTP_TASK_USERCOM = R.id.task_usercom;
    int HTTP_TASK_MY = R.id.task_my;
    int HTTP_SPEECH_ADD = R.id.speech_add;
    int HTTP_SPEECH_DEL = R.id.speech_del;
    int HTTP_SPEECH_LIST = R.id.speech_list;

    int HTTP_BUDGET_ALL = R.id.budget_all;
    int HTTP_BUDGET_GET = R.id.budget_get;
    int HTTP_BUDGET_EDIT = R.id.budget_edit;
    int HTTP_BUDGET_COMPUTE = R.id.budget_compute;

    int HTTP_FORTUNE_FIND = R.id.fortune_find;

    int HTTP_CARD_SAVE = R.id.card_save;
    int HTTP_CARD_INFO = R.id.card_info;
    int CARD_GETINFO = R.id.card_getinfo;

    int HTTP_GUESTLIST_ALL = R.id.guestlist_all;
    int HTTP_GUESTLIST_ADD = R.id.guestlist_add;
    int HTTP_GUESTLIST_DEL = R.id.guestlist_del;

    int HTTP_PRODUCT_CATE = R.id.product_cate;
    int HTTP_PRODUCT_BYTYPE = R.id.product_bytype;
    int HTTP_PRODUCT_DETAIL = R.id.product_detail;
    int HTTP_PRODUCT_COMMENT = R.id.product_comment;
    int HTTP_PRODUCT_FAVORITE = R.id.product_favorite;
    int HTTP_PRODUCT_FINDCOLLECT = R.id.product_findfavorite;
    int HTTP_PRODUCT_FINDCOMMENT = R.id.product_findcomment;

    int HTTP_TOPIC_ADD_EDIT = R.id.topic_addedit;
    int HTTP_TOPIC_BEST = R.id.topic_best;
    int HTTP_TOPIC_LATEST = R.id.topic_latest;
    int HTTP_TOPIC_INFO = R.id.topic_info;
    int HTTP_TOPIC_COLLECT = R.id.topic_collect;
    int HTTP_TOPIC_DELCOLLECT = R.id.topic_delcollect;
    int HTTP_TOPIC_COMMENT = R.id.topic_comment;
    int HTTP_TOPIC_COMMENT_LIST = R.id.topic_comment_list;

    int HTTP_PHASES_ALL = R.id.phases_all;

    int HTTP_TAGS_INPHASE = R.id.tags_inphase;
    int HTTP_TAGS_ADDONE = R.id.tags_addone;

    int HTTP_TOPIC_MYALL = R.id.topic_myall;
    int HTTP_TOPIC_MYCOLLECT = R.id.topic_mycollect;


    int HTTP_HOME_PAGE = R.id.home_page;

    int HTTP_SHOP_LIST = R.id.shop_list;
    int HTTP_SHOP_INFO = R.id.shop_info;
    int HTTP_SHOP_PACKAGE = R.id.shop_package;
    int HTTP_SHOP_FOLLOW = R.id.shop_follow;
    int HTTP_SHOP_DIS_FOLLOW = R.id.shop_dis_follow;
    int HTTP_SHOP_DEMO = R.id.shop_demo;
    int HTTP_SHOP_AD_LIST = R.id.shop_ad_list;
    int HTTP_SHOP_PACKAGE_INFO = R.id.shop_package_info;
    int HTTP_SHOP_DEMO_INFO = R.id.shop_demo_info;
    int HTTP_SHOP_MY_COLLECT = R.id.shop_my_collect;

    int HTTP_CITY_COUNTY = R.id.city_county;
    int HTTP_APPOINTMENT = R.id.shop_appointment;

    int HTTP_SHOP_COMMENT_LIST = R.id.shop_comment_list;
    int HTTP_SHOP_COMMENT_ADD = R.id.shop_comment_add;


    int HTTP_CHECK_VERSION = R.id.check_version;

}
