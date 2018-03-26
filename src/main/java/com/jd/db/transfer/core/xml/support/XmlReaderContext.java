package com.jd.db.transfer.core.xml.support;

import com.jd.db.transfer.core.xml.XmlDefinitionReader;

/**
 * 功能：
 * 
 * @author miaojundong
 *
 */
public class XmlReaderContext {
	
	private final XmlDefinitionReader reader;
	private final DbDefinitionRegistry registry;
	
	public XmlReaderContext(XmlDefinitionReader reader, DbDefinitionRegistry registry) {
		this.reader = reader;
		this.registry = registry;
	}
	
	public final XmlDefinitionReader getReader() {
		return this.reader;
	}

	public final DbDefinitionRegistry getRegistry() {
		return this.registry;
	}
}
