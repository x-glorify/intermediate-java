package Practice107;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.util.*;
public class SimpleSalesReceiptSystem extends JFrame{
	
	final String DELIMITER = "::";
	final String filename = "SalesSystem.txt";
	
	JTextField txtreceiptnum, txtname, txtquantity, txtprice, txttotal;
	String name, category, paymethod;
	int receiptnum;
	int quantity;
	double price, total;
	ButtonGroup bg;
	JComboBox <String> comcategory;
	JTable table;
	DefaultTableModel model;
	JScrollPane scroll;
	ArrayList<String> temprecord;
	
	
	SimpleSalesReceiptSystem() {
		
		
		
		JLabel lblreceiptnum = new JLabel("Receipt Number");
		add(lblreceiptnum).setBounds(30, 30, 150, 20);
		
		txtreceiptnum = new JTextField();
		add(txtreceiptnum).setBounds(150, 30, 150, 20);
		
		JLabel lblname = new JLabel("Customer Name");
		add(lblname).setBounds(30, 80, 150, 20);
		
		txtname = new JTextField();
		add(txtname).setBounds(150, 80, 150, 20);
		
		txtname.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char d = e.getKeyChar(); 
				if (Character.isDigit(d))
					e.consume();
			}
		});
		
		
		
		
		
		
	
		JLabel lblquantity = new JLabel("Quantity");
		add(lblquantity).setBounds(30, 130, 150, 20);
		
		txtquantity = new JTextField();
		add(txtquantity).setBounds(150, 130, 150, 20);
		
		txtquantity.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char g = e.getKeyChar();
				if (!Character.isDigit(g)) e.consume();
			}
		});
		
		
		
		JLabel lblprice = new JLabel("Price");
		add(lblprice).setBounds(370, 30, 150, 20);
		
		txtprice = new JTextField();
		add(txtprice).setBounds(430, 30, 150, 20);
		
		
		txtprice.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char h = e.getKeyChar();
				if (!Character.isDigit(h) && h != '.') e.consume();
			}
		});
		
		
		
		
		JLabel lblcategory = new JLabel("Category");
		add(lblcategory).setBounds(370, 80, 150, 20);
		
		comcategory = new JComboBox<>(new String[]
					{"Food", "Beverage", "Clothing", "Electronics", "Medicine"});
		
		add(comcategory).setBounds(430, 80, 80, 20);
		
		
		//radio buttons
		
		JLabel lblpaymethod = new JLabel("Payment Method");
		add(lblpaymethod).setBounds(370, 130, 150, 20);
		
		JRadioButton rbcash = new JRadioButton("Cash");
		add(rbcash).setBounds(370, 160, 80, 20);
		
		JRadioButton rbcard = new JRadioButton("Card");
		add(rbcard).setBounds(450, 160, 80, 20);
		
		bg = new ButtonGroup();
		
		bg.add(rbcash);
		bg.add(rbcard);
		
		
		JLabel lbltotal = new JLabel("Total");
		add(lbltotal).setBounds(700, 30, 150, 20);
		
		txttotal = new JTextField();
		add(txttotal).setBounds(750, 30, 100, 50);
		txttotal.setEditable(false);
		
		
		// buttons
		
		JButton btnadd = new JButton("Add");
		add(btnadd).setBounds(700, 120, 80, 20);
		
		JButton btndelete = new JButton("Delete");
		add(btndelete).setBounds(790, 120, 80, 20);
		
		JButton btnupdate = new JButton("Update");
		add(btnupdate).setBounds(700, 150, 80, 20);
		
		JButton btnclear = new JButton("Clear");
		add(btnclear).setBounds(790, 150, 80, 20);
		
		
		
		// button  functions
		
		btnadd.addActionListener(e ->{
			
			if (txtreceiptnum.getText().isEmpty() || txtname.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Fields can't be empty!");
				return;
			}
			
			
			
			receiptnum = Integer.parseInt(txtreceiptnum.getText());
			name = txtname.getText();
			category = comcategory.getSelectedItem().toString();
			quantity = Integer.parseInt(txtquantity.getText());
			price = Double.parseDouble(txtprice.getText());
			total = quantity * price;
			
			txttotal.setText(String.valueOf(total));
			
			
			if (rbcash.isSelected()) {
				paymethod = "Cash";
			} else {
				paymethod = "Card";
			}
			
			add();
			reload();
			
		});
		
		btndelete.addActionListener(e ->{
			delete();
			reload();
			
		});
		
		btnupdate.addActionListener(e ->{
			
			if (txtreceiptnum.getText().isEmpty() || txtname.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Fields can't be empty!");
				return;
			}
			
			
			
			receiptnum = Integer.parseInt(txtreceiptnum.getText());
			name = txtname.getText();
			category = comcategory.getSelectedItem().toString();
			quantity = Integer.parseInt(txtquantity.getText());
			price = Double.parseDouble(txtprice.getText());
			total = quantity * price;
			
			if (rbcash.isSelected()) {
				paymethod = "Cash";
			} else {
				paymethod = "Card";
			}
			
			update();
			reload();
			
		});
		
		btnclear.addActionListener(e ->{
			clear();
		});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// table
		
		String[] cols = {"Receipt ID", "Customer Name", "Category", "Quantity",
						"Price", "Total", "Payment Method"};
		
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		scroll = new JScrollPane(table);
		scroll.setBounds(30, 200, 850, 300);
		add(scroll);
		reload();
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					txtreceiptnum.setText(model.getValueAt(row, 0).toString());
					txtname.setText(model.getValueAt(row, 1).toString());
					
					comcategory.setSelectedItem(model.getValueAt(row, 2).toString());
					
					txtquantity.setText(model.getValueAt(row, 3).toString());
					txtprice.setText(model.getValueAt(row, 4).toString());
					txttotal.setText(model.getValueAt(row, 5).toString());
					
					paymethod = model.getValueAt(row, 6).toString();
					
					if (rbcash.isSelected()) {
						paymethod = "Cash";
					} else {
						paymethod = "Card";
					}
					
				}
			}
		});
		
		
		
		
		
		
		
		
		
		
		
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(950, 550);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	
	
	// methods
	
	
	
		void add() {
			try(FileWriter fw = new FileWriter(filename, true)) {
				
				fw.write(receiptnum + DELIMITER + name + DELIMITER + category + DELIMITER +
						quantity + DELIMITER + price + DELIMITER + total + DELIMITER + paymethod + "\n");
				fw.close();
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		
		
		
		void delete() {
			int selectedrow = table.getSelectedRow();
			
			if (selectedrow == -1) {
				JOptionPane.showMessageDialog(null, "Select a record to delete");
				return;
			}
			
			
			int confirm = JOptionPane.showConfirmDialog(null, "Delete record?", "Confirm",
														JOptionPane.YES_NO_OPTION);
			
			if (confirm != JOptionPane.YES_OPTION) return;
			
			
			
			
			
			temprecord = new ArrayList<>();
			
			try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
				String line;
				int rowIndex = 0;
				
				while ((line = br.readLine()) != null) {
					if (rowIndex != selectedrow) temprecord.add(line);
						rowIndex++;
				}
				
				
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e);
			}
			
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
				for (String record : temprecord) {
					bw.write(record + "\n");
				}
				
				
				
			} catch (IOException x) {
				JOptionPane.showMessageDialog(null, x);
			}
			
		}
		
		
		
		
		void update() {
			int selectedrow = table.getSelectedRow();
			
			if (selectedrow == -1) {
				JOptionPane.showMessageDialog(null, "Select a record to update.");
				return;
			}
			
			
			temprecord = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
				String line;
				int rowIndex = 0;
				while((line = br.readLine()) != null) {
					if (rowIndex == selectedrow) {
						String updatedinfo = receiptnum + DELIMITER + name + DELIMITER + category + DELIMITER +
											quantity + DELIMITER + price + DELIMITER + total + DELIMITER + paymethod;
					}
					rowIndex++;
				}
				
			} catch (IOException g) {
				JOptionPane.showMessageDialog(null, g);
			}
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
				
				for (String record : temprecord) {
					bw.write(record + "\n");
				}
				
			} catch (IOException f) {
				JOptionPane.showMessageDialog(null, f);
			}
			
			
			
		}
		
		
		
		
		
		
		
		
		
	
	
	
	
		void reload() {
			model.setRowCount(0);
			
			File file = new File(filename);
			if (!file.exists()) return;
			
			try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
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
			txtreceiptnum.setText("");
			txtname.setText("");
			txtquantity.setText("");
			txtprice.setText("");
			txttotal.setText("");
			comcategory.setSelectedIndex(0);
			bg.clearSelection();
			
		}
	
		
		
		
	

	public static void main(String[] args) {
	new SimpleSalesReceiptSystem();

	}

}
