package streamupdater.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import streamupdater.gui.components.StreamUpdaterTab;

public class SavingFileConfiguration
{
  private static String[] contents = { "Main Title", "Current Round", "Player One", "Player Two", "0", "0", "Commentator1", "Commentator2", "null", "null", "null", "null", "null", "null", "", "" };
  private static String[] files = { "maintitle.txt", "round.txt", "player1name.txt", "player2name.txt", "player1score.txt", "player2score.txt", "commentator1.txt", "commentator2.txt", "player1character.png", 
		  					 "player1sponsor.png", "player2character.png", "player2sponsor.png", "commentator1sponsor.png", "commentator2sponsor.png", "playerOneCharacter.txt", "playerTwoCharacter.txt"};
  private int playerTwoScore = 0;
  private String playerTwo = "Player Two";
  private int playerOneScore = 0;
  private String playerOne = "Player One";
  private String playerOneInfo = "@PlayerOne";
  private String playerTwoInfo = "@PlayerTwo";
  private String currentRound = "Current Round";
  private String mainTitle = "Main Title";
  private String commentators[] = { "Commentator1", "Commentator2" };
  private String commentatorsInfo[] = { "@Commentator1", "@Commentator2" };
  private String playerOneCharacterText = "";
  private String playerTwoCharacterText = "";
  private String PATH;
  
  private BufferedImage playerOneCharacter;
  private BufferedImage playerTwoCharacter;
  private BufferedImage playerOneSponsor;
  private BufferedImage playerTwoSponsor;
  private BufferedImage commentatorOneSponsor;
  private BufferedImage commentatorTwoSponsor;
  
  public BufferedImage getPlayerOneCharacter() {
	  return playerOneCharacter;
  }
  
  public BufferedImage getPlayerTwoCharacter() {
	  return playerTwoCharacter;
  }
  
  public String getPlayerOneCharacterText() {
	  return playerOneCharacterText;
  }
  
  public String getPlayerTwoCharacterText() {
	  return playerTwoCharacterText;
  }
  
  public BufferedImage getPlayerOneSponsor() {
	  return playerOneSponsor;
  }
  
  public BufferedImage getPlayerTwoSponsor() {
	  return playerTwoSponsor;
  }
  
  public BufferedImage getCommentatorOneSponsor() {
	  return commentatorOneSponsor;
  }
  
  public BufferedImage getCommentatorTwoSponsor() {
	  return commentatorTwoSponsor;
  }
  
  public void setPlayerOneCharacterText(String n) {
	  playerOneCharacterText = n;
  }
  
  public void setPlayerTwoCharacterText(String n) {
	  playerTwoCharacterText = n;
  }
  
  public void setPlayerOneCharacter(BufferedImage i) {
	  playerOneCharacter = i;
  }
  
  public void setPlayerTwoCharacter(BufferedImage i) {
	  playerTwoCharacter = i;
  }
  
  public void setPlayerOneSponsor(BufferedImage i) {
	  playerOneSponsor = i;
  }
  
  public void setPlayerTwoSponsor(BufferedImage i) {
	  playerTwoSponsor = i;
  }
  
  public void setCommentatorOneSponsor(BufferedImage i) {
	  commentatorOneSponsor = i;
  }
  
  public void setCommentatorTwoSponsor(BufferedImage i) {
	  commentatorTwoSponsor = i;
  }
  
  public int getPlayerOneScore()
  {
    return this.playerOneScore;
  }
  
  public int getPlayerTwoScore()
  {
    return this.playerTwoScore;
  }
  
  public String getMainTitle()
  {
    return this.mainTitle;
  }
  
  public String getCurrentRound()
  {
    return this.currentRound;
  }
  
  public String getPlayerOne()
  {
    return this.playerOne;
  }
  
  public String getPlayerOneInfo() {
	  return this.playerOneInfo;
  }
  
  public String getPlayerTwoInfo() {
	  return this.playerTwoInfo;
  }
  
  public String getPlayerTwo()
  {
    return this.playerTwo;
  }
  
  public String[] getCommentators() {
	  return commentators;
  }
  
  public String[] getCommentatorsInfo() {
	  return commentatorsInfo;
  }
  
