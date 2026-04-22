package CCE107Activities;
import javax.swing.*;
import java.io.*;
public class StudentFeedbackSystem {

	public static void main(String[] args) {
		
		collectFeedback();
		
		if (totalfeedbacks > 0) {
		    feedbackOutput(records, totalfeedbacks, totalrating);
		} else {
		    JOptionPane.showMessageDialog(null, "No feedback entered.");
		} 
		
		
		
		
		
		
	}
	
	
	
	static String records = "";
	static int totalfeedbacks = 0;
	static int totalrating = 0;
	
	
	static void collectFeedback()  {
		
		int choice = JOptionPane.YES_OPTION;
		
		while(choice != JOptionPane.NO_OPTION) {
		String name = JOptionPane.showInputDialog("Enter your name");
		if (name == null) break;
		
		String course = JOptionPane.showInputDialog("Enter program");
		if (course == null) break;
		
		String feedback = JOptionPane.showInputDialog("Type your feedback");
		if (feedback == null) break;
		
		String ratinginput = JOptionPane.showInputDialog("Enter rating (1-5)");
		if (ratinginput == null) break;
		
		int rating = Integer.parseInt(ratinginput);
		
		String category = ratingCategory(rating);
		
		
		//Storing records
		
		records += "\nStudent name: " + name +
					"\nCourse: " + course +
					"\nFeedback: " + feedback +
					"\nRating: " + rating + " (" + category + ")\n\n";
		
		totalfeedbacks++;
		totalrating += rating;
		
		
		
		choice = JOptionPane.showConfirmDialog(
				null,
				"Do you want to continue?",
				"Continue?",
				JOptionPane.YES_NO_OPTION);
		
		
		
		}
		
		
		
	}
	
	
	

	// rating
	
	static String ratingCategory(int rating) {
		switch (rating) {
		case 5: return "Excellent";
		case 4: return "Good";
		case 3: return "Average";
		case 2: return "Poor";
		case 1: return "Very Poor";
		default: return "Invalid Input";
		
		}
	}
	

	
	static void feedbackOutput(String records, int totalfeedbacks, int totalrating) {
		
		double avg = (totalfeedbacks > 0) ? (double) totalrating / totalfeedbacks : 0;

        String output =
                "--- Student Feedback Records ---\n\n" +
                records +
                "--------------------------\n" +
                "Total Feedbacks: " + totalfeedbacks + "\n" +
                "Average Rating: " + String.format("%.1f", avg) + "\n" +
                "--------------------------" + "\n Saved to file MenesesStudentFeedback.txt";

        JOptionPane.showMessageDialog(null, output);
		
        
        try {
            FileWriter fw = new FileWriter("MenesesStudentFeedback.txt");
            fw.write(output);
            fw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File error occurred!");
        }
    
	}
	
	
	
	
	
}
