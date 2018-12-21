package cn.zhmj.zkexample.configuration.zookeeper;

import java.util.List;
import java.util.Properties;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ZooKeeperCentralConfigurer {

    private final static Logger logger = LoggerFactory.getLogger(ZooKeeperCentralConfigurer.class);

    private String conns;
    private String path;
    private int sessionTimeout;
    private CuratorFramework client;
    private TreeCache treeCache;
    private Properties props;

	public Properties getProps() {
		return props;
	}

	public ZooKeeperCentralConfigurer(String conns, String path, int sessionTimeout) throws Exception {
		this.conns = conns;
		this.path = path;
		this.sessionTimeout = sessionTimeout;
		this.props = new Properties();
		initClient();
		loadData();
		refreshSystemProperties();
	}

	protected void initClient() {
		logger.info("[zookeeper] - init");
		client = CuratorFrameworkFactory.builder().connectString(conns).sessionTimeoutMs(sessionTimeout)
				.retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
		client.start();
		logger.info("[zookeeper] - init success.");
	}

	protected void loadData() throws Exception {
		List<String> list = client.getChildren().forPath(path);
		for (String key : list) {
			String value = new String(client.getData().forPath(path + "/" + key));
			if (value != null && value.length() > 0) {
				props.put(key, value);
			}
		}
	}

	protected void addListener(TreeCacheListener listener) throws Exception {
		treeCache = new TreeCache(client, path);
		treeCache.start();
		treeCache.getListenable().addListener(listener);
	}
	
	protected void refreshSystemProperties() {
		System.getProperties().putAll(props);
	}
}
