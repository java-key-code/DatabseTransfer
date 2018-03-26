package com.jd.db.transfer.core.xml;

import java.io.IOException;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.jd.db.transfer.core.xml.support.DbDefinitionRegistry;
import com.jd.db.transfer.core.xml.support.XmlDefinitionDocumentParser;
import com.jd.db.transfer.core.xml.support.XmlDocumentLoader;
import com.jd.db.transfer.core.xml.support.XmlReaderContext;

/**
 * 功能：读取数据库xml配置
 * 
 * @author miaojundong
 *
 */
public class XmlDefinitionReader {
	
	private XmlDocumentLoader documentLoader = new XmlDocumentLoader();
	
	private DbDefinitionRegistry registry;
	
	public XmlDefinitionReader(DbDefinitionRegistry registry) {
		this.registry = registry;
	}
	
	public void loadDatabaseDefinitions(String path) throws Exception {
		try {
			InputStream inputStream = getInputStream(path);
			InputSource inputSource = new InputSource(inputStream);
			Document doc = loadDocument(inputSource);
			registerDbDefinitions(doc);
		} catch (Exception e) {
			throw e;
		}
	}
	
	protected InputStream getInputStream(String path) throws IOException {
		InputStream is = XmlDefinitionReader.class.getResourceAsStream(path);
		return is;
	}
	
	protected Document loadDocument(InputSource inputSource) throws Exception {
		return this.documentLoader.loadDocument(inputSource);
	}
	
	protected void registerDbDefinitions(Document doc) {
		XmlDefinitionDocumentParser documentParser = new XmlDefinitionDocumentParser();
		documentParser.registerDbDefinitions(doc, createReaderContext());
	}
	
	protected XmlReaderContext createReaderContext() {
		return new XmlReaderContext(this, registry);
	}
	
	protected final DbDefinitionRegistry getRegistry() {
		return this.registry;
	}
}
