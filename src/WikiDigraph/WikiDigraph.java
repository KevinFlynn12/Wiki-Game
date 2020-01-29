package WikiDigraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Model.Digraph;

/**
 * Class to add items to the map and digraph from the files and to determine the
 * shortest path
 * 
 * @author Tristen Rivera & Kevin Flynn
 * @version Fall 2019
 * 
 *
 */
public class WikiDigraph {
	private Map<Integer, String> wikiIds;
	private Digraph<Integer> digraph;

	/**
	 * Instantiates a new WikiDigraph
	 * 
	 * @precondition none
	 * @postcondition a new controller is created
	 */
	public WikiDigraph() {
		this.wikiIds = new HashMap<Integer, String>();
		this.digraph = new Digraph<Integer>();
	}

	/**
	 * gets the maps of IDs
	 * 
	 * @precondition none
	 * @return the map of ids
	 */
	public Map<Integer, String> getWikiIds() {
		return this.wikiIds;
	}

	/**
	 * gets the graph
	 * 
	 * @precondition none
	 * @return the graph
	 */
	public Digraph<Integer> getGraph() {
		return this.digraph;
	}

	/**
	 * Gets the wiki Ids for the Map
	 * 
	 */
	public void generateIds() {
		String filename = "src/WikiIDs.txt";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] lines = line.split(":");
				this.wikiIds.put(Integer.parseInt(lines[0]), lines[1]);
			}

		} catch (IOException error) {
			error.printStackTrace();
		}
	}

	/**
	 * Generates the digraph from the wiki links
	 * 
	 */
	public void generateLinks() {
		String filename = "src/WikiLinks.txt";
		BufferedReader reader = null;
		try (Scanner scan = new Scanner(filename)) {
			String line;
			reader = new BufferedReader(new FileReader(filename));
			while ((line = reader.readLine()) != null) {
				String[] lines = line.split(" ");
				int link = Integer.parseInt(lines[0]);
				for (int i = 1; i < lines.length; i++) {

					this.digraph.addEdge(link, Integer.parseInt(lines[i]));
				}

			}
		} catch (IOException error) {
			error.printStackTrace();
		}

	}

	/**
	 * Gets the top 100 pages with the most neighbors
	 * 
	 * @return The pages with the most links
	 */
	public HashMap<Integer, Integer> getTop100() {

		HashMap<Integer, Integer> top100 = new HashMap<Integer, Integer>();

		for (Integer currentLink : this.digraph.nodeSet()) {

			for (Integer currentLinkNeighbors : this.digraph.getNeighbors(currentLink)) {

				int linkCount;
				try {
					linkCount = top100.get(currentLinkNeighbors);
				} catch (Exception error) {
					linkCount = 0;
				}
				linkCount++;

				top100.put(currentLinkNeighbors, linkCount);
			}
		}
		return top100;
	}

}
