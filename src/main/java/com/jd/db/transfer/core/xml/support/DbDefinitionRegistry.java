package com.jd.db.transfer.core.xml.support;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.jd.db.transfer.core.pojo.DatabaseBean;
import com.jd.db.transfer.core.pojo.DatabaseConfig.DatabaseResource;

/**
 * 功能：数据库注册器
 * 
 * @author miaojundong
 *
 */
public class DbDefinitionRegistry {
	private static Map<String, DatabaseBean> sourceDbMap = null;
	private static Map<String, DatabaseBean> targetDbMap = null;
	private static Map<String, DatabaseBean> allDbMap = null;
	
	static {
		sourceDbMap = new LinkedHashMap<String, DatabaseBean>();
		targetDbMap = new LinkedHashMap<String, DatabaseBean>();
		allDbMap = new HashMap<String, DatabaseBean>();
	}
	
	protected void registrySourceDb(DatabaseBean entity) {
		entity.setDatabaseResource(DatabaseResource.FROM);
		String key = entity.getDbname() + "(" + entity.getIp() + ":" + entity.getPort() + ")";
		sourceDbMap.put(key, entity);
		allDbMap.put(key, entity);
	}
	
	protected void registryTargetDb(DatabaseBean entity) {
		entity.setDatabaseResource(DatabaseResource.TO);
		String key = entity.getDbname() + "(" + entity.getIp() + ":" + entity.getPort() + ")";
		targetDbMap.put(key, entity);
		allDbMap.put(key, entity);
	}
	
	protected Map<String, DatabaseBean> getAllSourceDb() {
		return sourceDbMap;
	}
	
	protected Map<String, DatabaseBean> getAllTargetDb() {
		return targetDbMap;
	}
	
	protected Map<String, DatabaseBean> getAllDb() {
		return allDbMap;
	}
}
