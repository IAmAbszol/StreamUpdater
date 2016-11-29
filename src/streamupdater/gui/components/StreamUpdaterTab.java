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
import streamupdater.util.AdjustRounds;
import streamupdater.util.SavingFileConfiguration;

@SuppressWarnings("serial")
public class StreamUpdaterTab extends JPanel {

	public static String displayPools = "Pools %ad";
	public static String displayBracket = "Bracket %round";
	
	private static String[] modes = { "Singles", "Doubles" };
	private static JComboBox type = new JComboBox(modes);
	
	private static JTextField mainTitleText;
	private static JTextField currentRoundText;
	private static JTextField playerOneScoreText;
	private static JTextField playerTwoScoreText;
	
	private static JComboBox playerOneCombo;
	private static JComboBox playerTwoCombo;
	private static JComboBox playerThreeCombo;
	private static JComboBox playerFourCombo;
	private static JComboBox commentatorOne;
	private static JComboBox commentatorTwo;
	private static JComboBox playerOneSponsor;
	private static JComboBox playerOneCharacter;
	private static JComboBox playerTwoSponsor;
	private static JComboBox playerTwoCharacter;
	private static JComboBox commentatorOneSponsor;
	private static JComboBox commentatorTwoSponsor;
	private static JComboBox playerThreeCharacter;
	private static JComboBox playerFourCharacter;
	
	private static JButton overrideP1;
	private static JButton overrideP2;
	private static JButton overrideP3;
	private static JButton overrideP4;
	private static JButton overrideC1;
	private static JButton overrideC2;
	private static JButton overrideRound;
	private static JTextField overP1;
	private static JTextField overP2;
	private static JTextField overP3;
	private static JTextField overP4;
	private static JTextField overCom1;
	private static JTextField overCom2;
	private static JComboBox overrideR;
	private static boolean overridePlayerOne = false;
	private static boolean overridePlayerTwo = false;
	private static boolean overridePlayerThree = false;
	private static boolean overridePlayerFour = false;
	private static boolean overrideCom = false;
	private static boolean overrideRounds = true;
	
	// auto switch
	private static JCheckBox playerBox;
	private static JSpinner playerSpin;
	
	// limit 1 line
	private static JCheckBox lineBox;

	// override to text field
	private static JButton playerOneOverride;
	private static JButton playerTwoOverride;
	private static JButton playerThreeOverride;
	private static JButton playerFourOverride;
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
	
	private static JPanel panel;
	private static JTextField connector;
	private static AdjustRounds roundControl;
	
	public StreamUpdaterTab() {
		
		sfc = new SavingFileConfiguration();
		
		playerInfo = new ArrayList<String>();
		commentatorInfo = new ArrayList<String>();
		
		setLayout(null);
		panel = new JPanel();
		
		buildSingles();
		
	}
	
	private void buildSingles() {
		
		overridePlayerOne = false;
		overridePlayerTwo = false;
		overridePlayerThree = false;
		overridePlayerFour = false;
		
		panel.removeAll();
		panel.setBounds(10, 11, 539, 690);
		add(panel);
		panel.setLayout(null);
		
		connector = new JTextField("&");
		type.setBounds(0, 11, 75, 20);
		panel.add(type);
		
		JLabel lblNewLabel = new JLabel("Main Title");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 11, 519, 14);
		panel.add(lblNewLabel);
		
