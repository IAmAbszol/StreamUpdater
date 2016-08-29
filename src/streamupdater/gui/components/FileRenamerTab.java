package streamupdater.gui.components;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import streamupdater.util.Commands;
import streamupdater.util.SavingFileConfiguration;

public class FileRenamerTab extends JPanel {

	private static JTextField directoryMonitoringLocation;
	private JFileChooser jfc;
	
	private JButton monitor;
	private JButton stopMonitor;
	private JTextField videoTitle; 
	
	private static ArrayList<String> playerOneCharacters = new ArrayList<String>();
	private static ArrayList<String> playerTwoCharacters = new ArrayList<String>();
	
	private ArrayList<File> directoryFiles;
	
	private static boolean runThread;
	private Thread monitoringThread;
	
	private String dir;
	
	public FileRenamerTab() {
		
		directoryFiles = new ArrayList<File>();
		
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 539, 690);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Directory Monitoring & Renaming");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 11, 519, 20);
		panel.add(lblNewLabel);
		
		JLabel streamingLabel = new JLabel("Please Select A Directory To Monitor Newly Created Video Files");
		streamingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		streamingLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		streamingLabel.setBounds(10, 50, 519, 20);
		streamingLabel.setToolTipText("Default location for VLC recorded files are in Videos");
		panel.add(streamingLabel);
		
		directoryMonitoringLocation = new JTextField();
		directoryMonitoringLocation.setFont(new Font("Dialog", Font.PLAIN, 14));
		directoryMonitoringLocation.setBounds(100, 90, 224, 20);
		panel.add(directoryMonitoringLocation);
		directoryMonitoringLocation.setToolTipText("Default location for VLC recorded files are in Videos");
		directoryMonitoringLocation.setColumns(10);
		
		JButton browse = new JButton("Browse");
		browse.setFont(new Font("Dialog", Font.PLAIN, 14));
		browse.setBounds(344, 90, 89, 23);
		browse.setToolTipText("Select a directory");
		panel.add(browse);
		
		monitor = new JButton("Monitor");
		monitor.setFont(new Font("Dialog", Font.PLAIN, 14));
		monitor.setBounds(160, 140, 200, 23);
		panel.add(monitor);
		
		JLabel videoLabel = new JLabel("Video Renaming");
		videoLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		videoLabel.setBounds(210, 200, 361, 20);
		panel.add(videoLabel);
		
		videoTitle = new JTextField();
		videoTitle.setFont(new Font("Dialog", Font.PLAIN, 14));
		videoTitle.setBounds(89, 240, 361, 20);
		panel.add(videoTitle);
		videoTitle.setToolTipText(buildCommandList());
		videoTitle.setText("VIDEOTITLE HERE. REPLACE WITH W/E");
		videoTitle.setColumns(10);
		
		stopMonitor = new JButton("Stop Monitoring");
		stopMonitor.setFont(new Font("Dialog", Font.PLAIN, 14));
		stopMonitor.setBounds(160, 280, 200, 23);
		panel.add(stopMonitor);
		
