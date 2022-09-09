import java.util.ArrayList;


public class Schedule implements Comparable {
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
	private ArrayList<Dance> firstHalfMiss;

	public Schedule(ArrayList<Dance> dances) {
		singleQuick = new ArrayList<Dance>();
		doubleQuick = new ArrayList<Dance>();
		firstHalfMiss = new ArrayList<Dance>();
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
		return this.firstHalfMiss;
	}
	
	public ArrayList<Dance> getShow(){
		return this.show;
	}
	public ArrayList<Dance> fullShow(ArrayList<Dance>firstHalf, ArrayList<Dance>secondHalf,Dance  first, Dance preInter, Dance postInter, Dance end){
		ArrayList<Dance> showOrder = new ArrayList<Dance>();
		this.scheduled = 0;
		this.dances = firstHalf;
		this.numDances = firstHalf.size();
		firstHalf = findSchedule(first, preInter);
		if(firstHalf == null) { 
			this.show = null;
			return null;
		}
		showOrder.addAll(firstHalf);
		
		showOrder.add(new Dance("Intermisson"));
		this.scheduled = 0;
		secondHalf.addAll(this.firstHalfMiss);
		this.dances = secondHalf;
		this.numDances = secondHalf.size();
		secondHalf = findSchedule(postInter, end);
		if(secondHalf == null) {
			this.show = null;
			return null;
		}
		else if (secondHalf.size() != numDances) {
			this.show = null;
			return null;
		}
		else {
		showOrder.addAll(secondHalf);
		showOrder.add(new Dance("Finale"));
		this.show = showOrder;
		return this.show;
		}
	}
	/*
	 * This method finds a schedule with the least amount of conflicts 
	 * 
	 * @param first - the first dance in the show
	 * @param last - the last dance in the show
	 */
	
