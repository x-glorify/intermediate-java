package InterventionProgram;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
public class Meneses_DepartmentalPractice extends JFrame{
	private final String FILENAME = "payroll.txt";
	private final String DELIMITER = "::";
	
	private JTextField txtID, txtname, txtposition,
					txthours, txtrate, txttotalsalary, txtpaydate;
	
	private DefaultTableModel model;
	private JTable table;
	private ArrayList<String> records;
	private JScrollPane scrollPane;
	
	
	
	Meneses_DepartmentalPractice() {
		
		String[] cols = {"Employee ID", "Employee Name", "Position",
						"Hours Worked", "Hourly Rate", "Total Salary", "Pay Date"};
		
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(table);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					txtID.setText(model.getValueAt(row, 0).toString());
					txtname.setText(model.getValueAt(row, 1).toString());
					txtposition.setText(model.getValueAt(row, 2).toString());
					txthours.setText(model.getValueAt(row, 3).toString());
					txtrate.setText(model.getValueAt(row, 4).toString());
					txttotalsalary.setText(model.getValueAt(row, 5).toString());
					txtpaydate.setText(model.getValueAt(row, 6).toString());
					
				}
				
				
			}
		});
		
		
		JPanel southPanel = new JPanel(new BorderLayout());
		JPanel formsPanel = new JPanel(new GridLayout(2, 7, 7, 5));
		
		formsPanel.add(new JLabel("Employee ID"));
		formsPanel.add(new JLabel("Employee Name"));
		formsPanel.add(new JLabel("Position"));
		formsPanel.add(new JLabel("Hours Worked"));
		formsPanel.add(new JLabel("Hourly Rate"));
		formsPanel.add(new JLabel("Total Salary"));
		formsPanel.add(new JLabel("Pay Date"));
		
		txtID			= new JTextField();
		txtname			= new JTextField();
		txtposition		= new JTextField();
		txthours			= new JTextField();
		txtrate			= new JTextField();
		txttotalsalary	= new JTextField();
		txttotalsalary.setEditable(false);
		
		txtpaydate		= new JTextField();
		
		formsPanel.add(txtID);
		formsPanel.add(txtname);
		formsPanel.add(txtposition);
		formsPanel.add(txthours);
		formsPanel.add(txtrate);
		formsPanel.add(txttotalsalary);
		formsPanel.add(txtpaydate);
		
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JButton btnadd		= new JButton("Add");
		JButton btnupdate	= new JButton("Update");
		JButton btndelete	= new JButton("Delete");
		JButton btnclear		= new JButton("Clear");
		
		
		btnPanel.add(btnadd);
		btnPanel.add(btnupdate);
		btnPanel.add(btndelete);
		btnPanel.add(btnclear);
		
		southPanel.add(formsPanel, BorderLayout.CENTER);
		southPanel.add(btnPanel, BorderLayout.SOUTH);
		southPanel.setBorder(BorderFactory.createTitledBorder("Payroll Details:"));
		
		
		
		btnadd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtID.getText().isEmpty() || txtname.getText().isEmpty() || txtposition.getText().isEmpty() || 
						txthours.getText().isEmpty() || txtrate.getText().isEmpty() || txtpaydate.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please fill in all fields!");
						return;
					}
					
					if (!txtpaydate.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
						JOptionPane.showMessageDialog(null, "Pay Date must be in YYYY-MM-DD format.");
						return;
					}

					if(duplicatedID()) {
						JOptionPane.showMessageDialog(null, "This employee ID already exists.");
						return;
					}
					
					double hours		= Double.parseDouble(txthours.getText());
					double rate			= Double.parseDouble(txtrate.getText());
					double totalsalary	= hours * rate;
					txttotalsalary.setText(String.valueOf(totalsalary));
					
					Payroll pay = new Payroll(txtID.getText(), txtname.getText(), txtposition.getText(), txtpaydate.getText(),
												hours, rate, totalsalary);
					
					
					
					addPayroll(pay);
					loadTable();
					
					
				} catch (NumberFormatException num) {
					JOptionPane.showMessageDialog(null, "Hours, rate, and salary must be numbers only.");
					return;
				}
				
				
				
			}
			
		});
		
		
		
		btnupdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int selectedrow = table.getSelectedRow();
					if (selectedrow == -1) {
						JOptionPane.showMessageDialog(null, "Please choose a record to update.");
						return;
					}

					
					if (txtID.getText().isEmpty() || txtname.getText().isEmpty() || txtposition.getText().isEmpty() || 
						txthours.getText().isEmpty() || txtrate.getText().isEmpty() || txtpaydate.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please fill in all fields!");
						return;
					}
					
					if (!txtpaydate.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
						JOptionPane.showMessageDialog(null, "Pay Date must be in YYYY-MM-DD format.");
						return;
					}
					
					
					double hours			= Double.parseDouble(txthours.getText());
					double rate			= Double.parseDouble(txtrate.getText());
					double totalsalary	= hours * rate;
					txttotalsalary.setText(String.valueOf(totalsalary));
					
					Payroll pay = new Payroll(txtID.getText(), txtname.getText(), txtposition.getText(), txtpaydate.getText(),
												hours, rate, totalsalary);
					
					updatePayroll(pay);
					loadTable();
					
					
				} catch (NumberFormatException num) {
					JOptionPane.showMessageDialog(null, "Hours, rate, and salary must be numbers only.");
					return;
				}
				
				
				
				
				
			}
			
		});
		
		
		btndelete.addActionListener(e->{
			int selectedrow = table.getSelectedRow();
					if (selectedrow == -1) {
						JOptionPane.showMessageDialog(null, "Please choose a record to delete.");
						return;
					}
			deletePayroll();
			loadTable();
			
		});
		
		
		
		
		btnclear.addActionListener(e->{
			txtID.setText("");
			txtname.setText("");
			txtposition.setText("");
			txthours.setText("");
			txtrate.setText("");
			txttotalsalary.setText("");
			txtpaydate.setText("");
			
		});
		
		
		
		
		
		
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		loadTable();
		
		setTitle("Employee Payroll System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(960, 600);
		setLocationRelativeTo(null);
		setVisible(true);
		
	
		
	}
	
	
	//============= METHODS ===============================================
	
			private void addPayroll(Payroll pay) {
				try (FileWriter fw = new FileWriter(FILENAME, true)) {
					
					fw.write(pay.getEmployeeID() + DELIMITER +
							 pay.getName() + DELIMITER +
							 pay.getPosition() + DELIMITER + 
							 pay.getHours() + DELIMITER +
							 pay.getRate() + DELIMITER +
							 pay.getTotalSalary() + DELIMITER +
							 pay.getPayDate() + "\n");
					
					fw.close();
				
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
			
			
			
			private void updatePayroll(Payroll pay) {
				int selectedrow = table.getSelectedRow();
				records = new ArrayList<>();
				try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
					String line;
					int rowindex = 0;
					while((line = br.readLine()) != null) {
						if(rowindex == selectedrow) {
							String updatedinfo =
									 pay.getEmployeeID() + DELIMITER +
									 pay.getName() + DELIMITER +
									 pay.getPosition() + DELIMITER + 
									 pay.getHours() + DELIMITER +
									 pay.getRate() + DELIMITER +
									 pay.getTotalSalary() + DELIMITER +
									 pay.getPayDate();
							records.add(updatedinfo);
						} else {
							records.add(line);
						}
						rowindex++;
					}
					
					
					
					
				} catch (IOException x) {
					JOptionPane.showMessageDialog(null, x.getMessage());
				}
				
				
				
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
					for (String record:records) bw.write(record + "\n");
					bw.close();
					JOptionPane.showMessageDialog(null, "Payroll updated!");
					
				} catch (IOException z) {
					JOptionPane.showMessageDialog(null, z.getMessage());
				}
				
				
			}
			
			
			
			private void deletePayroll() {
				int selectedrow = table.getSelectedRow();
				int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?",
														"Confirm", JOptionPane.YES_NO_OPTION);
				if (confirm != JOptionPane.YES_OPTION) return;
				
				
				records = new ArrayList<>();
				try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
					String line;
					int rowindex = 0;
					while((line = br.readLine()) != null) {
						if(rowindex != selectedrow) records.add(line);
						rowindex++;
					
					}
					
					
					
				} catch (IOException x) {
					JOptionPane.showMessageDialog(null, x.getMessage());
				}
				
				
				
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
					for (String record:records) bw.write(record + "\n");
					bw.close();
					JOptionPane.showMessageDialog(null, "Payroll deleted!");
					
				} catch (IOException z) {
					JOptionPane.showMessageDialog(null, z.getMessage());
				}
				
			}
	
	
	
	
	
	
			private void loadTable() {
				
				model.setRowCount(0);
				File file = new File(FILENAME);
				if (!file.exists()) return;
				
				records = new ArrayList<>();
				try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))){
					String line;
					while((line = br.readLine()) != null) {
						String[] rows = line.split(DELIMITER);
						model.addRow(rows);
					}
				} catch (IOException io) {
					JOptionPane.showMessageDialog(null, io.getMessage());
				}
				
			
			}
	

			private boolean duplicateID() {
			File file = new File(FILENAME);
			if (!file.exists()) return false;
				try (BufferedReafer br = new BufferedReader(new FileReader(FILENAME))) {
					String line;
					while((line = br.readLine()) != null) {
						String[] rows = line.split(DELIMITER);
						if(rows[0].equals(txtID.getText())) return true;
					}
				} catch (IOException err) {
					JOptionPane.showMessageDialog(null, err/getMessage());
				}

				return false;
			}
 	
	
	
	
	
	
	
	
	
	

	public static void main(String[] args) {
		new Meneses_DepartmentalPractice();

	}

}
