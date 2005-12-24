package net.addictivesoftware.framed.pages;

import org.apache.tapestry.annotations.Meta;

@Meta({ "anonymous-access=true", "admin-page=false" })
public abstract class Home extends FramedPage {
	
	public void activate()
    {
        getRequestCycle().activate(this);
    }
}
