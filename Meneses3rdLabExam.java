package Meneses107;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Meneses3rdLabExam extends JFrame{

	
	private String filename = "Meneses3rdExam.txt";
	final String DELIMITER = ":::";
	JTextField txtname, txtcourse, txtyearlvl, txtbalance, txtstatus;
	String name, course, status;
	int yearlvl;
	double balance;
	JTable table;
	DefaultTableModel model;
	JScrollPane scroll;
	ArrayList <String> temprecord;
	
	
	
	
	Meneses3rdLabExam() {
		
		JLabel lblname = new JLabel("Full Name");
		add(lblname).setBounds(40, 50, 100, 30);
		
		txtname = new JTextField();
		add(txtname).setBounds(40, 80, 200, 20);
		

		JLabel lblcourse = new JLabel("Course/Dept");
		add(lblcourse).setBounds(40, 100, 100, 30);
		
		txtcourse = new JTextField();
		add(txtcourse).setBounds(40, 130, 200, 20);
		

		JLabel lblyearlvl = new JLabel("Year Level");
		add(lblyearlvl).setBounds(40, 150, 100, 30);
		
		txtyearlvl = new JTextField();
		add(txtyearlvl).setBounds(40, 180, 200, 20);
		
		JLabel lblbalance = new JLabel("Wallet Balance");
		add(lblbalance).setBounds(40, 200, 100, 30);
		
		txtbalance = new JTextField();
		add(txtbalance).setBounds(40, 230, 200, 20);
		
		JLabel lblstatus = new JLabel("Status (Active or Suspended)");
		add(lblstatus).setBounds(40, 250, 100, 30);
		
		txtstatus = new JTextField();
		add(txtstatus).setBounds(40, 280, 200, 20);
		
		
		
		JButton btnadd = new JButton("Add");
		add(btnadd).setBounds(40, 400, 80, 20);
		
		JButton btndelete = new JButton("Delete");
		add(btndelete).setBounds(130, 400, 80, 20);
		
		JButton btnupdate = new JButton("Update");
		add(btnupdate).setBounds(40, 430, 80, 20);
		
		JButton btnclear = new JButton("Clear");
		add(btnclear).setBounds(130, 430, 80, 20);
		
		
		
		
		btnadd.addActionListener(e->{
			if (txtname.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Name empty!");
				return;
			}
			
			
			name = txtname.getText();
			course = txtcourse.getText();
			yearlvl = Integer.parseInt(txtyearlvl.getText());
			balance = Double.parseDouble(txtbalance.getText());
			status = txtstatus.getText();
			
			
			
			
			add();
			loadtable();
			
			
			
		});
		
		btndelete.addActionListener(e->{
			delete();
			loadtable();
		});
		
		btnupdate.addActionListener(e->{
			name = txtname.getText();
			course = txtcourse.getText();
			yearlvl = Integer.parseInt(txtyearlvl.getText());
			balance = Double.parseDouble(txtbalance.getText());
			status = txtstatus.getText();
			
			
			update();
			loadtable();
		});

		btnclear.addActionListener(e->{
			clear();
		});
		
		
		
		
		
		
		
		String[] cols = {"Full Name", "Course/Dept", "Year Level", "Wallet Balance", "Status"};
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		scroll = new JScrollPane(table);
		scroll.setBounds(300, 50, 500, 600);
		add(scroll);
		loadtable();
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				txtname.setText(model.getValueAt(row, 0).toString());
				txtcourse.setText(model.getValueAt(row, 1).toString());
				txtyearlvl.setText(model.getValueAt(row, 2).toString());
				txtbalance.setText(model.getValueAt(row, 3).toString());
				txtstatus.setText(model.getValueAt(row, 4).toString());
			}
		});
		
		
		
		
		
		
		
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 800);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	
	

	
	void add() {

		
		
		try (FileWriter fw = new FileWriter(filename, true)) {
			
			fw.write(name + DELIMITER + course + DELIMITER + yearlvl +
					DELIMITER + balance + DELIMITER + status + "\n");
			
			
			fw.close();
			
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	
	void delete() {
		int selectedrow = table.getSelectedRow();
		
		if (selectedrow == -1) {
			JOptionPane.showMessageDialog(null, "Choose a record to delete.");
			return;
		}
		
		
		int confirm = JOptionPane.showConfirmDialog(null, "Delete record?", "Confirm?", JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION) return;
		
		
		
		temprecord = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			int rowIndex = 0;
			while((line = br.readLine()) != null) {
				if (rowIndex != selectedrow) {
					temprecord.add(line);
					
					rowIndex++;
				}
			}
			
			
			
		}catch (IOException x) {
			JOptionPane.showMessageDialog(null, x);
		}
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
			for (String record:temprecord) {
				bw.write(record + "\n");
			}
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, z);
		}
		
		
		
		
		
		
	}
	
	
	
	
	
	
	void update() {
		
		int selectedrow = table.getSelectedRow();
		
		if (selectedrow == -1) {
			JOptionPane.showMessageDialog(null, "Choose a record to update.");
			return;
		}
		
		
		
		
		
		temprecord = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			int rowIndex = 0;
			while((line = br.readLine()) != null) {
				if (rowIndex == selectedrow) {
					String updatedinfo = name + DELIMITER + course + DELIMITER + yearlvl +
										DELIMITER + balance + DELIMITER + status;
					
					temprecord.add(updatedinfo);
				}
				
				rowIndex++;
			}
			
			
			
			
		}catch (IOException x) {
			JOptionPane.showMessageDialog(null, x);
		}
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
			for (String record:temprecord) {
				bw.write(record + "\n");
			}
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, z);
		}
		
			
		
	}
	
	
	
	
	void loadtable() {
		model.setRowCount(0);
		
		File file = new File (filename);
		if (!file.exists()) return;
		
		try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while((line = br.readLine()) != null) {
				String[]row = line.split(DELIMITER);
				model.addRow(row);
			}
			
		}catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
		
		
	}
	
	
	void clear() {
		txtname.setText("");
		txtcourse.setText("");
		txtyearlvl.setText("");
		txtbalance.setText("");
		txtstatus.setText("");
	}
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
		new Meneses3rdLabExam();

	}

}
