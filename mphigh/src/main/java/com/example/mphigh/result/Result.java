package com.example.mphigh.result;


/**
 * 响应对象
 */
public class Result<T> {

	private Integer code;
	private String msg;
	private T data;

	private Result() {
		this.code = CodeMsg.SUCCESS.getCode();
		this.msg = CodeMsg.SUCCESS.getMsg();
	}

	private Result(T data) {
		this.code = CodeMsg.SUCCESS.getCode();
		this.msg = CodeMsg.SUCCESS.getMsg();
		this.data = data;
	}

	private Result(String msg, T data) {
		this.code = CodeMsg.SUCCESS.getCode();
		this.msg = msg;
		this.data = data;
	}

	private Result(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private Result(CodeMsg codeMsg) {
		if(codeMsg != null) {
			this.code = codeMsg.getCode();
			this.msg = codeMsg.getMsg();
		}
	}


	public boolean isSuccess(){
		return this.code == CodeMsg.SUCCESS.getCode();
	}

	public static <T> Result<T> success() {
		return new Result<>();
	}
	public static  <T> Result<T> success(T data){
		return new Result<T>(data);
	}
	public static  <T> Result<T> success(String msg, T data){
		return new Result<T>(msg, data);
	}

	public static  <T> Result<T> error(CodeMsg codeMsg){
		return new Result<T>(codeMsg);
	}
	public static  <T> Result<T> error(Integer code, String msg){
		return new Result<T>(code, msg);
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
