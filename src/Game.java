import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Game{
    private int[] board; //game board

    private int currentPlayer; //X is 1, O is -1

    //this stuff is set when a player wins
    public int winningPlayer = 0;
    enum posWinTypes {
        NONE,
        HORIZ,
        VERT,
        DIAG
    };
    posWinTypes winType = posWinTypes.NONE;
    public int winLocation = -1; // 0 for first row, col or diag, 1 for second, and 2 for third (no third diag)

    private static Scanner scanner = new Scanner(System.in);

    Game() {
        board = new int[9];
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
        if (!checkForWin()){
            currentPlayer *= -1;
            getNextTurn();
        } else {
            //someone has won!
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println(convertIntToPlayer(winningPlayer) + " has won!");

            //draw out the game board, with a line at the place where the user has won

            String soFar = " ";
            if (winType == posWinTypes.HORIZ){
                for (int i = 0; i < 3; i++){
                    for (int j = 0; j < 3; j++){
                        soFar+=getPlayerAt(j, i);
                        if (i != winLocation && j < 2) soFar+=" | ";
                        if (i == winLocation && j < 2) soFar+="-|-";
                    }
                    soFar+="\n";
                    if (i < 2) soFar+="---+---+---\n ";
                }
                System.out.println(soFar);
            }
        }
    }

    //returns if a win is on the board, and sets this classes' fields to reflect the position and winnner.
    private boolean checkForWin(){
        //reduce array into thirds representing whether or not there was a horizontal/vertical win.

        int[] horiz = new int[3];
        for (int i = 0; i < 9; i+=3){
            horiz[i/3] = checkIfSame(Arrays.copyOfRange(board, i, i+3));
        }

        System.out.print("\nHorizontal Wins:");
        System.out.print(horiz[0]);
        System.out.print(horiz[1]);
        System.out.print(horiz[2]);

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
        System.out.println(vert[2]);

        int [] diag = new int[2];
        //top left to bottom right diagonal
        int[] tlbr = new int[3];
        System.arraycopy(board, 0, tlbr, 0, 1);
        System.arraycopy(board, 4, tlbr, 1, 1);
        System.arraycopy(board, 8, tlbr, 2, 1); 
        diag[0] = checkIfSame(tlbr);

        //top right to bottom left diagonal
        int[] trbl = new int[3];
        System.arraycopy(board, 2, tlbr, 0, 1);
        System.arraycopy(board, 3, tlbr, 1, 1);
        System.arraycopy(board, 6, tlbr, 2, 1); 
        diag[1] = checkIfSame(trbl);

        //now we have 3 arrays that represent all of the wins that are currently on the board (in a real game there would only be one win at a time.)
        //this might be less efficient than immediately returning, but I want to be able to detect multiple wins up to this point
        //from here on out, i will only detect and report ONE WIN.

        for (int i = 0; i < horiz.length; i++){
            if (horiz[i] != 0){
                winningPlayer = horiz[i];
                winType = posWinTypes.HORIZ;
                winLocation = i;
                return true;
            }
        }

        for (int i = 0; i < vert.length; i++){
            if (vert[i] != 0){
                winningPlayer = vert[i];
                winType = posWinTypes.VERT;
                winLocation = i;
                return true;
            }
        }

        for (int i = 0; i < diag.length; i++){
            if (diag[i] != 0){
                winningPlayer = diag[i];
                winType = posWinTypes.DIAG;
                winLocation = i;
                return true;
            }
        }

        return false;
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
        return convertIntToPlayer(currentPlayer);
    }

    private char convertIntToPlayer(int player){
        return (player == 1 
        ? 'X' 
        : player == -1
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