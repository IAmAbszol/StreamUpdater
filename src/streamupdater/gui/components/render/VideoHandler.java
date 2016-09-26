package streamupdater.gui.components.render;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Scanner;

import javax.imageio.ImageIO;

/*
 * May branch into it's own development but for now it's all in this file
 */
public class VideoHandler {

	private String inputFile = null;
	private String outputFile = null;
	private String outputImageFile = null;
	private long duration = 0;
	private long offset = 0;
	private BufferedImage image = null;
	
	//String pathToFile = "C:\\Users\\Kyle\\Desktop\\1855.mp4";
	//String target = "C:\\Users\\Kyle\\Desktop\\hello.mp4";
	// "cmd.exe", "/c", "ffmpeg -y -i " + pathToFile + " -ss 10 -c copy -t 10 " + target);
	// get duration --> hook this to an object
	
	public void setVideoInput(String s) {
		inputFile = s;
	}
	
	public void setExtension(String s) {
		
	}
	
	public void setVideoOutput(String s) {
		outputFile = s;
	}
	
	public void setDuration(long l) {
		duration = l;
	}
	
	public void setOffset(long l) {
		offset = l;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public void setImageFileLocation(String n) {
		outputImageFile = n;
	}
	
	public void encode() {
		try {
			System.out.println(inputFile);
			System.out.println(outputFile);
			System.out.println(duration);
			System.out.println(offset);

			ProcessBuilder builder = new 
					 ProcessBuilder(
							 "cmd", "/c", "ffmpeg -y -i " + "\"" + inputFile + "\" -codec copy -ss " + offset + " -t " + duration + "" + "\"" + outputFile + "\"");
							 //"cmd.exe", "/c", "ffmpeg -y -i " + "\"" + inputFile + "\" -c:v libx264 -crf 23 -preset ultrafast -f mp4 -r 59.940 -threads 2" + " -ss " + offset + " -c:a copy -t " + duration + " " + "\"" + outputFile + "\"");
			builder.redirectErrorStream(true);
			Process p = builder.start();
			inheritIO(p.getInputStream(), System.out);
			
			ImageIO.write(image, "png", new File(outputImageFile));
	       
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void inheritIO(final InputStream src, final PrintStream dest) {
	    new Thread(new Runnable() {
	        public void run() {
	            Scanner sc = new Scanner(src);
	            while (sc.hasNextLine()) {
	                dest.print(sc.nextLine());
	            }
	            System.out.println("Render Complete");
	        }
	    }).start();
	}
	
	public long getDuration() {
		
		if(inputFile != null) {
			//System.out.println("ffmpeg -y -i " + "\"" + inputFile + "\"" + " -vcodec libx264 -g 1 -async 1 -ss " + offset + " -c copy -t " + duration + " " + "\"" + outputFile + "\"");
			try {
			
				long duration = 0;
				String n = "";
				
				 ProcessBuilder builder = new 
						 ProcessBuilder(
				            "cmd.exe", "/c", "ffmpeg -i " + "\"" + inputFile + "\"");
		        builder.redirectErrorStream(true);
		        Process p = builder.start();
		        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		        String line;
		        while (true) {
		            line = r.readLine();
		            if (line == null) { break; }
		            System.out.println(line);
		            if(line.contains("Duration"))
		            	n = line;
		        }	
				
				// remove front stuff
				n = n.replace("Duration: ", "");
				n = n.replaceAll("\\s", "");
				// remove after duration stuff
				boolean read = true;
				String build = "";
				for(int i = 0; i < n.length(); i++) {
					if(n.charAt(i) ==',') read = false;
					if(read) {
						build = build + n.charAt(i);
					}
				}
				//handle times hh:mm:ss:ms
				String[] parses = build.split(":");
				String tmp = "" + parses[2].charAt(0) + parses[2].charAt(1);
				int s = Integer.parseInt(tmp);
				int m = Integer.parseInt(parses[1]);
				int h = Integer.parseInt(parses[0]);
				duration = (h * 3600) + (m * 60) + s;
				System.out.println(duration);
				return duration;
				
			} catch (Exception e) {
				e.printStackTrace();
				
				return -1;
			}
			
		} else
			return -1;
		
	}
	
}
