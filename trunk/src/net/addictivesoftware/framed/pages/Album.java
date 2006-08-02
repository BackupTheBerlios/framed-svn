/*
 * Created on 18-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.addictivesoftware.framed.pages;
 
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;

import net.addictivesoftware.framed.CommentService;
import net.addictivesoftware.framed.CommentServiceImpl;
import net.addictivesoftware.framed.IPhotoList;
import net.addictivesoftware.framed.SecureFileSystemPhotoList;
import net.addictivesoftware.framed.security.User;
import net.addictivesoftware.framed.services.FotoPathService;
import net.addictivesoftware.framed.services.ThumbNailService;

import org.apache.tapestry.IBinding;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.PageRedirectException;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Meta;
import org.apache.tapestry.annotations.Persist;
import org.apache.tapestry.web.WebRequest;
import org.xml.sax.SAXException;


@Meta({ "anonymous-access=true", "admin-page=false" })
public abstract class Album extends FramedPage {
	private IPhotoList aList = null;
	
	@InjectObject("service:tapestry.globals.ServletContext")
	public abstract ServletContext getServletContext();

	@InjectObject("service:tapestry.globals.WebRequest")
	public abstract WebRequest getWebRequest();

	@InjectObject("service:framed.FotoPathService")
	public abstract FotoPathService getFotoPathService();

	@InjectObject("service:framed.ThumbNailService")
	public abstract ThumbNailService getThumbNailService();
	
	public List getEntries() {
			String sessionId = getWebRequest().getSession(true).getId();
	    	String dir = getFotoPathService().getCurrentPath(sessionId) + "/";
			aList = new SecureFileSystemPhotoList(dir, getValidFiles(dir));
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
	//TODO move this to admin page	
	public void doDeleteThumb(IRequestCycle cycle) {
		Object[] params = cycle.getListenerParameters();
		String image = (String)params[0];
		String thumbImage = getThumbNailService().getThumbName(image, false);
		File file = new File(thumbImage);
		if (null != file && file.exists()) {
			file.delete();
			System.out.println("Thumb deleted");
		}
		throw new PageRedirectException(this.getPageName());
	}
	
    private List<String> getValidFiles(String _dir) {
		File file = new File(_dir + "/comments.xml");
		CommentService parser;
		try {
			parser = new CommentServiceImpl(file);
			if (isUserLoggedIn()) {
				User user = getVisitState().getUser();
				return parser.getFilesForViewRight(user.getRoles().getName());
			} else {
				return parser.getFilesForViewRight("all");
			}
						
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
