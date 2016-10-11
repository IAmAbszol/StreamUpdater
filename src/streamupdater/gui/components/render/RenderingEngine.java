package streamupdater.gui.components.render;


/*
 * Rendering engine, allows the program to gather information about files 
 * and complete/halt rendering when the user suggests.
 */
public class RenderingEngine {
	
	private static RenderObject ro = null;
	
	public static int renderProject(VideoHandler video, int pos) {
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
	
	public void renderImages(VideoHandler video) {
		if(ro.getImages().size() == ro.getImageFileNames().size()) {
			for(int i = 0; i < ro.getImages().size(); i++) {
				video.setImage(ro.getImages().get(i));
				video.setImageFileLocation(ro.getImageFileNames().get(i));
				video.createImages();
			}
		}
	}

	// class that handles the overall rendering of the file
	private static void handleRender(VideoHandler video, String s, int pos) {
		video.setDuration(ro.getDurations().get(pos));
		video.setOffset(ro.getStartingPositions().get(pos));
		video.setVideoInput(s);
		video.setVideoOutput(ro.getFileNames().get(pos));
		video.setImage(ro.getImages().get(pos));
		video.setImageFileLocation(ro.getImageFileNames().get(pos));
		video.encode();
	}
	
	public void setObject(RenderObject ro) {
		this.ro = ro;
	}
	
	public void removePartObject(int pos) {
		
		if(ro != null) {
			System.out.println("Removed");
			ro.getDurations().remove(pos);
			ro.getFileNames().remove(pos);
			ro.getImageFileNames().remove(pos);
			ro.getImages().remove(pos);
			ro.getStartingPositions().remove(pos);
			
		}
		
	}
	
}
