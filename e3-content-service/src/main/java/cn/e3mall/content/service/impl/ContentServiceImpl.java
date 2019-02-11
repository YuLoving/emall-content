/**
 * 
 */
package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbContentExample.Criteria;

/**  

* <p>Title: ContentServiceImpl</p>  

* <p>Description: </p>  

* @author zty  

* @date 2018年10月11日  

*/
@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;
	/*
	 * 添加内容信息
	 */
	@Override
	public E3Result addcontent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		
		//缓存同步，就是删除缓存中对应的数据
		jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
		return E3Result.ok();
	}
	/* 
	 * 根据内容分类ID查询内容列表
	 */
	@Override
	public List<TbContent> getContentlistbycid(long cid) {
				//查询缓存
				try {
					String json = jedisClient.hget(CONTENT_LIST, cid + "");
					if(StringUtils.isNotBlank(json)){
						List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
						return list;
					}
					
					//如果缓存中有直接响应结果
				} catch (Exception e) {
					e.printStackTrace();
				}
				//如果缓存中有直接响应结果
				//如果没有则查询数据库
		//设置查询条件
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		//执行查询返回list
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
				//把结果添加到缓存
				try {
					jedisClient.hset(CONTENT_LIST, cid +"", JsonUtils.objectToJson(list));
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		return list;
	}

}
