package streamupdater.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import streamupdater.gui.components.StreamUpdaterTab;

public class SavingFileConfiguration
{
  private static String[] contents = { "Main Title", "Current Round", "Player One", "Player Two", "0", "0", "Commentator1", "Commentator2", "null", "null", "null", "null", "null", "null", "", "", "Player Three", "Player Four", "Team 1", "Team 2", "null", "null", "", ""};
  private static String[] files = { "maintitle.txt", "round.txt", "player1name.txt", "player2name.txt", "player1score.txt", "player2score.txt", "commentator1.txt", "commentator2.txt", "player1character.png", 
		  					 "player1sponsor.png", "player2character.png", "player2sponsor.png", "commentator1sponsor.png", "commentator2sponsor.png", "playerOneCharacter.txt", "playerTwoCharacter.txt", "player3name.txt", "player4name.txt", "team1name.txt", "team2name.txt", "player3character.png", "player4character.png", "playerThreeCharacter.txt", "playerFourCharacter.txt"};
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
  
  private static boolean sleeping = false;
  
  /*
   * Doubles
   */
  private String playerThree = "Player Three";
  private String playerThreeInfo = "@PlayerThree";
  private String playerThreeCharacterText = "";
  private String playerFour = "Player Four";
  private String playerFourInfo = "@PlayerFour";
  private String playerFourCharacterText = "";
  private String teamOne = "Team 1";
  private String teamTwo = "Team 2";
  private String teamCombineCharacter = "&";
  private BufferedImage playerThreeCharacter;
  private BufferedImage playerFourCharacter;
  
  
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
  
  public BufferedImage getPlayerThreeCharacter() {
	  return playerThreeCharacter;
  }
  
  public BufferedImage getPlayerFourCharacter() {
	  return playerFourCharacter;
  }
  
  public String getPlayerOneCharacterText() {
	  return playerOneCharacterText;
  }
  
  public String getPlayerTwoCharacterText() {
	  return playerTwoCharacterText;
  }
  
  public String getPlayerThreeCharacterText() {
	  return playerThreeCharacterText;
  }
  
