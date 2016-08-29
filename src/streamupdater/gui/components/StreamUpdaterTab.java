package streamupdater.gui.components;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import streamupdater.gui.main.GUI;
import streamupdater.util.SavingFileConfiguration;

@SuppressWarnings("serial")
public class StreamUpdaterTab extends JPanel {

	private static JTextField mainTitleText;
	private static JTextField currentRoundText;
	private static JTextField playerOneScoreText;
	private static JTextField playerTwoScoreText;
	
	private static JComboBox playerOneCombo;
	private static JComboBox playerTwoCombo;
	private static JComboBox commentatorOne;
	private static JComboBox commentatorTwo;
	private static JComboBox playerOneSponsor;
	private static JComboBox playerOneCharacter;
	private static JComboBox playerTwoSponsor;
	private static JComboBox playerTwoCharacter;
	private static JComboBox commentatorOneSponsor;
	private static JComboBox commentatorTwoSponsor;
	
	// auto switch
	private static JCheckBox playerBox;
	private static JSpinner playerSpin;
	
	// limit 1 line
	private static JCheckBox lineBox;

	// override to text field
	private static JButton playerOneOverride;
	private static JButton playerTwoOverride;
	private static JButton commentatorOneOverride;
	private static JButton commentatorTwoOverride;
	
	private static ArrayList<String> playerInfo;
	private static ArrayList<String> commentatorInfo;
	
	private static File[] sponsorsArray;
	private static File[] characterArray;
	
	private static SavingFileConfiguration sfc;
	
	private static Thread timerThread;
	private static boolean run = false;
	
	private static boolean pause = true;
	private static boolean swap = false;
	private static boolean oneLine = false;
	
	public StreamUpdaterTab() {
		
		sfc = new SavingFileConfiguration();
		
		playerInfo = new ArrayList<String>();
		commentatorInfo = new ArrayList<String>();
		
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 539, 690);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Main Title");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 11, 519, 14);
		panel.add(lblNewLabel);
		
		mainTitleText = new JTextField();
		mainTitleText.setHorizontalAlignment(SwingConstants.CENTER);
		mainTitleText.setFont(new Font("Dialog", Font.PLAIN, 12));
		mainTitleText.setBounds(89, 36, 361, 20);
		panel.add(mainTitleText);
		mainTitleText.setColumns(10);
		
		JLabel lblNewLabel_12 = new JLabel("Current Round");
		lblNewLabel_12.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_12.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_12.setBounds(10, 67, 519, 14);
		panel.add(lblNewLabel_12);
		
		currentRoundText = new JTextField();
		currentRoundText.setHorizontalAlignment(SwingConstants.CENTER);
		currentRoundText.setFont(new Font("Dialog", Font.PLAIN, 12));
		currentRoundText.setBounds(89, 92, 361, 20);
		panel.add(currentRoundText);
		currentRoundText.setColumns(10);
		
		JLabel lblNewLabel_13 = new JLabel("Player One");
		lblNewLabel_13.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_13.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_13.setBounds(10, 123, 519, 20);
		panel.add(lblNewLabel_13);
		
		playerOneCombo = new JComboBox();
		playerOneCombo.setBounds(239, 154, 211, 20);
		panel.add(playerOneCombo);
		
		JLabel lblNewLabel_14 = new JLabel("Player One Score");
		lblNewLabel_14.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_14.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_14.setBounds(10, 185, 519, 20);
		panel.add(lblNewLabel_14);
		
		playerOneScoreText = new JTextField();
		playerOneScoreText.setBounds(219, 216, 86, 20);
		panel.add(playerOneScoreText);
		playerOneScoreText.setColumns(10);
		
		JLabel playerOnePlus = new JLabel("");
		playerOnePlus.setBounds(89, 197, 70, 50);
		playerOnePlus.setIcon(new ImageIcon(getClass().getResource("/Plus.png")));
		panel.add(playerOnePlus);
		
