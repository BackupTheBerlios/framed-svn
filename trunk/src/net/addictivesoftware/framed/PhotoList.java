/*
 * Created on 18-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.addictivesoftware.framed;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gassies
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PhotoList implements IPhotoList  {
	private List<PhotoListEntry> entries;
	public PhotoList() {
		entries = new ArrayList<PhotoListEntry>();
	}
	/* (non-Javadoc)
	 * @see net.addictivesoftware.framed.IPhotoList#addEntry(net.addictivesoftware.framed.PhotoListEntry)
	 */
	public void addEntry(PhotoListEntry entry) {
		entries.add(entry);
	}
	/* (non-Javadoc)
	 * @see net.addictivesoftware.framed.IPhotoList#getEntries()
	 */
	public List getEntries() {
		return entries;
	}
}
