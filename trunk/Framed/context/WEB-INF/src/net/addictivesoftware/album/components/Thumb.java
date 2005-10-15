package net.addictivesoftware.album.components;

import net.addictivesoftware.album.pages.Album;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.Tapestry;

public abstract class Thumb extends BaseComponent {

	private static final Log LOG = LogFactory.getLog(Album.class);

	
	/**
     * Renders the &lt;img&gt; element.
     */

    protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle)
    {
        // Doesn't contain a body so no need to do anything on rewind (assumes no
        // sideffects to accessor methods via bindings).

        if (cycle.isRewinding())
            return;

        String sURL = getImage();

        if (sURL == null)
            throw Tapestry.createRequiredParameterException(this, "image");

        String path = sURL;
		if (path.startsWith("/")) {
			path = path.substring(path.indexOf("Album/context")+13, path.lastIndexOf("/")) + path.substring(path.lastIndexOf("/")+1);
		}

        String imageURL = "Album/context/" + path;
        LOG.info("URL:" + imageURL);

        writer.beginEmpty("img");

        writer.attribute("src", imageURL);
        writer.attribute("style", "padding:5px;");
        renderInformalParameters(writer, cycle);

        writer.closeTag();
    }

    public abstract String getImage();
	
	
}
