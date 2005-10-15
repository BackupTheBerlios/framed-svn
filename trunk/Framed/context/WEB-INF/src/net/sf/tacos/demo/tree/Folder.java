package net.sf.tacos.demo.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author phraktle
 */
public class Folder extends Item {

	private final List folders = new ArrayList();
	private final List items = new ArrayList();

	public Folder(String name) {
		super(name);
	}

	public Folder(Folder parent, String name) {
		super(parent, name);
	}

	public Folder folder(String name) {
		Folder f = new Folder(this, name);
		folders.add(f);
		return f;
	}

	public Folder item(String name) {
		Item i = new Item(this, name);
		items.add(i);
		return this;
	}

	public List getFolders() {
		return folders;
	}

	public List getItems() {
		return items;
	}

	public String toString() {
		return "Folder " + getName();
	}

}