package net.addictivesoftware.framed;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
			Arrays.sort(files);
			for (int i=0;i<files.length;i++) {
				File file = files[i];
				if (! file.isDirectory() 
						&& isImage(file) 
						&& !isThumb(file)
						&& !file.getName().startsWith(".")
						) {
					addEntry(new PhotoListEntry(file));
				}
			}
			
		} else {
			System.out.println(dir + " is NOT a directory");
		}
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
