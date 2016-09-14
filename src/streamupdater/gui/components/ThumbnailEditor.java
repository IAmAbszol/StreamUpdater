package streamupdater.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import streamupdater.util.ThumbnailObject;

public class ThumbnailEditor extends JPanel implements Runnable, KeyListener, MouseListener {
	
	private JFrame frame;
	private JPanel contentPane;
	
	private JFileChooser jfc;
	
	// automated gui, I'm lazy and its dynamic
	private int numberOfLayers = 10;		
	private int gap = 100;
	private int finalLinePos = 0;
	private int pos = 0;
	
	private JButton[] layer;
	private JButton[] bind;
	private JButton[] remove;
	private JButton[] select;
	
	private JSpinner localWidth;
	private JSpinner localHeight;
	private static Font customFont = new Font("Dialog", Font.BOLD, 36);			// custom, change here programmers
	private JSpinner posx;
	private JSpinner posy;
	private boolean ignore = false;
	
	private static JTextField width;
	private static JTextField height;
	
	// drawing stoof
	public static int WIDTH = 380;
	public static int HEIGHT = 260;
	public static int BASEX = 640;
	public static int BASEY = 480;
	
	private double multix = (double)WIDTH / (double)BASEX;
	private double multiy = (double)HEIGHT / (double)BASEY;
	
	private Thread thread;
	private boolean running;
	private int FPS = 60;

	private static File location;
	private static BufferedImage image;
	private Graphics2D g;
	
	// layer stoof
	// layer 0 --> at 0. layer 1 --> at 1
	private static ThumbnailObject[] layers;
	
