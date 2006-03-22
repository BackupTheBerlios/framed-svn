/*
 * Created on 29-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.addictivesoftware.utils.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * @author gassies
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XmlHelper {
	public static Document createDomDocument(File _file) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse( _file );
	}
	public static Document createDomDocument() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.newDocument();
	}
	
	private static XPath xpathprocessor  = null;

	public static Object evalXPath(Document _doc, String _xpath, NamespaceContext _nsContext) throws XPathExpressionException {
		return evalXPath(_doc, _xpath, _nsContext, XPathConstants.STRING);
	}
	
	public static Node getNode(Document _doc, String _xpath) throws XPathExpressionException {
		return (Node)evalXPath(_doc, _xpath, null, XPathConstants.NODE);
	}
	
	public static NodeList getNodeList(Document _doc, String _xpath) throws XPathExpressionException {
		return (NodeList)evalXPath(_doc, _xpath, null, XPathConstants.NODESET);
	}
	
	private static Object evalXPath(Document _doc, String _xpath, NamespaceContext _nsContext, QName _returnType) throws XPathExpressionException {
		//get an XPath processor
		if (null == xpathprocessor) {
			XPathFactory xpfactory = XPathFactory.newInstance();
	        xpathprocessor = xpfactory.newXPath();
		}

        //set the namespace context for resolving prefixes of the Qnames
        //to NS URI, if the xpath expresion uses Qnames. XPath expression
        //would use Qnames if the XML document uses namespaces.
		if (null != _nsContext) {
	        xpathprocessor.setNamespaceContext(_nsContext);
		}

        //create XPath expressions
        XPathExpression compiledXPath = xpathprocessor.compile(_xpath);

		return compiledXPath.evaluate(_doc, _returnType);	}
	
	public static boolean save(Document _doc, String _fileName) {
		File file = new File(_fileName);
		return save(_doc, file);
	}

	public static boolean save(Document _doc, File _file) {
		try {
	        TransformerFactory tranFactory = TransformerFactory.newInstance();
	        Transformer aTransformer = tranFactory.newTransformer();
	        Source src = new DOMSource(_doc);
	        Result dest = new StreamResult(_file);
	        aTransformer.transform(src, dest);
	        return true;
		} catch (TransformerConfigurationException e) {
			System.err.println(e.getMessage());
		} catch (TransformerException e) {
			System.err.println(e.getMessage());
		}
		return false;
	}
}
