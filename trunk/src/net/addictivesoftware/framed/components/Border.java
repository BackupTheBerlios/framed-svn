package net.addictivesoftware.framed.components;

import java.util.Locale;

import net.addictivesoftware.framed.pages.Home;
import net.addictivesoftware.framed.services.ApplicationLifecycle;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IPage;
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
    
    public IPage changeLocale(String _locale) {
    	Locale locale = new Locale(_locale);
    	this.getPage().getEngine().setLocale(locale);
    	return this.getPage();
    }
}