	public ThumbnailEditor() {
		try {

			layers = new ThumbnailObject[numberOfLayers];
			layer = new JButton[numberOfLayers];
			bind = new JButton[numberOfLayers];
			select = new JButton[numberOfLayers];
			remove = new JButton[numberOfLayers];
			for(int i = 0; i < layers.length; i++) {
				layers[i] = new ThumbnailObject();
			}
			
			frame = new JFrame("Thumbnail Editor - Panels at 380 x 260");
			frame.setResizable(false);
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
				bind[i] = new JButton("Bind");
				remove[i] = new JButton("Remove");
				select[i] = new JButton("Select");
				
					// odd 
				if(!sw1 && !sw2) {
					layer[i].setBounds(22, (66 + (gap * line)), 97, 25);
					panel.add(this.layer[i]);
					
					bind[i].setBounds(22, (106 + (gap * line)), 97, 25);
					panel.add(this.bind[i]);
					
					remove[i].setBounds(131, (66 + (gap * line)), 97, 25);
					panel.add(this.remove[i]);
					
					select[i].setBounds(131, (106 + (gap * line)), 97, 25);
					panel.add(this.select[i]);
					sw1 = true;
				} else
					if(sw1 && !sw2) {
					
						layer[i].setBounds(360, (66 + (gap * line)), 97, 25);
						panel.add(layer[i]);
						
						bind[i].setBounds(360, (106 + (gap * line)), 97, 25);
						panel.add(bind[i]);
						
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
				
				bind[i].setEnabled(false);
				
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
			
			JLabel lblWidthAndHeight = new JLabel("Width and Height");
			lblWidthAndHeight.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblWidthAndHeight.setToolTipText("Output Width and Height generated by test or auto");
			lblWidthAndHeight.setBounds(22, finalLinePos + 48, 153, 25);
			panel.add(lblWidthAndHeight);
			
			height = new JTextField();
			height.setText("720");
			height.setHorizontalAlignment(SwingConstants.CENTER);
			height.setBounds(84, finalLinePos + 86, 50, 25);
			panel.add(height);
			height.setColumns(10);
			
			width = new JTextField();
			width.setHorizontalAlignment(SwingConstants.CENTER);
			width.setText("1280");
			width.setBounds(22, finalLinePos + 86, 50, 25);
			panel.add(width);
			width.setColumns(10);
			
			setBounds(185, finalLinePos + 10, WIDTH, HEIGHT);
			panel.add(this);
			
			requestFocus();
			setFocusable(true);
			
			frame.setBounds(100, 100, 620, finalLinePos + 350);
			
			panel.setBounds(12, 13, 578, finalLinePos + 277);
			
			frame.setVisible(true);
			
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
						/*if(layers[i].isBinded() && layers[i].isSelected()) {
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
							/*if(layers[i].isBinded() && layers[i].isSelected()) {
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
				bind[i].addActionListener(new BindButton(i));
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
				BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
	}
	
	private long redraw() {
		
		long t = System.currentTimeMillis();
		
		update();
		
		draw();
		
		drawToScreen();
		
		
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
		g2.dispose();
	}

	public static BufferedImage generateThumbnail() {
		int genwidth = Integer.parseInt(width.getText());
		int genheight = Integer.parseInt(height.getText());
		// manipulate the width and height to specs
		
		BufferedImage resized = new BufferedImage(genwidth, genheight, BufferedImage.TYPE_INT_RGB);
		Graphics g = resized.createGraphics();
		g.drawImage(image, 0, 0, genwidth, genheight, null);
		g.dispose();
		return resized;
	}
	
	public static void generatePanel(String n) throws IOException {
		String user = System.getProperty("user.name");
		String location = "C:\\Users\\"+user+"\\Desktop\\";
		if(!FilesTab.getMediaFolder().equals("")) {
			location = FilesTab.getMediaFolder().replaceAll("/", "\\\\") + "\\"; 
		}
		int genwidth = Integer.parseInt(width.getText());
		int genheight = Integer.parseInt(height.getText());
		// manipulate the width and height to specs
		
		BufferedImage resized = new BufferedImage(genwidth, genheight, BufferedImage.TYPE_INT_RGB);
		Graphics g = resized.createGraphics();
		g.drawImage(image, 0, 0, genwidth, genheight, null);
		g.dispose();
		
		//print
		File outputfile = null;
		if(n == null || n == "")
			outputfile = new File(location + "\\test.png");
		else
			outputfile = new File(n);
		ImageIO.write(resized, "png", outputfile);

	}
	
	private void reupdateImages() {
		/* basically, this will update any changes thus
		 * if a char change happened, it will change ONLY if you are
		 * using root as your media center
		 */ 
		for(int i = 0; i < layers.length; i++) {
			if(layers[i] != null && layers[i].getFile() != null && layers[i].getImage() != null) {
				try {
					if(!layers[i].isReversed())
						if(layers[i].getFile().getName().contains(".txt")) {
							layers[i].setImage(convertTextToImage(layers[i].getFile()));
						} else
							layers[i].setImage(ImageIO.read(layers[i].getFile()));
					else {
						reverseImage(i);
					}
				} catch (Exception e) {
					// image is loading, be patient
				}
			}
		}
	}
	
	// really simple
	public static BufferedImage convertTextToImage(File f) {
		try {
			int tmpy = 0;
			BufferedImage tmp = new BufferedImage(WIDTH - 50, HEIGHT, BufferedImage.TYPE_INT_RGB);
			Graphics gx = tmp.createGraphics();
			gx.setColor(Color.white);
			gx.setFont(customFont);
			String line = null;
			BufferedReader reader = new BufferedReader(new FileReader(f));
			while((line = reader.readLine()) != null) {
				gx.drawString(line, 0, (tmpy += gx.getFontMetrics().getHeight()));
			}
			reader.close();
			return tmp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void reverseImage(int i) {
		BufferedImage tmp = new BufferedImage(
				layers[i].getImage().getWidth(),
				layers[i].getImage().getHeight(),
				BufferedImage.TYPE_INT_RGB);
		Graphics gx = tmp.createGraphics();
		try {
			if(layers[i].getFile().getName().contains(".txt")) {
				layers[i].setImage(convertTextToImage(layers[i].getFile()));
			} else
				layers[i].setImage(ImageIO.read(layers[i].getFile()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gx.drawImage(
				layers[i].getImage(), 
				layers[i].getX() + layers[i].getImage().getWidth(), 
				layers[i].getY(), -layers[i].getImage().getWidth(), 
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
						return;
					} else {
						layers[i].setReversed(true);
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

	private class BindButton implements ActionListener {

		private int pos = 0;
		
		public BindButton(int i) {
			pos = i;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(layers[pos].getImage() != null) {
				if(layers[pos].isBinded()) {
					bind[pos].setText("Bind");
					layers[pos].setBinded(false);
				} else {
					bind[pos].setText("Unbind");
					layers[pos].setBinded(true);
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
							layers[pos].setImage(convertTextToImage(layers[pos].getFile()));
						} else
							layers[pos].setImage(ImageIO.read(layers[pos].getFile()));
					else {
						reverseImage(pos);
					}
					layers[pos].setX(0);
					layers[pos].setY(0);
					layers[pos].setWidth((int) (layers[pos].getImage().getWidth() * multix));
					layers[pos].setHeight((int) (layers[pos].getImage().getHeight() * multiy));
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
