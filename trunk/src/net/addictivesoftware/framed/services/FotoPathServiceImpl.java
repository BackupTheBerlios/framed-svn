package net.addictivesoftware.framed.services;


public class FotoPathServiceImpl implements FotoPathService {
	private String path = "";
	private String currentPath = "";

	public String getPath() {
		return path;
	}

	public void setPath(String _path) {
		this.path = _path;
		// if path is set also reset current path
		this.currentPath = _path;
	}

	public String getCurrentPath() {
		return currentPath;
	}

	public void setCurrentPath(String _currentPath) {
		this.currentPath = _currentPath;
	}
	
}
