package cn.zhmj.zkexample.exception;
/**
 * 请求等待异常
 * @author 章敏杰
 * @createdTime 2018年12月12日
 */
public class RequestWaitException extends Exception {
	private static final long serialVersionUID = 1L;

	public RequestWaitException(String message) {
		super(message);
	}
}
