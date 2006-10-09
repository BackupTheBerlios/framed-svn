package net.addictivesoftware.framed.components;

import java.util.ArrayList;
import java.util.StringTokenizer;

import net.addictivesoftware.framed.Crumb;
import net.addictivesoftware.framed.services.FotoPathService;
import net.addictivesoftware.utils.Const;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.web.WebRequest;

public abstract class CrumbTrail extends BaseComponent {

	@InjectObject("service:framed.FotoPathService")
	public abstract FotoPathService getFotoPathService();

	@InjectObject("service:tapestry.globals.WebRequest")
	public abstract WebRequest getWebRequest();

	public ArrayList<Crumb> getCrumbs()  {
		ArrayList<Crumb> crumbs = new ArrayList<Crumb>();
		
		String sessionId = getWebRequest().getSession(true).getId();
		String currentPath = getFotoPathService().getCurrentPath(sessionId);
		
		StringTokenizer st = new StringTokenizer(currentPath, Const.SEPARATOR);
		while (st.hasMoreTokens()) {
			String crumb = st.nextToken();
			
			String pathUpto = currentPath.substring(0, currentPath.indexOf(crumb) + crumb.length());
			crumbs.add(new Crumb(crumb, pathUpto));
		}
		return crumbs;
	}
	
	public void goCrumb(IRequestCycle cycle) {
		String sessionId = getWebRequest().getSession(true).getId();
		Object[] params = cycle.getListenerParameters();
		getFotoPathService().setCurrentPath(sessionId, (String)params[0]);
		cycle.activate(this.getPage());
	}
}
