package streamupdater.gui.components;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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

import streamupdater.gui.components.render.RenderList;
import streamupdater.gui.components.render.RenderObject;
import streamupdater.gui.components.render.VideoHandler;
import streamupdater.gui.main.GUI;
import streamupdater.util.Commands;
import streamupdater.util.RenderSave;
import streamupdater.util.SavingFileConfiguration;

@SuppressWarnings("serial")
public class RenderTab extends JPanel {

	private JFileChooser jfc;
	
	private JButton loadRender;
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
	
	private static String mediaFolderLocation = "";
	
	private VideoHandler video;
	private ThumbnailEditor te;
	private RenderList rl;
	
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
		
		loadRender = new JButton("Load Saved Object");
		loadRender.setToolTipText("Load Saved Render File (Saves To Desktop or /Home/ BSD, Mac, Linux");
		loadRender.setFont(new Font("Dialog", Font.BOLD, 14));
		loadRender.setBounds(86, 65, 361, 40);
		pan.add(loadRender);
		
		renderAfterStream = new JButton("Render Live During Stream");
		renderAfterStream.setToolTipText("Render during the stream, is your computer strong enough?");
		renderAfterStream.setFont(new Font("Dialog", Font.BOLD, 14));
		renderAfterStream.setBounds(86, 125, 361, 40);
		pan.add(renderAfterStream);
		
		loadRender.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(FilesTab.getMediaFolder().equals("")) {
					JOptionPane.showMessageDialog(null, "Please Declare All The Paths In The Files Tab!"); GUI.switchTo(3);
					return;
				}
				// make the layout like the one after launch. Get ro stream url to here
				RenderSave rs = new RenderSave();
				rs.load();
				if(rs.getStreamURL() == null) return;
				ro = new RenderObject(rs.getStreamURL());
				ro.setStartingPositions(rs.getStartingPositions());
				ro.setDurations(rs.getDurations());
				ro.setFileNames(rs.getFileNames());
				ro.setThumbnails(rs.getImages());
				ro.setImageFile(rs.getImageFileNames());
				
				pan.remove(loadRender);
				pan.remove(renderAfterStream);
				save.setText("Save Object");
				save.setToolTipText("Save the current object");
				save.setFont(new Font("Dialog", Font.BOLD, 14));
				save.setBounds(86, 125, 361, 40);
				pan.add(save);
				render.setText("Open Render List");
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
				//jcb.setEnabled(false);
				jcb.setToolTipText("Enable to rename your video output to the stream");
				jcb.setBounds(86, 245, 20, 20);
				pan.add(jcb);
				
				videoName = new JTextField("MAINTITLE - PLAYERONENAME(PLAYERONECHAR) vs PLAYERTWONAME(PLAYERTWOCHAR) - CURRENTROUND");
				videoName.setToolTipText(buildCommandList());
				videoName.setFont(new Font("Dialog", Font.PLAIN, 14));
				videoName.setBounds(124, 245, 323, 20);
				videoName.setHorizontalAlignment(SwingConstants.CENTER);
				videoName.setColumns(10);
				videoName.setEnabled(true);
				pan.add(videoName);
				
				JButton thumbnailButton = new JButton("Thumbnail Editor");
				thumbnailButton.setToolTipText("Build your own custom thumbnail");
				thumbnailButton.setFont(new Font("Dialog", Font.BOLD, 14));
				thumbnailButton.setBounds(86, 285, 361, 40);
				pan.add(thumbnailButton);

				JButton changePath = new JButton("Change Stream URL");
				changePath.setToolTipText("Change the path to your file a.k.a file has moved");
				changePath.setFont(new Font("Dialog", Font.BOLD, 14));
				changePath.setBounds(86, 345, 361, 40);
				pan.add(changePath);
				
				capture.setToolTipText("Capturing at " + ro.getStreamURL());
				streamURL = ro.getStreamURL();
				video = new VideoHandler();
				video.setVideoInput(ro.getStreamURL());
				
				repaint();
				
