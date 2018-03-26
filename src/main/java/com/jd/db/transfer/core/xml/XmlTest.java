package com.jd.db.transfer.core.xml;

import java.util.Map;

import com.jd.db.transfer.core.pojo.DatabaseBean;

public class XmlTest {

	public static void main(String[] args) {
		try {
			DatabaseFactory factory = new DatabaseFactory("/dbTranferContext.xml");
			Map<String, DatabaseBean> databaseMap= factory.getAllDb();
			for (Map.Entry<String, DatabaseBean> entry : databaseMap.entrySet()) {
				System.out.println("-->" + entry.getKey() + "===" + entry.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
