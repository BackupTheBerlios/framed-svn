package net.addictivesoftware.utils;

import java.io.File;

public class ImageHelper {
	public static boolean isImage(File _file) {
		String name = _file.getName().toLowerCase();
		if ((name.endsWith(".jpeg") || name.endsWith(".jpg"))) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public static boolean isThumb(File _file) {
		String name = _file.getName().toLowerCase();
		if (name.startsWith("t_") && (name.endsWith(".jpeg") || name.endsWith(".jpg"))) {
			return true;
		} else {
			return false;
		}
	}	
}
