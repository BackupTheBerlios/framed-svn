package net.addictivesoftware.framed.components;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import net.addictivesoftware.framed.MenuItem;
import net.addictivesoftware.framed.services.FotoPathService;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.annotations.InjectObject;

public abstract class DirMenu extends BaseComponent {
	
	@InjectObject("service:framed.FotoPathService")
	public abstract FotoPathService getFotoPathService();
	
	@InjectObject("service:tapestry.globals.ServletContext")
	public abstract ServletContext getServletContext();

	public ArrayList<MenuItem> getMenuItems() {
		ArrayList<MenuItem> aList = new ArrayList<MenuItem>();

		String path = getFotoPathService().getCurrentPath();		
		String realpath = getServletContext().getRealPath(path);
		
		File currentDir = new File(realpath);

		if (null == path || "".equals(path)) {
			path = getFotoPathService().getPath();
		}
		// if path is not equal to root path add parent
		if (!path.equals(getFotoPathService().getPath())) {
			String parent = currentDir.getParent();
			aList.add(new MenuItem(parent.substring(parent.lastIndexOf("/")), path.substring(0,path.lastIndexOf("/"))));
		}
		
		if (currentDir.exists() && currentDir.isDirectory()) {
			File[] files = currentDir.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					
					aList.add(new MenuItem(files[i].getName(), path + "/" + files[i].getName()));
				}
			}
		}
		return aList;
	}
	
	public void addPathToURL(String _path) {
		getFotoPathService().setCurrentPath(_path);
	}
	
}
