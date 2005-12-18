package net.addictivesoftware.framed.components;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.Tapestry;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectory;

public abstract class Exif extends BaseComponent {

    public abstract String getImage();
	
    public abstract String getTags();
    
    public abstract String getMode();
    

	/**
     * Renders the &lt;img&gt; element.
     */

    protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle)
    {
        // Doesn't contain a body so no need to do anything on rewind (assumes no
        // sideffects to accessor methods via bindings).

        if (cycle.isRewinding())
           return;

    	System.out.println("GETTAGS:" + getTags());
    	
        String sImageURL = getImage();
        if (sImageURL == null)
            throw Tapestry.createRequiredParameterException(this, "image");

        writer.begin("table");
        writer.attribute("border", "0");
        writer.attribute("class", "exif");
        
        File file = new File(sImageURL);
        if (file.exists() && !file.isDirectory()) {
            Metadata metadata;
    		try {
    			metadata = JpegMetadataReader.readMetadata(file);
    	 		Directory exifDir = metadata.getDirectory(ExifDirectory.class);
    
    	 		System.out.println(getMode());
    	 		if ("short".equals(getMode())) {
    	 			writeDefinedTags(writer, exifDir);
    	 		} else if ("list".equals(getMode())) {
    	 			writeSomeTags(writer, exifDir);
    	 		} else {
    	 			writeAllTags(writer, exifDir);
    	 		}
    		} catch (JpegProcessingException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        	
        }
        

//         renderInformalParameters(writer, cycle);

        writer.end("table");
    }

    private void writeAllTags(IMarkupWriter writer, Directory exifDir) {
		Iterator tags = exifDir.getTagIterator();
		int cnt = 0;
		while  (tags.hasNext()) {
			Tag tag = (Tag)tags.next();
			writer.begin("tr");
			if ((cnt % 2) == 0) {
		        writer.attribute("class", "exif-even");
			} else {
		        writer.attribute("class", "exif-odd");
			}
			cnt++;
			
			writer.begin("td");
	        writer.attribute("class", "exif");
			 writer.print(tag.getTagName());
			writer.end("td");
			
			writer.begin("td");
	        writer.attribute("class", "exif");
			try {
				writer.print(tag.getDescription());
			} catch (MetadataException e) {
				writer.print("nada");
				e.printStackTrace();
			}
			writer.end("td");
	        writer.end("tr");
				
		}
    	
    }
    
    private void writeSomeTags(IMarkupWriter writer, Directory exifDir) {
		Iterator tags = exifDir.getTagIterator();
		int cnt = 0;
		while  (tags.hasNext()) {
			Tag tag = (Tag)tags.next();
			if (null == getTags() || (null != getTags() && showTag(tag))) {
				writer.begin("tr");
				if ((cnt % 2) == 0) {
    		        writer.attribute("class", "exif-even");
				} else {
    		        writer.attribute("class", "exif-odd");
				}
				cnt++;
				
				writer.begin("td");
		        writer.attribute("class", "exif");
				 writer.print(tag.getTagName());
				writer.end("td");
				
				writer.begin("td");
		        writer.attribute("class", "exif");
				try {
					writer.print(tag.getDescription());
				} catch (MetadataException e) {
					writer.print("nada");
					e.printStackTrace();
				}
				writer.end("td");
		        writer.end("tr");
				
			}
		}
    	
    }

    private void writeDefinedTags(IMarkupWriter writer, Directory exifDir) {
    	ArrayList<Integer> aList = new ArrayList<Integer>();
    	aList.add(ExifDirectory.TAG_MAKE);
    	aList.add(ExifDirectory.TAG_MODEL);
    	aList.add(ExifDirectory.TAG_DATETIME_ORIGINAL);
    	aList.add(ExifDirectory.TAG_EXPOSURE_TIME);
    	aList.add(ExifDirectory.TAG_APERTURE);
    	aList.add(ExifDirectory.TAG_X_RESOLUTION);
    	aList.add(ExifDirectory.TAG_Y_RESOLUTION);
    	aList.add(ExifDirectory.TAG_FLASH);
    	aList.add(ExifDirectory.TAG_ISO_EQUIVALENT);
    	aList.add(ExifDirectory.TAG_METERING_MODE);
    	aList.add(ExifDirectory.TAG_ORIENTATION);
    	aList.add(ExifDirectory.TAG_DOCUMENT_NAME);
    	Iterator it = aList.iterator();
    	int cnt=0;
    	while (it.hasNext()) {
    		int tagId = ((Integer)it.next()).intValue();
			writer.begin("tr");
			if ((cnt % 2) == 0) {
		        writer.attribute("class", "exif-even");
			} else {
		        writer.attribute("class", "exif-odd");
			}
			cnt++;
			
			writer.begin("td");
	        writer.attribute("class", "exif");
			 writer.print(exifDir.getTagName(tagId));
			writer.end("td");
			
			writer.begin("td");
	        writer.attribute("class", "exif");
			try {
				writer.print(exifDir.getDescription(tagId));
			} catch (MetadataException e) {
				writer.print("nada");
				e.printStackTrace();
			}
			writer.end("td");
	        writer.end("tr");
    		
    	}
    }

	private boolean showTag(Tag tag) {
		String tagName = tag.getTagName();
		Iterator it = getTagList().iterator();
		while (it.hasNext()) {
			String showTag = (String)it.next();
			if (tagName.toLowerCase().replace(" ", "_").trim().equals(showTag.trim())) {
				return true;
			}
		}
		return false;
	}

	private ArrayList<String> getTagList() {
		ArrayList<String> aList = new ArrayList<String>();
		if (null != getTags()) {
			StringTokenizer st = new StringTokenizer(getTags(), ",");
			while (st.hasMoreTokens()) {
				aList.add(st.nextToken());
			}
		}
		return aList;
	}

}
