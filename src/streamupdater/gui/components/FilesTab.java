package streamupdater.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import streamupdater.gui.main.GUI;
import streamupdater.util.ExcelSheetCollection;
import streamupdater.util.FilesTabSave;

@SuppressWarnings("serial")
public class FilesTab extends JPanel {
	
	private JTextField imageFolder1Location;
	private JTextField imageFolder2;
	private JTextField textField;
	private JTextField commentators;
	private JTextField textFileAccess;
	private JLabel images;
	private JLabel sponsors;
	private JLabel lblNewLabel_7;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_24;
	private JLabel label;
	
	private JFileChooser jfc;
	
	// file stuff
	private static File image1Folder;
	private static File[] image1Files;
	
	private static File image2Folder;
	private static File[] image2Files;
	
	private static File playerFolder;
	private static File[] playerFiles;
	
	private static File commentatorFolder;
	private static File[] commentatorFiles;
	
	private static File textFolder;
	private static File[] textFiles;
	
	// titles
	private String field1 = "Characters Image Folder";
	private String field2 = "Sponsors Image Folder";
	private String field3 = "Player Name(s) Folder";
	private String field4 = "Commentator Name(s) Folder";
	private String field5 = "Root File Access Folder";
	
	private static String saveConfigName = "filesconfig.cfg";
	
	private ArrayList<String> saveStuff;
	
	public FilesTab() {
		
		setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 11, 539, 690);
		add(panel_2);
		panel_2.setLayout(null);
		
		images = new JLabel(field1);
		images.setFont(new Font("Tahoma", Font.PLAIN, 14));
		images.setBounds(10, 11, 200, 23);
		panel_2.add(images);
		
		imageFolder1Location = new JTextField();
		imageFolder1Location.setFont(new Font("Tahoma", Font.PLAIN, 14));
		imageFolder1Location.setBounds(10, 45, 224, 20);
		panel_2.add(imageFolder1Location);
		imageFolder1Location.setColumns(10);
		
		JButton browseImageFolder1 = new JButton("Browse");
		browseImageFolder1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		browseImageFolder1.setBounds(244, 46, 89, 23);
		panel_2.add(browseImageFolder1);
		
		sponsors = new JLabel(field2);
		sponsors.setFont(new Font("Tahoma", Font.PLAIN, 14));
		sponsors.setBounds(10, 76, 187, 23);
		panel_2.add(sponsors);
		
		imageFolder2 = new JTextField();
		imageFolder2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		imageFolder2.setBounds(10, 110, 224, 20);
		panel_2.add(imageFolder2);
		imageFolder2.setColumns(10);
		
		JButton browseImageFolder2 = new JButton("Browse");
		browseImageFolder2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		browseImageFolder2.setBounds(244, 111, 89, 23);
		panel_2.add(browseImageFolder2);
		
		lblNewLabel_7 = new JLabel(field3);
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_7.setBounds(10, 141, 187, 23);
		panel_2.add(lblNewLabel_7);
		
		textField = new JTextField();
		textField.setBounds(10, 175, 224, 20);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_2.add(textField);
		textField.setColumns(10);
		
		JButton browsePlayerNames = new JButton("Browse");
		browsePlayerNames.setFont(new Font("Tahoma", Font.PLAIN, 14));
		browsePlayerNames.setBounds(244, 174, 89, 23);
		panel_2.add(browsePlayerNames);
		
		JButton load = new JButton("Load Files");
		load.setFont(new Font("Tahoma", Font.PLAIN, 14));
		load.setToolTipText("Load all the files you presently linked");
		load.setBounds(10, 350, 300, 23);
		panel_2.add(load);
		
		JButton loadFromGoogleSheets = new JButton("Load Data From Excel Sheets (Beta)");
		loadFromGoogleSheets.setFont(new Font("Tahoma", Font.PLAIN, 14));
		loadFromGoogleSheets.setToolTipText("Downloaded From Google Sheets, Read Users + Info All At Once");
		loadFromGoogleSheets.setBounds(10, 390, 300, 23);
		panel_2.add(loadFromGoogleSheets);
		
