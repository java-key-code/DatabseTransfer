package com.jd.db.transfer.view.controller.support;

/**
 * 功能：
 * 
 * @author miaojundong
 *
 */
public class TransferResult {
	/**
	 * 表名
	 */
	private String tableName;
	
	/**
	 * 迁移总记录数
	 */
	private int totalCount;
	
	/**
	 * 迁移成功数
	 */
	private int successCount;
	
	/**
	 * 迁移失败数
	 */
	private int failureCount;
	
	/**
	 * 迁移状态 0待处理，1处理完成，-1处理失败
	 */
	private int status;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
