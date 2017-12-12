package com.zhongyang.java.zyfyfront.returndata;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.util.Message;
import com.zhongyang.java.zyfyfront.pojo.Article;
import com.zhongyang.java.zyfyfront.vo.ArticleVO;

public class ArticleReturn {
	
	private Message message;
	
	private Page<ArticleVO> page;
	
	private Article article;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Page<ArticleVO> getPage() {
		return page;
	}

	public void setPage(Page<ArticleVO> page) {
		this.page = page;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}
	
}
