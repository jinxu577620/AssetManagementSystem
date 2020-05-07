package com.example.mphigh.result;

public enum CodeMsg {
	//通用的错误码
	SUCCESS(0, "success"),
	NO_TOKEN(500001, "无token，请重新登录"),
	NO_AUTHORITY(500002, "没有访问权限，请重新登录"),
	TOKEN_EXPIRED(500003, "Token已过期，请重新登录"),

	SERVER_ERROR(500100, "服务端异常"),
	BIND_ERROR(500101, "参数校验异常：%s"),
	PARAM_ERROR(500102, "参数不正确"),

	//登录模块 5002XX
	SESSION_ERROR(500210, "Session不存在或者已经失效"),
	PASSWORD_EMPTY(500211, "登录密码不能为空"),
	MOBILE_EMPTY(500212, "手机号不能为空"),
	MOBILE_ERROR(500213, "手机号格式错误"),
	MOBILE_NOT_EXIST(500214, "手机号不存在"),
	PASSWORD_ERROR(500215, "密码错误"),
	OLD_PASSWORD_ERROR(500215, "旧密码错误"),
	CODE_ERROR(500216, "验证码错误"),
	METHOD_IS_USED(500217, "支付方式已注册"),
	MOBILE_IS_NOT_REGISTER(500218, "手机号尚未注册，请先进行注册"),
	MAIL_IS_USED(500217, "邮箱已使用"),

	NO_USER(500220, "用户不存在，请重新登录"),
	NOT_CURRENT_USER(500221, "不是当前用户，请重新操作"),
	FREEZE_OR_DELETED(500222, "该用户被冻结或者已注销，不能登录，请联系管理员"),

	CAN_NOT_DELETE_ADMIN(500220, "不能删除管理员用户"),
	CAN_NOT_DELETE_USER(500220, "用户还有操作的订单，不能删除用户"),

	GET_OSS_TOKEN_ERROR(500501, "OssToken获取失败"),

	;
	private Integer code;
	private String msg;

	CodeMsg(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public CodeMsg fillArgs(Object... args) {
		this.msg = String.format(this.msg, args);
		return this;
	}
	@Override
	public String toString() {
		return "CodeMsg [code=" + code + ", msg=" + msg + "]";
	}
	
	
}
