package Practice107;
import javax.swing.*;
import java.awt.*;
import java.io.*;
public class GUIReview extends JFrame{
	
	//global  variables
	static String name, address, email;
	static int number = 0;
	
	
	GUIReview() {
		
	
		// space in between label and box is 40
		// space between label and next label is 90
		
		JLabel lblname = new JLabel("Full Name");
		add(lblname).setBounds(50, 80, 200, 30);
		lblname.setForeground(Color.white);
		lblname.setFont(new Font("Arial", Font.PLAIN , 13));
		
		JTextField txtname = new JTextField();
		add(txtname).setBounds(50, 120, 300, 30);
		
		
		JLabel lblemail = new JLabel("E-mail");
		add(lblemail).setBounds(50, 170, 200, 30);
		lblemail.setForeground(Color.white);
		lblemail.setFont(new Font("Arial", Font.PLAIN , 13));
		
		JTextField txtemail = new JTextField();
		add(txtemail).setBounds(50, 210, 300, 30);
		
		
		JLabel lbladdress = new JLabel("Address");
		add(lbladdress).setBounds(50, 260, 200, 30);
		lbladdress.setForeground(Color.white);
		lbladdress.setFont(new Font("Arial", Font.PLAIN , 13));
		
		JTextField txtaddress = new JTextField();
		add(txtaddress).setBounds(50, 300, 300, 30);
		
		
		JLabel lblnumber = new JLabel("Contact Nummber");
		add(lblnumber).setBounds(50, 350, 200, 30);
		lblnumber.setForeground(Color.white);
		lblnumber.setFont(new Font("Arial", Font.PLAIN , 13));
		
		JTextField txtnumber = new JTextField();
		add(txtnumber).setBounds(50, 390, 300, 30);
		
		
		// button x-axis 120
		JButton btndelete = new JButton("Delete");
		add(btndelete).setBounds(50, 440, 100, 25);
		btndelete.setForeground(Color.white);
		btndelete.setBackground(Color.black);
		
		JButton btnupdate = new JButton("Update");
		add(btnupdate).setBounds(170, 440, 100, 25);
		btnupdate.setForeground(Color.black);
		btnupdate.setBackground(Color.yellow);
		
		JButton btnregister = new JButton("Register");
		add(btnregister).setBounds(290, 440, 100, 25);
		btnregister.setForeground(Color.white);
		btnregister.setBackground(Color.blue);
		
		
		btnregister.addActionListener(e -> {
			name = txtname.getText();
			email = txtemail.getText();
			address = txtaddress.getText();
			number = Integer.parseInt(txtnumber.getText());
			
			register();
			
			
		});
		
		
		
		
		getContentPane().setBackground(Color.decode("#950606"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(1920, 1080);
		setVisible(true);
		
		
		
	}
		
		
		static void register() {
			
			try {
				FileWriter fw = new FileWriter("StudentRegister.txt", true);
				
				fw.write(name + " | " + address + " | " + email + " | " + number + "\n");
				fw.close();
				
			}
			catch (IOException e) {
				System.out.print("System error" + e);
			}
			
			
	}
	

	public static void main(String[] args) {
		new GUIReview();
		

	}

}
