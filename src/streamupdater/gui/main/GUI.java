package streamupdater.gui.main;

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
	
	private static String[] arguments;
	
	public GUI() {
		
		try {
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		} catch (Exception e) { };
		
		setTitle("Stream Updater Version 3.9.3 by Kyle Darling");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		ScanForFFMpeg s = new ScanForFFMpeg();
		if(!s.scan()) {
			JOptionPane.showMessageDialog(null, "FFMpeg has not been detected, please refer to my FFMpeg install video.");
			tabbedPane.remove(rt);
		} 
		
		setVisible(true);
			
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
	
}
