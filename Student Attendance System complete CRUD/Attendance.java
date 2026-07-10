package InterventionProgram;

public class Attendance {
		private String studentID, name, status, date;
		
		public Attendance(String studentID, String name, String status, String date) {
			this.studentID = studentID;
			this.name = name;
			this.status = status;
			this.date = date;
		}
		
		
		
		public String getID() {return studentID;}
		public String getName() {return name;}
		public String getStatus() {return status;}
		public String getDate() {return date;}
		
		
		
		public String toFileString(String DELIMITER) {
			return studentID + DELIMITER +
					name + DELIMITER +
					status + DELIMITER +
					date;
		}
		
}
