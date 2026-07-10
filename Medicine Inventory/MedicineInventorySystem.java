package InterventionProgram;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.time.*;
import java.time.format.*;
public class MedicineInventorySystem extends JFrame{

	private final String FILENAME = "medicine_inventory.txt";
	private final String DELIMITER = "::";
	
	private JTextField txtmedicineID, txtmedicineName, txtmanufacturer,
						txtquantity, txtunitcost, txttotal, txtexpirydate;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scroll;
	private ArrayList<String> records;
	
	
	MedicineInventorySystem() {
		String[] cols = {"Medicine ID", "Medicine Name", "Manufacturer", "Quantity",
						"Unit Cost", "Total Cost", "Expiry Date"};
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		scroll = new JScrollPane(table);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					txtmedicineID.setText(model.getValueAt(row, 0).toString());
					txtmedicineName.setText(model.getValueAt(row, 1).toString());
					txtmanufacturer.setText(model.getValueAt(row, 2).toString());
					txtquantity.setText(model.getValueAt(row, 3).toString());
					txtunitcost.setText(model.getValueAt(row, 4).toString());
					txttotal.setText(model.getValueAt(row, 5).toString());
					txtexpirydate.setText(model.getValueAt(row, 6).toString());
				}
			}
		});
		
		JPanel northPanel = new JPanel(new BorderLayout());
		JPanel inputPanel = new JPanel(new GridLayout(2, 7, 6, 6));
		
		inputPanel.add(new JLabel("Medicine ID:"));
		inputPanel.add(new JLabel("Medicine Name:"));
		inputPanel.add(new JLabel("Manufacturer:"));
		inputPanel.add(new JLabel("Quantity:"));
		inputPanel.add(new JLabel("Cost:"));
		inputPanel.add(new JLabel("Total:"));
		inputPanel.add(new JLabel("Expiry Date:"));
		
		txtmedicineID = new JTextField();
		txtmedicineName = new JTextField();
		txtmanufacturer = new JTextField();
		txtquantity = new JTextField();
		txtunitcost = new JTextField();
		txttotal = new JTextField();
		txttotal.setEditable(false);
		txtexpirydate = new JTextField();
		
		inputPanel.add(txtmedicineID);
		inputPanel.add(txtmedicineName);
		inputPanel.add(txtmanufacturer);
		inputPanel.add(txtquantity);
		inputPanel.add(txtunitcost);
		inputPanel.add(txttotal);
		inputPanel.add(txtexpirydate);
		
		
		JPanel btnPanel = new JPanel(new FlowLayout());
		JButton btnadd = new JButton("Add");
		JButton btnupdate = new JButton("Update");
		JButton btndelete = new JButton("Delete");
		JButton btnclear = new JButton("Clear");
		
		btnPanel.add(btnadd);
		btnPanel.add(btnupdate);
		btnPanel.add(btndelete);
		btnPanel.add(btnclear);
		
		northPanel.add(inputPanel, BorderLayout.CENTER);
		northPanel.add(btnPanel, BorderLayout.SOUTH);
		northPanel.setBorder(BorderFactory.createTitledBorder("Medicind Details:"));
		
		btnadd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtmedicineID.getText().isEmpty() || txtmedicineName.getText().isEmpty() ||
						txtmanufacturer.getText().isEmpty() || txtquantity.getText().isEmpty() ||
						txtunitcost.getText().isEmpty() || txtexpirydate.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please fill all empty fields.");
						return;
					}
					
					if (!txtexpirydate.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
						JOptionPane.showMessageDialog(null, "Expiry date must be in format YYYY-MM-DD");
						return;
					}
					LocalDate.parse(txtexpirydate.getText());
					
					if (duplicatedID()) {
						JOptionPane.showMessageDialog(null, "This medicine ID already exists.");
						return;
					}
					
					int qty = Integer.parseInt(txtquantity.getText());
					int unitcost = Integer.parseInt(txtunitcost.getText());
					
					
					if (qty < 0 || unitcost <= 0) {
						JOptionPane.showMessageDialog(null, "Quantity must not be negative and"
								+ " unit cost must not be equal or lower thann zero.");
						return;
					}
					
					Medicine med = new Medicine(txtmedicineID.getText(), txtmedicineName.getText(),
												txtmanufacturer.getText(), qty, unitcost,
												txtexpirydate.getText());
					
					txttotal.setText(String.valueOf(med.getTotal()));
					add(med);
					loadTable();
					
				} catch (NumberFormatException err) {
					JOptionPane.showMessageDialog(null, "Quantity and Cost must be numbers only.");
					return;
				} catch (DateTimeParseException err) {
					JOptionPane.showMessageDialog(null, "Please input a valid date.");
					return;
				}
				 
				
			}
			
		});
		
		btnupdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int selectedrow = table.getSelectedRow();
					if (selectedrow == -1) {
						JOptionPane.showMessageDialog(null, "Please select a record to update.");
						return;
					}
					
					if (txtmedicineID.getText().isEmpty() || txtmedicineName.getText().isEmpty() ||
						txtmanufacturer.getText().isEmpty() || txtquantity.getText().isEmpty() ||
						txtunitcost.getText().isEmpty() || txtexpirydate.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please fill all empty fields.");
						return;
					}
					
					if (!txtexpirydate.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
						JOptionPane.showMessageDialog(null, "Expiry date must be in format YYYY-MM-DD");
						return;
					}
					LocalDate.parse(txtexpirydate.getText());
					
					int qty = Integer.parseInt(txtquantity.getText());
					int unitcost = Integer.parseInt(txtunitcost.getText());
					int totalcost = qty*unitcost;
					txttotal.setText(String.valueOf(totalcost));
					
					if (qty < 0 || unitcost <= 0) {
						JOptionPane.showMessageDialog(null, "Quantity must not be negative and"
								+ " unit cost must not be equal or lower thann zero.");
						return;
					}
					
					//===== refer to Medicine.java file ========================================
					Medicine med = new Medicine(txtmedicineID.getText(), txtmedicineName.getText(),
												txtmanufacturer.getText(), qty, unitcost, totalcost,
												txtexpirydate.getText());
					
					update(med);
					loadTable();
					
				} catch (NumberFormatException err) {
					JOptionPane.showMessageDialog(null, "Quantity and Cost must be numbers only.");
					return;
				} catch (DateTimeParseException err) {
					JOptionPane.showMessageDialog(null, "Please input a valid date.");
					return;
				}
				
			}
			
		});
		
		btndelete.addActionListener(e->{
			int selectedrow = table.getSelectedRow();
			if (selectedrow == -1) {
				JOptionPane.showMessageDialog(null, "Please select a record to delete.");
				return;
			}
			delete();
			loadTable();
			
		});
		
		btnclear.addActionListener(e->{
			txtmedicineID.setText("");
			txtmedicineName.setText("");
			txtmanufacturer.setText("");
			txtquantity.setText("");
			txtunitcost.setText("");
			txttotal.setText("");
			txtexpirydate.setText("");
			
		});
		
		
		
		
		
		setLayout(new BorderLayout());
		add(scroll, BorderLayout.CENTER);
		add(northPanel, BorderLayout.NORTH);
		
		setTitle("Medicine Inventory");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000,600);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	//============= methods ================================================
	
	private void add(Medicine m) {
		try (FileWriter fw = new FileWriter(FILENAME, true)) {
			fw.write(m.toFileString(DELIMITER) + "\n");
			fw.close();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "There was an error writing file to text." + 
												e.getMessage());
		}
		
	}
	
	
	
	
	private void update(Medicine m) {
		int selectedrow = table.getSelectedRow();
		records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowindex = 0;
			while((line = br.readLine()) != null) {
				if (rowindex == selectedrow) {
					records.add(m.toFileString(DELIMITER));
				} else {
					records.add(line);
				}
				rowindex++;
			}
			
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, "There was an error updating the record. Please try again."
										+ x.getMessage());
		}
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
			for (String record : records) bw.write(record + "\n");
					bw.close();
					JOptionPane.showMessageDialog(null, "Record updated!");
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, "There was an error writing file to text."
												+ z.getMessage());
		}
		
	}
	
	private void delete() {
		int selectedrow = table.getSelectedRow();
		int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?",
					"Confirm", JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION) return;
		
		records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowindex = 0;
			while((line = br.readLine()) != null) {
				if (rowindex == selectedrow) records.add(line);
				rowindex++;
			}
			
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, "There was an error deleting the record. Please try again."
										+ x.getMessage());
		}
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
			for (String record : records) bw.write(record + "\n");
					bw.close();
					JOptionPane.showMessageDialog(null, "Record deleted!");
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, "There was an error writing file to text."
												+ z.getMessage());
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
		}catch (IOException e) {
			JOptionPane.showMessageDialog(null, "There was an error loading the table. Please try again later.");
		}
	}
	
	
	
	private boolean duplicatedID() {
		model.setRowCount(0);
		File file = new File(FILENAME);
		if (!file.exists()) return false;
		
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			while((line = br.readLine()) != null) {
				String[] rows = line.split(DELIMITER);
				if (rows[0].equals(txtmedicineID.getText())) return true;
			}
		}catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return false;
	}
	
	
	public static void main(String[] args) {
		new MedicineInventorySystem();

	}

}
