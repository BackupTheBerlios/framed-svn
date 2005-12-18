/*
 * Created on 18-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.addictivesoftware.framed;

import java.io.File;
import java.util.Iterator;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectory;

/**
 * @author gassies
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PhotoExif {
	private Metadata metadata = null;
	/**
	 * @param _path
	 */
	public PhotoExif(File _file) {
		try {
			metadata = JpegMetadataReader.readMetadata(_file);
		} catch (JpegProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String toString() {
		if (null == metadata) {
			return "no exif info available";
		} else {
				return getFormattedData();
		}
	}
	
	private String getFormattedData() {
		StringBuffer sb = new StringBuffer();

 		Directory exifDir = metadata.getDirectory(ExifDirectory.class);
		Iterator tags = exifDir.getTagIterator();
		while  (tags.hasNext()) {
			Tag tag = (Tag)tags.next();
			sb.append(tag.getTagName() + " : ");
			try {
				sb.append(tag.getDescription() + "\r\n");
			} catch (MetadataException e) {
				sb.append("nada\r\n");
				e.printStackTrace();
			}
		}
		
		return sb.toString();
	
	}
}
