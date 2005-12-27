package net.addictivesoftware.framed.components;

import java.util.Locale;

import net.addictivesoftware.framed.pages.Home;
import net.addictivesoftware.framed.services.ApplicationLifecycle;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.PageRedirectException;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.InjectPage;
import org.apache.tapestry.annotations.Message;

public abstract class Border extends BaseComponent {
	
    @InjectObject("service:framed.ApplicationLifecycle")
    public abstract ApplicationLifecycle getApplicationLifecycle();
    
    @InjectPage("Home")
    public abstract Home getHome();
    
    @Message
    public abstract String goodbye();
	
    public IPage logout()
    {
        getApplicationLifecycle().logout();

        Home home = getHome();
        home.setMessage(goodbye());

        return home;
    }
    
    public String getLocale() {
    	return this.getPage().getEngine().getLocale().getCountry();
    }

    public void changeLocale(IRequestCycle cycle, String _locale) {
    	Locale locale = new Locale(_locale);
    	this.getPage().getEngine().setLocale(locale);
    	cycle.cleanup();
    	throw new PageRedirectException(this.getPage());    
    }
}
