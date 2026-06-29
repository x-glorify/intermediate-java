package Practice107;
import javax.swing.*;
import java.util.*;
import java.io.*;
import javax.swing.table.*;
import java.awt.event.*;
public class MovieTicketSystem extends JFrame{
		private static final String FILENAME = "movie_tickets.txt";
		private static final String DELIMITER = "::";
		
		private JTextField txtreceiptID, txtmovietitle, txtname, txtseatnum, txtquantity, txtprice, txtstatus;
		private String receiptID, movietitle, name, status;
		private int seatnum, quantity, price;
		private JTable table;
		private DefaultTableModel model;
		private JScrollPane scroll;
		private ArrayList<String> records;
	
	
	
	MovieTicketSystem() {
		
		
		JLabel lblreceiptID = new JLabel("Receipt ID");
		add(lblreceiptID).setBounds(30, 300, 100, 20);
		
		txtreceiptID = new JTextField();
		add(txtreceiptID).setBounds(30, 330, 120, 20);
		
		JLabel lblmovietitle = new JLabel("Movie Title");
		add(lblmovietitle).setBounds(160, 300, 100, 20);
		
		txtmovietitle = new JTextField();
		add(txtmovietitle).setBounds(160, 330, 120, 20);
		
		JLabel lblname = new JLabel("Customer Name");
		add(lblname).setBounds(290, 300, 100, 20);
		
		txtname = new JTextField();
		add(txtname).setBounds(290, 330, 120, 20);
		
		txtname.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char a = e.getKeyChar();
				if(Character.isDigit(a)) e.consume();
			}
		});
		
		JLabel lblseatnum = new JLabel("Seat Number");
		add(lblseatnum).setBounds(420, 300, 100, 20);
		
		txtseatnum = new JTextField();
		add(txtseatnum).setBounds(420, 330, 120, 20);
		
		txtseatnum.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char b = e.getKeyChar();
				if(!Character.isDigit(b)) e.consume();
			}
		});
		
		JLabel lblquantity = new JLabel("Ticket Quantity");
		add(lblquantity).setBounds(550, 300, 100, 20);
		
		txtquantity = new JTextField();
		add(txtquantity).setBounds(550, 330, 120, 20);
		
		txtquantity.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c)) e.consume();
			}
		});
		
		JLabel lblprice = new JLabel("Price");
		add(lblprice).setBounds(680, 300, 100, 20);
		
		txtprice = new JTextField();
		add(txtprice).setBounds(680, 330, 120, 20);
		
		txtprice.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char d = e.getKeyChar();
				if(!Character.isDigit(d)) e.consume();
			}
		});
		
		JLabel lblstatus = new JLabel("Payment Status");
		add(lblstatus).setBounds(810, 300, 100, 20);
		
		txtstatus = new JTextField();
		add(txtstatus).setBounds(810, 330, 120, 20);
		
		txtstatus.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char g = e.getKeyChar();
				if(Character.isDigit(g)) e.consume();
			}
		});
		
		//buttons
		
		JButton btnadd = new JButton("Add");
		JButton btnview = new JButton("View");
		JButton btnupdate = new JButton("Update");
		JButton btndelete = new JButton("Delete");
		
		add(btnadd).setBounds(200, 380, 100, 20);
		add(btnview).setBounds(300, 380, 100, 20);
		add(btnupdate).setBounds(400, 380, 100, 20);
		add(btndelete).setBounds(500, 380, 100, 20);
		
		
		
		//button functions
		
		btnadd.addActionListener(e ->{
			
		if (txtreceiptID.getText().isEmpty() || txtmovietitle.getText().isEmpty() || txtname.getText().isEmpty() || 
			txtseatnum.getText().isEmpty() || txtquantity.getText().isEmpty() || txtprice.getText().isEmpty()
			|| txtstatus.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please fill in all necessary fields!");
			return;
		}
			
			
			
		receiptID = txtreceiptID.getText();
		movietitle = txtmovietitle.getText();
		name = txtname.getText();
		seatnum = Integer.parseInt(txtseatnum.getText());
		quantity = Integer.parseInt(txtquantity.getText());
		price = Integer.parseInt(txtprice.getText());
		status = txtstatus.getText();
		

		if (!status.equalsIgnoreCase("Paid") && !status.equalsIgnoreCase("Pending")) {
			JOptionPane.showMessageDialog(null, "Status must be 'Paid' or 'Pending' only.");
			return;
		}
		
		add();
		clear();
		view();
		});
		
		
		
		btnview.addActionListener(e ->{
			view();
		});
		
		
		
		btnupdate.addActionListener(e ->{
			if (txtreceiptID.getText().isEmpty() || txtmovietitle.getText().isEmpty() || txtname.getText().isEmpty() || 
					txtseatnum.getText().isEmpty() || txtquantity.getText().isEmpty() || txtprice.getText().isEmpty()
					|| txtstatus.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill in all necessary fields!");
					return;
				}
				
					
					
				receiptID = txtreceiptID.getText();
				movietitle = txtmovietitle.getText();
				name = txtname.getText();
				seatnum = Integer.parseInt(txtseatnum.getText());
				quantity = Integer.parseInt(txtquantity.getText());
				price = Integer.parseInt(txtprice.getText());
				status = txtstatus.getText();
				

				if (!status.equalsIgnoreCase("Paid") && !status.equalsIgnoreCase("Pending")) {
					JOptionPane.showMessageDialog(null, "Status must be 'Paid' or 'Pending' only.");
					return;
				}
				
				update();
				view();
		});
		
		
		
		btndelete.addActionListener(e ->{
			delete();
			view();
		});
		
		
		
		
		
		
		// table
		
		String[] cols = {"Receipt ID", "Movie Title", "Customer Name", "Seat Number",
						"Ticket Quantity", "Price", "Payment Status"};
		
		
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		scroll = new JScrollPane(table);
		scroll.setBounds(30, 30, 850, 200);
		add(scroll);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					txtreceiptID.setText(model.getValueAt(row, 0).toString());
					txtmovietitle.setText(model.getValueAt(row, 1).toString());
					txtname.setText(model.getValueAt(row, 2).toString());
					txtseatnum.setText(model.getValueAt(row, 3).toString());
					txtquantity.setText(model.getValueAt(row, 4).toString());
					txtprice.setText(model.getValueAt(row, 5).toString());
					txtstatus.setText(model.getValueAt(row, 6).toString());
					
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
	
	
	void add() {
		try (FileWriter fw = new FileWriter(FILENAME, true)) {
			
			fw.write(receiptID + DELIMITER + movietitle + DELIMITER + name + DELIMITER + seatnum
					+ DELIMITER + quantity + DELIMITER + price + DELIMITER + status + "\n");
			fw.close();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	void view() {
		model.setRowCount(0);
		
		File file = new File(FILENAME);
		if(!file.exists()) return;
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] row = line.split(DELIMITER);
				model.addRow(row);
			}
			
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	
	void update() {
		int selectedrow = table.getSelectedRow();
		if (selectedrow == -1) {
			JOptionPane.showMessageDialog(null, "Select a record to update.");
			return;
		}
		
		records = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowindex = 0;
			while((line = br.readLine()) != null) {
				if (rowindex == selectedrow) {
					String updatedinfo = receiptID + DELIMITER + movietitle + DELIMITER + name + DELIMITER + seatnum
							+ DELIMITER + quantity + DELIMITER + price + DELIMITER + status;
					records.add(updatedinfo);
				} else {
					records.add(line);
				}
				rowindex++;
			}
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, x);
		}
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
			for (String record : records) bw.write(record + "\n");
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, z);
		}
	}
	
	
	void delete() {
		int selectedrow = table.getSelectedRow();
		if (selectedrow == -1) {
			JOptionPane.showMessageDialog(null, "Select a record to update.");
			return;
		}
		
		int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?", "Confirm", JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION) return;
		
		records = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowindex = 0;
			while((line = br.readLine()) != null) {
				if (rowindex != selectedrow) records.add(line); 
				rowindex++;
			}
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, x);
		}
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
			for (String record : records) bw.write(record + "\n");
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, z);
		}
		
	}
	
	
	void clear() {
		
		txtreceiptID.setText("");
		txtmovietitle.setText("");
		txtname.setText("");
		txtseatnum.setText("");
		txtquantity.setText("");
		txtprice.setText("");
		txtstatus.setText("");
		
	}
	
	
	
	
	public static void main(String[] args) {
		new MovieTicketSystem();

	}

}
