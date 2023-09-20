package Vacuum;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashSet;
import java.util.Set;

public class BFSVacuumWorld {

    private static final int[][] TRANSITIONS = {
        {1, 2, 3},  // State 1 transitions for Left, Right, Suck
        {1, 2, 6},  // State 2
        {3, 4, 3},  // State 3
        {3, 4, 8},  // State 4
        {5, 6, 7},  // State 5
        {5, 6, 6},  // State 6
        {7, 8, 7},  // State 7
        {7, 8, 8}   // State 8
    };

    public static void main(String[] args) {
        int solution = bfs(1);
        if (solution != -1) {
            System.out.println("Goal state reached: " + solution);
        } else {
            System.out.println("No solution found.");
        }
    }

    public static int bfs(int initialState) {
        Queue<Integer> frontier = new LinkedList<>();
        Set<Integer> explored = new HashSet<>();

        frontier.add(initialState);

        while (!frontier.isEmpty()) {
            int currentState = frontier.poll();
            System.out.println("Exploring state: " + currentState);

            if (currentState == 7 || currentState == 8) {
                return currentState;  // Goal state reached
            }

            explored.add(currentState);

            // Expand the current state
            for (int nextState : TRANSITIONS[currentState - 1]) {
                if (!explored.contains(nextState) && !frontier.contains(nextState)) {
                    frontier.add(nextState);
                    System.out.println("  Adding to frontier: " + nextState);
                }
            }
        }

        return -1;  // No solution found
    }
}

