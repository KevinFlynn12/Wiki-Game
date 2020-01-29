package Model;

/**
 * Top100Links class to keep track of id and link count of a link
 * 
 * @author Tristen Rivera & Kevin Flynn
 * @version Fall 2019
 */
public class Top100Links implements Comparable<Top100Links> {
	private int id;
	private int count;

	/**
	 * Creates a Top100 class
	 * 
	 * @param id    The id of the node
	 * @param count The amount of links for the class
	 */
	public Top100Links(int id, int count) {

		this.count = count;
		this.id = id;
	}

	/**
	 * Gets the id
	 * 
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Gets the count
	 * 
	 * @return the count
	 */
	public int getCount() {
		return this.count;
	}

	/**
	 * Compares a Top100Link to another
	 * 
	 * @return 0 if they are equal, negative if the first is greater, and positive
	 *         if the second is bigger
	 */
	@Override
	public int compareTo(Top100Links other) {
		return other.count - this.count;
	}

}
