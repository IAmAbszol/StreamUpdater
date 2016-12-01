package streamupdater.util;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class CheckStreaming {
	
	private File f = null;
	private boolean streaming = false;
	private boolean thinking = false;
	
	public CheckStreaming(String file) {
		f = new File(file);
	}
	
	public boolean isStreaming() {
	
		if(!f.exists()) {
			JOptionPane.showMessageDialog(null, "File Not Found! Please reintilize the class with proper file.");
			return false;
		}

		thinking = true;
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				double preBytes = f.length();
				double pastBytes = 0;
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				pastBytes = f.length();
				
				if(pastBytes != preBytes) streaming = true;
				thinking = false;
				
			}
		});
		t.start();
		while(thinking) System.out.print("");
		
		return streaming;
		
	}
	
}
