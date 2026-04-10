package Meneses545673;
import java.util.Scanner;
public class HotelRoomReservationSystem {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int floors = 7;
		int rooms = 5;
		
		
		int hotel[][] = new int[floors][rooms];
		
		
		System.out.print("Welcome to Eiko's hotel.");
		
		int choice = 0;
		do {
			System.out.print("MENU\n1. View Rooms\n2. Check In\n3. Check Out\n4. Exit ");
			System.out.print("\nEnter: ");
			choice = sc.nextInt();
			
			
			
			switch (choice) {
			
			case 1: 
				for (int i = floors-1; i >= 0; i--) {
					System.out.print("Floor " + (i+1) + ": ");
					for (int j = 0; j < rooms; j++) {
						System.out.print(hotel[i][j]);
					}
					System.out.println();
				}
			break;
			
			
			case 2: 
				for (int i = floors -1; i >= 0; i--) {
					System.out.print("Enter floor (1-7): ");
					floors = sc.nextInt();
					sc.nextLine();
					
					for (int j=0; j < rooms; j++) {
						System.out.print("Enter room (1-5): ");
						rooms = sc.nextInt();
						
						
						if (hotel[floors][rooms] == 1) {
							System.out.print("Room not Available.");
						} else {
							System.out.print("Room: " + (i+j) + "booked successfully!");
							hotel[i][j] = 1;
						}
					}
				}
					
		
			
			
			} //switch close bracket
			
			
			
			
		}
		while (choice != 4);
	
	}

}
