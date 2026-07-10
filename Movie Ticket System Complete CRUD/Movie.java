package InterventionProgram;

public class Movie {
	private String receiptnum, movietitle, customername, paystatus;
	private int seatnum, quantity, price;

	
	
	public Movie (String receipt, String title, String name, int seat, 
				int qty, int p, String status) {
		
		receiptnum = receipt;
		movietitle = title;
		customername = name;
		seatnum = seat;
		quantity = qty;
		price = p;
		paystatus = status;
		
		
	}
	
	public String getReceipt() {return receiptnum;}
	public String getTitle() {return movietitle;}
	public String getName() {return customername;}
	public String getStatus() {return paystatus;}
	public int getSeat() {return seatnum;}
	public int getQty() {return quantity;}
	public int getPrice() {return price;}
	
	
	public String toFileString(String DELIMITER) {
		return receiptnum + DELIMITER +
				movietitle + DELIMITER +
				customername + DELIMITER +
				seatnum + DELIMITER +
				quantity + DELIMITER +
				price + DELIMITER +
				paystatus;
		
	}
	
	
	
	
}