				changePath.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
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
								while(true) {
									if(!textField.getText().equals("")) {
										if(textField.getText().contains(".flv")) {
											streamURL = textField.getText();
											break;
										} else {
											JOptionPane.showMessageDialog(null, "Invalid extension, please select a .flv selection");
											return;
										}
									} else {
										JOptionPane.showMessageDialog(null, "Please fill out the box");
										return;
									}
								}
								frame.dispose();
								ro.setStreamURL(streamURL);
								video.setVideoInput(streamURL);
								capture.setToolTipText("Capturing at " + ro.getStreamURL());
							}
							
						});

						frame.setResizable(false);
						frame.setVisible(true);
						
					}
					
				});
				
				thumbnailButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(te == null) {
							te = new ThumbnailEditor();
						} else
							te.getFrame().setVisible(true);
					}
					
				});
				
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
				
			}
			
		});
		
		renderAfterStream.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(FilesTab.getMediaFolder().equals("")) {
					JOptionPane.showMessageDialog(null, "Please Declare All The Paths In The Files Tab!"); GUI.switchTo(3);
					return;
				}
				pan.remove(loadRender);
				pan.remove(renderAfterStream);
				save.setText("Save Object");
				save.setToolTipText("Save the current object");
				save.setFont(new Font("Dialog", Font.BOLD, 14));
				save.setBounds(86, 125, 361, 40);
				pan.add(save);
				render.setText("Open Render List");
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
				jcb.setSelected(true);
				//jcb.setEnabled(false); // temporary, glitch is causing issues. Issues seem to be special characters, thanks LGBT | Che$$...
				jcb.setToolTipText("Enable to rename your video output to the stream");
				jcb.setBounds(86, 245, 20, 20);
				pan.add(jcb);
				
				videoName = new JTextField("MAINTITLE - PLAYERONENAME(PLAYERONECHAR) vs PLAYERTWONAME(PLAYERTWOCHAR) - CURRENTROUND");
				videoName.setToolTipText(buildCommandList());
				videoName.setFont(new Font("Dialog", Font.PLAIN, 14));
				videoName.setBounds(124, 245, 323, 20);
				videoName.setHorizontalAlignment(SwingConstants.CENTER);
				videoName.setColumns(10);
				videoName.setEnabled(true);
				pan.add(videoName);
				
				JButton thumbnailButton = new JButton("Thumbnail Editor");
				thumbnailButton.setToolTipText("Build your own custom thumbnail");
				thumbnailButton.setFont(new Font("Dialog", Font.BOLD, 14));
				thumbnailButton.setBounds(86, 285, 361, 40);
				pan.add(thumbnailButton);
				
				JButton changePath = new JButton("Change Stream URL");
				changePath.setToolTipText("Change the path to your file a.k.a file has moved");
				changePath.setFont(new Font("Dialog", Font.BOLD, 14));
				changePath.setBounds(86, 345, 361, 40);
				pan.add(changePath);
				
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

				changePath.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JFrame frame = new JFrame("Stream URL");
						frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
								while(true) {
									if(!textField.getText().equals("")) {
										if(textField.getText().contains(".flv")) {
											streamURL = textField.getText();
											break;
										} else {
											JOptionPane.showMessageDialog(null, "Invalid extension, please select a .flv selection");
											return;
										}
									} else {
										JOptionPane.showMessageDialog(null, "Please fill out the box");
										return;
									}
								}
								frame.dispose();
								ro.setStreamURL(streamURL);
								video.setVideoInput(streamURL);
								capture.setToolTipText("Capturing at " + ro.getStreamURL());
							}
							
						});

						frame.setResizable(false);
						frame.setVisible(true);
						
					}
					
				});

				thumbnailButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if(te == null) {
							te = new ThumbnailEditor();
						} else
							te.getFrame().setVisible(true);
					}
					
				});
				
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
						while(true) {
							if(!textField.getText().equals("")) {
								if(textField.getText().contains(".flv")) {
									streamURL = textField.getText();
									break;
								} else {
									JOptionPane.showMessageDialog(null, "Invalid extension, please select a .flv selection");
									return;
								}
							} else {
								JOptionPane.showMessageDialog(null, "Please fill out the box");
								return;
							}
						}
						frame.dispose();
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
						/* testing
						String location = "C:\\Users\\"+user+"\\Desktop\\";
						if(!FilesTab.getMediaFolder().equals("")) {
							location = FilesTab.getMediaFolder().replaceAll("/", "\\\\") + "\\"; 
						}
						*/
						String fileName = videoName.getText();
						if(!jcb.isSelected()) {
							ro.getFileNames().add((ro.getFileNames().size() + 1) + ".mp4");
							ro.getImageFileNames().add((ro.getImageFileNames().size() + 1) + ".png");
						} else {
							System.out.println("Generated Filename " + Commands.interpretString(fileName) + ".mp4");
							ro.getFileNames().add(Commands.interpretString(fileName) + ".mp4");
							ro.getImageFileNames().add(Commands.interpretString(fileName) + ".png");
						}
						if(te == null) {
							ro.getImages().add(new BufferedImage(1280, 720, BufferedImage.TYPE_INT_RGB));
						} else {
							ThumbnailEditor.deselect();
							ro.getImages().add(te.generateThumbnail());
						}
							// flush everything out
						playerOneCharacters.clear();
						playerTwoCharacters.clear();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			
		});
		
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
					new Thread(new Runnable() {
						public void run() {
							if(ro != null) {
								RenderSave rs = new RenderSave();
								rs.save(ro.getStreamURL(), ro.getStartingPositions(), ro.getDurations(), ro.getFileNames(), ro.getImages(), ro.getImageFileNames());
							} else {
								JOptionPane.showMessageDialog(null, "Render Object Broken? I'll rebuild it");
								ro = new RenderObject(streamURL);
								RenderSave rs = new RenderSave();
								rs.save(ro.getStreamURL(), ro.getStartingPositions(), ro.getDurations(), ro.getFileNames(), ro.getImages(), ro.getImageFileNames());
						}
					}
					}).start();
			}
			
		});
		
		render.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// open render list
				rl = new RenderList(video, ro);
				
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
