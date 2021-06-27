package com.readnews.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.readnews.beans.FetchNewsBean;
import com.readnews.beans.NewsBean;
import com.readnews.newssection.News;
import com.readnews.newssection.ToiNews;
import com.readnews.repository.NewsRepository;

/**
 * NewsService class is for business logics of news API.
 * @author Johnpeter Jesu
 *
 */
@Service
public class NewsService {
	
	@Autowired
	NewsRepository newsRepository;
	
	/** The Constant LOGGER */
	private static Logger LOGGER = LoggerFactory.getLogger(NewsService.class);
	
	/**
	 * 
	 * @param fetchNewsBean
	 * @return
	 */
	public ResponseEntity<?> fetchNewsAndSave(FetchNewsBean fetchNewsBean) {
		ResponseEntity<?> response = ResponseEntity.ok("");
		String newsSource = Objects.toString(fetchNewsBean.getNewsSource(), "");
		News news = null;
		List<NewsBean> newsList = new ArrayList<>();
		switch (newsSource.toLowerCase()) {
		case "toi":
			news = new ToiNews();
			newsList = news.fetchNews();
			break;

		default:
			response = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.body("Please provide proper news source such as TOI, Google News");
			return response;
		}
		if(!CollectionUtils.isEmpty(newsList)) {
			saveNews(newsList);
			response = ResponseEntity.status(HttpStatus.CREATED).body("Datas are fetched from " + newsSource);
		}

		return response;
	}
	
	/**
	 * saveNews method is for save news list into repository.
	 * @param newsList
	 */
	private void saveNews(List<NewsBean> newsList) {
		LOGGER.info("Save started.");
		newsRepository.saveAll(newsList);
		LOGGER.info("Save end");
	}

	/**
	 * readNews method is to read news from data base for given keyword.
	 * @param keyword
	 * @return
	 */
	public ResponseEntity<?> readNews(String keyword) {
		List<NewsBean> result = newsRepository.search(keyword);
		if(result.size() > 0) {
			return ResponseEntity.ok().body(result);
		}else {
			LOGGER.error("No data available for given keyword : " + keyword);
			return ResponseEntity.noContent().build();
		}
	}

}
