package cn.zhmj.zkexample.configuration.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;

import cn.zhmj.zkexample.configuration.redis.util.ClusterUtils;

@Configuration
@ConditionalOnExpression("${spring.redis.config-type} == 1")
public class RedisClusterZooKeeperCentralConfigurer extends ZooKeeperCentralConfigurer {
	private final static Logger logger = LoggerFactory.getLogger(RedisClusterZooKeeperCentralConfigurer.class);

	private ApplicationContext applicationContext;

	public RedisClusterZooKeeperCentralConfigurer(
			@Value("${spring.zookeeper.conns}") String conns,
			@Value("${spring.zookeeper.session-timeout}") String sessionTimeout,
			@Value("${spring.zookeeper.config.redis}") String path,
			ApplicationContext applicationContext
	) throws Exception {
		super(conns, path, Integer.valueOf(sessionTimeout));
		logger.info("[zookeeper][redis][config] - load");
		this.applicationContext = applicationContext;
		buildListener();
	}

	private void buildListener() throws Exception {
		TreeCacheListener listener = new TreeCacheListener() {
			@Override
			public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
				if (event.getType() == TreeCacheEvent.Type.NODE_UPDATED) {
					logger.info("[zookeeper][redis][config] - reload");
					loadData();
					refreshSystemProperties();
					refreshRedisClusterConfiguration();
				}
			}
		};
		super.addListener(listener);
	}

	private void refreshRedisClusterConfiguration() {
		// refresh RedisClusterConfiguration
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext
				.getAutowireCapableBeanFactory();
		BeanDefinitionBuilder redisClusterConfigurationBeanDefinitionBuilder = BeanDefinitionBuilder
				.rootBeanDefinition(RedisClusterConfiguration.class.getName());
		String nodes = System.getProperty("spring.redis.cluster.nodes");
		redisClusterConfigurationBeanDefinitionBuilder.addPropertyValue("clusterNodes",
				ClusterUtils.getClusterNodes(nodes));
		defaultListableBeanFactory.registerBeanDefinition("redisClusterConfiguration",
				redisClusterConfigurationBeanDefinitionBuilder.getBeanDefinition());
		logger.info("[redis][config] - register success.");
	}
}
