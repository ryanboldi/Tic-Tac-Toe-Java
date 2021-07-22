import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Game{
    private int[] board; //game board

    private int currentPlayer; //X is 1, O is -1

    private static Scanner scanner = new Scanner(System.in);

    Game() {
        board = new int[9];
        board[0] = 1;
        board[3] = 1;
        board[6] = 1;
        currentPlayer = 1; //X starts
    }

    /**
     * String representation of the current Game Board
     */
    public String toString() {
        String soFar = " ";
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                soFar+=getPlayerAt(j, i);
                if (j < 2) soFar+=" | ";
            }
            soFar+="\n";
            if (i < 2) soFar+="---+---+---\n ";
        }

        return soFar;
    }

    public void getNextTurn(){
        boolean turnGood = false;
        while(!turnGood){
            System.out.print(this.toString());
            System.out.println(String.format("Player: %c", getCurPlayer()));
            System.out.println("Please select a board position to place at (x,y):");
            int x = getIntFromUser("x (1-3): ", "Invalid input. Please enter an integer between 1 and 3", 1, 3);
            int y = getIntFromUser("y (1-3): ", "Invalid input. Please enter an integer between 1 and 3", 1, 3);

            try {
                placeAt(x-1, y-1);
            } catch (InvalidOperationError ex) {
                System.out.println(String.format("Position (%s, %s) already Taken. Please select a new one.\n\n", x-1, y-1));
                continue;
            }
            turnGood = true;
        }

        //check for win
        //if not win, change current turn to next player
    }

    //returns 1 if X has won, -1 if O has won, and 0 if no wins yet.
    public int checkForWin(){
        //reduce array into thirds representing whether or not there was a horizontal/vertical win.

        int[] horiz = new int[3];
        for (int i = 0; i < 9; i+=3){
            horiz[i/3] = checkIfSame(Arrays.copyOfRange(board, i, i+3););
        }
        System.out.print("\nHorizontal Wins:");
        System.out.print(horiz[0]);
        System.out.print(horiz[1]);
        System.out.print(horiz[2] + "\n");

        int[] vert = new int[3];
        for (int i = 0; i < 3; i++){
            int[] tempCol = new int[3];
            System.arraycopy(board, i, tempCol, 0, 1);
            System.arraycopy(board, i + 3, tempCol, 1, 1);
            System.arraycopy(board, i + 6, tempCol, 2, 1); 

            vert[i] = checkIfSame(tempCol);
        }
        System.out.print("\nVertical Wins:");
        System.out.print(vert[0]);
        System.out.print(vert[1]);
        System.out.print(vert[2]);

    

        return 0;
    }

    //checks if all elements in arr are the same. If so, return one of the elements. If not, return 0.
    private int checkIfSame(int[] arr){
        int compareTo = arr[0];
        for( int i = 1; i < arr.length; i++){
            if(arr[i] != compareTo){
                return 0;
            }
        }
        return compareTo;
    }

    /**
     * Int input parsing system. Continues untill user inputs valid integer between lowerbound and upperboard inclusive 
     * @param req Request Message
     * @param errMsg Message to display on error
     * @param lowerBound max possible integer
     * @param upperBound min possible integer
     * @return
     */
    private int getIntFromUser(String req, String errMsg, int lowerBound, int upperBound){
        boolean valid = false;
        int x;
        while(!valid){
            System.out.print(req);
            try {
                x = scanner.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println(errMsg);
                scanner.nextLine();
                continue;
            } 
            if(x >= lowerBound && x <= upperBound){
                valid = true;
                return x;
            } else {
                System.out.println(errMsg);
                continue;
            }
        }
        return 0; //should never get here
    }

    private char getCurPlayer(){
        return (currentPlayer == 1 
        ? 'X' 
        : currentPlayer == -1
            ? 'O' 
            : ' ');
    }

    
    /**
     * gets 'X','O' or ' ' depending on which player is occupying this position
     * @param x col that the player is in (0-indexed)
     * @param y row that the player is in (0-indexed)
     * @return one character 'X', 'O' or ' ';
     */
    private char getPlayerAt(int x, int y){
        int player = board[(y*3) + x];
        return (player == 1 
        ? 'X' 
        : player == -1
            ? 'O' 
            : ' ');
    }

    private int getNumAt(int x, int y) {return board[(y*3 + x)];}

    private void placeAt(int x, int y) throws InvalidOperationError {
        if (getNumAt(x, y) != 0){
            throw new InvalidOperationError();
        } else {
            board[(y * 3) + x] = currentPlayer;
        }
    }
}


class InvalidOperationError extends Exception{};