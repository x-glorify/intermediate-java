package Practice107;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
public class BasicInfoSystem extends JFrame{
	
	final String DELIMITER = ":::";
	final String filename = "BasicInfoSystem.txt";
	
	private JTextField txtname, txtage;
	private String name, civilstat, gender;
	private int age;
	JComboBox <String> comcivilstatus;
	JTable table;
	JScrollPane scroll;
	DefaultTableModel model;
	ButtonGroup bg;
	ArrayList <String> temprecord;
	
	
	
	BasicInfoSystem() {
		
		JLabel lblname = new JLabel("Full Name");
		add(lblname).setBounds(30, 30, 100, 20);
		
		txtname = new JTextField();
		add(txtname).setBounds(30, 60, 150, 20);
		
		JLabel lblage = new JLabel("Age");
		add(lblage).setBounds(30, 90, 100, 20);
		
		txtage = new JTextField();
		add(txtage).setBounds(30, 120, 50, 20);
		
		JLabel lblcivilstat = new JLabel("Civil Status");
		add(lblcivilstat).setBounds(30, 150, 100, 20);
		
		comcivilstatus = new JComboBox<>(new String[] {"Single", "Married", "Separated", "Widowed", "Divorced"});
		add(comcivilstatus).setBounds(30, 180, 100, 20);
		
		JLabel lblgender = new JLabel("Gender");
		add(lblgender).setBounds(30, 210, 100, 20);
		
		JRadioButton rbtn1 = new JRadioButton("Female");
		add(rbtn1).setBounds(30, 240, 70, 20);
		
		JRadioButton rbtn2 = new JRadioButton("Male");
		add(rbtn2).setBounds(120, 240, 70, 20);
		
		bg = new ButtonGroup();
		bg.add(rbtn1);
		bg.add(rbtn2);
		
		
		//buttons
		
		JButton btnadd = new JButton("Add");
		add(btnadd).setBounds(30, 300, 80, 30);
		
		JButton btndelete = new JButton("Delete");
		add(btndelete).setBounds(120, 300, 80, 30);
		
		JButton btnupdate = new JButton("Update");
		add(btnupdate).setBounds(30, 350, 80, 30);
		
		JButton btnclear = new JButton("Clear");
		add(btnclear).setBounds(120, 350, 80, 30);
		
		
		// button functions
		
		
		btnadd.addActionListener(e ->{
		
			try {
			
			if (txtname.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Name is empty!");
				return;
			}
			
			name = txtname.getText();
			age = Integer.parseInt(txtage.getText());
			civilstat = comcivilstatus.getSelectedItem().toString();
			
			if (bg == null) {
				JOptionPane.showMessageDialog(null, "Please select gender.");
				return;
			}
			
			if (rbtn1.isSelected()) {
				gender = "Female";
			} else {
				gender = "Male";
			}
			
			} catch (NumberFormatException x) {
				JOptionPane.showMessageDialog(null, "Enter age number only!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			add();
			loadtable();
		});
		
		
		btndelete.addActionListener(e ->{
			delete();
			loadtable();
			
		});
		
		
		btnupdate.addActionListener(e ->{
			
			try {
				
				if (txtname.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Name is empty!");
					return;
				}
				
				name = txtname.getText();
				age = Integer.parseInt(txtage.getText());
				civilstat = comcivilstatus.getSelectedItem().toString();
				
				if (bg == null) {
					JOptionPane.showMessageDialog(null, "Please select gender.");
					return;
				}
				
				if (rbtn1.isSelected()) {
					gender = "Female";
				} else {
					gender = "Male";
				}
				
				} catch (NumberFormatException x) {
					JOptionPane.showMessageDialog(null, "Enter age number only!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			
			update();
			loadtable();		
		});


		btnclear.addActionListener(e ->{
			clear();
			loadtable();
			
		});
		
		
		
		
		
		//table
		
		String[] cols = {"Name", "Age", "Civil Status", "Gender"};
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		scroll = new JScrollPane(table);
		add(scroll).setBounds(250, 30, 600, 400);
		loadtable();
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selectedrow = table.getSelectedRow();
				if (selectedrow != -1) {
					txtname.setText(model.getValueAt(selectedrow, 0).toString());
					txtage.setText(model.getValueAt(selectedrow, 1).toString());
					
					comcivilstatus.setSelectedItem(model.getValueAt(selectedrow, 2).toString());
					gender = model.getValueAt(selectedrow, 3).toString();
					if (gender.equals("Female")) {
						rbtn1.setSelected(true);
					} else {
						rbtn2.setSelected(true);
					}
					
				}
			}
		});
		
		
		
		
		
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 500);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	

	//methods
	
		void add() {
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
				
				bw.write(name + DELIMITER + age + DELIMITER + civilstat + DELIMITER + gender);
				bw.newLine();
				bw.close();
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
		
		void delete() {
			int selectedrow = table.getSelectedRow();
			
			if (selectedrow == -1) {
				JOptionPane.showMessageDialog(null, "Select a record to delete", null, JOptionPane.ERROR_MESSAGE);
			}
			
			int confirm = JOptionPane.showConfirmDialog(null, "Delete record?", "Confirm", JOptionPane.YES_NO_OPTION);
			if (confirm != JOptionPane.YES_OPTION) return;
			
			
			
			
			temprecord = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
				String line;
				int rowIndex = 0;
				
				while((line = br.readLine()) != null) {
					if (rowIndex != selectedrow) temprecord.add(line);
						rowIndex++;
	
				}
				
			} catch (IOException x) {
				JOptionPane.showMessageDialog(null, x);
			}
			
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
				
				for (String record:temprecord) {
					bw.write(record + "\n");
					
				
				}
				
				
				
			} catch (IOException z) {
				JOptionPane.showMessageDialog(null, z);
			}
			
			
		}
		
		void update() {
		int selectedrow = table.getSelectedRow();
			
			if (selectedrow == -1) {
				JOptionPane.showMessageDialog(null, "Select a record to delete", null, JOptionPane.ERROR_MESSAGE);
			}
			
			
			
			temprecord = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
				String line;
				int rowIndex = 0;
				
				while((line = br.readLine()) != null) {
					if (rowIndex == selectedrow) {
						String updatedinfo = name + DELIMITER + age + DELIMITER + civilstat + DELIMITER + gender;
						temprecord.add(updatedinfo);
					} else {
						temprecord.add(line);
					}
					rowIndex++;
				}
				
			} catch (IOException x) {
				JOptionPane.showMessageDialog(null, x);
			}
			
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
				
				for (String record:temprecord) {
					bw.write(record + "\n");
					
					
				}
				
				
				
			} catch (IOException z) {
				JOptionPane.showMessageDialog(null, z);
			}
			
			
			
			
		}
		
		
		void loadtable() {
			
			File file = new File(filename);
			if (!file.exists()) return;
			
			model.setRowCount(0);
			
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
			txtname.setText("");
			txtage.setText("");
			comcivilstatus.setSelectedIndex(0);
			bg.clearSelection();
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static void main(String[] args) {
		new BasicInfoSystem();

	}

}
