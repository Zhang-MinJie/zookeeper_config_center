package cn.zhmj.zkexample.configuration.redis;

import java.util.Set;

import javax.naming.ConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.context.support.StandardServletEnvironment;

import cn.zhmj.zkexample.configuration.ConfigType;
import cn.zhmj.zkexample.configuration.redis.util.ClusterUtils;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration {
	private final static Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);

    private StandardServletEnvironment env;

	public RedisConfiguration(StandardServletEnvironment env) throws ConfigurationException {
		logger.info("[redis][config] - init.");
		this.env = env;
		this.logConfigType();
	}

	private void logConfigType() throws ConfigurationException {
		String configType = env.getProperty("spring.redis.config-type");
		if(ConfigType.local.getCode().equals(configType)) {
			logger.info("[redis][config] - load {}.", ConfigType.local.getName());
		} else if(ConfigType.zookeeper.getCode().equals(configType)) {
			logger.info("[redis][config] - load {}.", ConfigType.zookeeper.getName());
		} else {
			throw new ConfigurationException("Redis 配置异常");
		}
	}

	@Bean(name = "jedisPoolConfig")
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		return config;
	}

	@Bean(name = "redisClusterConfiguration")
	public RedisClusterConfiguration redisClusterConfiguration() {
		RedisClusterConfiguration config = new RedisClusterConfiguration();
		String nodes = env.getProperty("spring.redis.cluster.nodes");
		logger.info("[redis][config] - nodes -> {}", nodes);
		Set<RedisNode> nodesSet = ClusterUtils.getClusterNodes(nodes);
		config.setClusterNodes(nodesSet);
		return config;
	}

	@Bean(name = "jedisConnectionFactory")
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory(redisClusterConfiguration());
		return factory;
	}

	@Bean(name = "redisTemplate")
	public RedisTemplate<?, ?> redisTemplate() {
		RedisTemplate<?, ?> template = new StringRedisTemplate(jedisConnectionFactory());
		return template;
	}
}
