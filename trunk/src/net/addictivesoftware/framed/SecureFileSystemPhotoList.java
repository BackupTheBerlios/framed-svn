
package net.addictivesoftware.framed;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecureFileSystemPhotoList implements IPhotoList {
	private String path = "";
	private List<PhotoListEntry> entries;
	
	public SecureFileSystemPhotoList(String _path, List<String> _validFiles) {
		this.path = _path;
		entries = new ArrayList<PhotoListEntry>();
		File dir = new File(path);
		
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			Arrays.sort(files);
			for (int i=0;i<files.length;i++) {
				File file = files[i];
				if (	! file.isDirectory()
						&& ! file.isHidden()
						&& isImage(file) 
						&& !isThumb(file)
						&& isValidFile( _validFiles, file.getName())) {
					
					PhotoListEntry e = new PhotoListEntry(file);
					addEntry(e);
				}
			}
		} else {
			System.out.println(dir + " is NOT a directory");
		}
	}
		
	private boolean isValidFile(List<String> files, String name) {
		if (null != files) {
			for (String file : files) {
				if (name.equals(file)){
					return true;
				}
			}
		}
		return false;
	}

	private boolean isImage(File _file) {
		String ext = _file.getName().substring(_file.getName().lastIndexOf(".")).toLowerCase();
		if (".jpg".equals(ext)  || ".jpeg".equals(ext)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isThumb(File _file) {
		if (_file.getName().startsWith("T_")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public void addEntry(PhotoListEntry entry) {
		entries.add(entry);
	}

	public List getEntries() {
		return entries;
	}

}
