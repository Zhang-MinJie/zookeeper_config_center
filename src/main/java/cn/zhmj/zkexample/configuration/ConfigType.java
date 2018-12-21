package cn.zhmj.zkexample.configuration;

public enum ConfigType {
	local		("0", "本地配置"),
	zookeeper	("1", "ZooKeeper服务配置");

	private String code;
	private String name;
	
	ConfigType(String code, String name) {
		this.setCode(code);
		this.setName(name);
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
