package cn.zhmj.zkexample.configuration.redis.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.redis.connection.RedisNode;

public class ClusterUtils {
	/**
	 * 获取ClusterNodes
	 * @param nodes 例：url:port,url:port
	 * @return
	 */
	public static Set<RedisNode> getClusterNodes(String nodes) {
		// 获取服务器数组(这里要相信自己的输入，所以没有考虑空指针问题)
		String[] serverArray = nodes.split(",");
		Set<RedisNode> nodesSet = new HashSet<RedisNode>();
		for (String ipPort : serverArray) {
			String[] ipPortPair = ipPort.split(":");
			String host = ipPortPair[0];
			Integer port = Integer.valueOf(ipPortPair[1]);
			RedisNode node = new RedisNode(host, port);
			nodesSet.add(node);
		}
		return nodesSet;
	}
}
