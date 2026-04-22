package CCE107Activities;
import java.util.*;
import javax.swing.*;
import java.util.Scanner;

public class MenesesGUIHotelReservation {

	public static void main(String[] args) {
		
		
		Scanner sc = new Scanner(System.in);
		
		int floors = 7;
		int rooms = 5;
		
		int hotel[][] = new int[floors][rooms];
		
		
		String choice = null;
			
		do {
			String[] menu = {"View Rooms", "Check In", "Check Out", "Exit"};
			choice = (String) JOptionPane.showInputDialog(
					null,
					"Menu",
					"Hotel Reservation System",
					JOptionPane.QUESTION_MESSAGE,
					null,
					menu,
					menu[0]);
			
			
			switch (choice) {
			
			case "View Rooms": 
					viewRooms(hotel, floors, rooms);
					break;
			
			case "Check In": 
					checkIn(hotel, sc);
					break;
				
			case "Check Out":
				
					checkOut(hotel, sc);
					break;
				
			case "Exit":
				
				JOptionPane.showMessageDialog(null, "THANK YOU!");
					break;
				
			default: 
				JOptionPane.showMessageDialog(
							null,
							"Check out successful!",
							"Error",
							JOptionPane.ERROR_MESSAGE);
						
					
		
			
			
			} //switch-case close bracket
			
			
			
			
		}
		while (!choice.equals("Exit"));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// choice 1
	public static void viewRooms (int[][] hotel, int floors, int rooms) {
		// System.out.print("\nRoom Status (1 = occupied, 0 = unoccupied)\n");
		String roomstatus = "Room Status (1 = occupied, 0 = unoccupied)\n";
		for (int i = floors-1; i >= 0; i--) {
			roomstatus += "Floor " + (i+1) + ": ";
			
			for (int j = 0; j < rooms; j++) {
				roomstatus += " [" +hotel[i][j] + "] ";
			}
			
			roomstatus+="\n";
		}
		JOptionPane.showMessageDialog(null, roomstatus);
	}
	
	
	//choice 2
	public static void checkIn(int[][] hotel, Scanner sc) {
		
		int floorIn = Integer.parseInt(JOptionPane.showInputDialog("Enter floor (1-7):"));
		int roomIn = Integer.parseInt(JOptionPane.showInputDialog("Enter room (1-5):"));
			
		int i = floorIn - 1;
		int j = roomIn -1;
		
		
		if (hotel[i][j] == 1) {
			
			JOptionPane.showMessageDialog(null, "Room already occupied.");
		} else {
			
			JOptionPane.showMessageDialog(null, "Check in successful!");
			hotel[i][j] = 1;
		}
		
	}
	
	
	
	// choice 3
	public static void checkOut (int[][] hotel, Scanner sc) {
		
		
		int floorOut = Integer.parseInt(JOptionPane.showInputDialog("Enter floor (1-7):"));
		int roomOut = Integer.parseInt(JOptionPane.showInputDialog("Enter room (1-5):"));
			
		int i = floorOut - 1;
		int j = roomOut -1;
		
		if (hotel[i][j] == 1) {
			
			JOptionPane.showMessageDialog(null, "Check out successful!");
			hotel[i][j] = 0;
		} else {
			
			JOptionPane.showMessageDialog(null, "Room already empty.");
		}
	}
	
	
	
	



}
