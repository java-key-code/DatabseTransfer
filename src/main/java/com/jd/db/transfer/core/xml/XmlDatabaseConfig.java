package com.jd.db.transfer.core.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.jd.db.transfer.core.pojo.DatabaseBean;

/**
 * 功能：
 * 
 * @author miaojundong
 *
 */
@Component
public class XmlDatabaseConfig implements InitializingBean {
	
	private DatabaseFactory factory;
	
	public Map<String, DatabaseBean> getAllSourceDb() {
		return factory.getAllSourceDb();
	}
	
	public List<String> getAllSourceDbFullName() {
		List<String> nameList = new ArrayList<String>();
		for (String fullName : factory.getAllSourceDb().keySet()) {  
			nameList.add(fullName);
		} 
		return nameList;
	}
	
	public Map<String, DatabaseBean> getAllTargetDb() {
		return factory.getAllTargetDb();
	}
	
	public List<String> getAllTargetDbFullName() {
		List<String> nameList = new ArrayList<String>();
		for (String fullName : factory.getAllTargetDb().keySet()) {  
			nameList.add(fullName);
		} 
		return nameList;
	}
	
	public Map<String, DatabaseBean> getAllDb() {
		return factory.getAllDb();
	}
	
	public DatabaseBean getDatabase(String fullName) {
		return factory.getAllDb().get(fullName);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		factory = new DatabaseFactory("/dbTranferContext.xml");
	}
}