		JButton loadBracket = new JButton("Load Bracket URL");
		loadBracket.setFont(new Font("Tahoma", Font.PLAIN, 14));
		loadBracket.setToolTipText("If you have a bracket, use this to manage it within Bracket Tab");
		loadBracket.setBounds(10, 430, 300, 23);
		panel_2.add(loadBracket);
		
		JButton save = new JButton("Save Configuration");
		save.setFont(new Font("Tahoma", Font.PLAIN, 14));
		save.setToolTipText("Save the configuration based on your OS ("+findFile(saveConfigName)+")");
		save.setBounds(10, 470, 300, 23);
		panel_2.add(save);
		
		JButton delete = new JButton("Remove Configuration");
		delete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		delete.setToolTipText("Remove the configuration at " + findFile(saveConfigName));
		delete.setBounds(10, 510, 300, 23);
		panel_2.add(delete);
		
		lblNewLabel_6 = new JLabel(field4);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_6.setBounds(10, 206, 224, 23);
		panel_2.add(lblNewLabel_6);
		
		commentators = new JTextField();
		commentators.setBounds(10, 240, 224, 20);
		commentators.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_2.add(commentators);
		commentators.setColumns(10);
		
		JButton browseCommentatorNames = new JButton("Browse");
		browseCommentatorNames.setFont(new Font("Tahoma", Font.PLAIN, 14));
		browseCommentatorNames.setBounds(244, 239, 89, 23);
		panel_2.add(browseCommentatorNames);
		
		lblNewLabel_24 = new JLabel(field5);
		lblNewLabel_24.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_24.setBounds(10, 271, 187, 23);
		panel_2.add(lblNewLabel_24);
		
		textFileAccess = new JTextField();
		textFileAccess.setBounds(10, 305, 224, 20);
		textFileAccess.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_2.add(textFileAccess);
		textFileAccess.setColumns(10);
		
		JButton browseAccess = new JButton("Browse");
		browseAccess.setFont(new Font("Tahoma", Font.PLAIN, 14));
		browseAccess.setBounds(244, 304, 89, 23);
		panel_2.add(browseAccess);
		
		JLabel change1 = new JLabel("Change");
		change1.setToolTipText("Click to change what we are searching for");
		change1.setForeground(Color.BLUE);
		change1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		change1.setBounds(287, 17, 46, 14);
		panel_2.add(change1);
		
		JLabel change2 = new JLabel("Change");
		change2.setToolTipText("Click to change what we are searching for");
		change2.setForeground(Color.BLUE);
		change2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		change2.setBounds(287, 82, 46, 14);
		panel_2.add(change2);
		
		JLabel change3 = new JLabel("Change");
		change3.setToolTipText("Click to change what we are searching for");
		change3.setForeground(Color.BLUE);
		change3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		change3.setBounds(287, 147, 46, 14);
		panel_2.add(change3);
		
		JLabel change4 = new JLabel("Change");
		change4.setToolTipText("Click to change what we are searching for");
		change4.setForeground(Color.BLUE);
		change4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		change4.setBounds(287, 212, 46, 14);
		panel_2.add(change4);
		
		JLabel change5 = new JLabel("Change");
		change5.setToolTipText("Click to change what we are searching for");
		change5.setForeground(Color.BLUE);
		change5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		change5.setBounds(287, 277, 46, 14);
		panel_2.add(change5);
		
		loadBracket.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame frame = new JFrame("Bracket URL");
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setBounds(100, 100, 450, 150);
				JPanel contentPane = new JPanel();
				contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				frame.setContentPane(contentPane);
				contentPane.setLayout(null);
				
				JPanel panel = new JPanel();
				panel.setBounds(10, 11, 414, 89);
				contentPane.add(panel);
				panel.setLayout(null);
				
