package com.jd.db.transfer.core.xml;

import java.util.Map;

import com.jd.db.transfer.core.pojo.DatabaseBean;
import com.jd.db.transfer.core.xml.support.DbDefinitionRegistry;

/**
 * 功能：数据库工厂类
 * 
 * @author miaojundong
 *
 */
public class DatabaseFactory extends DbDefinitionRegistry {
	
	private XmlDefinitionReader reader;
	
	public DatabaseFactory(String cfgPath) {
		reader = new XmlDefinitionReader(this);
		try {
			reader.loadDatabaseDefinitions(cfgPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, DatabaseBean> getAllSourceDb() {
		return super.getAllSourceDb();
	}
	
	public Map<String, DatabaseBean> getAllTargetDb() {
		return super.getAllTargetDb();
	}
	
	public Map<String, DatabaseBean> getAllDb() {
		return super.getAllDb();
	}
}
