package CCE107Final;
import java.io.*;
import java.time.*;
import java.time.format.*;
public class Booking {
	private String ID, name, roomtype, status;
	private LocalDate checkIn, checkOut;
	private int roomnum;
	private double total;
	
	public Booking(String ID, String name, int roomnum, String roomtype,
				LocalDate checkIn, LocalDate checkOut, double total, String status) {
		
		this.ID			= ID;
		this.name		= name;
		this.roomnum		= roomnum;
		this.roomtype	= roomtype;
		this.checkIn		= checkIn;
		this.checkOut	= checkOut;
		this.total		= total;
		this.status		= status;
		
	}
	
	
	public String getID()			{return ID;}
	public String getName()			{return name;}
	public int getroomnum()			{return roomnum;}
	public String getroomtype()		{return roomtype;}
	public LocalDate getcheckIn()	{return checkIn;}
	public LocalDate getcheckOut()	{return checkOut;}
	public double getTotal()			{return total;}
	public String getstatus()		{return status;}
	
	
	
	public String toFileString(String delimiter) {
		return getID() + delimiter +
				getName() + delimiter +
				getroomnum() + delimiter +
				getroomtype() + delimiter +
				getcheckIn() + delimiter +
				getcheckOut() + delimiter +
				getTotal() + delimiter +
				getstatus() + delimiter;
	}
	
	
	
	
}
