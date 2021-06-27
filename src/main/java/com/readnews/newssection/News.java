package com.readnews.newssection;

import java.util.List;

import com.readnews.beans.NewsBean;

/**
 * News Interface.
 * @author Johnpeter Jesu
 *
 */
public interface News {

	public List<NewsBean> fetchNews();
}