  public String getPath()
  {
    return this.PATH;
  }
  
  public void setPlayerOneScore(int i)
  {
    this.playerOneScore = i;
  }
  
  public void setPlayerTwoScore(int i)
  {
    this.playerTwoScore = i;
  }
  
  public void setMainTitle(String n)
  {
    this.mainTitle = n;
  }
  
  public void setCurrentRound(String n)
  {
    this.currentRound = n;
  }
  
  public void setPlayerOne(String n)
  {
    this.playerOne = n;
  }
  
  public void setPlayerTwo(String n)
  {
    this.playerTwo = n;
  }
  
  public void setPlayerOneInfo(String n) {
	  this.playerOneInfo = n;
  }
  
  public void setPlayerTwoInfo(String n) {
	  this.playerTwoInfo = n;
  }
  
  public void setPath(String n)
  {
    this.PATH = n;
  }
  
  public void setCommentators(String com1, String com2) {
	  commentators[0] = com1;
	  commentators[1] = com2;
  }
  
  public void setCommentatorsInfo(String com1, String com2) {
	  commentatorsInfo[0] = com1;
	  commentatorsInfo[1] = com2;
  }
  
  public void increasePlayerOneScore()
  {
    this.playerOneScore += 1;
  }
  
  public void decreasePlayerOneScore()
  {
    this.playerOneScore -= 1;
  }
  
  public void increasePlayerTwoScore()
  {
    this.playerTwoScore += 1;
  }
  
  public void decreasePlayerTwoScore()
  {
    this.playerTwoScore -= 1;
  }
  
  public void createFiles()
  {
    File f = null;
    PrintWriter writer = null;
    for (int i = 0; i < this.files.length; i++)
    {
      f = new File(this.PATH + this.files[i]);
      if (!f.exists()) {
        try
        {
          f.createNewFile();
          writer = new PrintWriter(this.PATH + this.files[i]);
          writer.print(this.contents[i]);
        }
        catch (Exception localException) {}
      }
      f = null;
    }
  }
  
  public void checkFiles()
  {
    createFiles();
  }
  
  public void readFiles()
  {
    try
    {
      String line = null;
      
      BufferedReader reader = new BufferedReader(new FileReader(this.PATH + this.files[0]));
      while ((line = reader.readLine()) != null) {
        this.mainTitle = line;
      }
      reader.close();
      line = null;
      
      reader = new BufferedReader(new FileReader(this.PATH + this.files[1]));
      while ((line = reader.readLine()) != null) {
        this.currentRound = line;
      }
      reader.close();
      line = null;
      
      reader = new BufferedReader(new FileReader(this.PATH + this.files[2]));
      while ((line = reader.readLine()) != null) {
        this.playerOne = line;
        line = reader.readLine();
        if(line != null) this.playerOneInfo = line;
      }
      reader.close();
      line = null;
      
      reader = new BufferedReader(new FileReader(this.PATH + this.files[3]));
      while ((line = reader.readLine()) != null) {
        this.playerTwo = line;
        line = reader.readLine();
        if(line != null) this.playerTwoInfo = line;
      }
      reader.close();
      line = null;
      
      reader = new BufferedReader(new FileReader(this.PATH + this.files[4]));
      while ((line = reader.readLine()) != null) {
        this.playerOneScore = Integer.parseInt(line);
      }
      reader.close();
      line = null;
      
      reader = new BufferedReader(new FileReader(this.PATH + this.files[5]));
      while ((line = reader.readLine()) != null) {
        this.playerTwoScore = Integer.parseInt(line);
      }
      
      reader = new BufferedReader(new FileReader(this.PATH + this.files[6]));
      if((line = reader.readLine()) != null) {
    	  this.commentators[0] = line;
    	  if((line = reader.readLine()) != null) commentatorsInfo[0] = line;
      }
      reader.close();
      reader = new BufferedReader(new FileReader(this.PATH + this.files[7]));
      if((line = reader.readLine()) != null) {
    	  this.commentators[1] = line;
    	  if((line = reader.readLine()) != null) commentatorsInfo[1] = line;
      }
      reader.close();
      
      playerOneCharacter = ImageIO.read(new File(this.PATH + this.files[8]));
      playerOneSponsor = ImageIO.read(new File(this.PATH + this.files[9]));
      playerTwoCharacter = ImageIO.read(new File(this.PATH + this.files[10]));
      playerTwoSponsor = ImageIO.read(new File(this.PATH + this.files[11]));
      commentatorOneSponsor = ImageIO.read(new File(this.PATH + this.files[12]));
      commentatorTwoSponsor = ImageIO.read(new File(this.PATH + this.files[13]));
      
    }
    catch (Exception localException) {}
  }
  
