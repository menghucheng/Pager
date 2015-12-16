package com.imooc.page.model;

import java.io.Serializable;
import java.util.List;

public class Pager<T> implements Serializable {

	private static final long serialVersionUID = -8741766802354222579L;
	
	/** 页面大小*/
	private int pageSize;
	
	/** 当前页数 */
	private int currentPage;
	
	/** 总页数 */
	private int totalPage;
	
	/** 总记录数 */
	private int totalRecords;
	
	/** 数据List<T> */
	private List<T> dataList;

	public Pager() {
		super();
	}
	
	public Pager(int pageNum,int pageSize,List<T> sourceList){
		if(sourceList == null){
			return;
		}
		//总记录数
		this.totalRecords = sourceList.size();
		
		//每页显示多少条记录
		this.pageSize = pageSize;
		
		//获取总页数
		int temp = this.totalRecords / this.pageSize;
		this.totalPage = (this.totalRecords % this.pageSize)==0 ? temp : temp + 1;
		
		//当前第几页
		if(this.totalPage < pageNum){
			this.currentPage = this.totalPage;
		}else{
			this.currentPage = pageNum;
		}
		
		//		起始索引
		int fromIndex = this.pageSize *  (this.currentPage - 1);
		
		//结束索引
		int toIndex = this.pageSize * this.currentPage > this.totalRecords ? this.totalRecords : this.pageSize * this.currentPage;
		
		//获取dataList   
		this.dataList = sourceList.subList(fromIndex, toIndex);
	}

	public Pager(int pageSize, int currentPage, int totalPage, int totalRecords, List<T> dataList) {
		super();
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.totalPage = totalPage;
		this.totalRecords = totalRecords;
		this.dataList = dataList;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	@Override
	public String toString() {
		return "Pager [pageSize=" + pageSize + ", currentPage=" + currentPage + ", totalPage=" + totalPage
				+ ", totalRecords=" + totalRecords + ", dataList=" + dataList + "]";
	}
}
