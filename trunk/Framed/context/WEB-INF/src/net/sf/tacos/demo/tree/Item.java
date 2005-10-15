package net.sf.tacos.demo.tree;

/**
 * @author phraktle
 */
public class Item {

	private final Folder parent;
	private final String name;

	public Item(String name) {
		this(null, name);
	}

	public Item(Folder parent, String name) {
		this.parent = parent;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Folder getParent() {
		return parent;
	}
	
	public String toString() {
		return "Item " + getName();
	}

}