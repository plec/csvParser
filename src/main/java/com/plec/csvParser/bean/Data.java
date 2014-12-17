package com.plec.csvParser.bean;

import java.util.ArrayList;
import java.util.List;

public class Data {
	List<String> headers = new ArrayList<String>();
	List<List<String>> content = new ArrayList<List<String>>();
	/**
	 * @return the headers
	 */
	public List<String> getHeaders() {
		return headers;
	}
	/**
	 * @param headers the headers to set
	 */
	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}
	/**
	 * @return the content
	 */
	public List<List<String>> getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(List<List<String>> content) {
		this.content = content;
	}
}
