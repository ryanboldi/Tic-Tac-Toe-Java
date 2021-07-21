public class Game{
    private int[] board = {1, 0, 1, 0, 1, 0, 1, 0, 1};; //game board
    private byte currentPlayer; //X is 1, O is -1

    Game() {
        //board = new int[9];
        currentPlayer = 1; //X starts
    }

    public String toString() {
        String soFar = "";
        int j = 0;
        for (int i = 0; i < 3; i++){
            if (i == 2){
                i = 0;
                j++;
                soFar+="\n";
            }
            System.out.println("i: " + i + " j: " + j);
            soFar+=(board[(j * 3) + i]);
        }

        return soFar;
    }
}