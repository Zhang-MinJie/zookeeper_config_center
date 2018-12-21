package cn.zhmj.zkexample.handler;

public enum ResultCode {
	
	SUCCESS						("0000", "请求成功"),
	ERROR						("1111", "未知异常"),
	HTTP_ERROR_404				("1001", "请求地址不存在"),
	WITHOUT_PERMISSION_ERROR	("1002", "暂无权限"),
	PARAMS_ERROR_1				("0001", "参数解析异常"),
	PARAMS_ERROR_2				("0002", "参数异常"),
	REQUEST_METHOD_ERROR		("0003", "请求方式异常"),
	JSON_ERROR					("0004", "JSON解析异常"),
	REDIS_ERROR					("0005", "Redis异常"),
	REQUEST_WAIT				("2001", "系统繁忙，请稍后再试");
	
	ResultCode(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	private String code;
	private String message;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
