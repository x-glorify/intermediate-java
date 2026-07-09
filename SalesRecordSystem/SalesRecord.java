package InterventionProgram;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class SalesRecord extends JFrame{
	private final String FILENAME = "SalesRecord.txt";
	private final String DELIMITER = "::";
	
	private JTextField txtID, txtagent, txtproduct, txtsold, txtprice, txttotal, txtdate;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private ArrayList<String> records;
	
	
	SalesRecord() {
		String[] cols = {"Sales ID", "Sales Agent", "Product",
				"Unit Sold", "Unit Price", "Total Sales", "Sale Date"};
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(table);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					txtID.setText(model.getValueAt(row, 0).toString());
					txtagent.setText(model.getValueAt(row, 1).toString());
					txtproduct.setText(model.getValueAt(row, 2).toString());
					txtsold.setText(model.getValueAt(row, 3).toString());
					txtprice.setText(model.getValueAt(row, 4).toString());
					txttotal.setText(model.getValueAt(row, 5).toString());
					txtdate.setText(model.getValueAt(row, 6).toString());
				}
				
			
			}
		});
		
		
		
		JPanel southPanel = new JPanel(new BorderLayout());
		JPanel formsPanel = new JPanel(new GridLayout(2, 7, 5, 5));
		
		formsPanel.add(new JLabel("Sales ID"));
		formsPanel.add(new JLabel("Agent Name"));
		formsPanel.add(new JLabel("Product"));
		formsPanel.add(new JLabel("Unit Sold"));
		formsPanel.add(new JLabel("Unit Price"));
		formsPanel.add(new JLabel("Total Sales"));
		formsPanel.add(new JLabel("Date"));
		
		
		txtID = new JTextField();
		txtagent = new JTextField();
		txtproduct = new JTextField();
		txtsold = new JTextField();
		txtprice = new JTextField();
		txttotal = new JTextField();
		txttotal.setEditable(false);
		txtdate = new JTextField();
		
		
		formsPanel.add(txtID);
		formsPanel.add(txtagent);
		formsPanel.add(txtproduct);
		formsPanel.add(txtsold);
		formsPanel.add(txtprice);
		formsPanel.add(txttotal);
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
		
		
		
		southPanel.add(formsPanel, BorderLayout.CENTER);
		southPanel.add(btnPanel, BorderLayout.SOUTH);
		southPanel.setBorder(BorderFactory.createTitledBorder("Sales Details:"));
		
		
		
		btnadd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					if (txtID.getText().isEmpty() || txtagent.getText().isEmpty() || txtproduct.getText().isEmpty() ||
							txtsold.getText().isEmpty() || txtprice.getText().isEmpty() || txtdate.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please fill in all the fields.");
						return;
					}
					
					if (!txtdate.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
						JOptionPane.showMessageDialog(null, "Date must be in format YYYY-MM-DD");
						return;
					}

					// duplicate check

					if(duplicateID()) {
						JOptionPane.showMessageDialog(null, "This sales ID already exists.");
						return;
						
					}
					
					int price = Integer.parseInt(txtprice.getText());
					int sold = Integer.parseInt(txtsold.getText());
					int total = price * sold;
					txttotal.setText(String.valueOf(total));
					
					
					Sales sale = new Sales(txtID.getText(), txtagent.getText(), txtproduct.getText(),
											sold, price, total, txtdate.getText());
					
					add(sale);
					loadTable();
					
					
				} catch (NumberFormatException err) {
					JOptionPane.showMessageDialog(null, "Units and sales must be numbers only.");
					return;
				}
				
			}
			
		});
		
		
		btnupdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedrow = table.getSelectedRow();
				if(selectedrow == -1) {
					JOptionPane.showMessageDialog(null, "Please choose a record to update.");
					return;
				}
