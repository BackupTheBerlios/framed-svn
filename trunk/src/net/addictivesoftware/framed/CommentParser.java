/*
 * Created on 29-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.addictivesoftware.framed;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import net.addictivesoftware.utils.ImageHelper;
import net.addictivesoftware.utils.xml.XmlHelper;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;


/**
 * @author gassies
 *
 */
public class CommentParser {
	private Document doc = null;
	public CommentParser(File _file) throws SAXException, IOException, ParserConfigurationException {
		if (null == doc) {
			if (_file.exists()) {
				doc = XmlHelper.createDomDocument( _file );
			} else {
				// create a comment file
			}
		}
	}
	
	public String getComment(String _photoName) throws XPathExpressionException {
		if (null != doc) {
			String XPath = "/fotos/foto[@path='" + _photoName + "']";
			
			String comment = (String)XmlHelper.evalXPath(doc, XPath, null);
			if (null == comment) {
				comment = "no comment found";
			}
			return comment;
		} else {
			return "";
		}
	}
	
	public String getViewRights(String _photoName) throws XPathExpressionException {
		if (null != doc) {
			String XPath = "/fotos/foto[@path='" + _photoName + "']/@view";
			
			String viewRights = (String)XmlHelper.evalXPath(doc, XPath, null);
			if (null == viewRights) {
				viewRights = "no comment found";
			}
			return viewRights;
		} else {
			return "";
		}
	}
	
	public List<String> getFilesForViewRight(String _right) {
		List<String> list = new LinkedList<String>();

		String XPath = "/fotos/foto[true ";
		if (_right.indexOf("|") > 0) {
			StringTokenizer st = new StringTokenizer(_right, "|");
			while (st.hasMoreTokens()) {
				XPath += " or @view='" + st.nextToken() + "'";
			}
			XPath += "]";
		} else {
			XPath = "/fotos/foto[@view='" + _right + "']";
		}
		NodeList nodelist;
		try {
			nodelist = XmlHelper.getNodeList(doc, XPath);
			for (int i=0;i<nodelist.getLength();i++) {
				Node node = nodelist.item(i);
				String filename = node.getAttributes().getNamedItem("path").getNodeValue();
				list.add(filename);
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public String getDirComment() throws XPathExpressionException {
		if (null != doc) {
			String XPath = "/fotos/comment";
			String comment = (String)XmlHelper.evalXPath(doc, XPath, null);
			if (null == comment) {
				comment = "";
			}
			return comment;
		} else {
			return "";
		}
	}
	
	public Document createCommentFile(File _dir) throws ParserConfigurationException {
		doc = XmlHelper.createDomDocument();	
		File[] files = _dir.listFiles();
			
		Node rootNode = doc.createElement("fotos");
		Node commentNode = doc.createElement("comment");
		rootNode.appendChild(commentNode);
		for (int i=0;i<files.length;i++) {
			if (ImageHelper.isImage(files[i]) && !ImageHelper.isThumb(files[i])) {
				Node fotoNode = doc.createElement("foto");
				Attr attr = doc.createAttribute("file");
				attr.setValue(files[i].getName());
				fotoNode.appendChild(attr);
			}
		}
		doc.appendChild(rootNode);
		return doc;
	}

	public void setComment(String fotoName) {
		
	}
	
	public String serialize(Document _doc) throws ClassCastException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.setProperty(DOMImplementationRegistry.PROPERTY,"org.apache.xerces.dom.DOMImplementationSourceImpl");
		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
		DOMImplementationLS impl = (DOMImplementationLS)registry.getDOMImplementation("LS");
		LSSerializer writer = impl.createLSSerializer();
		String str = writer.writeToString(_doc);
		return str;
	}
	
	public void save(Document _doc, File _dir)  {
		String xml = "";
		try {
			xml = serialize(_doc);
		} catch (ClassCastException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		File commentsFile = new File(_dir, "comments.xml");
		if (!commentsFile.exists()) {
			ByteBuffer bb = ByteBuffer.allocate(1024);
			CharBuffer cb = bb.asCharBuffer();
			bb.limit(2*cb.position());
			cb.put(xml);
			FileOutputStream fo = null;
			FileChannel foc = fo.getChannel();
			try {
				fo = new FileOutputStream(commentsFile);
				foc.write(bb);
				fo.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();			
			}
		}
	}


}
