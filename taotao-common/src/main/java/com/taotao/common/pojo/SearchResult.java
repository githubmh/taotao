package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable{
	private long totalPages;
	private long recordCount;
	private List<SearchItem> itemLis;
	public long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}
	public long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
	public List<SearchItem> getItemLis() {
		return itemLis;
	}
	public void setItemLis(List<SearchItem> itemLis) {
		this.itemLis = itemLis;
	}
	
	
}
