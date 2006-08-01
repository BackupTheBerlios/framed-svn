/*
 * Created on 29-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.addictivesoftware.framed;

import java.io.File;
import java.io.IOException;
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
import org.xml.sax.SAXException;


/**
 * @author gassies
 *
 */
public class CommentServiceImpl implements CommentService {
	private Document m_doc = null;
	private File m_file = null;
	
	public CommentServiceImpl(File _file) throws SAXException, IOException, ParserConfigurationException {
	if (null == m_doc) {
		if (_file.exists()) {
				m_doc = XmlHelper.createDomDocument( _file );
				// TODO check for added files
			} else {
				// create a comment file
				System.out.println("creating comment file for " + _file.getParentFile().getName());
				m_doc = createCommentFile(_file.getParentFile());
				XmlHelper.save(m_doc, _file);
		}
		m_file = _file;
	}
}
	
	/* (non-Javadoc)
	 * @see net.addictivesoftware.framed.CommentService#getComment(java.lang.String)
	 */
	public String getComment(String _photoName) throws XPathExpressionException {
		if (null != m_doc) {
			String XPath = "/fotos/foto[@path='" + _photoName + "']";
			
			String comment = (String)XmlHelper.evalXPath(m_doc, XPath, null);
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
		if (null != m_doc) {
			String XPath = "/fotos/foto[@path='" + _photoName + "']/@view";
			
			String viewRights = (String)XmlHelper.evalXPath(m_doc, XPath, null);
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
			nodelist = XmlHelper.getNodeList(m_doc, XPath);
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
		if (null != m_doc) {
			String XPath = "/fotos/comment";
			String comment = (String)XmlHelper.evalXPath(m_doc, XPath, null);
			if (null == comment) {
				comment = "";
			}
			return comment;
		} else {
			return "";
		}
	}
	
	public Document createCommentFile(File _dir) throws ParserConfigurationException {
		m_doc = XmlHelper.createDomDocument();	
		File[] files = _dir.listFiles();
		Node rootNode = m_doc.createElement("fotos");
		Node commentNode = m_doc.createElement("comment");
		commentNode.setTextContent("***generated comment file***");
		rootNode.appendChild(commentNode);
		if (null != files) {
			for (int i=0;i<files.length;i++) {
				if (ImageHelper.isImage(files[i]) && !ImageHelper.isThumb(files[i])) {
					Node fotoNode = m_doc.createElement("foto");
	
					Attr attr = m_doc.createAttribute("path");
					attr.setValue(files[i].getName());
					fotoNode.getAttributes().setNamedItem(attr);
					
					attr = m_doc.createAttribute("view");
					attr.setValue("all");
					fotoNode.getAttributes().setNamedItem(attr);
					
					rootNode.appendChild(fotoNode);
				}
			}
		}
		m_doc.appendChild(rootNode);
		return m_doc;
	}

	public void setComment(String _fotoName, String _comment) {
		try {
			Node node = XmlHelper.getNode(m_doc, "/fotos/foto[@path=" + _fotoName + "]");
			if (null != node) {
				Attr attr = m_doc.createAttribute("path");
				attr.setValue(_comment);
				node.getAttributes().setNamedItem(attr);
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XmlHelper.save(m_doc, m_file);
	}

	public void setDirComment(String _comment) {
		try {
			Node node = XmlHelper.getNode(m_doc, "/fotos/comment");
			if (null != node) {
				node.setTextContent(_comment);
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XmlHelper.save(m_doc, m_file);
	}
}
