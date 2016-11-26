package streamupdater.gui.components.render;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class RenderList {
	
	public RenderObject ro;
	public VideoHandler video;
	
	private JPanel columnpanel;
	
	private JFrame frame;
	private JPanel[] panel;
	private JLabel[] description;
	private JButton[] remove;
	private JButton[] render;
	
	private int gap = 5;
	
	public RenderList(VideoHandler video, RenderObject ros) {
		
		this.video = video;
		this.ro = ros;
		
		int size = ro.getDurations().size();
		
		panel = new JPanel[size];
		description = new JLabel[size];
		remove = new JButton[size];
		render = new JButton[size];
		
		frame = new JFrame();
		
		frame.setTitle("Rendering List");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 640, 480);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(10, 10, 604, 360);
		frame.getContentPane().add(scroll);
		
		JPanel borderlayoutpanel = new JPanel();
        scroll.setViewportView(borderlayoutpanel);
        borderlayoutpanel.setLayout(new BorderLayout(0, 0));

        JButton renderPanel = new JButton("Render");
        renderPanel.setToolTipText("<html>Rendering during your stream may kill cpu usage, I advise not to do so.<br>Also this is very time consuming, save the object and come back later after the stream is done.</html>");
       	renderPanel.setBounds(10, 400, 604, 30);
       	renderPanel.setEnabled(false);
       	frame.add(renderPanel);
       	
       	JButton renderImages = new JButton("Create Thumbnails");
       	renderImages.setToolTipText("I advise doing this before saving the object, issues are still a problem for images");
       	renderImages.setBounds(414, 370, 200, 30);
       	frame.add(renderImages);
       	renderImages.setEnabled(true);
       	
       	JButton convertPanel = new JButton("Convert to MP4");
       	convertPanel.setToolTipText("Convert before rendering... errors will occur if not");
       	convertPanel.setBounds(10, 370, 404, 30);
       	frame.add(convertPanel);
        
        columnpanel = new JPanel();
        borderlayoutpanel.add(columnpanel, BorderLayout.NORTH);
        columnpanel.setLayout(new GridLayout(0, 1, 0, 1));
        columnpanel.setBackground(Color.gray);
		
        for(int i = 0; i < size; i++) {
        	// build panel
        	panel[i] = new JPanel();
        	panel[i].setPreferredSize(new Dimension(300,30));
        	panel[i].setBorder(BorderFactory.createLineBorder(Color.gray));
            columnpanel.add(panel[i]);
            panel[i].setLayout(null);
            // build remove
            remove[i] = new JButton("Remove");
            remove[i].setFont(new Font("Dialog", Font.PLAIN, 12));
            remove[i].setBounds(485, 5, 89, 23);
            remove[i].addActionListener(new Remove(i));
            panel[i].add(remove[i]);
            // build render
            render[i] = new JButton("Render");
            render[i].setFont(new Font("Dialog", Font.PLAIN, 12));
            render[i].setBounds(386, 5, 89, 23);
            render[i].addActionListener(new Render(i));
            panel[i].add(render[i]);
            // build label
            description[i] = new JLabel("");
            description[i].setText(ro.getFileNames().get(i));
			description[i].setFont(new Font("Dialog", Font.PLAIN, 12));
			description[i].setBounds(10, 5, 366, 23);
			panel[i].add(description[i]);
        }
        frame.setVisible(true);
        
        renderImages.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				RenderingEngine re = new RenderingEngine();
				re.setObject(ro);
				re.renderImages(video);
			}
        	
        });
        
        convertPanel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				video.setVideoInput(ro.getStreamURL());
				video.convertToMp4();
			}
        	
        });
        
        renderPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RenderingEngine re = new RenderingEngine();
				re.setObject(ro);
				re.renderProject(video);
			}
        	
        });
		
	}
	// this may cause issues with the re.removePartObject. Probably will
	private class Remove implements ActionListener {
		
		int pos = 0;
		
		public Remove(int pos) {
			this.pos = pos;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			RenderingEngine re = new RenderingEngine();
			re.setObject(ro);
			re.removePartObject(pos);
			frame.setVisible(false);
			RenderList rl = new RenderList(video, ro);
			frame.dispose();
		}
		
	}
	
	private class Render implements ActionListener {
		
		int pos = 0;
		
		public Render(int i) {
			pos = i;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String tmp = ro.getStreamURL().replace("flv", "mp4");
			if(!new File(tmp).exists()) {
				JOptionPane.showMessageDialog(null, "MP4 not detected! Please click Convert to MP4 after stream has finished!");
				/* currently in development
				System.out.println("New override method for rendering");
				RenderingEngine re = new RenderingEngine();
				re.setObject(ro);
				re.forceRenderProject(video, pos);
				re.removePartObject(pos);
				frame.setVisible(false);
				RenderList rl = new RenderList(video, ro);
				frame.dispose();*/
				return;
			}
			RenderingEngine re = new RenderingEngine();
			re.setObject(ro);
			re.renderProject(video, pos);
			re.removePartObject(pos);
			frame.setVisible(false);
			RenderList rl = new RenderList(video, ro);
			frame.dispose();
		}
		
	}

}
