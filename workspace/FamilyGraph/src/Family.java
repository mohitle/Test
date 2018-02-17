
/**
 * 
 * @author Maria Grazia
 * @version 02/12/2017
 * 
 * Class to build families with constructor, setters, getters and other operations.
 * The class allows to apply recursion to enhance efficiency.
 * 
 */

public class Family {

	private boolean empty;
	private String member;
	private Family rest;

	/**
	 * Constructor
	 * @param member of the family
	 * @param rest of the members of family
	 */
	public Family(String member, Family rest) {
		this.rest = rest;
		this.member = member;
		this.empty = false;
	}

	/**
	 * Constructor for empty family
	 */
	public Family() {
		empty = true;
	}

	/**
	 * Checking if family is empty
	 * @return true if the family is empty
	 */
	public boolean isEmpty() {
		return empty;
	}

	/**
	 * Getter for field member
	 * @return the member
	 */
	public String getMember() {
		return member;
	}

	/**
	 * Setter for field member
	 * @param member
	 */
	public void setMember(String member) {
		this.member = member;
	}

	/**
	 * Getter for rest of family field
	 * @return a family that refers to a member
	 */
	public Family getRest() {
		return rest;
	}

	/**
	 * Setter for field rest
	 * @param rest
	 */
	public void setRest(Family rest) {
		this.rest = rest;
	}

	/**
	 * Setter for empty field
	 * @param empty
	 */
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	/**
	 * Given a family, gives the number of members
	 * @param f the family to count the members
	 * @return number of members
	 */
	public int familySize(Family f) {
		int i = 0;
		if (f.isEmpty()) {
			return i;
		} else {
			i++;
			return i + familySize(f.getRest());
		}
	}

	/**
	 * Checking if a family contains a certain person
	 * @param f the family to check
	 * @param person to find
	 * @return true if the person is member of the family
	 */
	public boolean familyContains(Family f, String person) {
		if (f.isEmpty()) {
			throw new IllegalStateException();
		} else {
			if (!f.getMember().equals(person) && !f.getRest().isEmpty()) {
				return familyContains(f.getRest(), person);
			} else if (f.getMember().equals(person)) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * Gives a String representation of a family
	 * @param f the family to convert into String
	 * @return the String displaying the family
	 */
	public String toString(Family f) {
		if (f.getRest().isEmpty()) {
			return f.getMember() + ".";
		} else {
			return f.getMember() + ", " + toString(f.getRest());
		}
	}

	public static void main(String args[]) {
		Family f = new Family("Bob", new Family("Ann", new Family("Amber", new Family())));
		System.out.println(f.familySize(f));
		System.out.println(f.toString(f));
			try {
				System.out.println(f.familyContains(new Family(), "Ambera"));
			} catch (Exception e) {
				System.out.println("Empty family!");
//				e.printStackTrace();
			}
		
	}

}


