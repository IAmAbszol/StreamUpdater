package streamupdater.gui.components;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import streamupdater.gui.main.GUI;

@SuppressWarnings("serial")
public class TournamentEnlisterTab extends JPanel {

	private JFileChooser jfc;
	
	private JTextField playerName;
	private JTextField playerInfo;
	private JTextField commentatorName;
	private JTextField commentatorInfo;
	private JTextField sponsorImageLocation;

	private static String playerPath = null;
	private static String commentatorPath = null;
	private static String sponsorPath = null;
	
	// flush
	private static JLabel flush;
	
	private static BufferedImage sponsorImage;
	
	public TournamentEnlisterTab() {
		
		setLayout(null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(10, 11, 539, 690);
		add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lblNewLabel_18 = new JLabel("Name/Tag");
		lblNewLabel_18.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_18.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_18.setBounds(10, 43, 519, 21);
		panel_4.add(lblNewLabel_18);
		
		playerName = new JTextField();
		playerName.setHorizontalAlignment(SwingConstants.CENTER);
		playerName.setFont(new Font("Dialog", Font.PLAIN, 12));
		playerName.setColumns(10);
		playerName.setBounds(86, 75, 361, 20);
		panel_4.add(playerName);
		
		JLabel lblNewLabel_19 = new JLabel("Where can we find you? (One Thing!)");
		lblNewLabel_19.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_19.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_19.setBounds(10, 106, 519, 21);
		panel_4.add(lblNewLabel_19);
		
		playerInfo = new JTextField();
		playerInfo.setHorizontalAlignment(SwingConstants.CENTER);
		playerInfo.setFont(new Font("Dialog", Font.PLAIN, 12));
		playerInfo.setBounds(86, 138, 361, 20);
		panel_4.add(playerInfo);
		playerInfo.setColumns(10);
		
		JLabel lblNewLabel_20 = new JLabel("Player Enlisting");
		lblNewLabel_20.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_20.setFont(new Font("Dialog", Font.BOLD, 20));
		lblNewLabel_20.setBounds(10, 11, 519, 26);
		panel_4.add(lblNewLabel_20);
		
		JButton playerEnlistButton = new JButton("Enlist!");
		playerEnlistButton.setFont(new Font("Dialog", Font.BOLD, 14));
		playerEnlistButton.setBounds(86, 169, 361, 40);
		panel_4.add(playerEnlistButton);
		
		flush = new JLabel("");
		flush.setHorizontalAlignment(SwingConstants.CENTER);
		flush.setBounds(250, 230, 50, 50);
		flush.setToolTipText("Click here to flush player list into commentators list");
		BufferedImage arrow;
		try {
			arrow = ImageIO.read(AboutMeTab.class.getResource("/Images/flush.png"));
			flush.setIcon(new ImageIcon(arrow));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		panel_4.add(flush);
		
		JLabel lblNewLabel_21 = new JLabel("Commentator Enlisting");
		lblNewLabel_21.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_21.setFont(new Font("Dialog", Font.BOLD, 20));
		lblNewLabel_21.setBounds(10, 304, 519, 21);
		panel_4.add(lblNewLabel_21);
		
		JLabel lblNewLabel_22 = new JLabel("Name/Tag");
		lblNewLabel_22.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_22.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_22.setBounds(10, 336, 519, 21);
		panel_4.add(lblNewLabel_22);
		
		commentatorName = new JTextField();
		commentatorName.setHorizontalAlignment(SwingConstants.CENTER);
		commentatorName.setFont(new Font("Dialog", Font.PLAIN, 12));
		commentatorName.setBounds(86, 368, 361, 20);
		panel_4.add(commentatorName);
		commentatorName.setColumns(10);
		
		JLabel lblNewLabel_23 = new JLabel("Where can we find you? (One Thing!)");
		lblNewLabel_23.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_23.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_23.setBounds(86, 399, 361, 21);
		panel_4.add(lblNewLabel_23);
		
		commentatorInfo = new JTextField();
		commentatorInfo.setHorizontalAlignment(SwingConstants.CENTER);
		commentatorInfo.setFont(new Font("Dialog", Font.PLAIN, 12));
		commentatorInfo.setBounds(86, 431, 361, 20);
		panel_4.add(commentatorInfo);
		commentatorInfo.setColumns(10);
		
		JButton commentatorEnlistButton = new JButton("Enlist!");
		commentatorEnlistButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		commentatorEnlistButton.setFont(new Font("Dialog", Font.BOLD, 14));
		commentatorEnlistButton.setBounds(86, 462, 361, 40);
		panel_4.add(commentatorEnlistButton);
		
		JLabel lblSponsor = new JLabel("Sponsor");
		lblSponsor.setHorizontalAlignment(SwingConstants.CENTER);
		lblSponsor.setFont(new Font("Dialog", Font.BOLD, 20));
		lblSponsor.setBounds(0, 548, 519, 21);
		panel_4.add(lblSponsor);
		
		JButton sponsorEnlistButton = new JButton("Enlist!");
		sponsorEnlistButton.setFont(new Font("Dialog", Font.BOLD, 14));
		sponsorEnlistButton.setBounds(86, 611, 361, 40);
		panel_4.add(sponsorEnlistButton);
		
		sponsorImageLocation = new JTextField();
		sponsorImageLocation.setBounds(86, 580, 262, 20);
		sponsorImageLocation.setFont(new Font("Dialog", Font.PLAIN, 12));
		panel_4.add(sponsorImageLocation);
		sponsorImageLocation.setColumns(10);
		
		JButton browseImage = new JButton("Browse");
		browseImage.setFont(new Font("Dialog", Font.PLAIN, 14));
		browseImage.setBounds(358, 580, 89, 23);
		panel_4.add(browseImage);
		
		browseImage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jfc = new JFileChooser();
				jfc.setCurrentDirectory(new java.io.File("user.home"));
				jfc.setDialogTitle("Please Select Your Image");
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if (jfc.showOpenDialog(browseImage) == JFileChooser.APPROVE_OPTION) {
					sponsorImageLocation.setText(jfc.getSelectedFile().getAbsolutePath().replace("\\", "/"));
				}
			}
		});
		
