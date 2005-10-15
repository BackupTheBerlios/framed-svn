package net.sf.tacos.demo.tree.fs;

import java.io.File;
import java.io.Serializable;

import net.sf.tacos.model.IKeyProvider;

/**
 * @author phraktle
 */
public class FileKeyProvider implements IKeyProvider {

	public final static FileKeyProvider INSTANCE = new FileKeyProvider();

	public Serializable getKey(Object value) {
		return ((File) value).getAbsolutePath();
	}

}