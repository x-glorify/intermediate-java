package Practice107;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.table.*;
import java.awt.event.*;
public class GymMembershipSystem extends JFrame{

	private static final String DELIMITER = "::";
	private static final String FILENAME = "GymMembership.txt";
	
	private JTextField txtmemberID, txtmembername, txtmonths, txttotalfee;
	private String name, plan, paymethod;
	private int months, totalfee, memberID;
	private JComboBox<String> complan;
	
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scroll;
	private ButtonGroup bg;
	private ArrayList<String> records;
	
	
	GymMembershipSystem() {
		
		JLabel lblmemberID = new JLabel("Member ID");
		add(lblmemberID).setBounds(50, 30, 100, 20);
		
		txtmemberID = new JTextField();
		add(txtmemberID).setBounds(50, 60, 150, 20);
		
		JLabel lblmembername = new JLabel("Name");
		add(lblmembername).setBounds(50, 110, 100, 20);
		
		txtmembername = new JTextField();
		add(txtmembername).setBounds(50, 140, 150, 20);
		
		JLabel lblmonths = new JLabel("Months");
		add(lblmonths).setBounds(50, 180, 100, 20);
		
		txtmonths = new JTextField();
		add(txtmonths).setBounds(50, 210, 100, 20);
		
		JLabel lblplan = new JLabel("Membership Plan");
		add(lblplan).setBounds(300, 30, 150, 20);
		
		complan = new JComboBox<>(new String[] {"Basic", "Standard", "Premium", "VIP"});
		add(complan).setBounds(300, 60, 150, 20);
		
		JLabel lblpaymethod = new JLabel("Payment Method");
		add(lblpaymethod).setBounds(300, 110, 150, 20);
		
		JRadioButton rbtn1 = new JRadioButton("Monthly");
		add(rbtn1).setBounds(300, 140, 150, 20);
		
		JRadioButton rbtn2 = new JRadioButton("Full Payment");
		add(rbtn2).setBounds(300, 160, 150, 20);
		
		bg = new ButtonGroup();
		bg.add(rbtn1);
		bg.add(rbtn2);
		
		JLabel lbltotal = new JLabel("Total Fee");
		add(lbltotal).setBounds(570, 30, 150, 20);
		
		txttotalfee = new JTextField();
		add(txttotalfee).setBounds(570, 60, 150, 20);
		txttotalfee.setEditable(false);
		
		
		
		JButton btnenroll = new JButton("Enroll");
		add(btnenroll).setBounds(300, 200, 100, 30);
		
		JButton btnupdate = new JButton("Update");
		add(btnupdate).setBounds(400, 200, 100, 30);
		
		JButton btndelete = new JButton("Delete");
		add(btndelete).setBounds(500, 200, 100, 30);
		
		JButton btnclear = new JButton("Clear");
		add(btnclear).setBounds(600, 200, 100, 30);
		
		
		
		btnenroll.addActionListener(e->{
			
			if (txtmemberID.getText().isEmpty() || txtmembername.getText().isEmpty() || txtmonths.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Fill out all fields!");
				return;
			}
			
			
			
			memberID = Integer.parseInt(txtmemberID.getText());
			name = txtmembername.getText();
			months = Integer.parseInt(txtmonths.getText());
			complan.getSelectedItem().toString();
			
			if (rbtn1.isSelected()) {
				paymethod = "Monthly";
			} else {
				paymethod = "Full Payment";
			}
			
			int rate = 0;
			
			if (plan.equals("VIP")) {
				rate = 2000;
			} else if (plan.equals("Premium")) {
				rate = 1200;
			} else if (plan.equals("Standard")) {
				rate = 800;
			} else {
				rate = 500;
			}
			
			if (paymethod.equals("Full Payment")) rate *= 0.12;
	
			totalfee = months * rate;
			txttotalfee.setText(String.valueOf(totalfee));
			
			
			enroll();
			refresh();
			
		});
		
		
		btnupdate.addActionListener(e->{
			if (txtmemberID.getText().isEmpty() || txtmembername.getText().isEmpty() || txtmonths.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Fill out all fields!");
				return;
			}
			
			
			
			memberID = Integer.parseInt(txtmemberID.getText());
			name = txtmembername.getText();
			months = Integer.parseInt(txtmonths.getText());
			complan.getSelectedItem().toString();
			
			if (rbtn1.isSelected()) {
				paymethod = "Monthly";
			} else {
				paymethod = "Full Payment";
			}
			
			int rate = 0;
			
			if (plan.equals("VIP")) {
				rate = 2000;
			} else if (plan.equals("Premium")) {
				rate = 1200;
			} else if (plan.equals("Standard")) {
				rate = 800;
			} else {
				rate = 500;
			}
			
			if (paymethod.equals("Full Payment")) rate *= 0.12;
	
			totalfee = months * rate;
			txttotalfee.setText(String.valueOf(totalfee));
			
			update();
			refresh();
			
		});
		
		
		btndelete.addActionListener(e->{
			delete();
			refresh();
			
		});
		
		
		btnclear.addActionListener(e->{
			txtmemberID.setText("");
			txtmembername.setText("");
			txtmonths.setText("");
			txttotalfee.setText("");
			complan.setSelectedIndex(0);
			bg.clearSelection();
			
			
		});
		
		
		
		
		
		
		
		String[] cols = {"Member ID", "Name", "Plan", "Months", "Payment Method", "Total Fee"};
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		scroll = new JScrollPane(table);
		scroll.setBounds(40, 280, 800, 320);
		add(scroll);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					txtmemberID.setText(model.getValueAt(row, 0).toString());
					txtmembername.setText(model.getValueAt(row, 1).toString());
					complan.setSelectedItem(model.getValueAt(row, 2).toString());
					txtmonths.setText(model.getValueAt(row, 3).toString());
					
					paymethod = model.getValueAt(row, 4).toString();
					if(paymethod.equals("Monthly")) {
						rbtn1.isSelected();
					} else {
						rbtn2.isSelected();
					}
					
					txttotalfee.setText(model.getValueAt(row, 5).toString());
					
				}
			}
			
		});
		
		
		
		
		
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(950, 520);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	
	
	// methods
	
	
	void enroll() {
		try (FileWriter fw = new FileWriter(FILENAME, true)) {
			
			fw.write(memberID + DELIMITER + name + DELIMITER + plan + DELIMITER + months
					+ DELIMITER + paymethod + DELIMITER + totalfee + "\n");
			
			
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
		
		int confirm = JOptionPane.showConfirmDialog(null, "Delete record?", "Confirm?", JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION) return;
		
		
		
		records = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowindex = 0;
			
			while ((line = br.readLine()) != null) {
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
	
	
	
	void update() {
		int selectedrow = table.getSelectedRow();
		if (selectedrow == -1) {
			JOptionPane.showMessageDialog(null, "Select a record to delete");
			return;
		}
		
		
		
		
		records = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowindex = 0;
			
			while ((line = br.readLine()) != null) {
				if (rowindex == selectedrow) {
					String updatedinfo = memberID + DELIMITER + name + DELIMITER + plan + DELIMITER + months
							+ DELIMITER + paymethod + DELIMITER + totalfee;
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
	
	
	
	
	void refresh() {
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		new GymMembershipSystem();

	}

}
