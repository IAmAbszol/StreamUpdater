package streamupdater.util;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.sun.prism.paint.Color;

public class TextEditor {
	
	private boolean completed = false;
	private int size = 24;
	private int width = 200;
	private int height = size * 2;
	private int r = 255;
	private int g = 255;
	private int b = 255;
	private int[] color = { r, g, b };
	private boolean bold = false;
	private boolean italic = false;
	private boolean underlined = false;
	private String font = "Arial";
	
	private JFrame f;
	
	public TextEditor(int s) {
		f = new JFrame("Text Editor - Layer " + s);
		f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		f.setBounds(100, 100, 250, 365);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		f.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 214, 310);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblCustomText = new JLabel("Custom Text");
		lblCustomText.setHorizontalAlignment(SwingConstants.CENTER);
		lblCustomText.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCustomText.setBounds(10, 11, 168, 30);
		panel.add(lblCustomText);
		
		JLabel lblFont = new JLabel("Font");
		lblFont.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFont.setBounds(10, 52, 46, 14);
		panel.add(lblFont);
		
		String[] systemFont = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		
		JComboBox fontBox = new JComboBox(systemFont);
		fontBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fontBox.setBounds(66, 51, 126, 20);
		panel.add(fontBox);
		
		JLabel lblSize = new JLabel("Size");
		lblSize.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSize.setBounds(10, 77, 46, 14);
		panel.add(lblSize);
		
		JSpinner sizes = new JSpinner();
		sizes.setValue(size);
		sizes.setBounds(66, 76, 72, 20);
		panel.add(sizes);
		
		JLabel lblColor = new JLabel("Color");
		lblColor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblColor.setBounds(10, 102, 46, 14);
		panel.add(lblColor);
		
		JSpinner red = new JSpinner();
		red.setValue(r);
		red.setToolTipText("Red");
		red.setFont(new Font("Tahoma", Font.PLAIN, 14));
		red.setBounds(65, 101, 50, 20);
		panel.add(red);
		
		JSpinner green = new JSpinner();
		green.setValue(g);
		green.setToolTipText("Green");
		green.setFont(new Font("Tahoma", Font.PLAIN, 14));
		green.setBounds(110, 101, 50, 20);
		panel.add(green);
		
		JSpinner blue = new JSpinner();
		blue.setValue(b);
		blue.setToolTipText("Blue");
		blue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		blue.setBounds(155, 101, 50, 20);
		panel.add(blue);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(10, 277, 168, 23);
		panel.add(btnSave);
		
		JCheckBox bolds = new JCheckBox("Bold");
		bolds.setSelected(true);
		bolds.setFont(new Font("Tahoma", Font.PLAIN, 10));
		bolds.setBounds(10, 195, 97, 23);
		panel.add(bolds);
		
		JCheckBox italics = new JCheckBox("Italic");
		italics.setFont(new Font("Tahoma", Font.PLAIN, 10));
		italics.setBounds(10, 221, 97, 23);
		panel.add(italics);
		
		JCheckBox underlines = new JCheckBox("Underline");
		underlines.setFont(new Font("Tahoma", Font.PLAIN, 10));
		underlines.setBounds(10, 247, 97, 23);
		panel.add(underlines);
		
		JLabel lblNewLabel = new JLabel("Width & Height");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 127, 168, 30);
		panel.add(lblNewLabel);
		
		JSpinner widths = new JSpinner();
		widths.setValue(width);
		widths.setBounds(10, 168, 75, 20);
		panel.add(widths);
		
		JSpinner heights = new JSpinner();
		heights.setValue(height);
		heights.setBounds(103, 168, 75, 20);
		panel.add(heights);
		
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				font = (String) fontBox.getSelectedItem();
				size = (int) sizes.getValue();
				width = (int) widths.getValue();
				height = (int) heights.getValue();
				color[0] = (int) red.getValue();
				color[1] = (int) green.getValue();
				color[2] = (int) blue.getValue();
				bold = bolds.isSelected();
				italic = italics.isSelected();
				underlined = underlines.isSelected();
				completed = true;
			}
			
		});
		
		f.setResizable(false);
		f.setVisible(true);
	}
	
	public boolean isComplete() {
		return completed;
	}
	
	public String getFont() {
		return font;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int[] getColor() {
		return color;
	}
	
	public boolean isBold() {
		return bold;
	}
	
	public boolean isItalic() {
		return italic;
	}
	
	public boolean isUnderlined() {
		return underlined;
	}
	
	public JFrame getFrame() {
		return f;
	}
	
}
