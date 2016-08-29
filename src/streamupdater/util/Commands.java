package streamupdater.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import streamupdater.gui.components.FileRenamerTab;
import streamupdater.gui.components.FilesTab;

public class Commands {
	
	public static String interpretString(String n) {
		
		if(!FileRenamerTab.isStreamEmpty() && !FilesTab.getTextFolder().equals("")) {
			
			if(n.contains("PLAYERONENAME")) {
				n = n.replaceAll("PLAYERONENAME", getPlayerName(true));
			}
			
			if(n.contains("PLAYERTWONAME")) {
				n = n.replaceAll("PLAYERTWONAME", getPlayerName(false));
			}
			
			if(n.contains("PLAYERONECHAR")) {
				n = n.replaceAll("PLAYERONECHAR", getCharacters(true));
			}
			
			if(n.contains("PLAYERTWOCHAR")) {
				n = n.replaceAll("PLAYERTWOCHAR", getCharacters(false));
			}
			
			if(n.contains("CURRENTROUND")) {
				n = n.replaceAll("CURRENTROUND", getRound());
			}
			
			if(n.contains("MAINTITLE")) {
				n = n.replaceAll("MAINTITLE", getTitle());
			}
			
			return n;
			
		} else
			
			return n;
		
	}
	
	private static String getCharacters(boolean playerOne) {

		LinkedHashSet<String> set1 = new LinkedHashSet<String>(FileRenamerTab.getPlayerOneCharacters());
        ArrayList<String> tmp1  = new ArrayList<String>(set1);
        LinkedHashSet<String> set2 = new LinkedHashSet<String>(FileRenamerTab.getPlayerTwoCharacters());
        ArrayList<String> tmp2  = new ArrayList<String>(set2);
        
        FileRenamerTab.setPlayerOneChars(tmp1);
        FileRenamerTab.setPlayerTwoChars(tmp2);
 
		
		if(playerOne) {
			
			String tmp = "BLANK";
			if(FileRenamerTab.getPlayerOneCharacters().size() > 0) tmp = "";
			
			for(int i = 0; i < FileRenamerTab.getPlayerOneCharacters().size(); i++) {
				
				if(i == 0) {
					tmp = tmp + FileRenamerTab.getPlayerOneCharacters().get(i);
				} else
					tmp = tmp + "," + FileRenamerTab.getPlayerOneCharacters().get(i);

			}
			
			return tmp;
		
		} else {
			
			String tmp = "BLANK";
			
			if(FileRenamerTab.getPlayerTwoCharacters().size() > 0) tmp = "";
			
			for(int i = 0; i < FileRenamerTab.getPlayerTwoCharacters().size(); i++) {
				
				if(i == 0) {
					tmp = tmp + FileRenamerTab.getPlayerTwoCharacters().get(i);
				} else
					tmp = tmp + "," + FileRenamerTab.getPlayerTwoCharacters().get(i);
				
			}
			
			return tmp;
			
		}
		
	}
	
	private static String getTitle() {
		
		String fileName = SavingFileConfiguration.getFiles()[0];
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(FilesTab.getTextFolder() + "/" + fileName)));
			String line = null;
			while((line = reader.readLine()) != null) {
				return line;
			}
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "BLANK";
		
	}
	
	private static String getRound() {
		
		String fileName = SavingFileConfiguration.getFiles()[1];
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(FilesTab.getTextFolder() + "/" + fileName)));
			String line = null;
			while((line = reader.readLine()) != null) {
				return line;
			}
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "BLANK";
	}
	
	
	private static String getPlayerName(boolean playerOne) {
		
		String fileName = "";
		
		if(playerOne) {
			fileName = SavingFileConfiguration.getFiles()[2];
		} else
			fileName = SavingFileConfiguration.getFiles()[3];
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(FilesTab.getTextFolder() + "/" + fileName)));
			String line = null;
			while((line = reader.readLine()) != null) {
				return line;
			}
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "BLANK";
	}
	
	private String getScore(boolean playerOne) {
		
		String fileName = "";
		
		if(playerOne) {
			fileName = SavingFileConfiguration.getFiles()[4];
		} else
			fileName = SavingFileConfiguration.getFiles()[5];
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(FilesTab.getTextFolder() + "/" + fileName)));
			String line = null;
			while((line = reader.readLine()) != null) {
				return line;
			}
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "0";
		
	}
	
}
