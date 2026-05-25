package Practice107;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
public class CarRentalSystem extends JFrame{

	private static final String DELIMITER = ":::";
	private static final String FILENAME = "CarRentalRecords.txt";
	
	private JTextField txtrentalID, txtcustomername, txtnumdays, txttotalcost;
	private String customername, carmodel, insurance;
	private int numdays, totalcost, rentalID;
	private JComboBox<String> comcarmodel;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scroll;
	private ButtonGroup bg;
	private ArrayList<String> temprecord;
	
	
	
	
	
	CarRentalSystem() {
		
		JLabel lblrentalID = new JLabel("Rental ID");
		add(lblrentalID).setBounds(30, 30, 100, 20);
		
		txtrentalID = new JTextField();
		add(txtrentalID).setBounds(30, 60, 150, 20);
		
		txtrentalID.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char a = e.getKeyChar();
				if (!Character.isDigit(a)) e.consume();
			}
		});
		
		JLabel lblcustomername = new JLabel("Customer Name");
		add(lblcustomername).setBounds(30, 100, 100, 20);
		
		txtcustomername = new JTextField();
		add(txtcustomername).setBounds(30, 130, 150, 20);
		
		txtcustomername.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char b = e.getKeyChar();
				if (Character.isDigit(b)) e.consume();
			}
			
		});
		
		
		
		JLabel lblnumdays = new JLabel("Number of days to borrow");
		add(lblnumdays).setBounds(30, 170, 100, 20);
		
		txtnumdays = new JTextField();
		add(txtnumdays).setBounds(30, 200, 100, 20);
		
		
		
		
		JLabel lblcarmodel = new JLabel("Car Model");
		add(lblcarmodel).setBounds(30, 240, 100, 20);
		
		
		comcarmodel = new JComboBox<>(new String[]
					{"Toyota Vios", "Honda City", "Mitsubishi Mirage", "Ford Ranger", "Toyota Fortuner"});
		
		add(comcarmodel).setBounds(30, 270, 150, 20);
		
		
		JLabel lblinsurance = new JLabel("Insurance");
		add(lblinsurance).setBounds(30, 310, 100, 20);
		
		JRadioButton rb1 = new JRadioButton("With Insurance");
		add(rb1).setBounds(30, 330, 150, 20);
		
		JRadioButton rb2 = new JRadioButton("Without Insurance");
		add(rb2).setBounds(30, 350, 150, 20);
		
		bg = new ButtonGroup();
		bg.add(rb1);
		bg.add(rb2);
		
		
		
		JLabel lbltotalcost = new JLabel("Total Cost:");
		add(lbltotalcost).setBounds(30, 390, 100, 20);
		
		txttotalcost = new JTextField();
		add(txttotalcost).setBounds(100, 390, 100, 20);
		txttotalcost.setEditable(false);
		
		
		//buttons
		
		JButton btnrent = new JButton("Rent");
		add(btnrent).setBounds(300, 400, 80, 30);
		
		JButton btnupdate = new JButton("Update");
		add(btnupdate).setBounds(400, 400, 80, 30);
		
		JButton btndelete = new JButton("Delete");
		add(btndelete).setBounds(500, 400, 80, 30);
		
		JButton btnclear = new JButton("Clear");
		add(btnclear).setBounds(600, 400, 80, 30);
		
		
		
		btnrent.addActionListener(e->{
			if (txtrentalID.getText().isEmpty() || txtcustomername.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Fill all necessary fields!");
				return;
			}
			
			
			rentalID = Integer.parseInt(txtrentalID.getText());
			customername = txtcustomername.getText();
			numdays = Integer.parseInt(txtnumdays.getText());
			carmodel = comcarmodel.getSelectedItem().toString();
			
			if (rb1.isSelected()) {
				insurance = "With Insurance";
			} else {
				insurance = "Without Insurance";
			}
			
			int rentalrate = 0;
			
			if (carmodel.equals("Toyota Fortuner")) {
				rentalrate = 3500;
			} else if (carmodel.equals("Honda City")) {
				rentalrate = 1800;
			} else if (carmodel.equals("Toyota Vios")) {
				rentalrate = 1500;
			} else if (carmodel.equals("Ford Ranger")) {
				rentalrate = 2500;
			} else {
				rentalrate = 1300;
			}
			
			
			totalcost = numdays * rentalrate;
			txttotalcost.setText(String.valueOf(totalcost));
			
			
			rent();
			reload();
			
			
		});
		
		btnupdate.addActionListener(e->{
			
			if (txtrentalID.getText().isEmpty() || txtcustomername.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Fill all necessary fields!");
				return;
			}
			
			
			rentalID = Integer.parseInt(txtrentalID.getText());
			customername = txtcustomername.getText();
			numdays = Integer.parseInt(txtnumdays.getText());
			carmodel = comcarmodel.getSelectedItem().toString();
			
			if (rb1.isSelected()) {
				insurance = "With Insurance";
			} else {
				insurance = "Without Insurance";
			}
			
			int rentalrate = 0;
			
			if (carmodel.equals("Toyota Fortuner")) {
				rentalrate = 3500;
			} else if (carmodel.equals("Honda City")) {
				rentalrate = 1800;
			} else if (carmodel.equals("Toyota Vios")) {
				rentalrate = 1500;
			} else if (carmodel.equals("Ford Ranger")) {
				rentalrate = 2500;
			} else {
				rentalrate = 1300;
			}
			
			
			totalcost = numdays * rentalrate;
			txttotalcost.setText(String.valueOf(totalcost));
			
			update();
			reload();
			
		});
		
		btndelete.addActionListener(e->{
			delete();
			reload();
		});
		
		btnclear.addActionListener(e->{
			clear();
		});
		
		
		
		
		
		
		
		
		
		String[] cols = {"Rental ID", "Customer Name", "Car Model", "Days", "Insurance", "Total Cost"};
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		scroll = new JScrollPane(table);
		scroll.setBounds(200, 30, 700, 300);
		add(scroll);
		reload();
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				
				if (row != -1) {
					txtrentalID.setText(model.getValueAt(row, 0).toString());
					txtcustomername.setText(model.getValueAt(row, 1).toString());
					comcarmodel.setSelectedItem(model.getValueAt(row, 2).toString());
					txtnumdays.setText(model.getValueAt(row, 3).toString());
					insurance = model.getValueAt(row, 4).toString();
					
					if(rb1.isSelected()) {
						insurance = "With Insurance";
					} else {
						insurance = "Without Insurance";
					}
					
					txttotalcost.setText(model.getValueAt(row, 5).toString());
				
				}
				
			
			}
		});
		
		
		
		
		
		
		
		
		
		
		
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(950, 500);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	
	
	// methods
	
		void rent() {
			try (FileWriter fw = new FileWriter(FILENAME, true)) {
				fw.write(rentalID + DELIMITER + customername + DELIMITER + carmodel
						+ DELIMITER + numdays + DELIMITER + insurance + DELIMITER +
						totalcost + "\n");
				
				fw.close();
				
				
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	
	
	
	
		void delete() {
			int selectedrow = table.getSelectedRow();
			
			if (selectedrow == -1) {
				JOptionPane.showMessageDialog(null, "Choose a record to delete");
				return;
			}
			
			int confirm = JOptionPane.showConfirmDialog
						(null, "Delete record?", "Confirm?", JOptionPane.YES_NO_OPTION);
			
			
			if (confirm != JOptionPane.YES_OPTION) return;
			
			
			temprecord = new ArrayList<>();
			
			try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
				String line;
				int rowindex = 0;
				
				while ((line = br.readLine()) != null) {
					if (rowindex != selectedrow) temprecord.add(line);
					rowindex++;
				
				}
				
			} catch (IOException x) {
				JOptionPane.showMessageDialog(null, x);
			}
			
			
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
				
				for (String record : temprecord) bw.write(record + "\n");
				
				
				
			} catch (IOException z) {
				JOptionPane.showMessageDialog(null, z);
			}
			
			
			
			
			
		}
		
		
		
		void update() {
		int selectedrow = table.getSelectedRow();
			
			if (selectedrow == -1) {
				JOptionPane.showMessageDialog(null, "Choose a record to delete");
				return;
			}
			
		
			
			temprecord = new ArrayList<>();
			
			try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
				String line;
				int rowindex = 0;
				
				while ((line = br.readLine()) != null) {
					if (rowindex == selectedrow) {
						String updatedinfo = rentalID + DELIMITER + customername + DELIMITER + carmodel
								+ DELIMITER + numdays + DELIMITER + insurance + DELIMITER +
								totalcost;
						temprecord.add(updatedinfo);
						
						
						} else {
							temprecord.add(line);
						}
					rowindex++;
				
				}
				
			} catch (IOException x) {
				JOptionPane.showMessageDialog(null, x);
			}
			
			
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
				
				for (String record : temprecord) bw.write(record + "\n");
				
				
				
			} catch (IOException z) {
				JOptionPane.showMessageDialog(null, z);
			}
			
			
			
			
			
		}
	
		
		
		
		
		void reload() {
			model.setRowCount(0);
			
			File file = new File(FILENAME);
			if (!file.exists()) return;
			
			try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
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
			
			txtrentalID.setText("");
			txtcustomername.setText("");
			txtnumdays.setText("");
			bg.clearSelection();
			comcarmodel.setSelectedIndex(0);
			txttotalcost.setText("");
			
			
			
			
			
			
			
		}
	
			
		
		
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new CarRentalSystem();
	}

}
