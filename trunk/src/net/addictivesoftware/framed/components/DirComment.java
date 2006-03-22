package net.addictivesoftware.framed.components;

import java.io.File;

import javax.servlet.ServletContext;
import javax.xml.xpath.XPathExpressionException;

import net.addictivesoftware.framed.CommentService;
import net.addictivesoftware.framed.CommentServiceFactory;
import net.addictivesoftware.framed.pages.FramedPage;
import net.addictivesoftware.framed.services.FotoPathService;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.PageRedirectException;
import org.apache.tapestry.annotations.InitialValue;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Persist;

public abstract class DirComment extends BaseComponent {

	@InjectObject("service:tapestry.globals.ServletContext")
	public abstract ServletContext getServletContext();

	@InjectObject("service:framed.FotoPathService")
	public abstract FotoPathService getFotoPathService();
	
	@Persist("session")
	@InitialValue("false")
	public abstract Boolean getEditmode();
	public abstract void setEditmode(Boolean mode);
	
    public void toggleEditMode(IRequestCycle cycle) {
    	this.setEditmode( ! this.getEditmode());    	
    	throw new PageRedirectException(this.getPage());    
    	
    
    }

    
    public void saveDirComment(IRequestCycle cycle) {
    	
    	System.out.println("saving dir comment");
    	
    	this.setEditmode(false);
    	String path = getServletContext().getRealPath(getFotoPathService().getCurrentPath() + "/") + "/comments.xml";
    	File file = new File(path);
    	
    	CommentService parser = getCommentParser(file);
    	parser.setDirComment(cycle.getParameter("dirComment"));
    	
    	throw new PageRedirectException(this.getPage());    
    }
    
    public String getComment() {
    	String result = "no comment (yet)";
    	String path = getServletContext().getRealPath(getFotoPathService().getCurrentPath() + "/") + "/comments.xml";

    	File file = new File(path);
    	
        CommentService parser;
		try {
			parser = getCommentParser(file);
			result = parser.getDirComment().trim();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
    	return result;
    }
    
    private CommentService getCommentParser(File _file) {
		return CommentServiceFactory.getInstance(_file);
    }
    
}
