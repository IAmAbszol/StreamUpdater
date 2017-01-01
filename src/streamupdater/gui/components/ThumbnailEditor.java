package streamupdater.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import streamupdater.util.SavingFileConfiguration;
import streamupdater.util.TextEditor;
import streamupdater.util.ThumbnailObject;

public class ThumbnailEditor extends JPanel implements Runnable, KeyListener, MouseListener {
	
	private JFrame frame;
	private JFrame preview;
	private JPanel contentPane;
	
	private JFileChooser jfc;
	
	// automated gui, I'm lazy and its dynamic
	private static int numberOfLayers = 12;		// even numbers only guys and keep above 8
	private int gap = 100;
	private int finalLinePos = 0;
	private int pos = 0;
	
	private JButton[] layer;
	private JButton[] edit;
	private JButton[] remove;
	private JButton[] select;
	
	private JSpinner localWidth;
	private JSpinner localHeight;
	private static Font customFFont = new Font("Dialog", Font.BOLD, 16);			// custom, change here programmers
	private JSpinner posx;
	private JSpinner posy;
	private boolean ignore = false;
	
	private static JTextField width;
	private static JTextField height;
	private static JTextField previewWidth;
	private static JTextField previewHeight;
	
	// drawing stoof
	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
	
	private Thread thread;
	private boolean running;
	private int FPS = 60;

	private static File location;
	private static BufferedImage panelImage;
	private static BufferedImage image;
	private Graphics2D g;
	private static boolean pause = false;
	
	// when it gets too big, annoying issues start to happen with the text. This automatically fixes it
	private static int[] overrideSizes = {
			8,
			9,
			10,
			11,
			12,
			14,
			16,
			18,
			20,
			22,
			24,
			26,
			28,
			36,
			48,
			72
	};
	
	// adjust this, low the more sensitive the changing of the font is.
	private static int sensitivity = 4;
	
	// layer stoof
	// layer 0 --> at 0. layer 1 --> at 1
	private static ThumbnailObject[] layers;
	public static TextEditor[] te;
	
	public ThumbnailEditor() {
		buildEditor();
	}
	
