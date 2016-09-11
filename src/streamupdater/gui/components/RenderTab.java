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
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import streamupdater.gui.components.render.RenderObject;
import streamupdater.gui.components.render.RenderingEngine;
import streamupdater.gui.components.render.VideoHandler;
import streamupdater.util.Commands;
import streamupdater.util.SavingFileConfiguration;

@SuppressWarnings("serial")
public class RenderTab extends JPanel {

	private JFileChooser jfc;
	
	private JButton renderNow;
	private JButton renderAfterStream;
	
	private JButton capture;
	private JButton save;
	private JButton render;
	private boolean capturing = false;
	private static String streamURL = null;
	private long offset = 0;
	private long duration = 0;
	
	// title tampering
	private JTextField videoName;
	private JCheckBox jcb;
	private static ArrayList<String> playerOneCharacters = new ArrayList<String>();
	private static ArrayList<String> playerTwoCharacters = new ArrayList<String>();
	
	private VideoHandler video;
	
	private boolean renderingNow = false;
	
	private RenderObject ro;
	
	public RenderTab() {
	
		setLayout(null);
		
		JPanel pan = new JPanel();
		pan.setBounds(10, 11, 539, 690);
		add(pan);
		pan.setLayout(null);
		
		JLabel mainText = new JLabel("Rendering Tab [Select Your Choice]");
		mainText.setHorizontalAlignment(SwingConstants.CENTER);
		mainText.setFont(new Font("Dialog", Font.BOLD, 20));
		mainText.setBounds(10, 11, 519, 26);
		pan.add(mainText);
		
		renderNow = new JButton("Render Now");
		renderNow.setToolTipText("Render now, lets forget the stream");
		renderNow.setFont(new Font("Dialog", Font.BOLD, 14));
		renderNow.setBounds(86, 65, 361, 40);
		renderNow.setEnabled(false); 									// temp
		pan.add(renderNow);
		
		renderAfterStream = new JButton("Render Live During Stream");
		renderAfterStream.setToolTipText("Render during the stream, is your computer strong enough?");
		renderAfterStream.setFont(new Font("Dialog", Font.BOLD, 14));
		renderAfterStream.setBounds(86, 125, 361, 40);
		pan.add(renderAfterStream);
		
		renderNow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				pan.remove(renderAfterStream);
				pan.repaint();
				if(renderingNow) {
					renderingNow = false;
					pan.add(renderAfterStream);
					renderNow.setText("Render Now");
					renderNow.setToolTipText("Render now, lets forget the stream");
				} else {
					renderingNow = true;
					renderNow.setText("Stop Rendering");
					renderNow.setToolTipText("Stop Rendering");
					int tmp = RenderingEngine.renderProject(video);
					if(tmp < 0) {
						JOptionPane.showMessageDialog(null, "ERROR [" + tmp + "]! Corrupted Object/File?");
						renderingNow = false;
						pan.add(renderAfterStream);
						renderNow.setText("Render Now");
						renderNow.setToolTipText("Render now, lets forget the stream");
					}
				}
				repaint();
			}
			
		});
		
		renderAfterStream.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				pan.remove(renderNow);
				pan.remove(renderAfterStream);
				save.setText("Save Object");
				save.setToolTipText("Save the current object");
				save.setFont(new Font("Dialog", Font.BOLD, 14));
				save.setBounds(86, 125, 361, 40);
				pan.add(save);
				render.setText("Render [Stream Has Completed]");
				render.setFont(new Font("Dialog", Font.BOLD, 14));
				render.setBounds(86, 185, 361, 40);
				pan.add(render);
				capture.setText("Start Capture");
				capture.setToolTipText("Render during the stream, is your computer strong enough?");
				capture.setFont(new Font("Dialog", Font.BOLD, 14));
				capture.setBounds(86, 65, 361, 40);
				pan.add(capture);
				
				// render stoof
				jcb = new JCheckBox();
				jcb.setSelected(false);
				jcb.setToolTipText("Enable to rename your video output to the stream");
				jcb.setBounds(86, 245, 20, 20);
				pan.add(jcb);
				
				videoName = new JTextField("VIDEOTITLE HERE. REPLACE WITH W/E");
				videoName.setToolTipText(buildCommandList());
				videoName.setFont(new Font("Dialog", Font.PLAIN, 14));
				videoName.setBounds(124, 245, 323, 20);
				videoName.setHorizontalAlignment(SwingConstants.CENTER);
				videoName.setColumns(10);
				videoName.setEnabled(false);
				pan.add(videoName);
				
				repaint();
				
				JFrame frame = new JFrame("Stream URL");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setBounds(100, 100, 450, 201);
				JPanel contentPane = new JPanel();
				contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				frame.setContentPane(contentPane);
				contentPane.setLayout(null);
				
				JPanel panel = new JPanel();
				panel.setBounds(10, 11, 414, 140);
				contentPane.add(panel);
				panel.setLayout(null);
				
				JLabel lblNewLabel = new JLabel("Streaming File URL");
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 14));
				lblNewLabel.setBounds(10, 11, 394, 30);
				panel.add(lblNewLabel);
				
				JTextField textField = new JTextField();
				textField.setFont(new Font("Dialog", Font.PLAIN, 12));
				textField.setBounds(10, 52, 295, 20);
				panel.add(textField);
				textField.setColumns(10);
				
				JButton browse = new JButton("Browse");
				browse.setFont(new Font("Dialog", Font.PLAIN, 12));
				browse.setBounds(315, 51, 89, 23);
				panel.add(browse);
				
				JButton launch = new JButton("Launch");
				launch.setFont(new Font("Dialog", Font.PLAIN, 12));
				launch.setBounds(315, 85, 89, 23);
				panel.add(launch);
				
				jcb.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(jcb.isSelected()) {
							videoName.setEnabled(true);
						} else {
							videoName.setEnabled(false);
						}
					}
					
				});
				
				browse.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						jfc = new JFileChooser();
						jfc.setCurrentDirectory(new java.io.File("user.home"));
						jfc.setDialogTitle("Select your streaming file");
						jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
						if (jfc.showOpenDialog(browse) == JFileChooser.APPROVE_OPTION) {
							textField.setText(jfc.getSelectedFile().getAbsolutePath().replace("\\", "\\\\"));
						}
					}
					
				});
				
				launch.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						frame.dispose();
						streamURL = textField.getText();
						ro = new RenderObject(textField.getText());
						capture.setToolTipText("Capturing at " + textField.getText());
						video = new VideoHandler();
						video.setVideoInput(streamURL);
					}
					
				});
				
				frame.setResizable(false);
				frame.setVisible(true);
			}
				
			});
		

		save = new JButton();
		render = new JButton();
		capture = new JButton();
		
		capture.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(!capturing) {
						capturing = true;
						capture.setText("Stop Capture");
						offset = video.getDuration();
						appendCharacterSystem();
					} else {
						capturing = false;
						capture.setText("Capture");
						duration = video.getDuration() - offset;
						ro.getDurations().add((int) duration);
						ro.getStartingPositions().add((int) offset);
						offset = 0;
						duration = 0;
						String user = System.getProperty("user.name");
						appendCharacterSystem();
						if(!jcb.isSelected()) {
							ro.getFileNames().add("C:\\Users\\"+user+"\\Desktop\\" + (ro.getFileNames().size() + 1) + ".mp4");
						} else {
							ro.getFileNames().add("C:\\Users\\"+user+"\\Desktop\\" + Commands.interpretString(videoName.getText()) +".mp4");
						}
						// flush everything out
						System.out.println("SIZE: " + playerOneCharacters.size());
						playerOneCharacters.clear();
						playerTwoCharacters.clear();
					}
				} catch (Exception e2) {}
			}
			
		});
		
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(ro != null) {
					ro.save(ro);
				} else {
					JOptionPane.showMessageDialog(null, "Render Object Broken? I'll rebuild it");
					ro = new RenderObject(streamURL);
					ro.save(ro);
				}
			}
			
		});
		
		render.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				for(int i = 0; i < ro.getDurations().size(); i++) {
					// garbonzo
					RenderingEngine re = new RenderingEngine();
					re.setObject(ro);
					re.renderProject(video, i);
					System.out.println("Rendering");
				}
			}
			
		});
		
	}
	
	private String buildCommandList() {
		
		String n = "<html>";
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(RenderTab.class.getResourceAsStream("/Text/awesomecommands.txt")));
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
	
	public static boolean isStreaming() {
		if(streamURL == null || streamURL.equals("")) {
			return false;
		} else
			return true;
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
		
}
