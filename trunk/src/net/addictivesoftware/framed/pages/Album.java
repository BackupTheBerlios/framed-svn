/*
 * Created on 18-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.addictivesoftware.framed.pages;
 
import java.util.List;

import javax.servlet.ServletContext;

import net.addictivesoftware.framed.FileSystemPhotoList;
import net.addictivesoftware.framed.IPhotoList;
import net.addictivesoftware.framed.services.FotoPathService;

import org.apache.tapestry.IBinding;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.html.BasePage;

public abstract class Album extends BasePage {
	private IPhotoList aList = null;
	
	@InjectObject("service:tapestry.globals.ServletContext")
	public abstract ServletContext getServletContext();
				
	@InjectObject("service:framed.FotoPathService")
	public abstract FotoPathService getFotoPathService();
	
	public List getEntries() {
		if (null == aList) {
			aList = new FileSystemPhotoList(getServletContext().getRealPath(getFotoPathService().getCurrentPath() + "/"));
		}
				return aList.getEntries();
	}

	public void doDetailPage(IRequestCycle cycle) {
		Object[] params = cycle.getListenerParameters();
		Detail detailPage = (Detail)cycle.getPage("Detail");
		IBinding imageBinding = detailPage.getNestedComponent("foto").getBinding("image");
		imageBinding.setObject(params[0]);
		detailPage.getNestedComponent("foto").setBinding("image",imageBinding);
		cycle.activate(detailPage);
    }    
	
}