		flush.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					if(playerPath != null && commentatorPath != null) {
						for(int i = 0; i < StreamUpdaterTab.getPlayers().size(); i++) {
							boolean write = true;
							for(int j = 0; j < StreamUpdaterTab.getCommentators().size(); j++) {
								if(StreamUpdaterTab.getPlayers().get(i).equals(StreamUpdaterTab.getCommentators().get(j))) {
									write = false;
								}
							}
							if(write) {
								PrintWriter writer = new PrintWriter(new File(commentatorPath + "/" + removeIllegal(StreamUpdaterTab.getPlayers().get(i)) + ".txt"));
								writer.println(StreamUpdaterTab.getPlayers().get(i));
								writer.print(StreamUpdaterTab.getPlayerInfo().get(i));
								writer.close();
		
								StreamUpdaterTab.updateList(StreamUpdaterTab.getPlayers().get(i), StreamUpdaterTab.getPlayerInfo().get(i), false);
							}
						}
					} else { JOptionPane.showMessageDialog(null, "Please Declare the Player/Commentator Path Using The Files Tab!"); GUI.switchTo(3); }
				} catch (Exception e) {}
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
		
		sponsorEnlistButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateSponsor();
			}
			
		});
		
		playerEnlistButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				updatePlayer();
			}
			
		});
		
		commentatorEnlistButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateCommentator();
			}
			
		});
		
	}
	
	private void updateSponsor() {
		if(sponsorPath != null) {
			try {
				File tmp = new File(sponsorImageLocation.getText());
				File name = new File(sponsorPath + "/" + tmp.getName());
				sponsorImage = ImageIO.read(new File(sponsorImageLocation.getText()));
				ImageIO.write(sponsorImage, "png", name);
				sponsorImageLocation.setText("");
			} catch (Exception e2) {  JOptionPane.showMessageDialog(null, "File Not Found! Please Revise"); }
				
		} else { JOptionPane.showMessageDialog(null, "Please Declare the Sponsors Path Using The Files Tab!"); GUI.switchTo(3); }
	}
	
	private void updatePlayer() {
		
		if(playerPath != null) {
			try {
				boolean write = true;
				for(int i = 0; i < StreamUpdaterTab.getPlayers().size(); i++) {
					if(playerName.getText().equals(StreamUpdaterTab.getPlayers().get(i))) {
						write = false;
					}
				}
				if(write) {
					PrintWriter writer = new PrintWriter(new File(playerPath + "/" + removeIllegal(playerName.getText()) + ".txt"));
					writer.println(playerName.getText());
					writer.print(playerInfo.getText());
					writer.close();
					
					StreamUpdaterTab.updateList(playerName.getText(), playerInfo.getText(), true);
					
					playerName.setText("");
					playerInfo.setText("");
				} else {
					playerName.setText("");
					playerInfo.setText("");
					JOptionPane.showMessageDialog(null, "Error! User Already Exists!");
				}
				
			} catch (Exception e) { 
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Folder Not Found!"); }
		} else {
			JOptionPane.showMessageDialog(null, "Please Declare The Players Path Using The Files Tab!");
			GUI.switchTo(3);
		}
		
	}
	
	private void updateCommentator() {
		if(commentatorPath != null) {
			try {
				boolean write = true;
				for(int i = 0; i < StreamUpdaterTab.getCommentators().size(); i++) {
					if(commentatorName.getText().equals(StreamUpdaterTab.getCommentators().get(i))) {
						write = false;
					}
				}
				if(write) {
					PrintWriter writer = new PrintWriter(new File(commentatorPath + "/" + removeIllegal(commentatorName.getText()) + ".txt"));
					writer.println(commentatorName.getText());
					writer.print(commentatorInfo.getText());
					writer.close();
	
					StreamUpdaterTab.updateList(commentatorName.getText(), commentatorInfo.getText(), false);
					
					commentatorName.setText("");
					commentatorInfo.setText("");
				} else {
					commentatorName.setText("");
					commentatorInfo.setText("");
					JOptionPane.showMessageDialog(null, "Error! User Already Exists!");
				}
				
			} catch (Exception e) { JOptionPane.showMessageDialog(null, "Folder Not Found!"); }
		} else {
			JOptionPane.showMessageDialog(null, "Please Declare The Commentators Path Using The Files Tab!");
			GUI.switchTo(3);
		}
	}
	
	public static void updatePlayer(String playerName, String playerInfo) {
		if(playerPath != null) {
			try {
				boolean write = true;
				for(int i = 0; i < StreamUpdaterTab.getPlayers().size(); i++) {
					if(playerName.equals(StreamUpdaterTab.getPlayers().get(i))) {
						write = false;
					}
				}
				if(write) {
					PrintWriter writer = new PrintWriter(new File(playerPath + "/" + removeIllegal(playerName) + ".txt"));
					writer.println(playerName);
					writer.print(playerInfo);
					writer.close();
					
					StreamUpdaterTab.updateList(playerName, playerInfo, true);
					
				}
				
			} catch (Exception e) { 
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Folder Not Found!"); }
		} else {
			JOptionPane.showMessageDialog(null, "Please Declare The Players Path Using The Files Tab!");
			GUI.switchTo(3);
		}
	}
	
	public static void updatePlayerPath(String player) {
		playerPath = player;
	}
	
	public static void updateCommentatorPath(String commentator)  {
		commentatorPath = commentator;
	}

	public static void updateSponsorPath(String sponsor) {
		sponsorPath = sponsor;
	}
	
	public static String getPlayerPath() {
		return playerPath;
	}
	
	public static String getCommentatorPath() {
		return commentatorPath;
	}
	
	private static String removeIllegal(String n) {
		String build = "";
		boolean start = false;
		for(int i = 0; i < n.length(); i++) {
			if(n.charAt(i) != ' ' && start) {
				build = build + n.charAt(i);
			}
			if(n.charAt(i) == '|') {
				start = true;
			}
		}
		if(!start) return n;
		return build;
	}
	
}
