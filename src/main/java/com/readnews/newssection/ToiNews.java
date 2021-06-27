package com.readnews.newssection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.readnews.beans.NewsBean;

/**
 * ToiNews class is for Times of India News paper.
 * @author Johnpeter Jesu
 *
 */
public class ToiNews implements News{

	/**
	 * fetchNews is method is to get TOI news.
	 */
	@Override
	public List<NewsBean> fetchNews() {
		Document document = null;
		try {
			document = Jsoup.connect("https://timesofindia.indiatimes.com/").get();
		} catch (IOException e) {
			return new ArrayList<>();
		}
		String source = "TOI";
		Elements elements = document.getElementsByTag("figure");
		List<NewsBean> newsList = elements.stream()
			.filter(predicate -> predicate.getElementsByTag("a").size() > 0)
			.map(mapper -> {
				String newsContent = mapper.getElementsByTag("figcaption").text();
				String href = mapper.getElementsByTag("a").attr("href");
				System.out.println(newsContent);
				return new NewsBean.NewsBuilder().setNewsContent(newsContent).setNewsLink(href).setSource(source).build();
			})
			.collect(Collectors.toList());
		newsList.stream().distinct().collect(Collectors.toList());
		return newsList;
	}

}
