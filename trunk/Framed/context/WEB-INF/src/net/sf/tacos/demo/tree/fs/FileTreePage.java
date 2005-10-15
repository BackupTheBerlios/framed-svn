package net.sf.tacos.demo.tree.fs;

import java.io.File;
import java.util.Set;
import java.util.Stack;

import net.sf.tacos.ajax.AjaxDirectService;
import net.sf.tacos.ajax.AjaxWebRequest;
import net.sf.tacos.model.IKeyProvider;
import net.sf.tacos.model.ITreeContentProvider;
import net.sf.tacos.tree.ITreeManager;
import net.sf.tacos.tree.TreeManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.html.BasePage;

/**
 * @author phraktle
 */
public abstract class FileTreePage extends BasePage {

	private static final Log log = LogFactory.getLog(FileTreePage.class);

	private final static String PROP_FSROOT = "net.sf.tacos.demo-filesystem-root";

	private final static ITreeContentProvider CONTENT_PROVIDER = new FileTreeContentProvider(getRootPath());

	public ITreeContentProvider getContentProvider() {
		return CONTENT_PROVIDER;
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
        ensurePreviousRefresh(f);
		setSelectedFile(f);
	}
	
    /**
     * Makes sure that the previously selected file is refreshed
     * to ensure the selected css class is updated.
     * @param newFile
     */
    private void ensurePreviousRefresh(File newFile)
    {
        if (getSelectedFile() != null 
                && getAjaxEngine().getAjaxRequest().isValidRequest()) {
            AjaxWebRequest ajax = getAjaxEngine().getAjaxRequest();
            File selFile = getSelectedFile();
            
            File pfile = getIntersectingParent(newFile, selFile);
            
            if (pfile != null) {
                ajax.getUpdateComponents().remove(newFile.getPath());
                ajax.getUpdateComponents().remove(selFile.getPath());
                ajax.getUpdateComponents().add(pfile.getPath());
                return;
            } else {
                ajax.getUpdateComponents().add(selFile.getPath());
            }
        }
    }
    
    /**
     * If specified files are both contained by a parent file,
     * that isn't the file returned from {@link #getRootPath()} this
     * method returns that file.
     * @param f1
     * @param f2
     * @return The nearest intersecting parent node, if any.
     */
    protected File getIntersectingParent(File f1, File f2)
    {
        //For cases where it's the same file
        if (f1.getPath().equals(f2.getPath())) {
            if (f1.getParentFile() != null 
                    && !f1.getParentFile().getPath().equals(getRootPath()))
                return f1.getParentFile();
            else
                return f1;
        }
        
        Stack s1 = new Stack();
        Stack s2 = new Stack();
        
        //Now build our list of parents
        File parent = f1.getParentFile();
        while (parent != null && !parent.getPath().equals(getRootPath())) {
            //For cases where newFile is a child of selFile and 
            //not a sibling node or sibling node's child
            if (parent.getPath().equals(f2.getPath()))
                return parent;
            log.debug("f1 traversing over parent of path:" + parent.getPath());
            s1.push(parent);
            parent = parent.getParentFile();
        }
        
        parent = f2.getParentFile();
        while (parent != null && !parent.getPath().equals(getRootPath())) {
            log.debug("f2 traversing over parent of path:" + parent.getPath());
            s2.push(parent);
            parent = parent.getParentFile();
        }
        
        //Iterate over first stack looking for a shared
        //parent
        if (s1.size() <= 0)
            return null;
        //File pfile = (File)s1.pop();
        while (!s1.isEmpty()) {
            File pfile = (File)s1.pop();
            
            if (s2.contains(pfile))
                return pfile;
            
            log.debug("pfile traversing over path:" + pfile.getPath());
            //if (!s1.isEmpty()) pfile = (File)s1.pop();
        }
        
        return null;
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

	private static String getRootPath() {
		String fsRoot = System.getProperty(PROP_FSROOT);
		if (fsRoot == null) {
			fsRoot = System.getProperty("user.dir");
			log.warn("JVM property " + PROP_FSROOT + " not specified, defaulting to " + fsRoot);
		}
		return fsRoot;
	}
    
    /** Injected ajax request */
    public abstract AjaxDirectService getAjaxEngine();

}