				JLabel lblNewLabel = new JLabel("Bracket URL Location");
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 14));
				lblNewLabel.setBounds(10, 11, 394, 30);
				panel.add(lblNewLabel);
				
				JTextField textField = new JTextField();
				textField.setFont(new Font("Dialog", Font.PLAIN, 12));
				textField.setBounds(10, 52, 295, 20);
				panel.add(textField);
				textField.setColumns(10);
				
				JButton go = new JButton("Go");
				go.setBounds(315, 52, 89, 23);
				panel.add(go);
				
				frame.setResizable(false);
				frame.setVisible(true);
				
				go.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						SwingUtilities.invokeLater(new Runnable() {
				            @Override
				            public void run() {
				            	frame.dispose();
				            	WebViewSample w = new WebViewSample(GUI.getArguments(), textField.getText());
				            	//BracketTab.buildBrowser(textField.getText());
				            }
				        });
					}
					
				});
			}
			
		});
		
		loadFromGoogleSheets.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!imageFolder1Location.getText().equals("") &&
						!imageFolder2.getText().equals("") && 
						!textField.getText().equals("") &&
						!commentators.getText().equals("") &&
						!textFileAccess.getText().equals("")) {
					
					// set the information, this button uses the tournament enlister functions to complete loading
					TournamentEnlisterTab.updatePlayerPath(textField.getText());
					TournamentEnlisterTab.updateCommentatorPath(commentators.getText());
					TournamentEnlisterTab.updateSponsorPath(imageFolder2.getText());
					
					new ExcelSheetCollection();
					
				} else JOptionPane.showMessageDialog(null, "Please Declare All Fields!");
				
			}
			
		});
		
		change1.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				String tmp = JOptionPane.showInputDialog(null, "Change text to ", field1);
				if(tmp != null) {
					field1 = tmp;
					images.setText(field1);
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
		});
		
		change2.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String tmp = JOptionPane.showInputDialog(null, "Change text to ", field2);
				if(tmp != null) {
					field2 = tmp;
					sponsors.setText(field2);
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});
		
		change3.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String tmp = JOptionPane.showInputDialog(null, "Change text to ", field3);
				if(tmp != null) {
					field3 = tmp;
					lblNewLabel_7.setText(field3);
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});
		
		change4.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String tmp = JOptionPane.showInputDialog(null, "Change text to ", field4);
				if(tmp != null) {
					field4 = tmp;
					lblNewLabel_6.setText(field4);
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});
		
		change5.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String tmp = JOptionPane.showInputDialog(null, "Change text to ", field5);
				if(tmp != null) {
					field5 = tmp;
					lblNewLabel_24.setText(field5);
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});
		
		browseImageFolder1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jfc = new JFileChooser();
				jfc.setCurrentDirectory(new java.io.File("user.home"));
				jfc.setDialogTitle("Please Select Your " + field1);
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (jfc.showOpenDialog(browseImageFolder1) == JFileChooser.APPROVE_OPTION) {
					imageFolder1Location.setText(jfc.getSelectedFile().getAbsolutePath().replace("\\", "/"));
				}
			}
			
		});
		
		browseImageFolder2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jfc = new JFileChooser();
				jfc.setCurrentDirectory(new java.io.File("user.home"));
				jfc.setDialogTitle("Please Select Your " + field2);
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (jfc.showOpenDialog(browseImageFolder2) == JFileChooser.APPROVE_OPTION) {
					imageFolder2.setText(jfc.getSelectedFile().getAbsolutePath().replace("\\", "/"));
				}
			}
			
		});
		
		browsePlayerNames.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jfc = new JFileChooser();
				jfc.setCurrentDirectory(new java.io.File("user.home"));
				jfc.setDialogTitle("Please Select Your Saved " + field3);
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (jfc.showOpenDialog(browsePlayerNames) == JFileChooser.APPROVE_OPTION) {
					textField.setText(jfc.getSelectedFile().getAbsolutePath().replace("\\", "/"));
				}
			}
			
		});
		
		browseCommentatorNames.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jfc = new JFileChooser();
				jfc.setCurrentDirectory(new java.io.File("user.home"));
				jfc.setDialogTitle("Please Select Your Saved " + field4);
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (jfc.showOpenDialog(browseCommentatorNames) == JFileChooser.APPROVE_OPTION) {
					commentators.setText(jfc.getSelectedFile().getAbsolutePath().replace("\\", "/"));
				}
			}
			
		});
		
		browseAccess.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jfc = new JFileChooser();
				jfc.setCurrentDirectory(new java.io.File("user.home"));
				jfc.setDialogTitle("Please Select Your Saved " + field5);
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (jfc.showOpenDialog(browseAccess) == JFileChooser.APPROVE_OPTION) {
					textFileAccess.setText(jfc.getSelectedFile().getAbsolutePath().replace("\\", "/"));
				}
			}
			
		});
		
		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// image 1 folder is characters
				// image 2 folder is sponsors
				image1Folder = new File(imageFolder1Location.getText());
				image2Folder = new File(imageFolder2.getText());
				playerFolder = new File(textField.getText());
				commentatorFolder = new File(commentators.getText());
				textFolder = new File(textFileAccess.getText());
				
				image1Files = image1Folder.listFiles();
				image2Files = image2Folder.listFiles();
				playerFiles = playerFolder.listFiles();
				commentatorFiles = commentatorFolder.listFiles();
				textFiles = textFolder.listFiles();
				
				
				
				if(!textField.getText().equals(""))
					TournamentEnlisterTab.updatePlayerPath(textField.getText());
				
				if(!commentators.getText().equals(""))
					TournamentEnlisterTab.updateCommentatorPath(commentators.getText());
				
				if(!imageFolder2.getText().equals(""))
					TournamentEnlisterTab.updateSponsorPath(imageFolder2.getText());
				
				// loads everything for the combobox
				StreamUpdaterTab.loadEverything(textFolder, playerFiles, commentatorFiles, image1Files, image2Files);
				
			}
			
		});
		
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveConfiguration();				
			}
		});
		
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				File f = new File(findFile(saveConfigName));
				f.delete();
				JOptionPane.showMessageDialog(null, "Successfully deleted the saved configuration from your computer.");
			}
			
		});
		
	}
	
	public static File[] getPlayerFiles() {
		return playerFiles = playerFolder.listFiles();
	}
	
	public static File[] getCommentatorFiles() {
		return commentatorFiles = commentatorFolder.listFiles();
	}
	
	public static File getImageFolder1() {
		return image1Folder;
	}
	
	public static File getImageFolder2() {
		return image2Folder;
	}
	
	public static File getPlayerFolder() {
		return playerFolder;
	}
	
	public static File getCommentatorFolder() {
		return commentatorFolder;
	}
	
	public static File getTextFolder() {
		return textFolder;
	}
	
	private void saveConfiguration() {
		
		compileSave();
	}
	
	private void compileSave() {
		saveStuff = new ArrayList<String>();
		saveStuff.add(imageFolder1Location.getText());
		saveStuff.add(imageFolder2.getText());
		saveStuff.add(textField.getText());
		saveStuff.add(commentators.getText());
		saveStuff.add(textFileAccess.getText());
		saveStuff.add(field1);
		saveStuff.add(field2);
		saveStuff.add(field3);
		saveStuff.add(field4);
		saveStuff.add(field5);
		FilesTabSave fts = new FilesTabSave(saveStuff);
	}
	
	public void setStuff(FilesTabSave fts) {
		imageFolder1Location.setText(fts.getList().get(0));
		imageFolder2.setText(fts.getList().get(1));
		textField.setText(fts.getList().get(2));
		commentators.setText(fts.getList().get(3));
		textFileAccess.setText(fts.getList().get(4));
		field1 = fts.getList().get(5);
		field2 = fts.getList().get(6);
		field3 = fts.getList().get(7);
		field4 = fts.getList().get(8);
		field5 = fts.getList().get(9);
		images.setText(field1);
		sponsors.setText(field2);
		lblNewLabel_7.setText(field3);
		lblNewLabel_6.setText(field4);
		lblNewLabel_24.setText(field5);
	}
	
	private static String findFile(String name) {
		String user = System.getProperty("user.name");
		if(System.getProperty("os.name").contains("Windows")) {
			return "C:/Users/"+user+"/AppData/Roaming/"+ name;
		} else
			return System.getProperty("user.home") + "/" + name;
	}
	
}
