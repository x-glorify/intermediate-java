package CCE107Activities;
import java.util.Scanner;
public class HotelRoomReservationSystem {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int floors = 7;
		int rooms = 5;
		
		int hotel[][] = new int[floors][rooms];
		
		
		System.out.println("=== HOTEL RESERVATION SYSTEM ===");
		
		int choice = 0;
		do {
			System.out.println("\nMENU\n1. View Rooms\n2. Check In\n3. Check Out\n4. Exit ");
			System.out.print("\nEnter Choice: ");
			choice = sc.nextInt();
			
			
			
			switch (choice) {
			
			case 1: 
					viewRooms(hotel, floors, rooms);
					break;
			
			
			case 2: 
					checkIn(hotel, sc);
					break;
				
			case 3:
				
					checkOut(hotel, sc);
					break;
				
			case 4:
				
				System.out.print("THANK YOU!");
				break;
				
				
			
				default: 
					System.err.print("Invalid input ! ! !");
						
					
		
			
			
			} //switch-case close bracket
			
			
			
			
		}
		while (choice != 4);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// choice 1
	public static void viewRooms (int[][] hotel, int floors, int rooms) {
		System.out.print("\nRoom Status (1 = occupied, 0 = unoccupied)\n");
		for (int i = floors-1; i >= 0; i--) {
			System.out.print("Floor " + (i+1) + ": ");
			for (int j = 0; j < rooms; j++) {
				System.out.print("[" +hotel[i][j] + "]");
			}
			System.out.println();
		}
		
	}
	
	
	//choice 2
	public static void checkIn(int[][] hotel, Scanner sc) {
		System.out.print("Enter floor (1-7): ");
		int floorIn = sc.nextInt();
		
			System.out.print("Enter room (1-5): ");
		int roomIn = sc.nextInt();
			
		int i = floorIn - 1;
		int j = roomIn -1;
		
		
		if (hotel[i][j] == 1) {
			System.out.print("Room already occupied.");
		} else {
			System.out.print("Check-in successful!");
			hotel[i][j] = 1;
		}
		
	}
	
	
	
	// choice 3
	public static void checkOut (int[][] hotel, Scanner sc) {
		System.out.print("Enter floor (1-7): ");
		int floorOut = sc.nextInt();
		
			System.out.print("Enter room (1-5): ");
		int roomOut = sc.nextInt();
			
		int i = floorOut - 1;
		int j = roomOut -1;
		
		if (hotel[i][j] == 1) {
			System.out.print("Check-out successful!");
			hotel[i][j] = 0;
		} else {
			System.out.print("Room already empty.");
		}
	}
	
	
	
	

}
