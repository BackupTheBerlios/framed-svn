package net.addictivesoftware.framed.services;


/**
 * @author gertjan
 *
 * 
 */
public interface ThumbNailService {


	/**
	 * @param _basePath
	 * @param _name
	 * @return
	 */
	public abstract String create(String context, String _basePath, String _name);
	/**
	 * @param _basePath
	 * @param _name
	 * @param _w
	 * @param _h
	 * @return path to thumbnail
	 */
	public abstract String create(String context, String _basePath, String _name, int _w, int _h);
	/**
	 * @param _basePath
	 * @param _name
	 * @param _w
	 * @param _h
	 * @param _q
	 * @return path to thumbnail
	 */
	public abstract String create(String context, String _basePath, String _name, int _w, int _h, int _q);

	/**
	 * @return the quality of the jpg
	 */
	public int getQuality() ;
	/**
	 * @param quality
	 */
	public void setQuality(int quality) ;

	/**
	 * @return
	 */
	public int getThumbHeight() ;
	/**
	 * @param thumbHeight
	 */
	public void setThumbHeight(int thumbHeight) ;
	
	/**
	 * @return
	 */
	public int getThumbWidth() ;
	/**
	 * @param thumbWidth
	 */
	public void setThumbWidth(int thumbWidth) ;

	/**
	 * @param imageName
	 * @param createPath
	 * @return
	 */
	public String getThumbName(String imageName, boolean createPath) ;
	
	/**
	 * @return
	 */
	public String getThumbPath();
	/**
	 * @param thumbPath
	 */
	public void setThumbPath(String thumbPath);
}