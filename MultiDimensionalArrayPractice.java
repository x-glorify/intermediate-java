package CCE107Activities;

public class MultiDimensionalArrayPractice {
	public static void main(String[] args) {
		
		
		
		String[][] names = {{"Neuvillette", "Wriothesley", "Furina"},
							{"Venti", "Dahlia", "Varka"}, 
							{"Zhongli", "Xianyun", "Xiao"}};
		
		
		// Goes through each ROW (row loop = vertical)
		for (int row = 0; row < names.length; row++) { 
			
			// Goes through each COLUMN (column loop = horizontal
			for (int col = 0; col < names[row].length; col++) { 
				
				System.out.println("Row [" + row + "] Col [" + col + "] = "
									+ names[row][col]);
			}
		}
		
		// names[row].length is "How many elements are inside THIS row?"
		
	}
}
