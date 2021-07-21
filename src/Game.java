import java.util.InputMismatchException;
import java.util.Scanner;


public class Game{
    private int[] board; //game board

    private byte currentPlayer; //X is 1, O is -1

    private static Scanner scanner = new Scanner(System.in);

    Game() {
        board = new int[9];
        board[1] = 1;
        board[2] = -1;
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
        System.out.println(String.format("Player: %c", getCurPlayer()));
        System.out.println("Please select a board position to place at (x,y):");
        boolean xValid = false;
        int x;
        while(!xValid){
            System.out.print("x (1-3): ");
            try {
                x = scanner.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Not a valid input. Please type and integer from 1 to 3");
                scanner.nextLine();
                continue;
            }
            xValid = true;
            System.out.println("selected x: " + x);
        }
    }

    private int getFromUser(String req, String errMsg, String var){
        
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
        }
    }
}


class InvalidOperationError extends Exception{};