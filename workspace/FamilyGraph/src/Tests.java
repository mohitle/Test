import static org.junit.Assert.*;
import java.util.HashMap;
import org.junit.Test;

/**
 * 
 * @author Maria Grazia
 * @version 02/12/2017
 * Class to test some of the methods in the classes People, Relastionships and Family.
 */

public class Tests {

	People peopleActual = new People("src/people.csv");
	Relationships relationshipsActual = new Relationships("src/relationships.csv", peopleActual);
	
	/* Test people to check if loaded the expected number of people */
	@Test
	public void test1() {
		/* List of people, field of People class (storing all people from people file) */
		assertEquals(12, peopleActual.getListOfPeople().size());
		/* Checking for equality of People.listOfPeople and list with total relationships from class Relationships */
		assertEquals(peopleActual.getListOfPeople().size(), relationshipsActual.getPeoplesRelationships().size());
	}
	
	/* Checking each person has correct expected number of connections to other people. */
	@Test
	public void test2() {
		
		HashMap<String, Integer> expected = new HashMap<String, Integer>();		
		expected.put("Joe", 2);
		expected.put("Finn", 3);
		expected.put("Pete", 4);
		expected.put("Bob", 4);
		expected.put("Amber", 3);
		expected.put("Alan", 0);
		expected.put("Derek", 3);
		expected.put("Dave", 1);
		expected.put("Jenny", 3);
		expected.put("Kerry", 3);
		expected.put("Nigel", 2);
		expected.put("Anna", 4);

		relationshipsActual.getPeoplesRelationships();
		
		for (String name : expected.keySet()) {
		assertEquals(expected.get(name), relationshipsActual.getPeoplesRelationships().get(name));
		}	
	}
	
	/* Test for method Family.familySize() returning size of family (helper method of Relationships.familySize()). */
	@Test
	public void test3() {
		
		Family actual = new Family("Bob", new Family("Ann", new Family("Amber", new Family()))); 
		Family actual2 = new Family("Bob", new Family());
		Family actual3 = new Family();
		assertEquals(3, actual.familySize(actual));
		assertEquals(1, actual2.familySize(actual2));
		assertEquals(0, actual3.familySize(actual3));
	}
	
	/* Test for method Relationships.familySize(). */
	@Test
	public void test4() {
		
		assertEquals(2, relationshipsActual.familySize("Joe"));
		assertEquals(4, relationshipsActual.familySize("Pete"));
		assertEquals(4, relationshipsActual.familySize("Anna"));
		assertEquals(2, relationshipsActual.familySize("Nigel"));
		assertEquals(0, relationshipsActual.familySize("Alan"));
		assertEquals(0, relationshipsActual.familySize("Derek"));
	}
	
}
