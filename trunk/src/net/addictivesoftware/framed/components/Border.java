package net.addictivesoftware.framed.components;

import java.util.Locale;

import net.addictivesoftware.framed.Visit;
import net.addictivesoftware.framed.pages.FramedPage;
import net.addictivesoftware.framed.pages.Home;
import net.addictivesoftware.framed.security.User;
import net.addictivesoftware.framed.services.ApplicationLifecycle;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.PageRedirectException;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.InjectPage;
import org.apache.tapestry.annotations.InjectState;
import org.apache.tapestry.annotations.InjectStateFlag;
import org.apache.tapestry.annotations.Message;

public abstract class Border extends BaseComponent {
	
    @InjectObject("service:framed.ApplicationLifecycle")
    public abstract ApplicationLifecycle getApplicationLifecycle();
    
    @InjectPage("Home")
    public abstract Home getHome();

    @InjectState("visit")
    public abstract Visit getVisitState();

    @InjectStateFlag("visit")
    public abstract boolean getVisitStateExists();

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
    
    public void toggleEditMode(IRequestCycle cycle) {
    	FramedPage page = (FramedPage)this.getPage();
    	//page.setEditmode(! page.getEditmode());
    	throw new PageRedirectException(this.getPage());
    }
   
    public boolean isAdmin(IRequestCycle cycle) {
    	if (getVisitStateExists()) {
    		User user = getVisitState().getUser();
    		if (user.isAdmin()) {
    			System.out.println("USER:" + user.getName() + " is admin");    			return true;
    		}
    	}
    	return false;
    }
    
    
    
    
    
    
    
}
