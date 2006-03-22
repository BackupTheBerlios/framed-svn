package net.addictivesoftware.framed.components;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import net.addictivesoftware.framed.CommentService;
import net.addictivesoftware.framed.CommentServiceFactory;
import net.addictivesoftware.framed.CommentServiceImpl;
import net.addictivesoftware.framed.Visit;
import net.addictivesoftware.framed.services.FotoPathService;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.InjectState;
import org.apache.tapestry.annotations.InjectStateFlag;
import org.xml.sax.SAXException;

public abstract class Comment extends BaseComponent {
	
	public abstract String getImage();
	
	@InjectObject("service:tapestry.globals.ServletContext")
	public abstract ServletContext getServletContext();

	@InjectObject("service:framed.FotoPathService")
	public abstract FotoPathService getFotoPathService();
    
    public String getComment() {
    	String result = "no comment (yet)";
    	String path = getServletContext().getRealPath(getFotoPathService().getCurrentPath() + "/") + "/comments.xml";
    	String imageName = getImage().substring(getImage().lastIndexOf("/")+1);

    	File file = new File(path);
    	
        CommentService parser;
		try {
			parser = CommentServiceFactory.getInstance(file);
			result = parser.getComment(imageName);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
    	return result;
    }
}
