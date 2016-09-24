package streamupdater.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;

import streamupdater.gui.components.render.RenderObject;
import streamupdater.gui.components.render.Selection;

public class RenderSave implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String renderPath = null;
	private ArrayList<Integer> startingPositions;
	private ArrayList<Integer> durations;
	private ArrayList<String> fileNames;
	private transient ArrayList<BufferedImage> thumbnails;
	private ArrayList<String> imageFile;
	
	public RenderSave() {
		
	}
	
	public ArrayList<Integer> getStartingPositions() {
		return startingPositions;
	}
	
	public ArrayList<Integer> getDurations() {
		return durations;
	}
	
	public ArrayList<String> getImageFileNames() {
		return imageFile;
	}
	
	public ArrayList<BufferedImage> getImages() {
		return thumbnails;
	}
	
	public ArrayList<String> getFileNames() {
		return fileNames;
	}
	
	public String getStreamURL() {
		return renderPath;
	}
	
	public void setStreamURL(String s) {
		renderPath = s;
	}
	
	public void setStartingPositions(ArrayList<Integer> i) {
		startingPositions = i;
	}
	
	public void setDurations(ArrayList<Integer> i) {
		durations = i;
	}
	
	public void setFileNames(ArrayList<String> i) {
		fileNames = i;
	}

	public void setThumbnails(ArrayList<BufferedImage> i) {
		thumbnails = i;
	}
	
	public void setImageFile(ArrayList<String> i) {
		imageFile = i;
	}
	
	public void save(String s, ArrayList<Integer> start, ArrayList<Integer> dur, ArrayList<String> fileName, ArrayList<BufferedImage> images, ArrayList<String> imageFile) {
		this.setStreamURL(s);
		this.setStartingPositions(start);
		this.setDurations(dur);
		this.setFileNames(fileName);
		this.setThumbnails(null);
		this.setImageFile(imageFile);
		try {
			DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
			// Get the date today using Calendar object.
			Date today = Calendar.getInstance().getTime();        
			// Using DateFormat format method we can create a string 
			// representation of a date with the defined format.
			String reportDate = df.format(today).replace(" ", "-").replace(":", "-").replace("\\", "-");
			File f = new File(findDesktop() + reportDate + ".sobj");		// returns desktop location
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			this.setThumbnails(images);
			oos.writeInt(this.getImages().size());
			for(BufferedImage eachImage : this.getImages()) {
				ImageIO.write(eachImage, "png", oos);
			}
			oos.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void load() {
		try {
			RenderSave ro = null;
			File f = new Selection().selectedLoad();		// opens a jfilechooser to select the file/object being loaded
			if(f != null) {
				FileInputStream fis = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fis);
				ro = (RenderSave) ois.readObject();
				final int imageCount = ois.readInt();
				thumbnails = new ArrayList<BufferedImage>(imageCount);
				for(int i = 0; i < imageCount; i++) {
					thumbnails.add(ImageIO.read(ois));
				}
				ois.close();
			}
			if(ro == null) return;
			this.setStreamURL(ro.getStreamURL());
			this.setDurations(ro.getDurations());
			this.setStartingPositions(ro.getStartingPositions());
			this.setFileNames(ro.getFileNames());
			this.setThumbnails(thumbnails);			// paranoid
			this.setImageFile(ro.getImageFileNames());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String findDesktop() {
		String user = System.getProperty("user.name");
		if(System.getProperty("os.name").contains("Windows")) {
			return "C:/Users/"+user+"/Desktop/";
		} else
			return System.getProperty("user.home") + "/Desktop/";
	}
	
	private String findFile(String name) {
		String user = System.getProperty("user.name");
		if(System.getProperty("os.name").contains("Windows")) {
			return "C:/Users/"+user+"/AppData/Roaming/"+ name;
		} else
			return System.getProperty("user.home") + "/" + name;
	}
	
	
}
