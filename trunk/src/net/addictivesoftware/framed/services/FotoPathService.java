package net.addictivesoftware.framed.services;

public interface FotoPathService {
	public abstract String getPath();
	public abstract void setPath(String _path);
	public abstract String getCurrentPath(String _sessionID);
	public abstract void setCurrentPath(String _sessionID, String _path);
}