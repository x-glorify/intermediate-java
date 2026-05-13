package CCE107Activities;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.io.*;
public class StudentInformationForm extends JFrame{

	
	
	
	private String filename = "MenesesStudentForm.txt";
	String delimiter = "##";
	JTextField txtname, txtcourse, txtsection;
	private String name, course, section;
	JTable table;
	DefaultTableModel model;
	ArrayList <String> temprecord;
	JScrollPane scroll;
	
	
	
	StudentInformationForm() {
		
		
		
		
		
		
		JLabel lblname = new JLabel("Name");
		add(lblname).setBounds(10, 280, 100, 20);
		lblname.setForeground(Color.white);
		
		txtname = new JTextField();
		add(txtname).setBounds(10, 300, 100, 20);
		
		
		JLabel lblcourse = new JLabel("Course");
		add(lblcourse).setBounds(150, 280, 100, 20);
		lblcourse.setForeground(Color.white);
		
		txtcourse = new JTextField();
		add(txtcourse).setBounds(150, 300, 100, 20);
		
		
		JLabel lblsection = new JLabel("Section");
		add(lblsection).setBounds(290, 280, 100, 20);
		lblsection.setForeground(Color.white);
		
		txtsection = new JTextField();
		add(txtsection).setBounds(290, 300, 100, 20);
		
		
		
		JButton btnadd = new JButton("Add");
		add(btnadd).setBounds(10, 350, 100, 20);
		
		JButton btnupdate = new JButton("Update");
		add(btnupdate).setBounds(120, 350, 100, 20);
		
		JButton btndelete = new JButton("Delete");
		add(btndelete).setBounds(230, 350, 100, 20);
		
		JButton btnclear = new JButton("Clear");
		add(btnclear).setBounds(340, 350, 100, 20);
		
		
		btnadd.addActionListener(e -> {
			name = txtname.getText();
			course = txtcourse.getText();
			section = txtsection.getText();
			
			add();
			refresh();
			
			
		});
		
		btnupdate.addActionListener(e -> {
			name = txtname.getText();
			course = txtcourse.getText();
			section = txtsection.getText();
			
			update();
			refresh();
			
		});

		btndelete.addActionListener(e -> {
			delete();
			refresh();
		});

		btnclear.addActionListener(e -> {
			clear();
		});
		
		
		
		
		
		
		
		String[] cols = {"Name", "Course", "Section"};
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		table.setOpaque(true);
		table.setForeground(Color.white);
		table.setBackground(Color.black);
		table.getTableHeader().setForeground(Color.white);
		table.getTableHeader().setBackground(Color.black);
		
		scroll = new JScrollPane(table);
		scroll.setBounds(18, 10, 410, 256);
		add(scroll);
		refresh();
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selectedrow = table.getSelectedRow();
				
				if (selectedrow != -1) {
					txtname.setText(model.getValueAt(selectedrow,  0).toString());
					txtcourse.setText(model.getValueAt(selectedrow,  1).toString());
					txtsection.setText(model.getValueAt(selectedrow,  2).toString());
				}
			}
			
			
		});
		
		
		
		
		
		setTitle("Student Information Form");
		getContentPane().setBackground(Color.black);
		setLayout(null);
		setSize(458, 417);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	// methods
	
	void add() {
		try (FileWriter fw = new FileWriter(filename, true)) {
			
			fw.write(name + delimiter + course + delimiter + section + "\n");
			fw.close();
			
			
			
		} catch (IOException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
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
			while ((line = br.readLine()) != null) {
				
				if (rowIndex == selectedrow) {
					String updatedrecord = name + delimiter + course + delimiter + section;
					temprecord.add(updatedrecord);

				} else {
					temprecord.add(line);
				}
			
				rowIndex++;
				
			}
			
			
			
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(scroll, e.getMessage());
		}
		
		
		
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
			
			for (String record:temprecord) {
				bw.write(record + "\n");
			}
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, x.getMessage());
		}
			
		
		
	}
	
	
	
	
	
	
	void delete() {
		int selectedrow = table.getSelectedRow();
		
		if (selectedrow == -1) {
			JOptionPane.showMessageDialog(null, "Select a record to delete.");
			return;
		}
		
		
		int confirm = JOptionPane.showConfirmDialog(null, "Delete record?", "Confirm", JOptionPane.YES_NO_OPTION);
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
			
			
			
			
		}catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
		
		
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
			
			for (String record:temprecord) {
				bw.write(record + "\n");
			}
			
		} catch (IOException x) {
			JOptionPane.showMessageDialog(null, x.getMessage());
		}
		
		
		
		
	}
	
	
	
	
	
	
	void refresh() {
		model.setRowCount(0);
		
		File file = new File(filename);
		if (!file.exists()) {
			return;
		}
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] row = line.split(delimiter);
				model.addRow(row);
			}
			
			
		} catch (IOException a) {
			JOptionPane.showMessageDialog(null, a);
		}
		
		
		
	}
	
	
	
	
	void clear() {
		txtname.setText("");
		txtcourse.setText("");
		txtsection.setText("");
	}
	
	
	
	
	
	public static void main(String[] args) {
		new StudentInformationForm();

	}

}
