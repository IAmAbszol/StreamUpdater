package streamupdater.gui.components.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/*
 * Class utilized to be a save for each game stream positions, etc
 */
public class RenderObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
	
	public static void save(RenderObject ro) {
		try {
			File f = new Selection().selectedSave();
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(ro);
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
	
	private static String findFile(String name) {
		String user = System.getProperty("user.name");
		if(System.getProperty("os.name").contains("Windows")) {
			return "C:/Users/"+user+"/AppData/Roaming/"+ name;
		} else
			return System.getProperty("user.home") + "/" + name;
	}

}
