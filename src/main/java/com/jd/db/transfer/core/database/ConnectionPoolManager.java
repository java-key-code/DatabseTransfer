package com.jd.db.transfer.core.database;

import java.sql.Connection;

/**
 * 功能：
 * 
 * @author miaojundong
 *
 */
public class ConnectionPoolManager {
	private static ConnectionPool connectionPool;

	public static Connection getConnectionOfFrom() {
		return connectionPool.getConnectionOfFrom();
	}

	public static Connection getConnectionOfTo() {
		return connectionPool.getConnectionOfTo();
	}

	protected static void setConnectionPool(ConnectionPool connectionPool) {
		ConnectionPoolManager.connectionPool = connectionPool;
	}

	private ConnectionPoolManager() {

	}
}
