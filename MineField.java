// Name: Martha Ann Williams
// USC NetID: marthaan
// CS 455 PA3
// Fall 2023

import java.util.Arrays;
import java.util.Random;

/** 
   MineField
      Class with locations of mines for a minesweeper game.
      This class is mutable, because we sometimes need to change it once it's created.
      Mutators: populateMineField, resetEmpty
      Includes convenience method to tell the number of mines adjacent to a location.
 */
public class MineField {
   private boolean[][] minefieldData;
   private int mines = 0;
   private int mineRow;
   private int mineCol;
   
   /**
      Create a minefield with same dimensions as the given array, and populate it with the mines in
      the array such that if mineData[row][col] is true, then hasMine(row,col) will be true and vice
      versa.  numMines() for this minefield will correspond to the number of 'true' values in
      mineData.
      @param mineData  the data for the mines; must have at least one row and one col,
                       and must be rectangular (i.e., every row is the same length)
    */
   public MineField(boolean[][] mineData) {
      int rows = mineData.length;
      int cols = mineData[0].length;

      assert rows != 0 && cols != 0;
      assert rows == cols;

      minefieldData = new boolean[rows][cols];

      mineRow = rows;
      mineCol = cols;

      for (int i = 0; i < rows; i++) {
         for (int j = 0; j < cols; j++) {
            minefieldData[i][j] = mineData[i][j];
            if (mineData[i][j] == true) {
               mines += 1;
            }
         }
      }
   }
   
   
   /**
      Create an empty minefield (i.e. no mines anywhere), that may later have numMines mines (once 
      populateMineField is called on this object).  Until populateMineField is called on such a 
      MineField, numMines() will not correspond to the number of mines currently in the MineField.
      @param numRows  number of rows this minefield will have, must be positive
      @param numCols  number of columns this minefield will have, must be positive
      @param numMines   number of mines this minefield will have,  once we populate it.
      PRE: numRows > 0 and numCols > 0 and 0 <= numMines < (1/3 of total number of field locations). 
    */
   public MineField(int numRows, int numCols, int numMines) {
      assert numRows > 0 && numCols > 0;
      assert 0 <= numMines && numMines < ((numRows * numCols) / 3);

      minefieldData = new boolean[numRows][numCols];

      resetEmpty();

      mineRow = numRows;
      mineCol = numCols;

      mines = numMines;
   }
   

   /**
      Removes any current mines on the minefield, and puts numMines() mines in random locations on
      the minefield, ensuring that no mine is placed at (row, col).
      @param row the row of the location to avoid placing a mine
      @param col the column of the location to avoid placing a mine
      PRE: inRange(row, col) and numMines() < (1/3 * numRows() * numCols())
    */
   public void populateMineField(int row, int col) {
      assert inRange(row, col) && numMines() < (mineRow * mineCol) / 3;

      resetEmpty();

      Random genRow = new Random();
      Random genCol = new Random();
      int r = 0;
      int c = 0;

      int countMines = 0;

      while (countMines < numMines()) {
         r = genRow.nextInt(mineRow);
         c = genCol.nextInt(mineCol);

         if (r != row && c != col && minefieldData[r][c] != true) {
            minefieldData[r][c] = true;
            countMines++;
         }
      }
   }
   
   
   /**
      Reset the minefield to all empty squares.  This does not affect numMines(), numRows() or
      numCols().  Thus, after this call, the actual number of mines in the minefield does not match
      numMines().  
      Note: This is the state a minefield created with the three-arg constructor is in at the 
      beginning of a game.
    */
   public void resetEmpty() {
      for (boolean[] row : minefieldData) {
         Arrays.fill(row, false);
      }
   }

   
  /**
     Returns the number of mines adjacent to the specified location (not counting a possible 
     mine at (row, col) itself).
     Diagonals are also considered adjacent, so the return value will be in the range [0,8]
     @param row  row of the location to check
     @param col  column of the location to check
     @return  the number of mines adjacent to the square at (row, col)
     PRE: inRange(row, col)
   */
   public int numAdjacentMines(int row, int col) {
      assert inRange(row, col);

      int totalAdj = 0;

      for (int i = row - 1; i <= row + 1; i++)
      {
         for (int j = col - 1; j <= col + 1; j++)
         {
            if (inRange(i, j) && hasMine(i, j) && !(i == row && j == col))
            {
               totalAdj++;
            }
         }
      }

      return totalAdj;
   }
   
   
   /**
      Returns true iff (row,col) is a valid field location.  Row numbers and column numbers
      start from 0.
      @param row  row of the location to consider
      @param col  column of the location to consider
      @return whether (row, col) is a valid field location
   */
   public boolean inRange(int row, int col) {
      boolean valid = false;

      if ((row >= 0 && row < mineRow) && (col >= 0 && col < mineCol)) {
         valid = true;
      }

      return valid;
   }
   
   
   /**
      Returns the number of rows in the field.
      @return number of rows in the field
   */  
   public int numRows() {
      return mineRow;
   }
   
   
   /**
      Returns the number of columns in the field.
      @return number of columns in the field
   */    
   public int numCols() {
      return mineCol;
   }
   
   
   /**
      Returns whether there is a mine in this square
      @param row  row of the location to check
      @param col  column of the location to check
      @return whether there is a mine in this square
      PRE: inRange(row, col)   
   */    
   public boolean hasMine(int row, int col) {
      boolean isMine = false;

      if (minefieldData[row][col] == true) {
         isMine = true;
      }

      return isMine;
   }
   
   
   /**
      Returns the number of mines you can have in this minefield.  For mines created with the 3-arg
      constructor, some of the time this value does not match the actual number of mines currently
      on the field.  See doc for that constructor, resetEmpty, and populateMineField for more
      details.
      @return number of mines
    */
   public int numMines() {
      return mines;
   }


   /**
    * Converts this minefield to a String.
    * @return minefield as a String
    */
   public String toString() {
      String result = "";

      for (boolean[] row : minefieldData) {
         result += Arrays.toString(row) + "\n";
      }

      return result;
   }
}

