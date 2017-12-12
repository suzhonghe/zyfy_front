package com.zhongyang.java.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.pojo.Article;
import com.zhongyang.java.zyfyfront.vo.ArticleVO;

/**
 * 
* @Title: ArticleDao.java 
* @Package com.zhongyang.java.dao 
* @Description: 
* @author 苏忠贺   
* @date 2016年3月3日 下午1:34:23 
* @version V1.0
 */
public interface ArticleDao {
	
	public List<Article> selectByParams(Page<ArticleVO> page);
	
	public Article getArticleById(String id);
	//获得文章的上下片
	public List<Article> getBeforAndAfter(@Param("columnId") String columnId,@Param("preIndex") int preIndex,@Param("surIndex") int surIndex);
}
