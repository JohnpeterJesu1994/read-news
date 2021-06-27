package com.readnews;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readnews.beans.FetchNewsBean;
import com.readnews.beans.NewsBean;
import com.readnews.controller.NewsController;
import com.readnews.newssection.News;
import com.readnews.repository.NewsRepository;
import com.readnews.service.NewsService;

/**
 * ReadNewsApplicationTests class is to test news controller methods.
 * @author Johnpeter Jesu
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
class ReadNewsApplicationTests {
	
	@MockBean
	NewsRepository newsRepository;
	
	@MockBean
	News news;
	
	@Autowired
	NewsController newsController;
	
	@Autowired
	NewsService newsService;
	
	@Autowired
	private MockMvc mockMvc;

	/***
	 * fetchNews method is to test fetchNews method with a valid source.
	 * @throws Exception
	 */
	@Test
	void fetchNews() throws Exception {
		List<NewsBean> mockNewsList = mockFetchedNews();
		when(news.fetchNews()).thenReturn(mockNewsList);
		FetchNewsBean mockFetchNewsBean = getMockFetchNewsBean();
		mockMvc.perform(post("/news/fetchnews").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(mockFetchNewsBean)))
			.andDo(print())
			.andExpect(status().isCreated());
	}
	
	/**
	 * fetchNewsWithInvalidSource method is to test fetchNews method with invalid news source.
	 * @throws Exception
	 */
	@Test
	void fetchNewsWithInvalidSource() throws Exception {
		FetchNewsBean mockFetchNewsBean = getInvalidMockFetchNewsBean();
		mockMvc.perform(post("/news/fetchnews").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(mockFetchNewsBean)))
			.andDo(print())
			.andExpect(status().isUnprocessableEntity());
	}
	
	/**
	 * readNews method is to test readNews method with valid keyword.
	 * @throws Exception
	 */
	@Test
	void readNews() throws Exception {
		String keyword = "sister";
		List<NewsBean> filteredNews = filteredNews(keyword);
		when(newsRepository.search(keyword)).thenReturn(filteredNews);
		mockMvc.perform(get("/news/readnews").contentType(MediaType.APPLICATION_JSON)
				.queryParam("keyword", keyword)
				)
		.andDo(print())
		.andExpect(status().isOk());
	}
	
	/**
	 * invalidReadNews method is to test readNews method with invalid keyword.
	 * @throws Exception
	 */
	@Test()
	void invalidReadNews() throws Exception {
		String keyword = "invalid";
		List<NewsBean> filteredNews = filteredNews(keyword);
		when(newsRepository.search(keyword)).thenReturn(filteredNews);
		mockMvc.perform(get("/news/readnews").contentType(MediaType.APPLICATION_JSON)
				.queryParam("keyword", keyword)
				)
		.andDo(print())
		.andExpect(status().isNoContent());
	}
	
	/**
	 * filteredNews method is to get filtered data for given keyword.
	 * @param keyword
	 * @return
	 */
	private List<NewsBean> filteredNews(String keyword){
		return mockFetchedNews().stream()
			.filter(predicate -> predicate.getNewsContent().contains(keyword))
			.collect(Collectors.toList());
	}
	
	/**
	 * getInvalidMockFetchNewsBean method will return mock invalid news source.
	 * @return
	 */
	private FetchNewsBean getInvalidMockFetchNewsBean() {
		FetchNewsBean fetchNewsBean = new FetchNewsBean();
		fetchNewsBean.setNewsSource("toii");
		return fetchNewsBean;
	}

	/**
	 * getFetchNewsBean method is to get mock fetchNewsBean entity.
	 * @return
	 */
	private FetchNewsBean getMockFetchNewsBean() {
		FetchNewsBean fetchNewsBean = new FetchNewsBean();
		fetchNewsBean.setNewsSource("toi");
		return fetchNewsBean;
	}
	
	/**
	 * mockFetchedNews method is to get mock NewsBean list.
	 * @return
	 */
	private List<NewsBean> mockFetchedNews(){
		return new ArrayList<>(Arrays.asList(new NewsBean.NewsBuilder().setSource("toi")
				.setNewsLink("https://timesofindia.indiatimes.com/entertainment/tamil/movies/news/pooja-hegde-begins-dance-rehearsal-for-vijays-beast/articleshow/83866927.cms")
				.setNewsContent("Pooja Hegde begins dance rehearsal for Vijay's Beast").build(),
				new NewsBean.NewsBuilder().setSource("toi")
				.setNewsContent("Salman Khan's rakhi sister Shweta Rohira goes platinum blonde inspired by Arjun Rampal after losing 40 kilos")
				.setNewsLink("https://timesofindia.indiatimes.com/videos/entertainment/hindi/salman-khans-rakhi-sister-shweta-rohira-goes-platinum-blonde-inspired-by-arjun-rampal-after-losing-40-kilos/videoshow/83867579.cms")
				.build()));
	}

}
