package com.jd.db.transfer.core.pojo;

/**
 * 功能：数据库信息实体类
 * 
 * @author miaojundong
 *
 */
public class DatabaseBean extends DatabaseConfig {
	private DatabaseResource databaseResource;
	private String driverName;
	private String url;
	
	public DatabaseResource getDatabaseResource() {
		return databaseResource;
	}

	public void setDatabaseResource(DatabaseResource databaseResource) {
		this.databaseResource = databaseResource;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String toString() {
		return "DatabaseEntity[databaseResource=" + databaseResource  + ", dbType=" + super.getDbType()  + ", driverName=" + driverName 
				+ ", ip=" + super.getIp()  + ", port=" + super.getPort() + ", dbname=" + super.getDbname()
				+ ", url=" + url + ", userName=" + super.getUserName() + ", password=" + super.getPassword() + "]";  
	}
}
