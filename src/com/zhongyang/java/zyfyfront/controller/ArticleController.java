package com.zhongyang.java.zyfyfront.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.zyfyfront.biz.ArticleBiz;
import com.zhongyang.java.zyfyfront.returndata.ArticleReturn;
import com.zhongyang.java.zyfyfront.vo.ArticleVO;

/**
 * 
* @Title: ArticleController.java 
* @Package com.zhongyang.java.controller 
* @Description:CMS文章控制器
* @author 苏忠贺   
* @date 2016年3月3日 下午4:04:24 
* @version V1.0
 */
@Controller
public class ArticleController extends BaseController{
	
	@Autowired
	private ArticleBiz articleBiz;
	
	@RequestMapping(value="/front/cms/queryArticleByParams",method=RequestMethod.POST)
	public @ResponseBody ArticleReturn queryByParams(Page<ArticleVO> page){
		return articleBiz.queryByParams(page);
	}
	
	@RequestMapping(value="/front/cms/getArticleContent",method=RequestMethod.POST)
	@ResponseBody
	public ArticleReturn getArticleById(String id){
		return articleBiz.getArticleById(id);
	}
	
}
