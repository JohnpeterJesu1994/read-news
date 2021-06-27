package com.readnews.beans;

import org.springframework.stereotype.Component;

/**
 * FetchNewsBean object is to get input parameters.
 * @author Johnpeter Jesu
 *
 */
@Component
public class FetchNewsBean {
	
	private String newsSource;
	
	private String newsPageLink;

	public String getNewsSource() {
		return newsSource;
	}

	public void setNewsSource(String newsSource) {
		this.newsSource = newsSource;
	}

	public String getNewsPageLink() {
		return newsPageLink;
	}

	public void setNewsPageLink(String newsPageLink) {
		this.newsPageLink = newsPageLink;
	}
}
