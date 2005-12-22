package net.addictivesoftware.framed;

public class Crumb {
	private String name;
	private String path;
	
	public Crumb(String _name, String _path) {
		this.name = _name;
		this.path = _path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


	
}
