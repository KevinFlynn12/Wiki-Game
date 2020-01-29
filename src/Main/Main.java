package Main;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import WikiDigraph.WikiDigraph;
import Model.Digraph;
import Model.Top100Links;

/**
 * Main class to run the program
 * 
 * @author Tristen Rivera & Kevin Flynn
 * @version Fall 2019
 *
 */
public class Main {

	public static int target = 595315;
	private static Digraph<Integer> digraph;
	private static Map<Integer, String> wikiIds;

	/**
	 * Runs the program
	 * 
	 * @param args The default arguments for the program
	 */
	public static void main(String[] args) {
		WikiDigraph digraph = new WikiDigraph();

		System.out.print("Generating IDs ~ ");
		long startTimeID = System.currentTimeMillis();
		digraph.generateIds();
		long endTimeID = System.currentTimeMillis();
		System.out.println((endTimeID - startTimeID) / 1000 + " seconds");
		System.out.print("Generating Links ~ ");
		long startTimeLink = System.currentTimeMillis();
		digraph.generateLinks();
		long endTimeLink = System.currentTimeMillis();
		System.out.println((endTimeLink - startTimeLink) / 1000 + " seconds");

		startGame(digraph);

	}

	private static void startGame(WikiDigraph digraph) {
		Scanner scan = new Scanner(System.in);
		String input = "";

		System.out.println(System.lineSeparator()
				+ "Welcome! If you would like to save the top 100 most linked-to articles please type \'save\' otherwise type \'game\' to begin");
		System.out.println("*Note If you save the file you can not play the game and likewise for playing the game");

		String response = scan.nextLine();
		if (response.equalsIgnoreCase("save")) {
			System.out.print("Generating Top 100 Links ~ ");
			long startTime100 = System.currentTimeMillis();
			writeTop100(digraph);
			long endTime100 = System.currentTimeMillis();
			System.out.println((endTime100 - startTime100) / 1000 + " seconds");

		} else if (response.equalsIgnoreCase("game")) {

			while (!input.equals("exit")) {
				System.out.println("Enter source link or type \'exit\' to end the program");
				input = scan.nextLine();
				try {

					int sourceID = 0;

					Main.wikiIds = digraph.getWikiIds();
					Main.digraph = digraph.getGraph();

					for (int current : Main.wikiIds.keySet()) {
						if (Main.wikiIds.get(current).equals(input)) {
							sourceID = current;
						}
					}
					ArrayList<String> path = getShortestPath(sourceID);
					System.out.println(System.lineSeparator() + "--------------------------------------------------------------------------------------------------");
					System.out.println("Source to Target path" + System.lineSeparator());
					for (int link = 0; link < path.size(); link++) {
						System.out.print(path.get(link));
						if (!(link == path.size() - 1)) {
							System.out.print(" -> ");
						}
					}

					System.out
							.println(System.lineSeparator() + System.lineSeparator() + "Links ~ " + (path.size() - 1));
					System.out.println("--------------------------------------------------------------------------------------------------" + System.lineSeparator());
				} catch (Exception e) {
					if (!input.equals("exit")) {
						System.out.println("Invalid link. Check the spelling or try another link.");
					}
				}
			}
		}
		scan.close();
	}

	private static ArrayList<String> getShortestPath(int sourceID) {

		Map<Integer, Integer> previous = Main.digraph.breadthFirstSearch(sourceID);
		ArrayList<String> sourceToTargetPath = new ArrayList<String>();
		Stack<Integer> articlesReversed = new Stack<Integer>();

		articlesReversed.push(Main.target);

		int currentLink = Integer.MIN_VALUE;
		int nextLink = previous.get(Main.target);

		while (nextLink != sourceID) {

			currentLink = nextLink;
			articlesReversed.push(nextLink);
			nextLink = previous.get(currentLink);

		}

		sourceToTargetPath.add(Main.wikiIds.get(sourceID));
		while (!articlesReversed.isEmpty()) {
			sourceToTargetPath.add(Main.wikiIds.get(articlesReversed.pop()));
		}

		return sourceToTargetPath;
	}

	private static void writeTop100(WikiDigraph digraph) {

		File file = new File("src/top100.txt");
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			ArrayList<Top100Links> sortedMap = new ArrayList<Top100Links>();
			StringBuilder builder = new StringBuilder();
			HashMap<Integer, Integer> map = digraph.getTop100();

			for (Integer current : map.keySet()) {
				Top100Links link = new Top100Links(current, map.get(current));
				sortedMap.add(link);
			}
			Collections.sort(sortedMap);

			for (int count = 0; count < 100; count++) {
				String link = digraph.getWikiIds().get(sortedMap.get(count).getId());
				builder.append((count + 1) + ". " + link + " ~ " + sortedMap.get(count).getCount() + " links"
						+ System.lineSeparator());
			}

			writer.write(builder.toString());
		} catch (Exception error) {
			error.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception error) {
				error.printStackTrace();
			}
		}

	}

}