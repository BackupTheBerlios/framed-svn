package net.sf.tacos.demo.tree.fs;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.annotations.InjectObject;

import net.addictivesoftware.album.pages.Album;
import net.sf.tacos.model.ITreeContentProvider;

/**
 * @author phraktle
 */
public class FileTreeContentProvider implements ITreeContentProvider {
	private static final Log LOG = LogFactory.getLog(Album.class);

	private final String rootPath;
	private FilenameFilter filter = null;

	public FileTreeContentProvider(String rootPath) {
		this.rootPath = rootPath;
	}
	public FileTreeContentProvider(String rootPath, FilenameFilter filter) {
		this.rootPath = rootPath;
		this.filter = filter;
	}
	
	public Collection getElements() {
		if (null != filter) {
			return Arrays.asList(new File(rootPath).listFiles(filter));
		}
		return Arrays.asList(new File(rootPath).listFiles());
	}

	public Collection getChildren(Object parentElement) {
		File f = (File) parentElement;
		if (f.isDirectory()) {
			return Arrays.asList(f.listFiles());
		}
		return Collections.EMPTY_LIST;
	}

	public Object getParent(Object childElement) {
		return ((File) childElement).getParentFile();
	}

	public boolean hasChildren(Object parentElement) {
		File f = (File) parentElement;
		return f.isDirectory();
	}

}