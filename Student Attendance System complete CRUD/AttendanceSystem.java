package InterventionProgram;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.time.*;
import java.time.format.*;
public class AttendanceSystem extends JFrame{
	private final String FILENAME = "attendance.txt";
	private final String DELIMITER = "::";
	
	private JTextField txtID, txtname, txtstatus, txtdate;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scroll;
	private ArrayList<String> records;
	
	
	
	AttendanceSystem() {
		
		JPanel northPanel = new JPanel(new BorderLayout());
		JPanel inputPanel = new JPanel(new GridLayout(2, 4, 5, 5));
		
		inputPanel.add(new JLabel("Student ID:"));
		inputPanel.add(new JLabel("Name:"));
		inputPanel.add(new JLabel("Status:"));
		inputPanel.add(new JLabel("Date:"));
		
		txtID = new JTextField();
		txtname = new JTextField();
		txtstatus = new JTextField();
		txtdate = new JTextField();
		
		inputPanel.add(txtID);
		inputPanel.add(txtname);
		inputPanel.add(txtstatus);
		inputPanel.add(txtdate);
		
		JPanel btnPanel = new JPanel(new FlowLayout());
		
		JButton btnadd = new JButton("Add");
		JButton btnview = new JButton("View");
		JButton btnupdate = new JButton("Update");
		JButton btndelete = new JButton("Delete");
		
		btnPanel.add(btnadd);
		btnPanel.add(btnview);
		btnPanel.add(btnupdate);
		btnPanel.add(btndelete);
		
		northPanel.add(inputPanel, BorderLayout.CENTER);
		northPanel.add(btnPanel, BorderLayout.SOUTH);
		northPanel.setBorder(BorderFactory.createTitledBorder("Attendance Details:"));
		
		
		String[] cols = {"Student ID", "Name", "Status", "Date"};
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		scroll = new JScrollPane(table);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					txtID.setText(model.getValueAt(row, 0).toString());
					txtname.setText(model.getValueAt(row, 1).toString());
					txtstatus.setText(model.getValueAt(row, 2).toString());
					txtdate.setText(model.getValueAt(row, 3).toString());
				}
			}
		});
		
		
		btnadd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			try {
				if (txtID.getText().isEmpty() || txtname.getText().isEmpty() ||
					txtstatus.getText().isEmpty() || txtdate.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please do not leave any fields empty.");
					return;
				}
				
				if (!txtstatus.getText().equalsIgnoreCase("Present") &&
					(!txtstatus.getText().equalsIgnoreCase("Absent"))) {
					JOptionPane.showMessageDialog(null, "Status must be Present or Absent only.");
					return;
				}
				
				if (!txtdate.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
					JOptionPane.showMessageDialog(null, "Date must be in format YYYY-MM-DD");
					return;
				}
				
				LocalDate.parse(txtdate.getText());
				
				if (isDuplicate()) return;
				
				Attendance attend = new Attendance(txtID.getText(), txtname.getText(),
												txtstatus.getText(), txtdate.getText());
				
				
				add(attend);
				clear();
				
			} catch (DateTimeParseException err) {
				JOptionPane.showMessageDialog(null, "Please input a valid date.");
				return;
			}
				
				
			}});
		

		btnupdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int selectedrow = table.getSelectedRow();
					if (selectedrow == -1) {
						JOptionPane.showMessageDialog(null, "Please choose a record to update.");
						return;
					}
					
					if (txtID.getText().isEmpty() || txtname.getText().isEmpty() ||
						txtstatus.getText().isEmpty() || txtdate.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please do not leave any fields empty.");
						return;
					}
					

					if (!txtstatus.getText().equalsIgnoreCase("Present") &&
						(!txtstatus.getText().equalsIgnoreCase("Absent"))) {
						JOptionPane.showMessageDialog(null, "Status must be Present or Absent only.");
						return;
					}
					
					if (!txtdate.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
						JOptionPane.showMessageDialog(null, "Date must be in format YYYY-MM-DD");
						return;
					}
					
					LocalDate.parse(txtdate.getText());
					Attendance attend = new Attendance(txtID.getText(), txtname.getText(),
													txtstatus.getText(), txtdate.getText());
					
					update(attend);
					clear();
					loadTable();
					
				} catch (DateTimeParseException err) {
					JOptionPane.showMessageDialog(null, "Please input a valid date.");
					return;
				}
				
			}});
		

		btndelete.addActionListener(e->{
			records = new ArrayList<>();
			int selectedrow = table.getSelectedRow();
			if (selectedrow == -1) {
				JOptionPane.showMessageDialog(null, "Please choose a record to update.");
				return;
			}
			delete();
			clear();
			loadTable();
		});
		
		btnview.addActionListener(e->{
			view();
		});
		
		
		
		
		setLayout(new BorderLayout());
		add(northPanel, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		
		setTitle("Student Attendance System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 500);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private boolean isDuplicate() {
		model.setRowCount(0);
		File file = new File(FILENAME);
		if (!file.exists()) return false;
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			while((line = br.readLine()) != null) {
				String[] row = line.split(DELIMITER);
				if (row[0].equals(txtID.getText())) {
					JOptionPane.showMessageDialog(null, "This student ID already exists.");
					return true;
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return false;
 	}
	
	
	
	private void add(Attendance a) {
		try (FileWriter fw = new FileWriter(FILENAME, true)) {
			fw.write(a.toFileString(DELIMITER) + "\n");
			fw.close();
			JOptionPane.showMessageDialog(null, "Record added successfully!");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "There was an error writing file to text.");
		}
	}
	
	
	private void update(Attendance a) {
		int selectedrow = table.getSelectedRow();
		records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowindex = 0;
			while((line = br.readLine()) != null) {
				if (rowindex == selectedrow) {
					records.add(a.toFileString(DELIMITER));
				} else {
					records.add(line);
				}
				rowindex++;
			}
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, "There was an error updating the record. Please try again.");
		} 
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
			for (String record : records) bw.write(record + "\n");
			bw.close();
			JOptionPane.showMessageDialog(null, "Record updated!");
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, z.getMessage());
		}
		
		
	}
	
	
	public void delete() {
		int selectedrow = table.getSelectedRow();
		int confirm = JOptionPane.showConfirmDialog(null, "Delete this record?", "Confirm",
													JOptionPane.YES_NO_OPTION);
		
		if (confirm != JOptionPane.YES_OPTION) return;
		
		records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowindex = 0;
			while((line = br.readLine()) != null) {
				if (rowindex != selectedrow) records.add(line);
				rowindex++;
			}
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, "There was an error deleting the record. Please try again.");
		} 
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
			for (String record : records) bw.write(record + "\n");
			bw.close();
			JOptionPane.showMessageDialog(null, "Record deleted!");
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, z.getMessage());
		}
		
	}
	
	private void view() {
		model.setRowCount(0);
		File file = new File(FILENAME);
		if (!file.exists() || file.length() == 0) {
		JOptionPane.showMessageDialog(null, "There is currently no record.");
		return;
		}
		
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			while((line = br.readLine()) != null) {
				String[] data = line.split(DELIMITER);
				model.addRow(new Object[] {
				data[0], data[1], data[2], data[3]
				});
			}
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, x.getMessage());
		}
	}
	
	private void loadTable() {
		model.setRowCount(0);
		File file = new File(FILENAME);
		if (!file.exists()) return;
		
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			while((line = br.readLine()) != null) {
			String[] rows = line.split(DELIMITER);
			model.addRow(rows);
			}
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, x.getMessage());
		}
		
	}
	
	
	private void clear() {
		txtID.setText("");
		txtname.setText("");
		txtstatus.setText("");
		txtdate.setText("");
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		
		new AttendanceSystem();
	}

}
