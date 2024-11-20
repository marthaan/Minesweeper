// Name: Martha Ann Williams
// USC NetID: marthaan
// CS 455 PA3
// Fall 2023

import java.util.Arrays;

/**
  VisibleField class
  This is the data that's being displayed at any one point in the game (i.e., visible field, because
  it's what the user can see about the minefield). Client can call getStatus(row, col) for any 
  square.  It actually has data about the whole current state of the game, including the underlying
  minefield (getMineField()).  Other accessors related to game status: numMinesLeft(),
  isGameOver().  It also has mutators related to actions the player could do (resetGameDisplay(),
  cycleGuess(), uncover()), and changes the game state accordingly.
  
  It, along with the MineField (accessible in mineField instance variable), forms the Model for the
  game application, whereas GameBoardPanel is the View and Controller in the MVC design pattern.  It
  contains the MineField that it's partially displaying.  That MineField can be accessed
  (or modified) from outside this class via the getMineField accessor.  
 */
public class VisibleField {
   // ----------------------------------------------------------   
   // The following public constants (plus values [0,8] mentioned in comments below) are the
   // possible states of one location (a "square") in the visible field (all are values that can be
   // returned by public method getStatus(row, col)).
   
   // The following are the covered states (all negative values):
   public static final int COVERED = -1;   // initial value of all squares
   public static final int MINE_GUESS = -2;
   public static final int QUESTION = -3;

   // The following are the uncovered states (all non-negative values):
   
   // values in the range [0,8] corresponds to number of mines adjacent to this opened square
   
   public static final int MINE = 9;      // this loc is a mine that hasn't been guessed already
                                          // (end of losing game)
   public static final int INCORRECT_GUESS = 10;  // is displayed a specific way at the end of
                                                  // losing game
   public static final int EXPLODED_MINE = 11;   // the one you uncovered by mistake (that caused
                                                 // you to lose)
   // ----------------------------------------------------------   

   private MineField underlyingMineField;
   private int[][] states;
   private boolean gameOver;
   private int numGuesses;

   /**
      Create a visible field that has the given underlying mineField.
      The initial state will have all the locations covered, no mines guessed, and the game not
      over.
      @param mineField  the minefield to use for this VisibleField
    */
   public VisibleField(MineField mineField) {
      underlyingMineField = mineField;

      int statesRows = underlyingMineField.numRows();
      int statesCols = underlyingMineField.numCols();

      states = new int[statesRows][statesCols];

      resetGameDisplay();

      gameOver = false;
   }
   
   
   /**
      Reset the object to its initial state (see constructor comments), using the same underlying
      MineField. 
   */     
   public void resetGameDisplay() {
      gameOver = false;

      for (int[] array : states) {
         Arrays.fill(array, -1);
      }
   }
  
   
   /**
      Returns a reference to the mineField that this VisibleField "covers"
      @return the minefield
    */
   public MineField getMineField() {
      return underlyingMineField;
   }
   
   
   /**
      Returns the visible status of the square indicated.
      @param row  row of the square
      @param col  col of the square
      @return the status of the square at location (row, col).  See the public constants at the
            beginning of the class for the possible values that may be returned, and their meanings.
      PRE: getMineField().inRange(row, col)
    */
   public int getStatus(int row, int col) {
      assert getMineField().inRange(row, col);

      return states[row][col];
   }

   
   /**
      Returns the number of mines left to guess.  This has nothing to do with whether the mines
      guessed are correct or not.  Just gives the user an indication of how many more mines the user
      might want to guess.  This value will be negative if they have guessed more than the number of
      mines in the minefield.     
      @return the number of mines left to guess.
    */
   public int numMinesLeft() {
      return underlyingMineField.numMines() - numGuesses;
   }
 
   
   /**
      Cycles through covered states for a square, updating number of guesses as necessary.  Call on
      a COVERED square changes its status to MINE_GUESS; call on a MINE_GUESS square changes it to
      QUESTION;  call on a QUESTION square changes it to COVERED again; call on an uncovered square
      has no effect.  
      @param row  row of the square
      @param col  col of the square
      PRE: getMineField().inRange(row, col)
    */
   public void cycleGuess(int row, int col) {
      assert getMineField().inRange(row, col);

      if (states[row][col] == COVERED) {
         states[row][col] = MINE_GUESS;
         numGuesses++;
      }
      else if (states[row][col] == MINE_GUESS) {
         states[row][col] = QUESTION;
      }
      else if (states[row][col] == QUESTION) {
         states[row][col] = COVERED;
      }
   }

   
   /**
      Uncovers this square and returns false iff you uncover a mine here.
      If the square wasn't a mine or adjacent to a mine it also uncovers all the squares in the
      neighboring area that are also not next to any mines, possibly uncovering a large region.
      Any mine-adjacent squares you reach will also be uncovered, and form (possibly along with
      parts of the edge of the whole field) the boundary of this region.
      Does not uncover, or keep searching through, squares that have the status MINE_GUESS. 
      Note: this action may cause the game to end: either in a win (opened all the non-mine squares)
      or a loss (opened a mine).
      @param row  of the square
      @param col  of the square
      @return false   iff you uncover a mine at (row, col)
      PRE: getMineField().inRange(row, col)
    */
   public boolean uncover(int row, int col) {
      assert getMineField().inRange(row, col);

      if (underlyingMineField.hasMine(row, col)) {
         states[row][col] = EXPLODED_MINE;
         gameOver = true;
         return false;
      }

      updateStates(row, col);

      return true;
   }


