package ws.hoyland.sszs;

public class EngineMessageType {
	//incoming message
	public static final int IM_CONFIG_UPDATED = 0x1010; //配置更新
	public static final int IM_USERLOGIN = 0x1011; //UU用户登录
	public static final int IM_UL_STATUS = 0x1012; //登录结果
	public static final int IM_LOAD_ACCOUNT = 0x1013; //加载帐号文件
	public static final int IM_LOAD_MAIL = 0x1014; //加载邮件列表
	public static final int IM_CAPTCHA_TYPE = 0x1015;
	public static final int IM_PROCESS = 0x1016;
	
	//outgoing message
	public static final int OM_LOGINING = 0x2010;
	public static final int OM_LOGINED = 0x2011;
	public static final int OM_CLEAR_ACC_TBL = 0x2012;
	public static final int OM_ADD_ACC_TBIT = 0x2013;
	public static final int OM_ACCOUNT_LOADED = 0x2014;
	
	public static final int OM_CLEAR_MAIL_TBL = 0x2015;
	public static final int OM_ADD_MAIL_TBIT = 0x2016;
	public static final int OM_MAIL_LOADED = 0x2017;
	
	public static final int OM_READY = 0x2018;
	public static final int OM_UNREADY = 0x2019;
	public static final int OM_LOGIN_ERROR = 0x2020;
	public static final int OM_RUNNING = 0x2021;
}