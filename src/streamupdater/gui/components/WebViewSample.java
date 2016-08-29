package streamupdater.gui.components;
import java.awt.Dimension;

import javax.swing.JFrame;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
 
 
public class WebViewSample {
    private Scene scene;
    public static String site;
 
    public WebViewSample(String[] args, String web) {
    	site = web;
    	JFrame frame = new JFrame(site);
    	frame.setResizable(true);
    	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	
    	JFXPanel fx = new JFXPanel();
    	
    	 Platform.runLater(new Runnable() {
             @Override
             public void run() {
            	 scene = new Scene(new Browser(),800,600, Color.web("#666970"));
                 fx.setScene(scene);
                 scene.getStylesheets().add("webviewsample/BrowserToolbar.css");
                 fx.show();
                 frame.add(fx);
             }
        });
    	 
    	frame.setPreferredSize(new Dimension(800, 600));
    	frame.setMinimumSize(new Dimension(400, 300));
    	frame.setVisible(true);
    	
    	frame.repaint();
        fx.repaint();
    	
    }
    

}