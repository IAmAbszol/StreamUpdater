package streamupdater.gui.components.render;

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

/*
 * Class utilized to be a save for each game stream positions, etc
 */
public class RenderObject implements Serializable {
	
	private static final long serialVersionUID = 2L;
	
	private static String renderPath = null;
	private static ArrayList<Integer> startingPositions;
	private static ArrayList<Integer> durations;
	private static ArrayList<String> fileNames;
	private static ArrayList<BufferedImage> thumbnails;
	private static ArrayList<String> imageFile;
	
	// for serialization
	public RenderObject() {}
	
	public RenderObject(String url) {
		renderPath = url;
		startingPositions = new ArrayList<Integer>();
		durations = new ArrayList<Integer>();
		fileNames = new ArrayList<String>();
		thumbnails = new ArrayList<BufferedImage>();
		imageFile = new ArrayList<String>();
	}
	
	public static ArrayList<Integer> getStartingPositions() {
		return startingPositions;
	}
	
	public static ArrayList<Integer> getDurations() {
		return durations;
	}
	
	public static ArrayList<String> getImageFileNames() {
		return imageFile;
	}
	
	public static ArrayList<BufferedImage> getImages() {
		return thumbnails;
	}
	
	public static ArrayList<String> getFileNames() {
		return fileNames;
	}
	
	public static String getStreamURL() {
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
	
	public void save() {
		try {
			DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

			// Get the date today using Calendar object.
			Date today = Calendar.getInstance().getTime();        
			// Using DateFormat format method we can create a string 
			// representation of a date with the defined format.
			String reportDate = df.format(today).replace(" ", "-").replace(":", "-").replace("\\", "-");
			File f = new File(findDesktop() + reportDate + ".sobj");
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.close();	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static RenderObject load() {
		try {
			RenderObject ro = null;
			File f = new Selection().selectedLoad();
			if(f != null) {
				FileInputStream fis = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fis);
				ro = (RenderObject) ois.readObject();
				ois.close();
			}
			return ro;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String findDesktop() {
		String user = System.getProperty("user.name");
		if(System.getProperty("os.name").contains("Windows")) {
			return "C:/Users/"+user+"/Desktop/";
		} else
			return System.getProperty("user.home") + "/Desktop/";
	}
	
	private static String findFile(String name) {
		String user = System.getProperty("user.name");
		if(System.getProperty("os.name").contains("Windows")) {
			return "C:/Users/"+user+"/AppData/Roaming/"+ name;
		} else
			return System.getProperty("user.home") + "/" + name;
	}

}