   /**
      Returns whether the game is over.
      (Note: This is not a mutator.)
      @return whether game has ended
    */
   public boolean isGameOver() {
      if (gameOver == true) {
         for (int i = 0; i < underlyingMineField.numRows(); i++) {
            for (int j = 0; j < underlyingMineField.numCols(); j++) {
               if (states[i][j] == MINE_GUESS && underlyingMineField.hasMine(i, j)) {
                  states[i][j] = INCORRECT_GUESS;
               }
               if (!isUncovered(i, j) && underlyingMineField.hasMine(i, j)) {
                  states[i][j] = MINE;
               }
            }
         }
         return true;
      }

      for (int i = 0; i < underlyingMineField.numRows(); i++) {
         for (int j = 0; j < underlyingMineField.numCols(); j++) {
            if (!isUncovered(i, j) && !underlyingMineField.hasMine(i, j)) {
               return false;
            }
         }
      }

      for (int i = 0; i < underlyingMineField.numRows(); i++) {
         for (int j = 0; j < underlyingMineField.numCols(); j++) {
            if (!isUncovered(i, j) && underlyingMineField.hasMine(i, j)) {
               states[i][j] = MINE_GUESS;
            }
         }
      }

      return true;
   }


   /**
      Returns whether this square has been uncovered.  (i.e., is in any one of the uncovered states, 
      vs. any one of the covered states).
      @param row of the square
      @param col of the square
      @return whether the square is uncovered
      PRE: getMineField().inRange(row, col)
    */
   public boolean isUncovered(int row, int col) {
      assert getMineField().inRange(row, col);

      return states[row][col] != COVERED && states[row][col] != MINE_GUESS && states[row][col] != QUESTION;
   }


   /**
    * Converts this VisibleField to a String.
    * @return VisibleField as a String
    */
   public String toString() {
      String result = "";

      for (int[] row : states) {
         result += Arrays.toString(row) + "\n";
      }

      return result;
   }

   /**
    * Recursive method for uncover(). Updates states accordingly.
    * @param row of the square
    * @param col of the square
    */
   private void updateStates(int row, int col) {
      if (!underlyingMineField.inRange(row, col)) {
         return;
      }

      if (isUncovered(row, col)) {
         return;
      }

      if (states[row][col] == MINE_GUESS) {
         return;
      }

      int adjMines = underlyingMineField.numAdjacentMines(row, col);

      if (adjMines == 0) {
         states[row][col] = adjMines;
         for (int i = row - 1; i <= row + 1; i++)
         {
            for (int j = col - 1; j <= col + 1; j++)
            {
               if (i != row || j != col) {
                  updateStates(i, j);
               }
            }
         }
      }
      else {
         states[row][col] = adjMines;
      }
   }
}
