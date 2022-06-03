import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector; 

/** Class which represents useful helper to manager files */
public class FileHelper {	
	/**
	 * Read content of file specified by its path
	 * @param filePath Path of file to read
	 * @return File content as a vector of integers
	 */
	public Vector<Integer> readFile(String filePath) {
		Vector<Integer> rankingResults = new Vector<Integer>();
	    try {
	        File myObj = new File(filePath);
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {
	          String data = myReader.nextLine();
	          rankingResults.add(Integer.parseInt(data));
	        }
	    } catch(FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    return rankingResults;
	}
	
	/**
	 * Write to file specified by its path
	 * @param filePath Path of file to write
	 * @param data Data which will be written to specified file
	 */
	public void writeFile(String filePath, Vector<Integer> data) {
		try {
			FileWriter myWriter = new FileWriter(filePath);
			for(int i = 0; i < data.size(); ++i) {
				myWriter.write(String.valueOf(data.get(i)) + "\n");
			}
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
