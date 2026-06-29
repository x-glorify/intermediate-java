package ExitExamPrac;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class PatientAppointmentSystem extends JFrame{

	private final String DELIMITER = "::";
	private final String FILENAME = "PatientAppointment.txt";
	private JComboBox<String> comdept;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scroll;
	private ButtonGroup bg;
	private ArrayList<String> records;
	private JRadioButton rb1, rb2;
	
	private JTextField txtpatientID, txtpatientname, txtage, txtcontactnum, txtdoctor, txtdate;
	private String name, contactnum, doctor, date, dept, status;
	private int patientID, age;
	
	
	PatientAppointmentSystem() {
		
		String[] cols =
			{"Patient ID", "Name", "Age", "Contact Number", "Doctor", "Date", "Department", "Status"};
		
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		scroll = new JScrollPane(table);
		add(scroll).setBounds(30, 30, 980, 300);
		refreshTable();
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					txtpatientID.setText(model.getValueAt(row, 0).toString());
					txtpatientname.setText(model.getValueAt(row, 1).toString());
					txtage.setText(model.getValueAt(row, 2).toString());
					txtcontactnum.setText(model.getValueAt(row, 3).toString());
					txtdoctor.setText(model.getValueAt(row, 4).toString());
					txtdate.setText(model.getValueAt(row, 5).toString());
					comdept.setSelectedItem(model.getValueAt(row, 6).toString());
					
					status = model.getValueAt(row, 7).toString();
					if (status.equals("Scheduled")) {
						rb1.setSelected(true);
					} else {
						rb2.setSelected(true);
					}
					
					
				}
			}
		});
		
		
		//==========Labels, Text Fields, Radio Buttons, Combo Box ==================
		
		JLabel lblpatientID = new JLabel("Patient ID");
		JLabel lblpatientname = new JLabel("Name");
		JLabel lblage = new JLabel("Age");
		JLabel lblcontactnum = new JLabel("Contact Number");
		JLabel lbldoctor = new JLabel("Doctor");
		JLabel lbldate = new JLabel("Date");
		JLabel lbldept = new JLabel("Department");
		JLabel lblstatus = new JLabel("Status");
		
		add(lblpatientID).setBounds(30, 330, 100, 100);
		add(lblpatientname).setBounds(160, 330, 100, 100);
		add(lblage).setBounds(290, 330, 100, 100);
		add(lblcontactnum).setBounds(420, 330, 100, 100);
		add(lbldoctor).setBounds(550, 330, 100, 100);
		add(lbldate).setBounds(680, 330, 100, 100);
		add(lbldept).setBounds(810, 330, 100, 100);
		add(lblstatus).setBounds(940, 330, 100, 100);
		
		txtpatientID = new JTextField();
		txtpatientname = new JTextField();
		txtage = new JTextField();
		txtcontactnum = new JTextField();
		txtdoctor = new JTextField();
		txtdate = new JTextField();
		
		add(txtpatientID).setBounds(30, 400, 100, 20);
		add(txtpatientname).setBounds(160, 400, 100, 20);
		add(txtage).setBounds(290, 400, 100, 20);
		add(txtcontactnum).setBounds(420, 400, 100, 20);
		add(txtdoctor).setBounds(550, 400, 100, 20);
		add(txtdate).setBounds(680, 400, 100, 20);
		
		txtage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char z = e.getKeyChar();
				if (!Character.isDigit(z)) e.consume();
			}
		});
		
		
		txtpatientname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char x = e.getKeyChar();
				if (Character.isDigit(x)) e.consume();
			}
		});
		
		
		txtcontactnum.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char y = e.getKeyChar();
				if (!Character.isDigit(y)) e.consume();
			}
		});
		
		
		rb1 = new JRadioButton("Scheduled");
		rb2 = new JRadioButton("Completed");
		
		add(rb1).setBounds(920, 400, 100, 30);
		add(rb2).setBounds(920, 430, 100, 30);
		bg = new ButtonGroup();
		bg.add(rb1);
		bg.add(rb2);		
		
		
		comdept = new JComboBox<>
				(new String[] {"Cardiology", "Dermatology", "Neurology", "Orthopedics", "Pediatrics"});
		add(comdept).setBounds(810, 400, 100, 20);
		
		
		
		// ==============JButtons========================
		
		JButton btnadd = new JButton("Add");
		JButton btnupdate = new JButton("Update");
		JButton btndelete = new JButton("Delete");
		JButton btnclear = new JButton("Clear");
		
		add(btnadd).setBounds(300, 480, 100, 30);
		add(btnupdate).setBounds(400, 480, 100, 30);
		add(btndelete).setBounds(500, 480, 100, 30);
		add(btnclear).setBounds(600, 480, 100, 30);
		
		
		btnadd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				 if (txtpatientID.getText().isEmpty() || txtpatientname.getText().isEmpty() ||
				            txtage.getText().isEmpty() || txtcontactnum.getText().isEmpty() ||
				            txtdoctor.getText().isEmpty() || txtdate.getText().isEmpty()) {
				            JOptionPane.showMessageDialog(null, "Please fill in all fields!");
				            return;
				 }
				
				
				patientID = Integer.parseInt(txtpatientID.getText());
				name = txtpatientname.getText();
				age = Integer.parseInt(txtage.getText());
				contactnum = txtcontactnum.getText();
				doctor = txtdoctor.getText();
				date = txtdate.getText();
				dept = comdept.getSelectedItem().toString();
				
				if (bg.getSelection() == null) {
					JOptionPane.showMessageDialog(null, "Select appointment status.");
					return;
				}
				
				
				if (rb1.isSelected()) {
					status = "Scheduled";
				} else {
					status = "Completed";
				}
				
				
				addRecord();
				refreshTable();
				
				
				}
			
			
		});
		
		
		
		btnupdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				 if (txtpatientID.getText().isEmpty() || txtpatientname.getText().isEmpty() ||
				            txtage.getText().isEmpty() || txtcontactnum.getText().isEmpty() ||
				            txtdoctor.getText().isEmpty() || txtdate.getText().isEmpty()) {
				            JOptionPane.showMessageDialog(null, "Please fill in all fields!");
				            return;
				 }
				
				patientID = Integer.parseInt(txtpatientID.getText());
				name = txtpatientname.getText();
				age = Integer.parseInt(txtage.getText());
				contactnum = txtcontactnum.getText();
				doctor = txtdoctor.getText();
				date = txtdate.getText();
				dept = comdept.getSelectedItem().toString();
				
				if (bg.getSelection() == null) {
					JOptionPane.showMessageDialog(null, "Select appointment status.");
					return;
				}
				
				
				if (rb1.isSelected()) {
					status = "Scheduled";
				} else {
					status = "Completed";
				}
				
				
				updateRecord();
				refreshTable();
				
			}
			
			
		});
		
		
		btndelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				deleteRecord();
				refreshTable();
			}
			
		});
		
		
		
		btnclear.addActionListener(e->{
			txtpatientID.setText("");
			txtpatientname.setText("");
			txtage.setText("");
			txtcontactnum.setText("");
			txtdoctor.setText("");
			txtdate.setText("");
			bg.clearSelection();
			comdept.setSelectedIndex(0);
			
		});
		
	
		
		
		
		
		
		
		
		setTitle("Patient Appointment System");
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1050, 580);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	
	
	
	
	
	//=================METHODS======================================
	
	
	
	
	private void addRecord() {
		try (FileWriter fw  = new FileWriter(FILENAME, true)) {
			
			fw.write(patientID + DELIMITER + name + DELIMITER + age + DELIMITER +
					contactnum + DELIMITER + doctor + DELIMITER + date + DELIMITER +
					dept + DELIMITER + status + "\n");
			fw.close();
			
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	
	private void updateRecord() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow ==  -1) {
			JOptionPane.showMessageDialog(null, "Choose a record to update.");
			return;
		}
		
		records = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowIndex = 0;
			
			while ((line = br.readLine()) != null) {
				if (rowIndex == selectedRow) {
					String updatedInfo = patientID + DELIMITER + name + DELIMITER + age + DELIMITER +
							contactnum + DELIMITER + doctor + DELIMITER + date + DELIMITER +
							dept + DELIMITER + status;
					records.add(updatedInfo);
				} else {
					records.add(line);
				}
				rowIndex++;
			}
			
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, x);
		}
		
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
			for (String record : records) bw.write(record + "\n");
			bw.close();
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, z);
		}
		
		
	}
	
	
	
	private void deleteRecord() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(null, "Select a record to delete.");
			return;
		}
		
		int choice = JOptionPane.showConfirmDialog(null,
					"Are you sure you want tp delete this record?",
					"Delete?",
					JOptionPane.YES_NO_OPTION);
		
		if (choice != JOptionPane.YES_OPTION) return;
		
		
		records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowIndex = 0;
			while((line = br.readLine()) != null) {
				if (rowIndex != selectedRow) {
					records.add(line);
				}
				rowIndex++;
			}
			
			
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, x);
		}
		
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
			for (String record : records) bw.write(record + "\n"); 
			bw.close();
			
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, z);
		}
		
	}
	
	
	private void refreshTable() {
		model.setRowCount(0);
		
		File file = new File(FILENAME);
		if (!file.exists()) return;
		
		try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] rows = line.split(DELIMITER);
				model.addRow(rows);
			}
			
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
	new PatientAppointmentSystem();

	}

}
