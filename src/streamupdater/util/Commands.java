package streamupdater.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import streamupdater.gui.components.RenderTab;
import streamupdater.gui.components.FilesTab;

public class Commands {
	
	public static String interpretString(String n) {
		
		if(!FilesTab.getTextFolder().equals("")) {
			
			if(n.contains("PLAYERONENAME")) {
				n = n.replace("PLAYERONENAME", getPlayerName(1));
			}
			
			if(n.contains("PLAYERTWONAME")) {
				n = n.replace("PLAYERTWONAME", getPlayerName(2));
			}
			
			if(n.contains("PLAYERTHREENAME")) {
				n = n.replace("PLAYERTHREENAME", getPlayerName(3));
			}
			
			if(n.contains("PLAYERFOURNAME")) {
				n = n.replace("PLAYERFOURNAME", getPlayerName(4));
			}
			
			if(n.contains("TEAM1")) {
				n = n.replace("TEAM1", getTeamName(1));
			}
			
			if(n.contains("TEAM2")) {
				n = n.replace("TEAM2", getTeamName(2));
			}
			
			if(n.contains("PLAYERONECHAR")) {
				n = n.replace("PLAYERONECHAR", getCharacters(true));
			}
			
			if(n.contains("PLAYERTWOCHAR")) {
				n = n.replace("PLAYERTWOCHAR", getCharacters(false));
			}
			
			if(n.contains("CURRENTROUND")) {
				n = n.replace("CURRENTROUND", getRound());
			}
			
			if(n.contains("MAINTITLE")) {
				n = n.replace("MAINTITLE", getTitle());
			}
			
			return removeIllegal(n);
			
		} else
			
			return removeIllegal(n);
		
	}
	
	private static String removeIllegal(String n) {
		char[] illegal = {
				':', '\"', '/', '\\', '|', '?'
		};
		for(int i = 0; i < illegal.length; i++) {
			n = n.replace("" + illegal[i] + " ", "");
		}
		System.out.println("\n\n\nGenerated File " + n + "\n\n\n\n");
		return n;
	}
	
	private static String getCharacters(boolean playerOne) {

		LinkedHashSet<String> set1 = new LinkedHashSet<String>(RenderTab.getPlayerOneCharacters());
        ArrayList<String> tmp1  = new ArrayList<String>(set1);
        LinkedHashSet<String> set2 = new LinkedHashSet<String>(RenderTab.getPlayerTwoCharacters());
        ArrayList<String> tmp2  = new ArrayList<String>(set2);
        
        RenderTab.setPlayerOneChars(tmp1);
        RenderTab.setPlayerTwoChars(tmp2);
 
		
		if(playerOne) {
			
			String tmp = "BLANK";
			if(RenderTab.getPlayerOneCharacters().size() > 0) tmp = "";
			
			for(int i = 0; i < RenderTab.getPlayerOneCharacters().size(); i++) {
				
				if(i == 0) {
					tmp = tmp + RenderTab.getPlayerOneCharacters().get(i);
				} else
					tmp = tmp + "," + RenderTab.getPlayerOneCharacters().get(i);

			}
			
			return tmp;
		
		} else {
			
			String tmp = "BLANK";
			
			if(RenderTab.getPlayerTwoCharacters().size() > 0) tmp = "";
			
			for(int i = 0; i < RenderTab.getPlayerTwoCharacters().size(); i++) {
				
				if(i == 0) {
					tmp = tmp + RenderTab.getPlayerTwoCharacters().get(i);
				} else
					tmp = tmp + "," + RenderTab.getPlayerTwoCharacters().get(i);
				
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
	
	private static String getTeamName(int i) {
		
		String fileName = "";
		
		if(i == 1) 
			fileName = SavingFileConfiguration.getFiles()[18];
		else
		if(i == 2)
			fileName = SavingFileConfiguration.getFiles()[19];
			
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
	
	private static String getPlayerName(int i) {
		
		String fileName = "";
		
		if(i == 1) 
			fileName = SavingFileConfiguration.getFiles()[2];
		else
		if(i == 2)
			fileName = SavingFileConfiguration.getFiles()[3];
		else
		if(i == 3) 
			fileName = SavingFileConfiguration.getFiles()[16];
		else
		if(i == 4)
			fileName = SavingFileConfiguration.getFiles()[17];
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
