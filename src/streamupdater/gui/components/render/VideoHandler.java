package streamupdater.gui.components.render;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import streamupdater.util.ScanForFFMpeg;

/*
 * May branch into it's own development but for now it's all in this file
 */
public class VideoHandler {

	private String inputFile = null;
	private String alteredFile = null;
	private String outputFile = null;
	private String outputImageFile = null;
	private long duration = 0;
	private long offset = 0;
	private BufferedImage image = null;
	
	private String readConvert = null;
	private boolean canEncode = true;
	private String readEncode = null;
	
	//String pathToFile = "C:\\Users\\Kyle\\Desktop\\1855.mp4";
	//String target = "C:\\Users\\Kyle\\Desktop\\hello.mp4";
	// "cmd.exe", "/c", "ffmpeg -y -i " + pathToFile + " -ss 10 -c copy -t 10 " + target);
	// get duration --> hook this to an object
	
	public void setVideoInput(String s) {
		inputFile = s;
		alteredFile = s.replaceAll(".flv", ".mp4");
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
	
	public void convertToMp4() {
		try {
			alteredFile = inputFile.replace(".flv", ".mp4");
			
			final URI uri;
	        final String exe;
	        String arg = null;
	        
	        if(!ScanForFFMpeg.scan()) {

		        uri = getJarURI();
		        exe = getFile(uri, "streamupdater/gui/components/render/ffmpeg.exe").toString().replace("file:/", "");
				
		        arg = " -y -i " + "\"" + inputFile + "\" -codec copy " + "\"" + alteredFile + "\"";
		        arg = exe + arg;
	        
	        } else
	        	arg = "ffmpeg -y -i " + "\"" + inputFile + "\" -codec copy " + "\"" + alteredFile + "\"";
	        
			ProcessBuilder builder = new 
					 ProcessBuilder(
							 "cmd", "/c", arg);
			
			builder.redirectErrorStream(true);
			Process p = builder.start();
			inheritIO(p.getInputStream(), System.out, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void encode() {
		try {
			System.out.println(inputFile);
			System.out.println(alteredFile);
			System.out.println(outputFile);
			System.out.println(duration);
			System.out.println(offset);
			
			final URI uri;
	        final String exe;
	        String arg = null;
	        
	        if(!ScanForFFMpeg.scan()) {
	        
		        uri = getJarURI();
		        exe = getFile(uri, "streamupdater/gui/components/render/ffmpeg.exe").toString().replace("file:/", "");
				
		        arg = " -y -ss " + offset + " -i " + "\"" + alteredFile + "\" -t " + duration + " " + "\"" + outputFile + "\"";
		        arg = exe + arg;
	        
	        } else 
	        	arg = "ffmpeg -y -ss " + offset + " -i " + "\"" + alteredFile + "\" -t " + duration + " " + "\"" + outputFile + "\"";
	        	
			ProcessBuilder builder = new 
					 ProcessBuilder(
							 "cmd", "/c", arg);

			builder.redirectErrorStream(true);
			Process p = builder.start();
			inheritIO(p.getInputStream(), System.out, false);
			
			try {
				ImageIO.write(image, "png", new File(removeIllegal(outputImageFile)));
			} catch (Exception e) {
			}
			System.out.println("Rendering Complete");
			readEncode = null;
	       
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createImages() {
		
		try {
			ImageIO.write(image, "png", new File(removeIllegal(outputImageFile)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String removeIllegal(String n) {
		char[] illegal = {
				'|'
		};
		String build = "";
		boolean start = false;
		for(int i = 0; i < n.length(); i++) {
			if(n.charAt(i) != ' ' && start) {
				build = build + n.charAt(i);
			}
			for(int z = 0; z < illegal.length; z++) {
				if(n.charAt(i) == illegal[z]) {
					start = true;
				}
			}
		}
		if(!start) return n;
		return build;
	}
	
	private void inheritIO(final InputStream src, final PrintStream dest, boolean convert) {
	    new Thread(new Runnable() {
	        public void run() {
	            Scanner sc = new Scanner(src);
	            while (sc.hasNextLine()) {
	            	if(convert) {
	            		readConvert = sc.nextLine();
		                dest.println(readConvert);
	            	} else {
	            		readEncode = sc.nextLine();
	            		dest.println(readEncode);
	            	}
	            }
	            sc.close();
	        }
	    }).start();
	}
	
    private static URI getJarURI()
        throws URISyntaxException
    {
        final ProtectionDomain domain;
        final CodeSource       source;
        final URL              url;
        final URI              uri;

        domain = VideoHandler.class.getProtectionDomain();
        source = domain.getCodeSource();
        url    = source.getLocation();
        uri    = url.toURI();

        return (uri);
    }

    private static URI getFile(final URI where, final String fileName)
        throws ZipException,
               IOException
    {
        final File location;
        final URI  fileURI;

        location = new File(where);

        // not in a JAR, just return the path on disk
        if(location.isDirectory())
        {
            fileURI = URI.create(where.toString() + fileName);
        }
        else
        {
            final ZipFile zipFile;

            zipFile = new ZipFile(location);

            try
            {
                fileURI = extract(zipFile, fileName);
            }
            finally
            {
                zipFile.close();
            }
        }

        return (fileURI);
    }

    private static URI extract(final ZipFile zipFile,
                               final String  fileName)
        throws IOException
    {
        final File         tempFile;
        final ZipEntry     entry;
        final InputStream  zipStream;
        OutputStream       fileStream;

        tempFile = File.createTempFile(fileName, Long.toString(System.currentTimeMillis()));
        tempFile.deleteOnExit();
        entry    = zipFile.getEntry(fileName);
        if(entry == null)
        {
            throw new FileNotFoundException("cannot find file: " + fileName + " in archive: " + zipFile.getName());
        }

        zipStream  = zipFile.getInputStream(entry);
       
        fileStream = null;

        try
        {
            final byte[] buf;
            int          i;

            fileStream = new FileOutputStream(tempFile);
            buf        = new byte[1024];
            i          = 0;

            while((i = zipStream.read(buf)) != -1)
            {
                fileStream.write(buf, 0, i);
            }
        }
        finally
        {
            close(zipStream);
            close(fileStream);
        }

        return (tempFile.toURI());
    }

    private static void close(final Closeable stream)
    {
        if(stream != null)
        {
            try
            {
                stream.close();
            }
            catch(final IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }
	
	public long getDuration() {
		
		if(inputFile != null) {
			//System.out.println("ffmpeg -y -i " + "\"" + inputFile + "\"" + " -vcodec libx264 -g 1 -async 1 -ss " + offset + " -c copy -t " + duration + " " + "\"" + outputFile + "\"");
			try {
			
				long duration = 0;
				String n = "";
				
				final URI uri;
		        final String exe;
		        String arg = null;

		        if(!ScanForFFMpeg.scan()) {
		        
			        uri = getJarURI();
			        exe = getFile(uri, "streamupdater/gui/components/render/ffmpeg.exe").toString().replace("file:/", "");
					
			        arg = " -i " + "\"" + inputFile + "\"";
			        arg = exe + arg;
			        
		        } else
		        	arg = "ffmpeg -i " + "\"" + inputFile + "\"";
		        
				ProcessBuilder builder = new 
						 ProcessBuilder(
								 "cmd", "/c", arg);

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
