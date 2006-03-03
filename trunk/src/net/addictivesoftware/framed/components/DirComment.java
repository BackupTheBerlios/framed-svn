package net.addictivesoftware.framed.components;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import net.addictivesoftware.framed.CommentParser;
import net.addictivesoftware.framed.Visit;
import net.addictivesoftware.framed.services.FotoPathService;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.InjectState;
import org.apache.tapestry.annotations.InjectStateFlag;
import org.xml.sax.SAXException;

public abstract class DirComment extends BaseComponent {

	@InjectObject("service:tapestry.globals.ServletContext")
	public abstract ServletContext getServletContext();

	@InjectObject("service:framed.FotoPathService")
	public abstract FotoPathService getFotoPathService();
	
    public String getComment() {
    	String result = "no comment (yet)";
    	String path = getServletContext().getRealPath(getFotoPathService().getCurrentPath() + "/") + "/comments.xml";

    	File file = new File(path);
    	
        CommentParser parser;
		try {
			parser = new CommentParser(file);
			result = parser.getDirComment();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
    	return result;
    }	
}
