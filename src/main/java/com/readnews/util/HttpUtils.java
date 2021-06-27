package com.readnews.util;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpUtils class is for util operations
 * @author Johnpeter Jesu
 *
 */
public class HttpUtils {

	/**
	 * getHeaderValues method is to get header values from http servlet request
	 * @param httpRequest
	 * @return
	 */
	public static Map<String, String> getHeaderValues(HttpServletRequest httpRequest){
		Enumeration<String> requestHeaders = httpRequest.getHeaderNames();
		Map<String, String> headerMap = new HashMap<>();
		while (requestHeaders.hasMoreElements()) {
			String requestKey = requestHeaders.nextElement();
			httpRequest.getHeader(requestKey);
			headerMap.put(requestKey, httpRequest.getHeader(requestKey));
		}
		
		return headerMap;
	}
	
	/**
	 * getUrlValues method is to get url param values as map.
	 * @param httpRequest
	 * @return
	 */
	public static Map<String, String> getUrlValues(HttpServletRequest httpRequest){
		String[] splitted = Optional.ofNullable(httpRequest.getQueryString()).orElse("").split("&");
		Map<String, String> urlMap = Arrays.stream(splitted)
			.filter(predicate -> predicate.contains("="))
			.collect(Collectors.toMap(mapper -> mapper.split("=")[0], value -> value.split("=")[1],
					(key1, key2) -> key1));
		
		return urlMap;
	}
}
