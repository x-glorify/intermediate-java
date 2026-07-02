package InterventionProgram;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
public class FlightBookingSystem extends JFrame{
	private static final String FILENAME = "FlightBooking.txt";
	private static final String DELIMITER = "::";
	
	private static JTextField txtbookingID, txtpassengername, txtnumpassengers, txttotalfare;
	private static String bookingID, passengername, destination, flightclass;
	private static int numpassengers, totalfare;
	
	private static DefaultTableModel model;
	private static JTable table;
	private static JComboBox comdestination;
	private static JRadioButton rb_eco, rb_business;
	private static ButtonGroup bg;
	private static JScrollPane scroll;
	private static ArrayList<String> records;
	
	FlightBookingSystem() {
		
		// ============== TABLE =================================================
		
		String[] cols =
			{"Booking ID", "Passenger Name", "Destination", "No. of Passengers", "Class", "Total Fare"};
		
		
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		scroll = new JScrollPane(table);
		
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					txtbookingID.setText(model.getValueAt(row, 0).toString());
					txtpassengername.setText(model.getValueAt(row, 1).toString());
					comdestination.setSelectedItem(model.getValueAt(row, 2).toString());
					txtnumpassengers.setText(model.getValueAt(row, 3).toString());
					destination = model.getValueAt(row, 4).toString();
					if (destination.equals("Economy")) {
						rb_eco.isSelected();
					} else {
						rb_business.isSelected();
					}
					
				}
			}
		});
		
		
		
		// ============ LABELS AND INPUT FIELDS ==================================
		
		JPanel bottomPanel = new JPanel(new BorderLayout());

		JPanel formPanel = new JPanel(new GridLayout(2, 6, 5, 5 ));
			formPanel.add(new JLabel("Booking ID"));
			formPanel.add(new JLabel("Passenger Name"));
			formPanel.add(new JLabel("Destination"));
			formPanel.add(new JLabel("No. of passengers"));
			formPanel.add(new JLabel("Class"));
			formPanel.add(new JLabel("Total Fare"));
			
			txtbookingID = new JTextField();
			txtpassengername = new JTextField();
			comdestination = new JComboBox<>(new String[] {"Manila", "Cebu", "Davao", "Boracay", "Palawan"});
			txtnumpassengers = new JTextField();
			
			JPanel flightclassPanel = new JPanel(new GridLayout(2,1));
			rb_eco = new JRadioButton("Economy");
			rb_business = new JRadioButton("Business");
			bg = new ButtonGroup();
			bg.add(rb_eco);
			bg.add(rb_business);
			flightclassPanel.add(rb_eco);
			flightclassPanel.add(rb_business);
			
			txttotalfare = new JTextField();
			txttotalfare.setEditable(false);
			
			formPanel.add(txtbookingID);
			formPanel.add(txtpassengername);
			formPanel.add(comdestination);
			formPanel.add(txtnumpassengers);
			formPanel.add(flightclassPanel);
			formPanel.add(txttotalfare);
		
			
		
		
		
		//=======================BUTTONS==========================================
			
			
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
		
		JButton btnadd		= new JButton("Add");
		JButton btnupdate	= new JButton("Update");
		JButton btndelete	= new JButton("Delete");
		JButton btnclear	= new JButton("Clear");
		
		btnPanel.add(btnadd);
		btnPanel.add(btnupdate);
		btnPanel.add(btndelete);
		btnPanel.add(btnclear);
		
		
		bottomPanel.add(formPanel, BorderLayout.CENTER);
		bottomPanel.add(btnPanel, BorderLayout.SOUTH);
		bottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		
		
		
		//===================ACTION LISTENERS ========================================
		
		
		btnadd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtbookingID.getText().isEmpty() || txtpassengername.getText().isEmpty() ||
					txtnumpassengers.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill out all fields!");
					return;
				}
				
				
				bookingID		= txtbookingID.getText();
				passengername	= txtpassengername.getText();
				destination		= comdestination.getSelectedItem().toString();
				flightclass		= rb_eco.isSelected() ? "Economy" : "Business";
				
				try {
					numpassengers	= Integer.parseInt(txtnumpassengers.getText());
					
				} catch (NumberFormatException x) {
					JOptionPane.showMessageDialog(null, "Number of passengers must be numbers only.");
					return;
				}
				
				
				
				int subtotal = 0;
				
				if (destination.equals("Manila")) {
					subtotal += 2500;
				} else if (destination.equals("Cebu")) {
					subtotal += 3000;
				} else if (destination.equals("Davao")) {
					subtotal += 3500;
				} else if (destination.equals("Boracay")) {
					subtotal += 4000;
				} else {
					subtotal += 4500;
				}
				
				if (flightclass.equals("Business")) {
					subtotal += 2500;
				} else {
					subtotal += 0;
				}
				
				totalfare = subtotal * numpassengers;
				txttotalfare.setText(String.valueOf(totalfare));
				
				
				add();
				refresh();
				
			}
			
		});
		
		
		btnupdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (txtbookingID.getText().isEmpty() || txtpassengername.getText().isEmpty() ||
						txtnumpassengers.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please fill out all fields!");
						return;
					}
					
					
					bookingID		= txtbookingID.getText();
					passengername	= txtpassengername.getText();
					destination		= comdestination.getSelectedItem().toString();
					flightclass		= rb_eco.isSelected() ? "Economy" : "Business";
					
					
					
					try {
						numpassengers	= Integer.parseInt(txtnumpassengers.getText());
						
					} catch (NumberFormatException x) {
						JOptionPane.showMessageDialog(null, "Number of passengers must be numbers only.");
						return;
					}
					
					
					
					
					int subtotal = 0;
					
					if (destination.equals("Manila")) {
						subtotal += 2500;
					} else if (destination.equals("Cebu")) {
						subtotal += 3000;
					} else if (destination.equals("Davao")) {
						subtotal += 3500;
					} else if (destination.equals("Boracay")) {
						subtotal += 4000;
					} else {
						subtotal += 4500;
					}
					
					if (flightclass.equals("Business")) {
						subtotal += 2500;
					} else {
						subtotal += 0;
					}
					
					totalfare = subtotal * numpassengers;
					txttotalfare.setText(String.valueOf(totalfare));
					
					update();
					refresh();
				
				
			}
			
		});
		
		
		
		btndelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				delete();
				refresh();
				
				
			}
			
		});
		
		btnclear.addActionListener(e->{
			txtbookingID.setText("");
			txtpassengername.setText("");
			comdestination.setSelectedIndex(0);
			txtnumpassengers.setText("");
			bg.clearSelection();
			txttotalfare.setText("");
		});
		
		
		
		
		
		
		
		
		
		setLayout(new BorderLayout());
		add(scroll, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		
		refresh();
		
		
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Flight Booking System");
		setSize(900, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	
	
	
	// ====================== METHODS =======================================================	
	
	private void add() {
		try(FileWriter fw = new FileWriter(FILENAME, true)) {
			fw.write(bookingID + DELIMITER + passengername + DELIMITER + destination + DELIMITER + numpassengers 
					+ DELIMITER + flightclass + DELIMITER + totalfare + "\n");
			fw.close();
			
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
	}
	
	
	
	
	private void update() {
		int selectedrow = table.getSelectedRow();
		if (selectedrow == -1) {
			JOptionPane.showMessageDialog(null, "Select a record to update");
			return;
		}
		
		
		
		records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowIndex = 0;
			while ((line = br.readLine()) != null) {
				if (rowIndex == selectedrow) {
					String updatedinfo = bookingID + DELIMITER + passengername + DELIMITER + destination +
										DELIMITER + numpassengers + DELIMITER + flightclass + DELIMITER + totalfare;
					records.add(updatedinfo);
				} else {
					records.add(line);
				}
				rowIndex++;
			}
			
			
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, x.getMessage());
		}
		
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
			for (String record:records) bw.write(record + "\n");
			JOptionPane.showMessageDialog(null, "Record updated!");
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, z.getMessage());
		}
		
		
	}
	
	private void delete() {
		int selectedrow = table.getSelectedRow();
		if(selectedrow == -1) {
			JOptionPane.showMessageDialog(null, "Select a record to delete.");
			return;
		}
		int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?",
													"Delete?", JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION) return;
		
		
		records =  new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowIndex = 0;
			while((line = br.readLine()) != null) {
				if (rowIndex != selectedrow) {
					records.add(line);
				}
				rowIndex++;
			}
			
			
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, x.getMessage());
		}
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
			for (String record:records) bw.write(record + "\n");
			JOptionPane.showMessageDialog(null, "Record deleted!");
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, z.getMessage());
		}
		
		
	}
	
	
	private void refresh() {
		model.setRowCount(0);
		File file = new File(FILENAME);
		if (!file.exists()) return;
		
		records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			
			while((line = br.readLine()) != null) {
				String[] rows = line.split(DELIMITER);
				model.addRow(rows);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static void main(String[] args) {
		new FlightBookingSystem();

	}

}
