/*******************************************************************************
 * Tacos - 2005
 *
 * Created by: ebin111
 *******************************************************************************/
package net.sf.tacos.demo.partial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import net.sf.tacos.ajax.components.ListItemRenderer;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.html.BasePage;


/**
 * Demonstrates a combination of effects now possible.
 * 
 * @author jkuhnert
 */
public abstract class FormEffects extends BasePage {
    
    /** List html renderer */
    private static final ListItemRenderer FLAG_RENDERER = new CountryFlagRenderer();
    
    public ListItemRenderer getListRenderer()
    {
        return FLAG_RENDERER;
    }
    
    /**
     * Invoked by ajax request to perform autocomplete search.
     * 
     * @param search The value to search on
     */
    public void searchCountries(String search)
    {   
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
    
    /**
     * Invoked by form
     * @param cycle
     */
    public void chooseCountry(IRequestCycle cycle)
    {
        String country = (String)cycle.getParameter("textNoteFlag");
        if (country == null)
            return;
        
        Locale[] locales = Locale.getAvailableLocales();
        for (int i = 0; i < locales.length; i++) {
            if (locales[i].getDisplayCountry().toUpperCase().equals(country.toUpperCase())) {
                setCountry(locales[i]);
                break;
            }
        }
    }
    
    /**
     * Invoked to clear the chosen country.
     * @param cycle
     */
    public void clearCountry(IRequestCycle cycle)
    {
        setCountry(null);
    }
    
    /**
     * Does nothing
     * @param cycle
     */
    public void chooseRegion(IRequestCycle cycle)
    {
        
    }
    
    /** Set by invocation of {@link #searchCountries(String)} */
    public abstract Collection getSearchList();
    /** Sets the search list return */
    public abstract void setSearchList(Collection values);
    /** Set by invocation of {@link #searchCountries(String)} */
    public abstract Collection getLocaleList();
    /** Sets the search list return */
    public abstract void setLocaleList(Collection values);
    /** Sets the chosen country */
    public abstract void setCountry(Locale country);
}
