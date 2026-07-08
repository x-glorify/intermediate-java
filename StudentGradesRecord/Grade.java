package InterventionProgram;

public class Grade {
	
	private String studentID, studentName, subject;
	private int prelimgrade, midtermgrade, finalgrade, average;
	
	public Grade(String id, String name, String subj, int pre, int mid, int fi, int avg) {
		
		studentID = id;
		studentName = name;
		subject = subj;
		prelimgrade = pre;
		midtermgrade = mid;
		finalgrade = fi;
		average = avg;
	
	}
	
	
	public String getstudentID() {return studentID;}
	public String getstudentName() {return studentName;}
	public String getsubject() {return subject;}
	public int getprelim() {return prelimgrade;}
	public int getmidterm() {return midtermgrade;}
	public int getfinal() {return finalgrade;}
	public int getaverage() {return average;}
	
	

}
