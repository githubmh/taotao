package com.taotao.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class TestJedis {
	@Test
	public void testJedis() throws Exception{
		//创建一个jedis对象，需要指定服务的IP和端口号
		Jedis jedis = new Jedis("192.168.156.13", 6379);
		//直接操作数据库
		jedis.set("jedis-key", "1234");
		String result = jedis.get("jedis-key");
		System.out.println(result);
		//关闭jedis
		jedis.close();
	}
	@Test
	public void testJedisPool() throws Exception{
		//创建一个数据库连接池对象 需要指定服务的IP和端口号
		JedisPool jedisPool = new JedisPool("192.168.156.13", 6379);
		//从连接池中获得连接
		Jedis jedis = jedisPool.getResource();
		//使用jedis操作数据库（方法级别使用）
		String result = jedis.get("jedis-key");
		System.out.println(result);
		//一定关闭jedis连接
		jedis.close();
		//系统关闭前关闭连接池
		jedisPool.close();
	}
	@Test
	public void testJedisCluster() throws Exception{
		//创建一个JedisCluster对象，构造参数set类型，集合中每个元素是hostandport类型
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.156.13", 7001));
		nodes.add(new HostAndPort("192.168.156.13", 7002));
		nodes.add(new HostAndPort("192.168.156.13", 7003));
		nodes.add(new HostAndPort("192.168.156.13", 7004));
		nodes.add(new HostAndPort("192.168.156.13", 7005));
		nodes.add(new HostAndPort("192.168.156.13", 7006));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		//直接使用JedisCluster操作redis，自带连接池。jediscluster对象可以是单例的。
		jedisCluster.set("cluster-test", "hello jedis cluster");
		String string = jedisCluster.get("cluster-test");
		System.out.println(string);
		//系统关闭前关闭jediscluster
		jedisCluster.close();
	}
}
