/*
 * Created on 15-dec-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.addictivesoftware.framed.services;


import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.addictivesoftware.utils.Const;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ThumbNailServiceImpl implements ThumbNailService {
		private static final int NAMING_METHOD_T_ = 0;
		private static final int NAMING_METHOD_WIDTH_HEIGHT = 1;
		private static final int SCALE_METHOD_AUTO = 0;
		private static final int SCALE_METHOD_WIDTH = 1;
		private static final int SCALE_METHOD_HEIGHT = 2;
		private static String thumbPath;
		
		private int thumbHeight = 120;
		private int thumbWidth = 120;
		private double thumbRatio = 0.5d;
		private int quality = 60;
		private static int scalingMethod = SCALE_METHOD_AUTO;
		private int namingMethod = NAMING_METHOD_WIDTH_HEIGHT;

		private String basePath;
		private String context;
	
		
		/**
		 * 
		 */
		public ThumbNailServiceImpl() {
			this(120,60, 60);
		}
		/**
		 * @param _w
		 * @param _h
		 * @param _q
		 */
		public ThumbNailServiceImpl(int _w, int _h, int _q) {
			this.quality = _q;
			this.thumbHeight = _h;
			this.thumbWidth = _w;
			this.thumbRatio = (double)thumbWidth / (double)thumbHeight;
		}
		
		public int getQuality() {
			return quality;
		}

		public void setQuality(int quality) {
			this.quality = quality;
		}

		public int getThumbHeight() {
			return thumbHeight;
		}

		public void setThumbHeight(int thumbHeight) {
			this.thumbHeight = thumbHeight;
		}

		public int getThumbWidth() {
			return thumbWidth;
		}

		/* (non-Javadoc)
		 * @see net.addictivesoftware.framed.services.ThumbNailService#setThumbWidth(int)
		 */
		public void setThumbWidth(int thumbWidth) {
			this.thumbWidth = thumbWidth;
		}
		
		/* (non-Javadoc)
		 * @see net.addictivesoftware.framed.services.IThumbNailService#create(java.lang.String)
		 */
		public String create(String _name)  {
			System.out.println("TNSI: name" + _name);
			boolean hasError = false;
			String thumbFileName = "images/nothumb.jpg";
			thumbFileName = getThumbName(_name, true);
			System.out.println("TNSI: thumb" + thumbFileName);
				
			// load image from INFILE
		    if (!exists(thumbFileName)) {
			    Image image = null;
			    
			    try {
			    	image = Toolkit.getDefaultToolkit().getImage(_name);
			    	MediaTracker mediaTracker = new MediaTracker(new Container());
			    	mediaTracker.addImage(image, 0);
					mediaTracker.waitForID(0);
				} catch (InterruptedException e) {

					e.printStackTrace();
					hasError = true;
				} catch (OutOfMemoryError oom) {
					// soOutOfMemoryErrormetimes heap space runs out when creating thumbs
					// this makes sure the thums is deleted
					hasError = true;
					System.err.println("OUT OF MEMORY while creating thumb !!");
				}
			    // determine thumbnail size from WIDTH and HEIGHT
			    int imageWidth = image.getWidth(null);
			    int imageHeight = image.getHeight(null);
			    double imageRatio = (double)imageWidth / (double)imageHeight;
			    switch (scalingMethod) {
			    	case SCALE_METHOD_AUTO:
					    if (thumbRatio < imageRatio) {
					      thumbHeight = (int)(thumbWidth / imageRatio);
					    } else {
					      thumbWidth = (int)(thumbHeight * imageRatio);
					    }
			    		break;
			    	case SCALE_METHOD_WIDTH:
					    thumbHeight = (int)(thumbWidth / imageRatio);
			    		break;
			    	case SCALE_METHOD_HEIGHT:
					    thumbWidth = (int)(thumbHeight * imageRatio);					    	
			    		break;
			    }
			    // draw original image to thumbnail image object and
			    // scale it to the new size on-the-fly
			    BufferedImage thumbImage = new BufferedImage(thumbWidth, 
			      thumbHeight, BufferedImage.TYPE_INT_RGB);
			    Graphics2D graphics2D = thumbImage.createGraphics();
			    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
			      RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			    graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);
			    // save thumbnail image to OUTFILE
			    BufferedOutputStream out;
				try {
					out = new BufferedOutputStream(new FileOutputStream(thumbFileName));
					JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				    JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImage);

				    quality = Math.max(0, Math.min(quality, 100));
				    param.setQuality((float)quality / 100.0f, false);
				    
				    encoder.setJPEGEncodeParam(param);
					encoder.encode(thumbImage);
					out.flush();
					out.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					System.out.println("ThumbNailServiceImpl: " + e.getMessage());
					hasError = true;
				} catch (ImageFormatException e) {
					e.printStackTrace();
					System.out.println("ThumbNailServiceImpl: " + e.getMessage());
					hasError = true;
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("ThumbNailServiceImpl: " + e.getMessage());
					hasError = true;
				} finally {
					if (hasError) {
						System.out.println("error occured -> deleting thumb");
						File file = new File(thumbFileName);
						if (file != null) {
							file.delete();
						}
					}
				}
		    }
			if (hasError) {
				return "images/nothumb.jpg";
			} else {
				return thumbFileName;
			}
		}
		
		/* (non-Javadoc)
		 * @see net.addictivesoftware.framed.services.ThumbNailService#create(java.lang.String, int, int)
		 */
		public String create(String _context, String _basePath, String _name, int _w, int _h) {
			this.setContext(_context);
			this.setBasePath(_basePath);
			this.setThumbWidth(_w);
			this.setThumbHeight(_h);
			this.thumbRatio = (double)thumbWidth / (double)thumbHeight;
			return create(_name);
		}
		/* (non-Javadoc)
		 * @see net.addictivesoftware.framed.services.ThumbNailService#create(java.lang.String, int, int, int)
		 */
		public String create(String _context, String _basePath, String _name, int _w, int _h, int _q) {
			this.setContext(_context);
			this.setBasePath(_basePath);
			this.setThumbWidth(_w);
			this.setThumbHeight(_h);
			this.thumbRatio = (double)thumbWidth / (double)thumbHeight;
			setQuality(_q);
			return create(_name);
		}

		public String create(String _context, String _basePath, String _name) {
			this.setContext(_context);
			this.setBasePath(_basePath);
			return create(_name);
		}
		
		private boolean exists(String _name) {
			File file = new File(_name); 
			return null != file && file.isFile() && file.exists();
		}

		private boolean isThumb(String _name) {
			String filename = _name.substring(_name.lastIndexOf(Const.SEPARATOR)+1);
			String ext = "";
			switch (namingMethod) {
				case NAMING_METHOD_T_:
					ext = "T_"; 
					break;
				case NAMING_METHOD_WIDTH_HEIGHT:
					ext = "T_" + thumbWidth + "_" + thumbHeight;
					break;
			}
			if (filename.startsWith(ext)) {
				return true;
			} else {
				return false;
			}
		}
		/**
		 * @param _name
		 * @return
		 */
		public String getThumbName(String _name, boolean _createPath) {
			String path = _name.substring(this.basePath.length(),_name.lastIndexOf(Const.SEPARATOR)+1).replace(":", "_");
			String filename = _name.substring(_name.lastIndexOf(Const.SEPARATOR)+1);
			String ext = "Unknown_";
			switch (namingMethod) {
				case NAMING_METHOD_T_:
					ext = "T"; 
					break;
				case NAMING_METHOD_WIDTH_HEIGHT:
					ext = "T_" + thumbWidth + "_" + thumbHeight;
					break;
			}
			
			if (_createPath) {
				File dir = new File(getContext() + getThumbPath() + path);
				if (!dir.exists()) {
					dir.mkdirs();
				}
			}
			String out = getContext() + getThumbPath() + path + ext + "_" + filename;
			System.out.println("getThumbName() returns : " + out);
			return out;
		}
		
		public String getThumbPath() {
			return ThumbNailServiceImpl.thumbPath;
		}
		public void setThumbPath(String _thumbPath) {
			ThumbNailServiceImpl.thumbPath = _thumbPath;
		}
		public String getBasePath() {
			return this.basePath;
		}
		public void setBasePath(String basePath) {
			this.basePath = basePath;
		}
		public String getContext() {
			return context;
		}
		public void setContext(String context) {
			this.context = context;
		}

}
