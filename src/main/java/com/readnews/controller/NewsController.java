package com.readnews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.readnews.beans.FetchNewsBean;
import com.readnews.service.NewsService;

/**
 * NewsController class is to map news mappings.
 * @author Johnpeter Jesu
 *
 */
@RequestMapping("/news")
@RestController
public class NewsController {

	@Autowired
	NewsService newsService;
	
	/**
	 * fetchNews method is to read news from web sites and save into table.
	 * @param fetchNewsBean
	 * @return
	 */
	@RequestMapping(value="/fetchnews", method=RequestMethod.POST)
	public ResponseEntity<?> fetchNews(@RequestBody FetchNewsBean fetchNewsBean) {
		return newsService.fetchNewsAndSave(fetchNewsBean);
	}
	
	/**
	 * readNews method is to read news from data base.
	 * @param keyword
	 * @return
	 */
	@GetMapping(value="/readnews")
	public ResponseEntity<?> readNews(@RequestParam String keyword) {
		return newsService.readNews(keyword);
	}
}
