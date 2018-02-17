import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * 
 * @author Maria Grazia
 * @version 02/12/2017
 * 
 * Class building a collection of families, by reading from file containing email of people related to each other by relationship of family or friendship.
 * It allows to store the people according to their names. To do this, it uses a People object to get name from email.
 * Information is provided about: 1- number of families, 2- members and size of each family, 3- number of total relationships for each person (both family and friends).
 * 
 */

public class Relationships {

	private ArrayList<Family> families = new ArrayList<Family>();
	private HashSet<String> supportList = new HashSet<String>();
	private HashMap<String, Integer> peoplesRelationships = new HashMap<String, Integer>();

	/**
	 * Constructor of collection Relationships. Reads from file, stores number of total relationships and creates families
	 * @param path of the file to read from to get the information
	 * @param people gives the pairs name-email for each person (to better use of file with relationships, containing only emails)
	 */
	public Relationships(String path, People people) {		
		for (String s : people.getListOfPeople().keySet()) {
			peoplesRelationships.put(people.getListOfPeople().get(s), 0);
		}
		String line;
		String[] parts;
		String firstPerson;
		String secondPerson;
		try {
			/* BufferedReader to read from file line by line */
			FileReader relationshipsFile = new FileReader(path);
			BufferedReader br = new BufferedReader(relationshipsFile);
			while ((line = br.readLine()) != null) {
				/* Discard blank lines */
				if (!line.equals("")) {
					parts = line.split(",");
					/* Each line contains two people, whose relationship (of both friendship or family) is recorded in the map peoplesRelationships */
					firstPerson = people.getListOfPeople().get(parts[0]);
					secondPerson = people.getListOfPeople().get(parts[2]);
					peoplesRelationships.put(firstPerson, peoplesRelationships.get(firstPerson) + 1);
					peoplesRelationships.put(secondPerson, peoplesRelationships.get(secondPerson) + 1);

					/* Recording family relationships and building families */
					if (parts[1].equals("FAMILY")) {
						/* Both people in the line have already been stored in a family */
						if (supportList.contains(firstPerson) && supportList.contains(secondPerson)) {

						} 
						/* Only one person in the line has been stored in a family - store the other */
						else if (supportList.contains(firstPerson) || supportList.contains(secondPerson)) {
							for (Family f : families) {
								if (f.familyContains(f, firstPerson)) {
									f.setRest(new Family(secondPerson, f.getRest()));
									supportList.add(secondPerson);
									break;
								} else if (f.familyContains(f, secondPerson)) {
									f.setRest(new Family(firstPerson, f.getRest()));
									supportList.add(firstPerson);
									break;
								}
							}
						/* Both people are not in a family - a family is created which stores both together */
						} else {
							Family newFamily = new Family(firstPerson, new Family(secondPerson, new Family()));
							families.add(newFamily);
							supportList.add(firstPerson);
							supportList.add(secondPerson);
						}
					}
				}
			}
			br.close();
		} catch (IOException e) {
			System.out.println("File not found.");
		}
	}
	
	/**
	 * Getter for peoplesRelationships map
	 * @return the map 
	 */
	public HashMap<String, Integer> getPeoplesRelationships() {
		return peoplesRelationships;
	}

	/**
	 * Setter for peoplesRelationships
	 * @param peoplesRelationships
	 */
	public void setPeoplesRelationships(HashMap<String, Integer> peoplesRelationships) {
		this.peoplesRelationships = peoplesRelationships;
	}
	
	/**
	 * Calculate the number of members in a family containing a given person
	 * @param member whose family needs to be counted 
	 * @return the number of members
	 */
	public int familySize(String member) {
		int size = 0;
		for (Family f : families) {
			if (f.familyContains(f, member)) {
				size = f.familySize(f);
				break;
			}
		}
		return size;
	}

	/**
	 * Printing friendly output of all families
	 * @param l list of the families
	 */
	public void print(List<Family> l) {
		int i = 0;
		for (Family f : l) {
			i++;
			System.out.println("Family n. " + i + ": " + f.toString(f) + " Members: " + f.familySize(f) + ".");
		}
	}
	
	public static void main(String[] args) {
		People p = new People("src/people.csv");
		Relationships r = new Relationships("src/relationships.csv", p);
		
		r.print(r.families);
		String member = "Amber";
		System.out.println(member + "'s family has " + r.familySize(member) + " members.");
		
		for (String s : r.peoplesRelationships.keySet()) {
			System.out.println(s + ": " + r.peoplesRelationships.get(s) + " relationships");
		}
	}
}
