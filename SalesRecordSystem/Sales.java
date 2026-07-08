package InterventionProgram;

public class Sales {
	// txtID, txtagent, txtsold, txtprice, txttotal, txtdate;
	private String salesID, agentName, saledate, product;
	private int unitSold, unitPrice, totalsale;
	
	
	public Sales (String id, String name, String prod,
			int sold, int price, int total, String date ) {
		
		salesID = id;
		agentName = name;
		product = prod;
		unitSold = sold;
		unitPrice = price;
		totalsale = total;
		saledate = date;
		
		
	}
	
	
	public String getID() {return salesID;}
	public String getname() {return agentName;}
	public String getproduct() {return product;}
	public int getsold() {return unitSold;}
	public int getprice() {return unitPrice;}
	public int gettotal() {return totalsale;}
	public String getdate() {return saledate;}
	
	
	
	
	
	
}
