package streamupdater.gui.components.render;

import java.io.File;

/*
 * Rendering engine, allows the program to gather information about files 
 * and complete/halt rendering when the user suggests.
 */
public class RenderingEngine {
	
	private static RenderObject ro = null;
	
	public static int renderProject(VideoHandler video, int pos) {
		if(ro == null) {
			ro = RenderObject.load();
		}
		if(ro != null) {
			if(ro.getDurations().size() == ro.getFileNames().size() && ro.getStartingPositions().size() == ro.getFileNames().size() ) {
				handleRender(video, ro.getStreamURL(), pos);
			} else
				return -3;
		} else
			return -2;
		return -1;
	}
	
	public static int renderProject(VideoHandler video) {
		if(ro == null) {
			ro = RenderObject.load();
		}
		if(ro != null) {
			if(ro.getDurations().size() == ro.getFileNames().size() && ro.getStartingPositions().size() == ro.getFileNames().size() ) {
				for(int i = 0; i < ro.getDurations().size(); i++) {
					handleRender(video, ro.getStreamURL(), i);
				}
			} else
				return -3;
		} else
			return -2;
		return -1;
	}

	// class that handles the overall rendering of the file
	private static void handleRender(VideoHandler video, String s, int pos) {
		video.setDuration(ro.getDurations().get(pos));
		video.setOffset(ro.getStartingPositions().get(pos));
		video.setVideoInput(s);
		video.setVideoOutput(ro.getFileNames().get(pos));
		video.encode();
	}
	
	public void setObject(RenderObject ro) {
		this.ro = ro;
	}
	
}
