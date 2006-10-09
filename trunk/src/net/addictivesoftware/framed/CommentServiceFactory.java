package net.addictivesoftware.framed;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class CommentServiceFactory {
	private static HashMap<String, CommentService> csMap = new HashMap<String, CommentService>();
	
	public static CommentService getInstance(File _file) {
		String path = _file.getAbsolutePath().replace(":", "_");
		System.out.println("getInstance : " + path);
		if (false && contains(path)) {
			System.out.println("getInstance : returning instance");
			return get(path);
		} else {
			System.out.println("getInstance : creating instance");
			CommentService cs = null;
			try {
				cs = new CommentServiceImpl(_file);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			csMap.put(path, cs);
			return cs;
		}
	}
	
	
	private static boolean contains(String _name) {
		for (String key : csMap.keySet()) {
			System.out.println("contains :" + key + " <> " + _name);
			if (key.equals(_name)) {
				return true;
			}
		}
		return false;
	}
	private static CommentService get(String _name) {
		for (String key : csMap.keySet()) {
			System.out.println("get :" + key + " <> " + _name);
			if (key.equals(_name)) {
				return csMap.get(key);
			}
		}
		return null;
	}
	
}
