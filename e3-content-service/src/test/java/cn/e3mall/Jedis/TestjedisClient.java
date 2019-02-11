
package cn.e3mall.Jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.e3mall.common.jedis.JedisClient;

/**  

* <p>Title: TestjedisClient</p>  

* <p>Description: </p>  

* @author zty  

* @date 2018年10月12日  

*/
public class TestjedisClient {
		@Test
		public void test() throws Exception{
			//初始化spring容器
			ApplicationContext ap = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
			JedisClient jedisClient = ap.getBean(JedisClient.class);
			jedisClient.set("testclient", "jedisClient");
			String string = jedisClient.get("testclient");
			System.out.println(string);
		

		}
}
