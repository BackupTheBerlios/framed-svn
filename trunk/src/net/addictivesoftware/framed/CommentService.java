package net.addictivesoftware.framed;

import java.util.List;

import javax.xml.xpath.XPathExpressionException;

public interface CommentService {

	public abstract String getComment(String _photoName)
			throws XPathExpressionException;

	public abstract String getViewRights(String _photoName)
			throws XPathExpressionException;

	public abstract List<String> getFilesForViewRight(String _right);

	public abstract String getDirComment() throws XPathExpressionException;
	
	public abstract void setDirComment(String _comment);
	public abstract void setComment(String _name, String _comment);
}