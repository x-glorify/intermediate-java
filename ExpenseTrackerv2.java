package Practice107;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.table.*;
public class ExpenseTrackerv2 extends JFrame{

	JTextField txtreceiptnum, txtstorename, txttotal, txttax, txtfinal;
	String receiptnum, storename;
	double total, tax, finalamount;
	
	String DELIMITER = "###";
	String FILENAME = "ExpenseV2.txt";
	JTable table;
	JScrollPane scrollpane;
	DefaultTableModel model;
	ArrayList<String> lines;
	
	
	
	
	
	ExpenseTrackerv2() {
		JLabel lblheader = new JLabel("Expense Tracker");
		add(lblheader).setBounds(50, 30, 200, 30);
		lblheader.setFont(new Font("Arial Black", Font.BOLD, 20));
		lblheader.setForeground(Color.white);
		
		JLabel lblreceipt = new JLabel("Receipt Number");
		add(lblreceipt).setBounds(50, 100, 200, 20);
		lblreceipt.setFont(new Font("Arial Narrow", Font.PLAIN, 16));
		lblreceipt.setForeground(Color.white);
		
		txtreceiptnum = new JTextField();
		add(txtreceiptnum).setBounds(50, 130, 200, 25);
		
		
		JLabel lblstore = new JLabel("Store Name");
		add(lblstore).setBounds(50, 180, 200, 20);
		lblstore.setFont(new Font("Arial Narrow", Font.PLAIN, 16));
		lblstore.setForeground(Color.white);
		
		txtstorename = new JTextField();
		add(txtstorename).setBounds(50, 210, 200, 25);
		
		JLabel lbltotalcost = new JLabel("Total Cost");
		add(lbltotalcost).setBounds(50, 260, 200, 20);
		lbltotalcost.setFont(new Font("Arial Narrow", Font.PLAIN, 16));
		lbltotalcost.setForeground(Color.white);
		
		txttotal = new JTextField();
		add(txttotal).setBounds(50, 290, 200, 25);
		
		
		
		JLabel lbltax = new JLabel("Tax (12%)");
		add(lbltax).setBounds(50, 350, 200, 20);
		lbltax.setFont(new Font("Arial Narrow", Font.PLAIN, 16));
		lbltax.setForeground(Color.white);
		
		txttax = new JTextField();
		add(txttax).setBounds(150, 350, 80, 25);
		txttax.setEditable(false);
		
		JLabel lbltotalamount = new JLabel("Total Amount");
		add(lbltotalamount).setBounds(50, 380, 200, 20);
		lbltotalamount.setFont(new Font("Arial Narrow", Font.PLAIN, 16));
		lbltotalamount.setForeground(Color.white);
		
		txtfinal = new JTextField();
		add(txtfinal).setBounds(150, 380, 80, 25);
		txtfinal.setEditable(false);
		
		
		JButton btncalculate = new JButton("Calculate");
		add(btncalculate).setBounds(50, 430, 100, 25);
		btncalculate.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		JButton btnadd = new JButton("Add");
		add(btnadd).setBounds(50, 460, 100, 25);
		btnadd.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		JButton btndelete = new JButton("Delete");
		add(btndelete).setBounds(50, 490, 100, 25);
		btndelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		JButton btnupdate = new JButton("Update");
		add(btnupdate).setBounds(50, 520, 100, 25);
		btnupdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		
		//actions
		
		
		btncalculate.addActionListener(e->{
			receiptnum = txtreceiptnum.getText();
			storename = txtstorename.getText();
			total = Double.parseDouble(txttotal.getText());
			tax = total * 0.12;
			finalamount = total + tax;
			
			txttax.setText(String.valueOf(tax));
			txtfinal.setText(String.valueOf(finalamount));
			
			
		});
		
		
		
		
		
		btnadd.addActionListener(e->{
			receiptnum = txtreceiptnum.getText();
			storename = txtstorename.getText();
			total = Double.parseDouble(txttotal.getText());
			tax = total * 0.12;
			finalamount = total + tax;
			
			txttax.setText(String.valueOf(tax));
			txtfinal.setText(String.valueOf(finalamount));
			
			add();
			clear();
			refresh();
			
		});
		
		
		btndelete.addActionListener(e->{
			
			delete();
			clear();
			refresh();
					
		});
		
		
		btnupdate.addActionListener(e->{
			receiptnum = txtreceiptnum.getText();
			storename = txtstorename.getText();
			total = Double.parseDouble(txttotal.getText());
			tax = total * 0.12;
			finalamount = total + tax;
			
			txttax.setText(String.valueOf(tax));
			txtfinal.setText(String.valueOf(finalamount));
			update();
			clear();
			refresh();
					
		});
		
		
		
		String[] cols = {"Receipt Number", "Store Name", "Total Cost", "Tax", "Total Amount"};
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		table.setOpaque(true);
		table.setFillsViewportHeight(true);
		table.setBackground(Color.black);
		table.setForeground(Color.white);
		table.getTableHeader().setBackground(Color.black);
		table.getTableHeader().setForeground(Color.white);
		
		
		scrollpane = new JScrollPane(table);
		scrollpane.setBounds(300, 30, 660, 500);
		add(scrollpane);
		refresh();
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					txtreceiptnum.setText(model.getValueAt(row, 0).toString());
					txtstorename.setText(model.getValueAt(row, 1).toString());
					txttotal.setText(model.getValueAt(row, 2).toString());
					txttax.setText(model.getValueAt(row, 3).toString());
					txtfinal.setText(model.getValueAt(row, 4).toString());
					
				}
			}
			
		});
		
		
		
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.BLACK);
		setLayout(null);
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	
	
	
	// button methods
	
	void add() {
		
		try(FileWriter fw = new FileWriter(FILENAME, true)) {
			fw.write(receiptnum + DELIMITER + storename + DELIMITER + 
					total + DELIMITER + tax + DELIMITER + finalamount + "\n");
			fw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
		
	}
	
	
	void update() {
		
		int selectedrow = table.getSelectedRow();
		if (selectedrow == -1) {
			JOptionPane.showMessageDialog(null, "Select a record to update.");
		}
		
		lines = new ArrayList<>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowIndex = 0;
			while((line = br.readLine()) != null) {
				if (rowIndex == selectedrow) {
					String  updatedrecord = receiptnum + DELIMITER + storename + DELIMITER + total + DELIMITER
							+ tax + DELIMITER + finalamount;
				
							lines.add(updatedrecord);
				} else {
					lines.add(line);
				}
				rowIndex++;
			}
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
		
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
			for (String record:lines) {
				bw.write(record + "\n");
			}
			bw.close();
			
		}catch (IOException x) {
			JOptionPane.showMessageDialog(null, x);
		}
		
		JOptionPane.showMessageDialog(null, "Record updated successfully!");
		
	}
	
	
	
	
	void delete() {
		
		int selectedrow = table.getSelectedRow();
		if (selectedrow == -1) {
			JOptionPane.showMessageDialog(null, "Select a record to delete.");
			return;
		}
		
		
		int confirm = JOptionPane.showConfirmDialog
				(null, "Are you sure you want to delete record?", "Confirm deletion", JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION) return;
		
		lines = new ArrayList<>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowIndex = 0;
			
			while((line = br.readLine()) != null) {
				if (rowIndex != selectedrow) {
					lines.add(line);
				
				}
				rowIndex++;
			}
			
			
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, x);
		}
	
	
	try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
		
		for (String record:lines) {
			bw.write(record + "\n");
		}
		bw.close();
		
		
		
	} catch (IOException y) {
		JOptionPane.showConfirmDialog(null, y);
	}
	JOptionPane.showMessageDialog(null, "Record deleted successfully!");
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	void refresh() {
		
		File file = new File(FILENAME);
		if (!file.exists()) {
			return;
		}
		
		model.setRowCount(0);
		try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			
			while ((line = br.readLine()) != null) {
				String[] row = line.split(DELIMITER);
				model.addRow(row);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
		
		
		
	}
	
	
	
	
	
	
	void clear() {
		txtreceiptnum.setText("");
		txtstorename.setText("");
		txttotal.setText("");
		txttax.setText("");
		txtfinal.setText("");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		new ExpenseTrackerv2();

	}

}
