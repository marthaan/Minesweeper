// Name: Martha Ann Williams
// USC NetID: marthaan
// CS 455 PA3
// Fall 2023

// TODO: comments

public class MineFieldTester {
   public static void main(String args[]) {
      boolean[][] data = {
            {true, false, true},
            {false, false, true},
            {true, true, false},
      };

      MineField mine = new MineField(data);

      System.out.println("---TEST: MineField 1-arg constructor (and toString())---");
      System.out.println("Expected:");
      System.out.println("[true, false, true]\n" +
            "[false, false, true]\n" +
            "[true, true, false]");
      System.out.println("Actual: ");
      System.out.println(mine.toString());
      System.out.println();

      System.out.println("---TEST: numAdjacentMines(1, 1)---");
      System.out.println("Expected: 5");
      System.out.println("Actual: " + mine.numAdjacentMines(1, 1));
      System.out.println();

      System.out.println("---TEST: inRange(2, 2)---");
      System.out.println("Expected: true");
      System.out.println("Actual: " + mine.inRange(2, 2));
      System.out.println();

      System.out.println("---TEST: inRange(5, 5)---");
      System.out.println("Expected: false");
      System.out.println("Actual: " + mine.inRange(5, 5));
      System.out.println();

      System.out.println("---TEST: numRows() and numCols()---");
      System.out.println("Expected: rows = 3, cols = 3");
      System.out.println("Actual: rows = " + mine.numRows() + ", cols = " + mine.numCols());
      System.out.println();

      System.out.println("---TEST: hasMine(0, 0)---");
      System.out.println("Expected: true");
      System.out.println("Actual: " + mine.hasMine(0, 0));
      System.out.println();

      System.out.println("---TEST: hasMine(2, 2)---");
      System.out.println("Expected: false");
      System.out.println("Actual: " + mine.hasMine(2, 2));
      System.out.println();

      System.out.println("---TEST: numMines()---");
      System.out.println("Expected: 5");
      System.out.println("Actual: " + mine.numMines());
      System.out.println();

      System.out.println("---TEST: resetEmpty()---");
      mine.resetEmpty();
      System.out.println("Expected: ");
      System.out.println("[false, false, false]\n" +
            "[false, false, false]\n" +
            "[false, false, false]");
      System.out.println("Actual: ");
      System.out.println(mine.toString());
      System.out.println();

      System.out.println("---TEST: MineField 3-arg constructor (and toString())---");
      MineField mineField3Arg = new MineField(3, 3, 2);     // ran with -ea to check that numMines() < 1/3 (numRows() * numCols())
      System.out.println("Expected: ");
      System.out.println("[false, false, false]\n" +
            "[false, false, false]\n" +
            "[false, false, false]");
      System.out.println("Actual: ");
      System.out.println(mineField3Arg.toString());
      System.out.println();

      System.out.println("---TEST: populateMineField(2, 2)---");
      mineField3Arg.populateMineField(2, 2);
      System.out.println("Expected: 2 random mines == true");
      System.out.println("Actual: ");
      System.out.println(mineField3Arg.toString());
      System.out.println();

      System.out.println("---TEST: resetEmpty(), then repopulate same as last test---");
      mineField3Arg.resetEmpty();
      System.out.println("Expected: all mines == false");
      System.out.println("Actual: ");
      System.out.println(mineField3Arg.toString());
      mineField3Arg.populateMineField(2, 2);
      System.out.println("Repopulated w/ 2 random mines == true --> actual: " + "\n" + mineField3Arg.toString());

      System.out.println("---TEST: numAdjacentMines()---");
      System.out.println("numAdj(1, 1) = " + mineField3Arg.numAdjacentMines(1, 1));    // no expected since placement is random
      System.out.println("numAdj(0, 0) = " + mineField3Arg.numAdjacentMines(0, 0));    // no expected since placement is random
      System.out.println();

      System.out.println("---TEST: inRange(3, 3)---");
      System.out.println("Expected: false");
      System.out.println("Actual: " + mineField3Arg.inRange(3,3));
      System.out.println();

      System.out.println("---TEST: numRows() and numCols()---");
      System.out.println("Expected: rows = 3, cols = 3");
      System.out.println("Actual: rows = " + mineField3Arg.numRows() + ", cols = " + mineField3Arg.numCols());
      System.out.println();

      System.out.println("---TEST: hasMine(1, 1)---");
      System.out.println("hasMine(1, 1) = " + mineField3Arg.hasMine(1, 1));         // no expected since placement is random
      System.out.println();

      System.out.println("---TEST: numMines()");
      System.out.println("Expected: 2");
      System.out.println("Actual: " + mineField3Arg.numMines());
   }
}