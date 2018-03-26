package com.jd.db.transfer.core.pojo;

/**
 * 功能：数据库配置
 * 
 * @author miaojundong
 *
 */
public class DatabaseConfig {
	public enum DatabaseResource { FROM, TO };
	public enum DatabaseType { MYSQL, ORACLE };
	
	private String dbType;
	private String ip;
	private Integer port;
	private String dbname;
	private String userName;
	private String password;
	
	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
