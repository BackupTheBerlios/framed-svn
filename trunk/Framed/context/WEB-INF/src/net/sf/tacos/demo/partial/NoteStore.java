package net.sf.tacos.demo.partial;

import java.io.Serializable;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

public class NoteStore implements Serializable {
	/** SerialverUID */
	private static final long serialVersionUID = -5128943499710844218L;
	
	private SortedMap notes = new TreeMap();
	private long idGen = 1;
	
	public Collection getAllNotes() {
		return notes.values();
	}

	public Note getNote(Long id) {
		return (Note) notes.get(id);
	}

	public Note createNote() {
		Note note = new Note();
		note.setId(new Long(idGen++));
		return note;
	}

	public void storeNote(Note note) {
		notes.put(note.getId(), note);
	}

	public void removeNote(Long id) {
		this.notes.remove(id);
	}
}
