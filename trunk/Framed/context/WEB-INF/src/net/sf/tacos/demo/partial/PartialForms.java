package net.sf.tacos.demo.partial;

import java.util.HashSet;
import java.util.Set;

import net.sf.tacos.ajax.AjaxDirectService;
import net.sf.tacos.ajax.AjaxWebRequest;
import net.sf.tacos.ajax.components.AjaxForm;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.valid.IValidationDelegate;

/**
 * Demonstrates the use of {@link AjaxForm} components that enable
 * the posting of form data without refreshing the entire page.
 *
 * @author phraktle
 */
public abstract class PartialForms extends BasePage {
    
    /**
     * Called by form to store input value.
     * @param cycle
     */
	public void storeNote(IRequestCycle cycle) {
		IValidationDelegate delegate = (IValidationDelegate) getBeans().getBean("delegate");
		if (delegate.getHasErrors()) {
			return;
		}
        
        storeNote(getCurrNote());
		setEditing(getCurrNote().getId(), false);
	}
	
    /**
     * Invoked by edit link.
     * @param cycle
     */
	public void editNote(Long id) {
		setEditing(id, true);
	}
	
    /**
     * Invoked by remove link.
     * @param cycle
     */
	public void removeNote(Long id) {
		setEditing(id, false);
        
        //Force removal of our component on the client
        if (getAjaxEngine() != null 
                && getAjaxEngine().getAjaxRequest().isValidRequest()) {
            AjaxWebRequest ajaxr = getAjaxEngine().getAjaxRequest();
            ajaxr.getResponseBuilder()
            .getScriptWriter()
            .printRaw("<script>"
                    + "setTimeout((function() { Element.remove('note_" + id + "'); }), 10);"
                    + "</script>");
        }
        //Remove from store as well
		NoteStore store = getStore();
		store.removeNote(id);
	}
	
    /**
     * Creates a new note and sets its
     * state to editing.
     * @param cycle
     */
	public void addNote(IRequestCycle cycle) {
        if (getStore() == null) {
            NoteStore store = new NoteStore();
            Set editorIds = new HashSet();
            setStore(store);
            setEditorIds(editorIds);
        }
        
		Note note = getStore().createNote();
		storeNote(note);
		setEditing(note.getId(), true);
	}
	
    /**
     * Utility to persist the selected
     * notes state to the session.
     * @param note
     */
	private void storeNote(Note note) {
		NoteStore store = getStore();
		store.storeNote(note);
	}
	
    /**
     * Sets the state of the specified note
     * to the boolean flag of edit.
     * @param noteId To to set
     * @param edit True if note should be editable, false otherwise
     */
	private void setEditing(Long noteId, boolean edit) {
		Set editIds = getEditorIds();
		if (edit)
			editIds.add(noteId);
		else
			editIds.remove(noteId);
	}
    
    /** Injected engine */
    public abstract AjaxDirectService getAjaxEngine();
    /** Holds notes */
	public abstract NoteStore getStore();
	/** Sets the current store */
	public abstract void setStore(NoteStore store);
	/** Set of notes currently being edited */
	public abstract Set getEditorIds();
	/** Sets notes currently being edited */
	public abstract void setEditorIds(Set edit);
	/** Selects the currently selected note, in the Foreach */
	public abstract Note getCurrNote();
    /** sets the current note */
    public abstract void setCurrNote(Note note);
}
