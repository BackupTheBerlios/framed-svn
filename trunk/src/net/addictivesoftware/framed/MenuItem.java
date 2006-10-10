package net.addictivesoftware.framed;

public class MenuItem {
	private String name;
	private String path;

	public MenuItem(String _name, String _path) {
		this.name = _name;
		if (_path.equals("")) {
			this.path = null;
		} else { 
			this.path = _path;
		}
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
