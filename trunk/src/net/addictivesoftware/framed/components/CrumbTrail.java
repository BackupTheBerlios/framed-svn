package net.addictivesoftware.framed.components;

import java.util.ArrayList;
import java.util.StringTokenizer;

import net.addictivesoftware.framed.Crumb;
import net.addictivesoftware.framed.pages.Album;
import net.addictivesoftware.framed.services.FotoPathService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InjectObject;

public abstract class CrumbTrail extends BaseComponent {
	private static final Log LOG = LogFactory.getLog(Album.class);

	@InjectObject("service:framed.FotoPathService")
	public abstract FotoPathService getFotoPathService();
	
	public ArrayList<Crumb> getCrumbs()  {
		ArrayList<Crumb> crumbs = new ArrayList<Crumb>();
		String currentPath = getFotoPathService().getCurrentPath();
		StringTokenizer st = new StringTokenizer(currentPath, "/");
		while (st.hasMoreTokens()) {
			String crumb = st.nextToken();
			LOG.debug(currentPath);
			LOG.debug(crumb);
			
			String pathUpto = currentPath.substring(0, currentPath.indexOf(crumb) + crumb.length());
			crumbs.add(new Crumb(crumb, pathUpto));
		}
		return crumbs;
	}
	public void goCrumb(IRequestCycle cycle) {
		Object[] params = cycle.getListenerParameters();
		getFotoPathService().setCurrentPath((String)params[0]);
		cycle.activate("Album");
	}
}