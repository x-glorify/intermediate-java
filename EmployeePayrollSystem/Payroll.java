package InterventionProgram;

public class Payroll {

	private String employeeID, name, position, paydate;
	private double hours, rate, totalsalary;
	
	
	
	
	public Payroll(String employeeID, String name, String position, String paydate,
							double hours, double rate, double totalsalary) {
		
		this.employeeID		= employeeID;
		this.name			= name;
		this.position		= position;
		this.hours			= hours;
		this.rate			= rate;
		this.totalsalary		= totalsalary;
		this.paydate			= paydate;
		
		
	}
	
	
	
	
	public String getEmployeeID()	{ return employeeID; }
	public String getName()			{ return name; }
	public String getPosition()		{ return position; }
	public String getPayDate()		{ return paydate;}
	public double getHours()			{ return hours; }
	public double getRate()			{ return rate; }
	public double getTotalSalary()	{ return totalsalary; }
	
	
	
	
	
	public void setEmployeeID(String employeeID)		{ this.employeeID = employeeID; }
	public void setName(String name)					{ this.name = name; }
	public void setPosition(String position)			{ this.position = position; }
	public void setPayDate(String paydate)			{ this.paydate = paydate; }
	public void setHours(double hours)				{ this.hours = hours; }
	public void setRate(double rate)					{ this.rate = rate; }
	public void setTotalSalary(double totalsalary)	{ this.totalsalary = totalsalary; }
	
 	
	
	
}
