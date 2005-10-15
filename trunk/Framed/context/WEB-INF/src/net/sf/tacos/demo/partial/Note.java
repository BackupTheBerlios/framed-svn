package net.sf.tacos.demo.partial;

import java.io.Serializable;

/**
 * Represents a generic Note. 
 * 
 */
public class Note implements Serializable {

	/* serialverUID */
	private static final long serialVersionUID = 962268765310912776L;
	private Long id;
	private String text;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

}
