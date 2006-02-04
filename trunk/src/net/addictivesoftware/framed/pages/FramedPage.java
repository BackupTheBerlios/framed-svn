package net.addictivesoftware.framed.pages;

import net.addictivesoftware.framed.IErrorProperty;
import net.addictivesoftware.framed.IMessageProperty;
import net.addictivesoftware.framed.Delegate;
import net.addictivesoftware.framed.Visit;
import net.addictivesoftware.framed.services.ErrorPresenter;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.PageRedirectException;
import org.apache.tapestry.annotations.Bean;
import org.apache.tapestry.annotations.InjectMeta;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.InjectPage;
import org.apache.tapestry.annotations.InjectState;
import org.apache.tapestry.annotations.InjectStateFlag;
import org.apache.tapestry.annotations.Meta;
import org.apache.tapestry.callback.PageCallback;
import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.event.PageValidateListener;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.valid.IValidationDelegate;



@Meta(
{ "anonymous-access=false", "admin-page=false" })
public abstract class FramedPage extends BasePage implements IErrorProperty, IMessageProperty,
        PageValidateListener
{
    @Bean(Delegate.class)
    public abstract IValidationDelegate getValidationDelegate();

    @InjectState("visit")
    public abstract Visit getVisitState();

    @InjectStateFlag("visit")
    public abstract boolean getVisitStateExists();

    @InjectPage("Login")
    public abstract Login getLogin();

    @InjectMeta("anonymous-access")
    public abstract boolean getAllowAnonymousAccess();

    @InjectMeta("admin-page")
    public abstract boolean isAdminPage();

    @InjectObject("service:framed.ErrorPresenter")
    public abstract ErrorPresenter getErrorPresenter();
    
 
    protected void setErrorField(String componentId, String message)
    {
        IFormComponent component = (IFormComponent) getComponent(componentId);

        IValidationDelegate delegate = getValidationDelegate();

        delegate.setFormComponent(component);
        delegate.record(message, null);
    }

    /**
     * Returns true if the delegate indicates an error, or the error property is not null.
     */

    protected boolean isInError()
    {
        return getError() != null || getValidationDelegate().getHasErrors();
    }

    /**
     * Checks if the user is logged in. If not, they are sent to the {@link Login} page before
     * coming back to whatever this page is.
     */

    public void pageValidate(PageEvent event)
    {
        if (isAdminPage())
            ensureUserIsLoggedInAsAdmin();

        if (!getAllowAnonymousAccess())
            ensureUserIsLoggedIn();
    }

    private void ensureUserIsLoggedIn()
    {
        if (isUserLoggedIn())
            return;

        // User not logged in ... redirect through the Login page.

        Login login = getLogin();
        login.setCallback(new PageCallback(this));
        throw new PageRedirectException(login);
    }

    /**
     * Returns true if the {@link Visit} exists, and the user is logged in as well.
     * 
     * @return true if logged in
     */
    protected final boolean isUserLoggedIn()
    {
        return getVisitStateExists() && getVisitState().isUserLoggedIn();
    }

    protected void ensureUserIsLoggedInAsAdmin()
    {
        if (!isUserLoggedIn())
        {
            Login login = getLogin();
            login.setCallback(new PageCallback(this));
            throw new PageRedirectException(login);
        }

        IRequestCycle cycle = getRequestCycle();

        if (!getVisitState().getUser().isAdmin())
        {
            getErrorPresenter().presentError(
                    "That function is restricted to administrators.",
                    cycle);
 
            throw new PageRedirectException(cycle.getPage());
        }
    }
    
}