package com.mksoft.shop;

public class Constants {

	/**
	 * 删除标识：0-未删除, -1-已删除
	 */
	public static final Integer VER_MINUS_1 = -1;
	public static final Integer VER_0 = 0;

	public static final String LOGIN_PKID_KEY = "loginPkid";
	public static final String LOGIN_UUID_KEY = "loginUUID";
	public static final String LOGIN_LOGIN_KEY = "login";

	public static final String QINIU_SPACE_PICTURE = "xl-image";	//qiniu图片空间名
	public static final String QINIU_SPACE_VIDEO = "xl-video"; 	//qiniu视频空间名
	public static final String QINIU_SPACE_PORTRAIT = "xl-portrait";	//qiniu头像空间名
	public static final String QINIU_SPACE_AUDIO = "xl-audio"; 	//qiniu音频空间名

	public static final String SYS_NAME_KEY = "SYS_NAME";
	public static final String SYS_NAME_APP = "APP";
	public static final String SYS_NAME_ADMIN = "ADMIN";

	public static final String PRODUCT_BASE_CATEGORY_DISPLAY_FLAG_KEY = "productBaseCategoryDisplayFlagKey"; //商品品类类型在微商城主页上显示
	public static final Integer PRODUCT_BASE_CATEGORY_DISPLAY_FLAG_VALUE_SHOW = 1; //商品品类类型在微商城主页上显示
	public static final Integer PRODUCT_BASE_CATEGORY_DISPLAY_FLAG_VALUE_HIDE = 0; //商品品类类型在微商城主页上不显示

	public static final String WECHAT_UNIONID = "unionId"; //微信返回的unionid参数ID
	public static final String WECHAT_SYSTEM = "wechat"; //更新数据库cid,uid用的id
	public static final String URL_WECHAT_GET_SESSIONKEY_AND_OPENID_BY_CODE = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={js_code}&grant_type=authorization_code"; //微信返回的unionid参数ID
	public static final String WECHAT_APP_ID = "wx2435b569a2ba2693"; //微信小程序APPID
	public static final String WECHAT_APP_SECRET = "260dcb3ffcbfa40bf8623a814d69a199"; //微信小程序APPSECRET

	public static final String CUSTOMER_DEFAULT_PASSWORD = "888888"; //默认customer表的密码

	public static final String EXCEL_FILE_NAME = "fileName";
	public static final String EXCEL_HEAD_NAME = "head";
	public static final String EXCEL_LIST_NAME = "list";
}
