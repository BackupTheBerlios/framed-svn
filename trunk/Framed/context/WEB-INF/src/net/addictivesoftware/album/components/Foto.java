package net.addictivesoftware.album.components;

import net.addictivesoftware.album.pages.Album;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.AbstractComponent;
import org.apache.tapestry.IAsset;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.Tapestry;

public abstract class Foto extends AbstractComponent {

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

        String imageURL = sURL;
        LOG.info(sURL);

        writer.beginEmpty("img");

        writer.attribute("src", imageURL);

        renderInformalParameters(writer, cycle);

        writer.closeTag();
    }

    public abstract String getImage();
}
