

import java.util.ArrayList;


/**
 * Stores a Yavalath board and the contents of its
 * hexagonal cells.
 * <p>
 * The move methods can be used to make a new move.
 * This class also maintains some other
 * board/game properties. It keeps track of which
 * colour is to move next.
 * <p>
 * A list of all moves
 * made in the game so far is also kept. This
 * list is used by the unmove method. The unmove
 * method can be used to unwind previous move(s)
 * and restore the board to a past state.
 * <p>
 * After ever move, a check is made as to whether
 * the game is over and who, if anyone, has won.
 * <p>
 * A piece can only be played in an empty cell
 * (with one exception).
 * White always plays first.
 * To nullify first player advantage, there is one
 * exception to this rule. In the second move of the
 * game, when black plays, the black player has the
 * option of playing on the position just occupied
 * by white. If he specifies the coordinates white
 * has just played in his first move, then this white
 * piece is removed and a black piece used to replace
 * it. This is know as the "swap rule".
 */
class Board
{
    public final static int EMPTY = 0;
    public final static int WHITE = 1;
    public final static int BLACK = 2;

    public final static int DRAW = 0;


	/** Minimum allowed board size. */
    public final static int MIN_BOARD_SIZE = 5;
    /** Upper limit on board size. */
    public final static int MAX_BOARD_SIZE = 10;

	/** Size of the board. */
    protected int size;

    /** The game board (each cell is either EMPTY, WHITE or BLACK). */
    protected int [][] board;

	/** Number of moves made so far in the game. */
    protected int numberOfMovesMade;

	/** Array containing all moves made so far. */
    protected HexCoord [] moveArray;

	/** Records whether Black used the "swap rule" when starting off. */
    boolean swapRuleUsed;

    /** What colour player is due to move next. */
    protected int colourToPlay;

	/** Is game over? */
    protected boolean gameOver;

    /** If game is over ,then this holds DRAW (0) for a draw,
     *  WHITE (1) if white won, and BLACK (2) if black won.
     */
    protected int winner;


	/* --------- Some Getters and Setters ---------- */

    public int getSize()
    {
	return size;
    }


    public int getCell(int row, int col)
    {
	if (row<0 || row>=size)
	    throw new IndexOutOfBoundsException("board row index must be in range [0,"+
						 (size-1)+"]");
	if (col<0 || col>=size)
	    throw new IndexOutOfBoundsException("board col index must be in range [0,"+
						 (size-1)+"]");
	return board[row][col];
    }

    public int getCell(HexCoord hc)
    {
	int r = hc.getRow();
	int c = hc.getCol();
	return getCell(r,c);
    }


    public int getNumberOfMovesMade()
    {
	return numberOfMovesMade;
    }

    public boolean wasSwapRuleUsed()
    {
	return swapRuleUsed;
    }

	/** Gives colour of player due to play. */
    public int getColourToPlay()
    {
	return colourToPlay;
    }

	/** Gives opponent (opposite) colour of
	 *  player currently due to play.
	 */
    public int getOpponentColour()
    {
	if (colourToPlay == WHITE)
	    return BLACK;
	return WHITE;
    }


	/* ----------- Methods for checking if game is over ---------*/


    public boolean isGameOver()
    {
	return gameOver;
    }


    public boolean isDraw()
    {
	return (gameOver && winner==DRAW);
    }

    public boolean hasWhiteWon()
    {
	return (gameOver && winner==WHITE);
    }

    public boolean hasBlackWon()
    {
	return (gameOver && winner==BLACK);
    }


	/* ---------- Methods for resetting/emptying board ---------*/


    /** Empties and resets board. */
    public void reset()
    {
	int r,c;
	for (r=0; r<size; r++)
	    for (c=0; c<size; c++)
		board[r][c] = EMPTY;

	numberOfMovesMade = 0;
	swapRuleUsed = false;
	colourToPlay = WHITE; // white always plays first

	gameOver = false;
	winner = 0;
    }


