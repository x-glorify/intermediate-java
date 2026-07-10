package InterventionProgram;

public class Medicine {

	private String medicineID, medicineName, manufacturer, expirydate;
	private int quantity, unitcost, total;
	
	
	public Medicine (String id, String name, String mfr,
					int qty, int cost, String expiry) {
		
		medicineID = id;
		medicineName = name;
		manufacturer = mfr;
		quantity = qty;
		unitcost = cost;
		total = quantity*cost;
		expirydate = expiry;
		
	}
	
	
	public String getID() {return medicineID;}
	public String getName() {return medicineName;}
	public String getMFR() {return manufacturer;}
	public int getQty() {return quantity;}
	public int getCost() {return unitcost;}
	public int getTotal() {return total;}
	public String getExpiry() {return expirydate;}
	
	
	
	
	
}
