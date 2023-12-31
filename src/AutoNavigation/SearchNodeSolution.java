package AutoNavigation;

import java.util.*;

//   This class implements a search node, and follows the description closely. 

public class SearchNodeSolution {
	// Node attributes -- x and y location (which together are the state),
	// and the depth in the search tree (which is needed for the ID search).
	int x;
	int y;
	int depth;

	// Simple constructor
	SearchNodeSolution(int _x, int _y, int _depth){
		x = _x;
		y = _y;
		depth = _depth;
	}

	public ArrayList<SearchNodeSolution> expand(int[][] grid) {
		ArrayList<SearchNodeSolution> neighbours = new ArrayList<SearchNodeSolution>();
		
		// Check boundaries of grid and whether intersection is blocked
		// before generating each of the four neighbours.
		if (x - 1 >= 0 && grid[x - 1][y] != 1) {
			neighbours.add(new SearchNodeSolution(x - 1, y, depth + 1));
		}
		if (y - 1 >= 0 && grid[x][y - 1] != 1) {
			neighbours.add(new SearchNodeSolution(x, y - 1, depth + 1));
		}
		if (x + 1 < grid.length && grid[x + 1][y] != 1) {
			neighbours.add(new SearchNodeSolution(x + 1, y, depth + 1));
		}
		if (y + 1 < grid[x].length && grid[x][y + 1] != 1) {
			neighbours.add(new SearchNodeSolution(x, y + 1, depth + 1));
		}
		return neighbours;
	}

	public String toString() {
		return "(" + x + "," + y + "," + depth + ")";
	}

	// Implement the equals method to compare the x and y values only (i.e
	// compare the state, not the entire node).
	@Override
	public boolean equals(Object A) {
		if (A instanceof SearchNodeSolution) {
			SearchNodeSolution sn = (SearchNodeSolution) A;
			if (x == sn.x && y == sn.y) {
				return true;
			}
		}

		return false;
	}
}
