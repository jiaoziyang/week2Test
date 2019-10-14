package com.jzy.demo;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.jzy.demo.bean.Goods;
import com.tzh.utils.IOToFileUtils;

public class TestDemo {

	// 注入
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Test
	public void SpiltFile() {
		
		//文件的地址
		String property = System.getProperty("user.dir") + "\\src\\test\\java\\testDemo.txt";
		
		//工具类读取文件  返回集合
		List<String> readFileByLinesList = IOToFileUtils.readFileByLinesList(property);
		
		//循环读取集合中的美容
		for (String oneGoods : readFileByLinesList) {
			
			//每循环一次创建一个对象
			Goods goods = new Goods();
			
			//用==切割字符串
			String[] split = oneGoods.split("==");
			
			//吧每个放进对象中
			goods.setGid(Integer.parseInt(split[0]));
			goods.setGname(split[1]);
			goods.setPrice(Double.parseDouble(split[2]));
			goods.setQz(split[3]);
			
			//redisTemplate.opsForList()
			ListOperations<String, Object> opsForList = redisTemplate.opsForList();
			
			//放到list中
			opsForList.leftPush("goodsList", goods);
		}
		
	}
	
}
