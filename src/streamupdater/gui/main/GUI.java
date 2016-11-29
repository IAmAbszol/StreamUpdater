package streamupdater.gui.main;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import streamupdater.gui.components.AboutMeTab;
import streamupdater.gui.components.BracketTab;
import streamupdater.gui.components.FileRenamerTab;
import streamupdater.gui.components.FilesTab;
import streamupdater.gui.components.RenderTab;
import streamupdater.gui.components.StreamUpdaterTab;
import streamupdater.gui.components.TournamentEnlisterTab;
import streamupdater.util.FilesTabSave;
import streamupdater.util.ScanForFFMpeg;

public class GUI extends JFrame {

	private TournamentEnlisterTab tet;
	private StreamUpdaterTab sut;
	private RenderTab rt;
	private FileRenamerTab frt;
	private FilesTab ft;
	private BracketTab bt;
	private AboutMeTab at;
	
	private JPanel contentPane;
	private static JTabbedPane tabbedPane;
	
	private int boot = 0;
	
	public static ArrayList<String> rounds;
	
	/*
	 * DEVELOPERS CHANGE THE VALUES TO INCREASE LETTERS, ROUNDS, ETC!!!
	 */

	private static int numberOfPools = 10;
	private static int numberOfRounds = 20;
	private static boolean simple = true;
	
	private static String[] arguments;
	
	public GUI() {
		
		try {
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		} catch (Exception e) { };

		constructRounds();
		
		setTitle("Stream Updater Version 3.9.5 by Kyle Darling");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 600, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPane = new JTabbedPane(SwingConstants.TOP);
		tabbedPane.setBounds(10, 11, 564, 740);
		contentPane.add(tabbedPane);
		
		// tournament enlisting creation
		tet = new TournamentEnlisterTab();
		tabbedPane.addTab("Tournament Enlisting Tab", tet);
		
		// stream updating tab
		sut = new StreamUpdaterTab();
		tabbedPane.addTab("Stream Updater Tab", sut);
		
		// rendering tab
		rt = new RenderTab();
		tabbedPane.addTab("Rendering Tab", rt);
		
		//bt = new BracketTab();
		//tabbedPane.addTab("Bracket Tab", bt);
		
		// file renamer tab
		//frt = new FileRenamerTab();
		//tabbedPane.addTab("File Renamer Tab", frt);
		
		// files
		ft = new FilesTab();
		if(FilesTabSave.load() != null) {
			boot = JOptionPane.showConfirmDialog(null, "Previous Saved Configuration Detected! Boot Off That?");
			if(boot == JOptionPane.YES_OPTION) {
				ft.setStuff(FilesTabSave.load());
			}
		}
		tabbedPane.addTab("Files Tab", ft);
		
		at = new AboutMeTab();
		tabbedPane.addTab("About Me", at);
	
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        if (JOptionPane.showConfirmDialog(null, 
		            "Are you sure you want to close?", null,
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		            System.exit(0);
		        }
		    }
		});
		
		setVisible(true);
			
	}
	
	// Algorithm I'm working on to programmatically create stoof
	private static void constructRounds() {
		rounds = new ArrayList<String>();
		String[] keyWords = { "Pool", "Winners", "Losers", "Grand Finals" };
		// lets build the pools ~ any higher than Z, we have a problemo
		for(int i = 0; i < numberOfPools; i++) {
			char tmp = (char) (i + 65);
			rounds.add(keyWords[0] + " " + tmp);
		}
		// lets add Winners + Losers
		String[] roundStuff = { "Quater-Finals", "Semi-Finals", "Finals" };
		if(simple) {
			rounds.add("Winners");
			rounds.add("Losers");
			for(int i = 0; i < roundStuff.length; i++) {
				rounds.add(keyWords[1] + " " + roundStuff[i]);
			}
			for(int i = 0; i < roundStuff.length; i++) {
				rounds.add(keyWords[2] + " " + roundStuff[i]);
			}
		} else {
			for(int i = 0; i < numberOfRounds; i++) {
				rounds.add(keyWords[1] + " Round " + (i+1));
				rounds.add(keyWords[2] + " Round " + (i+1));
			}
			for(int i = 0; i < roundStuff.length; i++) {
				rounds.add(keyWords[1] + " " + roundStuff[i]);
			}
			for(int i = 0; i < roundStuff.length; i++) {
				rounds.add(keyWords[2] + " " + roundStuff[i]);
			}
		}
		rounds.add(keyWords[3]);
	}
	
	public static void switchTo(int i) {
		tabbedPane.setSelectedIndex(i);
	}
	
	public static void main(String[] args) {
		new GUI();
	}
	
	public static String[] getArguments() {
		return arguments;
	}
	
	public static int getNumberOfPools() {
		return numberOfPools;
	}
	
	public static int getNumberOfRounds() {
		return numberOfRounds;
	}
	
}
