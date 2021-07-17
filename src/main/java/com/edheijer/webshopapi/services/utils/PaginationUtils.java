package com.edheijer.webshopapi.services.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class PaginationUtils {

	/*
	 * This header is used to calculate number of pages in frontend
	 */
	public HttpHeaders getTotalItemHeader(String totalItems) {
		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.setContentType(new MediaType("application","json"));
		responseHeader.set("x-total-items", totalItems);
		return responseHeader;
	}
}
