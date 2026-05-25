package Practice107;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
public class FoodOrderSystemGUI extends JFrame{

		private static final String DELIMITER = "::";
		private static final String FILENAME = "FoodOrderaRecord.txt";
		
		private JTextField txtorderID, txtcustomername, txtquantity, txttotal;
		private String customername, fooditem, ordertype;
		private int orderID, quantity, total;
		
		private JComboBox<String> comfooditem;
		private JTable table;
		private DefaultTableModel model;
		private JScrollPane scroll;
		private ButtonGroup bg;
		private ArrayList<String> records;
		
	
	
	
	FoodOrderSystemGUI() {
		
		
		JLabel lblorderID = new JLabel("Order ID");
		add(lblorderID).setBounds(30, 30, 100, 20);
		
		txtorderID = new JTextField();
		add(txtorderID).setBounds(30, 60, 150, 20);
		
		txtorderID.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char a = e.getKeyChar();
				if (!Character.isDigit(a)) e.consume();
			}
		});
		
		
		
		JLabel lblcustomername = new JLabel("Customer Name");
		add(lblcustomername).setBounds(30, 110, 100, 20);
		
		txtcustomername = new JTextField();
		add(txtcustomername).setBounds(30, 140, 150, 20);
		
		txtcustomername.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char b = e.getKeyChar();
				if (Character.isDigit(b)) e.consume();
			}
		});
		
		
		
		
		JLabel lblfooditem = new JLabel("Food Item");
		add(lblfooditem).setBounds(30, 180, 150, 20);
		
		comfooditem = new JComboBox<>
					(new String[] {"Burger", "Pizza", "Pasta", "Fried Chicken", "Sushi"});
		add(comfooditem).setBounds(30, 210, 100, 20);
		
		JLabel lblquantity = new JLabel("Quantity");
		add(lblquantity).setBounds(300, 30, 150, 20);
		
		txtquantity = new JTextField();
		add(txtquantity).setBounds(300, 60, 80, 20);
		
		JLabel lblordertype = new JLabel("Order Type");
		add(lblordertype).setBounds(300, 110, 150, 20);
		
		JRadioButton rbtn1 = new JRadioButton("Dine In");
		add(rbtn1).setBounds(300, 140, 150, 20);
		
		JRadioButton rbtn2 = new JRadioButton("Takeout");
		add(rbtn2).setBounds(300, 160, 150, 20);
		
		bg = new ButtonGroup();
		bg.add(rbtn1);
		bg.add(rbtn2);
		
		
		
		JLabel lbltotal = new JLabel("Total");
		add(lbltotal).setBounds(570, 30, 150, 20);
		
		txttotal = new JTextField();
		add(txttotal).setBounds(570, 60, 150, 30);
		txttotal.setEditable(false);
		
		
		JButton btnorder = new JButton("Order");
		add(btnorder).setBounds(300, 200, 100, 30);
		
		JButton btnupdate = new JButton("Update");
		add(btnupdate).setBounds(400, 200, 100, 30);
		
		JButton btndelete = new JButton("Delete");
		add(btndelete).setBounds(500, 200, 100, 30);
		
		JButton btnclear = new JButton("Clear");
		add(btnclear).setBounds(600, 200, 100, 30);
		
		
		
		
		btnorder.addActionListener(e->{
			
			if (txtorderID.getText().isEmpty() || txtcustomername.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Fill out necessary fields!");
				return;
			}
			
			
			orderID = Integer.parseInt(txtorderID.getText());
			customername = txtcustomername.getText();
			fooditem = comfooditem.getSelectedItem().toString();
			quantity = Integer.parseInt(txtquantity.getText());
			if (rbtn1.isSelected()) {
				ordertype = "Dine In";
			} else {
				ordertype = "Takeout";
			}
			
			int subtotal = 0;
			
			if (fooditem.equals("Sushi")) {
				subtotal = 400;
			} else if (fooditem.equals("Pizza")) {
				subtotal = 350;
			} else if (fooditem.equals("Pasta")) {
				subtotal = 200;
			} else if (fooditem.equals("Fried Chicken")) {
				subtotal = 180;
			} else {
				subtotal = 150;
			}
			
			if (ordertype.equals("Dine In")) {
				subtotal += 50;
			} else {
				subtotal += 30;
			}
			
			total = quantity * subtotal;
			txttotal.setText(String.valueOf(total));
			
			
			order();
			reload();
		});
		
		
		
		
		btnupdate.addActionListener(e->{
			if (txtorderID.getText().isEmpty() || txtcustomername.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Fill out necessary fields!");
				return;
			}
			
			
			orderID = Integer.parseInt(txtorderID.getText());
			customername = txtcustomername.getText();
			fooditem = comfooditem.getSelectedItem().toString();
			quantity = Integer.parseInt(txtquantity.getText());
			if (rbtn1.isSelected()) {
				ordertype = "Dine In";
			} else {
				ordertype = "Takeout";
			}
			
			int subtotal = 0;
			
			if (fooditem.equals("Sushi")) {
				subtotal = 400;
			} else if (fooditem.equals("Pizza")) {
				subtotal = 350;
			} else if (fooditem.equals("Pasta")) {
				subtotal = 200;
			} else if (fooditem.equals("Fried Chicken")) {
				subtotal = 180;
			} else {
				subtotal = 150;
			}
			
			if (ordertype.equals("Dine In")) {
				subtotal += 50;
			} else {
				subtotal += 30;
			}
			
			total = quantity * subtotal;
			txttotal.setText(String.valueOf(total));
			
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
		
		
		
		
		String[] cols = {"Order ID", "Customer Name", "Food Item", "Quantity", "Order Type", "Total"};
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		scroll = new JScrollPane(table);
		scroll.setBounds(30, 280, 800, 300);
		add(scroll);
		reload();
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					
					txtorderID.setText(model.getValueAt(row, 0).toString());
					txtcustomername.setText(model.getValueAt(row, 1).toString());
					comfooditem.setSelectedItem(model.getValueAt(row, 2).toString());
					txtquantity.setText(model.getValueAt(row, 3).toString());
					ordertype = model.getValueAt(row, 4).toString();
					
					if (ordertype.equals("Dine In")) {
						rbtn1.isSelected();
					} else {
						rbtn2.isSelected();
					}
					
					txttotal.setText(model.getValueAt(row, 5).toString());
					
					
					
					
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
	
	
	
	
		void order() {
			try (FileWriter fw = new FileWriter(FILENAME, true)) {
				
				fw.write(orderID + DELIMITER + customername + DELIMITER + fooditem +
						DELIMITER + quantity + DELIMITER + ordertype + DELIMITER + total + "\n");
				
				fw.close();
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		
		
		
		void update() {
			int selectedrow = table.getSelectedRow();
			if (selectedrow == -1) {
				JOptionPane.showMessageDialog(null, "Select a record to update");
				return;
			}
			
			
			
			
			records = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
				String line;
				int rowindex = 0;
				while((line = br.readLine()) != null) {
					if (rowindex == selectedrow) {
						String updatedinfo = orderID + DELIMITER + customername + DELIMITER + fooditem +
								DELIMITER + quantity + DELIMITER + ordertype + DELIMITER + total;
						records.add(updatedinfo);
					} else {
						records.add(line);
					}
					rowindex++;
				} 
				
				
				
				
				
				
			} catch (IOException x) {
				JOptionPane.showMessageDialog(null, x);
			}
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
				
				for (String record : records) bw.write(record + "\n");
				
			} catch (IOException z) {
				JOptionPane.showMessageDialog(null, z);
			}
			
			
		}
		
		
		
		void delete() {
			int selectedrow = table.getSelectedRow();
			if (selectedrow == -1) {
				JOptionPane.showMessageDialog(null, "Select a record to update");
				return;
			}
			
			int confirm = JOptionPane.showConfirmDialog(null, "Delete record?", "Confirm", JOptionPane.YES_NO_OPTION);
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
				JOptionPane.showMessageDialog(null, x);
			}
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
				
				for (String record : records) bw.write(record + "\n");
				
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
				txtorderID.setText("");
				txtcustomername.setText("");
				txtquantity.setText("");
				comfooditem.setSelectedItem(0);
				bg.clearSelection();
				txttotal.setText("");
			}
		
		
		
		
		
		
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new FoodOrderSystemGUI();
	}

}
