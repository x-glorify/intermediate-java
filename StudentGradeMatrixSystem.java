package CCE107Activities;
import java.util.*;
public class StudentGradeMatrixSystem {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		
		// task 1 - grade input
		int students = 3;
		int subjects = 3;
		
		double[][] grades = new double[students][subjects]; 
		String[] subjectName = {"English", "Math", "Science"};
		
		
		for (int i=0; i<students; i++) {
			System.out.println("Student: " + (i + 1));
			
			for (int j = 0; j < subjects; j++) {
			System.out.print(subjectName[j] + ": ");
			grades[i][j] = sc.nextDouble();
			}
			
		}
		
		System.out.println();
		
		
		
		
		
		// task 2 table
		
		System.out.println();
		
		System.out.printf("%-10s", " ");
		for (int j = 0; j < subjects; j++) {
			System.out.printf("%-10s", subjectName[j]);
		}
		
		System.out.println();
		
		
		for (int i = 0; i < students; i++) {
				System.out.printf("%-10s", "Student " + (i+1));
				
				
			for (int j = 0; j < subjects; j++) {
				System.out.printf("%-10.2f", grades[i][j]);
			}
			System.out.println();
		}
		
		
		System.out.println();
		
		
		
		// task 3 - average per student
		System.out.print("Student average \n");
		for (int i = 0; i < students; i++) {
			int sum = 0;
			for (int j = 0; j < subjects; j++) {
				sum += grades[i][j];
			}
			double average = sum / subjects;
			System.out.print("Student " + (i+1) + ": " + average + "\n");
			
		}
		
		System.out.println();
		
		
		
		
		
		//task 4 - average per subject
		System.out.print("Average per subject \n");
		for (int j = 0; j < subjects; j++) {
			int sum = 0;
			
			for (int i = 0; i < students ; i++) {
				sum += grades[i][j];
			}
			double average = sum / students;
			System.out.print(subjectName[j] +": "+ average + "\n");
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		}
		
		

}
