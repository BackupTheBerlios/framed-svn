package net.addictivesoftware.framed.components;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import net.addictivesoftware.framed.CommentParser;
import net.addictivesoftware.framed.services.FotoPathService;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InjectObject;
import org.xml.sax.SAXException;

public abstract class DirComment extends BaseComponent {
	
	public abstract String getImage();

	@InjectObject("service:tapestry.globals.ServletContext")
	public abstract ServletContext getServletContext();

	@InjectObject("service:framed.FotoPathService")
	public abstract FotoPathService getFotoPathService();
	
    protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle)
    {
        if (cycle.isRewinding())
            return;
        // Doesn't contain a body so no need to do anything on rewind (assumes no
        // sideffects to accessor methods via bindings).
    	String path = getServletContext().getRealPath(getFotoPathService().getCurrentPath() + "/") + "/comments.xml";

    	File file = new File(path);
    	
        writer.begin("p");
        writer.attribute("class", "comment");
        CommentParser parser;
		try {
			parser = new CommentParser(file);
			writer.print(parser.getDirComment());
		} catch (XPathExpressionException e) {
		writer.print(e.getMessage());
		} catch (SAXException e) {
			writer.print(e.getMessage());
		} catch (IOException e) {
			writer.print(e.getMessage());
		} catch (ParserConfigurationException e) {
			writer.print(e.getMessage());
		}
        writer.end("p");
    }
	
}
