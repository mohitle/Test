import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Maria Grazia
 * @version 02/12/2017
 * 
 * Class creating the set of people in the file to read from.
 * It stores in a map the email and the name of each person.
 * 
 */
public class People {

	private Map<String, String> listOfPeople;

	/**
	 * Constructor reading from file and storing email as kay and name as value of the map
	 * @param path the file to read from
	 */
	public People(String path) {
		listOfPeople = new HashMap<String, String>();
		String line;
		try {
			FileReader listFile = new FileReader(path);
			BufferedReader br = new BufferedReader(listFile);
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				listOfPeople.put(parts[1], parts[0]);
			}
			br.close();
		} catch (IOException e) {
			System.out.println("File not found.");
		}
	}

	/**
	 * Getter of field listOfPeople
	 * @return the map containing the people
	 */
	public Map<String, String> getListOfPeople() {
		return listOfPeople;
	}

	/**
	 * Setters of listOfPeople
	 * @param listOfPeople
	 */
	public void setListOfPeople(Map<String, String> listOfPeople) {
		this.listOfPeople = listOfPeople;
	}

	public static void main(String[] args) {
		People p = new People("src/people.csv");
		for (String email : p.listOfPeople.keySet()) {
			System.out.println(p.listOfPeople.get(email) + " " + email);
		}
	}
}
