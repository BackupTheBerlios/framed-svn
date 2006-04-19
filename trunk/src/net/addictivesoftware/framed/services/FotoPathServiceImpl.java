package net.addictivesoftware.framed.services;

import java.util.HashMap;


public class FotoPathServiceImpl implements FotoPathService {
	private String path = "";
	private HashMap<String, String> currentPaths = new HashMap<String, String>();

	public String getPath() {
		return path;
	}

	public void setPath(String _path) {
		this.path = _path;
	}

	public String getCurrentPath(String _sessionId) {
		if (this.currentPaths.containsKey(_sessionId)) {
			return currentPaths.get(_sessionId);
		} else {
			this.setCurrentPath(_sessionId, getPath());
			return getPath();
		}
	}

	public void setCurrentPath(String _sessionId, String _currentPath) {
		this.currentPaths.put(_sessionId, _currentPath);
	}
	
}