    /** Empties board and resets it to new size aSize. */
    public void resetToSize(int aSize)
    {
	if (aSize<MIN_BOARD_SIZE)
	    throw new IllegalArgumentException("board size must be at least "+
					       MIN_BOARD_SIZE);
	if (aSize>MAX_BOARD_SIZE)
	    throw new IllegalArgumentException("board size must not exceed "+
					       MAX_BOARD_SIZE);
	size = aSize;
	board = new int[size][size];

	moveArray = new HexCoord[size*size+1]; // maximum possible number of moves

	reset();
    }


	/** Constructor. */
    public Board(int aSize)
    {
		resetToSize(aSize);
    }


	/** Checks if proposed move is valid. */
    public boolean isValidMove(int row, int col)
    {
	if (gameOver) // can't move if game is already over
	    return false;
	if (row<0 || row>=size) // invalid row coord?
	    return false;
	if (col<0 || col>=size) // invalud col coord?
	    return false;
	if (numberOfMovesMade==1 && board[row][col]!=EMPTY) // swap rule case
	    return true;
	if (board[row][col]==EMPTY) // otherwise move must be into empty cell
	    return true;
	return false;
    }

    public boolean isValidMove(HexCoord hc)
    {
	int r = hc.getRow();
	int c = hc.getCol();
	return isValidMove(r,c);
    }


	/** Attempts to make requested move on the board. */
    public void move(int row, int col)
    {
	if (gameOver) // can't move is game is already over
	    throw new IllegalYavalathMove("cannot move (game already over)");
	// check if coordinates are valid
	if (row<0 || row>=size)
	    throw new IllegalYavalathMove("board row index must be in range [0,"+
						 (size-1)+"]");
	if (col<0 || col>=size)
	    throw new IllegalYavalathMove("board col index must be in range [0,"+
						 (size-1)+"]");

	// on second move of game (first move by black), black has
	// the option of replacing white's first piece with its own
	if (numberOfMovesMade==1 && board[row][col]!=EMPTY) // swap rule case
	    {
		board[row][col] = colourToPlay;
		swapRuleUsed = true;
	    }
	else if (board[row][col]==EMPTY) // conventional move of filling empty cell
	    {
		board[row][col] = colourToPlay;
	    }
	else throw new IllegalYavalathMove("cell already occupied");

	moveArray[numberOfMovesMade] = new HexCoord(row,col); // store move
	numberOfMovesMade++;

	// check whether this move has caused a won or drawn game

	int maxRowAtCoord = maxInARowAtCoord(row,col);
	if (maxRowAtCoord>=4) { // part of a 4-in-a-row?
	    gameOver = true;
	    winner = colourToPlay;
	}
	else if (maxRowAtCoord==3) { // or lost the game: 3-in-a-row?
	    gameOver = true;
	    winner = getOpponentColour(); // other player wins
	}
	// or we have a draw (full board and no winner)
	else if ( numberOfMovesMade == size*size + (swapRuleUsed ? 1 : 0)) {
	    gameOver = true;
	    winner = DRAW;
	}

	colourToPlay = getOpponentColour(); // switch colour for next move



    }

    public void move(HexCoord hc)
    {
	int r = hc.getRow();
	int c = hc.getCol();
	move(r,c);
    }


	/** Unwind last move. Possible to keep calling this
	 *  method to undo several recent moves.
	 */
    public void unmove()
    {
	HexCoord lastMove;
	int r,c;

	if (numberOfMovesMade==0)
	    throw new IllegalYavalathMove("cannot unmove (game not started)");


	lastMove = moveArray[numberOfMovesMade-1];
	r = lastMove.getRow(); c = lastMove.getCol();

	if (numberOfMovesMade==2 && swapRuleUsed) // swap rule case
	{
	    board[r][c] = WHITE;
	    swapRuleUsed = false;
	}
	else // usual case of removing a piece from board
	    board[r][c] = EMPTY;

	moveArray[numberOfMovesMade-1] = null;

	colourToPlay = getOpponentColour();
	numberOfMovesMade--;

	gameOver = false;
	winner = 0;


    }

