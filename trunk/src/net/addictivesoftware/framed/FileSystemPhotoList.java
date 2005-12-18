package net.addictivesoftware.framed;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSystemPhotoList implements IPhotoList {
	private String path = "";
	private List<PhotoListEntry> entries;
	
	public FileSystemPhotoList(String _path) {
		this.path = _path;
		entries = new ArrayList<PhotoListEntry>();
		File dir = new File(path);
		
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (int i=0;i<files.length;i++) {
				File file = files[i];
				if (! file.isDirectory() && isImage(file) && !isThumb(file)) {
					PhotoListEntry e = new PhotoListEntry(file);
					addEntry(e);
				}
			}
		} else {
			System.out.println(dir + " is NOT a directory");
		}
	}
	
	private boolean isImage(File _file) {
		String ext = _file.getName().substring(_file.getName().lastIndexOf("."));
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
		// TODO Auto-generated method stub
		entries.add(entry);
	}

	public List getEntries() {
		return entries;
	}

}
