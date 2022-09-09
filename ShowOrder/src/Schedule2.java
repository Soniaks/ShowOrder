import java.util.ArrayList;


public class Schedule2 implements Comparable {
	private  ArrayList<Dance> dances;
	private int singleQuickChangeCount; // number of single quick changes in this schedule 
	private int doubleQuickChangeCount; //number of double quick changes in this schedule
	private ArrayList<Dance> doubleQuick;
	private ArrayList<Dance> singleQuick;
	private int numDances; // number of dances in the recital
	private int possible; // all possible show orders
	private int possibleConflicts; // all possible conflicts with dancers being in multiple dances
	private int scheduled; // number of dances put into schedule
	private ArrayList<Dance> show; //arraylist of all dances with a quick change
	private ArrayList<Dance> firstHalf;
	private ArrayList<Dance> secondHalf;
	private ArrayList<Dance> miss;


	public Schedule2(ArrayList<Dance> dances) {
		singleQuick = new ArrayList<Dance>();
		doubleQuick = new ArrayList<Dance>();
		miss = new ArrayList<Dance>();
		this.dances = dances;
		for(int i = 0; i < this.dances.size(); ++i) {
			this.dances.get(i).setCommon(this.dances);
		}
		this.numDances = dances.size();
		this.scheduled = 0;

		
	}
	public ArrayList<Dance> getSingleQuickDances(){
		return this.singleQuick;
	}
	public ArrayList<Dance> getDoubleQuickDances(){
		return this.doubleQuick;
	}
	public int getSingleQuick() {
		return singleQuickChangeCount;
	}
	public int getDoubleQuick() {
		return doubleQuickChangeCount;
	}
	public ArrayList<Dance> getMiss(){
		return this.miss;
	}
	/*public ArrayList<Dance> getSecondMiss(){
		return this.secondHalfMiss;
	}*/
	