		JLabel playerOneMinus = new JLabel("");
		playerOneMinus.setBounds(380, 197, 70, 50);
		playerOneMinus.setIcon(new ImageIcon(getClass().getResource("/Minus.png")));
		panel.add(playerOneMinus);
		
		JLabel lblNewLabel_15 = new JLabel("Player Two");
		lblNewLabel_15.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_15.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_15.setBounds(10, 278, 519, 20);
		panel.add(lblNewLabel_15);
		
		playerTwoCombo = new JComboBox();
		playerTwoCombo.setBounds(239, 309, 211, 20);
		panel.add(playerTwoCombo);
		
		JLabel lblNewLabel_16 = new JLabel("Player Two Score");
		lblNewLabel_16.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_16.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_16.setBounds(10, 340, 519, 20);
		panel.add(lblNewLabel_16);
		
		playerTwoScoreText = new JTextField();
		playerTwoScoreText.setBounds(219, 371, 86, 20);
		panel.add(playerTwoScoreText);
		playerTwoScoreText.setColumns(10);
		
		JLabel playerTwoPlus = new JLabel("");
		playerTwoPlus.setBounds(89, 360, 70, 50);
		playerTwoPlus.setIcon(new ImageIcon(getClass().getResource("/Plus.png")));
		panel.add(playerTwoPlus);
		
		JLabel playerTwoMinus = new JLabel("");
		playerTwoMinus.setBounds(380, 360, 70, 50);
		playerTwoMinus.setIcon(new ImageIcon(getClass().getResource("/Minus.png")));
		panel.add(playerTwoMinus);
		
		// auto switch text
		playerBox = new JCheckBox("");
		playerBox.setSelected(false);
		playerBox.setBounds(89, 440, 20, 20);
		panel.add(playerBox);
		
		JLabel playersSwitch = new JLabel("Switch Players Every ");
		playersSwitch.setToolTipText("This will switch the player names and information every set seconds.");
		playersSwitch.setBounds(125, 440, 210, 20);
		playersSwitch.setHorizontalAlignment(SwingConstants.LEFT);
		playersSwitch.setFont(new Font("Dialog", Font.BOLD, 16));
		panel.add(playersSwitch);
		
		// restrict to 1 line
		lineBox = new JCheckBox("");
		lineBox.setSelected(false);
		lineBox.setBounds(89, 480, 20, 20);
		panel.add(lineBox);
		
		JLabel lineRestrict = new JLabel("Restrict Output To One Line");
		lineRestrict.setToolTipText("Enable this feature to only show one line instead of two");
		lineRestrict.setBounds(125, 480, 250, 20);
		lineRestrict.setHorizontalAlignment(SwingConstants.LEFT);
		lineRestrict.setFont(new Font("Dialog", Font.BOLD, 16));
		panel.add(lineRestrict);
		
		playerSpin = new JSpinner();
		playerSpin.setValue(10);
		playerSpin.setBounds(350, 440, 80, 20);
		playerSpin.setFont(new Font("Dialog", Font.PLAIN, 16));
		panel.add(playerSpin);
		
		JLabel lblNewLabel_17 = new JLabel("Commentators");
		lblNewLabel_17.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_17.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_17.setBounds(10, 523, 519, 20);
		panel.add(lblNewLabel_17);
		
		commentatorOne = new JComboBox();
		commentatorOne.setBounds(239, 554, 211, 20);
		panel.add(commentatorOne);
		
		commentatorTwo = new JComboBox();
		commentatorTwo.setBounds(239, 585, 211, 20);
		panel.add(commentatorTwo);
		
		JButton updateButton = new JButton("Update!");
		updateButton.setFont(new Font("Dialog", Font.BOLD, 16));
		updateButton.setBounds(89, 629, 361, 50);
		panel.add(updateButton);
		
