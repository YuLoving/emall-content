
package cn.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;


/**  

* <p>Title: ContentCategoryServiceImpl</p>  

* <p>Description: 内容分类管理service</p>  

* @author zty  

* @date 2018年10月10日  

*/
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentcategorylist(long parentId) {
		// 1、取查询参数id，parentId
		// 2、根据parentId查询tb_content_category，查询子节点列表。
		TbContentCategoryExample example = new TbContentCategoryExample();
		//设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		// 3、得到List<TbContentCategory>
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		// 4、把列表转换成List<EasyUITreeNode>
		
		List<EasyUITreeNode> resultlist=new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			//添加到列表
			resultlist.add(node);
		}
		return resultlist;
	}

	/*
	 * 添加内容分类
	 */
	@Override
	public E3Result addContentcategroy(long parentId, String name) {
		// 创建tb_content_category表的pojo对象
		TbContentCategory contentCategory = new TbContentCategory();
		// 设置属性，id就可以不需要了，mapper中已经设置为插入便生成ID
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		contentCategory.setStatus(1);//可选值:1(正常),2(删除)',
		//默认排序为1
		contentCategory.setSortOrder(1);
		//新添加的节点一定是叶子节点
		contentCategory.setIsParent(false);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		// 插入到数据库
		contentCategoryMapper.insert(contentCategory);
		// 判断父节点的isparent的属性，如果不是true则改为true
				//可以根据parentId来查询父节点
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent()){
			parent.setIsParent(true);
			//更新到数据库
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		// 返回 E3Result，包含pojo
		
		return E3Result.ok(contentCategory);
	}

	/* 
	 * 修改内容分类名称
	 */
	@Override
	public E3Result editContentcategory(long id, String name) {
		//根据ID查到这条记录
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		//修改对应的name
		contentCategory.setName(name);
		//更新到数据库
		contentCategoryMapper.updateByPrimaryKey(contentCategory);
		// 返回 E3Result，包含pojo
		return E3Result.ok(contentCategory);
	}

	/* 
	 * 删除内容分类
	 */
	@Override
	public E3Result deleContentcategory(long id) {
		//先通过ID找到这条记录
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		//判断父类目是否为false
		if (!contentCategory.getIsParent()) {
			//不是父类目 ，直接删除
			contentCategoryMapper.deleteByPrimaryKey(id);
			// 判断父节点的isparent的属性，如果不是true则改为true
			//可以根据contentCategory.parentId来查询父节点
			//List<TbContentCategory> parentlist = (List<TbContentCategory>) contentCategoryMapper.selectByPrimaryKey(contentCategory.getParentId());
			/*//判断该父节点还有没有子节点，如果没有，设置isparent的属性为false。否则还是true
				if(parentlist.size()>1){
					//该父节点有多个节点，则isparent的属性仍然为true
					return E3Result.ok();
				}else{
					
						parentlist.get(0).setIsParent(false);
						//更新到数据库
						TbContentCategory  partent=	(TbContentCategory)parentlist.get(0);
					//contentCategoryMapper.updateByPrimaryKey(partent);*/
					return E3Result.ok();
				
		} else {
			//是父类目 
			return E3Result.ok();
		}
		
	
	}

}
