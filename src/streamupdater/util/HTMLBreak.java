package streamupdater.util;

import java.io.File;
import java.io.PrintWriter;

import streamupdater.gui.components.StreamUpdaterTab;

public class HTMLBreak {
	
	private String[] creationFiles = SavingFileConfiguration.getFiles();
	private String[] fileContents = SavingFileConfiguration.getContents();
	
	private SavingFileConfiguration sfc;
	private String PATH = null;
	private String dirName = "HTML";
	
	public HTMLBreak(String path, SavingFileConfiguration transfer) {
		PATH = path;
		sfc = transfer;
	}
	
	public void writeToScores() {
		  try {
			  
			  PrintWriter writer = null;
			  
			  writer = new PrintWriter(this.PATH + dirName + "/" + this.creationFiles[4]);
		      writer.println("<html>");
		      writer.println(sfc.getPlayerOneScore());
		      writer.print("</html>");
		      writer.close();
		      
		      writer = new PrintWriter(this.PATH + dirName + "/" + this.creationFiles[5]);
		      writer.println("<html>");
		      writer.println(sfc.getPlayerTwoScore());
		      writer.print("</html>");
		      writer.close();
			  
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	  }
	  
	  public void writeToFiles()
	  {
	    try
	    {
	      PrintWriter writer = null;
	      
	      writer = new PrintWriter(this.PATH + dirName + "/" + this.creationFiles[0]);
	      writer.println("<html>");
	      writer.println(sfc.getMainTitle());
	      writer.print("</html>");
	      writer.close();
	      
	      writer = new PrintWriter(this.PATH + dirName + "/" + this.creationFiles[1]);
	      writer.println("<html>");
	      writer.print(sfc.getCurrentRound());
	      writer.print("</html>");
	      writer.close();
	      
	      writer = new PrintWriter(this.PATH + dirName + "/" + this.creationFiles[2]);
	      writer.println("<html>");
	      writer.println(sfc.getPlayerOne());
	      if(!StreamUpdaterTab.getRestriction()) {
		      writer.println("<br>");
		      writer.println(sfc.getPlayerOneInfo());
	      }
	      writer.print("</html>");
	      writer.close();
	      
	      writer = new PrintWriter(this.PATH + dirName + "/" + this.creationFiles[3]);
	      writer.println("<html>");
	      writer.println(sfc.getPlayerTwo());
	      if(!StreamUpdaterTab.getRestriction()) {
		      writer.println("<br>");
		      writer.println(sfc.getPlayerTwoInfo());
	      }
	      writer.print("</html>");
	      writer.close();
	      
	      writer = new PrintWriter(this.PATH + dirName + "/" + this.creationFiles[4]);
	      writer.println("<html>");
	      writer.println(sfc.getPlayerOneScore());
	      writer.print("</html>");
	      writer.close();
	      
	      writer = new PrintWriter(this.PATH + dirName + "/" + this.creationFiles[5]);
	      writer.println("<html>");
	      writer.println(sfc.getPlayerTwoScore());
	      writer.print("</html>");
	      writer.close();
	      
	      writer = new PrintWriter(this.PATH + dirName + "/" + this.creationFiles[6]);
	      writer.println("<html>");
	      writer.println(sfc.getCommentators()[0]);
	      writer.println("<br>");
	      writer.println(sfc.getCommentatorsInfo()[0]);
	      writer.print("</html>");
	      writer.close();
	      
	      writer = new PrintWriter(this.PATH + dirName + "/" + this.creationFiles[7]);
	      writer.println("<html>");
	      writer.println(sfc.getCommentators()[1]);
	      writer.println("<br>");
	      writer.println(sfc.getCommentatorsInfo()[1]);
	      writer.print("</html>");
	      writer.close();
	      
	      writer = new PrintWriter(this.PATH + dirName + "/" + this.creationFiles[14]);
	      writer.println("<html>");
	      writer.println(sfc.getPlayerOneCharacterText());
	      writer.print("</html>");
	      writer.close();
	      
	      writer = new PrintWriter(this.PATH + dirName + "/" + this.creationFiles[15]);
	      writer.println("<html>");
	      writer.println(sfc.getPlayerTwoCharacterText());
	      writer.print("</html>");
	      writer.close();
	      
	    }
	    catch (Exception localException) {}
	  }
	
	public void createFiles()
	  {
	    File f = null;
	    PrintWriter writer = null;
	    
	    File dir = new File(this.PATH + dirName);
	    if(!dir.exists()) dir.mkdir();
	    
	    for (int i = 0; i < this.creationFiles.length; i++)
	    {
	      f = new File(this.PATH + dirName + "/" + this.creationFiles[i]);
	      if (!f.exists()) {
	        try
	        {
	          f.createNewFile();
	          writer = new PrintWriter(this.PATH  + dirName + "/" + this.creationFiles[i]);
	          writer.print(this.fileContents[i]);
	        }
	        catch (Exception localException) {}
	      }
	      f = null;
	    }
	  }

}
