package net.addictivesoftware.framed.components;

import javax.servlet.ServletContext;

import net.addictivesoftware.framed.services.FotoPathService;
import net.addictivesoftware.framed.services.ThumbNailService;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.Tapestry;
import org.apache.tapestry.annotations.InjectObject;

public abstract class Thumb extends BaseComponent {

	public abstract String getImage();
	public abstract int getHeight();
	public abstract int getWidth();
	
	@InjectObject("service:framed.ThumbNailService")
	public abstract ThumbNailService getThumbNailService();
	
	@InjectObject("service:framed.FotoPathService")
	public abstract FotoPathService getFotoPathService();
	
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
        // gets the url for the thumbnail, creates the thumbnail if need be

		String context = getServletContext().getRealPath("/");

		String basePath = getFotoPathService().getPath();
		String sThumbImageURL = getThumbNailService().create(context, basePath, sURL, getWidth(), getHeight());
        String sBiggerImageURL = getThumbNailService().create(context, basePath, sURL, 760, 570);
        String sRelativeThumbImageURL = sThumbImageURL.substring(sThumbImageURL.indexOf(getThumbNailService().getThumbPath())+1);
        String sRelativeBiggerImageURL = sBiggerImageURL.substring(sBiggerImageURL.indexOf(getThumbNailService().getThumbPath())+1);

        writer.begin("a");
        writer.attribute("class", "thickbox");
        writer.attribute("rel", "same-for-paging");
        writer.attribute("href", sRelativeBiggerImageURL); 

        	writer.beginEmpty("img");
        	writer.attribute("src", sRelativeThumbImageURL);
        	writer.attribute("border", "0");
        
        		renderInformalParameters(writer, cycle);

        	writer.closeTag();
        writer.end("a");
    }

}
