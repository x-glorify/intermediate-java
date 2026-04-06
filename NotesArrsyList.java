package CCE107Activities;
import java.util.*;
public class NotesArrsyList {

	public static void main(String[] args) {
		
		
		
		
		//ArrayList - a resizeable array that stores objects
		
		ArrayList<String> names = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
	     

		System.out.println("Enter names. Type stop when done");
		
		
		while (true) {
			String userinput = sc.nextLine();
			if (userinput.equalsIgnoreCase("Stop")) {
				break;
		}
		
		
		names.add(userinput);
		
		}
		
		
	for (int i = 0; i < names.size(); i++ ) {
		System.out.println("Name " + (i+1) + ": " + names.get(i));
	}
		
		
		
		
		
		

	}

}
