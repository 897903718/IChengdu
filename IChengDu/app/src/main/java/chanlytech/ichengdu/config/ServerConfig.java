package chanlytech.ichengdu.config;

/**
 * Created by Lyy on 2015/7/6.
 * 服务器接口
 */
public class ServerConfig {
    public static final String BASE_URL = "http://aichengdu.chanlytech.com:8088/index.php/";

    /**
     * 获取首页服务更新信息
     */
    public static final String UPDATE_SERVE = "Home/Home/updateServe";

    /**
     * 获取首页数据
     */
    public static final String GET_HOME_LIST_DATA = "Home/Home/getHomeList";

    /**
     * 获取活动数据
     */
    public static final String GET_ACTIVITY_LIST_DATA = "/Activity/Activity/getActivityList";

    /**
     * 获取业务数据
     */
    public static final String GET_BUSINESS_DATA = "Fw/Fwdetail/Fdetail";

    /**
     * 获取商品详情信息
     */
    public static final String GET_GOODS_DETAIL = "Fw/Fwdetail/goodsDetail";

    /**
     * 提交订单
     */
    public static final String POST_GOODS_ORDER = "Fw/Fwdetail/putOrder";

    /**
     * 咨询首页数据
     */
    public static final String GET_INFOR_DATA = "Fw/Flzx/getHomeList";

    /**
     * 提交咨询问题
     */
    public static final String COMMENT_PROBLEM = "Fw/Flzx/putContent";

    /**
     * 获取咨询问题列表
     */
    public static final String GET_CHAT_LIST = "Fw/Flzx/getFeedbackList";
    /**
     * 获取短信验证码
     **/
    public static final String GET_SMS_CODE = "User/User/sendCode";

    /**
     * 注册接口
     **/
    public static final String USER_REGISTER = "User/User/register";
    /**
     * 用户登陆接口
     */
    public static final String USER_LOGIN = "User/User/login";
    /**
     * 获取用户菜单（如是否有新的订单、活动等）
     */
    public static final String GET_USER_NEWS_INFO = "User/User/getUserData";

    /**
     * 提交订单
     */
    public static final String SUBMIT_ORDER = "Fw/Fwdetail/putOrder";

    /**
     * 验证优惠码
     */
    public static final String CHECK_COUPON_CODE = "User/User/CheckCouponCode";

    /**
     * 用户信息
     */
    public static final String GET_USER_INFO = "User/User/getUserInfo";

    /**
     * 订单列表
     */
    public static final String ORDER_LIST = "Order/Order/getOrderList";
    /**
     * 套餐列表
     */
    public static final String TAO_CAN_LIST = "Taocan/Taocan/taocanList";
    /**
     * 提交套餐订单
     */
    public static final String SUBMIT_TAOCAN_ORDER = "Taocan/Taocan/putTaocanOrder";
    /**
     * 法律资讯----获取栏目列表
     */
    public static final String GET_Category_LIST = "News/News/getCategoryList";

    /**
     * 法律资讯----获取新闻和广告列表
     */
    public static final String GET_POSTER_NEWS = "News/News/getPosterAndNews";
    /**
     * 上传用户头像
     */
    public static final String UPDATE_Avatar = "User/User/uploadAvatar";
    /**
     * 修改用户信息
     */
    public static final String MODIFY_USER_INFO = "User/User/modifyUserInfo";
    /**
     * 修改密码
     */
    public static final String CHANGE_PWD = "User/User/modifyPassword";

    /**
     * 获取城市列表
     */
    public static final String GET_CITY_LIST = "City/City/getCityList";

    /**
     * 意见反馈
     */
    public static final String SUBMIT_FEEBACK = "Feedback/Feedback/submitFeedback";

    /**
     * 版本更新
     */
    public static final String UPDATE = "Version/Version/update";

    /**
     * 获取首页启动图
     */
    public static final String START_IMG = "Startimg/Startimg/getList";

    /**
     * 获取分享信息
     * */
    public static final String getShareData="Share/Share/getShareData";
    /**
     * 获取评论列表
     * */
    public static final String getCommentList="News/News/commentList";
    /**
     * 案列点赞
     *
     * */
    public static  final  String commentZan   ="News/News/commentZan";
    /**
     * 案例评论提交
     * */
    public static  final  String commentSub   ="News/News/commentSub";

    /**
     * 有无更新查询接
     *
     * */
    public static final String isUpdateWifiList="Ap/Ap/isUpdateWifiList";
    /**
     * 获取wifi列表
     * */
    public static final String getWifiList="Ap/Ap/getWifiList";
}
