package net.addictivesoftware.framed.components;

import javax.servlet.ServletContext;

import net.addictivesoftware.framed.pages.Album;
import net.addictivesoftware.framed.services.ThumbNailService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.Tapestry;
import org.apache.tapestry.annotations.InjectObject;

public abstract class Thumb extends BaseComponent {

	private static final Log LOG = LogFactory.getLog(Album.class);

	public abstract String getImage();
	public abstract int getHeight();
	public abstract int getWidth();
	
	@InjectObject("service:framed.ThumbNailService")
	public abstract ThumbNailService getThumbNailService();
	@InjectObject("service:tapestry.globals.ServletContext")
	public abstract ServletContext getServletContext();
	
    protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle)
    {
        // Doesn't contain a body so no need to do anything on rewind (assumes no
        // sideffects to accessor methods via bindings).

        if (cycle.isRewinding())
            return;

        String sURL = getImage();

        if (sURL == null) {
            throw Tapestry.createRequiredParameterException(this, "image");
        }

        String sImageURL = getThumbNailService().create(sURL, getWidth(), getHeight());
        
        String ContextName = getServletContext().getServletContextName();
        sImageURL = sImageURL.substring(sImageURL.indexOf(ContextName)+21);
               
        writer.beginEmpty("img");

        writer.attribute("src", sImageURL);
        writer.attribute("border", "0");
        
        renderInformalParameters(writer, cycle);

        writer.closeTag();
    }

}
