package net.sf.tacos.demo.tree.fs;

import java.io.File;
import java.util.Comparator;

/**
 * @author phraktle
 */
public class FileComparator implements Comparator {

	public final static Comparator INSTANCE = new FileComparator();

	public int compare(Object o1, Object o2) {
		File f1 = (File) o1;
		File f2 = (File) o2;
		if (f1.isDirectory() != f2.isDirectory()) {
			return f1.isDirectory() ? -1 : 1;
		}
		return f1.compareTo(f2);
	}

}