		JLabel gear = new JLabel("");
		gear.setBounds(89, 92, 20, 20);
		try {
			gear.setIcon(new ImageIcon(ImageIO.read(AboutMeTab.class.getResource("/Images/test.png"))));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		panel.add(gear);
		
		overrideRound = new JButton("*");
		overrideRound.setBounds(430, 92, 20, 20);
		panel.add(overrideRound);
		
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
		
		overrideR = new JComboBox();
		overrideR.setBounds(114, 92, 311, 20);
		panel.add(overrideR);
		
		for(int i = 0; i < GUI.rounds.size(); i++) {
			overrideR.addItem(GUI.rounds.get(i));
		}
		
		currentRoundText = new JTextField();
		currentRoundText.setHorizontalAlignment(SwingConstants.CENTER);
		currentRoundText.setFont(new Font("Dialog", Font.PLAIN, 12));
		currentRoundText.setBounds(114, 92, 311, 20);
		currentRoundText.setColumns(10);
		
		JLabel lblNewLabel_13 = new JLabel("Player One");
		lblNewLabel_13.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_13.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_13.setBounds(10, 123, 519, 20);
		panel.add(lblNewLabel_13);
		
		overrideP1 = new JButton("*");
		overrideP1.setBounds(430, 154, 20, 20);
		panel.add(overrideP1);
		
		overrideP2 = new JButton("*");
		overrideP2.setBounds(430, 309, 20, 20);
		panel.add(overrideP2);
		
		overrideP3 = new JButton("*");
		overrideP4 = new JButton("*");
		
		playerOneCombo = new JComboBox();
		playerOneCombo.setBounds(239, 154, 186, 20);
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

		playerThreeCombo = new JComboBox();
		playerFourCombo = new JComboBox();
		
		playerTwoCombo = new JComboBox();
		playerTwoCombo.setBounds(239, 309, 186, 20);
		panel.add(playerTwoCombo);
		
		overP1 = new JTextField("");
		overP1.setBounds(239, 154, 186, 20);
		
		overP2 = new JTextField("");
		overP2.setBounds(239, 309, 186, 20);
		
		overP3 = new JTextField("");
		overP4 = new JTextField("");
		
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
		
		// swap players names, scores, sponsors, etc
		JLabel swapLabel = new JLabel();
		swapLabel.setToolTipText("Swap Player One Objects With Player Two Objects");
		swapLabel.setBounds(240, 432, 40, 40);
		try {
			BufferedImage image = ImageIO.read(StreamUpdaterTab.class.getResource("/Images/swap.png"));
			swapLabel.setIcon(new ImageIcon(image));
		} catch (Exception e) {}
		panel.add(swapLabel);
		
		// auto switch text
		playerBox = new JCheckBox("");
		playerBox.setSelected(false);
		playerBox.setBounds(89, 475, 20, 20);
		panel.add(playerBox);
		
		JLabel playersSwitch = new JLabel("Switch Players Every ");
		playersSwitch.setToolTipText("This will switch the player names and information every set seconds.");
		playersSwitch.setBounds(125, 475, 210, 20);
		playersSwitch.setHorizontalAlignment(SwingConstants.LEFT);
		playersSwitch.setFont(new Font("Dialog", Font.BOLD, 16));
		panel.add(playersSwitch);
		
		// restrict to 1 line
		lineBox = new JCheckBox("");
		lineBox.setSelected(true);
		lineBox.setBounds(89, 510, 20, 20);
		panel.add(lineBox);
		
		JLabel lineRestrict = new JLabel("Restrict Output To One Line");
		lineRestrict.setToolTipText("Enable this feature to only show one line instead of two");
		lineRestrict.setBounds(125, 510, 250, 20);
		lineRestrict.setHorizontalAlignment(SwingConstants.LEFT);
		lineRestrict.setFont(new Font("Dialog", Font.BOLD, 16));
		panel.add(lineRestrict);
		
		playerSpin = new JSpinner();
		playerSpin.setValue(10);
		playerSpin.setBounds(350, 475, 80, 20);
		playerSpin.setFont(new Font("Dialog", Font.PLAIN, 16));
		panel.add(playerSpin);
		
		JLabel lblNewLabel_17 = new JLabel("Commentators");
		lblNewLabel_17.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_17.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_17.setBounds(10, 545, 519, 20);
		panel.add(lblNewLabel_17);
		
		overCom1 = new JTextField("");
		overCom1.setBounds(239, 575, 186, 20);
		
		overCom2 = new JTextField("");
		overCom2.setBounds(239, 605, 186, 20);
		
		overrideC1  = new JButton("*");
		overrideC1.setBounds(430, 575, 20, 20);
		panel.add(overrideC1);
		
		overrideC2 = new JButton("*");
		overrideC2.setBounds(430, 605, 20, 20);
		panel.add(overrideC2);
		
		commentatorOne = new JComboBox();
		commentatorOne.setBounds(239, 575, 186, 20);
		panel.add(commentatorOne);
		
		commentatorTwo = new JComboBox();
		commentatorTwo.setBounds(239, 605, 186, 20);
		panel.add(commentatorTwo);
		
		JButton updateButton = new JButton("Update!");
		updateButton.setFont(new Font("Dialog", Font.BOLD, 16));
		updateButton.setBounds(89, 640, 361, 50);
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
		
		playerThreeCharacter = new JComboBox();
		playerFourCharacter = new JComboBox();
		
		commentatorOneSponsor = new JComboBox();
		commentatorOneSponsor.setBounds(89, 575, 140, 20);
		panel.add(commentatorOneSponsor);
		
		commentatorTwoSponsor = new JComboBox();
		commentatorTwoSponsor.setBounds(89, 605, 140, 20);
		panel.add(commentatorTwoSponsor);
		
		this.loadEverything(FilesTab.getTextFolder(), FilesTab.getPlayerFiles(), FilesTab.getCommentatorFiles(), FilesTab.getImage1Files(), FilesTab.getImage2Files());
		repaint();

		type.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if((boolean)type.getSelectedItem().equals("Singles"))
					buildSingles();
				else
					buildDoubles();
			}
			
		});
		
		swapLabel.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
				if(FilesTab.getImageFolder1() == null || FilesTab.getImageFolder2() == null 
						|| FilesTab.getPlayerFolder() == null || FilesTab.getCommentatorFolder() == null 
						|| FilesTab.getTextFolder() == null) {
					JOptionPane.showMessageDialog(null, "Please Declare All The Paths In The Files Tab!"); GUI.switchTo(3);
				} else {
					if((boolean)type.getSelectedItem().equals("Singles")) {
						int player1Pos = playerOneCombo.getSelectedIndex();
						int player2Pos = playerTwoCombo.getSelectedIndex();
						int player1Sponsor = playerOneSponsor.getSelectedIndex();
						int player2Sponsor = playerTwoSponsor.getSelectedIndex();
						int player1Char = playerOneCharacter.getSelectedIndex();
						int player2Char = playerTwoCharacter.getSelectedIndex();
						int player1Score = Integer.parseInt(playerOneScoreText.getText());
						int player2Score = Integer.parseInt(playerTwoScoreText.getText());
						String playerOneOver = overP1.getText();
						String playerTwoOver = overP2.getText();
						boolean op1 = overridePlayerOne;
						boolean op2 = overridePlayerTwo;
						//set score
						playerOneScoreText.setText(""+player2Score);
						playerTwoScoreText.setText(""+player1Score);
						//set chars
						playerOneCharacter.setSelectedIndex(player2Char);
						playerTwoCharacter.setSelectedIndex(player1Char);
						//set sponsor
						playerOneSponsor.setSelectedIndex(player2Sponsor);
						playerTwoSponsor.setSelectedIndex(player1Sponsor);
						
						// player one & two have a text field
						if(overridePlayerOne && overridePlayerTwo) {
							overP2.setText(playerOneOver);
							overP1.setText(playerTwoOver);
						} else 
						// player one is text but player two is combo
						if(overridePlayerOne && !overridePlayerTwo) {
							// swap the booleans
							overridePlayerOne = op2;
							overridePlayerTwo = op1;
							// enable p1 combo but enable p2 text
							panel.remove(overP1);
							panel.add(playerOneCombo);
							panel.remove(playerTwoCombo);
							panel.add(overP2);
							repaint();
							// set stuff
							overP2.setText(playerOneOver);
							playerOneCombo.setSelectedIndex(player2Pos);
							
						} else
						// player one is combo but player two is text
						if(!overridePlayerOne && overridePlayerTwo) {
							// swap the booleans
							overridePlayerOne = op2;
							overridePlayerTwo = op1;
							// enable p1 combo but enable p2 text
							panel.remove(playerOneCombo);
							panel.add(overP1);
							panel.remove(overP2);
							panel.add(playerTwoCombo);
							repaint();
							// set stuff
							overP1.setText(playerTwoOver);
							playerTwoCombo.setSelectedIndex(player1Pos);
						} else
						// both are combos
						if(!overridePlayerOne && !overridePlayerTwo) {
							playerTwoCombo.setSelectedIndex(player1Pos);
							playerOneCombo.setSelectedIndex(player2Pos);
						}
						updateButton.doClick();
					} else {
						int player1Pos = playerOneCombo.getSelectedIndex();
						int player2Pos = playerTwoCombo.getSelectedIndex();
						int player3Pos = playerThreeCombo.getSelectedIndex();
						int player4Pos = playerFourCombo.getSelectedIndex();
						int player1Char = playerOneCharacter.getSelectedIndex();
						int player2Char = playerTwoCharacter.getSelectedIndex();
						int player3Char = playerThreeCharacter.getSelectedIndex();
						int player4Char = playerFourCharacter.getSelectedIndex();
						int player1Score = Integer.parseInt(playerOneScoreText.getText());
						int player2Score = Integer.parseInt(playerTwoScoreText.getText());
						String playerOneOver = overP1.getText();
						String playerTwoOver = overP2.getText();
						String playerThreeOver = overP3.getText();
						String playerFourOver = overP4.getText();
						boolean op1 = overridePlayerOne;
						boolean op2 = overridePlayerTwo;
						boolean op3 = overridePlayerThree;
						boolean op4 = overridePlayerFour;
						//set score
						playerOneScoreText.setText(""+player2Score);
						playerTwoScoreText.setText(""+player1Score);
						//set chars
						playerOneCharacter.setSelectedIndex(player3Char);
						playerTwoCharacter.setSelectedIndex(player4Char);
						playerThreeCharacter.setSelectedIndex(player1Char);
						playerFourCharacter.setSelectedIndex(player2Char);
						
						// have to do this twice, first is player 1 vs player 3 swap
						// player one & two have a text field
						if(overridePlayerOne && overridePlayerThree) {
							overP3.setText(playerOneOver);
							overP1.setText(playerTwoOver);
						} else 
						// player one is text but player two is combo
						if(overridePlayerOne && !overridePlayerThree) {
							// swap the booleans
							overridePlayerOne = op3;
							overridePlayerThree = op1;
							// enable p1 combo but enable p2 text
							panel.remove(overP1);
							panel.add(playerOneCombo);
							panel.remove(playerThreeCombo);
							panel.add(overP3);
							repaint();
							// set stuff
							overP3.setText(playerOneOver);
							playerOneCombo.setSelectedIndex(player3Pos);
							
						} else
						// player one is combo but player two is text
						if(!overridePlayerOne && overridePlayerThree) {
							// swap the booleans
							overridePlayerOne = op3;
							overridePlayerThree = op1;
							// enable p1 combo but enable p2 text
							panel.remove(playerOneCombo);
							panel.add(overP1);
							panel.remove(overP3);
							panel.add(playerThreeCombo);
							repaint();
							// set stuff
							overP1.setText(playerThreeOver);
							playerThreeCombo.setSelectedIndex(player1Pos);
						} else
						// both are combos
						if(!overridePlayerOne && !overridePlayerThree) {
							playerThreeCombo.setSelectedIndex(player1Pos);
							playerOneCombo.setSelectedIndex(player3Pos);
						}
						/*
						 * player 2 vs player 4
						 */
						if(overridePlayerTwo && overridePlayerFour) {
							overP4.setText(playerTwoOver);
							overP2.setText(playerFourOver);
						} else 
						// player one is text but player two is combo
						if(overridePlayerTwo && !overridePlayerFour) {
							// swap the booleans
							overridePlayerTwo = op4;
							overridePlayerFour = op2;
							// enable p1 combo but enable p2 text
							panel.remove(overP2);
							panel.add(playerTwoCombo);
							panel.remove(playerFourCombo);
							panel.add(overP4);
							repaint();
							// set stuff
							overP4.setText(playerTwoOver);
							playerTwoCombo.setSelectedIndex(player4Pos);
							
						} else
						// player one is combo but player two is text
						if(!overridePlayerTwo && overridePlayerFour) {
							// swap the booleans
							overridePlayerTwo = op4;
							overridePlayerFour = op2;
							// enable p1 combo but enable p2 text
							panel.remove(playerTwoCombo);
							panel.add(overP2);
							panel.remove(overP4);
							panel.add(playerFourCombo);
							repaint();
							// set stuff
							overP2.setText(playerFourOver);
							playerFourCombo.setSelectedIndex(player2Pos);
						} else
						// both are combos
						if(!overridePlayerTwo && !overridePlayerFour) {
							playerFourCombo.setSelectedIndex(player2Pos);
							playerTwoCombo.setSelectedIndex(player4Pos);
						}
						updateButton.doClick();
					}
				}
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
		
		gear.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
				// only event I care about
				if(roundControl == null)
					roundControl = new AdjustRounds();
				else
					roundControl.unhide();
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
			
		});
		
		overrideRound.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(overrideRounds) {
					overrideRounds = false;
					panel.remove(overrideR);
					panel.add(currentRoundText);
					repaint();
				} else {
					overrideRounds = true;
					panel.remove(currentRoundText);
					panel.add(overrideR);
					repaint();
				}
			}
			
		});
		
		overrideP1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(overridePlayerOne) {
					overridePlayerOne = false;
					panel.remove(overP1);
					panel.add(playerOneCombo);
					repaint();
				} else {
					overridePlayerOne = true;
					panel.remove(playerOneCombo);
					panel.add(overP1);
					repaint();
				}
			}
			
		});
		
		overrideP2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(overridePlayerTwo) {
					overridePlayerTwo = false;
					panel.remove(overP2);
					panel.add(playerTwoCombo);
					repaint();
				} else {
					overridePlayerTwo = true;
					panel.remove(playerTwoCombo);
					panel.add(overP2);
					repaint();
				}
			}
			
		});
		
		overrideP3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(overridePlayerThree) {
					overridePlayerThree = false;
					panel.remove(overP3);
					panel.add(playerThreeCombo);
					repaint();
				} else {
					overridePlayerThree = true;
					panel.remove(playerThreeCombo);
					panel.add(overP3);
					repaint();
				}
			}
			
		});
		
		overrideP4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(overridePlayerFour) {
					overridePlayerFour = false;
					panel.remove(overP4);
					panel.add(playerFourCombo);
					repaint();
				} else {
					overridePlayerFour = true;
					panel.remove(playerFourCombo);
					panel.add(overP4);
					repaint();
				}
			}
			
		});
		
		overrideC1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(overrideCom) {
					overrideCom = false;
					panel.remove(overCom2);
					panel.add(commentatorTwo);
					panel.remove(overCom1);
					panel.add(commentatorOne);
					repaint();
				} else {
					overrideCom = true;
					panel.remove(commentatorTwo);
					panel.add(overCom2);
					panel.remove(commentatorOne);
					panel.add(overCom1);
					repaint();
				}
			}
		});
		
		overrideC2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(overrideCom) {
					overrideCom = false;
					panel.remove(overCom2);
					panel.add(commentatorTwo);
					panel.remove(overCom1);
					panel.add(commentatorOne);
					repaint();
				} else {
					overrideCom = true;
					panel.remove(commentatorTwo);
					panel.add(overCom2);
					panel.remove(commentatorOne);
					panel.add(overCom1);
					repaint();
				}				
			}
			
		});
		
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
												if(playerOneCombo.getItemCount() > 0 || !overP1.getText().equals(""))
													setPlayerOneName(swap);
												if(playerTwoCombo.getItemCount() > 0 || !overP2.getText().equals(""))
													setPlayerTwoName(swap);
												if(playerThreeCombo.getItemCount() > 0 || !overP3.getText().equals(""))
													setPlayerThreeName(swap);
												if(playerFourCombo.getItemCount() > 0 || !overP4.getText().equals(""))
													setPlayerFourName(swap);
												swap = true;
												
											} else {
												if(playerOneCombo.getItemCount() > 0 || !overP1.getText().equals(""))
													setPlayerOneName(swap);
												if(playerTwoCombo.getItemCount() > 0 || !overP2.getText().equals(""))
													setPlayerTwoName(swap);
												if(playerThreeCombo.getItemCount() > 0 || !overP3.getText().equals(""))
													setPlayerThreeName(swap);
												if(playerFourCombo.getItemCount() > 0 || !overP4.getText().equals(""))
													setPlayerFourName(swap);
												swap = false;
											}
											sfc.writeToText();
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
						if(RenderTab.isStreaming()) {
							RenderTab.appendCharacterSystem();
						}
						setMainTitle();
						setCurrentRound();
						if(playerOneCombo.getItemCount() > 0 || !overP1.getText().equals(""))
							setPlayerOneName(swap);
						setPlayerOneScore();
						if(playerTwoCombo.getItemCount() > 0 || !overP2.getText().equals(""))
							setPlayerTwoName(swap);
						if(playerThreeCombo.getItemCount() > 0 || !overP3.getText().equals(""))
							setPlayerThreeName(swap);
						if(playerFourCombo.getItemCount() > 0 || !overP4.getText().equals(""))
							setPlayerFourName(swap);
						setTeamOne();
						setTeamTwo();
						setPlayerTwoScore();
						if((commentatorOne.getItemCount() > 0 && commentatorTwo.getItemCount() > 0) || (!overCom1.getText().equals("") && !overCom2.getText().equals("")))
							setCommentators();
						setPlayerOneCharacterText(removeColors((String)playerOneCharacter.getSelectedItem()));
						setPlayerTwoCharacterText(removeColors((String)playerTwoCharacter.getSelectedItem()));
						setPlayerThreeCharacterText(removeColors((String)playerThreeCharacter.getSelectedItem()));
						setPlayerFourCharacterText(removeColors((String)playerFourCharacter.getSelectedItem()));
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
						setPlayerThreeCharacter();
						setPlayerFourCharacter();
					}
					if(FilesTab.getImageFolder2().listFiles().length > 0)
						setPlayerTwoSponsor();
					if(FilesTab.getImageFolder2().listFiles().length > 0) {
						setCommentatorOneSponsor();
						setCommentatorTwoSponsor();
					}
					// init thread so everything is set
					setConnector();
					sfc.writeToFiles();
				}
			}
			
		});
	}
	
	private void buildDoubles() {
		
		overridePlayerOne = false;
		overridePlayerTwo = false;
		overridePlayerThree = false;
		overridePlayerFour = false;
		
		panel.removeAll();
		panel.setBounds(10, 11, 539, 690);
		add(panel);
		panel.setLayout(null);
		
		connector = new JTextField("&");
		connector.setBounds(0, 36, 75, 20);
		connector.setToolTipText("Connecting statement for doubles partners, ex: Abszol & Bob");
		panel.add(connector);
		
		type.setBounds(0, 11, 75, 20);
		panel.add(type);
		
		JLabel lblNewLabel = new JLabel("Main Title");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 11, 519, 14);
		panel.add(lblNewLabel);
		
		JLabel gear = new JLabel("");
		gear.setBounds(89, 92, 20, 20);
		try {
			gear.setIcon(new ImageIcon(ImageIO.read(AboutMeTab.class.getResource("/Images/test.png"))));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		panel.add(gear);
		
		overrideRound = new JButton("*");
		overrideRound.setBounds(430, 92, 20, 20);
		panel.add(overrideRound);
		
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
		
		overrideR = new JComboBox();
		overrideR.setBounds(114, 92, 311, 20);
		panel.add(overrideR);
		
		for(int i = 0; i < GUI.rounds.size(); i++) {
			overrideR.addItem(GUI.rounds.get(i));
		}
		
		currentRoundText = new JTextField();
		currentRoundText.setHorizontalAlignment(SwingConstants.CENTER);
		currentRoundText.setFont(new Font("Dialog", Font.PLAIN, 12));
		currentRoundText.setBounds(114, 92, 311, 20);
		currentRoundText.setColumns(10);
		
		/*
		 * Doubles revamp
		 */
		
		JLabel lblNewLabel_13 = new JLabel("Team One");
		lblNewLabel_13.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_13.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_13.setBounds(10, 123, 519, 20);
		panel.add(lblNewLabel_13);
		
		playerOneCombo = new JComboBox();
		playerOneCombo.setBounds(89, 154, 140, 20);
		panel.add(playerOneCombo);
		
		overrideP1 = new JButton("*");
		overrideP1.setBounds(235, 154, 20, 20);
		panel.add(overrideP1);
		
		overP1 = new JTextField("");
		overP1.setBounds(89, 154, 140, 20);
		
		playerTwoCombo = new JComboBox();
		playerTwoCombo.setBounds(285, 154, 140, 20);
		panel.add(playerTwoCombo);
		
		overP2 = new JTextField("");
		overP2.setBounds(285, 154, 140, 20);
		
		overrideP2 = new JButton("*");
		overrideP2.setBounds(430, 154, 20, 20);
		panel.add(overrideP2);
		
		// team score 185, one character 247
		// textfield 216 plus/minus = 197
		playerOneCharacter = new JComboBox();
		playerOneCharacter.setBounds(89, 185, 140, 20);
		panel.add(playerOneCharacter);
		
		playerTwoSponsor = new JComboBox();
		
		playerTwoCharacter = new JComboBox();
		playerTwoCharacter.setBounds(285, 185, 140, 20);
		panel.add(playerTwoCharacter);
		
		JLabel lblNewLabel_14 = new JLabel("Team One Score");
		lblNewLabel_14.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_14.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_14.setBounds(10, 216, 519, 20);
		panel.add(lblNewLabel_14);
		
		playerOneScoreText = new JTextField();
		playerOneScoreText.setBounds(219, 247, 86, 20);
		panel.add(playerOneScoreText);
		playerOneScoreText.setColumns(10);
		
		JLabel playerOnePlus = new JLabel("");
		playerOnePlus.setBounds(89, 228, 70, 50);
		playerOnePlus.setIcon(new ImageIcon(getClass().getResource("/Plus.png")));
		panel.add(playerOnePlus);
		
		JLabel playerOneMinus = new JLabel("");
		playerOneMinus.setBounds(380, 228, 70, 50);
		playerOneMinus.setIcon(new ImageIcon(getClass().getResource("/Minus.png")));
		panel.add(playerOneMinus);
		
		JLabel lblNewLabel_15 = new JLabel("Team Two");
		lblNewLabel_15.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_15.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_15.setBounds(10, 278, 519, 20);
		panel.add(lblNewLabel_15);

		playerThreeCombo = new JComboBox();
		playerThreeCombo.setBounds(89, 309, 140, 20);
		panel.add(playerThreeCombo);
		
		overP3 = new JTextField("");
		overP3.setBounds(89, 309, 140, 20);
		
		overrideP3 = new JButton("*");
		overrideP3.setBounds(235, 309, 20, 20);
		panel.add(overrideP3);
		
		playerFourCombo = new JComboBox();
		playerFourCombo.setBounds(285, 309, 140, 20);
		panel.add(playerFourCombo);
		
		overP4 = new JTextField("");
		overP4.setBounds(285, 309, 140, 20);
		
		overrideP4 = new JButton("*");
		overrideP4.setBounds(430, 309, 20, 20);
		panel.add(overrideP4);
		
		playerThreeCharacter = new JComboBox();
		playerThreeCharacter.setBounds(89,340,140,20);
		panel.add(playerThreeCharacter);
		
		playerFourCharacter = new JComboBox();
		playerFourCharacter.setBounds(285,340,140,20);
		panel.add(playerFourCharacter);
		
		JLabel lblNewLabel_16 = new JLabel("Team Two Score");
		lblNewLabel_16.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_16.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_16.setBounds(10, 371, 519, 20);
		panel.add(lblNewLabel_16);
		
		playerTwoScoreText = new JTextField();
		playerTwoScoreText.setBounds(219, 402, 86, 20);
		panel.add(playerTwoScoreText);
		playerTwoScoreText.setColumns(10);
		
		JLabel playerTwoPlus = new JLabel("");
		playerTwoPlus.setBounds(89, 391, 70, 50);
		playerTwoPlus.setIcon(new ImageIcon(getClass().getResource("/Plus.png")));
		panel.add(playerTwoPlus);
		
		JLabel playerTwoMinus = new JLabel("");
		playerTwoMinus.setBounds(380, 391, 70, 50);
		playerTwoMinus.setIcon(new ImageIcon(getClass().getResource("/Minus.png")));
		panel.add(playerTwoMinus);
		
		// swap players names, scores, sponsors, etc
		JLabel swapLabel = new JLabel();
		swapLabel.setToolTipText("Swap Player One Objects With Player Two Objects");
		swapLabel.setBounds(240, 432, 40, 40);
		try {
			BufferedImage image = ImageIO.read(StreamUpdaterTab.class.getResource("/Images/swap.png"));
			swapLabel.setIcon(new ImageIcon(image));
		} catch (Exception e) {}
		panel.add(swapLabel);
		
		// auto switch text
		playerBox = new JCheckBox("");
		playerBox.setSelected(false);
		playerBox.setBounds(89, 475, 20, 20);
		panel.add(playerBox);
		
		JLabel playersSwitch = new JLabel("Switch Players Every ");
		playersSwitch.setToolTipText("This will switch the player names and information every set seconds.");
		playersSwitch.setBounds(125, 475, 210, 20);
		playersSwitch.setHorizontalAlignment(SwingConstants.LEFT);
		playersSwitch.setFont(new Font("Dialog", Font.BOLD, 16));
		panel.add(playersSwitch);
		
		// restrict to 1 line
		lineBox = new JCheckBox("");
		lineBox.setSelected(true);
		lineBox.setBounds(89, 510, 20, 20);
		panel.add(lineBox);
		
		JLabel lineRestrict = new JLabel("Restrict Output To One Line");
		lineRestrict.setToolTipText("Enable this feature to only show one line instead of two");
		lineRestrict.setBounds(125, 510, 250, 20);
		lineRestrict.setHorizontalAlignment(SwingConstants.LEFT);
		lineRestrict.setFont(new Font("Dialog", Font.BOLD, 16));
		panel.add(lineRestrict);
		
		playerSpin = new JSpinner();
		playerSpin.setValue(10);
		playerSpin.setBounds(350, 475, 80, 20);
		playerSpin.setFont(new Font("Dialog", Font.PLAIN, 16));
		panel.add(playerSpin);
		
		JLabel lblNewLabel_17 = new JLabel("Commentators");
		lblNewLabel_17.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_17.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_17.setBounds(10, 545, 519, 20);
		panel.add(lblNewLabel_17);
		
		overCom1 = new JTextField("");
		overCom1.setBounds(239, 575, 186, 20);
		
		overCom2 = new JTextField("");
		overCom2.setBounds(239, 605, 186, 20);
		
		overrideC1  = new JButton("*");
		overrideC1.setBounds(430, 575, 20, 20);
		panel.add(overrideC1);
		
		overrideC2 = new JButton("*");
		overrideC2.setBounds(430, 605, 20, 20);
		panel.add(overrideC2);
		
		commentatorOne = new JComboBox();
		commentatorOne.setBounds(239, 575, 186, 20);
		panel.add(commentatorOne);
		
		commentatorTwo = new JComboBox();
		commentatorTwo.setBounds(239, 605, 186, 20);
		panel.add(commentatorTwo);
		
		JButton updateButton = new JButton("Update!");
		updateButton.setFont(new Font("Dialog", Font.BOLD, 16));
		updateButton.setBounds(89, 640, 361, 50);
		panel.add(updateButton);
		
		playerOneSponsor = new JComboBox();
		
		commentatorOneSponsor = new JComboBox();
		commentatorOneSponsor.setBounds(89, 575, 140, 20);
		panel.add(commentatorOneSponsor);
		
		commentatorTwoSponsor = new JComboBox();
		commentatorTwoSponsor.setBounds(89, 605, 140, 20);
		panel.add(commentatorTwoSponsor);
		
		this.loadEverything(FilesTab.getTextFolder(), FilesTab.getPlayerFiles(), FilesTab.getCommentatorFiles(), FilesTab.getImage1Files(), FilesTab.getImage2Files());
		repaint();

		type.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if((boolean)type.getSelectedItem().equals("Singles"))
					buildSingles();
				else
					buildDoubles();
			}
			
		});
		
		swapLabel.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
				if(FilesTab.getImageFolder1() == null || FilesTab.getImageFolder2() == null 
						|| FilesTab.getPlayerFolder() == null || FilesTab.getCommentatorFolder() == null 
						|| FilesTab.getTextFolder() == null) {
					JOptionPane.showMessageDialog(null, "Please Declare All The Paths In The Files Tab!"); GUI.switchTo(3);
				} else {
					if((boolean)type.getSelectedItem().equals("Singles")) {
						int player1Pos = playerOneCombo.getSelectedIndex();
						int player2Pos = playerTwoCombo.getSelectedIndex();
						int player1Sponsor = playerOneSponsor.getSelectedIndex();
						int player2Sponsor = playerTwoSponsor.getSelectedIndex();
						int player1Char = playerOneCharacter.getSelectedIndex();
						int player2Char = playerTwoCharacter.getSelectedIndex();
						int player1Score = Integer.parseInt(playerOneScoreText.getText());
						int player2Score = Integer.parseInt(playerTwoScoreText.getText());
						String playerOneOver = overP1.getText();
						String playerTwoOver = overP2.getText();
						boolean op1 = overridePlayerOne;
						boolean op2 = overridePlayerTwo;
						//set score
						playerOneScoreText.setText(""+player2Score);
						playerTwoScoreText.setText(""+player1Score);
						//set chars
						playerOneCharacter.setSelectedIndex(player2Char);
						playerTwoCharacter.setSelectedIndex(player1Char);
						//set sponsor
						playerOneSponsor.setSelectedIndex(player2Sponsor);
						playerTwoSponsor.setSelectedIndex(player1Sponsor);
						
						// player one & two have a text field
						if(overridePlayerOne && overridePlayerTwo) {
							overP2.setText(playerOneOver);
							overP1.setText(playerTwoOver);
						} else 
						// player one is text but player two is combo
						if(overridePlayerOne && !overridePlayerTwo) {
							// swap the booleans
							overridePlayerOne = op2;
							overridePlayerTwo = op1;
							// enable p1 combo but enable p2 text
							panel.remove(overP1);
							panel.add(playerOneCombo);
							panel.remove(playerTwoCombo);
							panel.add(overP2);
							repaint();
							// set stuff
							overP2.setText(playerOneOver);
							playerOneCombo.setSelectedIndex(player2Pos);
							
						} else
						// player one is combo but player two is text
						if(!overridePlayerOne && overridePlayerTwo) {
							// swap the booleans
							overridePlayerOne = op2;
							overridePlayerTwo = op1;
							// enable p1 combo but enable p2 text
							panel.remove(playerOneCombo);
							panel.add(overP1);
							panel.remove(overP2);
							panel.add(playerTwoCombo);
							repaint();
							// set stuff
							overP1.setText(playerTwoOver);
							playerTwoCombo.setSelectedIndex(player1Pos);
						} else
						// both are combos
						if(!overridePlayerOne && !overridePlayerTwo) {
							playerTwoCombo.setSelectedIndex(player1Pos);
							playerOneCombo.setSelectedIndex(player2Pos);
						}
						updateButton.doClick();
					} else {
						int player1Pos = playerOneCombo.getSelectedIndex();
						int player2Pos = playerTwoCombo.getSelectedIndex();
						int player3Pos = playerThreeCombo.getSelectedIndex();
						int player4Pos = playerFourCombo.getSelectedIndex();
						int player1Char = playerOneCharacter.getSelectedIndex();
						int player2Char = playerTwoCharacter.getSelectedIndex();
						int player3Char = playerThreeCharacter.getSelectedIndex();
						int player4Char = playerFourCharacter.getSelectedIndex();
						int player1Score = Integer.parseInt(playerOneScoreText.getText());
						int player2Score = Integer.parseInt(playerTwoScoreText.getText());
						String playerOneOver = overP1.getText();
						String playerTwoOver = overP2.getText();
						String playerThreeOver = overP3.getText();
						String playerFourOver = overP4.getText();
						boolean op1 = overridePlayerOne;
						boolean op2 = overridePlayerTwo;
						boolean op3 = overridePlayerThree;
						boolean op4 = overridePlayerFour;
						//set score
						playerOneScoreText.setText(""+player2Score);
						playerTwoScoreText.setText(""+player1Score);
						//set chars
						playerOneCharacter.setSelectedIndex(player3Char);
						playerTwoCharacter.setSelectedIndex(player4Char);
						playerThreeCharacter.setSelectedIndex(player1Char);
						playerFourCharacter.setSelectedIndex(player2Char);
						
						// have to do this twice, first is player 1 vs player 3 swap
						// player one & two have a text field
						if(overridePlayerOne && overridePlayerThree) {
							overP3.setText(playerOneOver);
							overP1.setText(playerTwoOver);
						} else 
						// player one is text but player two is combo
						if(overridePlayerOne && !overridePlayerThree) {
							// swap the booleans
							overridePlayerOne = op3;
							overridePlayerThree = op1;
							// enable p1 combo but enable p2 text
							panel.remove(overP1);
							panel.add(playerOneCombo);
							panel.remove(playerThreeCombo);
							panel.add(overP3);
							repaint();
							// set stuff
							overP3.setText(playerOneOver);
							playerOneCombo.setSelectedIndex(player3Pos);
							
						} else
						// player one is combo but player two is text
						if(!overridePlayerOne && overridePlayerThree) {
							// swap the booleans
							overridePlayerOne = op3;
							overridePlayerThree = op1;
							// enable p1 combo but enable p2 text
							panel.remove(playerOneCombo);
							panel.add(overP1);
							panel.remove(overP3);
							panel.add(playerThreeCombo);
							repaint();
							// set stuff
							overP1.setText(playerThreeOver);
							playerThreeCombo.setSelectedIndex(player1Pos);
						} else
						// both are combos
						if(!overridePlayerOne && !overridePlayerThree) {
							playerThreeCombo.setSelectedIndex(player1Pos);
							playerOneCombo.setSelectedIndex(player3Pos);
						}
						/*
						 * player 2 vs player 4
						 */
						if(overridePlayerTwo && overridePlayerFour) {
							overP4.setText(playerTwoOver);
							overP2.setText(playerFourOver);
						} else 
						// player one is text but player two is combo
						if(overridePlayerTwo && !overridePlayerFour) {
							// swap the booleans
							overridePlayerTwo = op4;
							overridePlayerFour = op2;
							// enable p1 combo but enable p2 text
							panel.remove(overP2);
							panel.add(playerTwoCombo);
							panel.remove(playerFourCombo);
							panel.add(overP4);
							repaint();
							// set stuff
							overP4.setText(playerTwoOver);
							playerTwoCombo.setSelectedIndex(player4Pos);
							
						} else
						// player one is combo but player two is text
						if(!overridePlayerTwo && overridePlayerFour) {
							// swap the booleans
							overridePlayerTwo = op4;
							overridePlayerFour = op2;
							// enable p1 combo but enable p2 text
							panel.remove(playerTwoCombo);
							panel.add(overP2);
							panel.remove(overP4);
							panel.add(playerFourCombo);
							repaint();
							// set stuff
							overP2.setText(playerFourOver);
							playerFourCombo.setSelectedIndex(player2Pos);
						} else
						// both are combos
						if(!overridePlayerTwo && !overridePlayerFour) {
							playerFourCombo.setSelectedIndex(player2Pos);
							playerTwoCombo.setSelectedIndex(player4Pos);
						}
						updateButton.doClick();
					}
				}
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
		
		gear.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
				// only event I care about
				if(roundControl == null) {
					roundControl = new AdjustRounds();
				} else
					roundControl.unhide();
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
			
		});
		
		overrideRound.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(overrideRounds) {
					overrideRounds = false;
					panel.remove(overrideR);
					panel.add(currentRoundText);
					repaint();
				} else {
					overrideRounds = true;
					panel.remove(currentRoundText);
					panel.add(overrideR);
					repaint();
				}
			}
			
		});
		
		overrideP1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(overridePlayerOne) {
					overridePlayerOne = false;
					panel.remove(overP1);
					panel.add(playerOneCombo);
					repaint();
				} else {
					overridePlayerOne = true;
					panel.remove(playerOneCombo);
					panel.add(overP1);
					repaint();
				}
			}
			
		});
		
		overrideP2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(overridePlayerTwo) {
					overridePlayerTwo = false;
					panel.remove(overP2);
					panel.add(playerTwoCombo);
					repaint();
				} else {
					overridePlayerTwo = true;
					panel.remove(playerTwoCombo);
					panel.add(overP2);
					repaint();
				}
			}
			
		});
		
		overrideP3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(overridePlayerThree) {
					overridePlayerThree = false;
					panel.remove(overP3);
					panel.add(playerThreeCombo);
					repaint();
				} else {
					overridePlayerThree = true;
					panel.remove(playerThreeCombo);
					panel.add(overP3);
					repaint();
				}
			}
			
		});
		
		overrideP4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(overridePlayerFour) {
					overridePlayerFour = false;
					panel.remove(overP4);
					panel.add(playerFourCombo);
					repaint();
				} else {
					overridePlayerFour = true;
					panel.remove(playerFourCombo);
					panel.add(overP4);
					repaint();
				}
			}
			
		});
		
		overrideC1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(overrideCom) {
					overrideCom = false;
					panel.remove(overCom2);
					panel.add(commentatorTwo);
					panel.remove(overCom1);
					panel.add(commentatorOne);
					repaint();
				} else {
					overrideCom = true;
					panel.remove(commentatorTwo);
					panel.add(overCom2);
					panel.remove(commentatorOne);
					panel.add(overCom1);
					repaint();
				}
			}
		});
		
		overrideC2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(overrideCom) {
					overrideCom = false;
					panel.remove(overCom2);
					panel.add(commentatorTwo);
					panel.remove(overCom1);
					panel.add(commentatorOne);
					repaint();
				} else {
					overrideCom = true;
					panel.remove(commentatorTwo);
					panel.add(overCom2);
					panel.remove(commentatorOne);
					panel.add(overCom1);
					repaint();
				}				
			}
			
		});
		
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
												if(playerOneCombo.getItemCount() > 0 || !overP1.getText().equals(""))
													setPlayerOneName(swap);
												if(playerTwoCombo.getItemCount() > 0 || !overP2.getText().equals(""))
													setPlayerTwoName(swap);
												if(playerThreeCombo.getItemCount() > 0 || !overP3.getText().equals(""))
													setPlayerThreeName(swap);
												if(playerFourCombo.getItemCount() > 0 || !overP4.getText().equals(""))
													setPlayerFourName(swap);
												swap = true;
												
											} else {
												if(playerOneCombo.getItemCount() > 0 || !overP1.getText().equals(""))
													setPlayerOneName(swap);
												if(playerTwoCombo.getItemCount() > 0 || !overP2.getText().equals(""))
													setPlayerTwoName(swap);
												if(playerThreeCombo.getItemCount() > 0 || !overP3.getText().equals(""))
													setPlayerThreeName(swap);
												if(playerFourCombo.getItemCount() > 0 || !overP4.getText().equals(""))
													setPlayerFourName(swap);
												swap = false;
											}
											sfc.writeToText();
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
						if(RenderTab.isStreaming()) {
							RenderTab.appendCharacterSystem();
						}
						setMainTitle();
						setCurrentRound();
						if(playerOneCombo.getItemCount() > 0 || !overP1.getText().equals(""))
							setPlayerOneName(swap);
						setPlayerOneScore();
						if(playerTwoCombo.getItemCount() > 0 || !overP2.getText().equals(""))
							setPlayerTwoName(swap);
						if(playerThreeCombo.getItemCount() > 0 || !overP3.getText().equals(""))
							setPlayerThreeName(swap);
						if(playerFourCombo.getItemCount() > 0 || !overP4.getText().equals(""))
							setPlayerFourName(swap);
						setTeamOne();
						setTeamTwo();
						setPlayerTwoScore();
						if((commentatorOne.getItemCount() > 0 && commentatorTwo.getItemCount() > 0) || (!overCom1.getText().equals("") && !overCom2.getText().equals("")))
							setCommentators();
						setPlayerOneCharacterText(removeColors((String)playerOneCharacter.getSelectedItem()));
						setPlayerTwoCharacterText(removeColors((String)playerTwoCharacter.getSelectedItem()));
						setPlayerThreeCharacterText(removeColors((String)playerThreeCharacter.getSelectedItem()));
						setPlayerFourCharacterText(removeColors((String)playerFourCharacter.getSelectedItem()));
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
						setPlayerThreeCharacter();
						setPlayerFourCharacter();
					}
					if(FilesTab.getImageFolder2().listFiles().length > 0)
						setPlayerTwoSponsor();
					if(FilesTab.getImageFolder2().listFiles().length > 0) {
						setCommentatorOneSponsor();
						setCommentatorTwoSponsor();
					}
					// init thread so everything is set
					setConnector();
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
			playerThreeCombo.addItem(name);
			playerFourCombo.addItem(name);
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
		if(textFolder == null) return;
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
		playerThreeCombo.removeAllItems();
		playerFourCombo.removeAllItems();
		commentatorOne.removeAllItems();
		commentatorTwo.removeAllItems();
		playerOneCharacter.removeAllItems();
		playerOneSponsor.removeAllItems();
		playerTwoCharacter.removeAllItems();
		playerTwoSponsor.removeAllItems();
		playerThreeCharacter.removeAllItems();
		playerFourCharacter.removeAllItems();
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
						playerThreeCombo.addItem(line);
						playerFourCombo.addItem(line);
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
					playerThreeCharacter.addItem(characterArray[i].getName());
					playerFourCharacter.addItem(characterArray[i].getName());
					
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
		if(overrideRounds) {
			StreamUpdaterTab.sfc.setCurrentRound((String)StreamUpdaterTab.overrideR.getSelectedItem());
		} else
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
		if(!overridePlayerOne) {
			if(!swap) {
				sfc.setPlayerOne((String) playerOneCombo.getSelectedItem());
				sfc.setPlayerOneInfo(playerInfo.get(playerOneCombo.getSelectedIndex()));
			} else {
				sfc.setPlayerOne(playerInfo.get(playerOneCombo.getSelectedIndex()));
				sfc.setPlayerOneInfo((String) playerOneCombo.getSelectedItem());
			}
		} else {
			sfc.setPlayerOne(overP1.getText());
		}
	}
	
	private void setPlayerTwoName(boolean swap) {
		if(!overridePlayerTwo) {
			if(!swap) {
				sfc.setPlayerTwo((String) playerTwoCombo.getSelectedItem());
				sfc.setPlayerTwoInfo(playerInfo.get(playerTwoCombo.getSelectedIndex()));
			} else {
				sfc.setPlayerTwo(playerInfo.get(playerTwoCombo.getSelectedIndex()));
				sfc.setPlayerTwoInfo((String) playerTwoCombo.getSelectedItem());
			}
		} else {
			sfc.setPlayerTwo(overP2.getText());
		}
	}
	
	private void setPlayerThreeName(boolean swap) {
		if(!overridePlayerThree) {
			if(!swap) {
				sfc.setPlayerThree((String) playerThreeCombo.getSelectedItem());
				sfc.setPlayerThreeInfo(playerInfo.get(playerThreeCombo.getSelectedIndex()));
			} else {
				sfc.setPlayerThree(playerInfo.get(playerThreeCombo.getSelectedIndex()));
				sfc.setPlayerThreeInfo((String) playerThreeCombo.getSelectedItem());
			}
		} else {
			sfc.setPlayerThree(overP3.getText());
		}
	}
	
	private void setPlayerFourName(boolean swap) {
		if(!overridePlayerFour) {
			if(!swap) {
				sfc.setPlayerFour((String) playerFourCombo.getSelectedItem());
				sfc.setPlayerFourInfo(playerInfo.get(playerFourCombo.getSelectedIndex()));
			} else {
				sfc.setPlayerFour(playerInfo.get(playerFourCombo.getSelectedIndex()));
				sfc.setPlayerFourInfo((String) playerFourCombo.getSelectedItem());
			}
		} else {
			sfc.setPlayerFour(overP4.getText());
		}
	}
	
	/*
	 * Incase of failure to set
	 */
	public static String getPlayerOneName() {
		if(!overridePlayerOne) {
				return (String) playerOneCombo.getSelectedItem();
		} else {
			return overP1.getText();
		}
	}
	
	public static String getPlayerTwoName() {
		if(!overridePlayerTwo) {
			return (String) playerTwoCombo.getSelectedItem();
		} else {
			return overP2.getText();
		}
	}
	
	private void setCommentators() {
		if(!overrideCom) {
			sfc.setCommentators((String)commentatorOne.getSelectedItem(), (String)commentatorTwo.getSelectedItem());
			sfc.setCommentatorsInfo(commentatorInfo.get(commentatorOne.getSelectedIndex()), commentatorInfo.get(commentatorTwo.getSelectedIndex()));
		} else {
			sfc.setCommentators(overCom1.getText(), overCom2.getText());
		}
	}
	
	private void setTeamOne() {
		sfc.setTeamOne(sfc.getPlayerOne(), connector.getText(), sfc.getPlayerTwo());
	}
	
	private void setTeamTwo() {
		sfc.setTeamTwo(sfc.getPlayerThree(), connector.getText(), sfc.getPlayerFour());
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
	
	private void setPlayerThreeCharacter() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(characterArray[playerThreeCharacter.getSelectedIndex()].getAbsolutePath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		sfc.setPlayerThreeCharacter(image);
	}
	
	private void setPlayerFourCharacter() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(characterArray[playerFourCharacter.getSelectedIndex()].getAbsolutePath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		sfc.setPlayerFourCharacter(image);
	}
	
	private void setConnector() {
		sfc.setTeamCombineCharacter(connector.getText());
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
	
	private void setPlayerThreeCharacterText(String n) {
		sfc.setPlayerThreeCharacterText(n);
	}
	
	private void setPlayerFourCharacterText(String n) {
		sfc.setPlayerFourCharacterText(n);
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
	
	public static void setRounds(ArrayList<String> rounds) {
		overrideR.removeAllItems();
		for(int i = 0; i < rounds.size(); i++) {
			overrideR.addItem(rounds.get(i));
		}
	}
	
}
