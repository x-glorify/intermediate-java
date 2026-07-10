package InterventionProgram;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.time.*;
import java.time.format.*;
public class MovieTicket extends JFrame{
	
	private final String FILENAME = "ticketsbooked.txt";
	private final String DELIMITER = "::";
	private JTextField txtreceiptNum, txtmovieTitle, txtcustomerName, txtseatnum, txtquantity,
						txtprice, txtpayStatus;
	
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scroll;
	private ArrayList<String> records = new ArrayList<>();
	
	
	MovieTicket() {
		JPanel northPanel = new JPanel(new BorderLayout());
		JPanel inputPanel = new JPanel(new GridLayout(2, 7, 5,5));
		
		
		inputPanel.add(new JLabel("Receipt Number:"));
		inputPanel.add(new JLabel("Movie Title:"));
		inputPanel.add(new JLabel("Customer Name:"));
		inputPanel.add(new JLabel("Seat Number:"));
		inputPanel.add(new JLabel("Quantity:"));
		inputPanel.add(new JLabel("Price:"));
		inputPanel.add(new JLabel("Payment Status:"));
		
		txtreceiptNum = new JTextField();
		txtmovieTitle = new JTextField();
		txtcustomerName = new JTextField();
		txtseatnum = new JTextField();
		txtquantity = new JTextField();
		txtprice = new JTextField();
		txtpayStatus = new JTextField();
		
		inputPanel.add(txtreceiptNum);
		inputPanel.add(txtmovieTitle);
		inputPanel.add(txtcustomerName);
		inputPanel.add(txtseatnum);
		inputPanel.add(txtquantity);
		inputPanel.add(txtprice);
		inputPanel.add(txtpayStatus);
		
		JPanel btnPanel = new JPanel(new FlowLayout());
		JButton btnadd = new JButton("Add");
		JButton btnview = new JButton("View");
		JButton btndelete = new JButton("Delete");
		JButton btnupdate = new JButton("Update");
		
		btnPanel.add(btnadd);
		btnPanel.add(btnview);
		btnPanel.add(btnupdate);
		btnPanel.add(btndelete);
		
		northPanel.add(inputPanel, BorderLayout.CENTER);
		northPanel.add(btnPanel, BorderLayout.SOUTH);
		northPanel.setBorder(BorderFactory.createTitledBorder("Movie Ticket Details"));
		
		String[] cols = {"Receipt Number", "Movie Title", "Customer Name", "Seat Number",
						"Quantity", "Price", "Payment Status"};
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		scroll = new JScrollPane(table);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if(row != -1) {
					txtreceiptNum.setText(model.getValueAt(row, 0).toString());
					txtmovieTitle.setText(model.getValueAt(row, 1).toString());
					txtcustomerName.setText(model.getValueAt(row, 2).toString());
					txtseatnum.setText(model.getValueAt(row, 3).toString());
					txtquantity.setText(model.getValueAt(row, 4).toString());
					txtprice.setText(model.getValueAt(row, 5).toString());
					txtpayStatus.setText(model.getValueAt(row, 6).toString());
			
				}
			}
		});
		
		
		
		btnadd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					if (txtreceiptNum.getText().isEmpty() || txtcustomerName.getText().isEmpty() || txtseatnum.getText().isEmpty() ||
						txtquantity.getText().isEmpty() || txtprice.getText().isEmpty() || txtpayStatus.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please do not leave the fields empty.");
						return;
					}
					
					if (!txtpayStatus.getText().equalsIgnoreCase("Pending") && (!txtpayStatus.getText().equalsIgnoreCase("Paid"))) {
						JOptionPane.showMessageDialog(null, "Payment Status must be Paid or Pending only");
						return;
					}
					
					if (duplicateCheck()) return;
					
					
					int seatnum = Integer.parseInt(txtseatnum.getText());
					int qty = Integer.parseInt(txtquantity.getText());
					int price = Integer.parseInt(txtprice.getText());
					
					Movie movie = new Movie(txtreceiptNum.getText(), txtmovieTitle.getText(),
							txtcustomerName.getText(), seatnum, qty, price, txtpayStatus.getText());
					
					add(movie);
					clear();
					
				} catch (NumberFormatException err) {
					JOptionPane.showMessageDialog(null, "Seat number, quantity, and price must be in numbers only.");
					return;
				}
				
			}
			
		});
		
		
		btnview.addActionListener(e->{
			view();
		});
		
		
		
		btnupdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int selectedrow = table.getSelectedRow();
					if (selectedrow == -1) {
						JOptionPane.showMessageDialog(null, "Please select a record to delete.");
						return;
					}
					
					if (txtreceiptNum.getText().isEmpty() || txtcustomerName.getText().isEmpty() || txtseatnum.getText().isEmpty() ||
						txtquantity.getText().isEmpty() || txtprice.getText().isEmpty() || txtpayStatus.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please do not leave the fields empty.");
						return;
					}
					
					if (!txtpayStatus.getText().equalsIgnoreCase("Pending") && (!txtpayStatus.getText().equalsIgnoreCase("Paid"))) {
						JOptionPane.showMessageDialog(null, "Payment Status must be Paid or Pending only");
						return;
					}
					
					
					int seatnum = Integer.parseInt(txtseatnum.getText());
					int qty = Integer.parseInt(txtquantity.getText());
					int price = Integer.parseInt(txtprice.getText());
					
					Movie movie = new Movie(txtreceiptNum.getText(), txtmovieTitle.getText(),
							txtcustomerName.getText(), seatnum, qty, price, txtpayStatus.getText());
					
					update(movie);
					clear();
					loadTable();
					
				} catch (NumberFormatException err) {
					JOptionPane.showMessageDialog(null, "Seat number, quantity, and price must be in numbers only.");
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
			clear();
			
		});
		
		
		
		
		
		
		setLayout(new BorderLayout());
		add(northPanel, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		
		setTitle("Movie Ticket System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 500);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	
	
	//============= METHODS =======================================
	
	
	private void add(Movie m) {
		try (FileWriter fw = new FileWriter(FILENAME, true)) {
			fw.write(m.toFileString(DELIMITER) + "\n");
			fw.close();
			JOptionPane.showMessageDialog(null, "Record added successfully!");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "There was a problem writing file to text."
										+ e.getMessage());
		}
		
	}
	
	
	private void update(Movie m) {
		int selectedrow = table.getSelectedRow();
		
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
			JOptionPane.showMessageDialog(null, "Error updating record. Please try again later."
										+ x.getMessage());
		} 
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
			for (String record : records) bw.write(record + "\n");
			bw.close();
			JOptionPane.showMessageDialog(null, "Record updated!");
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, "There was an error writing file to text." + z.getMessage());
		}
		
	}
	
	
	private void delete() {
		int selectedrow = table.getSelectedRow();
		int confirm = JOptionPane.showConfirmDialog(null, "Delete this record?", "Confirm",
					JOptionPane.YES_NO_OPTION);
		
		if (confirm != JOptionPane.YES_OPTION) return;
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowindex = 0;
			while((line = br.readLine()) != null) {
				if (rowindex != selectedrow) records.add(line);
				rowindex++;
			}
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, "Error updating record. Please try again later."
										+ x.getMessage());
		} 
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
			for (String record : records) bw.write(record + "\n");
			bw.close();
			JOptionPane.showMessageDialog(null, "Record updated!");
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, "There was an error writing file to text." + z.getMessage());
		}
		
	}
	
	private void view() {
		model.setRowCount(0);
		File file = new File(FILENAME);
		if(!file.exists()) return;
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			while((line = br.readLine()) != null) {
				String[] data = line.split(DELIMITER);
				model.addRow(new Object[] {
						data[0],
						data[1],
						data[2],
						data[3],
						data[4],
						data[5],
						data[6],
						
					
				});
			}
		} catch (IOException err) {
			JOptionPane.showMessageDialog(null, err.getMessage());
		}
	
	}
	
	
	
	private boolean duplicateCheck() {
		model.setRowCount(0);
		File file = new File(FILENAME);
		if(!file.exists()) return false;
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			while((line = br.readLine()) != null) {
				String[] rows = line.split(DELIMITER);
				if (rows[0].equals(txtreceiptNum.getText())) {
					JOptionPane.showMessageDialog(null, "This receipt number already exists.");
					return true;
				}
				
				if (rows[3].equals(txtseatnum.getText())) {
					JOptionPane.showMessageDialog(null, "Seat number already occupied.");
					return true;
				}
			}
		} catch (IOException err) {
			JOptionPane.showMessageDialog(null, err.getMessage());
		}
		return false;
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
			JOptionPane.showMessageDialog(null, err.getMessage());
		}
	}
	
	
	private void clear() {
		txtreceiptNum.setText("");
		txtmovieTitle.setText("");
		txtcustomerName.setText("");
		txtseatnum.setText("");
		txtquantity.setText("");
		txtprice.setText("");
		txtpayStatus.setText("");
		
	}
	

	public static void main(String[] args) {
		new MovieTicket();
	

	}

}
