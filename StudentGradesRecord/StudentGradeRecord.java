package InterventionProgram;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class StudentGradeRecord extends JFrame{
	private final String FILENAME = "studentgrade.txt";
	private final String DELIMITER = "##";
	
	private JTextField txtID, txtname, txtsubj, txtprelim, txtmidterm, txtfinal, txtavg;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scrollPane;
	private ArrayList<String> records;
	
	StudentGradeRecord() {
		String[] cols =
			{"Student ID", "Student Name", "Subject", "Prelim", "Midterm", "Final Grade", "Average"};
		
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(table);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if(row != -1) {
					txtID.setText(model.getValueAt(row, 0).toString());
					txtname.setText(model.getValueAt(row, 1).toString());
					txtsubj.setText(model.getValueAt(row, 2).toString());
					txtprelim.setText(model.getValueAt(row, 3).toString());
					txtmidterm.setText(model.getValueAt(row, 4).toString());
					txtfinal.setText(model.getValueAt(row, 5).toString());
					txtavg.setText(model.getValueAt(row, 6).toString());
				}
			}
		});
				
		
		JPanel southPanel = new JPanel(new BorderLayout());
		JPanel formsPanel = new JPanel(new GridLayout(2, 7, 6, 6));
		
		formsPanel.add(new JLabel("Student ID"));
		formsPanel.add(new JLabel("Student Name"));
		formsPanel.add(new JLabel("Subject"));
		formsPanel.add(new JLabel("Prelim Grade"));
		formsPanel.add(new JLabel("Midterm Grade"));
		formsPanel.add(new JLabel("Final Grade"));
		formsPanel.add(new JLabel("Average"));
		
		txtID = new JTextField();
		txtname = new JTextField();
		txtsubj = new JTextField();
		txtprelim = new JTextField();
		txtmidterm = new JTextField();
		txtfinal = new JTextField();
		txtavg = new JTextField();
		txtavg.setEditable(false);
		
		formsPanel.add(txtID);
		formsPanel.add(txtname);
		formsPanel.add(txtsubj);
		formsPanel.add(txtprelim);
		formsPanel.add(txtmidterm);
		formsPanel.add(txtfinal);
		formsPanel.add(txtavg);
		
		
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
		southPanel.setBorder(BorderFactory.createTitledBorder("Student grade details:"));
		
		
		
		btnadd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtID.getText().isEmpty() || txtname.getText().isEmpty() || 
							txtsubj.getText().isEmpty() || txtprelim.getText().isEmpty() ||
							txtmidterm.getText().isEmpty() || txtfinal.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Please fill in all the fields!");
							return;
						}

					if (duplicateID()) {
						JOptionPane.showMessageDialog(null, "This student ID already exits.");
						return;
					}
					
					int prelimgr = Integer.parseInt(txtprelim.getText());
					int midtermgr = Integer.parseInt(txtmidterm.getText());
					int finalgr = Integer.parseInt(txtfinal.getText());
					int average = (prelimgr+midtermgr+finalgr) / 3;
					txtavg.setText(String.valueOf(average));
					
					Grade gr = new Grade(txtID.getText(), txtname.getText(), txtsubj.getText(), 
										prelimgr, midtermgr, finalgr, average);
					
					
					
					
					add(gr);
					loadTable();
					
					
				} catch (NumberFormatException err) {
					JOptionPane.showMessageDialog(null, "Grades must be in numbers only.");
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
						JOptionPane.showMessageDialog(null, "Please select a record to update.");
						return;
					}
					
					
					
					
					if (txtID.getText().isEmpty() || txtname.getText().isEmpty() || 
							txtsubj.getText().isEmpty() || txtprelim.getText().isEmpty() ||
							txtmidterm.getText().isEmpty() || txtfinal.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null, "Please fill in all the fields!");
							return;
						}


					if (duplicateID()) {
						JOptionPane.showMessageDialog(null, "This student ID already exits.");
						return;
					}
					
					
					int prelimgr = Integer.parseInt(txtprelim.getText());
					int midtermgr = Integer.parseInt(txtmidterm.getText());
					int finalgr = Integer.parseInt(txtfinal.getText());
					int average = (prelimgr+midtermgr+finalgr) / 3;
					txtavg.setText(String.valueOf(average));
					
					Grade gr = new Grade(txtID.getText(), txtname.getText(), txtsubj.getText(), 
										prelimgr, midtermgr, finalgr, average);
					
					
					
					
					update(gr);
					loadTable();
					
					
				} catch (NumberFormatException err) {
					JOptionPane.showMessageDialog(null, "Grades must be in numbers only.");
					return;
				}
				
				
			}
			
		});
		
		
		btndelete.addActionListener(e->{
			int selectedrow = table.getSelectedRow();
			if (selectedrow == -1) {
				JOptionPane.showMessageDialog(null, "Please select a record to update.");
				return;
			}
			
			delete();
			loadTable();
			
		});
		
		
		btnclear.addActionListener(e->{
			txtID.setText("");
			txtname.setText("");
			txtsubj.setText("");
			txtprelim.setText("");
			txtmidterm.setText("");
			txtfinal.setText("");
			txtavg.setText("");
			
		});
		
		
		
		
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		
		loadTable();
		
		setTitle("Student Grades Record");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200,500);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	private void add(Grade gr) {
		try (FileWriter fw = new FileWriter(FILENAME, true)) {
			fw.write(gr.getstudentID() + DELIMITER +
					gr.getstudentName() + DELIMITER +
					gr.getsubject() + DELIMITER +
					gr.getprelim() + DELIMITER +
					gr.getmidterm() + DELIMITER +
					gr.getfinal() + DELIMITER +
					gr.getaverage() + "\n");
			fw.close();
		
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error saving record. Please try again." + e.getMessage());
		}
	}
	
	
	
	private void update(Grade gr) {
		int selectedrow = table.getSelectedRow();
		
		records = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowindex = 0;
			while((line = br.readLine()) != null) {
				if(rowindex == selectedrow) {
					String updatedinfo = gr.getstudentID() + DELIMITER +
							gr.getstudentName() + DELIMITER +
							gr.getsubject() + DELIMITER +
							gr.getprelim() + DELIMITER +
							gr.getmidterm() + DELIMITER +
							gr.getfinal() + DELIMITER +
							gr.getaverage();
					records.add(updatedinfo);
				} else {
					records.add(line);
				}
				rowindex++;
			}
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, "There was an error updating record." + x.getMessage());
		}
		
		
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
			for (String record:records) bw.write(record+"\n");
			bw.close();
			JOptionPane.showMessageDialog(null, "Record updated!");
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, "There was an error writing file to text." + z.getMessage());
		}
		
		
		
	}
	
	
	private void delete() {
		int selectedrow = table.getSelectedRow();
		int confirm = JOptionPane.showConfirmDialog(null, "Delete record?", "Confirm", JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION) return;
		
		records = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int rowindex = 0;
			while((line = br.readLine()) != null) {
				if(rowindex == selectedrow) records.add(line);
				rowindex++;
			}
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, "There was an error deleting record." + x.getMessage());
		}
		
		
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
			for (String record:records) bw.write(record+"\n");
			bw.close();
			JOptionPane.showMessageDialog(null, "Record deleted!");
		} catch (IOException z) {
			JOptionPane.showMessageDialog(null, "There was an error writing file to text." + z.getMessage());
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
			JOptionPane.showMessageDialog(null, "Error loading records!" + e.getMessage());
		}
	}


	private boolean duplicateID() {
		model.setRowCount(0);
		File file = new File(FILENAME);
		if(!file.exists()) return false;

		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
		String line;
			while((line = br.readLine()) != null) {
				String[] rows = line.split(DELIMITER);
				model.addRow(rows);

				if (rows[0].equals(txtID.getText()) return true;
			}
			
		} catch(IOException err) {
			JOptionPane.showMessageDialog(null, err.getMessage());
		}
		return false;
	}
	
	
	
	

	public static void main(String[] args) {
	new StudentGradeRecord();

	}

}
