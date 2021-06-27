package com.readnews.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * NewsBean entity will handle table operations of jp_news table.
 * @author Johnpeter Jesu
 *
 */
@Entity
@Table(name="jp_news")
public class NewsBean {

	@Column(name="news_source")
	private String source;
	
	@Id
	@Column(name="news_content")
	private String newsContent;
	
	@Column(name="news_link")
	private String newsLink;
	
	public NewsBean() {
		
	}
	
	public NewsBean(String source, String newsContent, String newsLink){
		this.source = source;
		this.newsContent = newsContent;
		this.newsLink = newsLink;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public String getNewsLink() {
		return newsLink;
	}

	public void setNewsLink(String newsLink) {
		this.newsLink = newsLink;
	}

	/**
	 * NewsBuilder builder class.
	 * @author Johnpeter Jesu
	 *
	 */
	public static class NewsBuilder{
		
		private String source;
		
		private String newsContent;
		
		private String newsLink;
		
		public NewsBuilder setSource(String source) {
			this.source = source;
			return this;
		}

		public NewsBuilder setNewsContent(String newsContent) {
			this.newsContent = newsContent;
			return this;
		}

		public NewsBuilder setNewsLink(String newsLink) {
			this.newsLink = newsLink;
			return this;
		}
		
		public NewsBean build() {
			return new NewsBean(source, newsContent, newsLink);
		}
		
		
	}
}
