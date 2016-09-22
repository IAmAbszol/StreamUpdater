package streamupdater.gui.components.render;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.sun.glass.events.WindowEvent;

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
	
	public RenderList(VideoHandler video, RenderObject ro) {
		
		this.video = video;
		this.ro = ro;
		
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
		scroll.setBounds(10, 10, 604, 470);
		frame.getContentPane().add(scroll);
		
		JPanel borderlaoutpanel = new JPanel();
        scroll.setViewportView(borderlaoutpanel);
        borderlaoutpanel.setLayout(new BorderLayout(0, 0));

        columnpanel = new JPanel();
        borderlaoutpanel.add(columnpanel, BorderLayout.NORTH);
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
