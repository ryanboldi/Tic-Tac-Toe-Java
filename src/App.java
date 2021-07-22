public class App {
    public static void main(String[] args) {
        Game g = new Game();
        g.getNextTurn();
        System.out.print(g.toString());
        g.checkForWin();
    }
}
