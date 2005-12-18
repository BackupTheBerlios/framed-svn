/*
 * Created on 29-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.addictivesoftware.framed;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import net.addictivesoftware.utils.xml.XmlHelper;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/**
 * @author gassies
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CommentParser {
	private XmlHelper xmlhelper = new XmlHelper();
	private Document doc = null;
	public CommentParser(File _file) throws SAXException, IOException, ParserConfigurationException {
		if (null == doc) {
			doc = xmlhelper.createDomDocument( _file );
		}
	}
	public String getComment(String _photoName) throws XPathExpressionException {
		String XPath = "/fotos/foto[@path='" + _photoName + "']";
		String comment = xmlhelper.evalXPath(doc, XPath, null);
		if (null == comment) {
			comment = "no comment found";
		}
		return comment;
	}
	public String getDirComment() throws XPathExpressionException {
		String XPath = "/fotos/comment";
		String comment = xmlhelper.evalXPath(doc, XPath, null);
		if (null == comment) {
			comment = "";
		}
		return comment;
	}

	
	
	
	

}
