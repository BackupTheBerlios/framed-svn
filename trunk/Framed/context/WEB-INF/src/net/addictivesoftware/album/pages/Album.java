package net.addictivesoftware.album.pages;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Set;

import javax.servlet.ServletContext;

import net.sf.tacos.demo.tree.fs.FileKeyProvider;
import net.sf.tacos.demo.tree.fs.FileTreeContentProvider;
import net.sf.tacos.model.IKeyProvider;
import net.sf.tacos.model.ITreeContentProvider;
import net.sf.tacos.tree.ITreeManager;
import net.sf.tacos.tree.TreeManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hivemind.util.ContextResource;
import org.apache.tapestry.IAsset;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.asset.ContextAsset;
import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.web.WebContext;
import org.apache.tapestry.web.WebContextResource;
import org.apache.tapestry.web.WebRequest;


public abstract class Album extends BasePage {

	public abstract void setFile(File file);
	public abstract File getFile();
	
	@InjectObject("service:tapestry.globals.WebRequest")
	public abstract WebRequest getWebRequest();
	
	@InjectObject("service:tapestry.globals.ServletContext")
	public abstract ServletContext getServletContext();

	@InjectObject("service:tapestry.globals.WebContext")
	public abstract WebContext getWebContext();
	
	private static final Log LOG = LogFactory.getLog(Album.class);
	private final static String PROP_FSROOT = "net.addictivesoftware.album-filesystem-root";
	private final ITreeContentProvider CONTENT_PROVIDER = new FileTreeContentProvider(getRootPath(), filterName());

	public IAsset getDynamicAsset() {

		String path = getFile().getParent();
		if (path.startsWith("/")) {
			path = path.substring(path.indexOf("Album/context")+13, path.length());
		}
		path += "/ThumbNails/64x64-0-" + getFile().getName();
		LOG.info("PATH:" + path);
		return new ContextAsset(
				getWebRequest().getContextPath(),
				new ContextResource(getServletContext(), path), null);
		
	}
	
	public ITreeContentProvider getContentProvider() {
		return CONTENT_PROVIDER;
	}

	private static FilenameFilter filterName() {
		FilenameFilter filter = new FilenameFilter(){

			public boolean accept(File dir, String name) {
				LOG.info(name);
				if (name.toLowerCase().equals("thumbnails")) {
					return false;
				}
				if (name.toLowerCase().equals("index.xml")) {
					return false;
				}
				return true;
			}
		
		};
		return filter;
	}

	public IKeyProvider getKeyProvider() {
		return FileKeyProvider.INSTANCE;
	}

	public void select(IRequestCycle cycle) {
		String path = (String) cycle.getListenerParameters()[0];
		if (!path.startsWith(getRootPath())) {
			throw new IllegalArgumentException("Path has to be relative to root " + getRootPath());
		}
		File f = new File(path);
		
		setSelectedFile(f);
		LOG.info(f.getPath() + " selected");
	}

	public void expandAll(IRequestCycle cycle) {
		getTreeManager().expandAll();
	}

	public void collapseAll(IRequestCycle cycle) {
		getTreeManager().collapseAll();
	}

	public void revealSelected(IRequestCycle cycle) {
		getTreeManager().reveal(getSelectedFile());
	}

	public ITreeManager getTreeManager() {
		return new TreeManager(getFileState(), getContentProvider(), getKeyProvider());
	}

	public abstract File getSelectedFile();

	public abstract void setSelectedFile(File file);

	public abstract Set getFileState();

	private String getRootPath() {
		String fsRoot = System.getProperty(PROP_FSROOT);
		if (null == getServletContext()) {
			fsRoot = "/home/gertjan/workspace/Album/context/fotos/";
		} else {
			if (fsRoot == null) {
				fsRoot = getRealPath("/fotos/");
				LOG.warn("JVM property " + PROP_FSROOT + " not specified, defaulting to " + fsRoot);
			}
		}
		return fsRoot;
	}
	
	private String getRealPath(String string) {
		return getServletContext().getRealPath(string); 
	}

	public abstract WebRequest getRequest();

	
}
