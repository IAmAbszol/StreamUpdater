package streamupdater.gui.components;

import javax.swing.JPanel;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;

/*
 * Still being built
 */

@SuppressWarnings("serial")
public class BracketTab extends JPanel {
	
	// bunch of objects to be added, removed, etc
	private static JPanel jp;
	static JFXPanel fxPanel;
	
	public BracketTab() {
		
		// browsing
		fxPanel = new JFXPanel();
		
		setLayout(null);
		
		jp = new JPanel();
		jp.setBounds(10, 11, 539, 690);
		jp.setLayout(null);
		add(jp);
		
	}
	public static void buildBrowser(String n) {
		
		Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	Scene scene = new Scene(new Browser());
            	scene.getStylesheets().add("webviewsample/BrowserToolbar.css");
        		fxPanel.setScene(scene);
        		jp.add(fxPanel);
            }
       });
		
	}
	
}
