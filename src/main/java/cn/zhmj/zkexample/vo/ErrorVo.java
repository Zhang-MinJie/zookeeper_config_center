package cn.zhmj.zkexample.vo;

public class ErrorVo extends BaseVo {
	private String errorMsg = "未知异常";
	public ErrorVo() {
		setResultCode("1111");
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
