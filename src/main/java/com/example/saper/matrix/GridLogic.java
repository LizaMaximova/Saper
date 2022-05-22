package com.example.saper.matrix;

import java.util.Random;

public class GridLogic {

    private int[][] grid = new int[12][12];

    public GridLogic() {

        fillGrid();
    }

    private void fillGrid() {

        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                Random random = new Random();
                if (random.nextInt(1000) % 5 == 0) {
                    grid[i][j] = 9;
                } else grid[i][j] = 0;
            }
        }

        for (int i = 1; i <= 10; i++)
            for (int j = 1; j <= 10; j++)
            {
                int n = 0;
                if (grid[i][j] == 9) continue;
                if (grid[i + 1][j] == 9) n++;
                if (grid[i][j + 1] == 9) n++;
                if (grid[i - 1][j] == 9) n++;
                if (grid[i][j - 1] == 9) n++;
                if (grid[i + 1][j + 1] == 9) n++;
                if (grid[i - 1][j - 1] == 9) n++;
                if (grid[i - 1][j + 1] == 9) n++;
                if (grid[i + 1][j - 1] == 9) n++;
                grid[i][j] = n;
            }
    }

    public int[][] getGrid() {
        return grid;
    }
}
