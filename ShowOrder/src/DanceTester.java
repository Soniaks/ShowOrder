import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;


public class DanceTester {
	private static Dance first;
	private static Dance preInter;
	private static Dance postInter;
	private static Dance last;
	private static ArrayList <Dance> allDances = new ArrayList<Dance>();
	
	public static ArrayList<Dance> getDances(){
		return allDances;
	}

	public static void main(String[]args) {
		DanceLoader loader = new DanceLoader();
		ArrayList <String> dances = new ArrayList<String>();
		ArrayList <String> dancers = new ArrayList<String>();
		try {
			File file = new File(System.getProperty("user.dir") + "/src/ShowOrder.csv");
			
			dances = loader.loadDances(file);
			dancers = loader.loadDancers(file);
			for(int i = 0; i < dancers.size(); ++i) {
				//System.out.println("Dance: " + dances.get(i) + "Dancers: " + dancers.get(i));
				//System.out.println();
			}
			
			//System.out.println("Last dance: " + dances.get(dances.size()-1));
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//OrderFinder finder = new OrderFinder(dances, dancers);
		for(int i = 0; i < dances.size(); ++i) {
			Dance add = new Dance(dances.get(i), dancers.get(i));
			allDances.add(add);
		}

		String first = "Kim Possible";
		String preInter = "Boys";
		String postInter = "Dress";
		String end = "Great One";
		Dance dFirst = new Dance(first);
		Dance dPreInter = new Dance(preInter);
		Dance dPostInter = new Dance(postInter);
		Dance dEnd = new Dance(end);
		ArrayList<Dance> allDances = new ArrayList<Dance>();
		PriorityQueue<Schedule2> best = new PriorityQueue<Schedule2>();
		for(int i = 0; i < dances.size(); ++i) {
			Dance add = new Dance(dances.get(i), dancers.get(i));
			if(add.toString().equalsIgnoreCase(first)) dFirst = add;
			if(add.toString().equalsIgnoreCase(preInter)) dPreInter = add;
			if(add.toString().equalsIgnoreCase(postInter)) dPostInter = add;
			if(add.toString().equalsIgnoreCase(end)) dEnd = add;
			allDances.add(add);
		}
		for(int i = 0; i < 40; ++i) {
		Collections.shuffle(allDances);
		Schedule2 test = new Schedule2(allDances);
		ArrayList <Dance> firstHalf = new ArrayList<Dance>();
		firstHalf.add(dFirst);
		firstHalf.add(dPreInter);
		ArrayList <Dance> secondHalf = new ArrayList<Dance>();
		secondHalf.add(dPostInter);
		secondHalf.add(dEnd);
		for(int j = 0; j <= allDances.size()/2;++j) {
			Dance add = allDances.get(j);
			if(firstHalf.contains(allDances.get(j)) || allDances.get(j).toString().equals(postInter) || allDances.get(j).toString().equals(end)){
			continue;
			}
			else {
				firstHalf.add(add);
			}
		}
		for(int j = (allDances.size()/2) + 1; j < allDances.size(); ++j) {
			Dance add = allDances.get(j);
			if(firstHalf.contains(allDances.get(j)) || secondHalf.contains(allDances.get(j))){
			continue;
			}
			else {
				secondHalf.add(add);
			}
		}
		test.fullShow(firstHalf, secondHalf, dFirst, dEnd);
		
		//System.out.println("First half miss: " + test.getFirstMiss());
		//System.out.println("Second half miss: " + test.getSecondMiss());
		//System.out.println("test: " + test.toString());
		for(int j = 0; j < allDances.size(); ++j) {
			allDances.get(j).unadd();
		}
		if(test.getShow().size() - 2 == dances.size()) best.add(test);
		


		}
		for(int i = 0; i < best.size(); ++i) {
			Schedule2 check = best.poll();
			System.out.println("Checking: " + check.check());
		}
		System.out.println("BEST SIZE: " + best.size());
		if(best.size() > 0) {
		System.out.println(best.poll());
		}

}
}
