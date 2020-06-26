package com.learnjava;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    // String[] Turn2DTo1D : turns 2D String arrays to 1D String array
    // @param String[][] cells : this 2D array will be turned into 1D array
    public static String[] Turn2DTo1D(String[][] cells) {
        // Initializes an 1D array with [number of elements in first row * number of rows] number of elements
        String[] oneDArray = new String[cells[0].length * cells.length];

        // Turns 2D arrays to 1D array
        for (int row = 0; row < cells.length; row++) {
            for(int col = 0; col < cells[0].length; col++) {
                oneDArray[row * cells[0].length + col] = cells[row][col];
            }
        }

        return oneDArray;
    }


    // boolean PlayerWinsTheGame : returns true when someone has won the game, returns false when no one has won the game yet
    // @param String[][] cells : boardGame cells, which will be used to check if any Connect Four is completed
    // @param int[] cellCoordinate : takes in a coordinate of a cell that is last filled; this will be used as a origin point to check for any completed path
    public static boolean PlayerWinsTheGame(String[][] cells, int[] cellCoordinate) {
        int row = cellCoordinate[0];
        int col = cellCoordinate[1];

        int numRow = cells.length;
        int numCol = cells[0].length;

        int midColNum = numCol/2;
        int midRowNum = numRow/2;

        // Checks columns
        // First checks at least four cells of that column is filled because otherwise there is no way to complete a path
        if (numRow - 3 >= row + 1) {
            // Checks if four items on that column are the same item
            if (cells[row][col].equals(cells[row + 1][col]) && cells[row][col].equals(cells[row + 2][col]) && cells[row][col].equals(cells[row + 3][col])) {
                return true;
            }
        }


        // Checks rows; from left side to right side
        for (int i = 0; i <= midColNum; i++) {
            if (cells[row][i].equals(cells[row][i + 1]) && cells[row][i].equals(cells[row][i + 2]) && cells[row][i].equals(cells[row][i + 3]) && !cells[row][i].equals(" ")) {
                return true;
            }
        }


        // Checks diagonals
        // Checks top-left-to-bottom-right diagonal line
        for (int h = 0; h <= midColNum; h++){ // h stands for horizontal shift
            for (int v = 0; v < midRowNum; v++) { // v stands for vertical shift
                // Checks diagonal lines from top-left of the gameBoard to bottom-right of the gameBoard
                if (cells[v][h].equals(cells[v + 1][h + 1]) && cells[v][h].equals(cells[v + 2][h + 2]) && cells[v][h].equals(cells[v + 3][h + 3]) && !cells[v][h].equals(" ")) {
                    return true;
                }
            }
        }

        // Checks top-right-to-bottom-left diagonal line
        for (int h = numCol - 1; h >= midColNum; h--) { // h stands for horizontal shift
            for (int v = 0; v < midRowNum; v++) { // v stands for vertical shift
                // Checks diagonal lines from top-right of the gameBoard to the bottom-left of the gameBoard
                if (cells[v][h].equals(cells[v + 1][h - 1]) && cells[v][h].equals(cells[v + 2][h - 2]) && cells[v][h].equals(cells[v + 3][h - 3]) && !cells[v][h].equals(" ")) {
                    return true;
                }
            }
        }


        // Returns false by default
        return false;
    }


    // String RenderGameBoard : returns formatted game board in a String form
    // @param String[][] cells : takes in 1D array for formatting of the game board
    public static String RenderGameBoard(String[][] cells){
        // Initializes gameBoard String with all String placeholders
        String gameBoard = "-1-2-3-4-5-6-7-\n---------------\n" +
                "|%s|%s|%s|%s|%s|%s|%s|\n---------------\n" +
                "|%s|%s|%s|%s|%s|%s|%s|\n---------------\n" +
                "|%s|%s|%s|%s|%s|%s|%s|\n---------------\n" +
                "|%s|%s|%s|%s|%s|%s|%s|\n---------------\n" +
                "|%s|%s|%s|%s|%s|%s|%s|\n---------------\n" +
                "|%s|%s|%s|%s|%s|%s|%s|\n---------------\n";

        String[] oneDCells = Turn2DTo1D(cells);

        String formattedGameBoard = String.format(gameBoard, oneDCells);

        return formattedGameBoard;
    }


    public static void main(String[] args) {
        // Game introduction and rule explanation
        System.out.println("=== Connect Four ===");
        System.out.println("Play Connect Four with your friend (two players).");
        System.out.println("To insert a coin into a column you want, type the column's corresponding number in the console.");


        // ===== Game Set Ups =====
        int NUM_ROWS = 6;
        int NUM_COLS = 7;

        // String 2D arrays [row][column]
        String[][] cells = new String[NUM_ROWS][NUM_COLS];

        // Fills in cells with " " for initialization
        for (String[] row: cells)
            Arrays.fill(row, " ");

        // Scanner Object for taking in inputs from console
        Scanner scanner = new Scanner(System.in);

        // Initializes the gameBoard for Connect Four
        String gameBoard = RenderGameBoard(cells);

        // This switches between ○ and ●
        String cellFiller = "";

        // Coordinate of a cell that is last filled [row, column]
        int[] lastCellCoordinate = new int[2];

        // Current number of turns/moves
        int numOfTurns = 1;


        // ===== Game Mechanics =====

        // Game loop
        while(true) {

            System.out.println(gameBoard);

            // Switches cellFiller as the game proceeds ==> as numOfTurns increases
            if (numOfTurns%2 == 0) // Every even turn ● player moves
                cellFiller = "●"; // This circle will be referred to as "filled circle"
            else // Every odd turn ○ player moves
                cellFiller = "○"; // This circle will be referred to as "empty circle"


            // Error handling
            while(true) {

                // Asks for player cellFiller to type column's number
                System.out.print("Player " + cellFiller + ": ");
                int userColumnChoice = scanner.nextInt();

                // Checks if userColumnChoice above 0 and below or equal to NUM_COLS
                // Also checks if the top row of that column is empty to show that that chosen column is not filled up
                if (userColumnChoice >= 1 && userColumnChoice <= NUM_COLS && cells[0][userColumnChoice - 1].equals(" ")) {

                    // The row's number that is being checked, its initial value corresponds to the bottom row
                    int currentlyCheckRow = NUM_ROWS - 1;

                    // Checks the user chosen column of the game board from bottom row to the top row
                    while(true) {
                        // As soon as there is an empty cell, that cell will be filled with current cellFiller
                        if (cells[currentlyCheckRow][userColumnChoice - 1].equals(" ")){
                            cells[currentlyCheckRow][userColumnChoice - 1] = cellFiller;

                            // Updates lastCellCoordinate
                            lastCellCoordinate[0] = currentlyCheckRow;
                            lastCellCoordinate[1] = userColumnChoice - 1;

                            break;
                        }
                        // If currentlyCheckRow is filled up, it will checks the row one above it
                        currentlyCheckRow--;
                    }

                    // Updates the gameBoard with new cells
                    gameBoard = RenderGameBoard(cells);
                    break;
                }

                // Tells the player to type the column's number again
                System.out.println("Chosen column is full or type valid column's number [1-" + NUM_COLS + "]: ");

            }


            // Checks if cellFiller player has won the game
            if (PlayerWinsTheGame(cells, lastCellCoordinate)) {
                System.out.println(gameBoard);
                System.out.println("Congratulations! Player " + cellFiller + " Wins!");
                System.out.println("Total number of turns: " + numOfTurns);
                break;
            } else if (numOfTurns == (cells[0].length * cells.length)) { // when all cells are filled up without anyone completing Connect Four, game is tied
                System.out.println(gameBoard);
                System.out.println("=== The Game Tied! ===");
                break;
            }

            numOfTurns++;
        }

    }
}
