import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * This interface defines a class that loads a csv file with list of dances, choreographers, and dancers
*/


public class DanceLoader implements DanceLoaderInterface{

	@Override
	public ArrayList<String> loadDances(File csvFile) throws FileNotFoundException {
		ArrayList<String> dances = new ArrayList<>();
        Scanner sc = new Scanner(csvFile);
        sc.nextLine();
        while (sc.hasNextLine()) {
            String dance = "Default dance";
            String fullDance = sc.nextLine();
            Scanner danceSc = new Scanner(fullDance);
        	danceSc.useDelimiter(",");
        	if(danceSc.hasNext()) {
        	dance = danceSc.next();
        	}
            if(!dances.contains(dance) && !dance.equals("Default dance") && danceSc.hasNext()) dances.add(dance);
        }
        sc.close();
        return dances;

	}

	@Override
	public ArrayList<String> loadDancers(File csvFile) throws FileNotFoundException {
	    ArrayList<String> dancers = new ArrayList<>();
        Scanner sc = new Scanner(csvFile);
        sc.nextLine();
        while (sc.hasNextLine()) {
            String dancersInDance = "";
            String fullDance = sc.nextLine();
            Scanner danceSc = new Scanner(fullDance);
            danceSc.useDelimiter(",| and");
            if(danceSc.hasNext()) {
            	danceSc.next();
            }
            while(danceSc.hasNext()) {
            	dancersInDance = (dancersInDance.concat(danceSc.next() + ", "));
            	
            }
            if(dancersInDance.length() > 0) {
            dancersInDance = dancersInDance.substring(0, dancersInDance.length() - 2);
            dancersInDance.replaceAll("^\"|\"$", "");
            dancers.add(dancersInDance);
            }
        }

        sc.close();
        //dancers.remove(dancers.size() - 1);
        return dancers;

	}
	
	
}
