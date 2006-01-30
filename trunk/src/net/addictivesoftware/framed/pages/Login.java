// Copyright 2004, 2005 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package net.addictivesoftware.framed.pages;



import net.addictivesoftware.framed.VirtualLibraryDelegate;
import net.addictivesoftware.framed.Visit;
import net.addictivesoftware.framed.security.User;
import net.addictivesoftware.framed.services.Security;
import net.addictivesoftware.framed.services.Users;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.Bean;
import org.apache.tapestry.annotations.InjectComponent;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.InjectPage;
import org.apache.tapestry.annotations.Message;
import org.apache.tapestry.annotations.Meta;
import org.apache.tapestry.annotations.Persist;
import org.apache.tapestry.callback.ICallback;
import org.apache.tapestry.event.PageBeginRenderListener;
import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.services.CookieSource;
import org.apache.tapestry.valid.IValidationDelegate;


/**
 * Allows the user to login, by providing email address and password. After succesfully logging in,
 * a cookie is placed on the client browser that provides the default email address for future
 * logins (the cookie persists for a week).
 * 
 * @author Howard Lewis Ship
 */

@Meta("anonymous-access=true")
public abstract class Login extends FramedPage implements PageBeginRenderListener
{
    /**
     * The name of a cookie to store on the user's machine that will identify them next time they
     * log in.
     */

    private static final String COOKIE_NAME = "org.apache.tapestry.vlib.Login.email";

    @Bean(VirtualLibraryDelegate.class)
    public abstract IValidationDelegate getValidationDelegate();

    public abstract void setEmail(String value);

    public abstract String getEmail();

    public abstract String getPassword();

    public abstract void setPassword(String password);

    @InjectObject("infrastructure:cookieSource")
    public abstract CookieSource getCookieSource();

    @Persist("client:app")
    public abstract ICallback getCallback();

    public abstract void setCallback(ICallback value);

    @InjectComponent("email")
    public abstract IFormComponent getEmailField();

    @InjectComponent("password")
    public abstract IFormComponent getPasswordField();

    @InjectPage("Home")
    public abstract Home getHome();

    @InjectObject("service:framed.Users") 
    public abstract Users getUsers();

    @InjectObject("service:framed.Security") 
    public abstract Security getSecurity();

    @Message
    public abstract String loginfailed();
    /**
     * Attempts to login.
     * <p>
     * If the user name is not known, or the password is invalid, then an error message is
     * displayed.
     */

    public void attemptLogin(IRequestCycle cycle)
    {
    	System.out.println("attemptLogin");
        final String password = getPassword();
        final String email = getEmail();
        System.out.println("for " + email + " / " + password );
        
        // Do a little extra work to clear out the password.

        setPassword(null);
        IValidationDelegate delegate = getValidationDelegate();

        delegate.setFormComponent(getPasswordField());
        delegate.recordFieldInputValue(null);

        // An error, from a validation field, may already have occured.

        if (delegate.getHasErrors())
            return;
        

        User user = getUsers().getUser(email);
        if (getSecurity().isValid(user) && password.equals(user.getPassword())) {
        	loginUser(user);
        } else {
        	setError(loginfailed());
        }
    }

    /**
     * Sets up the {@link User} as the logged in user, creates a cookie for thier email address
     * (for subsequent logins), and redirects to the appropriate page ({@link MyLibrary}, or a
     * specified page).
     */

    public void loginUser(User person)
    {
        IRequestCycle cycle = getRequestCycle();

        String email = person.getEmail();

        // Get the visit object; this will likely force the
        // creation of the visit object and an HttpSession.

        Visit visit = getVisitState();
        visit.setUser(person);

        // After logging in, go to the Home page, unless otherwise
        // specified.

        ICallback callback = getCallback();

        if (callback == null)
            getHome().activate();
        else
            callback.performCallback(cycle);

        // TODO: Set max age of cookie once TAPESTRY-438 is fixed.

        getCookieSource().writeCookieValue(COOKIE_NAME, email);

        cycle.forgetPage(getPageName());
    }

    public void pageBeginRender(PageEvent event)
    {
        if (getEmail() == null)
            getCookieSource().readCookieValue(COOKIE_NAME);
    }


}