try {
					
					if (txtID.getText().isEmpty() || txtagent.getText().isEmpty() || txtproduct.getText().isEmpty() ||
							txtsold.getText().isEmpty() || txtprice.getText().isEmpty()  || txtdate.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please fill in all the fields.");
						return;
					}
					
					if (!txtdate.getText().matches("\\d{4}-\\d{2}-\\{2}")) {
						JOptionPane.showMessageDialog(null, "Date must be in format YYYY-MM-DD");
						return;
					}


					if(duplicateID()) {
						JOptionPane.showMessageDialog(null, "This sales ID already exists.");
						return;
						
					}
					
					int price = Integer.parseInt(txtprice.getText());
					int sold = Integer.parseInt(txtsold.getText());
					int total = price * sold;
					txttotal.setText(String.valueOf(total));
					
					
					Sales sale = new Sales(txtID.getText(), txtagent.getText(), txtproduct.getText(),
											sold, price, total,
											txtdate.getText());
					
					update(sale);
					loadTable();
					
					
				} catch (NumberFormatException err) {
					JOptionPane.showMessageDialog(null, "Units and sales must be numbers only.");
					return;
				}
				
			}
			
		});
		
		
		btndelete.addActionListener(e->{
			int selectedrow = table.getSelectedRow();
			if(selectedrow == -1) {
				JOptionPane.showMessageDialog(null, "Please choose a record to delete");
				return;
			}
			int confirm = JOptionPane.showConfirmDialog(null, "Delete this record?", "Confirm", JOptionPane.YES_NO_OPTION);
			if (confirm != JOptionPane.YES_OPTION) return;
			
			delete();
			loadTable();
			
		});
		
		btnclear.addActionListener(e->{
			txtID.setText("");
			txtagent.setText("");
			txtproduct.setText("");
			txtsold.setText("");
			txtprice.setText("");
			txttotal.setText("");
			txtdate.setText("");
			
		});
		
		
		
		
	setLayout(new BorderLayout());
	add(scrollPane, BorderLayout.CENTER);
	add(southPanel, BorderLayout.SOUTH);
	loadTable();
		
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(900, 500);	
	setLocationRelativeTo(null);
	setVisible(true);
		
	}
	
	
	//========= methods ========================================
	
	
	// {"Sales ID", "Sales Agent", "Product",
	// "Unit Sold", "Unit Price", "Total Sales", "Sale Date"};
	
	
	private void add(Sales sale) {
		try (FileWriter fw = new FileWriter(FILENAME, true)) {
			
			fw.write(sale.getID() + DELIMITER +
					sale.getname() + DELIMITER +
					sale.getproduct() + DELIMITER +
					sale.getsold() + DELIMITER +
					sale.getprice() + DELIMITER +
					sale.gettotal() + DELIMITER +
					sale.getdate() + "\n");
			fw.close();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
	}
	
	
	
	private void update(Sales sale) {
		int selectedrow = table.getSelectedRow();
		
		records = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowindex = 0;
			while((line = br.readLine()) != null) {
				if(rowindex == selectedrow) {
					String updatedinfo = sale.getID() + DELIMITER +
							sale.getname() + DELIMITER +
							sale.getproduct() + DELIMITER +
							sale.getsold() + DELIMITER +
							sale.getprice() + DELIMITER +
							sale.gettotal() + DELIMITER +
							sale.getdate();
					records.add(updatedinfo);
				} else {
					records.add(line);
				}
				rowindex++;
				
			}
		} catch(IOException x) {
			JOptionPane.showMessageDialog(null, x.getMessage());
		}
		
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
			for (String record:records) bw.write(record + "\n");
			bw.close();
			JOptionPane.showMessageDialog(null, "Record updated!");
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, z.getMessage());
		}
	}
	
	
	
	private void delete() {
		int selectedrow = table.getSelectedRow();
		
		records = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowindex = 0;
			while((line = br.readLine()) != null) {
				if(rowindex != selectedrow) records.add(line);
				rowindex++;
				
			}
		} catch(IOException x) {
			JOptionPane.showMessageDialog(null, x.getMessage());
		}
		
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
			for (String record:records) bw.write(record + "\n");
			bw.close();
			JOptionPane.showMessageDialog(null, "Record deleted!");
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, z.getMessage());
		}
	
		
	}
	
	
	private void loadTable() {
		model.setRowCount(0);
		
		try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			while((line = br.readLine()) != null) {
				String[] rows = line.split(DELIMITER);
				model.addRow(rows);
			}
			
			
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}


	private boolean duplicateID() {
	model.setRowCount(0);
		File file = new File(FILENAME);
		if(!file.exists()) return false;

		try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			while((line = br.readLine()) != null) {
				String[] rows = line.split(DELIMITER);
				model.addRow(rows);

				if(rows[0].equals(txtID.getText()) return true;
			}

			
		} catch (IOException err) {
			JOptionPane.showMessageDialog(null, err.getMessage());
		}
		return false;
	}
	
	
	
	
	
	
	

	public static void main(String[] args) {
	new SalesRecord();	
	

	}

}
