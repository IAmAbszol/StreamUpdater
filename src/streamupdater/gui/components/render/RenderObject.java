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
public class RenderObject {
	
	private static String renderPath = null;
	private static ArrayList<Integer> startingPositions;
	private static ArrayList<Integer> durations;
	private static ArrayList<String> fileNames;
	private transient static ArrayList<BufferedImage> thumbnails;
	private static ArrayList<String> imageFile;
	
	public RenderObject(String url, 
			ArrayList<Integer> sp,
			ArrayList<Integer> dur, 
			ArrayList<String> fileName,
			ArrayList<BufferedImage> images,
			ArrayList<String> imageName) {
		renderPath = url;
		startingPositions = sp;
		durations = dur;
		fileNames = fileName;
		thumbnails = images;
		imageFile = imageName;
	}
	
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