		stopMonitor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				runThread = false;
				monitor.setText("Monitor");
			}
		});
		
		monitor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!directoryMonitoringLocation.getText().equals("")) {
					monitor.setText("Update To New Directory");
					monitor.setToolTipText("Click this if you changed the directory you are watching");
					
					appendCharacterSystem();
					
					// invoke thread
					if(!runThread) {

						runThread = true;
						
						monitoringThread = new Thread(new Runnable() {

							@Override
							public void run() {
								monitoringSystem();
							}
							
						});
						
						monitoringThread.start();
						
					} else {
						
						appendCharacterSystem();
						directoryFiles.clear();
						dir = directoryMonitoringLocation.getText();
						
					}
					
				}
			}
			
		});
		
		browse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				jfc = new JFileChooser();
				jfc.setCurrentDirectory(new java.io.File("user.home"));
				jfc.setDialogTitle("Please Select The Directory For Monitoring");
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (jfc.showOpenDialog(browse) == JFileChooser.APPROVE_OPTION) {
					directoryMonitoringLocation.setText(jfc.getSelectedFile().getAbsolutePath().replace("\\", "/"));
				}
			}
			
		});
		
	}
	
	public static void appendCharacterSystem() {
		
		String fileName = SavingFileConfiguration.getFiles()[14];
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(FilesTab.getTextFolder() + "/" + fileName)));
			String line = null;
			while((line = reader.readLine()) != null) {
				getPlayerOneCharacters().add(line);
			}
			reader.close();
			fileName = SavingFileConfiguration.getFiles()[15];
			reader = new BufferedReader(new FileReader(new File(FilesTab.getTextFolder() + "/" + fileName)));
			line = null;
			while((line = reader.readLine()) != null) {
				getPlayerTwoCharacters().add(line);
			}
			reader.close();
		} catch (Exception e) {}
		
	}
	
	private void monitoringSystem() {
		
		dir = directoryMonitoringLocation.getText();
		
		// load files
		File f = new File(dir);
		File[] list = f.listFiles();
		
		// load into array for monitoring
		for(int i = 0; i < list.length; i++) {
			directoryFiles.add(list[i]);
		}
		
		while(runThread) {
			
			// load files
			File tmpF = new File(dir);
			File[] tmpList = tmpF.listFiles();
			
			if(tmpList.length > directoryFiles.size()) {
				
				// build a temp array list and flood it with the list
				ArrayList<File> tmpFileList = new ArrayList<File>();
				for(int i = 0; i < tmpList.length; i++) {
					tmpFileList.add(tmpList[i]);
				}

				// remove all duplicates, anything that matches. We are left with the sole file that was recently added
				tmpFileList.removeAll(directoryFiles);
				
				// now to rename and add that file to the directory list
				if(isVideo(tmpFileList.get(0).getName())) {
					File rename = tmpFileList.get(0);
					File newFile = new File(directoryMonitoringLocation.getText() + "/" + Commands.interpretString(videoTitle.getText()) + ".mp4");
					System.out.println(newFile.getName());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(rename.renameTo(newFile)) {
						System.out.println("Success");
					
						// now to clean up and add the new file
						tmpF = new File(dir);
						tmpList = tmpF.listFiles();
						
						directoryFiles.clear();
						for(int i = 0; i < tmpList.length; i++) {
							directoryFiles.add(tmpList[i]);
						}
						
						tmpFileList.clear();
						
						// clear the characters, gotta get ready for the next set
						playerOneCharacters.clear();
						playerTwoCharacters.clear();
						
					} else {
						
						System.out.println("Failed");
						tmpFileList.clear();
						
					}
					
				} else {
					
					System.out.println("Non-important file, cleaning up");
					
					// now to clean up and add the new file
					tmpF = new File(dir);
					tmpList = tmpF.listFiles();
					
					directoryFiles.clear();
					for(int i = 0; i < tmpList.length; i++) {
						directoryFiles.add(tmpList[i]);
					}
					
					tmpFileList.clear();
					
				}
				
			}
			
			// something got removed, updating
			if(tmpList.length < directoryFiles.size()) {
				
				directoryFiles.clear();
				for(int i = 0; i < tmpList.length; i++) {
					directoryFiles.add(tmpList[i]);
				}
				
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		System.out.println("Monitoring has seized");
		
	}
	
	private boolean isVideo(String n) {
		ArrayList<String> videoTypes = new ArrayList<String>();
		try {
			// flood with file extensions
			BufferedReader reader = new BufferedReader(new InputStreamReader(FileRenamerTab.class.getResourceAsStream("/Text/videofiles")));
			String line = null;
			while((line = reader.readLine()) != null) {
				videoTypes.add(line);
			}
			reader.close();
			
			for(int i = 0; i < videoTypes.size(); i++) {
				if(n.contains(videoTypes.get(i))) return true;
			}
		} catch (Exception e) {
			
		}
		return false;
	}
	
	private String buildCommandList() {
		
		String n = "<html>";
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(FileRenamerTab.class.getResourceAsStream("/Text/awesomecommands.txt")));
		String line = null;
		try {
			while((line = reader.readLine()) != null) {
				
				n = n + line + "<br>";
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return n + "</html>";
		
	}
	
	public static boolean isRunningMonitor() {
		return runThread;
	}
	
	public static ArrayList<String> getPlayerOneCharacters() {
		return playerOneCharacters;
	}
	
	public static ArrayList<String> getPlayerTwoCharacters() {
		return playerTwoCharacters;
	}
	
	public static void setPlayerOneChars(ArrayList<String> tm) {
		playerOneCharacters.clear();
		for(int i = 0; i < tm.size(); i++)
			playerOneCharacters.add(tm.get(i));
	}
	
	public static void setPlayerTwoChars(ArrayList<String> tm) {
		playerTwoCharacters.clear();
		for(int i = 0; i < tm.size(); i++)
			playerTwoCharacters.add(tm.get(i));
	}
	
	public static boolean isStreamEmpty() {
		if(directoryMonitoringLocation.getText().equals(""))
				return true;
			else
				return false;
	}
	
}
