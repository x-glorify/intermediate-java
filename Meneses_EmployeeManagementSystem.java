package CCE107Activities;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.*;
import java.io.*;
import java.awt.*;
public class Meneses_EmployeeManagementSystem extends JFrame{

	JTextField txtID, txtname, txtbirth, txtage, txtnationality, 
			   txtnumber, txtemail, txtdep, txtposition;
	
	String empID, name, birth, age, civilstat,
		   nationality, gender, number, email,
		   department, position;
	
	static final String FILE_NAME = "EmployeeManagementSys.txt";
	static final String DELIMITER = "#";
	
	
	DefaultTableModel model;
	JTable table;
	JComboBox <String> comcivilstat;
	ButtonGroup bg;
	JScrollPane scrollPane;
	ArrayList <String> lines;
	
	Meneses_EmployeeManagementSystem() {
		
		
			JLabel lblID = new JLabel("Employee ID");
			add(lblID).setBounds(20, 30, 100, 20);
			lblID.setForeground(Color.white);
			
			txtID = new JTextField();
			add(txtID).setBounds(20, 60, 150, 20);
			
			JLabel lblname = new JLabel("Full Name");
			add(lblname).setBounds(20, 100, 100, 20);
			lblname.setForeground(Color.white);
		
			txtname = new JTextField();
			add(txtname).setBounds(20, 130, 150, 20);
		
			JLabel lblbirth = new JLabel("Date of Birth");
			add(lblbirth).setBounds(20, 170, 100, 20);
			lblbirth.setForeground(Color.white);
		
			txtbirth = new JTextField();
			add(txtbirth).setBounds(20, 200, 150, 20);
		
		
		
			JLabel lblage = new JLabel("Age");
			add(lblage).setBounds(220, 30, 100, 20);
			lblage.setForeground(Color.white);
		
			txtage = new JTextField();
			add(txtage).setBounds(220, 60, 50, 20);
			
			JLabel lblstatus = new JLabel("Civil Status");
			add(lblstatus).setBounds(220, 100, 100, 20);
			lblstatus.setForeground(Color.white);
		
			comcivilstat = new JComboBox<>
						  (new String[]{"Single", "Married", "Widowed","Separated", "Divorced"});
			
			add(comcivilstat).setBounds(220, 130, 150, 20);
			comcivilstat.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			JLabel lblnationality = new JLabel("Nationality");
			add(lblnationality).setBounds(220, 170, 100, 20);
			lblnationality.setForeground(Color.white);
			
			txtnationality = new JTextField();
			add(txtnationality).setBounds(220, 200, 150, 20);
			
			
			
			JLabel lblgender = new JLabel("Gender");
			add(lblgender).setBounds(420, 30, 100, 20);
			lblgender.setForeground(Color.white);
			
			JRadioButton rdfemale = new JRadioButton("Female");
			add(rdfemale).setBounds(420, 60, 80, 20);
			rdfemale.setForeground(Color.white);
			rdfemale.setOpaque(false);
			rdfemale.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			JRadioButton rdmale = new JRadioButton("Male");
			add(rdmale).setBounds(500, 60, 80, 20);
			rdmale.setForeground(Color.white);
			rdmale.setOpaque(false);
			rdmale.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			
			bg = new ButtonGroup();
			bg.add(rdfemale);
			bg.add(rdmale);
			
			
			
			
			JLabel lblnumber = new JLabel("Contact Number");
			add(lblnumber).setBounds(420, 100, 100, 20);
			lblnumber.setForeground(Color.white);
			
			txtnumber = new JTextField();
			add(txtnumber).setBounds(420, 130, 150, 20);
			
			JLabel lblemail = new JLabel("E-mail");
			add(lblemail).setBounds(420, 170, 100, 20);
			lblemail.setForeground(Color.white);
			
			txtemail = new JTextField();
			add(txtemail).setBounds(420, 200, 150, 20);
			
			
			
			JLabel lbldep = new JLabel("Department");
			add(lbldep).setBounds(620, 30, 100, 20);
			lbldep.setForeground(Color.white);
			
			txtdep = new JTextField();
			add(txtdep).setBounds(620, 60, 150, 20);
			
			JLabel lblposition = new JLabel("Job Title/Position");
			add(lblposition).setBounds(620, 100, 100, 20);
			lblposition.setForeground(Color.white);
			
			txtposition = new JTextField();
			add(txtposition).setBounds(620, 130, 150, 20);
			
			JButton btnadd = new JButton("Add Employee");
			add(btnadd).setBounds(620, 160, 150, 30);
			btnadd.setForeground(Color.white);
			btnadd.setBackground(Color.black);
			btnadd.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			JButton btndelete = new JButton("Delete");
			add(btndelete).setBounds(620, 190, 150, 30);
			btndelete.setForeground(Color.white);
			btndelete.setBackground(Color.black);
			btndelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			JButton btnupdate = new JButton("Update");
			add(btnupdate).setBounds(620, 220, 150, 30);
			btnupdate.setForeground(Color.white);
			btnupdate.setBackground(Color.black);
			btnupdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			
			btnadd.addActionListener(e -> {
				empID = txtID.getText();
				name = txtname.getText();
				birth = txtbirth.getText();
				age = txtage.getText();
				civilstat = comcivilstat.getSelectedItem().toString();
				nationality = txtnationality.getText();
				
				if (bg.getSelection() == null) {
					JOptionPane.showMessageDialog(null, "Please select gender.");
					return;
				} 
				
				if (rdmale.isSelected()) {
					gender = "Male";
				} else if (rdfemale.isSelected()) {
					gender = "Female";
				} else {
					gender = "";
				}
				
				
				number = txtnumber.getText();
				email = txtemail.getText();
				department = txtdep.getText();
				position = txtposition.getText();
				
				
				
				recordData();
				loadTable();
				clearFields();
				
			});
			
			
			
			
			btndelete.addActionListener(e ->{
				deleteRecord();
				loadTable();
				clearFields();
				
			});
			
			
			
			btnupdate.addActionListener(e ->{
				
				empID = txtID.getText();
				name = txtname.getText();
				birth = txtbirth.getText();
				age = txtage.getText();
				civilstat = comcivilstat.getSelectedItem().toString();
				nationality = txtnationality.getText();
				
				if (bg.getSelection() == null) {
					JOptionPane.showMessageDialog(null, "Please select gender.");
					return;
				} 
				
				if (rdmale.isSelected()) {
					gender = "Male";
				} else if (rdfemale.isSelected()) {
					gender = "Female";
				} else {
					gender = "";
				}
				
				
				number = txtnumber.getText();
				email = txtemail.getText();
				department = txtdep.getText();
				position = txtposition.getText();
				
				
				
				updateRecord();
				loadTable();
				clearFields();
				
			});
			
			
			
			
			String[] cols = {"Employee ID", "Name", "Birthday", "Age", "Civil Status", "Nationality",
							"Gender", "Contact Number", "Email", "Department", "Job Title"};
			
			model  = new DefaultTableModel(cols, 0);
			table = new JTable(model);
			table.setOpaque(true);
			table.setFillsViewportHeight(true);
			table.setForeground(Color.white);
			table.setBackground(Color.decode("#000432"));
			table.getTableHeader().setBackground(Color.decode("#000432"));
			table.getTableHeader().setForeground(Color.white);
			
			
			
			scrollPane = new JScrollPane(table);
			scrollPane.setBounds(20, 250, 800, 200);
			add(scrollPane);
			loadTable();
			
			// fills the text fields from a selected row
			table.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e) {
					int row = table.getSelectedRow();
					if (row != -1) {
						txtID.setText(model.getValueAt(row,  0).toString());
						txtname.setText(model.getValueAt(row,  1).toString());
						txtbirth.setText(model.getValueAt(row,  2).toString());
						txtage.setText(model.getValueAt(row,  3).toString());
						
						comcivilstat.setSelectedItem(model.getValueAt(row, 4).toString());
						txtnationality.setText(model.getValueAt(row,  5).toString());
						
						gender = model.getValueAt(row,  6).toString();
						if (gender.equals("Female")) {
							rdfemale.setSelected(true);
						} else {
							rdmale.setSelected(true);
						}
						
						
						txtnumber.setText(model.getValueAt(row,  7).toString());
						txtemail.setText(model.getValueAt(row,  8).toString());
						txtdep.setText(model.getValueAt(row,  9).toString());
						txtposition.setText(model.getValueAt(row,  10).toString());
					}
				}
				
			});
		
		
		getContentPane().setBackground(Color.decode("#000432"));
		setTitle("Employee Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(855, 520);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	void recordData() {
		try {
			FileWriter fw = new FileWriter(FILE_NAME, true);
			
			fw.write(empID + DELIMITER + name + DELIMITER + birth + DELIMITER + age + DELIMITER 
					+ civilstat + DELIMITER + nationality + DELIMITER + gender + DELIMITER + number
					+ DELIMITER + email + DELIMITER + department + DELIMITER + position + "\n");
			
			
			fw.close();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error saving " + e.getMessage());
		}
	}
	
	
	
	void deleteRecord() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(null, "Please select a data to delete.");
			return;
		}
		
		
		int confirm = JOptionPane.showConfirmDialog
					(null, 
					"Are you sure you want to delete this record?",
					"Confirm delete",
					JOptionPane.YES_NO_OPTION);
		
		if (confirm != JOptionPane.YES_OPTION) {
			return;
		}
		
		
		//deletion
		lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
			
			String line;
			int rowIndex = 0;
			
			while((line = br.readLine()) != null) {
				if (rowIndex != selectedRow) lines.add(line);
				rowIndex++;
			}
			
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
			
			for (String record : lines) {
				bw.write(record + "\n");
				
			}
			
			bw.close();
			
			JOptionPane.showMessageDialog(null, "Record deleted.");
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, x);
		}
		
		
		
		
	}
	
	
	void updateRecord() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(null, "Select a record to update.");
			return;
		}
		
		lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
			
			String line;
			int rowIndex = 0;
			
			while((line = br.readLine()) != null) {
				if (rowIndex == selectedRow) {
					String updateRecord = empID + DELIMITER + name + DELIMITER + birth + DELIMITER + age + DELIMITER 
							+ civilstat + DELIMITER + nationality + DELIMITER + gender + DELIMITER + number
							+ DELIMITER + email + DELIMITER + department + DELIMITER + position + "\n";
							
					lines.add(updateRecord);		
							
							
				} else {
					lines.add(line);
				}
				rowIndex++;
					
			}
			
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
			
			for (String record : lines) {
				bw.write(record + "\n");
				
			}
			
			bw.close();
			
			JOptionPane.showMessageDialog(null, "Record updated.");
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, x);
		}
		
		
		
		
		
	}
	
	
	
	void loadTable() {
		model.setRowCount(0);
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
			while ((line = br.readLine()) != null) {
				String[] row = line.split(DELIMITER);
				model.addRow(row);
			}
			
		} catch (IOException e) {
		
		}
	}
	
	void clearFields() {
		
		txtID.setText("");
		txtname.setText("");
		txtbirth.setText("");
		txtage.setText("");
		txtnationality.setText("");
		bg.clearSelection();
		txtnumber.setText("");
		txtemail.setText("");
		txtdep.setText("");
		txtposition.setText("");
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		new Meneses_EmployeeManagementSystem();
		

	}

}