  public void writeToScores() {
	  try {
		  
		  PrintWriter writer = null;
		  HTMLBreak hb = new HTMLBreak(PATH, this);
		  hb.createFiles();
		  hb.writeToScores();
		  
		  writer = new PrintWriter(this.PATH + this.files[4]);
	      writer.print(this.playerOneScore);
	      writer.close();
	      
	      writer = new PrintWriter(this.PATH + this.files[5]);
	      writer.print(this.playerTwoScore);
	      writer.close();
	      
	      hb = null;
		  
	  } catch (Exception e) {
		  e.printStackTrace();
	  }
  }
  
  public void writeToFiles()
  {
    try
    {
    	HTMLBreak hb = new HTMLBreak(PATH, this);
		hb.createFiles();
		hb.writeToFiles();
      PrintWriter writer = null;
      
      writer = new PrintWriter(this.PATH + this.files[0]);
      writer.print(this.mainTitle);
      writer.close();
      
      writer = new PrintWriter(this.PATH + this.files[1]);
      writer.print(this.currentRound);
      writer.close();
      
      writer = new PrintWriter(this.PATH + this.files[2]);
      if(!StreamUpdaterTab.getRestriction()) {
    	  writer.println(this.playerOne);
    	  writer.print(this.playerOneInfo);
      } else {
    	  writer.print(this.playerOne);
      }
      writer.close();
      
      writer = new PrintWriter(this.PATH + this.files[3]);
      if(!StreamUpdaterTab.getRestriction()) {
    	  writer.println(this.playerTwo);
    	  writer.print(this.playerTwoInfo);
      } else {
    	  writer.print(this.playerTwo);
      }
      writer.close();
      
      writer = new PrintWriter(this.PATH + this.files[4]);
      writer.print(this.playerOneScore);
      writer.close();
      
      writer = new PrintWriter(this.PATH + this.files[5]);
      writer.print(this.playerTwoScore);
      writer.close();
      
      writer = new PrintWriter(this.PATH + this.files[6]);
      writer.println(commentators[0]);
      writer.print(commentatorsInfo[0]);
      writer.close();
      
      writer = new PrintWriter(this.PATH + this.files[7]);
      writer.println(commentators[1]);
      writer.print(commentatorsInfo[1]);
      writer.close();
      
      writer = new PrintWriter(this.PATH + this.files[14]);
      writer.println(playerOneCharacterText);
      writer.close();
      
      writer = new PrintWriter(this.PATH + this.files[15]);
      writer.println(playerTwoCharacterText);
      writer.close();
      
      // first declare a file, this is to our output
      File f;
      
      f = new File(this.PATH + this.files[8]);
      ImageIO.write(playerOneCharacter, "png", f);
      
      f = new File(this.PATH + this.files[9]);
      ImageIO.write(playerOneSponsor, "png", f);
      
      f = new File(this.PATH + this.files[10]);
      ImageIO.write(playerTwoCharacter, "png", f);
      
      f = new File(this.PATH + this.files[11]);
      ImageIO.write(playerTwoSponsor, "png", f);
      
      f = new File(this.PATH + this.files[12]);
      ImageIO.write(commentatorOneSponsor, "png", f);
      
      f = new File(this.PATH + this.files[13]);
      ImageIO.write(commentatorTwoSponsor, "png", f);
      
      hb = null;
      
    }
    catch (Exception localException) {}
  }
  
  public static String[] getFiles() {
	  return files;
  }
  
  public static String[] getContents() {
	  return contents;
  }
  
}