	public ArrayList<Dance> getShow(){
		return this.show;
	}
	public void insert(ArrayList<Dance> show) {
		for(int i = 0; i < dances.size(); ++i) {
			Dance check = dances.get(i);
			if(check.getAdded()) continue;
			if(show.size() == 0) return;
			if(!show.get(show.size() - 1).commonDancer(check)) {
				//System.out.println("Pass first check: " + check);
				if(show.size() < 2) {
				
					show.add(check); check.setAdded(); scheduled++; return;
				}
				
				else{
					
				if(!show.get(show.size() - 2).commonDancer(check)) {
					if(show.size() < 3) {
					show.add(check); check.setAdded();++scheduled; return;
				}
					else{
						
					if(!show.get(show.size() - 3).commonDancer(check)) {
						if(show.size() < 4) {
							show.add(check); check.setAdded();++scheduled; return;
					}
						else {
							if(!show.get(show.size() - 4).commonDancer(check)) {
								show.add(check); check.setAdded();++scheduled; return;
							}
							
						}
						
					}	
					}
				}
			}
		}
		}	
		return;
	}
	public void doubleQuick(ArrayList<Dance> show) {
		for(int i = 0; i < dances.size(); ++i) {
			Dance check = dances.get(i);
			if(check.getAdded()) continue;
			if(show.size() == 0) return;
			if(!show.get(show.size() - 1).commonDancer(check)) {
				if(show.size() < 2) {
					show.add(check); check.setAdded(); scheduled++; return;
				}
				
				else{
					
				if(!show.get(show.size() - 2).commonDancer(check)) {
					if(show.size() < 3) {
					show.add(check); check.setAdded();++scheduled; return;
				}
					else{
						
					if(!show.get(show.size() - 3).commonDancer(check)) {
					//System.out.println("Double");
							show.add(check); check.setAdded();++scheduled; return;
					
	
					}	
					}
				}
			}
		}
		}	
		return;
	}
	public void singleQuick(ArrayList<Dance> show) {
		for(int i = 0; i < dances.size(); ++i) {
			Dance check = dances.get(i);
			if(check.getAdded()) continue;
			if(show.size() == 0) return;
			if(!show.get(show.size() - 1).commonDancer(check)) {
				if(show.size() < 2) {
					show.add(check); check.setAdded(); scheduled++; return;
				}
				
				else{
					
				if(!show.get(show.size() - 2).commonDancer(check)) {
					//System.out.println("Single");
					show.add(check); check.setAdded();++scheduled; return;
				

				}
			}
		}
		}	
		return;
	}
	public void insertDouble() {
		if(firstHalf.size() < secondHalf.size()) {
			this.show = firstHalf;
			
		}
		else {
			this.show = secondHalf;
		}
		if(show.size() < 4) return;
		Dance one = show.get(0);
		Dance two = show.get(1);
		Dance three = show.get(2);
		Dance four = show.get(3);
		Dance check = miss.get(0);
		for(int j = 0; j < miss.size(); ++j) {
			check = miss.get(j);
		for(int i = 0; i < show.size() - 4; ++i) {
			if(one.commonDancer(check) || two.commonDancer(check) || three.commonDancer(check) || four.commonDancer(check)) {
				if(show.indexOf(four) == show.size() - 1) break;
				one = two;
				two = three;
				three = four; 
				four = show.get(show.indexOf(four) + 1);
				continue;
			}
			else {
				//System.out.println("Inserting Double: " + check);
				show.add(show.indexOf(three),check); scheduled++; check.setAdded();
				miss.remove(check);
				if(show.equals(firstHalf)) this.firstHalf = show;
				else this.secondHalf = show;
				return;
			}
		}
		}

			if(this.dances.equals(firstHalf)) {
				one = firstHalf.get(firstHalf.size() - 2);
				two = firstHalf.get(firstHalf.size() - 1);
				for(int i = 0; i < miss.size(); ++i) {
					check = miss.get(i);
				if(!one.commonDancer(check) && !two.commonDancer(check)) {
					
					firstHalf.add(check); scheduled++; check.setAdded();
					miss.remove(check);
					return;
				}
			}
		}
			else {
				one = secondHalf.get(0);
				two = secondHalf.get(1);
				for(int i = 0; i < miss.size(); ++i) {
					check = miss.get(i);
					if(!one.commonDancer(check) && !two.commonDancer(check)) {
						
						secondHalf.add(0,check); scheduled++; check.setAdded();
						miss.remove(check);
						return;
					}
			}
		}
		
	}
	public void insertSingle() {
		if(firstHalf.size() < secondHalf.size()) {
			this.show = firstHalf;
			
		}
		else {
			this.show = secondHalf;
		}
		if(show.size() < 2) return;
		Dance one = show.get(0);
		Dance two = show.get(1);
		Dance check = miss.get(0);
		for(int j = 0; j < miss.size(); ++j) {
			check = miss.get(j);
		for(int i = 0; i < show.size() - 4; ++i) {
		
			if(one.commonDancer(check) || two.commonDancer(check)) {
			if(show.indexOf(two) == show.size() - 1) return;
				one = two;
				two = show.get(show.indexOf(two) + 1);
				continue;
			}
			else {
				//System.out.println("Inserting Single: " + check);
				show.add(show.indexOf(two),check);scheduled++; check.setAdded();
				if(show.equals(firstHalf)) this.firstHalf = show;
				else this.secondHalf = show;
				miss.remove(check);
				return;
			}
		}
		}
		if(this.dances.equals(firstHalf)) {
			one = firstHalf.get(firstHalf.size() - 1);
			for(int i = 0; i < miss.size(); ++i) {
				check = miss.get(i);
			if(!one.commonDancer(check)) {
				
				firstHalf.add(check);scheduled++; check.setAdded();
				miss.remove(check);
				return;
			}
		}
	}
		else {
			one = secondHalf.get(0);
			for(int i = 0; i < miss.size(); ++i) {
				check = miss.get(i);
				if(!one.commonDancer(check)) {
					
					secondHalf.add(0,check); scheduled++; check.setAdded();
					miss.remove(check);
					return;
				}
		}
	}
		
	}
	
	public ArrayList<Dance> getOrder(Dance first){
		ArrayList<Dance> order = new ArrayList<Dance>();
		if(!first.getAdded()) {
			order.add(first);
			first.setAdded();
		scheduled++;
		}
		while(numDances != scheduled) {
			int check = scheduled;
		    insert(order);
		    if(scheduled == check) {
		    doubleQuick(order);
		    }
		    if(scheduled == check) {
		    	singleQuick(order);
		    }

		    if(scheduled == check) {
		    	//System.out.println("NOTHING CAN BE ADDED! NumDances " + numDances + "Scheduled " + scheduled); 
		    	
		    	break;
		    }
		    	
	}
		return order;
	}
	public void insertMiss(){
		while(numDances != scheduled) {
			int check = scheduled;
			insertDouble();
			if(scheduled == check) {
				insertSingle();
			}
			if(scheduled == check) break;
		}
		return;
	}

