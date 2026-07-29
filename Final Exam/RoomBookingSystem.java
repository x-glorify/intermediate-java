package CCE107Final;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.time.*;
import java.time.format.*;
public class RoomBookingSystem extends JFrame{
	private final String FILENAME = "booking.txt";
	private final String DELIMITER = "##";
	
	private JTextField txtID, txtname, txtroomnum, txtroomtype, txtcheckIn, txtcheckOut,
						txttotal, txtpaystatus;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private ArrayList<String> records;
	
	RoomBookingSystem() {
		JPanel northPanel = new JPanel(new BorderLayout());
		JPanel formsPanel = new JPanel(new GridLayout(2, 8, 5, 5));
		
		formsPanel.add(new JLabel("Booking ID"));
		formsPanel.add(new JLabel("Customer Name"));
		formsPanel.add(new JLabel("Room Number"));
		formsPanel.add(new JLabel("Room Type"));
		formsPanel.add(new JLabel("Check-In Date"));
		formsPanel.add(new JLabel("Check-Out Date"));
		formsPanel.add(new JLabel("Total Cost"));
		formsPanel.add(new JLabel("Payment Status"));
		
		txtID			= new JTextField();
		txtname			= new JTextField();
		txtroomnum		= new JTextField();
		txtroomtype		= new JTextField();
		txtcheckIn		= new JTextField();
		txtcheckOut		= new JTextField();
		txttotal			= new JTextField();
		txtpaystatus		= new JTextField();
		
		
		formsPanel.add(txtID);
		formsPanel.add(txtname);
		formsPanel.add(txtroomnum);
		formsPanel.add(txtroomtype);
		formsPanel.add(txtcheckIn);
		formsPanel.add(txtcheckOut);
		formsPanel.add(txttotal);
		formsPanel.add(txtpaystatus);
		
		
		JPanel btnPanel	= new JPanel(new FlowLayout());
		
		JButton btnadd		= new JButton("Add");
		JButton btnupdate	= new JButton("Update");
		JButton btndelete	= new JButton("Delete");
		JButton btnclear		= new JButton("Clear");
		JButton btnexit		= new JButton("Exit");
		
		btnPanel.add(btnadd);
		btnPanel.add(btnupdate);
		btnPanel.add(btndelete);
		btnPanel.add(btnclear);
		btnPanel.add(btnexit);
		
		
		northPanel.add(formsPanel, BorderLayout.CENTER);
		northPanel.add(btnPanel, BorderLayout.SOUTH);
		northPanel.setBorder(BorderFactory.createTitledBorder("Booking Details:"));
		
		//================ table ======================================================
		String cols[] = {"Booking ID", "Customer Name", "Room No.", "Room Type",
						"Check-In Date", "Check-Out Date", "Total", "Payment Status"};
		
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(table);
		
		// ================ table and button functions =================================
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					txtID.setText(model.getValueAt(row, 0).toString());
					txtname.setText(model.getValueAt(row, 1).toString());
					txtroomnum.setText(model.getValueAt(row, 2).toString());
					txtroomtype.setText(model.getValueAt(row, 3).toString());
					txtcheckIn.setText(model.getValueAt(row, 4).toString());
					txtcheckOut.setText(model.getValueAt(row, 5).toString());
					txttotal.setText(model.getValueAt(row, 6).toString());
					txtpaystatus.setText(model.getValueAt(row, 7).toString());
				}
			}
		});
		
		
		btnadd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtID.getText().isEmpty() || txtname.getText().isEmpty() || txtroomnum.getText().isEmpty() || txtroomtype.getText().isEmpty() ||
						txtcheckIn.getText().isEmpty() || txtcheckOut.getText().isEmpty() || txttotal.getText().isEmpty() || txtpaystatus.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please fill out all fields.");
						return;
					}
					
					
					if (isDuplicate()) {
						return;
					}
					
					
					if (!txtcheckIn.getText().matches("\\d{4}-\\d{2}-\\d{2}") || !txtcheckOut.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
						JOptionPane.showMessageDialog(null, "Date must be in format (YYYY-MM-DD).");
						return;
					}
					
					if (!txtroomtype.getText().equalsIgnoreCase("Regular") && !txtroomtype.getText().equalsIgnoreCase("Deluxe")) {
						JOptionPane.showMessageDialog(null, "Eoom type must be Regular or Deluxe only.");
						return;
					}
					
				
					double total = Double.parseDouble(txttotal.getText());
					int roomnum = Integer.parseInt(txtroomnum.getText());
					LocalDate checkIn = LocalDate.parse(txtcheckIn.getText());
					LocalDate checkOut = LocalDate.parse(txtcheckOut.getText());
					String type = txtroomtype.getText();
					String status = txtpaystatus.getText();
					
					if (roomnum > 20 || roomnum < 1 ) {
						JOptionPane.showMessageDialog(null, "Sorry, there are only 20 available rooms. Please input a valid room number.");
						return;
					}
					
					
					if (type.equalsIgnoreCase("Regular")) {
						type = "Regular";
					} else {
						type = "Deluxe";
					}
					
					if (!status.equalsIgnoreCase("Paid") && !status.equalsIgnoreCase("Pending")) {
						JOptionPane.showMessageDialog(null, "Payment status must be Paid or Pending only.");
						return;
					} else if (status.equalsIgnoreCase("Paid")){
						status = "Paid";
					} else {
						status = "Pending";
					}
					
					
					Booking booked = new Booking(txtID.getText(), txtname.getText(), roomnum, type,
									checkIn, checkOut, total, status);
					
					
					add(booked);
					refresh();
					
					
				} catch (NumberFormatException x) {
					JOptionPane.showMessageDialog(null, "Total amount and room number must be in numbers only.");
					return;
				} catch (DateTimeParseException z) {
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
						JOptionPane.showMessageDialog(null, "Please choose a record to update.");
						return;
					}
					
					
					if (txtID.getText().isEmpty() || txtname.getText().isEmpty() || txtroomnum.getText().isEmpty() || txtroomtype.getText().isEmpty() ||
						txtcheckIn.getText().isEmpty() || txtcheckOut.getText().isEmpty() || txttotal.getText().isEmpty() || txtpaystatus.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please fill out all fields.");
						return;
					}
					
					
					if (!txtcheckIn.getText().matches("\\d{4}-\\d{2}-\\d{2}") && !txtcheckOut.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
						JOptionPane.showMessageDialog(null, "Date must be in format (YYYY-MM-DD).");
						return;
					}
					
					if (!txtroomtype.getText().equalsIgnoreCase("Regular") && !txtroomtype.getText().equalsIgnoreCase("Deluxe")) {
						JOptionPane.showMessageDialog(null, "Eoom type must be Regular or Deluxe only.");
						return;
					}
					
				
					double total = Double.parseDouble(txttotal.getText());
					int roomnum = Integer.parseInt(txtroomnum.getText());
					LocalDate checkIn = LocalDate.parse(txtcheckIn.getText());
					LocalDate checkOut = LocalDate.parse(txtcheckOut.getText());
					String type = txtroomtype.getText();
					String status = txtpaystatus.getText();
					
					if (roomnum > 20 || roomnum < 1 ) {
						JOptionPane.showMessageDialog(null, "Sorry, there are only 20 available rooms. Please input a valid room number.");
						return;
					}
					
					
					if (type.equalsIgnoreCase("Regular")) {
						type = "Regular";
					} else {
						type = "Deluxe";
					}
					
					if (!status.equalsIgnoreCase("Paid") && !status.equalsIgnoreCase("Pending")) {
						JOptionPane.showMessageDialog(null, "Payment status must be Paid or Pending only.");
						return;
					} else if (status.equalsIgnoreCase("Paid")){
						status = "Paid";
					} else {
						status = "Pending";
					}
					
					Booking booked = new Booking(txtID.getText(), txtname.getText(), roomnum, txtroomtype.getText(),
									checkIn, checkOut, total, status);
					
					
					update(booked);
					refresh();
					
					
				} catch (NumberFormatException x) {
					JOptionPane.showMessageDialog(null, "Total amount and room number must be in numbers only.");
					return;
				} catch (DateTimeParseException z) {
					JOptionPane.showMessageDialog(null, "Please input a valid date.");
					return;
				}
				
			}
			
		});
		
		btndelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				records = new ArrayList<>();
				int selectedrow = table.getSelectedRow();
				if (selectedrow == -1) {
					JOptionPane.showMessageDialog(null, "Please select a record to delete.");
					return;
				}
				
				delete();
				refresh();
				
			}
			
		});
		
		btnclear.addActionListener(e->{
			txtID.setText("");
			txtname.setText("");
			txtroomnum.setText("");
			txtroomtype.setText("");
			txtcheckIn.setText("");
			txtcheckOut.setText("");
			txttotal.setText("");
			txtpaystatus.setText("");
			
		});
		
		
		btnexit.addActionListener(e->{System.exit(0);});
		
		
	setLayout(new BorderLayout());	
	add(northPanel, BorderLayout.NORTH);
	add(scrollPane, BorderLayout.CENTER);
	refresh();
	
	setTitle("Meneses Final Exam");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(1200, 600);
	setLocationRelativeTo(null);
	setVisible(true);
	}
	
	//=========================== methods ==========================================
	
	private void add(Booking b) {
		try(FileWriter fw = new FileWriter(FILENAME, true)) {
			fw.write(b.toFileString(DELIMITER) + "\n");
			fw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "There was a problem adding record." +e.getMessage());
		}
	}
	
	private void update(Booking b) {
		int selectedrow = table.getSelectedRow();
		
		records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowIndex = 0;
			while((line = br.readLine()) != null) {
				if (rowIndex == selectedrow) {
					records.add(b.toFileString(DELIMITER));
				} else { 
					rowIndex++;
				}
			}
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, "There was a problem reading file from text." + z.getMessage());
		}
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
			for (String record : records) bw.write(record + "\n");
			bw.close();
			JOptionPane.showMessageDialog(null, "Record updated!");
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, "There was a problem updating the record." + z.getMessage());
		}
	}
	
	
	private void delete() {
		int selectedrow = table.getSelectedRow();
		int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?",
												"Confirm?", JOptionPane.YES_NO_OPTION);
		if (choice != JOptionPane.YES_OPTION) return;
		
		records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowIndex = 0;
			while((line = br.readLine()) != null) {
				if (rowIndex != selectedrow) records.add(line);
				rowIndex++;
			}
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, "There was a problem reading file from text." + z.getMessage());
		}
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
			for (String record : records) bw.write(record + "\n");
			bw.close();
			JOptionPane.showMessageDialog(null, "Record deleted successfully!");
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, "There was a problem deleting the record." + z.getMessage());
		}
		
		
	}
	
	
	private void refresh() {
		model.setRowCount(0);
		File file = new File(FILENAME);
		if (!file.exists()) return;
		
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			while((line = br.readLine())!= null) {
				String[] rows = line.split(DELIMITER);
				model.addRow(rows);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	
	//============== checking for duplicate ID =========================================
	
	private boolean isDuplicate() {
		File file = new File(FILENAME);
		if (!file.exists()) return false;
		
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			while((line = br.readLine())!= null) {
				String[] rows = line.split(DELIMITER);
				if (rows[0].equals(txtID.getText())) {
					JOptionPane.showMessageDialog(null, "This booking ID already exists.");
					return true;
				}
				
				if (rows[2].equals(txtroomnum.getText())) {
					JOptionPane.showMessageDialog(null, "This room is already occupied.");
					return true;
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return false;
	}
	
	

	public static void main(String[] args) {
		new RoomBookingSystem();

	}

}