	/** Looks for the maximum k-in-a-row sequence going
	 *  going through the given coordinate (with the same
	 *  colour as that coordiante).
	 */
    public int maxInARowAtCoord(int row, int col)
    {
	// sequence counts for all six directions from initial point (row,col).
	int wCount=0, eCount=0, nwCount=0, seCount=0, neCount=0, swCount=0;

	int colour = getCell(row,col);

	int r,c;
	boolean continueRow; // boolean flag, true as long as sequence is continuing

	if (colour==EMPTY) // if initial coord is empty, just return 0
	    return 0;

	// starts from (row,col) and see if sequence continues in West direction
	// (count doesn't include initial point)
	for (continueRow=true, r=row, c=col-1; c>= 0 && continueRow; c--)
	    {
		if (board[r][c]==colour)
		    wCount++;
		else
		    continueRow = false;
	    }

	// East direction count
	for (continueRow=true, r=row, c=col+1; c<size && continueRow; c++)
	    {
		if (board[r][c]==colour)
		    eCount++;
		else
		    continueRow = false;
	    }

	// NW count
	for (continueRow=true, r=row-1, c=col; r>= 0 && continueRow; r--)
	    {
		if (board[r][c]==colour)
		    nwCount++;
		else
		    continueRow = false;
	    }

	// SE count
	for (continueRow=true, r=row+1, c=col; r<size && continueRow; r++)
	    {
		if (board[r][c]==colour)
		    seCount++;
		else
		    continueRow = false;
	    }

	// NE count
	for (continueRow=true, r=row-1, c=col+1; r>= 0 && c<size && continueRow; r--, c++)
	    {
		if (board[r][c]==colour)
		    neCount++;
		else
		    continueRow = false;
	    }

	// SW count
	for (continueRow=true, r=row+1, c=col-1; r<size && c>=0 && continueRow; r++, c--)
	    {
		if (board[r][c]==colour)
		    swCount++;
		else
		    continueRow = false;
	    }


	int weCount = wCount + 1 + eCount; // total count in West-East direction (including initial cell)
	int nwseCount = nwCount + 1 + seCount; // total count in NW-SE direction (including initial cell)
	int neswCount = neCount + 1 + swCount; // total count in NE-SW direction (including initial cell)

	return Math.max(weCount,Math.max(nwseCount,neswCount)); // return largest of these

    }


	/** Gives an array of all possible valid moves for the
	 * current colour play due to play. */
    public ArrayList<HexCoord> getListOfPossibleMoves()
    {
	int r,c;
	ArrayList<HexCoord> moveList = new ArrayList<HexCoord>();
	if (gameOver) return moveList; // return empty list
	for (r=0; r<size; r++)
	    for (c=0; c<size; c++)
		{
		    if (board[r][c]==EMPTY)
			moveList.add(new HexCoord(r,c));
		}
	if (numberOfMovesMade==1) // swap rule case
	    moveList.add(new HexCoord(moveArray[0]));

	return moveList;
    }



    public int maxInARowAtCoord(HexCoord hc)
    {
	int r = hc.getRow();
	int c = hc.getCol();
	return maxInARowAtCoord(r,c);
    }


	/** Gives textual representation of the board. */
    public String toString()
    {
	String str = "  ";
	int r,c, spaces;
	int cell;


	for (c=0; c<size; c++) // column headers
	    str += c + " ";
	str += "\n";
	for (r=0; r<size; r++)
	    {
		// each successive row is indented by one extra space
		for (spaces = 0; spaces <r+1; spaces++)
		    str += " ";
		str += r + " "; // row header
		for (c=0; c<size; c++)
		    {
			cell = board[r][c];
			if (cell==EMPTY)
			    str += ". ";
			else if (cell==WHITE)
			    str += "W ";
			else
			    str += "B ";
		    }
		str += "\n";
	    }

	return str;
    }



}

