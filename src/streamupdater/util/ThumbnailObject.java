package streamupdater.util;

import java.awt.image.BufferedImage;
import java.io.File;

public class ThumbnailObject {

	private File file = null;
	private BufferedImage image = null;
	private int width = 0;
	private int height = 0;
	private int posx = 0;
	private int posy = 0;
	private boolean binded = false;
	private boolean selected = false;
	private boolean rev = false;
	
	public ThumbnailObject() {
		file = null;
		this.image = null;
		this.posx = -1;
		this.posy = -1;
		this.width = -1;
		this.height = -1;
		this.selected = false;
		binded = false;
		rev = false;
	}
	
	public void reset() {
		file = null;
		this.image = null;
		this.posx = -1;
		this.posy = -1;
		this.width = -1;
		this.height = -1;
		this.selected = false;
		binded = false;
		rev = false;
	}
	
	public boolean isReversed() {
		return rev;
	}
	
	public void setReversed(boolean r) {
		rev = r;
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File f) {
		file = f;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public int getX() {
		return posx;
	}
	
	public void setX(int x) {
		posx = x;
	}
	
	public int getY() {
		return posy;
	}
	
	public void setY(int y) {
		posy = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public boolean isBinded() {
		return binded;
	}
	
	public void setBinded(boolean t) {
		binded = t;
	}
	
}
