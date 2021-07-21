public class Game{
    private int[] board; //game board

    private byte currentPlayer; //X is 1, O is -1

    Game() {
        board = new int[9];
        currentPlayer = 1; //X starts
    }

    public String toString() {
        String soFar = " ";
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                soFar+=(board[(i * 3) + j]);
                if (j < 2) soFar+=" | ";
            }
            soFar+="\n";
            if (i < 2) soFar+="---+---+---\n ";
        }

        return soFar;
    }
}