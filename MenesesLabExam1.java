package CCE107Activities;
import javax.swing.*;
import java.awt.*;
import java.io.*;
public class MenesesLabExam1 extends JFrame{
	
		static String storename;
		static int receiptnum;
		static double total = 0;
		static double tax = 0;
		static double finalamount = 0;
		
		
		
		MenesesLabExam1() {
		JLabel lblheader = new JLabel("Expense Tracker");
		add(lblheader).setBounds(120, 20, 400, 80);
		lblheader.setFont(new Font("Arial", Font.BOLD, 20));
		
		JLabel lblreceiptnum = new JLabel("Receipt Number");
		add(lblreceiptnum).setBounds(30, 120, 200, 20);
		
		JTextField txtreceiptnum = new JTextField();
		add(txtreceiptnum).setBounds(150, 120, 200, 20);
		
		JLabel lblstorename = new JLabel("Store Name");
		add(lblstorename).setBounds(30, 180, 200, 20);
		
		JTextField txtstorename = new JTextField();
		add(txtstorename).setBounds(150, 180, 200, 20);
		
		JLabel lbltotal = new JLabel("Total Cost");
		add(lbltotal).setBounds(30, 240, 200, 20);
		
		JTextField txttotal = new JTextField();
		add(txttotal).setBounds(150, 240, 200, 20);
		
		JLabel lbltax = new JLabel("Tax (12%)");
		add(lbltax).setBounds(30, 350, 200, 20);
		
		JTextField txttax = new JTextField();
		add(txttax).setBounds(150, 350, 200, 20);
		txttax.setEditable(false);
		
		JLabel lblfinal = new JLabel("Final Amount");
		add(lblfinal).setBounds(30, 380, 200, 20);
		
		JTextField txtfinal = new JTextField();
		add(txtfinal).setBounds(150, 380, 200, 20);
		txtfinal.setEditable(false);
		
		
		
		//buttons
		JButton btn1 = new JButton("Record");
		add(btn1).setBounds(100, 450, 100, 30);
		
		
		JButton btn2 = new JButton("Clear");
		add(btn2).setBounds(250, 450, 100, 30);
		
		
		
		btn1.addActionListener(e -> {
			
		storename = txtstorename.getText();
		receiptnum = Integer.parseInt(txtreceiptnum.getText());
		total = Double.parseDouble(txttotal.getText());
		tax = total * 0.12;
		finalamount = total + tax;
		
		txttax.setText(String.valueOf(tax));
		txtfinal.setText(String.valueOf(finalamount));
		
		
		record();
		});
		
		
		
		btn2.addActionListener(e -> {
			
		txtreceiptnum.setText("");
		txtstorename.setText("");
		txtreceiptnum.setText("");
		txttotal.setText("");
		txtfinal.setText("");
		
		});
		
		
		
		
		
		setTitle("Meneses Lab Exam");
		setSize(500, 700);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		
		
		
		}
		
		
		
		static void record() {
		try {
		FileWriter fw = new FileWriter("Meneses545673expense.txt", true);
		
		
		fw.write("Receipt Number: " + receiptnum + "|" + "Store Name: "+ storename +"Total: "+ total + "|" +"Final amount: "+
		finalamount + "\n");
		
		
		fw.close();
		}
		
		catch (IOException e) {
		System.out.print("System error. " + e);
		}
		
		
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		public static void main(String[] args) {
		new MenesesLabExam1();
		}
		}