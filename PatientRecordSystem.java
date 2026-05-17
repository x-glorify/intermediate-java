package Practice107;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
public class PatientRecordSystem extends JFrame{

	
		final String DELIMITER = "::";
		final String filename = "PatientRecords.txt";
		JTextField txtpatientID, txtname, txtage, txtdiagnosis;
		String name, diagnosis, bloodtype, gender;
		int age, patientID;
		ButtonGroup bg;
		JComboBox<String> combloodtype;
		JTable table;
		JScrollPane scroll;
		DefaultTableModel model;
		ArrayList<String> temprecord;
		
		
		
	PatientRecordSystem() {
		
		JLabel lblpatientID = new JLabel("Patient ID");
		add(lblpatientID).setBounds(30, 30, 100, 20);
		
		txtpatientID = new JTextField();
		add(txtpatientID).setBounds(30, 60, 100, 20);
		
		
		txtpatientID.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				
				if (!Character.isDigit(c)) {
					e.consume();
				}
			}
		});
		
		
		
		
		JLabel lblname = new JLabel("Name");
		add(lblname).setBounds(30, 90, 100, 20);
		
		txtname = new JTextField();
		add(txtname).setBounds(30, 120, 150, 20);
		
		JLabel lblage = new JLabel("Age");
		add(lblage).setBounds(30, 150, 100, 20);
		
		txtage = new JTextField();
		add(txtage).setBounds(30, 180, 60, 20);
		
		
		txtage.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char d = e.getKeyChar();
				
				if (!Character.isDigit(d)) {
					e.consume();
				}
			}
		});
		
		
		JLabel lbldiagnosis = new JLabel("Diagnosis");
		add(lbldiagnosis).setBounds(30, 210, 100, 20);
		
		txtdiagnosis = new JTextField();
		add(txtdiagnosis).setBounds(30, 240, 200, 20);
		
		JLabel lblbloodtype = new JLabel("Blood Type");
		add(lblbloodtype).setBounds(30, 270, 100, 20);
		
		combloodtype = new JComboBox<>(new String[] {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"});
		add(combloodtype).setBounds(120, 270, 50, 20);
		
		
		//radio buttons
		
		JLabel lblgender = new JLabel("Gender");
		add(lblgender).setBounds(30, 300, 100, 20);
		
		JRadioButton rbfemale = new JRadioButton("Female");
		add(rbfemale).setBounds(30, 330, 80, 20);
		
		JRadioButton rbmale = new JRadioButton("Male");
		add(rbmale).setBounds(30, 360, 80, 20);
		
		bg = new ButtonGroup();
		bg.add(rbfemale);
		bg.add(rbmale);
		
		
		// buttons
		
		JButton btnadd = new JButton("Add");
		add(btnadd).setBounds(30, 420, 100, 30);
		
		JButton btndelete = new JButton("Delete");
		add(btndelete).setBounds(130, 420, 100, 30);
		
		JButton btnupdate = new JButton("Update");
		add(btnupdate).setBounds(30, 450, 100, 30);
		
		JButton btnclear = new JButton("Clear");
		add(btnclear).setBounds(130, 450, 100, 30);
		
		
		// button functions
		
		btnadd.addActionListener(e->{
			
			if (txtname.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Patient's name needed!", "Important!",
											JOptionPane.ERROR_MESSAGE);
			}
			
			patientID = Integer.parseInt(txtpatientID.getText());
			name = txtname.getText();
			age = Integer.parseInt(txtage.getText());
			bloodtype = combloodtype.getSelectedItem().toString();
			
			
			if (rbfemale.isSelected()) {
				gender = "Female";
			} else {
				gender = "Male";
			}
			
			
			diagnosis = txtdiagnosis.getText();
			
			
		
			add();
			reload();
			
		});
		
		
		
		btndelete.addActionListener(e->{
			delete();
			reload();
			
		});
		
		
		
		btnupdate.addActionListener(e->{
			
			if (txtname.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Patient's name needed!", "Important!",
											JOptionPane.ERROR_MESSAGE);
			}
			
			patientID = Integer.parseInt(txtpatientID.getText());
			name = txtname.getText();
			age = Integer.parseInt(txtage.getText());
			bloodtype = combloodtype.getSelectedItem().toString();
			
			
			if (rbfemale.isSelected()) {
				gender = "Female";
			} else {
				gender = "Male";
			}
			
			
			diagnosis = txtdiagnosis.getText();
			
			
			update();
			reload();
			
		});
		
		
		
		btnclear.addActionListener(e->{
			clear();
			
		});
		
		
		
		
		
		//table
		
		
		String[] cols = {"Patient ID", "Name", "Age", "Blood Type", "Gender", "Diagnosis"};
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		scroll = new JScrollPane(table);
		scroll.setBounds(250, 30, 600, 450);
		add(scroll);
		reload();
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				
				
				if (row != -1) {
					txtpatientID.setText(model.getValueAt(row, 0).toString());
					txtname.setText(model.getValueAt(row, 1).toString());
					txtage.setText(model.getValueAt(row, 2).toString());
					
					combloodtype.setSelectedItem(model.getValueAt(row, 3).toString());
					gender = model.getValueAt(row, 4).toString();

					
					if (rbfemale.isSelected()) {
						gender = "Female";
					} else {
						gender = "Male";
					}
					
					txtdiagnosis.setText(model.getValueAt(row, 5).toString());
					
						
				}
				
				
			}
		});
		
		
		
		
		setTitle("Patient Record System");
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 550);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	
		void add() {
			try (FileWriter fw = new FileWriter(filename, true)) {
				
				fw.write(patientID + DELIMITER + name + DELIMITER + age +
						DELIMITER + bloodtype + DELIMITER + gender + DELIMITER + diagnosis + "\n");
				
				
				fw.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	
	
	
		void delete() {
			int selectedrow = table.getSelectedRow();
			
			if (selectedrow == -1) {
				JOptionPane.showMessageDialog(null, "Select a record to delete.");
				return;
			}
			
			int confirm = JOptionPane.showConfirmDialog(null, "Delete patient record?", "Confirm deletion",
														JOptionPane.YES_NO_OPTION);
			
			if (confirm != JOptionPane.YES_OPTION) return;
			
			
			
			temprecord = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
				String line;
				int rowindex = 0;
				
				while ((line = br.readLine()) != null) {
					if (rowindex != selectedrow) {
						temprecord.add(line);
						rowindex++;
					}
				}
				
				
				
			} catch (IOException x) {
				JOptionPane.showMessageDialog(null, x);
			}
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
				
				
				for (String record : temprecord) {
					bw.write(record + "\n");
				}
			
				
			} catch (IOException z) {
				JOptionPane.showMessageDialog(null, z);
			}
			
			
			
			
			
			
			
			
		}
	
	
		void update() {
			
		int selectedrow = table.getSelectedRow();
			
			if (selectedrow == -1) {
				JOptionPane.showMessageDialog(null, "Select a record to delete.");
				return;
			}
			
			
			temprecord = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
				String line;
				int rowindex = 0;
				
				while ((line = br.readLine()) != null) {
					if (rowindex == selectedrow) {
						String updatedinfo = patientID + DELIMITER + name + DELIMITER + age + DELIMITER + bloodtype + DELIMITER +
											gender + DELIMITER + diagnosis;
						
						temprecord.add(updatedinfo);
					} else {
						temprecord.add(line);
					}
					rowindex++;
				}
				
				
				
			} catch (IOException x) {
				JOptionPane.showMessageDialog(null, x);
			}
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
				
				
				for (String record : temprecord) {
					bw.write(record + "\n");
				}
			
				
			} catch (IOException z) {
				JOptionPane.showMessageDialog(null, z);
			}
			
			
		
			
		}
		
		
		
		void reload() {
			model.setRowCount(0);
			
			File file = new File(filename);
			if (!file.exists()) return;
			
			
			
			try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
				String line;
				
				while((line = br.readLine()) != null) {
					String[] rows = line.split(DELIMITER);
					model.addRow(rows);
				}
				
				
				
				
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e);
			}
			
			
			
			
		}
	
	
	
		void clear() {
			txtpatientID.setText("");
			txtname.setText("");
			txtage.setText("");
			txtdiagnosis.setText("");
			combloodtype.setSelectedIndex(0);
			bg.clearSelection();
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new PatientRecordSystem();
		
		
	}

}
