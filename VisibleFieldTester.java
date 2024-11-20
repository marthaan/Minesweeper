// Name: Martha Ann Williams
// USC NetID: marthaan
// CS 455 PA3
// Fall 2023

public class VisibleFieldTester {
   public static void main(String[] args) {
      boolean[][] data = {
            {true, false, true},
            {false, false, true},
            {true, true, false},
      };
      MineField mf = new MineField(data);
      VisibleField vf = new VisibleField(mf);

      System.out.println("---TEST: VisibleField constructor, resetGameDisplay(), and toString()---"); // test vf (already know mf to string works)
      System.out.println("Expected: ");
      System.out.println("[-1, -1, -1]\n" +
            "[-1, -1, -1]\n" +
            "[-1, -1, -1]");
      System.out.println("Actual: ");
      System.out.println(vf.toString());       // also shows resetGameDisplay() works since in constructor

      System.out.println("---TEST: getMineField()---");
      System.out.println("Expected: ");
      System.out.println("[true, false, true]\n" +
            "[false, false, true]\n" +
            "[true, true, false]");
      MineField underlying = vf.getMineField();
      System.out.println("Actual: ");
      System.out.println(underlying.toString());

      System.out.println("---TEST: getStatus(1, 1)---");    // all will be -1 since don't need to manually change, but shows still runs
      System.out.println("Expected: -1");
      System.out.println("Actual: " + vf.getStatus(1, 1));
      System.out.println();

      System.out.println("---TEST: numMinesLeft()---");
      System.out.println("Expected: 5");
      System.out.println("Actual: " + vf.numMinesLeft());
      System.out.println();

      // successfully tested cycleGuesses via running MineSweeper

      System.out.println("---TEST: isUncovered(0, 0), uncover(0, 0)");
      System.out.println("isUncovered --> expected: false, actual: " + vf.isUncovered(0, 0));
      System.out.println("uncover --> expected: false, actual: " + vf.uncover(0, 0));        // returns false iff you uncover a mine at (0, 0)
      System.out.println();

      System.out.println("---TEST: isUncovered(2, 2), uncover(2, 2)");
      System.out.println("uncover --> expected: true, actual: " + vf.uncover(2, 2));        // returns false iff you uncover a mine at (0, 0)
      System.out.println("isUncovered --> expected: true, actual: " + vf.isUncovered(2, 2));

      // successfully tested isGameOver properties via MineSweeper
   }
}