package streamupdater.gui.components;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultCaret;

public class AboutMeTab extends JPanel {
	
	private JLabel youtube;
	private JLabel twitter;
	private JLabel twitch;
	private JLabel donate;
	private JTextArea notes;
	
	public AboutMeTab() {
		
		setLayout(null);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(10, 11, 539, 690);
		add(panel_6);
		panel_6.setLayout(null);
		
		JLabel lblNewLabel_26 = new JLabel("About The Developer");
		lblNewLabel_26.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_26.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 24));
		lblNewLabel_26.setBounds(10, 11, 519, 19);
		panel_6.add(lblNewLabel_26);
		
		youtube = new JLabel("");
		youtube.setHorizontalAlignment(SwingConstants.CENTER);
		youtube.setBounds(10, 41, 519, 47);
		panel_6.add(youtube);
		
		twitter = new JLabel("");
		twitter.setHorizontalAlignment(SwingConstants.CENTER);
		twitter.setBounds(10, 99, 519, 47);
		panel_6.add(twitter);
		
		twitch = new JLabel("");
		twitch.setHorizontalAlignment(SwingConstants.CENTER);
		twitch.setBounds(10, 157, 519, 47);
		panel_6.add(twitch);
		
		JLabel lblNewLabel_27 = new JLabel("How to use notes, etc...");
		lblNewLabel_27.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblNewLabel_27.setBounds(10, 273, 146, 14);
		panel_6.add(lblNewLabel_27);
		
		donate = new JLabel("");
		donate.setHorizontalAlignment(SwingConstants.CENTER);
		donate.setBounds(10, 215, 519, 47);
		panel_6.add(donate);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 295, 519, 384);
		panel_6.add(scrollPane);
		
		notes = new JTextArea();
		notes.setBounds(10, 298, 519, 381);
		notes.setEditable(true);
		notes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		DefaultCaret caret = (DefaultCaret)notes.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		notes.setWrapStyleWord(true);
		addNotes();
		scrollPane.setViewportView(notes);
		
		youtube.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					java.awt.Desktop.getDesktop().browse(new URI("https://www.youtube.com/user/Abszol"));
				} catch (IOException | URISyntaxException e) {
					JOptionPane.showMessageDialog(null, "Strange... Browser wasn't found? Well go here https://www.youtube.com/user/Abszol");
					e.printStackTrace();
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
		
		twitter.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					java.awt.Desktop.getDesktop().browse(new URI("https://twitter.com/Abszol"));
				} catch (IOException | URISyntaxException e) {
					JOptionPane.showMessageDialog(null, "Strange... Browser wasn't found? Well go here https://twitter.com/Abszol");
					e.printStackTrace();
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
		
		twitch.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					java.awt.Desktop.getDesktop().browse(new URI("https://www.twitch.tv/theabszol"));
				} catch (IOException | URISyntaxException e) {
					JOptionPane.showMessageDialog(null, "Strange... Browser wasn't found? Well go here https://www.twitch.tv/theabszol");
					e.printStackTrace();
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
		
		donate.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					java.awt.Desktop.getDesktop().browse(new URI("https://www.paypal.com/us/cgi-bin/webscr?cmd=_flow&SESSION=ru_H9xhZMN900HI--3tocpsyIZDzq4uOiyLCzFNASSeeFAnpaIux6aym3V0&dispatch=5885d80a13c0db1f8e263663d3faee8dcce3e160f5b9538489e17951d2c62172"));
				} catch (IOException | URISyntaxException e) {
					JOptionPane.showMessageDialog(null, "Strange... Browser wasn't found? This link is too long.");
					e.printStackTrace();
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
		
		setInformation();
		
		notes.setCaretPosition(0);
		
	}
	
	private void addNotes() {
		
		try {
			 
			BufferedReader reader = new BufferedReader(new InputStreamReader(AboutMeTab.class.getResourceAsStream("/Text/Notes.txt")));
			String line = null;
			while((line = reader.readLine()) != null) {
				
				notes.append(line + "\n");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void setInformation() {

		try {
			
			BufferedImage youtubepic = ImageIO.read(AboutMeTab.class.getResource("/Images/youtube.png"));
			BufferedImage twitterpic = ImageIO.read(AboutMeTab.class.getResource("/Images/twitter.jpg"));
			BufferedImage twitchpic = ImageIO.read(AboutMeTab.class.getResource("/Images/twitch.jpg"));
			BufferedImage donatepic = ImageIO.read(AboutMeTab.class.getResource("/Images/donate.png"));
			
			youtube.setIcon(new ImageIcon(youtubepic));
			twitter.setIcon(new ImageIcon(twitterpic));
			twitch.setIcon(new ImageIcon(twitchpic));
			donate.setIcon(new ImageIcon(donatepic));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
