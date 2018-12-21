package cn.zhmj.zkexample.vo;

public class MessageVo extends SuccessVo {
	private Object message;
	
	public MessageVo() {
		setMessage("请求成功");
	}
	
	public MessageVo(Object message) {
		setMessage(message);
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
}
