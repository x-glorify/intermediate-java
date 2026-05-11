package Practice107;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
public class DepartmentalPractice extends JFrame{
	
	String delimiter = "##";
	String filename = "MenesesEnrollmentSys.txt";
	JTable table;
	DefaultTableModel model;
	ArrayList<String> temprecord;
	JTextField txtname, txtemail, txtaddress, txtcontactnumber;
	JComboBox comgender;
	JScrollPane scroll;
	
	String name, email, address, contactnumber, gender;
	
	
	
	
	

	DepartmentalPractice() {
		
		JLabel lblheader = new JLabel("Enrollment System");
		add(lblheader).setBounds(50, 50, 300, 30);
		lblheader.setForeground(Color.yellow);
		lblheader.setFont(new Font("Arial Black", Font.PLAIN, 25));
		
		
		JLabel lblname = new JLabel("Full Name");
		add(lblname).setBounds(50, 150, 200, 20);
		lblname.setForeground(Color.white);
		
		txtname = new JTextField();
		add(txtname).setBounds(50, 180, 200, 20);
		
		JLabel lblemail = new JLabel("E-mail");
		add(lblemail).setBounds(50, 210, 200, 20);
		lblemail.setForeground(Color.white);
		
		txtemail = new JTextField();
		add(txtemail).setBounds(50, 240, 200, 20);
		
		JLabel lbladdress = new JLabel("Address");
		add(lbladdress).setBounds(50, 270, 200, 20);
		lbladdress.setForeground(Color.white);
		
		txtaddress = new JTextField();
		add(txtaddress).setBounds(50, 300, 200, 20);
		
		JLabel lblcontactnumber = new JLabel("Contact Number");
		add(lblcontactnumber).setBounds(50, 330, 200, 20);
		lblcontactnumber.setForeground(Color.white);
		
		txtcontactnumber = new JTextField();
		add(txtcontactnumber).setBounds(50, 360, 200, 20);
		
		JLabel lblgender = new JLabel("Gender");
		add(lblgender).setBounds(50, 390, 200, 20);
		lblgender.setForeground(Color.white);
		
		comgender = new JComboBox(new String[] {"Male", "Female"});
		add(comgender).setBounds(50, 420, 80, 20);
		comgender.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		
		
		//buttons
		
		JButton btn1 = new JButton("Enroll");
		add(btn1).setBounds(50, 500, 100, 30);
		btn1.setBackground(Color.green);
		
		JButton btn2 = new JButton("Update");
		add(btn2).setBounds(150, 500, 100, 30);
		btn2.setBackground(Color.yellow);
		
		JButton btn3 = new JButton("Delete");
		add(btn3).setBounds(250, 500, 100, 30);
		btn3.setForeground(Color.white);
		btn3.setBackground(Color.red);
		
		
		
		//actions

		btn1.addActionListener(e ->{
			name = txtname.getText();
			email = txtemail.getText();
			address = txtaddress.getText();
			contactnumber = txtcontactnumber.getText();
			gender = comgender.getSelectedItem().toString();
			
			enroll();
			clear();
			load();
			
		});
		
		
		
		
		
		btn2.addActionListener(e ->{
			name = txtname.getText();
			email = txtemail.getText();
			address = txtaddress.getText();
			contactnumber = txtcontactnumber.getText();
			gender = comgender.getSelectedItem().toString();
			
			update();
			clear();
			load();
			
		});
		
		
		
		
		
		btn3.addActionListener(e ->{
			delete();
			clear();
			load();
			
			
		});
		
		
		
		
		// table
		
		String[] cols = {"Name", "Email", "Address", "Contact Number", "Gender"};
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		table.setOpaque(true);
		table.setFillsViewportHeight(true);
		table.setForeground(Color.white);
		table.setBackground(Color.decode("#800000"));
		table.getTableHeader().setBackground(Color.decode("#800000"));
		table.getTableHeader().setForeground(Color.white);
		
		
		scroll = new JScrollPane(table);
		scroll.setBounds(400, 20, 770, 650);
		add(scroll);
		load();
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					txtname.setText(model.getValueAt(row, 0).toString());
					txtemail.setText(model.getValueAt(row, 1).toString());
					txtaddress.setText(model.getValueAt(row, 2).toString());
					txtcontactnumber.setText(model.getValueAt(row, 3).toString());
					comgender.setSelectedItem(model.getValueAt(row, 4).toString());
				}
			}
		});
		
		
		
		
		setResizable(false);
		setTitle("Meneses Departmental Practice");
		getContentPane().setBackground(Color.decode("#800000"));
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 900);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	void enroll() {
		try (FileWriter fw = new FileWriter(filename, true)) {
			
			fw.write(name + delimiter + email + delimiter + address
					+ delimiter + contactnumber + delimiter + gender + "\n");
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
		
		
		temprecord = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			int rowindex = 0;
			while((line = br.readLine()) != null) {
				if (rowindex == selectedrow) {
					String updatedinfo = name + delimiter + email + delimiter +
										address + delimiter + contactnumber + delimiter +
										gender;
					temprecord.add(updatedinfo);
				} else {
					temprecord.add(line);
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
			for (String record:temprecord) {
				bw.write(record + "\n");
				bw.close();
			}
		} catch (IOException g) {
			JOptionPane.showMessageDialog(null, g);
		}
		
		
	}
	
	
	
	void delete() {
		int selectedrow = table.getSelectedRow();
		
		if (selectedrow == -1) {
			JOptionPane.showMessageDialog(null, "Select a record to delete.");
			return;
		}
		
		int confirm = JOptionPane.showConfirmDialog
					(null, 
					"Are you sure you want to delete record?",
					"Confirm Deletion",
					JOptionPane.YES_NO_OPTION);
		
		if (confirm != JOptionPane.YES_OPTION) return;
		
		
		
		temprecord = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			int rowindex = 0;
			while((line = br.readLine()) != null) {
				if (rowindex != selectedrow) temprecord.add(line);
			}
			
			rowindex++;
			
		}catch (IOException x) {
			JOptionPane.showMessageDialog(null, x);
		}
		
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
			for (String record:temprecord) {
				bw.write(record + "\n");
				bw.close();
				
			
			
			}
		}catch (IOException z) {
			JOptionPane.showMessageDialog(null, z);
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	void load() {
		
		File file = new File(filename);
		if (!file.exists()) return;
		
		model.setRowCount(0);
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			
			while((line = br.readLine()) != null) {
				String[] row = line.split(delimiter);
				model.addRow(row);
			}
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, x);
		}
		
		
	}
	
	
	
	
	

	void clear() {
		txtname.setText("");
		txtemail.setText("");
		txtaddress.setText("");
		txtcontactnumber.setText("");
		comgender.setSelectedIndex(0);
	}
	
	public static void main(String[] args) {
		new DepartmentalPractice();

	}

}