  public String getPlayerFourCharacterText() {
	  return playerFourCharacterText;
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
  
  public void setPlayerThreeCharacterText(String n) {
	  playerThreeCharacterText = n;
  }
  
  public void setPlayerFourCharacterText(String n) {
	  playerFourCharacterText = n;
  }
  
  public void setTeamCombineCharacter(String n) {
	  this.teamCombineCharacter = n;
  }
  
  public void setPlayerOneCharacter(BufferedImage i) {
	  playerOneCharacter = i;
  }
  
  public void setPlayerTwoCharacter(BufferedImage i) {
	  playerTwoCharacter = i;
  }
  
  public void setPlayerThreeCharacter(BufferedImage i) {
	  playerThreeCharacter = i;
  }
  
  public void setPlayerFourCharacter(BufferedImage i) {
	  playerFourCharacter = i;
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
  
  public String getPlayerThree() {
	  return playerThree;
  }
  
  public String getPlayerThreeInfo() {
	  return playerThreeInfo;
  }
  
  public String getPlayerFour() {
	  return playerFour;
  }
  
  public String getPlayerFourInfo() {
	  return playerFourInfo;
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
    if(n.equals(""))
    	this.playerOne = StreamUpdaterTab.getPlayerOneName();
  }
  
  public void setPlayerTwo(String n)
  {
    this.playerTwo = n;
    if(n.equals(""))
    	this.playerTwo = StreamUpdaterTab.getPlayerTwoName();
  }
  
  public void setPlayerOneInfo(String n) {
	  this.playerOneInfo = n;
  }
  
  public void setPlayerTwoInfo(String n) {
	  this.playerTwoInfo = n;
  }
  
  public void setPlayerThree(String n) {
	  playerThree = n;
  }
  
  public void setPlayerFour(String n) {
	  playerFour = n;
  }
  
  public void setPlayerThreeInfo(String n) {
	  playerThreeInfo = n;
  }
  
  public void setPlayerFourInfo(String n) {
	  playerFourInfo = n;
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
  
  public void setTeamOne(String one, String con, String two) {
	  if(one.equals("") || one.equals(" ")) one = playerOne;
	  if(two.equals("") || two.equals(" ")) two = playerTwo;
	  teamOne = one + " " + con + " " + two;
  }
  
  public void setTeamTwo(String one, String con, String two) {
	  if(one.equals("") || one.equals(" ")) one = playerOne;
	  if(two.equals("") || two.equals(" ")) two = playerTwo;
	  teamTwo = one + " " + con + " " + two;
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
      reader.close();
      
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
      
      /*
       * Doubles
       */
      
      reader = new BufferedReader(new FileReader(this.PATH + this.files[16]));
      if((line = reader.readLine()) != null) {
    	  playerThree = line;
    	  if(line != null) playerThreeInfo = line;
      }
      reader.close();
      
      reader = new BufferedReader(new FileReader(this.PATH + this.files[17]));
      if((line = reader.readLine()) != null) {
    	  playerFour = line;
    	  if(line != null) playerFourInfo = line;
      }
      reader.close();
      
      reader = new BufferedReader(new FileReader(this.PATH + this.files[18]));
      if((line = reader.readLine()) != null) {
    	  teamOne = line;
      }
      reader.close();
      
      reader = new BufferedReader(new FileReader(this.PATH + this.files[19]));
      if((line = reader.readLine()) != null) {
    	 teamTwo = line;
      }
      reader.close();
      
      playerThreeCharacter = ImageIO.read(new File(this.PATH + this.files[20]));
      playerFourCharacter = ImageIO.read(new File(this.PATH + this.files[21]));
      
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
  
  public void writeToText() {
	  try {
		  HTMLBreak hb = new HTMLBreak(PATH, this);
			hb.createFiles();
			hb.writeToFiles();
	      PrintWriter writer = null;
	      sleeping = true;
	      System.out.println("Enacting Sleep: Image preservation for slower computers");
	      
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
	      if(!StreamUpdaterTab.getRestriction()) {
	    	  writer.println(commentators[0]);
		      writer.print(commentatorsInfo[0]);
	      } else {
	    	  writer.print(commentators[0]);
	      }
	      writer.close();
	      
	      writer = new PrintWriter(this.PATH + this.files[7]);
	      if(!StreamUpdaterTab.getRestriction()) {
	    	  writer.println(commentators[1]);
		      writer.print(commentatorsInfo[1]);
	      } else {
	    	  writer.print(commentators[1]);
	      }
	      writer.close();
	      
	      writer = new PrintWriter(this.PATH + this.files[14]);
	      writer.println(playerOneCharacterText);
	      writer.close();
	      
	      writer = new PrintWriter(this.PATH + this.files[15]);
	      writer.println(playerTwoCharacterText);
	      writer.close();
	      
	      writer = new PrintWriter(this.PATH + this.files[16]);
	      if(!StreamUpdaterTab.getRestriction()) {
		      writer.println(playerThree);
		      writer.print(playerThreeInfo);
	      } else 
	    	  writer.print(playerThree);
	      writer.close();
	      
	      writer = new PrintWriter(this.PATH + this.files[17]);
	      if(!StreamUpdaterTab.getRestriction()) {
	    	  writer.println(playerFour);
	    	  writer.print(playerFourInfo);
	      } else
	    	  writer.print(playerFour);
	      writer.close();
	      
	      writer = new PrintWriter(this.PATH + this.files[18]);
	      writer.print(teamOne);
	      writer.close();
	      
	      writer = new PrintWriter(this.PATH + this.files[19]);
	      writer.print(teamTwo);
	      writer.close();
	      
	      writer = new PrintWriter(this.PATH + this.files[20]);
	      writer.print(playerThreeCharacter);
	      writer.close();
	      
	      writer = new PrintWriter(this.PATH + this.files[21]);
	      writer.print(playerFourCharacter);
	      writer.close();
	      
	      hb = null;
	  } catch (Exception e) {}
  }
  
  public void writeToFiles()
  {
    try
    {
		HTMLBreak hb = new HTMLBreak(PATH, this);
		hb.createFiles();
		hb.writeToFiles();
      PrintWriter writer = null;
      sleeping = true;
      System.out.println("Enacting Sleep: Image preservation for slower computers");
      
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
      if(!StreamUpdaterTab.getRestriction()) {
    	  writer.println(commentators[0]);
	      writer.print(commentatorsInfo[0]);
      } else {
    	  writer.print(commentators[0]);
      }
      writer.close();
      
      writer = new PrintWriter(this.PATH + this.files[7]);
      if(!StreamUpdaterTab.getRestriction()) {
    	  writer.println(commentators[1]);
	      writer.print(commentatorsInfo[1]);
      } else {
    	  writer.print(commentators[1]);
      }
      writer.close();
      
      writer = new PrintWriter(this.PATH + this.files[14]);
      writer.println(playerOneCharacterText);
      writer.close();
      
      writer = new PrintWriter(this.PATH + this.files[15]);
      writer.println(playerTwoCharacterText);
      writer.close();
      
      writer = new PrintWriter(this.PATH + this.files[16]);
      if(!StreamUpdaterTab.getRestriction()) {
	      writer.println(playerThree);
	      writer.print(playerThreeInfo);
      } else 
    	  writer.print(playerThree);
      writer.close();
      
      writer = new PrintWriter(this.PATH + this.files[17]);
      if(!StreamUpdaterTab.getRestriction()) {
    	  writer.println(playerFour);
    	  writer.print(playerFourInfo);
      } else
    	  writer.print(playerFour);
      writer.close();
      
      writer = new PrintWriter(this.PATH + this.files[18]);
      writer.print(teamOne);
      writer.close();
      
      writer = new PrintWriter(this.PATH + this.files[19]);
      writer.print(teamTwo);
      writer.close();
      
      writer = new PrintWriter(this.PATH + this.files[20]);
      writer.print(playerThreeCharacter);
      writer.close();
      
      writer = new PrintWriter(this.PATH + this.files[21]);
      writer.print(playerFourCharacter);
      writer.close();
    	  
      Thread tmp = new Thread(new Runnable() {

		@Override
		public void run() {
			
			try {
				// first declare a file, this is to our output
			      File f;
			      
			      f = new File(PATH + files[8]);
			      if(f.exists() && playerOneCharacter != null)
			    	  ImageIO.write(playerOneCharacter, "png", f);
			      //Thread.sleep(250);
			      
			      f = new File(PATH + files[10]);
			      if(f.exists() && playerTwoCharacter != null)
			    	  ImageIO.write(playerTwoCharacter, "png", f);
			      //Thread.sleep(250);
			      
			      f = new File(PATH + files[9]);
			      if(f.exists() && playerOneSponsor != null)
			    	  ImageIO.write(playerOneSponsor, "png", f);
			      //Thread.sleep(250);
			      
			      f = new File(PATH + files[11]);
			      if(f.exists() && playerTwoSponsor != null)
			    	  ImageIO.write(playerTwoSponsor, "png", f);
			      //Thread.sleep(250);
			      
			      f = new File(PATH + files[12]);
			      if(f.exists() && commentatorOneSponsor != null)
			    	  ImageIO.write(commentatorOneSponsor, "png", f);
			      //Thread.sleep(250);
			      
			      f = new File(PATH + files[13]);
			      if(f.exists() && commentatorTwoSponsor != null)
			    	  ImageIO.write(commentatorTwoSponsor, "png", f);
			      //Thread.sleep(250);
			      
			      f = new File(PATH + files[20]);
			      if(f.exists() && playerThreeCharacter != null)
			    	  ImageIO.write(playerThreeCharacter, "png", f);
			      //Thread.sleep(250);
			      
			      f = new File(PATH + files[21]);
			      if(f.exists() && playerFourCharacter != null)
			    	  ImageIO.write(playerFourCharacter, "png", f);
			      //Thread.sleep(250);
			      Thread.sleep(1500);
			      sleeping = false;
			      
			} catch (Exception e) {
				
			}
		}
    	  
      });
      
      tmp.start();
      
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
  
  public static boolean isSleeping() {
	  return sleeping;
  }
  
}
