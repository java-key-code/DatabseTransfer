package com.jd.db.transfer.core.xml.support;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jd.db.transfer.core.pojo.DatabaseBean;
import com.jd.db.transfer.core.pojo.DatabaseConfig.DatabaseType;

/**
 * 功能：
 * 
 * @author miaojundong
 *
 */
public class DbDefinitionParserDelegate {
	
	public static final String DBTYPE_ATTRIBUTE = "dbType";
	public static final String IP_ELEMENT = "ip";
	public static final String PORT_ELEMENT = "port";
	public static final String DBNAME_ELEMENT = "dbname";
	public static final String USERNAME_ELEMENT = "username";
	public static final String PASSWORD_ELEMENT = "password";

	public String getLocalName(Node node) {
		return node.getLocalName();
	}

	public boolean nodeNameEquals(Node node, String desiredName) {
		return desiredName.equals(node.getNodeName()) || desiredName.equals(getLocalName(node));
	}
	
	public DatabaseBean parseDbDefinitionElement(Element element) {
		DatabaseBean entity =  new DatabaseBean();
		entity.setDbType(element.getAttribute(DBTYPE_ATTRIBUTE));
		
		NodeList nl = element.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if (node instanceof Element) {
				Element ele = (Element) node;
				if (this.nodeNameEquals(ele, IP_ELEMENT)) {
					entity.setIp(getTextValue(ele));
				} else if (this.nodeNameEquals(ele, PORT_ELEMENT)) {
					entity.setPort(Integer.valueOf(getTextValue(ele)));
				} else if (this.nodeNameEquals(ele, DBNAME_ELEMENT)) {
					entity.setDbname(getTextValue(ele));
				} else if (this.nodeNameEquals(ele, USERNAME_ELEMENT)) {
					entity.setUserName(getTextValue(ele));
				} else if (this.nodeNameEquals(ele, PASSWORD_ELEMENT)) {
					entity.setPassword(getTextValue(ele));
				}
			}
		}
		
		String driverName = null;
		String url = null;
		if(DatabaseType.MYSQL.name().equalsIgnoreCase(entity.getDbType())) {
			driverName = "com.mysql.jdbc.Driver";
			url = "jdbc:mysql://" + entity.getIp() + ":" + entity.getPort() + "/" + entity.getDbname();
		} else if(DatabaseType.ORACLE.name().equalsIgnoreCase(entity.getDbType())) {
			driverName = "oracle.jdbc.driver.OracleDriver";
			url = "jdbc:oracle:thin:@" + entity.getIp() + ":" + entity.getPort() + ":" + entity.getDbname();
		}
		entity.setDriverName(driverName);
		entity.setUrl(url);
		
		return entity;
	}
	
	public static String getTextValue(Element valueEle) {
		StringBuilder sb = new StringBuilder();
		NodeList nl = valueEle.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node item = nl.item(i);
			if ((item instanceof CharacterData && !(item instanceof Comment)) || item instanceof EntityReference) {
				sb.append(item.getNodeValue());
			}
		}
		return sb.toString();
	}
}
