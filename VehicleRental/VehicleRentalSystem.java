package InterventionProgram;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class VehicleRentalSystem extends JFrame{

	private final String FILENAME = "vehiclerent.txt";
	private final String DELIMITER = "::";
	
	private JTextField txtrentalID, txtcustomer, txtvehicle, txtdays, txtrate, txttotal, txtdate;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private ArrayList<String> records;
	
	
	VehicleRentalSystem() {
		String[] cols = {"Rental ID", "Customer Name", "Vehicle", "No. of Days", "Daily Rate","Totao", "Date"};
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(table);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					txtrentalID.setText(model.getValueAt(row, 0).toString());
					txtcustomer.setText(model.getValueAt(row, 1).toString());
					txtvehicle.setText(model.getValueAt(row, 2).toString());
					txtdays.setText(model.getValueAt(row, 3).toString());
					txtrate.setText(model.getValueAt(row, 4).toString());
					txttotal.setText(model.getValueAt(row, 5).toString());
					txtdate.setText(model.getValueAt(row, 6).toString());
					
					
					
				}
				
				
			}
		});
		
		
		
		
		JPanel westPanel = new JPanel(new BorderLayout());
		JPanel formsPanel = new JPanel(new GridLayout(14, 1, 5, 5));
		
		formsPanel.add(new JLabel("Rental ID"));
		txtrentalID = new JTextField();
		formsPanel.add(txtrentalID);
		
		formsPanel.add(new JLabel("Customer Name"));
		txtcustomer = new JTextField();
		formsPanel.add(txtcustomer);
		
		formsPanel.add(new JLabel("Vehicle Name"));
		txtvehicle = new JTextField();
		formsPanel.add(txtvehicle);
		
		formsPanel.add(new JLabel("Number of Days"));
		txtdays = new JTextField();
		formsPanel.add(txtdays);
		
		formsPanel.add(new JLabel("Daily Rate"));
		txtrate = new JTextField();
		formsPanel.add(txtrate);
		
		formsPanel.add(new JLabel("Total"));
		txttotal = new JTextField();
		txttotal.setEditable(false);
		formsPanel.add(txttotal);
		
		formsPanel.add(new JLabel("Date"));
		txtdate = new JTextField();
		formsPanel.add(txtdate);
		
		
		
		JPanel btnPanel = new JPanel(new FlowLayout());
		
		JButton btnadd = new JButton("Add");
		JButton btnupdate = new JButton("Update");
		JButton btndelete = new JButton("Delete");
		JButton btnclear = new JButton("Clear");
		
		
		btnPanel.add(btnadd);
		btnPanel.add(btnupdate);
		btnPanel.add(btndelete);
		btnPanel.add(btnclear);
		
		
		westPanel.add(formsPanel, BorderLayout.CENTER);
		westPanel.add(btnPanel, BorderLayout.SOUTH);
		westPanel.setBorder(BorderFactory.createTitledBorder("Rent Details:"));
		
		
		btnadd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
				if (txtrentalID.getText().isEmpty() || txtcustomer.getText().isEmpty() || txtvehicle.getText().isEmpty() ||
					txtdays.getText().isEmpty() || txtrate.getText().isEmpty() || txtdate.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill in all the fields!");
					return;
					
				}
				
				
				if (!txtdate.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
					JOptionPane.showMessageDialog(null, "Date must be in format YYYY-MM-DD");
					return;
				}
				
				if (duplicateIDcheck()) {
					JOptionPane.showMessageDialog(null, "This ID already exists.");
					return;
				}
				
				double rate = Double.parseDouble(txtrate.getText());
				double days = Double.parseDouble(txtdays.getText());
				double total = rate*days;
				txttotal.setText(String.valueOf(total));
				
				Rental rent = new Rental(txtrentalID.getText(),txtcustomer.getText(), txtvehicle.getText(),
						days, rate, total, txtdate.getText());
				
				add(rent);
				loadTable();
				
				
				} catch (NumberFormatException err) {
					JOptionPane.showMessageDialog(null, "Rate and days must be in numbers only.");
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
					
				if (txtrentalID.getText().isEmpty() || txtcustomer.getText().isEmpty() || txtvehicle.getText().isEmpty() ||
					txtdays.getText().isEmpty() || txtrate.getText().isEmpty() || txtdate.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill in all the fields!");
					return;
					
				}
				
				
				if (!txtdate.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
					JOptionPane.showMessageDialog(null, "Date must be in format YYYY-MM-DD");
					return;
				}
				
				if (duplicateIDcheck()) {
					JOptionPane.showMessageDialog(null, "This ID already exists.");
					return;
				}
				
				double rate = Double.parseDouble(txtrate.getText());
				double days = Double.parseDouble(txtdays.getText());
				double total = rate*days;
				txttotal.setText(String.valueOf(total));
				
				Rental rent = new Rental(txtrentalID.getText(),txtcustomer.getText(), txtvehicle.getText(),
						days, rate, total, txtdate.getText());
				
				update(rent);
				loadTable();
				
				} catch (NumberFormatException err) {
					JOptionPane.showMessageDialog(null, "Rate and days must be in numbers only.");
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
			txtrentalID.setText("");
			txtcustomer.setText("");
			txtvehicle.setText("");
			txtdays.setText("");
			txtrate.setText("");
			txttotal.setText("");
			txtdate.setText("");
			
		});
		
		
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		add(westPanel, BorderLayout.WEST);
		loadTable();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setVisible(true);
		
		
	}
	
	
	private void add(Rental r) {
		try (FileWriter fw = new FileWriter(FILENAME, true)) {
			fw.write(r.getID() + DELIMITER + 
					r.getcustomer() + DELIMITER +
					r.getvehicle() + DELIMITER +
					r.getdays() + DELIMITER +
					r.getrate() + DELIMITER +
					r.gettotal() + DELIMITER +
					r.getdate() + "\n");
			fw.close();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "There was an error writing file to text"+e.getMessage());
		}
		
	
	}
	
	
	
	private void update(Rental r) {
		int selectedrow = table.getSelectedRow();
		
		records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowindex = 0;
			while((line = br.readLine()) != null) {
				if (rowindex == selectedrow) {
					String updatedinfo = r.getID() + DELIMITER + 
							r.getcustomer() + DELIMITER +
							r.getvehicle() + DELIMITER +
							r.getdays() + DELIMITER +
							r.getrate() + DELIMITER +
							r.gettotal() + DELIMITER +
							r.getdate();
					records.add(updatedinfo);
				} else {
					records.add(line);
				}
				rowindex++;
			}
			
			
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, "Updating record error. Please try again later."+x.getMessage());
		}
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
			for (String record:records) bw.write(record + "\n");
			bw.close();
			
			JOptionPane.showMessageDialog(null, "Record updated!");
		} catch (IOException z) {
			JOptionPane.showMessageDialog(scrollPane, "There was an error writing file to text."+z.getMessage());
		}
		
		
	}
	
	
	private void delete() {
		int selectedrow = table.getSelectedRow();
		int confirm = JOptionPane.showConfirmDialog(null, "Delete this record?", "Confirm", JOptionPane.YES_NO_OPTION);
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
			JOptionPane.showMessageDialog(null, "Updating record error. Please try again later."+x.getMessage());
		}
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
			for (String record:records) bw.write(record + "\n");
			bw.close();
			
			JOptionPane.showMessageDialog(null, "Record deleted!");
		} catch (IOException z) {
			JOptionPane.showMessageDialog(scrollPane, "There was an error writing file to text."+z.getMessage());
		}
		
		
		
		
		
	}
	
	
	private void loadTable() {
		model.setRowCount(0);
		
		File file = new File(FILENAME);
		if(!file.exists()) return; 
		
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			while((line = br.readLine()) != null) {
				String[] rows = line.split(DELIMITER);
				model.addRow(rows);
				
			}
			
		} catch (IOException err) {
			JOptionPane.showMessageDialog(null, "Error loading table. Please try again later."+err.getMessage());
		}
	}
	
	
	private boolean duplicateIDcheck() {
		model.setRowCount(0);
		
		File file = new File(FILENAME);
		if(!file.exists()) return false; 
		
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			while((line = br.readLine()) != null) {
				String[] rows = line.split(DELIMITER);
				model.addRow(rows);
				if (rows[0].equals(txtrentalID.getText())) return true;
			}
			
		} catch (IOException err) {
			JOptionPane.showMessageDialog(null, "Error loading table. Please try again later."+err.getMessage());
		}
		
		
		return false;
		
		
	}
	
	
	
	public static void main(String[] args) {
		new VehicleRentalSystem();
		
		
		
	}

}
