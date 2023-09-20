package AutoNavigation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IDSearch {
    private final int[][] grid;
    private int statesVisited = 0;
    private int maxStatesInMemory = 0;

    public IDSearch(int[][] grid) {
        this.grid = grid;
    }

    public SearchNode depthLimitedSearch(SearchNode start, SearchNode goal, int depthLimit) {
        List<SearchNode> frontier = new ArrayList<>();
        Set<SearchNode> explored = new HashSet<>();

        frontier.add(start);

        while (!frontier.isEmpty()) {
            SearchNode currentNode = frontier.remove(frontier.size() - 1);

            // Increment states visited
            statesVisited++;

            if (currentNode.equals(goal)) {
                return currentNode; // Goal found
            }

            if (currentNode.getDepth() < depthLimit) {
                List<SearchNode> neighbors = currentNode.expand(grid);

                for (SearchNode neighbor : neighbors) {
                    if (!isNodeInExploredWithLowerDepth(neighbor, explored) && !frontier.contains(neighbor)) {
                        frontier.add(neighbor);
                    }
                }
            }

            explored.add(currentNode);

            // Check max states in memory
            int currentStatesInMemory = frontier.size() + explored.size();
            if (currentStatesInMemory > maxStatesInMemory) {
                maxStatesInMemory = currentStatesInMemory;
            }
        }

        return null; // Goal not found within depthLimit
    }

    private boolean isNodeInExploredWithLowerDepth(SearchNode node, Set<SearchNode> explored) {
        for (SearchNode exploredNode : explored) {
            if (exploredNode.getX() == node.getX() &&
                    exploredNode.getY() == node.getY() &&
                    exploredNode.getDepth() <= node.getDepth()) {
                return true;
            }
        }
        return false;
    }

    public SearchNode iterativeDeepening(SearchNode start, SearchNode goal) {
        for (int depth = 0;; depth++) {
            SearchNode result = depthLimitedSearch(start, goal, depth);
            if (result != null) {
                return result;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting the program...");
        List<int[][]> grids = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("./grids.txt"))) {
            String line;
            int[][] currentGrid = new int[15][15];
            int rowIndex = 0;

            while ((line = reader.readLine()) != null) {
                System.out.println("Read line: " + line);
                line = line.trim();

                if (line.contains("int[][]")) {
                    if (rowIndex == 15) {
                        grids.add(currentGrid);
                        System.out.println("Added a grid to the list. Total grids: " + grids.size());
                        currentGrid = new int[15][15];
                    }
                    System.out.println("Starting a new grid...");
                    rowIndex = 0;
                    continue;
                }

                // if the line starts with a { and ends with a , or }
                if (line.startsWith("{") && (line.endsWith(",") || line.endsWith("}"))) {
                    // split the line into an array of strings, splitting on commas
                    String[] rowStrings = line.substring(1, line.length() - 2).split(",");

                    // create an empty array of ints, the same length as the rowStrings array
                    int[] row = new int[rowStrings.length];

                    // for each string in the rowStrings array
                    for (int i = 0; i < rowStrings.length; i++) {
                        // trim the string and parse it to an int
                        row[i] = Integer.parseInt(rowStrings[i].trim());
                    }

                    // add the row to the currentGrid
                    currentGrid[rowIndex] = row;

                    // increment the rowIndex
                    rowIndex++;
                }
            }
            // Add the last grid to the list after the loop
            if (rowIndex == 15) {
                grids.add(currentGrid);
                System.out.println("Added a grid to the list. Total grids: " + grids.size());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int[][] grid : grids) {
            System.out.println("Processing Grid with dimensions: " + grid.length + "x" + grid[0].length);
            for (int[] row : grid) {
                System.out.println(Arrays.toString(row));
            }

            IDSearch search = new IDSearch(grid);

            // Identify the start and goal nodes
            SearchNode start = null, goal = null;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] == 2) {
                        start = new SearchNode(i, j, 0);
                    } else if (grid[i][j] == 3) {
                        goal = new SearchNode(i, j, 0);
                    }
                }
            }

            if (start == null || goal == null) {
                System.out.println("Start or Goal node not found in the grid. Please check the grid.");
                continue; // Continue to process the next grid
            }

            SearchNode result = search.iterativeDeepening(start, goal);

            if (result != null) {
                System.out.println("Found goal at: " + result);

                // Backtrack to reconstruct the path
                List<SearchNode> path = new ArrayList<>();
                SearchNode current = result;
                while (current != null) {
                    path.add(current);
                    current = current.getParent();
                }
                Collections.reverse(path);
                System.out.println("Path taken: " + path);
            } else {
                System.out.println("Goal not found.");
            }

            System.out.println("States Visited: " + search.statesVisited);
            System.out.println("Max States in Memory: " + search.maxStatesInMemory);
            System.out.println("-------------------------------");
        }
    }
}
