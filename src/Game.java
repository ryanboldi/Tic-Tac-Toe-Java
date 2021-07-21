public class Game{
    private int[] board; //game board

    private byte currentPlayer; //X is 1, O is -1

    Game() {
        board = new int[9];
        board[1] = 1;
        board[2] = -1;
        currentPlayer = 1; //X starts
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
}