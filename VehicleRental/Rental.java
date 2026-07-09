package InterventionProgram;

public class Rental {
	private String rentalID, customer, vehicle, date;
	private double days, rate, total;
	
	
	public Rental (String id, String cname, String vname, double numdays, double drate, double totalrent, String rentdate) {
		rentalID = id;
		customer = cname;
		vehicle = vname;
		days = numdays;
		rate = drate;
		total = totalrent;
		date = rentdate;
		
		
	}
	
	public String getID() {return rentalID;}
	public String getcustomer() {return customer;}
	public String getvehicle() {return vehicle;}
	public String getdate() {return date;}
	public double getdays() {return days;}
	public double getrate() {return rate;}
	public double gettotal() {return total;}
	

}
