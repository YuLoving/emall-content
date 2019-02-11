
package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;

/**  

* <p>Title: ContentCategoryService</p>  

* <p>Description: </p>  

* @author zty  

* @date 2018年10月10日  

*/
public interface ContentCategoryService {

	//内容分类
	List<EasyUITreeNode> getContentcategorylist(long parentId);
	
	//添加内容分类
	E3Result addContentcategroy(long parentId,String name);
	
	//修改内容分类名称
	E3Result editContentcategory(long id,String name);
	
	//删除内容分类
	E3Result deleContentcategory(long id);
	
}
