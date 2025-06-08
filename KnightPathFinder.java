import java.util.*;

class KnightPathFinder {

    // Possible knight moves
    private static final int[][] directions = {
        {-2, -1}, {-1, -2}, {1, -2}, {2, -1},
        {2, 1}, {1, 2}, {-1, 2}, {-2, 1}
    };

    public int shortestPathWithOneTeleport(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        // visited[x][y][0] = visited without teleport
        // visited[x][y][1] = visited after teleport used
        boolean[][][] visited = new boolean[n][m][2];

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 0, 0, 0}); // {x, y, steps, usedTeleport}
        visited[0][0][0] = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int x = curr[0], y = curr[1], steps = curr[2], usedTeleport = curr[3];

            if (x == n - 1 && y == m - 1) {
                return steps;
            }

            for (int[] dir : directions) {
                int nx = x + dir[0];
                int ny = y + dir[1];

                if (isValid(nx, ny, n, m)) {
                    if (grid[nx][ny] == '0' && !visited[nx][ny][usedTeleport]) {
                        visited[nx][ny][usedTeleport] = true;
                        queue.add(new int[]{nx, ny, steps + 1, usedTeleport});
                    } else if (grid[nx][ny] == '1' && usedTeleport == 0 && !visited[nx][ny][1]) {
                        visited[nx][ny][1] = true;
                        queue.add(new int[]{nx, ny, steps + 1, 1});
                    }
                }
            }
        }

        return -1;
    }

    private boolean isValid(int x, int y, int n, int m) {
        return x >= 0 && x < n && y >= 0 && y < m;
    }

    public static void main(String[] args) {
        KnightPathFinder finder = new KnightPathFinder();

        // Test Case 1: Simple path without teleport
        char[][] grid1 = {
            {'0', '0', '0'},
            {'0', '1', '0'},
            {'0', '0', '0'}
        };
        System.out.println("Test Case 1 Expected: 4, Actual: " + finder.shortestPathWithOneTeleport(grid1));

        // Test Case 2: No path without teleport, teleport helps
        char[][] grid2 = {
            {'0', '1', '1'},
            {'1', '1', '1'},
            {'0', '0', '0'}
        };
        System.out.println("Test Case 2 Expected: 5, Actual: " + finder.shortestPathWithOneTeleport(grid2));

        // Test Case 3: No possible path even with teleport
        char[][] grid3 = {
            {'0', '1', '1'},
            {'1', '1', '1'},
            {'1', '1', '0'}
        };
        System.out.println("Test Case 3 Expected: -1, Actual: " + finder.shortestPathWithOneTeleport(grid3));
    }
}
