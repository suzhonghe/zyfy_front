package com.zhongyang.java.zyfyfront.biz;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.returndata.ArticleReturn;
import com.zhongyang.java.zyfyfront.vo.ArticleVO;

/**
 * 
* @Title: ArticleBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:文章业务处理接口
* @author 苏忠贺   
* @date 2016年3月3日 下午3:08:54 
* @version V1.0
 */
public interface ArticleBiz {
	
	public ArticleReturn queryByParams(Page<ArticleVO> page);
	
	public ArticleReturn getArticleById(String id);


}
