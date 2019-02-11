/**
 * 
 */
package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbContent;

/**  

* <p>Title: ContentService</p>  

* <p>Description: </p>  

* @author zty  

* @date 2018年10月11日  

*/
public interface ContentService {
		E3Result addcontent(TbContent content);
		
		
		List<TbContent> getContentlistbycid(long cid);
		
}
