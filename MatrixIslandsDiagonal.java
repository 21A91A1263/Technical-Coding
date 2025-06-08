import java.util.*;

public class MatrixIslandsDiagonal {
    public int countIslands(char[][] grid) {
        int totalIslands = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] seen = new boolean[rows][cols];

        // 8 directions: up, down, left, right, and 4 diagonals
        int[][] directions = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1},  // up, down, left, right
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1} // diagonals
        };

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1' && !seen[i][j]) {
                    totalIslands++;
                    markIsland(grid, i, j, seen, directions);
                }
            }
        }

        return totalIslands;
    }

    private void markIsland(char[][] grid, int x, int y, boolean[][] seen, int[][] directions) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x, y});
        seen[x][y] = true;

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            for (int[] dir : directions) {
                int newX = cell[0] + dir[0];
                int newY = cell[1] + dir[1];

                if (isSafe(grid, newX, newY, seen)) {
                    seen[newX][newY] = true;
                    queue.offer(new int[]{newX, newY});
                }
            }
        }
    }

    private boolean isSafe(char[][] grid, int x, int y, boolean[][] seen) {
        return x >= 0 && y >= 0 && x < grid.length && y < grid[0].length &&
               grid[x][y] == '1' && !seen[x][y];
    }

    // Test cases
    public static void main(String[] args) {
        MatrixIslandsDiagonal obj = new MatrixIslandsDiagonal();

        char[][] grid1 = {
            {'1', '1', '0'},
            {'0', '1', '0'},
            {'0', '0', '1'}
        };
        System.out.println("Islands: " + obj.countIslands(grid1)); // Output: 2

        char[][] grid2 = {
            {'1', '0', '1'},
            {'0', '1', '0'},
            {'1', '0', '1'}
        };
        System.out.println("Islands: " + obj.countIslands(grid2)); // Output: 1

        char[][] grid3 = {
            {'0', '0', '0'},
            {'0', '0', '0'},
            {'0', '0', '0'}
        };
        System.out.println("Islands: " + obj.countIslands(grid3)); // Output: 0
    }
}
