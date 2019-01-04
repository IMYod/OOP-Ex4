package gui;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import gameData.SQLPull;

public class StaticsWindow extends JFrame implements Printer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JTable table;
	JScrollPane jsp;
	
	public StaticsWindow() {
		
		setTitle("Statics");
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setType(Type.POPUP);
	}

	@Override
	public void print(String text) {
		String[] headLine = SQLPull.getHeadline().split("\t");
		
		//split the data
		String[] rows = text.split("\n");
		String[][] data = new String[rows.length][headLine.length];
		for (int i=0; i<rows.length; i++)
			data[i] = rows[i].split("\t");
		
		//insert to table		
		table = new JTable(data, headLine);
		jsp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(jsp, BorderLayout.CENTER);
		
		//setSize
		setSize(800, 83+17*data.length);
		
		setVisible(true);
	}

}
