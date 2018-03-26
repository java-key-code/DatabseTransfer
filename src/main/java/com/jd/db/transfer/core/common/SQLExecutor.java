package com.jd.db.transfer.core.common;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jd.db.transfer.util.StopWatch;

/**
 * 功能：SQL执行器
 * 
 * @author miaojundong
 *
 */
public class SQLExecutor {
	
	private static final Logger logger = LoggerFactory.getLogger(SQLExecutor.class);
	
	private Connection connection;
	
	private SQLExecutor(Connection connection) {
		this.connection = connection;
	}
	
	public static SQLExecutor newInstance(Connection connection) {
		return new SQLExecutor(connection);
	}
	
	/**
	 * 查询数据库表名
	 * 
	 * @return
	 */
	public List<String> showTables() {
		List<String> list = new ArrayList<String>();
		
		ResultSet rs = null;
		try {
			DatabaseMetaData dmd = connection.getMetaData();
			rs = dmd.getTables(null, null, "%", null);
            while (rs.next()) {  
            	list.add(rs.getString("TABLE_NAME"));
            } 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, null, connection);
		}
		return list;
	}
	
	/**
	 * 查询表字段信息
	 * @param tableName
	 */
	public List<Map<String, String>> showTableFields(String tableName) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		ResultSet rs = null;
		try {
			DatabaseMetaData dmd = connection.getMetaData();
			rs = dmd.getColumns(null, null, tableName.toUpperCase(), "%");

			Map<String, String> columnMap = null;
			while (rs.next()) {
				columnMap = new HashMap<String, String>();
				columnMap.put("columnName", rs.getString("COLUMN_NAME"));
				columnMap.put("typeName", rs.getString("TYPE_NAME"));
				list.add(columnMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, null);
		}
		return list;
	}

	/**
	 * 执行查询SQL
	 * 
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> executeSQL(String sql) {
		StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String, Object> map = null;
		try {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				map = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					map.put(metaData.getColumnLabel(i), rs.getObject(i));
				}
				list.add(map);
			}

			stopWatch.stop();
			logger.info("执行查询时间: {}s", stopWatch.elapsed(TimeUnit.SECONDS));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, ps);
		}
		return list;
	}
	
	/**
	 * 执行单条更新SQL
	 * 
	 * @param sql
	 * @return
	 */
	public int executeUpdateSQL(String sql) {
		int result = 0;
		Statement ps = null;
		try {
			connection.setAutoCommit(false);
			ps = connection.createStatement();
			result = ps.executeUpdate(sql);
			connection.commit();
		} catch (Exception e) {
			result = -1;
		} finally {
			JdbcUtil.close(null, ps);
		}
		return result;
	}
	
	/**
	 * 批量执行更新SQL
	 * 
	 * @param sqlList
	 * @return
	 */
	public int executeUpdateSQL(List<String> sqlList) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		int result = 0;
		Statement ps = null;
		int i = 0;
		try {
			connection.setAutoCommit(false);
			ps = connection.createStatement();
			for (String sql : sqlList) {
				// 加入SQL队列
				ps.addBatch(sql);
				i++;
				
				// 每10000条SQL提交一次
				if (i % 10000 == 0) {
					ps.executeBatch();
					connection.commit();
					logger.info("执行第{}次提交, 成功{}条", i, i*10000);
				}
			}
			// 若总条数不是批量数值的整数倍, 则还需要再额外的执行一次.
			if (i % 10000 != 0) {
				ps.executeBatch();
				connection.commit();
				logger.info("执行第{}次提交, 成功{}条", i, i*10000);
			}
			
			result = 1;
			stopWatch.stop();
			logger.info("执行添加时间: {}s", stopWatch.elapsed(TimeUnit.SECONDS));
		} catch (Exception e) {
			result = -1;
			e.printStackTrace();
		} finally {
			JdbcUtil.close(null, ps);
		}
		return result;
	}
    
	public void batchInsertData(String tableName, List<Map<String, String>> columnInfoMap, List<Map<String, Object>> dataList) {
		List<String> sqlList = new ArrayList<String>();
		String insertSql = null;
		for (Map<String, Object> columnDataMap : dataList) {
			insertSql = generateSQL(tableName, columnInfoMap, columnDataMap);
			System.out.println("SQL--->" + insertSql);
			sqlList.add(insertSql);
		}
		executeUpdateSQL(sqlList);

	}
	
	public static String generateSQL(String tableName, List<Map<String, String>> columnInfoList, Map<String, Object> columnDataMap) {
		if(null == columnDataMap || columnDataMap.size() <= 0) {
			return null;
		}
		
		StringBuilder insertSql = new StringBuilder("INSERT INTO ");
		insertSql.append(tableName);
		StringBuilder fields = new StringBuilder();
		StringBuilder values = new StringBuilder();
		
		for (Map.Entry<String, Object> columnData : columnDataMap.entrySet()) {
			for (Map<String, String> columnInfo : columnInfoList) {
				if(columnData.getKey().equalsIgnoreCase(columnInfo.get("columnName"))) {
					fields.append(columnInfo.get("columnName")).append(", ");
					if(isNeedQuotation(columnInfo.get("typeName"))) {
						values.append("'").append(columnData.getValue()).append("', ");
					} else {
						values.append(columnData.getValue()).append(", ");
					}
					continue;
				}
			}
		}
		
		String fieldsStr = fields.toString().trim();
		String valuesStr = values.toString().trim();
		if(fieldsStr.length() > 0) {
			insertSql.append(" (");
			insertSql.append(fieldsStr.substring(0, fieldsStr.length()-1));
			insertSql.append(")");
			insertSql.append(" VALUES (");
			insertSql.append(valuesStr.substring(0, valuesStr.length()-1));
			insertSql.append(")");
			return insertSql.toString();
		}
		
		return null;
	}
	
	private static boolean isNeedQuotation(String columnType) {  
		columnType = columnType.toUpperCase();  
        switch(columnType){  
            case "VARCHAR":  
            case "VARCHAR2":  
            case "CHAR":  
                return true;  
            case "NUMBER":  
            case "DECIMAL":  
            case "INT":  
            case "SMALLINT":  
            case "INTEGER":  
            case "BIGINT":
            	return false;
            case "DATETIME":  
            case "TIMESTAMP":  
            case "DATE":  
                return true;  
            default:  
                return true;  
        }  
    } 
}