	public ArrayList<Dance> fullShow(ArrayList<Dance>firstHalf, ArrayList<Dance>secondHalf, Dance  first, Dance end){
		this.dances = firstHalf;
		this.numDances = firstHalf.size();
		this.scheduled = 0;
		ArrayList<Dance> fullShow = new ArrayList<Dance>();
		fullShow.addAll(getOrder(first));
		for(int i = 0; i < numDances; ++i) {
			if(!dances.get(i).getAdded()) {
				miss.add(dances.get(i));
			}
		}
		
		this.firstHalf = fullShow;
		this.dances = secondHalf;
		this.scheduled = 0;
		this.numDances = secondHalf.size();
		ArrayList<Dance> temp = new ArrayList<Dance>();
		temp.addAll(getOrder(end));
		for(int i = 0; i < numDances; ++i) {
			if(!dances.get(i).getAdded()) {
				miss.add(dances.get(i));
			}
		}
		reverse(temp);
		
		this.secondHalf = temp;
		this.scheduled = this.firstHalf.size() + this.secondHalf.size();
		this.numDances = firstHalf.size() + secondHalf.size();
		insertMiss();
		if((firstHalf.size() + secondHalf.size()) != (this.firstHalf.size() + this.secondHalf.size())) return null;
		this.show = this.firstHalf;
		Dance inter = new Dance("Intermission");
		this.show.add(inter);
		this.show.addAll(this.secondHalf);
		this.show.add(new Dance("Finale"));
		System.out.println("Scheduled: " + scheduled);
		System.out.println("Show size: " + show.size());
		System.out.println("Misses: " + miss);
		System.out.println("Show: " + show);
		
		try {
			
			for(int i = 0; i < show.size() - 4; ++i) {
				
				Dance check = show.get(i);
				if(check.commonDancer(show.get(i + 2)) && !show.get(i + 2).equals(inter) && !show.get(i + 1).equals(inter)) {
					System.out.println("Single Quick: " + check + "-> " + show.get(i + 2));
					singleQuickChangeCount++;
					singleQuick.add(check);
				}
				if(check.commonDancer(show.get(i + 3)) && !show.get(i + 3).equals(inter) && !show.get(i + 2).equals(inter) && !show.get(i + 1).equals(inter)) {
					//System.out.println("Double Quick: " + check + "-> " + showOrder.get(i + 3));
					doubleQuickChangeCount++;
					doubleQuick.add(check);
				}
				/*if(check.commonDancer(show.get(i + 2)) && check.commonDancer(show.get(i + 3))) {
					System.out.println("DOUBLE AND SINGLE??? " + check + show.get(i + 3) + show.get(i + 2));
				}*/
			
			}}catch(Exception e){
				System.out.println("OUT OF BOUNDS");
				return null;
			}
		
			
			if(show.get(show.size() - 4).commonDancer(show.get(show.size()-2))) {
				singleQuickChangeCount++;
				singleQuick.add(show.get(show.size() - 4));
				
			
		}
		
		return this.show;
		
	}
	/*
	 * This method finds a schedule with the least amount of conflicts 
	 * 
	 * @param first - the first dance in the show
	 * @param last - the last dance in the show
	 */
	
private void reverse(ArrayList<Dance> second){
	for(int i = 0; i < second.size()/2; ++i) {
		Dance temp = second.get(i);
		second.set(i, second.get(second.size() - i - 1));
		second.set(second.size() - i - 1, temp);
	}
	return;
}
	@Override
	public String toString() {
		String showString= "";
		for(int i = 0; i < this.show.size(); ++i) {
			if(doubleQuick.contains(show.get(i)) && singleQuick.contains(show.get(i))) {
				showString = showString.concat(show.get(i) + " DOUBLE AND SINGLE QUICK\n");
				Dance common = show.get(i + 3);
				showString = showString.concat("\t" + "Double: " + show.get(i).getCommonDancers(common) + "\n");
				common = show.get(i + 2);
				showString = showString.concat("\t" + "Single: " +  show.get(i).getCommonDancers(common) + "\n");
			}
			else if(doubleQuick.contains(show.get(i))){
				showString = showString.concat(show.get(i) + " DOUBLE QUICK\n");
				Dance common = show.get(i + 3);
				showString = showString.concat("\t" + show.get(i).getCommonDancers(common) + "\n");
			}
			
			else if(singleQuick.contains((show).get(i))) {
				showString = showString.concat(show.get(i) + " SINGLE QUICK\n");
				Dance common = show.get(i + 2);
				showString = showString.concat("\t" + show.get(i).getCommonDancers(common) + "\n");
			}
			
			else {
			showString = showString.concat(show.get(i) + "\n");
			}

		}
		return showString;
	}
	
	public boolean check() {
		for (int i = 0; i < show.size() - 2; ++i) {
			Dance check = show.get(i);
			if(check.commonDancer(show.get(i + 1))) {
				System.out.println("ERROR CONSECUTIVE" + check + show.get(i + 1));
				return false;
			}
			if(check.commonDancer(show.get(i + 2)) && !singleQuick.contains(check)) {
				System.out.println("ERROR SHOULD BE SINGLE" + check + show.get(i + 2));
				return false;
			}
		}
		return true;
	}
	@Override
	public int compareTo(Object o) {
	return (this.singleQuickChangeCount + this.doubleQuickChangeCount) - (((Schedule2) o).getSingleQuick() + ((Schedule2) o).doubleQuickChangeCount);
	}
	
	

	}
