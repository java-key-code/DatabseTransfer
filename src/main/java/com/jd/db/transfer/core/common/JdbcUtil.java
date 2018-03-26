package com.jd.db.transfer.core.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jd.db.transfer.core.pojo.DatabaseBean;

/**
 * 功能：JDBC辅助类
 * 
 * @author miaojundong
 *
 */
public class JdbcUtil {
      
	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	public static Connection getDbConnection(DatabaseBean database) {
		Connection connection = null;
		try {
			Class.forName(database.getDriverName());
			connection = DriverManager.getConnection(database.getUrl(), database.getUserName(), database.getPassword());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
	public static void close(ResultSet rs, Statement st) {
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
    }
	
	/**
	 * 关流
	 * 
	 * @param rs
	 * @param st
	 * @param conn
	 */
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
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