	private void buildEditor() {
		try {
			
			pause = true;
			layers = new ThumbnailObject[numberOfLayers];
			layer = new JButton[numberOfLayers];
			edit = new JButton[numberOfLayers];
			select = new JButton[numberOfLayers];
			remove = new JButton[numberOfLayers];
			te = new TextEditor[numberOfLayers];
			for(int i = 0; i < layers.length; i++) {
				layers[i] = new ThumbnailObject();
			}
			
			frame = new JFrame("Thumbnail Editor - Panels at " + WIDTH + " x " + HEIGHT);
			frame.setResizable(true);
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			frame.setBounds(100, 100, 620, 1000);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			frame.setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JPanel panel = new JPanel();
			panel.setBounds(12, 13, 578, 927);
			contentPane.add(panel);
			panel.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("Thumbnail Editor");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
			lblNewLabel.setBounds(12, 13, 554, 40);
			panel.add(lblNewLabel);
			
			boolean sw1 = false;
			boolean sw2 = false;
			int line = 0;
			for(int i = 0; i < numberOfLayers; i++) {
				
				layer[i] = new JButton("Layer " + i);
				edit[i] = new JButton("Edit");
				remove[i] = new JButton("Remove");
				select[i] = new JButton("Select");
				
					// odd 
				if(!sw1 && !sw2) {
					layer[i].setBounds(22, (66 + (gap * line)), 97, 25);
					panel.add(this.layer[i]);
					
					edit[i].setBounds(22, (106 + (gap * line)), 97, 25);
					panel.add(this.edit[i]);
					
					remove[i].setBounds(131, (66 + (gap * line)), 97, 25);
					panel.add(this.remove[i]);
					
					select[i].setBounds(131, (106 + (gap * line)), 97, 25);
					panel.add(this.select[i]);
					sw1 = true;
				} else
					if(sw1 && !sw2) {
					
						layer[i].setBounds(360, (66 + (gap * line)), 97, 25);
						panel.add(layer[i]);
						
						edit[i].setBounds(360, (106 + (gap * line)), 97, 25);
						panel.add(edit[i]);
						
						remove[i].setBounds(469, (66 + (gap * line)), 97, 25);
						panel.add(remove[i]);
						
						select[i].setBounds(469, (106 + (gap * line)), 97, 25);
						panel.add(select[i]);
						sw2 = true;
						
					}
				if(sw1 && sw2)  {
					line++;
					sw1 = sw2 = false;
				}
				
				finalLinePos = (66 + (gap * line));
				
				edit[i].setEnabled(true);
				
			}
			
			JLabel objectLabel = new JLabel("Width and Height");
			objectLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
			objectLabel.setToolTipText("Selected Width and Height");
			objectLabel.setBounds(233, 106, 153, 25);
			panel.add(objectLabel);
			
			localWidth = new JSpinner();
			localWidth.setValue(0);
			localWidth.setBounds(240, 135, 50, 25);
			panel.add(localWidth);
			
			localHeight = new JSpinner();
			localHeight.setValue(0);
			localHeight.setBounds(300, 135, 50, 25);
			panel.add(localHeight);
			
			JLabel labelPos = new JLabel("(x,y)");
			labelPos.setFont(new Font("Tahoma", Font.PLAIN, 16));
			labelPos.setHorizontalAlignment(SwingConstants.CENTER);
			labelPos.setToolTipText("Hard Position X And Y");
			labelPos.setBounds(233, 180, 130, 25);
			panel.add(labelPos);
			
			posx = new JSpinner();
			posx.setValue(0);
			posx.setBounds(240, 210, 50, 25);
			panel.add(posx);
			
			posy = new JSpinner();
			posy.setValue(0);
			posy.setBounds(300, 210, 50, 25);
			panel.add(posy);
			
			JButton generate = new JButton("Generate Test Image");
			generate.setBounds(22, finalLinePos + 10, 153, 25);
			generate.setToolTipText("Outputs test to media folder or desktop");
			panel.add(generate);
			
			JButton deselectAll = new JButton("Deselect All Layers");
			deselectAll.setBounds(22, finalLinePos + 120, 153, 25);
			deselectAll.setToolTipText("Run this before generation");
			panel.add(deselectAll);
			
			JLabel lblWidthAndHeight = new JLabel("Width and Height");
			lblWidthAndHeight.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblWidthAndHeight.setToolTipText("Output Width and Height generated by test or auto");
			lblWidthAndHeight.setBounds(22, finalLinePos + 48, 153, 25);
			panel.add(lblWidthAndHeight);
			
			height = new JTextField();
			height.setText(""+HEIGHT);
			height.setHorizontalAlignment(SwingConstants.CENTER);
			height.setBounds(84, finalLinePos + 86, 50, 25);
			panel.add(height);
			height.setColumns(10);
			
			width = new JTextField();
			width.setHorizontalAlignment(SwingConstants.CENTER);
			width.setText(""+WIDTH);
			width.setBounds(22, finalLinePos + 86, 50, 25);
			panel.add(width);
			width.setColumns(10);
			
			/*
			 * Thumbnail editor
			 */
			setPreferredSize(new Dimension(WIDTH, HEIGHT));
			requestFocus();
			setFocusable(true);
			
			preview = new JFrame("Preview Window");
			preview.setContentPane(this);
			preview.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			preview.setResizable(false);
			preview.pack();
			preview.setVisible(false);	
			
			JButton previewWindow = new JButton("Preview Thumbnail");
			previewWindow.setBounds(360, finalLinePos + 10, 150, 25);
			panel.add(previewWindow);
			
			JLabel previewLabel = new JLabel("Preview Width & Height");
			previewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
			previewLabel.setToolTipText("Adjust thumbnail size, keeping ");
			previewLabel.setBounds(360, finalLinePos + 48, 220, 25);
			panel.add(previewLabel);
			
			previewWidth = new JTextField();
			previewWidth.setHorizontalAlignment(SwingConstants.CENTER);
			previewWidth.setText(""+WIDTH);
			previewWidth.setBounds(360, finalLinePos + 86, 50, 25);
			previewWidth.setColumns(10);
			panel.add(previewWidth);
			
			previewHeight = new JTextField();
			previewHeight.setHorizontalAlignment(SwingConstants.CENTER);
			previewHeight.setText(""+HEIGHT);
			previewHeight.setBounds(422, finalLinePos + 86, 50, 25);
			previewHeight.setColumns(10);
			panel.add(previewHeight);
			
			JButton update = new JButton("Update Preview");
			update.setBounds(360, finalLinePos + 120, 200, 25);
			panel.add(update);
			
			frame.setBounds(100, 100, 620, finalLinePos + 250);
			
			panel.setBounds(12, 13, 578, finalLinePos + 148);
			
			frame.setVisible(true);
			repaint();
			pause = false;
			
			update.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					WIDTH = Integer.parseInt(previewWidth.getText());
					HEIGHT = Integer.parseInt(previewHeight.getText());
					frame.setTitle("Thumbnail Editor - Panels at " + WIDTH + " x " + HEIGHT);
					setPreferredSize(new Dimension(WIDTH, HEIGHT));
					repaint();
					preview.repaint();
					panel.repaint();
					frame.repaint();
					preview.pack();
					width.setText(""+WIDTH);
					height.setText(""+HEIGHT);
				}
				
			});
			
			previewWindow.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					preview.setVisible(true);
				}
				
			});
			
			deselectAll.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					for(int i = 0; i < numberOfLayers; i++) {
						layers[i].setSelected(false);
					}
				}
				
			});
			
			generate.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						generatePanel(null);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			});
			
			posx.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent arg0) {
					for(int i = 0; i < layers.length; i++) {
						if(layers[i].isSelected()) {
							layers[i].setX((int) posx.getValue());
						}
					}
				}
				
			});
			
			posy.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					for(int i = 0; i < layers.length; i++) {
						if(layers[i].isSelected()) {
							layers[i].setY((int) posy.getValue());
						}
					}
				}
				
			});
			
			localWidth.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					for(int i = 0; i < layers.length; i++) {
						/*if(layers[i].isedited() && layers[i].isSelected()) {
							// decrease
							if(wval > (int)localWidth.getValue()) {
								ignore = true;
								localHeight.setValue((int) localHeight.getValue() - 1);
								hval = (int) localHeight.getValue();
								wval = (int) localWidth.getValue();
								layers[i].setWidth(wval);
							} else 
								if(wval < (int) localWidth.getValue()) {
									ignore = true;
									localHeight.setValue((int) localHeight.getValue() + 1);
									hval = (int) localHeight.getValue();
									wval = (int) localWidth.getValue();
									layers[i].setWidth(wval);
								}
						}*/
						if(layers[i].isSelected())
							layers[i].setWidth((int) localWidth.getValue());
					}
				}
			});
			
			localHeight.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					if(!ignore) {
						for(int i = 0; i < layers.length; i++) {
							/*if(layers[i].isedited() && layers[i].isSelected()) {
								// decrease
								if(hval > (int)localWidth.getValue()) {
									localWidth.setValue((int) localWidth.getValue() - 1);
									wval = (int) localWidth.getValue();
									hval = (int) localHeight.getValue();
									layers[i].setHeight(hval);
								} else 
									if(hval < (int) localHeight.getValue()) {
										localWidth.setValue((int) localWidth.getValue() + 1);
										wval = (int) localWidth.getValue();
										hval = (int) localHeight.getValue();
										layers[i].setHeight(hval);
									}
							}*/
							if(layers[i].isSelected())
								layers[i].setHeight((int) localHeight.getValue());
						}
					} else {
						ignore = false;
					}
				}
			});
			
			for(int i = 0; i < numberOfLayers; i++) {
				pos = i;
				layer[i].addActionListener(new LayerButton(i));
				remove[i].addActionListener(new RemoveButton(i));
				edit[i].addActionListener(new editButton(i));
				select[i].addActionListener(new SelectButton(i));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			addMouseListener(this);
			thread.start();
		}
	}
	
	private void init() {
		image = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
	}
	
	private long redraw() {
		
		long t = System.currentTimeMillis();
		
		if(!SavingFileConfiguration.isSleeping())
			update();
		
		if(!pause) {
			draw();
			drawToScreen();
		}
		
		return System.currentTimeMillis() - t;
	}
	
	public void run() {
		
		init();
		
		while(running) {
			
			long durationMs = redraw();
			
			try {
				Thread.sleep(Math.max(0, FPS - durationMs));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void update () {
		reupdateImages();
	}
	
	private void draw() {
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		for(int i = 0; i < layers.length; i++) {
			if(layers[i] != null) {
				g.drawImage(
						layers[i].getImage(), 
						layers[i].getX(), 
						layers[i].getY(), 
						layers[i].getWidth(), 
						layers[i].getHeight(), 
						null
				);
				g.setColor(Color.red);
				for(int j = 0; j < layers.length; j++) {
					if(layers[j].isSelected()) 
						g.drawRect(
								layers[j].getX(),
								layers[j].getY(),
								layers[j].getWidth(),
								layers[j].getHeight()
						);
				}
			}
		}
	}
	
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, 
				WIDTH, HEIGHT, 
				null);
		panelImage = image;
		g2.dispose();
	}

	public BufferedImage generateThumbnail() {
		// so draw doesnt interfere
		if(!pause) pause = true;
		for(int i = 0; i < numberOfLayers; i++) {
			layers[i].setSelected(false);
		}
		draw();
		drawToScreen();
		int genwidth = Integer.parseInt(width.getText());
		int genheight = Integer.parseInt(height.getText());
		// manipulate the width and height to specs
		
		BufferedImage resized = new BufferedImage(genwidth, genheight, BufferedImage.TYPE_INT_ARGB);
		Graphics g = resized.createGraphics();
		g.drawImage(panelImage, 0, 0, genwidth, genheight, null);
		g.dispose();
		pause = false;
		return resized;
	}
	
	public static void deselect() {
		for(int i = 0; i < numberOfLayers; i++) {
			layers[i].setSelected(false);
		}
	}
	
	private void generatePanel(String n) throws IOException {
		if(!pause) pause = true;
		for(int i = 0; i < numberOfLayers; i++) {
			layers[i].setSelected(false);
		}
		draw();
		drawToScreen();
		String user = System.getProperty("user.name");
		String location = "C:\\Users\\"+user+"\\Desktop\\";
		if(!FilesTab.getMediaFolder().equals("")) {
			location = FilesTab.getMediaFolder().replaceAll("/", "\\\\") + "\\"; 
		}
		int genwidth = WIDTH;
		int genheight = HEIGHT;
		// manipulate the width and height to specs
		
		BufferedImage resized = new BufferedImage(genwidth, genheight, BufferedImage.TYPE_INT_ARGB);
		Graphics g = resized.createGraphics();
		g.drawImage(panelImage, 0, 0, genwidth, genheight, null);
		g.dispose();
		
		//print
		File outputfile = null;
		if(n == null || n == "")
			outputfile = new File(location + "\\test.png");
		else
			outputfile = new File(n);
		pause = false;
		ImageIO.write(resized, "png", outputfile);

	}
	
	public static void reupdateImagesOverride() {
		/* basically, this will update any changes thus
		 * if a char change happened, it will change ONLY if you are
		 * using root as your media center
		 */ 
		for(int i = 0; i < layers.length; i++) {
			if(layers[i] != null && layers[i].getFile() != null && layers[i].getImage() != null) {
				try {
					if(!layers[i].isReversed()) {
						if(layers[i].getFile().getName().contains(".txt")) {
							layers[i].setImage(convertTextToImage(layers[i].getFile(), i));
							layers[i].setWidth(layers[i].getImage().getWidth());
							layers[i].setHeight(layers[i].getImage().getHeight());
						} else
							layers[i].setImage(ImageIO.read(layers[i].getFile()));
					} else {
						reverseImage(i);
					}
					try{
						layers[i].collectTimeStamp();
					} catch (Exception e2) {
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void reupdateImages() {
		/* basically, this will update any changes thus
		 * if a char change happened, it will change ONLY if you are
		 * using root as your media center
		 */ 
		for(int i = 0; i < layers.length; i++) {
			if(layers[i] != null && layers[i].getFile() != null && layers[i].getImage() != null) {
				if(layers[i].getFile().lastModified() != layers[i].getTimeStamp()) {
					try {
						if(!layers[i].isReversed()) {
							if(layers[i].getFile().getName().contains(".txt")) {
								layers[i].setImage(convertTextToImage(layers[i].getFile(), i));
								layers[i].setWidth(layers[i].getImage().getWidth());
								layers[i].setHeight(layers[i].getImage().getHeight());
							} else
								layers[i].setImage(ImageIO.read(layers[i].getFile()));
						} else {
							reverseImage(i);
						}
						try{
							layers[i].collectTimeStamp();
						} catch (Exception e2) {
							
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	// really simple
	public static BufferedImage convertTextToImage(File f, int i) {
		try {
			int tmpy = 0;
			int type = Font.PLAIN;
			if(layers[i].isBold()) type = Font.BOLD;
			if(layers[i].isItalic()) type = type | Font.ITALIC;
			layers[i].setFont(te[i].getFont());
			layers[i].setAlignment(te[i].getAlignment());
			layers[i].setSize(te[i].getSize());
			layers[i].setColor(te[i].getColor()[0], te[i].getColor()[1], te[i].getColor()[2]);
			layers[i].setBold(te[i].isBold());
			layers[i].setItalic(te[i].isItalic());
			layers[i].setAdjusted(te[i].isAdjusted());
			layers[i].setWidth(te[i].getWidth());
			layers[i].setHeight(te[i].getHeight());
			
			// grab width of longest line, if it's multi-line
			BufferedImage tmp = new BufferedImage(layers[i].getWidth(), layers[i].getHeight(), BufferedImage.TYPE_INT_ARGB);
			BufferedImage actual;
			BufferedImage ghetto;
			Graphics2D gx = tmp.createGraphics();
			gx.setColor(new Color(layers[i].getColor()[0], layers[i].getColor()[1], layers[i].getColor()[2]));
			gx.setFont(new Font(layers[i].getFont(), type, layers[i].getSize()));
			String line = null;
			BufferedReader reader = new BufferedReader(new FileReader(f));
			int longest = 0;
			while((line = reader.readLine()) != null) {
				if(gx.getFontMetrics().stringWidth(line) > longest) {
					longest = gx.getFontMetrics().stringWidth(line);
				}
			}
			
			// check if the image/text is longer then designated width
			if(longest > layers[i].getWidth()) {
				if(layers[i].isAdjusted()) {
					int tmpnum = longest - layers[i].getWidth();
					int reduce = 0;
					while(tmpnum > sensitivity) {
						tmpnum %= sensitivity;
						reduce++;
					}
					for(int z = 0; z < overrideSizes.length; z++) {
						if(overrideSizes[z] > layers[i].getSize()) {
							if(z - reduce >= 0) {
								layers[i].setSize(overrideSizes[z - reduce]);
								if(layers[i].getAlignment().equals("right") || layers[i].getAlignment().equals("center")) {
									if(z - (reduce + 1) >= 0) layers[i].setSize(overrideSizes[z - (reduce + 1)]);
								}
							} else {
								layers[i].setSize(overrideSizes[0]);
							}
							break;
						}
					}
				}
				// draw to image, ignore controllers wish of width, we will do that later
				actual = new BufferedImage(longest, layers[i].getHeight(), BufferedImage.TYPE_INT_ARGB);
				gx.dispose();
				tmp = null;
				gx = actual.createGraphics();
				gx.setColor(new Color(te[i].getColor()[0], te[i].getColor()[1], te[i].getColor()[2]));
				gx.setFont(new Font(layers[i].getFont(), type, layers[i].getSize()));
				reader.close();
				reader = new BufferedReader(new FileReader(f));
				while((line = reader.readLine()) != null) {
					gx.drawString(line,0, (tmpy += gx.getFontMetrics().getHeight()));
				}
				reader.close();
				// now lets resize this
				ghetto = new BufferedImage(layers[i].getWidth(), layers[i].getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2x = ghetto.createGraphics();
				g2x.drawImage(actual, 0, 0, layers[i].getWidth(), layers[i].getHeight(), null);
				g2x.dispose();
				return ghetto;
			} else {
				actual = new BufferedImage(layers[i].getWidth(), layers[i].getHeight(), BufferedImage.TYPE_INT_ARGB);
				gx.dispose();
				tmp = null;
				gx = actual.createGraphics();
				gx.setColor(new Color(te[i].getColor()[0], te[i].getColor()[1], te[i].getColor()[2]));
				gx.setFont(new Font(layers[i].getFont(), type, layers[i].getSize()));
				reader.close();
				reader = new BufferedReader(new FileReader(f));
				while((line = reader.readLine()) != null) {
					if(layers[i].getAlignment().equals("left"))
						gx.drawString(line,0, (tmpy += gx.getFontMetrics().getHeight()));
					else
					if(layers[i].getAlignment().equals("right"))
						gx.drawString(line, layers[i].getWidth() - gx.getFontMetrics().stringWidth(line), (tmpy += gx.getFontMetrics().getHeight()));
					else
					if(layers[i].getAlignment().equals("center"))
						gx.drawString(line, (layers[i].getWidth() / 2) - (gx.getFontMetrics().stringWidth(line) / 2), (tmpy += gx.getFontMetrics().getHeight()));
				}
				reader.close();
				return actual;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void reverseImage(int i) {
		BufferedImage tmp = new BufferedImage(
				layers[i].getImage().getWidth(),
				layers[i].getImage().getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics gx = tmp.createGraphics();
		try {
			if(layers[i].getFile().getName().contains(".txt")) {
				layers[i].setImage(convertTextToImage(layers[i].getFile(), i));
			} else
				layers[i].setImage(ImageIO.read(layers[i].getFile()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gx.drawImage(
				layers[i].getImage(), 
				layers[i].getImage().getWidth(), 
				0, 
				-layers[i].getImage().getWidth(), 
				layers[i].getImage().getHeight(), 
				null);
		gx.dispose();
		layers[i].setImage(tmp);
	}
	
	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
		// button is being held, scanning layers that match
		if(arg0.getButton() == MouseEvent.BUTTON3) {
			for(int i = 0; i < layers.length; i++) {
				if(layers[i].getFile() != null && layers[i].getImage() != null && layers[i].isSelected()) {
					if(layers[i].isReversed()) {
						layers[i].setReversed(false);
						reupdateImagesOverride();
						return;
					} else {
						layers[i].setReversed(true);
						reupdateImagesOverride();
						return;
					}
				}
			}
		}
		if(arg0.getButton() == MouseEvent.BUTTON1) {
			for(int i = 0; i < layers.length; i++) {
				if(layers[i].isSelected()) {
					layers[i].setX(arg0.getX());
					layers[i].setY(arg0.getY());
					posx.setValue(arg0.getX());
					posy.setValue(arg0.getY());
				}
			}
		}
	}

	public void mouseReleased(MouseEvent arg0) {
	}

	public void keyPressed(KeyEvent arg0) {
	}

	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}

	/*
	 * Ahh action listeners <3
	 */
	
	private class SelectButton implements ActionListener {
		
		private int pos = 0;
		
		public SelectButton(int i) {
			pos = i;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(layers[pos].getImage() != null) {
				for(int i = 0; i < layers.length; i++) {
					layers[i].setSelected(false);
				}
				layers[pos].setSelected(true);
				localWidth.setValue(layers[pos].getWidth());
				localHeight.setValue(layers[pos].getHeight());
				posx.setValue(layers[pos].getX());
				posy.setValue(layers[pos].getY());
			}			
		}
		
	}

	private class editButton implements ActionListener {

		private int pos = 0;
		
		public editButton(int i) {
			pos = i;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(layers[pos].getImage() != null) {
				if(te[pos] != null) {
					te[pos].getFrame().setVisible(true);
				}
			}
		}
		
	}
	
	private class RemoveButton implements ActionListener {

		int pos = 0;
		
		public RemoveButton(int i) {
			pos = i;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			layers[pos].reset();
			layer[pos].setToolTipText("");
			if(te[pos] != null) {
				te[pos].getFrame().dispose();
				te[pos] = null;
			}
		}
		
	}
	
	private class LayerButton implements ActionListener {

		private int pos = 0;
		
		public LayerButton(int i) {
			pos = i;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			jfc = new JFileChooser();
			jfc.setCurrentDirectory(new java.io.File("user.home"));
			jfc.setDialogTitle("Select Layer 0 Image File");
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if (jfc.showOpenDialog(layer[pos]) == JFileChooser.APPROVE_OPTION) {
				try {
					layers[pos].setFile(jfc.getSelectedFile());
					if(!layers[pos].isReversed())
						if(layers[pos].getFile().getName().contains(".txt")) {
							if(te[pos] == null)
								te[pos] = new TextEditor(pos);
							else
								te[pos].getFrame().setVisible(true);
							// load defaults, this will be overriden when saved
							layers[pos].setFont(te[pos].getFont());
							layers[pos].setAlignment(te[pos].getAlignment());
							layers[pos].setSize(te[pos].getSize());
							layers[pos].setColor(te[pos].getColor()[0], te[pos].getColor()[1], te[pos].getColor()[2]);
							layers[pos].setBold(te[pos].isBold());
							layers[pos].setItalic(te[pos].isItalic());
							layers[pos].setAdjusted(te[pos].isAdjusted());
							layers[pos].setWidth(te[pos].getWidth());
							layers[pos].setHeight(te[pos].getHeight());
							layers[pos].setImage(convertTextToImage(layers[pos].getFile(), pos));
						} else 
							if(layers[pos].getFile().getName().contains("png") ||
									layers[pos].getFile().getName().contains("jpg") ||
									layers[pos].getFile().getName().contains("jpeg") ||
									layers[pos].getFile().getName().contains("bmp")){
							layers[pos].setImage(ImageIO.read(layers[pos].getFile()));
							layers[pos].setX(0);
							layers[pos].setY(0);
							layers[pos].setWidth((int) (layers[pos].getImage().getWidth()));
							layers[pos].setHeight((int) (layers[pos].getImage().getHeight()));
						} else {
							JOptionPane.showMessageDialog(null, "txt, png, jpg, and bmp files only!");
							layers[pos].reset();
							layer[pos].setToolTipText("");
							if(te[pos] != null) {
								te[pos].getFrame().dispose();
								te[pos] = null;
							}
							return;
						}
					else {
						reverseImage(pos);
					}
					edit[pos].setEnabled(true);
					layer[pos].setToolTipText(jfc.getSelectedFile().getAbsolutePath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
}
