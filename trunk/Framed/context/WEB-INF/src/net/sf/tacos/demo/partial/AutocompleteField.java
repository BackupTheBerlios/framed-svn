package net.sf.tacos.demo.partial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import net.sf.tacos.ajax.components.ListItemRenderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.valid.IValidationDelegate;

/**
 * Provides example of using an autocompleteing text field.
 *
 * @author jkuhnert
 */
public abstract class AutocompleteField extends BasePage {
    
    /** Logger */
    private static final Log log = LogFactory.getLog(AutocompleteField.class);
    /** List html renderer */
    private static final ListItemRenderer FLAG_RENDERER = new CountryFlagRenderer();
    
    public ListItemRenderer getListRenderer()
    {
        return FLAG_RENDERER;
    }
    
	public void storeNote(IRequestCycle cycle) {
		IValidationDelegate delegate = (IValidationDelegate) getBeans().getBean("delegate");
		if (delegate.getHasErrors()) {
			return;
		}
		
        //does nothing
	}
    
    /**
     * Invoked by ajax request to perform autocomplete search.
     * 
     * @param search The value to search on
     */
    public void searchCountries(String search)
    {
        log.debug("searchCountries(" + search + ")");
        
        //Performs country name search
        search = search.toUpperCase();
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList searchList = new ArrayList();
        ArrayList localeList = new ArrayList();
        for (int i = 0; i < locales.length; i++) {
            if (locales[i].getDisplayCountry().toUpperCase().indexOf(search) > -1) {
                searchList.add(locales[i].getDisplayCountry());
                localeList.add(locales[i]);
            }
        }
        
        setSearchList(searchList);
        setLocaleList(localeList);
    }
    
    /** Set by invocation of {@link #searchCountries(String)} */
    public abstract Collection getSearchList();
    /** Sets the search list return */
    public abstract void setSearchList(Collection values);
    /** Set by invocation of {@link #searchCountries(String)} */
    public abstract Collection getLocaleList();
    /** Sets the search list return */
    public abstract void setLocaleList(Collection values);
    /** form note value */
    public abstract String getNoteValue();
}
