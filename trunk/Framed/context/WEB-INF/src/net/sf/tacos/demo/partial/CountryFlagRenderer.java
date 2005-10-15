/*******************************************************************************
 * Tacos - 2005
 *
 * Created by: jkuhnert
 *******************************************************************************/
package net.sf.tacos.demo.partial;

import java.util.Iterator;
import java.util.Locale;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;

import net.sf.tacos.ajax.components.ListItemRenderer;


/**
 * Demonstrates rendering more than just string values for 
 * {@link Autocompleter} components.
 * 
 * @author jkuhnert
 */
public class CountryFlagRenderer implements ListItemRenderer {

    /**
     * {@inheritDoc}
     */
    public void renderList(IMarkupWriter writer, IRequestCycle cycle,
            Iterator values)
    {
        if (cycle.isRewinding())
            return;
        
        //Write values out as simple strings
        writer.begin("ul");
        
        while (values.hasNext()) {
            Locale value = (Locale)values.next();
            if (value == null)
                continue;
            
            writer.begin("li");
            writer.beginEmpty("img");
            writer.attribute("src", "http://setiathome.free.fr/images/flags/" 
                    + value.getCountry().toLowerCase() + ".gif");
            writer.print(value.getDisplayCountry());
            writer.end("li");
        }
        
        writer.end();
    }

}
