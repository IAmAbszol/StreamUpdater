package streamupdater.util;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import streamupdater.gui.components.TournamentEnlisterTab;

public class ExcelSheetCollection extends JFrame {
	
	private JFileChooser jfc;
	
	private String path = "";
	
	public ExcelSheetCollection() {
		
		setTitle("Excel Sheet Collection");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 200);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 13, 408, 150);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Excel Sheet Collection");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(12, 13, 384, 30);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tag Column");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(12, 56, 175, 30);
		panel.add(lblNewLabel_1);
		
		JSpinner tagColumn = new JSpinner();
		tagColumn.setBounds(199, 61, 50, 22);
		panel.add(tagColumn);
		
		JLabel lblNewLabel_2 = new JLabel("Info Column");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(12, 99, 175, 30);
		panel.add(lblNewLabel_2);
		
		JSpinner infoColumn = new JSpinner();
		infoColumn.setBounds(199, 104, 50, 22);
		panel.add(infoColumn);
		
		JButton launch = new JButton("GO");
		launch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		launch.setBounds(299, 56, 97, 75);
		panel.add(launch);
		
		launch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				jfc = new JFileChooser();
				jfc.setCurrentDirectory(new java.io.File("user.home"));
				jfc.setDialogTitle("Please Select Your Excel Sheet");
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
					     "xlsx files (*.xlsx)", "xlsx");
				jfc.setFileFilter(filter);
				if (jfc.showOpenDialog(infoColumn) == JFileChooser.APPROVE_OPTION) {
					path = jfc.getSelectedFile().getAbsolutePath().replace("\\", "/");
				}
				setVisible(false);
				read(path, (int)tagColumn.getValue() - 1, (int)infoColumn.getValue() - 1);
			}
			
		});
		
		setVisible(true);
		
	}
	
	private void read(String n, int playerCol, int infoCol) {
		
		try {
			
			String excelFilePath = n;
	         
	        XSSFWorkbook workbook = new XSSFWorkbook(new File(excelFilePath));
	        XSSFSheet sheet = workbook.getSheetAt(0);

	        ArrayList<String> playerNames = null;
	        ArrayList<String> infoNames = null;

	        Iterator<Row> rowIterator = sheet.iterator();
	        playerNames = new ArrayList<>();
	        infoNames = new ArrayList<>();

	        while (rowIterator.hasNext()) {
	            Row row = rowIterator.next();
	            Iterator<Cell> cellIterator = row.cellIterator();
	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();

	                if (row.getRowNum() > 0) { // To filter column headings
	                    if (cell.getColumnIndex() == playerCol) {// To match column
	                                                        // index
	                        switch (cell.getCellType()) {
	                        case Cell.CELL_TYPE_STRING:
	                            playerNames.add(cell.getStringCellValue());
	                            break;
	                        }
	                    }
	                }
	            }
	        }
	        
	        workbook = new XSSFWorkbook(new File(excelFilePath));
	        sheet = workbook.getSheetAt(0);

	        rowIterator = sheet.iterator();
	        
	        while (rowIterator.hasNext()) {
	            Row row = rowIterator.next();
	            Iterator<Cell> cellIterator = row.cellIterator();
	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();

	                if (row.getRowNum() > 0) { // To filter column headings
	                    if (cell.getColumnIndex() == infoCol) {// To match column
	                                                        // index
	                        switch (cell.getCellType()) {
	                        case Cell.CELL_TYPE_STRING:
	                            infoNames.add(cell.getStringCellValue());
	                            break;
	                        }
	                    }
	                }
	            }
	        }
	        
	        for(int i = 0; i < playerNames.size(); i++) {
	        	TournamentEnlisterTab.updatePlayer(playerNames.get(i), infoNames.get(i));
	        }
	        
	        dispose();
        
		} catch (Exception e) {
			
		}
		
	}

}
