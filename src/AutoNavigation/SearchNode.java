package AutoNavigation;

import java.util.ArrayList;
import java.util.List;

public class SearchNode {
    int x;
    int y;
    int depth;
    private SearchNode parent;

    public int getDepth() {
        return depth;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public SearchNode(int x, int y, int depth) {
        this(x, y, depth, null);
    }

    public SearchNode(int x, int y, int depth, SearchNode parent) {
        this.x = x;
        this.y = y;
        this.depth = depth;
        this.parent = parent;
    }

    public SearchNode getParent() { // Add this method
        return parent;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        SearchNode node = (SearchNode) obj;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y; // Simple hash combining x and y
    }

    public List<SearchNode> expand(int[][] grid) {
        List<SearchNode> neighbors = new ArrayList<>();

        int[] dx = { -1, 1, 0, 0 }; // Movement in x-direction (up, down, right, left)
        int[] dy = { 0, 0, 1, -1 }; // Movement in y-direction (up, down, right, left)

        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            // Check boundaries and avoid traffic jams
            if (newX >= 0 && newX < grid.length && newY >= 0 && newY < grid[0].length && grid[newX][newY] != 1) {
                neighbors.add(new SearchNode(newX, newY, depth + 1, this));
            }
        }

        return neighbors;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + "," + depth + ")";
    }
}
