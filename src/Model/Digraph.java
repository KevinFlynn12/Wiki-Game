package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

/**
 * Digraph class t hold all articles and their links
 * 
 * @author Tristen Rivera & Kevin Flynn
 * @version Fall 2019
 *
 * @param <T> The link to compare o when sorting
 */
public class Digraph<T extends Comparable<T>> {

	private Map<T, List<T>> edges;

	/**
	 * Creates a new empty Digraph<T> object must add nodes and edges using the
	 * interface
	 * 
	 * @preconditions none
	 * @postconditions order() == 0
	 */
	public Digraph() {
		this.edges = new HashMap<T, List<T>>();
	}

	/**
	 * Return the set of nodes
	 * 
	 * @return this.edges.KeySet()
	 */
	public Set<T> nodeSet() {
		return this.edges.keySet();
	}

	/**
	 * Adds a new node to the Digraph Throws an exception if the node is already in
	 * the digraph
	 * 
	 * @param node The node to add
	 * @preconditions node != null
	 * @postconditions order() = order()@prev + 1
	 */
	public void addNode(T node) {
		if (edges.containsKey(node)) {
			throw new IllegalArgumentException("Can not add duplicate node to digraph");
		}
		edges.put(node, new ArrayList<T>());
	}

	/**
	 * Returns true if the node is in the digraph and false otherwise
	 * 
	 * @param node The node to check if this.eges contains
	 * @preconditions node != null
	 * @postconditions none
	 * @return this.edges.keySet().contains(node)
	 */
	public boolean containsNode(T node) {
		return this.edges.keySet().contains(node);
	}

	/**
	 * Adds an edge to the digraph If either nodes are not already in the digraph
	 * they are added
	 * 
	 * @param node0 The first node
	 * @param node1 The second node
	 */
	public void addEdge(T node0, T node1) {
		if (!containsNode(node0)) {
			addNode(node0);
		}
		if (!containsNode(node1)) {
			addNode(node1);
		}
		this.edges.get(node0).add(node1);
	}

	/**
	 * Returns the list of nodes adjacent to the input node
	 * 
	 * @param node The node to get the neighbors of
	 * @return the list of neighbors of the input node
	 */
	public List<T> getNeighbors(T node) {
		if (!containsNode(node)) {
			throw new IllegalArgumentException("Node must be in digraph to provide neighbors");
		}
		Collections.sort(edges.get(node));
		return edges.get(node);
	}

	/**
	 * Returns the BFT as a list starting at the given node
	 * 
	 * @param node The start node
	 * @return An ordered map by breath first search
	 */
	public Map<T, T> breadthFirstSearch(T start) {

		Queue<T> queue = new LinkedList<T>();
		Map<T, T> previous = new HashMap<T, T>();
		Set<T> visited = new TreeSet<T>();

		if (!this.containsNode(start)) {
			throw new IllegalArgumentException("Node is not in the digraph.");
		}

		visited.add(start);
		queue.add(start);
		while (!queue.isEmpty()) {
			T node = queue.remove();
			for (T neighbor : this.getNeighbors(node)) {
				if (neighbor.equals(Main.Main.target)) {
					previous.put(neighbor, node);
					return previous;
				}
				if (!visited.contains(neighbor)) {
					visited.add(neighbor);
					queue.add(neighbor);
					previous.put(neighbor, node);
				}
			}
		}
		return previous;
	}

}
