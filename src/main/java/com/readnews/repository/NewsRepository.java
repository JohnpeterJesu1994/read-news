package com.readnews.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.readnews.beans.NewsBean;

/**
 * NewsRepository class is to interact with NewsBean table.
 * @author Johnpeter Jesu
 *
 */
@Repository
public interface NewsRepository extends JpaRepository<NewsBean, String>{

	/**
	 * search method is to search records for given keyword.
	 * @param keyword
	 * @return
	 */
	@Query("SELECT newsBean from NewsBean newsBean WHERE CONCAT(newsBean.source, newsBean.newsContent) LIKE %?1%")
	public List<NewsBean> search(String keyword);
}