		playerOneSponsor = new JComboBox();
		playerOneSponsor.setBounds(89, 154, 140, 20);
		panel.add(playerOneSponsor);
		
		playerOneCharacter = new JComboBox();
		playerOneCharacter.setBounds(200, 247, 134, 20);
		panel.add(playerOneCharacter);
		
		playerTwoSponsor = new JComboBox();
		playerTwoSponsor.setBounds(89, 309, 140, 20);
		panel.add(playerTwoSponsor);
		
		playerTwoCharacter = new JComboBox();
		playerTwoCharacter.setBounds(200, 402, 134, 20);
		panel.add(playerTwoCharacter);
		
		commentatorOneSponsor = new JComboBox();
		commentatorOneSponsor.setBounds(89, 554, 140, 20);
		panel.add(commentatorOneSponsor);
		
		commentatorTwoSponsor = new JComboBox();
		commentatorTwoSponsor.setBounds(89, 585, 140, 20);
		panel.add(commentatorTwoSponsor);

		playersSwitch.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
				if(playerBox.isSelected()) {
					playerBox.setSelected(false);
				} else
					playerBox.setSelected(true);
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
			}
		});
		
		lineRestrict.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
				if(lineBox.isSelected()) {
					lineBox.setSelected(false);
				} else
					lineBox.setSelected(true);
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
			}
			
		});
		
		lineBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(lineBox.isSelected()) {
					oneLine = true;
				} else {
					oneLine = false;
				}
			}
			
		});
		
		playerBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(playerBox.isSelected()) {
					pause = false;
					if(!run) {
						run = true;
						timerThread = new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									while(true) {
										if(!pause) {
											if(!swap) {
												if(playerOneCombo.getItemCount() > 0)
													setPlayerOneName(swap);
												if(playerTwoCombo.getItemCount() > 0)
													setPlayerTwoName(swap);
												swap = true;
												
											} else {
												if(playerOneCombo.getItemCount() > 0)
													setPlayerOneName(swap);
												if(playerTwoCombo.getItemCount() > 0)
													setPlayerTwoName(swap);
												swap = false;
											}
											sfc.writeToFiles();
											Thread.sleep((int)playerSpin.getValue() * 1000);
										}
										// feel free to add anything here, basically this is an active thread once switch is on
										Thread.sleep(500);
									}
								} catch (Exception e) {}
								
							}
						});
						timerThread.start();
					}
					
				} else {
					swap = false;
					pause = true;
				}
				
			}
			
		});
		
		playerOnePlus.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(FilesTab.getImageFolder1() == null || FilesTab.getImageFolder2() == null 
						|| FilesTab.getPlayerFolder() == null || FilesTab.getCommentatorFolder() == null 
						|| FilesTab.getTextFolder() == null) {
					JOptionPane.showMessageDialog(null, "Please Declare All The Paths In The Files Tab!"); GUI.switchTo(3);
				} else {
					sfc.increasePlayerOneScore();
					playerOneScoreText.setText("" + sfc.getPlayerOneScore());
					setPlayerOneScore();
					setPlayerTwoScore();
					sfc.writeToScores();
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {	}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
		});
		
		playerOneMinus.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(FilesTab.getImageFolder1() == null || FilesTab.getImageFolder2() == null 
						|| FilesTab.getPlayerFolder() == null || FilesTab.getCommentatorFolder() == null 
						|| FilesTab.getTextFolder() == null) {
					JOptionPane.showMessageDialog(null, "Please Declare All The Paths In The Files Tab!"); GUI.switchTo(3);
				} else {
					sfc.decreasePlayerOneScore();
					playerOneScoreText.setText("" + sfc.getPlayerOneScore());
					setPlayerOneScore();
					setPlayerTwoScore();
					sfc.writeToScores();
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {	}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
		});
		
		playerTwoPlus.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(FilesTab.getImageFolder1() == null || FilesTab.getImageFolder2() == null 
						|| FilesTab.getPlayerFolder() == null || FilesTab.getCommentatorFolder() == null 
						|| FilesTab.getTextFolder() == null) {
					JOptionPane.showMessageDialog(null, "Please Declare All The Paths In The Files Tab!"); GUI.switchTo(3);
				} else {
					sfc.increasePlayerTwoScore();
					playerTwoScoreText.setText("" + sfc.getPlayerTwoScore());
					setPlayerOneScore();
					setPlayerTwoScore();
					sfc.writeToScores();
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {	}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
		});
		
		playerTwoMinus.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(FilesTab.getImageFolder1() == null || FilesTab.getImageFolder2() == null 
						|| FilesTab.getPlayerFolder() == null || FilesTab.getCommentatorFolder() == null 
						|| FilesTab.getTextFolder() == null) {
					JOptionPane.showMessageDialog(null, "Please Declare All The Paths In The Files Tab!"); GUI.switchTo(3);
				} else {
					sfc.decreasePlayerTwoScore();
					playerTwoScoreText.setText("" + sfc.getPlayerTwoScore());
					setPlayerOneScore();
					setPlayerTwoScore();
					sfc.writeToScores();
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {	}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
		});
		
		updateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(FilesTab.getImageFolder1() == null || FilesTab.getImageFolder2() == null 
						|| FilesTab.getPlayerFolder() == null || FilesTab.getCommentatorFolder() == null 
						|| FilesTab.getTextFolder() == null) {
					JOptionPane.showMessageDialog(null, "Please Declare All The Paths In The Files Tab!"); GUI.switchTo(3);
				} else {
					try {
						if(FileRenamerTab.isRunningMonitor()) {
							FileRenamerTab.appendCharacterSystem();
						}
						setMainTitle();
						setCurrentRound();
						if(playerOneCombo.getItemCount() > 0)
							setPlayerOneName(swap);
						setPlayerOneScore();
						if(playerTwoCombo.getItemCount() > 0)
							setPlayerTwoName(swap);
						setPlayerTwoScore();
						setCommentators();
						setPlayerOneCharacterText(removeColors((String)playerOneCharacter.getSelectedItem()));
						setPlayerTwoCharacterText(removeColors((String)playerTwoCharacter.getSelectedItem()));
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Error! File Not Found!"); 
						e.printStackTrace();
					}
					if(FilesTab.getImageFolder1().listFiles().length > 0) {
						setPlayerOneCharacter();
					}
					if(FilesTab.getImageFolder2().listFiles().length > 0)
						setPlayerOneSponsor();
					if(FilesTab.getPlayerFolder().listFiles().length > 0) {
						setPlayerTwoCharacter();
					}
					if(FilesTab.getImageFolder2().listFiles().length > 0)
						setPlayerTwoSponsor();
					if(FilesTab.getImageFolder2().listFiles().length > 0) {
						setCommentatorOneSponsor();
						setCommentatorTwoSponsor();
					}
					// init thread so everything is set
					sfc.writeToFiles();
				}
			}
			
		});
		
	}
	
	private String removeColors(String n) {
		String[] colors = { "Blue", "Black", "Red", "White", "Pink", "Green", "Orange", "Purple", "Brown" }; 
		for(int i = 0; i < colors.length; i++) {
			n = n.replace(colors[i], "");
		}
		n = n.replace(".png", "");
		return n;
		
	}
	
	public static void updateList(String name, String info, boolean player) {
		
		if(player) {
			playerOneCombo.addItem(name);
			playerTwoCombo.addItem(name);
			playerInfo.add(info);
		} else {
			commentatorOne.addItem(name);
			commentatorTwo.addItem(name);
			commentatorInfo.add(info);
		}
		
	}
	
	public static void loadEverything(File textFolder, File[] players, File[] commentators, File[] characters, File[] sponsors) {
		
		BufferedReader reader;
		String line = null;
		
		characterArray = characters;
		sponsorsArray = sponsors;
		
		// set and create files
		sfc.setPath(textFolder.getAbsolutePath() + "/");
		sfc.createFiles();
		sfc.readFiles();
		
		// set scores
		playerOneScoreText.setText(""+sfc.getPlayerOneScore());
		playerTwoScoreText.setText(""+sfc.getPlayerTwoScore());
		
		// set titles
		mainTitleText.setText(sfc.getMainTitle());
		currentRoundText.setText(sfc.getCurrentRound());
		
		playerOneCombo.removeAllItems();
		playerTwoCombo.removeAllItems();
		commentatorOne.removeAllItems();
		commentatorTwo.removeAllItems();
		playerOneCharacter.removeAllItems();
		playerOneSponsor.removeAllItems();
		playerTwoCharacter.removeAllItems();
		playerTwoSponsor.removeAllItems();
		commentatorOneSponsor.removeAllItems();
		commentatorTwoSponsor.removeAllItems();
		playerInfo.clear();
		commentatorInfo.clear();
		
		// set player names
		if(players != null) {
			for(int i = 0; i < players.length; i++) {
				try {
					
					reader = new BufferedReader(new FileReader(new File(players[i].getAbsolutePath())));
					line = reader.readLine();
					if(line != null) { 
						playerOneCombo.addItem(line);
						playerTwoCombo.addItem(line);
						String tmp = line;
						if((line = reader.readLine()) != null) playerInfo.add(line);
						else
							playerInfo.add(tmp); 		// set playerInfo to default
					}
					reader.close();
					
				} catch (Exception e) { JOptionPane.showMessageDialog(null, "Folder Not Found!"); }
			}
		}
		
		line = null;
		
		// set commentators
		if(commentators != null) {
			for(int i = 0; i < commentators.length; i++) {
				try {
					
					reader = new BufferedReader(new FileReader(new File(commentators[i].getAbsolutePath())));
					line = reader.readLine();
					if(line != null) {
						commentatorOne.addItem(line);
						commentatorTwo.addItem(line);
						String tmp = line;
						if((line = reader.readLine()) != null) commentatorInfo.add(line);
						else
							commentatorInfo.add(tmp);
					}
					reader.close();
					
				} catch (Exception e) { JOptionPane.showMessageDialog(null, "Folder Not Found!"); }
			}
		}
		
		if(sponsorsArray != null) {
			for(int i = 0; i < sponsorsArray.length; i++) {
				try {
					
					playerOneSponsor.addItem(sponsorsArray[i].getName());
					playerTwoSponsor.addItem(sponsorsArray[i].getName());
					commentatorOneSponsor.addItem(sponsorsArray[i].getName());
					commentatorTwoSponsor.addItem(sponsorsArray[i].getName());
					
				} catch (Exception e) { JOptionPane.showMessageDialog(null, "Folder Not Found!"); }
			}
		}
		
		if(characterArray != null) {
			for(int i = 0; i < characterArray.length; i++) {
				try {
					
					playerOneCharacter.addItem(characterArray[i].getName());
					playerTwoCharacter.addItem(characterArray[i].getName());
					
				} catch (Exception e) { JOptionPane.showMessageDialog(null, "Folder Not Found!"); }
			}
		}
		
	}
	
	private void setMainTitle()
	{
	  StreamUpdaterTab.sfc.setMainTitle(StreamUpdaterTab.mainTitleText.getText());
	}

	private void setCurrentRound()
	{
	  StreamUpdaterTab.sfc.setCurrentRound(StreamUpdaterTab.currentRoundText.getText());
	}

	private void setPlayerOneScore()
	{
	  StreamUpdaterTab.sfc.setPlayerOneScore(Integer.parseInt(StreamUpdaterTab.playerOneScoreText.getText()));
	}

	private void setPlayerTwoScore()
	{
	  StreamUpdaterTab.sfc.setPlayerTwoScore(Integer.parseInt(StreamUpdaterTab.playerTwoScoreText.getText()));
	}
	
	private void setPlayerOneName(boolean swap) {
		if(!swap) {
			sfc.setPlayerOne((String) playerOneCombo.getSelectedItem());
			sfc.setPlayerOneInfo(playerInfo.get(playerOneCombo.getSelectedIndex()));
		} else {
			sfc.setPlayerOne(playerInfo.get(playerOneCombo.getSelectedIndex()));
			sfc.setPlayerOneInfo((String) playerOneCombo.getSelectedItem());
		}
	}
	
	private void setPlayerTwoName(boolean swap) {
		if(!swap) {
			sfc.setPlayerTwo((String) playerTwoCombo.getSelectedItem());
			sfc.setPlayerTwoInfo(playerInfo.get(playerTwoCombo.getSelectedIndex()));
		} else {
			sfc.setPlayerTwo(playerInfo.get(playerTwoCombo.getSelectedIndex()));
			sfc.setPlayerTwoInfo((String) playerTwoCombo.getSelectedItem());
		}
	}
	
	private void setCommentators() {
		sfc.setCommentators((String)commentatorOne.getSelectedItem(), (String)commentatorTwo.getSelectedItem());
		sfc.setCommentatorsInfo(commentatorInfo.get(commentatorOne.getSelectedIndex()), commentatorInfo.get(commentatorTwo.getSelectedIndex()));
	}
	
	private void setPlayerOneCharacter() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(characterArray[playerOneCharacter.getSelectedIndex()].getAbsolutePath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sfc.setPlayerOneCharacter(image);
	}
	
	private void setPlayerTwoCharacter() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(characterArray[playerTwoCharacter.getSelectedIndex()].getAbsolutePath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sfc.setPlayerTwoCharacter(image);
	}
	
	private void setPlayerOneSponsor() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(sponsorsArray[playerOneSponsor.getSelectedIndex()].getAbsolutePath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sfc.setPlayerOneSponsor(image);
	}
	
	private void setPlayerTwoSponsor() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(sponsorsArray[playerTwoSponsor.getSelectedIndex()].getAbsolutePath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sfc.setPlayerTwoSponsor(image);
	}
	
	private void setCommentatorOneSponsor() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(sponsorsArray[commentatorOneSponsor.getSelectedIndex()].getAbsolutePath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sfc.setCommentatorOneSponsor(image);
	}
	
	private void setCommentatorTwoSponsor() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(sponsorsArray[commentatorTwoSponsor.getSelectedIndex()].getAbsolutePath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sfc.setCommentatorTwoSponsor(image);
	}
	
	private void setPlayerOneCharacterText(String n) {
		sfc.setPlayerOneCharacterText(n);
	}
	
	private void setPlayerTwoCharacterText(String n) {
		sfc.setPlayerTwoCharacterText(n);
	}
	
	public static ArrayList<String> getPlayers() {
		ArrayList<String> playerOne = new ArrayList<String>();
		ComboBoxModel model = playerOneCombo.getModel();
		for(int i = 0; i < playerOneCombo.getItemCount(); i++) {
			playerOne.add((String) model.getElementAt(i));
		}
		return playerOne;
	}
	
	public static ArrayList<String> getPlayerInfo() {
		return playerInfo;
	}
	
	public static ArrayList<String> getCommentators() {
		ArrayList<String> commentator = new ArrayList<String>();
		ComboBoxModel model = commentatorOne.getModel();
		for(int i = 0; i < commentatorOne.getItemCount(); i++) {
			commentator.add((String) model.getElementAt(i));
		}
		return commentator;
	}
	
	public static ArrayList<String> getCommentatorInfo() {
		return commentatorInfo;
	}
	
	public static boolean getRestriction() {
		return lineBox.isSelected();
	}
	
}