package Practice107;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
public class HotelRoomReservationGUI extends JFrame {

	private static final String DELIMITER = "::";
	private static final String FILENAME = "RoomReservationGUI.txt";
	
	private JTextField txtreservationID, txtguestname, txtnights, txttotalcost;
	private String reservationID, guestname, boardbasis, roomtype;
	private int nights, totalcost;
	
	private JComboBox<String> comroomtype;
	private JScrollPane scroll;
	private JTable table;
	private DefaultTableModel model;
	private ButtonGroup bg;
	private ArrayList<String> temprecord;
	
	
	
	
	
		HotelRoomReservationGUI () {
				
		JLabel lblreservationID = new JLabel("Reservation ID:");
		add(lblreservationID).setBounds(30, 50, 100, 25);
		
		txtreservationID = new JTextField();
		add(txtreservationID).setBounds(130, 50, 150, 25);
		
		txtreservationID.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char a = e.getKeyChar();
				if(!Character.isDigit(a)) e.consume();
					
			}
			
		});
		
		
		
		
		JLabel lblguestname = new JLabel("Guest Name:");
		add(lblguestname).setBounds(30, 80, 100, 25);
		
		txtguestname = new JTextField();
		add(txtguestname).setBounds(130, 80, 150, 25);
		
		txtguestname.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char b = e.getKeyChar();
				if (Character.isDigit(b)) e.consume();
			}
			
			
			
		});
		
		
		
		
		
		JLabel lblnights = new JLabel("Nights:");
		add(lblnights).setBounds(30, 110, 100, 25);
		
		txtnights = new JTextField();
		add(txtnights).setBounds(130, 110, 80, 25);
		
		
		//radio btn
		
				JLabel lblboardbasis = new JLabel("Board Basis");
				add(lblboardbasis).setBounds(30, 140, 150, 25);
				
				JRadioButton rb1 = new JRadioButton("Bed Only");
				add(rb1).setBounds(30, 170, 150, 25);
				
				JRadioButton rb2 = new JRadioButton("Bed and Breakfast");
				add(rb2).setBounds(30, 200, 150, 25);
				
				bg = new ButtonGroup();
				bg.add(rb1);
				bg.add(rb2);
			
		
		JLabel lblroomtype = new JLabel("Room Type");
		add(lblroomtype).setBounds(350, 50, 150, 25);
		
		comroomtype = new JComboBox<>(new String[] {"Standard", "Deluxe", "Suite", "Presidential"});
		add(comroomtype).setBounds(350, 80, 150, 25);
		
		
		
		JLabel lbltotalcost = new JLabel("Total Cost");
		add(lbltotalcost).setBounds(570, 50, 150, 25);
		
		txttotalcost = new JTextField();
		add(txttotalcost).setBounds(570, 80, 100, 30);
		txttotalcost.setEditable(false);
		
		
		
		//buttons
		
		JButton btnreserve = new JButton("Reserve");
		add(btnreserve).setBounds(400, 150, 100, 25);
		
		JButton btndelete = new JButton("Delete");
		add(btndelete).setBounds(500, 150, 100, 25);
		
		JButton btnupdate = new JButton("Update");
		add(btnupdate).setBounds(600, 150, 100, 25);
		
		JButton btnclear = new JButton("Clear");
		add(btnclear).setBounds(700, 150, 100, 25);
		
		
		
		// button functions
		
		btnreserve.addActionListener(e->{
			
			if (txtreservationID.getText().isEmpty() || txtguestname.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Fill in empty fields!");
				return;
			}
			
			
			reservationID = txtreservationID.getText();
			guestname = txtguestname.getText();
			nights = Integer.parseInt(txtnights.getText());
			
			
			if (rb1.isSelected()) {
				boardbasis = "Bed only";
			} else {
				boardbasis = "Bed and Breakfast";
			}
			
			roomtype = comroomtype.getSelectedItem().toString();
			
			int roomrate = 0;
			
			if (roomtype.equals("Standard")) {
				roomrate = 1500;
			} else if (roomtype.equals("Deluxe")) {
				roomrate = 3000;
			} else if (roomtype.equals("Suite")) {
				roomrate = 6000;
			} else {
				roomrate = 12000;
			}
			
			if (boardbasis.equals("Bed and Breakfast")) {
				roomrate += 500;
			} else {
				roomrate += 0;
			}
			
			totalcost = nights * roomrate;
			txttotalcost.setText(String.valueOf(totalcost));
			
			
			reserve();
			reload();
			
		});
		
		
		btndelete.addActionListener(e->{
			delete();
			reload();
			
		});
		
		
		btnupdate.addActionListener(e->{
			
			if (txtreservationID.getText().isEmpty() || txtguestname.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Fill in empty fields!");
				return;
			}
			
			
			reservationID = txtreservationID.getText();
			guestname = txtguestname.getText();
			nights = Integer.parseInt(txtnights.getText());
			
			
			if (rb1.isSelected()) {
				boardbasis = "Bed only";
			} else {
				boardbasis = "Bed and Breakfast";
			}
			
			roomtype = comroomtype.getSelectedItem().toString();
			
			int roomrate = 0;
			
			if (roomtype.equals("Standard")) {
				roomrate = 1500;
			} else if (roomtype.equals("Deluxe")) {
				roomrate = 3000;
			} else if (roomtype.equals("Suite")) {
				roomrate = 6000;
			} else {
				roomrate = 12000;
			}
			
			if (boardbasis.equals("Bed and Breakfast")) {
				roomrate += 500;
			} else {
				roomrate += 0;
			}
			
			totalcost = nights * roomrate;
			txttotalcost.setText(String.valueOf(totalcost));
			
			update();
			reload();
			
		});
			
			
		btnclear.addActionListener(e->{
			clear();
			
		});	
			
			
		// table
		
		
		String[] cols = {"Reservation ID", "Guest Name", "Room Type", "Nights", "Board Basis", "Total Cost"};
		model = new DefaultTableModel(cols, 0);
		table = new JTable(model);
		scroll = new JScrollPane(table);
		scroll.setBounds(30, 250, 800, 300);
		add(scroll);
		reload();
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			
			if (row != -1) {
				
				txtreservationID.setText(model.getValueAt(row, 0).toString());
				txtguestname.setText(model.getValueAt(row, 1).toString());
				comroomtype.setSelectedItem(model.getValueAt(row, 2).toString());
				txtnights.setText(model.getValueAt(row, 3).toString());
				
				
				boardbasis = model.getValueAt(row, 4).toString();
				if (rb1.isSelected()) {
					boardbasis = "Bed only";
				} else {
					boardbasis = "Bed and Breakfast";
				}
				
				
				txttotalcost.setText(model.getValueAt(row, 5).toString());
				
				
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
		
		
		void reserve() {
			try (FileWriter fw = new FileWriter(FILENAME, true)) {
				fw.write(reservationID + DELIMITER + guestname + DELIMITER + roomtype + DELIMITER + nights
						+ DELIMITER + boardbasis + DELIMITER + totalcost + "\n");
				
				fw.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		
		
		void delete() {
			int selectedrow = table.getSelectedRow();
			
			if (selectedrow == -1) {
				JOptionPane.showMessageDialog(null, "Select a record to delete.");
				return;
			}
			
			int confirm = JOptionPane.showConfirmDialog
						(null,"Delete this record?", "Confirm", JOptionPane.YES_NO_OPTION);
			
			if (confirm != JOptionPane.YES_OPTION) return;
			
			
			
			temprecord = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
				String line;
				int rowindex = 0;
				
				while((line = br.readLine()) != null) {
					if (rowindex != selectedrow) temprecord.add(line);
					rowindex++;
				}
				
				
				
				
			}catch (IOException x) {
				JOptionPane.showMessageDialog(null, x);
			}
			
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
				for (String record:temprecord) bw.write(record + "\n");
				
				
			}catch (IOException z) {
				JOptionPane.showMessageDialog(null, z);
			}
			
			
			
			
		}
	
	
		
		
		void update() {
			int selectedrow = table.getSelectedRow();
			
			if (selectedrow == -1) {
				JOptionPane.showMessageDialog(null, "Select a record to delete.");
				return;
			}
			
			
			
			
			
			temprecord = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
				String line;
				int rowindex = 0;
				
				while((line = br.readLine()) != null) {
					if (rowindex == selectedrow) {
						String updatedinfo = reservationID + DELIMITER + guestname + DELIMITER + roomtype + DELIMITER + nights
								+ DELIMITER + boardbasis + DELIMITER + totalcost;
						temprecord.add(updatedinfo);
					}
					rowindex++;
				}
				
				
				
				
			}catch (IOException x) {
				JOptionPane.showMessageDialog(null, x);
			}
			
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
				for (String record:temprecord) bw.write(record + "\n");
				
				
			}catch (IOException z) {
				JOptionPane.showMessageDialog(null, z);
			}
			
			
			
		}
		
		
		void reload() {
			model.setRowCount(0);
			
			File file = new File(FILENAME);
			if (!file.exists()) return;
			
			
			try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
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
			txtreservationID.setText("");
			txtguestname.setText("");
			txtnights.setText("");
			bg.clearSelection();
			comroomtype.setSelectedIndex(0);
			txttotalcost.setText("");
		}
		
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new HotelRoomReservationGUI();
	}

}
