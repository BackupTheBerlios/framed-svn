package net.addictivesoftware.framed.services;

public interface ThumbNailService {

	public abstract String create(String _name);
	public abstract String create(String _name, int _w, int _h);
	public abstract String create(String _name, int _w, int _h, int _q);

	public int getQuality() ;
	public void setQuality(int quality) ;

	public int getThumbHeight() ;
	public void setThumbHeight(int thumbHeight) ;
	
	public int getThumbWidth() ;
	public void setThumbWidth(int thumbWidth) ;

	public String getThumbName(String imageName) ;
}