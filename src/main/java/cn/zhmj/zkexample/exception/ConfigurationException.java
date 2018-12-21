package cn.zhmj.zkexample.exception;

public class ConfigurationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ConfigurationException(String message) {
		super("系统配置异常：" + message);
	}
}
