import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
/*
 * This interface defines a class that loads a csv file with list of dances, choreographers, and dancers
*/
public interface DanceLoaderInterface {
	/*
	 * Loads a csv file with dances, cheographers, and dancers
	 * @param csvFilePath where the csv file is located 
	 *   
	 */
	public ArrayList<String> loadDances(File csvFile) throws FileNotFoundException;
	public ArrayList<String> loadDancers(File csvFile) throws FileNotFoundException;
}
