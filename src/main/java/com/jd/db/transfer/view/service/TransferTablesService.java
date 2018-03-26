package com.jd.db.transfer.view.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jd.db.transfer.core.common.SQLExecutor;

/**
 * 功能：
 * 
 * @author miaojundong
 *
 */
@Service
public class TransferTablesService {
	
	private static final Logger logger = LoggerFactory.getLogger(TransferTablesService.class);
	
	/**
	 * 查询需要迁移的表的记录数
	 * 
	 * @param databaseFrom
	 * @param tableName
	 * @return
	 */
	public Long getMoveDataCount(Connection databaseFrom, String tableName) {
		try {
			SQLExecutor sqlExecutor = SQLExecutor.newInstance(databaseFrom);
			List<Map<String, Object>> result = sqlExecutor.executeSQL("select count(*) AS num from " + tableName);
			if(null != result && result.size() > 0 ) {
				return (Long) result.get(0).get("num");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return 0L;
	}
	
	/**
	 * 执行数据库表数据迁移
	 * 
	 * @param databaseFrom
	 * @param databaseTo
	 * @param tableName
	 */
	public int moveTablesData(Connection databaseFrom, Connection databaseTo, String tableName) {
		SQLExecutor sqlExecutor = SQLExecutor.newInstance(databaseFrom);
		List<Map<String, Object>> dataList = sqlExecutor.executeSQL("select * from " + tableName);
		List<Map<String, String>> tableFieldsMap = sqlExecutor.showTableFields(tableName);
		
		sqlExecutor = SQLExecutor.newInstance(databaseTo);
		sqlExecutor.batchInsertData(tableName, tableFieldsMap, dataList);
		logger.debug(Thread.currentThread().getName() +"--迁移表名==》" + tableName + "--databaseFrom="+databaseFrom + "--databaseTo="+databaseTo);
		return dataList.size();
	}
}
