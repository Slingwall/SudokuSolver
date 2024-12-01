public class Sudoku {

    public static boolean checked = false;

    // generate a random sudoku puzzle, then attempt to solve it by first checking for validity
    public static void main(String[] args) {
        char[][] puzzle = SudokuP.puzzle();

        System.out.println("Initial Puzzle: ");
        puzzleDisplayer(puzzle);

        if (solve(puzzle)) {
            System.out.println("Solved Puzzle: ");
            puzzleDisplayer(puzzle);
        } else {
            System.out.println("This puzzle is unsolvable");
        }
    }

    // validate if the puzzle we are given fits within the rules of sudoku
    public static boolean check(char[][] puzzle) {
        boolean rowsValid = true; 
        boolean colsValid = true;
        boolean totalTruth = true;

        // validate rows
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int twoAppearances = 0;
                for (int k = 0; k < 9; k++) {
                    if (puzzle[i][j] == '.') {
                        break;
                    } else {
                        if (puzzle[i][j] == puzzle[i][k]) {
                            twoAppearances++;
                            if (twoAppearances == 2) {
                                rowsValid = false;
                            }
                        }
                    }
                }
            }
        }

        // validate cols
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int twoAppearances = 0;
                for (int k = 0; k < 9; k++) {
                    if (puzzle[j][i] == '.') {
                        break;
                    } else {
                        if (puzzle[j][i] == puzzle[k][i]) {
                            twoAppearances++;
                            if (twoAppearances == 2) {
                                colsValid = false;
                            }
                        }
                    }
                }
            }
        }

        // validate the 9 3x3 squares
        int squaresValid = 0;
        squaresValid += squares(puzzle, 0, 0, 2, 2);
        squaresValid += squares(puzzle, 0, 3, 2, 5);
        squaresValid += squares(puzzle, 0, 6, 2, 8);
        squaresValid += squares(puzzle, 3, 0, 5, 2);
        squaresValid += squares(puzzle, 3, 3, 5, 5);
        squaresValid += squares(puzzle, 3, 6, 5, 8);
        squaresValid += squares(puzzle, 6, 0, 8, 2);
        squaresValid += squares(puzzle, 6, 3, 8, 5);
        squaresValid += squares(puzzle, 6, 6, 8, 8);
        

        // confirms if rows, cols, and 3x3 squares are all valid, confirming total validity of the sudoku
        if (rowsValid && colsValid && squaresValid == 9){
            totalTruth = true;
        } else {
            totalTruth = false;
        }
        return totalTruth;
    }

    // helper method that checks one 3x3 square, of our choosing, for validity 
    public static int squares(char[][] puzzle, int x1, int y1, int x2, int y2) {
        int squaresValid = 1;
        char[] thisSquare = new char[9];

        // create an array of each element in the 3x3
        int count = 0;
        for (int i = x1; i < x2 + 1; i++) {
            for (int j = y1; j < y2 + 1; j++) {
                thisSquare[count] = puzzle[i][j];
                count++;
            }
        }

        // check for any repeats in the 3x3
        int count2 = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if ((thisSquare[i] != '.') && (thisSquare[i] == thisSquare[j]) && (i != j)) {
                    count2++;
                }   
            }
        }
        
        // if there are no repeats, return 1 (which in essence means the 3x3 is valid)
        if (count2 == 0) {
            squaresValid = 1;
        }
        return squaresValid;
    }

    // solve the sudoku automatically
    public static boolean solve(char[][] puzzle) {

        if (check(puzzle)) {
        // by brute force, check every possibility until a correct sudoku solution is found
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (puzzle[i][j] == '.') {
                    for (int k = 1; k < 10; k++) {
                        char charCount = (char)(k + '0');
                        puzzle[i][j] = charCount;
                        if (check(puzzle) && solve(puzzle)) {
                            return true;
                        }
                        puzzle[i][j] = '.'; 
                    }
                    return false;
                }
            } 
        }
        return true;
        } 
        System.out.println("The given sudoku puzzle is invalid");
        return false;
    }

    // sudoku displayer
    public static void puzzleDisplayer(char[][] puzzle) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(" " + puzzle[i][j]);
            }
            System.out.println();
        }
    }
}