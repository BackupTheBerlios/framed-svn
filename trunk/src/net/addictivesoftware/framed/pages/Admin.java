package net.addictivesoftware.framed.pages;

import java.io.File;

import net.addictivesoftware.framed.CommentParser;
import net.addictivesoftware.framed.services.FotoPathService;
import net.addictivesoftware.utils.ImageHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Meta;
import org.w3c.dom.Document;

@Meta({ "anonymous-access=false", "admin-page=true" })
public abstract class Admin extends FramedPage {
	private static final Log LOG = LogFactory.getLog(Album.class);
	
	@InjectObject("service:framed.FotoPathService")
	public abstract FotoPathService getFotoPathService();
	
	public abstract String getMessage();
	public abstract void setMessage(String _string);
	
	public void goUserManagement(IRequestCycle cycle) {
		UserManagement usermanPage = (UserManagement)cycle.getPage("UserManagement");
		cycle.activate(usermanPage);
    }    


	public void cleanThumbNails(IRequestCycle cycle) {
		setMessage("cleaning thumbnails");
		File file = new File(getFotoPathService().getPath());
		if (file.isDirectory()) {
//			cleanThumbsFromDir(file);
		}
	}
		
	public void createComments(IRequestCycle cycle) {
		setMessage("creating Comments files for all directories");

		File rootDir = new File(getFotoPathService().getPath());
			if (rootDir.isDirectory()) {
//				createCommentsRecursive(rootDir);
			}
			
	}
	
	private void createCommentsRecursive(File _dir) {
		createCommentsForDir(_dir);
		File[] files = _dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				createCommentsForDir(_dir);
			}
		}
	}
	
	private void cleanThumbsFromDir(File _dir) {
		File[] files = _dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				cleanThumbsFromDir(files[i]);
			} else if (ImageHelper.isThumb(files[i])) {
				files[i].delete();
			}
		}
	}	
	private void createCommentsForDir(File _dir)  {
		CommentParser cp;
		try {
			cp = new CommentParser(_dir);
			File commentFile = new File(_dir, "comments.xml");
			if (!commentFile.exists()) {
					Document doc = cp.createCommentFile(_dir);
					cp.save(doc,_dir);
			} else {
				// TODO Update existing file with new photo's
			}		
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
	}


}



