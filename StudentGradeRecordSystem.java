package Practice107;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
public class StudentGradeRecordSystem extends JFrame{

	private String filename = "GradeRecords.txt";
	String DELIMITER = ":::";
	private JTextField txtstudentID, txtname, txtprogram, txtenglish,
						txtmath, txtscience, txtavg, txtmark;
	
	private String studentID, name, program, mark;
	private int english, math, science, average;
	JTable table;
	DefaultTableModel model;
	JScrollPane scroll;
	ArrayList <String> temprecords;
	
	StudentGradeRecordSystem() {
		
		JLabel lblstudentID = new JLabel("Student ID");
		add(lblstudentID).setBounds(30, 30, 100, 20);
		
		txtstudentID = new JTextField();
		add(txtstudentID).setBounds(30, 60, 150, 20);
		
		JLabel lblname = new JLabel("Name");
		add(lblname).setBounds(30, 110, 100, 20);
		
		txtname = new JTextField();
		add(txtname).setBounds(30, 140, 150, 20);
		
		JLabel lblprogram = new JLabel("Program");
		add(lblprogram).setBounds(30, 190, 100, 20);
		
		txtprogram = new JTextField();
		add(txtprogram).setBounds(30, 220, 150, 20);
		
		
		
		//grade textfields
		
		JLabel lblenglish = new JLabel("English Grade:");
		add(lblenglish).setBounds(250, 60, 100, 20);
		
		txtenglish = new JTextField();
		add(txtenglish).setBounds(350, 60, 60, 20);
		
		JLabel lblmath = new JLabel("Math Grade:");
		add(lblmath).setBounds(250, 120, 100, 20);
		
		txtmath = new JTextField();
		add(txtmath).setBounds(350, 120, 60, 20);
		
		JLabel lblscience = new JLabel("Science Grade:");
		add(lblscience).setBounds(250, 180, 100, 20);
		
		txtscience = new JTextField();
		add(txtscience).setBounds(350, 180, 60, 20);
		
		
		
		// result textfields
		
		JLabel lblavg = new JLabel("Average Grade");
		add(lblavg).setBounds(500, 60, 100, 20);
		
		txtavg = new JTextField();
		add(txtavg).setBounds(500, 90, 50, 20);
		txtavg.setEditable(false);
		
		JLabel lblmark = new JLabel("Remark");
		add(lblmark).setBounds(500, 120, 100, 20);
		
		txtmark = new JTextField();
		add(txtmark).setBounds(500, 150, 100, 20);
		txtmark.setEditable(false);
		
		// buttons
		
		JButton btnsubmit = new JButton("Submit");
		add(btnsubmit).setBounds(250, 240, 100, 20);
		
		JButton btndelete = new JButton("Delete");
		add(btndelete).setBounds(380, 240, 100, 20);
		
		JButton btnupdate = new JButton("Update");
		add(btnupdate).setBounds(510, 240, 100, 20);

		JButton btnclear = new JButton("Clear");
		add(btnclear).setBounds(640, 240, 100, 20);
		
		
		// button functions
		
		btnsubmit.addActionListener(e -> {
			studentID = txtstudentID.getText();
			name = txtname.getText();
			program = txtprogram.getText();
			english = Integer.parseInt(txtenglish.getText());
			math = Integer.parseInt(txtmath.getText());
			science = Integer.parseInt(txtscience.getText());
			
			int gradetotal = english + math + science;
			average = gradetotal/3;
			
			if (average >= 75) {
				mark = "PASSED";
			} else {
				mark = "FAILED";
			}
			
			txtavg.setText(String.valueOf(average));
			txtmark.setText(String.valueOf(mark));
			
			submit();
			loadtable();
		});
		
		
		
		btndelete.addActionListener(e -> {
			delete();
			clear();
			loadtable();
		});
		
		
		
		btnupdate.addActionListener(e -> {
			
			studentID = txtstudentID.getText();
			name = txtname.getText();
			program = txtprogram.getText();
			english = Integer.parseInt(txtenglish.getText());
			math = Integer.parseInt(txtmath.getText());
			science = Integer.parseInt(txtscience.getText());
			
			int gradetotal = english + math + science;
			average = gradetotal/3;
			
			txtavg.setText(String.valueOf(average));
			
			if (average >= 75) {
				mark = "PASSED";
			} else {
				mark = "FAILED";
			}
			
			txtmark.setText(String.valueOf(mark));
			
		});
		
		
		
		btnclear.addActionListener(e ->{
			clear();
		});
		
		
		
		//table
		
		String[] rows = {"Student ID", "Name", "Program", "English Grade", "Math Grade",
						"Science Grade", "Average", "Mark"};
		
		model = new DefaultTableModel(rows, 0);
		table = new JTable(model);
		scroll = new JScrollPane(table);
		scroll.setBounds(30, 300, 800, 300);
		add(scroll);
		loadtable();
		
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selectedrow = table.getSelectedRow();
				
				if (selectedrow != -1) {
					txtstudentID.setText(model.getValueAt(selectedrow, 0).toString());
					txtname.setText(model.getValueAt(selectedrow, 1).toString());
					txtprogram.setText(model.getValueAt(selectedrow, 2).toString());
					
					txtenglish.setText(model.getValueAt(selectedrow, 3).toString());
					txtmath.setText(model.getValueAt(selectedrow, 4).toString());
					txtscience.setText(model.getValueAt(selectedrow, 5).toString());
					
					txtavg.setText(model.getValueAt(selectedrow, 6).toString());
					txtmark.setText(model.getValueAt(selectedrow, 7).toString());
				}
				
				
			}
		});
		
		
		
		
		setTitle("Student Grade Record System");
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 500);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	
	
	
	
	void submit() {
		try (FileWriter fw = new FileWriter(filename, true)) {
			
			fw.write(studentID + DELIMITER + name + DELIMITER + program +
					DELIMITER + english + DELIMITER + math + DELIMITER +
					science + DELIMITER + average + DELIMITER + mark +"\n");
			fw.close();
			
			
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	void delete() {
		int selectedrow = table.getSelectedRow();
		
		if (selectedrow == -1) {
			JOptionPane.showMessageDialog(null, "Select a record to delete.");
			return;
		}
		
		int confirm = JOptionPane.showConfirmDialog(null, "Delete record?", "Confirm?", JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION) return;
		
		
		
		
		temprecords = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			
			String line;
			int rowIndex = 0;
			
			while ((line = br.readLine()) != null) {
				if (rowIndex != selectedrow) {
					temprecords.add(line);
					rowIndex++;
				}
			}
			
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, x);
		}
		
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
			
			for (String record:temprecords) {
				bw.write(record + "\n");
			}
			
			bw.close();
			
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, z);
		}
		
		
	}
	
	
	void update() {
		int selectedrow = table.getSelectedRow();
		
		if (selectedrow == -1) {
			JOptionPane.showMessageDialog(null, "Select a record to update.");
			return;
		}
		
		temprecords = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			int rowindex = 0;
			while((line = br.readLine()) != null) {
				if (rowindex == selectedrow) {
					String updatedinfo = studentID + DELIMITER + name + DELIMITER + program + DELIMITER + 
										english + DELIMITER + math + DELIMITER + science + DELIMITER +
										average + DELIMITER + mark;
					
					temprecords.add(updatedinfo);
				}
				rowindex++;
			}
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, x);
		}
		
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
			
			for (String record:temprecords) {
				bw.write(record + "\n");
			}
			
			bw.close();
			
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, z);
		}
		
		
		
		
		
		
	}
	
	
	
	void loadtable() {
		model.setRowCount(0);
		
		File file = new File(filename);
		if (!file.exists()) return;
	
	
	
	try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
		String line;
		while((line = br.readLine()) != null) {
			String[] row = line.split(DELIMITER);
			
			model.addRow(row);
		}
	} catch (IOException e) {
		JOptionPane.showMessageDialog(null, e);
	}
	
	
	
	}
	
	
	void clear() {
		txtstudentID.setText("");
		txtname.setText("");
		txtprogram.setText("");
		txtenglish.setText("");
		txtmath.setText("");
		txtscience.setText("");
		txtavg.setText("");
		txtmark.setText("");
	}
	
	
	
	
	
	public static void main(String[] args) {
	new StudentGradeRecordSystem();

	}

}
