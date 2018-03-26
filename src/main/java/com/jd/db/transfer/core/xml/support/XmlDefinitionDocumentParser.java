package com.jd.db.transfer.core.xml.support;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jd.db.transfer.core.pojo.DatabaseBean;
import com.jd.db.transfer.core.pojo.DatabaseConfig.DatabaseResource;

/**
 * 功能：
 * 
 * @author miaojundong
 *
 */
public class XmlDefinitionDocumentParser {
	
	public static final String FROM_DB_ELEMENT = "from";
	public static final String TO_DB_ELEMENT = "to";
	public static final String DATABASE_ELEMENT = "database";
	
	private XmlReaderContext readerContext;
	
	public void registerDbDefinitions (Document doc, XmlReaderContext readerContext) {
		this.readerContext = readerContext;
		Element root = doc.getDocumentElement();
		doRegisterDbDefinitions(root, createDelegate());
	}
	
	protected DbDefinitionParserDelegate createDelegate() {
		DbDefinitionParserDelegate delegate = new DbDefinitionParserDelegate();
		return delegate;
	}

	private void doRegisterDbDefinitions(Element root, DbDefinitionParserDelegate delegate) {
		NodeList nl = root.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if (node instanceof Element) {
				Element ele = (Element) node;
				parseDefaultElement(ele, delegate);
			}
		}
	}
	
	private void parseDefaultElement(Element element, DbDefinitionParserDelegate delegate) {
		if (delegate.nodeNameEquals(element, FROM_DB_ELEMENT)) {
			doParseDefaultElement(element, delegate, DatabaseResource.FROM);
		} else if (delegate.nodeNameEquals(element, TO_DB_ELEMENT)) {
			doParseDefaultElement(element, delegate, DatabaseResource.TO);
		}
	}
	
	private void doParseDefaultElement(Element element, DbDefinitionParserDelegate delegate, DatabaseResource db) {
		NodeList nl = element.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if (node instanceof Element) {
				Element ele = (Element) node;
				processDbDefinition(ele, delegate, db);
			}
		}
	}
	
	private void processDbDefinition(Element ele, DbDefinitionParserDelegate delegate, DatabaseResource db) {
		if (delegate.nodeNameEquals(ele, DATABASE_ELEMENT)) {
			DatabaseBean entity = delegate.parseDbDefinitionElement(ele);
			if(db == DatabaseResource.FROM) {
				readerContext.getRegistry().registrySourceDb(entity);
			}
			if(db == DatabaseResource.TO) {
				readerContext.getRegistry().registryTargetDb(entity);
			}
		}
	}
}
