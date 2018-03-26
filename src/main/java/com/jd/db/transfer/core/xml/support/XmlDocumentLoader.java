package com.jd.db.transfer.core.xml.support;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;

/**
 * 功能：
 * 
 * @author miaojundong
 *
 */
public class XmlDocumentLoader {
	
	public Document loadDocument(InputSource inputSource) throws Exception {

		DocumentBuilderFactory factory = createDocumentBuilderFactory();
		DocumentBuilder builder = createDocumentBuilder(factory, null, null);
		return builder.parse(inputSource);
	}
	
	protected DocumentBuilderFactory createDocumentBuilderFactory() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		return factory;
	}
	
	protected DocumentBuilder createDocumentBuilder(
			DocumentBuilderFactory factory, EntityResolver entityResolver, ErrorHandler errorHandler)
			throws ParserConfigurationException {

		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		if (entityResolver != null) {
			docBuilder.setEntityResolver(entityResolver);
		}
		if (errorHandler != null) {
			docBuilder.setErrorHandler(errorHandler);
		}
		return docBuilder;
	}
}