	public ArrayList<Dance> findSchedule(Dance first, Dance last) {
		ArrayList<Dance> showOrder = new ArrayList<Dance>(dances.size());
		// all dances set to first and the first and last dances are added
		Dance dance1 = first;
		Dance dance2 = first;
		Dance dance3 = first;
		Dance dance4 = first;
		showOrder.add(dance1);
		showOrder.add(last);
		last.setAdded();
		dance1.setAdded();
		scheduled += 2;
		ArrayList<Dance> not = new ArrayList<Dance>(); //arraylist of dances not yet added to be used when quick changes are needed
		int counter = 0;
		while(scheduled != numDances) {
			counter = numDances - scheduled;
		//loop used before quick changes are needed
		while (scheduled != numDances) {
			int quickNeeded = 0; //variable used to determine weather or not there are any more dances to add without needing a quick change
			ArrayList <Dance> dance1Conflicts = dance1.getCommon();
			ArrayList <Dance> dance2Conflicts = dance2.getCommon();
			ArrayList <Dance> dance3Conflicts = dance3.getCommon();
			ArrayList <Dance> dance4Conflicts = dance4.getCommon();
			
			//cycle through all dances and add a dance if it doesn't have conflicts
			for(int i = 0; i < dances.size(); ++i) {
				if(!dances.get(i).getAdded()) {
					//System.out.println("Dance not added: " + dances.get(i));
					if(!dance1Conflicts.contains(dances.get(i)) && !dance1.equals(last)) {
						//System.out.println("No conflict");
						if(!dance2Conflicts.contains(dances.get(i))) {
							//System.out.println("Single quick change" + dance2 + " : " + dances.get(i));

							if(!dance3Conflicts.contains(dances.get(i))) {
								if(!dance4Conflicts.contains(dances.get(i))) {
									if(!last.getCommon().contains(dances.get(i)) || showOrder.size() < 4) {
								showOrder.add(showOrder.indexOf(dance1) + 1, dances.get(i));
								dance4 = dance3;
								dance3 = dance2;
								dance2 = dance1;
								dance1 = dances.get(i);
								dance1.setAdded();
								scheduled++;
								quickNeeded = 1;
								//System.out.println(showOrder);
								break;
									}
								}
							}
						}
					}
				}
			
			}
			//a quick change is needed -> add unadded dances to not and run similar loop
			if(quickNeeded == 0) {
				
				//System.out.println("No quicks: " + showOrder);
				dance1 = first;
				dance2 = first;
				dance3 = first;
				not.clear();
				for(int i = 0; i < dances.size(); ++i) {
					if(!dances.get(i).getAdded()) {
						not.add(dances.get(i));
			} 
			}
			break;
			}
			
		}
		if(showOrder.size() < 3) return null;
			while (scheduled != numDances) {
				int superNeeded = 0; //used if a single quick change is needed
				for(int j = 0; j < not.size(); ++j) {
					dance1 = showOrder.get(2);
					dance2 = showOrder.get(1);
					dance3 = first;
					
				Dance check = not.get(j);
				for(int i = 3; i < showOrder.size(); ++i) {
					ArrayList <Dance> dance1Conflicts = dance1.getCommon();
					ArrayList <Dance> dance2Conflicts = dance2.getCommon();
					ArrayList <Dance> dance3Conflicts = dance3.getCommon();
					if(!check.getAdded()) {					
						if(!dance1Conflicts.contains(check) && !dance2Conflicts.contains(check) && !dance3Conflicts.contains(check) && !dance1.equals(last)) {	
									showOrder.add(showOrder.indexOf(dance1) + 1, check);
									/*try {
									System.out.println("Double Quick: " + showOrder.get(showOrder.indexOf(dance3) - 1) + "-> " + check);
									}catch(Exception e) {
										System.out.println("OUT OF BOUNDS" + showOrder);
									}*/
									dance3 = dance2;
									dance2 = dance1;
									dance1 = check;
									dance1.setAdded();
									scheduled++;
									//doubleQuickChangeCount++;
									//if(check != null)doubleQuick.add(check);
									superNeeded = 1;								
									break;	
							
						}
						else {
							if(showOrder.indexOf(dance1) != showOrder.size()-1) {
							dance3 = dance2;
							dance2 = dance1;
							dance1 = showOrder.get(showOrder.indexOf(dance1) + 1);
							}
						
						}
				
					}
					}
				}
				
			 //break;
			
				if(superNeeded == 0) {	
					
					dance1 = first;
					dance2 = first;
					dance3 = first;
					not.clear();
					for(int i = 0; i < dances.size(); ++i) {
						if(!dances.get(i).getAdded()) {
							not.add(dances.get(i));
				} 
				}
					break;
				}
				
			}
	 

			//if(change == 0 && not.size() == loop) break;
			
	
		
		while (scheduled != numDances) {
			//int change = 0;
			for(int j = 0; j < not.size(); ++j) {
				dance1 = showOrder.get(1);
				dance2 = first;
			Dance check = not.get(j);
			for(int i = 2; i < showOrder.size(); ++i) {
				ArrayList <Dance> dance1Conflicts = dance1.getCommon();
				ArrayList <Dance> dance2Conflicts = dance2.getCommon();
				if(!check.getAdded()) {					
					if(!dance1Conflicts.contains(check) && !dance2Conflicts.contains(check) && !dance1.equals(last)) {	
						if(dance2.equals(first) || !showOrder.get(showOrder.indexOf(dance2) - 1).commonDancer(check)){
								showOrder.add(showOrder.indexOf(dance1) + 1, check); 
								//System.out.println("Single Quick: " + showOrder.get(showOrder.indexOf(dance2) - 1) + "-> " + check);
								dance2 = dance1;
								dance1 = check;
								dance1.setAdded();
								scheduled++;
								//singleQuickChangeCount++;
								//if(check != null)singleQuick.add(check);
								//change = 1;								
								break;
						}
						
					}
					else {
						if(showOrder.indexOf(dance1) != showOrder.size()-1) {
						dance2 = dance1;
						dance1 = showOrder.get(showOrder.indexOf(dance1) + 1);
						}
					
					}
			
				}
				}
			}
			/*System.out.println("Scheduled: " + scheduled);
			System.out.println("Num Dances: " +  numDances);
			System.out.println("All Dances: " + this.dances);
			System.out.println("counter: " + counter);
			System.out.println("not: " + not);
			System.out.println("first half miss: " + this.firstHalfMiss);
			System.out.println("Show: " + showOrder);
			*/
			for(int p = 0; p < numDances; ++p) {
				if(!showOrder.contains(dances.get(p)) && dances.get(p).getAdded()) {
					System.out.println("isAdded but not in showOrder? " + dances.get(p));
				}
			}
		 break;
			//if(change == 0) break;
		} 
		
		if(counter == not.size()) {
			if(counter < 3) {
			for(int p = 0; p < not.size(); ++p) {
				if(!not.get(p).getAdded()) {
					this.firstHalfMiss.add(not.get(p));
				}
			}
		
			}
			else {
				return null;
			}
			break;
		}
		
		}

		try {
		for(int i = 0; i < showOrder.size() - 3; ++i) {
			Dance check = showOrder.get(i);
			if(check.commonDancer(showOrder.get(i + 2))) {
				//System.out.println("Single Quick: " + check + "-> " + showOrder.get(i + 2));
				singleQuickChangeCount++;
				singleQuick.add(check);
			}
			if(check.commonDancer(showOrder.get(i + 3))) {
				//System.out.println("Double Quick: " + check + "-> " + showOrder.get(i + 3));
				doubleQuickChangeCount++;
				doubleQuick.add(check);
			}
			if(check.commonDancer(showOrder.get(i + 2)) && check.commonDancer(showOrder.get(i + 3))) {
				System.out.println("DOUBLE AND SINGLE??? " + check + showOrder.get(i + 3) + showOrder.get(i + 2));
			}
		
		}}catch(Exception e){
			System.out.println("OUT OF BOUNDS");
			return null;
		}
	
		
		if(showOrder.get(showOrder.size() - 3).commonDancer(showOrder.get(showOrder.size()-1))) {
			singleQuickChangeCount++;
			singleQuick.add(showOrder.get(showOrder.size() - 3));
			
		
	}
		//System.out.println(showOrder);
		return showOrder;
}
	@Override
	public String toString() {
		String showString= "";
		for(int i = 0; i < this.show.size(); ++i) {
			if(doubleQuick.contains(show.get(i))){
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
	@Override
	public int compareTo(Object o) {
	return (this.singleQuickChangeCount + this.doubleQuickChangeCount) - (((Schedule) o).getSingleQuick() + ((Schedule) o).doubleQuickChangeCount);
	}
	
	

	}

