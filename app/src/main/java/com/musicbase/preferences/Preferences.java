package com.musicbase.preferences;

import com.google.gson.Gson;
import com.musicbase.MyApplication;
import com.musicbase.R;
import com.musicbase.entity.AppInfo;

public class Preferences {

    // 正式服务器
//    public static final String BASE_URL_HTTP = "https://yinji.pndoo.com/pndoo_server";
    public static final String BASE_URL_HTTP = "https://www.yinyuesuyang.com/pndoo_server";
//    public static final String BASE_URL_HTTP = "http://101.200.74.204:8080/pndoo_server";
//    public static final String BASE_URL_HTTP ="http://tianbao.isli.info/pndo3。41
//    o_server";

    //广告接入
//    public static final String APPID = "1101152570";
    public static final String APPID = "1110090297";
    //    public static final String SplashPosID = "8863364436303842593";
    public static final String SplashPosID = "5060495620707639";


    public static final String LOGIN_URL = BASE_URL_HTTP + "/login.json?_method=login";// 登录
    public static final String WEIXIN_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";// 微信获取token
    public static final String WEIXIN_USERINFO = "https://api.weixin.qq.com/sns/userinfo";// 微信获取userinfo
    public static final String LOGOUT_URL = BASE_URL_HTTP + "/login.json?_method=logout";//退出登录
    public static final String RESET_PASSWORD = BASE_URL_HTTP + "/user.json?_method=resetPwd";// 忘记密码
    public static final String REGINST_URL = BASE_URL_HTTP + "/user.json?_method=register";// 注册
    public static final String GET_BIND_METHOD_URL = BASE_URL_HTTP + "/user.json?_method=getUserauthsList";// 获取绑定列表
    public static final String ADD_BIND_URL = BASE_URL_HTTP + "/user.json?_method=insertUserAuths";// 添加绑定类型
    public static final String REMOVE_BIND_URL = BASE_URL_HTTP + "/user.json?_method=unbindUserAuths";// 解除绑定类型
    public static final String VERISON_UPDATE_URL = BASE_URL_HTTP + "/login.json?_method=checkVersion";//版本更新
    public static final String UNBIND_URL = BASE_URL_HTTP + "/user.json?_method=unbind";//解绑
    public static final String BIND_TEL_URL = BASE_URL_HTTP + "/user.json?_method=bindTelPhone";//绑定手机
    public static final String SEND_SMS = BASE_URL_HTTP + "/message.json?_method=message";//短信
    public static final String HOME_INFO = BASE_URL_HTTP + "/course.json?_method=getHomeCourseList";//首页信息
    public static final String HOME_MORE_INFO = BASE_URL_HTTP + "/course.json?_method=getSystemCodeDetail";//首页栏目更多
    public static final String IMAGE_HTTP_LOCATION = BASE_URL_HTTP + "/course.json?_method=getImg&imagePath=";// 图片地址
    public static final String COURSE_DETAIL = BASE_URL_HTTP + "/course.json?_method=getCourseDetail";// 图片地址
    public static final String GET_FOLDER = BASE_URL_HTTP + "/course.json?_method=getFolderResouceList";// 获取文件夹资源列表
    public static final String ADD_YUEXUE = BASE_URL_HTTP + "/course.json?_method=addCourseToYueXue";// 添加到乐学
    public static final String DELETE_YUEXUE = BASE_URL_HTTP + "/attachorder.json?_method=deleteYuexue";// 删除乐学
    public static final String BUY_ORDER = BASE_URL_HTTP + "/attachorder.json?_method=addAttachOrder";// 下订单
    public static final String COUNT_POSTAGE = BASE_URL_HTTP + "/attachorder.json?_method=countPostage";// 计算运费
    public static final String ALIPAY = BASE_URL_HTTP + "/attachorder.json?_method=aliPay";
    public static final String ORDER_RECORDE = BASE_URL_HTTP + "/attachorder.json?_method=AttachOrderRecord";//订单查询
    public static final String WXPAY = BASE_URL_HTTP + "/attachorder.json?_method=weChatPay";
    public static final String FILE_DOWNLOAD_URL = BASE_URL_HTTP + "/dFile?_method=downLoadFile&filePath=";// 下载
    public static final String BUYED = BASE_URL_HTTP + "/attachorder.json?_method=yuexuePage";//乐学
    public static final String ADDRESS_LIST = BASE_URL_HTTP + "/express.json?_method=getExpressList";//获取地址列表
    public static final String ADDRESS_ADD = BASE_URL_HTTP + "/express.json?_method=addExpress";//新增地址
    public static final String ADDRESS_UPDATE = BASE_URL_HTTP + "/express.json?_method=updateExpress";//修改地址
    public static final String ADDRESS_DELETE = BASE_URL_HTTP + "/express.json?_method=deleteExpress";//删除地址
    public static final String CONTRACT_USER = "https://www.yinyuesuyang.com/contract.html";//用户协议地址
    public static final String CHECK_CARD = BASE_URL_HTTP + "/attachorder.json?_method=checkCardIsNeedPwd";//输入实体卡号
    public static final String COURSE_CARD = BASE_URL_HTTP + "/attachorder.json?_method=openCourseByCard";//实体卡号卡密添加课程
    public static final String ADD_REGISTER_ID = BASE_URL_HTTP + "/user.json?_method=addRegistrationId";//注册极光推送id

    public static String appInfo;


    public final static int SHOW_PROGRESS_DIALOG = 11;
    public final static int SHOW_ERROR_DIALOG = 12;
    public final static int SHOW_CONFIRM_DIALOG = 13;
    public final static int SHOW_SUCCESS_DIALOG = 14;
    public final static int SHOW_BUTTON_DIALOG = 15;
    public final static int SHOW_JIESUO_DIALOG = 18;
    public final static int SHOW_PROGRESS_DIALOG_NO = 23;


    public final static int REGIST_CODE = 111;
    public final static int BIND_PHONE_CODE = 112;


    /**
     * weixin appid
     */
//    public static final String WX_APP_ID = "wx16b40680fdfc9ea4";
//    public static final String WX_APP_SECRET = "b5e6babf43c1ceece044afc3ac98c14e";
    /**
     * weixin appid 音为乐
     */
    public static final String WX_APP_ID = "wxbbf878a36afb3fee";
    public static final String WX_APP_SECRET = "c3d6b8fd0ea43706279f4c3f8c107428";
    /**
     * qq appid
     */
    public static final String QQ_APP_ID = "1106701619";
    public static final String QQ_APP_KEY = "HCbcMSLiJ0yPJnoI";
    /**
     * tencent cloud
     */
    public final static int DEFAULT_APPID = 1252077913;


    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";

    public static final String phoneMatcher = "^[1][3456789][0-9]{9}$";
    public static boolean isNotMusic = true;

    public static String MUSIC_NAME = "";
    public static String MUSIC_TITLE = "";
    public static String MUSIC_ID = "";

}