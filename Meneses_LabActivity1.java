package LabActivity1;
import java.util.Scanner;
import java.io.*;

public class Meneses_LabActivity1 {
	
	// method 1
	static int[] inputConditions() {
		Scanner sc = new Scanner(System.in);
		System.out.print("How many books to inspect?: ");
		int books = sc.nextInt();
		
		int[] scores = new int[books];
		
		System.out.print("Enter condition scores \n");
		
		for (int i = 0; i < books; i++) {
			System.out.print("Book " + (i + 1) + ": ");
			scores[i] = sc.nextInt();
			sc.nextLine();
		}
		return scores;
	}
	
	
	
	//method 2
	
	
	static String checkCondition(int score) {
		if (score > 85) {
			return "Excellent Condition";
		} else if (score >= 60) {
			return "Good Condition";
		} else {
			return "Needs Repair";
		}
	}
	
	
	//method 3
	
	static void saveReport(String data) {
		try {
			FileWriter fw = new FileWriter("Meneses_545673.txt");
			PrintWriter pw = new PrintWriter(fw);
			
			pw.print(data);
			
			pw.close();
			fw.close();
			
			System.out.print("Saved to text file Meneses_545673.txt");
			
		}
		
		
		catch (IOException e) {
			System.out.print("An error has occured.");
		}
		
	}
	
	

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		
		int[] scores = inputConditions();
		
		StringBuilder bookreport = new StringBuilder();
		System.out.println();
		
		
		
		for (int i = 0; i < scores.length; i++) {
			String condition = checkCondition(scores[i]);
			String info = String.format("Book %d: %d - %s \n", (i+1), scores[i], condition);
			
			System.out.print(info);
			bookreport.append(info).append("\n");
		}
		
		
		
		
		saveReport(bookreport.toString());
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
