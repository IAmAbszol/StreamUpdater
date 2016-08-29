package streamupdater.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class FilesTabSave implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<String> save;
	
	public FilesTabSave() {}
	
	public FilesTabSave(ArrayList<String> saveStuff) {
		
		save = saveStuff;
		save();
		
	}
	
	private void save() {
		try {
			
			File f = new File(findFile("filesconfig.cfg"));
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.close();	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static FilesTabSave load() {
		try {
			FilesTabSave fts = null;
			File f = new File(findFile("filesconfig.cfg"));
			if(f != null) {
				FileInputStream fis = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fis);
				fts = (FilesTabSave) ois.readObject();
				ois.close();
			}
			return fts;
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
	
	public ArrayList<String> getList() {
		return save;
	}

}
