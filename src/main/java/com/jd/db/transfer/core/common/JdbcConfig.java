package com.jd.db.transfer.core.common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 功能：JDBC辅助类
 * 
 * @author miaojundong
 *
 */
public class JdbcConfig {
      
	private static final Properties config = new Properties();
	
	private static String DATABASE_DRIVER;
	private static String DATABASE_URL;
	private static String DATABASE_USER;
	private static String DATABASE_PWD;
	
	static {
		try {
			config.load(JdbcConfig.class.getResourceAsStream("/jdbc.properties"));
			DATABASE_DRIVER = config.getProperty("database.driverClassName");
			DATABASE_URL = config.getProperty("database.url");
			DATABASE_USER = config.getProperty("database.username");
			DATABASE_PWD = config.getProperty("database.password");
			Class.forName(DATABASE_DRIVER);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	public static Connection getDbConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PWD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	/**
	 * 关流
	 * 
	 * @param rs
	 * @param st
	 * @param conn
	 */
	public static void close(ResultSet rs, Statement st, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    }
}
