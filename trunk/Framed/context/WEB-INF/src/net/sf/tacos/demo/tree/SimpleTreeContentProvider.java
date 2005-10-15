package net.sf.tacos.demo.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.sf.tacos.model.ITreeContentProvider;

/**
 * @author phraktle
 */
public class SimpleTreeContentProvider implements ITreeContentProvider {

	private final static List ROOTS = new ArrayList();
	static {
		Folder root = new Folder("Root");
		Folder f1 = root.folder("One").item("A1").item("A2").item("A3");
		f1.folder("One/One").item("B1").item("B2").item("B3");
		f1.folder("One/Two");
		root.folder("Two");
		ROOTS.add(root);
	}

	public Collection getElements() {
		return ROOTS;
	}

	public boolean hasChildren(Object parentElement) {
		if (parentElement instanceof Folder) {
			Folder folder = (Folder) parentElement;
			return !(folder.getFolders().isEmpty() && folder.getItems().isEmpty());
		}
		return false;
	}

	public Collection getChildren(Object parentElement) {
		if (parentElement instanceof Folder) {
			Folder folder = (Folder) parentElement;
			List l = new ArrayList();
			l.addAll(folder.getFolders());
			l.addAll(folder.getItems());
			return l;
		}
		return Collections.EMPTY_LIST;
	}

	public Object getParent(Object childElement) {
		return ((Item) childElement).getParent();
	}
}