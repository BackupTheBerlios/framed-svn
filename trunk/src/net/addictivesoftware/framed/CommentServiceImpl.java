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
public class CommentServiceImpl implements CommentService {
	private Document doc = null;
	
	
	public CommentServiceImpl(File _file) throws SAXException, IOException, ParserConfigurationException {
	if (null == doc) {
		System.out.println(_file.getAbsolutePath());
		System.out.println(_file.exists());
		if (_file.exists()) {
				doc = XmlHelper.createDomDocument( _file );
				// TODO check for added files
			} else {
				// create a comment file
				System.out.println("creating comment file for " + _file.getParentFile().getName());
				doc = createCommentFile(_file.getParentFile());
//				save(doc, _file);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see net.addictivesoftware.framed.CommentService#getComment(java.lang.String)
	 */
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
	
	/* (non-Javadoc)
	 * @see net.addictivesoftware.framed.CommentService#getViewRights(java.lang.String)
	 */
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
	
	/* (non-Javadoc)
	 * @see net.addictivesoftware.framed.CommentService#getFilesForViewRight(java.lang.String)
	 */
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
			e.printStackTrace();
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see net.addictivesoftware.framed.CommentService#getDirComment()
	 */
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
		commentNode.setTextContent("***generated comment file***");
		rootNode.appendChild(commentNode);
		for (int i=0;i<files.length;i++) {
			if (ImageHelper.isImage(files[i]) && !ImageHelper.isThumb(files[i])) {
				Node fotoNode = doc.createElement("foto");

				Attr attr = doc.createAttribute("file");
				attr.setValue(files[i].getName());
				fotoNode.getAttributes().setNamedItem(attr);
				
				attr = doc.createAttribute("view");
				attr.setValue("all");
				fotoNode.getAttributes().setNamedItem(attr);
				
				rootNode.appendChild(fotoNode);
			}
		}
		doc.appendChild(rootNode);
		return doc;
	}

	public void setComment(String fotoName) {
		
	}
	
	public String serialize(Document _doc) throws ClassCastException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println(_doc.getDocumentElement().getNodeName());
		System.setProperty(DOMImplementationRegistry.PROPERTY,"org.apache.xerces.dom.DOMImplementationSourceImpl");
		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
		DOMImplementationLS impl = (DOMImplementationLS)registry.getDOMImplementation("LS");
		LSSerializer writer = impl.createLSSerializer();
		String str = writer.writeToString(_doc);
		return str;
	}
	
	public synchronized void save(Document _doc, File _commentsFile)  {
		String xml = "";
		try {
			xml = serialize(_doc);
			System.out.println(xml);
		} catch (ClassCastException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		if (!_commentsFile.exists()) {
			ByteBuffer bb = ByteBuffer.allocate(1024);
			CharBuffer cb = bb.asCharBuffer();
			bb.limit(2*cb.position());
			cb.put(xml);
			FileOutputStream fo = null;
			try {
				_commentsFile.createNewFile();
				fo = new FileOutputStream(_commentsFile);
				FileChannel foc = fo.getChannel();
				foc.lock();
				foc.write(bb);
				foc.force(true);
				fo.flush();
				fo.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();			
			}

		}
	}

}
