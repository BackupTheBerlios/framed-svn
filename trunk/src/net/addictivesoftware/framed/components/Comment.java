package net.addictivesoftware.framed.components;

import java.io.File;

import javax.servlet.ServletContext;
import javax.xml.xpath.XPathExpressionException;

import net.addictivesoftware.framed.CommentService;
import net.addictivesoftware.framed.CommentServiceFactory;
import net.addictivesoftware.framed.services.FotoPathService;
import net.addictivesoftware.utils.Const;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.web.WebRequest;

public abstract class Comment extends BaseComponent {
	
	public abstract String getImage();
	
	@InjectObject("service:tapestry.globals.ServletContext")
	public abstract ServletContext getServletContext();

	@InjectObject("service:tapestry.globals.WebRequest")
	public abstract WebRequest getWebRequest();
	
	@InjectObject("service:framed.FotoPathService")
	public abstract FotoPathService getFotoPathService();
    
    public String getComment() {
    	String sessionId = getWebRequest().getSession(true).getId();
    	
    	String path = getServletContext().getRealPath(getFotoPathService().getCurrentPath(sessionId) + Const.SEPARATOR) + "/comments.xml";
    	String imageName = getImage().substring(getImage().lastIndexOf(Const.SEPARATOR)+1);
    	String result = "no comment (yet) for " + imageName;
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
