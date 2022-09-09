import java.util.ArrayList;
import java.util.Scanner;

public class Dance implements DanceInterface {
	private ArrayList<String> dancers = new ArrayList<String>();
	private boolean added;
	private String name;
	private ArrayList<Dance> common = new ArrayList<Dance>(); 
	private ArrayList<Dance> notCommon = new ArrayList<Dance>();
	
public Dance(String dance) {
	this.name = dance.strip();
	this.added = true;
}
	
public Dance(String dance, String dancers) {
	this.name = dance.strip();
	this.added = false;
	Scanner sc = new Scanner(dancers);
	sc.useDelimiter(",");
	while(sc.hasNext()) {
	this.dancers.add(sc.next().trim().replaceAll("\"", ""));
	}
	sc.close();
	
	}

public void setCommon(ArrayList<Dance> allDances) {
	for(int i = 0; i < allDances.size(); ++i) {
		if(commonDancer( allDances.get(i))) {
			common.add(allDances.get(i));
		}
	}
	//System.out.println(this + "Common" + common);
}
private void setNotCommon() {
	for(int i = 0; i < DanceTester.getDances().size(); ++i) {
		if(!commonDancer(DanceTester.getDances().get(i))) {
			notCommon.add(DanceTester.getDances().get(i));
		}
	}
}
public void setAdded() {
	this.added = true;

}
public void unadd() {
	this.added = false;
}
public boolean getAdded() {
	return this.added;
}
public String getName() {
	return this.name;
}
public void setName(String name) {
	this.name = name;
}
public ArrayList<String> getDancers() {
	return dancers;
}
public ArrayList<String> getCommonDancers(Dance common){
	ArrayList<String> commonDancers = new ArrayList<String>();
	if(this.commonDancer(common)) {
		for(int i = 0; i < this.getDancers().size(); ++i) {
			if(common.getDancers().contains(this.getDancers().get(i))) {
				commonDancers.add(this.dancers.get(i) + ", ");
			}
		}
		
	}
	return commonDancers;
	
}

public boolean commonDancer(Dance dance1) {
	//System.out.println(dance1.getDancers());
	//System.out.println(dance2.getDancers());
	if(dance1.equals(this)) return true;
	for(int i = 0; i < this.getDancers().size(); ++i) {
		if(dance1.getDancers().contains(this.getDancers().get(i))) {
			//System.out.println("Dance 1: " + dance1 + "Dance 2: " +  this + "Common Dancer: " + this.getDancers().get(i));
			return true;
		}
	}
	
	return false;
}
public ArrayList<Dance> getCommon() {
	return this.common;
}
@Override
public  String toString() {
	return this.name;
	
}
@Override
public boolean equals(Object o) {
	return o.toString().equals(this.name);
}
}


