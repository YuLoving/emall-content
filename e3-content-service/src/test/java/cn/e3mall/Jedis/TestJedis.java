
package cn.e3mall.Jedis;

import java.util.HashSet;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**  

* <p>Title: TestJedis</p>  

* <p>Description: </p>  

* @author zty  

* @date 2018年10月12日  

*/
public class TestJedis {
		
		/*
		 * 单机版Redis的测试
		 * */

		@Test
		public void testjedis()throws Exception{
			//创建一个连接Jedis对象，参数 host  post
			Jedis jedis = new Jedis("192.168.25.129", 6379);
			//直接用jedis操作redis,所有的Jedis命令对应一个方法
			jedis.set("test123", "my first jedis test");
			String string = jedis.get("test123");
			System.out.println(string);
			//关闭连接
			jedis.close();
		}
		
		
		/*
		 * 使用Jedis的连接池
		 * */
		@Test
		public void testJedisPool() throws Exception{
			//创建一个JEDis的连接池对象，两个参数host post
			JedisPool jedisPool = new JedisPool("192.168.25.129", 6379);
			//从连接池中获得一个连接，就是一个jedis对象
			Jedis jedis = jedisPool.getResource();
			//用jedis操作redis
			jedis.set("testpool", "my first testredispool");
			String string = jedis.get("testpool");
			System.out.println(string);
			//关闭连接，每次使用完毕后关闭连接，连接池回收资源
			jedis.close();
			//关闭连接池
			jedisPool.close();
		}
		
		/*
		 * 使用redis集群时候的连接
		 * */
		@Test
		public void testJediscluster()throws Exception{
			//创建一个JedisCluster对象，有一个参数nodes是set类型，set中包含若干个hostandpost对象
			HashSet<HostAndPort> nodes = new HashSet<>();
			nodes.add(new HostAndPort("192.168.25.129", 7001));
			nodes.add(new HostAndPort("192.168.25.129", 7002));
			nodes.add(new HostAndPort("192.168.25.129", 7003));
			nodes.add(new HostAndPort("192.168.25.129", 7004));
			nodes.add(new HostAndPort("192.168.25.129", 7005));
			nodes.add(new HostAndPort("192.168.25.129", 7006));
			JedisCluster jedisCluster = new JedisCluster(nodes);
			//使用jedisCluster操作Redis
			jedisCluster.set("testjedisCluster", "123");
			String string = jedisCluster.get("testjedisCluster");
			System.out.println(string);
			//关闭连接
			jedisCluster.close();
			
			
		}
}
