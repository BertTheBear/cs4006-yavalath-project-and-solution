import java.util.Scanner;

/** Simple console-based game class. */
public class Game
{
    protected Board board;

    protected Player player1; // first player to move: always white
    protected Player player2; // always black


    /** Utility method to get input (a valid board coordinate) from the user. */
    protected HexCoord getCoordFromConsole()
    {
	int r=0, c=0;

	boolean validMove = false; // keep asking until a valid coord is received

	do {

	    // Get Row.

	    boolean inputError; // valid number?
	    Scanner rowScanner = new Scanner(System.in);
	    inputError = true;
	    do {
		try {
		    System.out.println("Enter row: ");
		    r = Integer.parseInt(rowScanner.next());
		    if (r<0 || r>=board.getSize())
			{
			    System.out.println("Row must be from 0 to " + (board.getSize()-1) + " (inclusive).");
			    rowScanner.reset();
			}
		    else
				inputError = false;
		}
		catch (Exception e) {
		    System.out.println("Row must be an integer");
		    rowScanner.reset();
		}
	    } while (inputError);

		// Get Col.

	    Scanner colScanner = new Scanner(System.in);
	    inputError = true;
	    do {
		try {
		    System.out.println("Enter col: ");
		    c = Integer.parseInt(colScanner.next());
		    if (c<0 || c>=board.getSize())
			{
			    System.out.println("Col must be from 0 to " + (board.getSize()-1) + " (inclusive).");
			    colScanner.reset();
			}
		    else
				inputError = false;

		}
		catch (Exception e) {
		    System.out.println("Col must be an integer.");
		    colScanner.reset();
		}
	    } while (inputError);


	    if (board.isValidMove(r,c)) // is move valid?
			validMove = true;
	    else
			System.out.println("Not a valid move!");

	} while (!validMove); // if not, go again


	return new HexCoord(r,c);
    }


	/** Sets up a game between two players with
	 * given board size
	 * (the first being WHITE and playing first,
	 *  the second being BLACK).
	 */
    public Game(int aSize, Player p1, Player p2)
    {
	board = new Board(aSize);
	player1 = p1;
	player1.setColour(Board.WHITE);
	player2 = p2;
	player2.setColour(Board.BLACK);


    }


	/** Plays a game (resets board first).
	 *  Takes input if necessary from human players.
	 *  @return Returns WHITE (1) if white won,
	 *  BLACK (2) is black won, and DRAW (0) if
	 *  the game finished in a draw.
	 */
    public int play()
    {
	HexCoord move;
	board.reset();

	System.out.println("Player 1: " + player1.getName() + " (White) Versus");
	System.out.println("Player 2: " + player2.getName() + " (Black).");
	System.out.println("\n");
	System.out.println(board);

	while (true) {
	    if (player1.isHuman())
		{
		    System.out.println("Player 1: " + player1.getName() + " (White) To Move");
		    move = getCoordFromConsole();
		}
	    else
		{
		    move = player1.makeMove(board);
		    System.out.println("Player 1: " + player1.getName() + " (White) plays " + move);
		}

	    board.move(move);

	    System.out.println("\n");
	    System.out.println(board);
	    System.out.println();

	    if (board.isGameOver())
		{
		    if (board.isDraw())
			{
			    System.out.println("Game is a draw!\n\n");
			    return Board.DRAW;
			}
		    else if (board.hasWhiteWon())
			{
			    System.out.println("White won!\n\n");
			    return Board.WHITE;
			}
		    else
			{
			    System.out.println("Black won!\n\n");
			    return Board.BLACK;
			}
		}

	    if (player2.isHuman())
		{
		    System.out.println("Player 2: " + player2.getName() + " (Black) To Move");
		    move = getCoordFromConsole();
		}
	    else
		{
		    move = player2.makeMove(board);
		    System.out.println("Player 2: " + player2.getName() + " (Black) plays " + move);
		}

	    board.move(move);

	    System.out.println("\n");
	    System.out.println(board);
	    System.out.println();

	    if (board.isGameOver())
		{
		    if (board.isDraw())
			{
			    System.out.println("Game is a draw!\n\n");
			    return Board.DRAW;
			}
		    else if (board.hasWhiteWon())
			{
			    System.out.println("White won!\n\n");
			    return Board.WHITE;
			}
		    else
			{
			    System.out.println("Black won!\n\n");
			    return Board.BLACK;
			}
		}
	}
    }


	/** Play multiple games between the same two players.
	 *  Gives total number of wins, draws and loses at the end.
	 */
    public void playNGames(int n)
    {
		int gameResult;
		int whiteWins = 0, blackWins = 0, draws = 0;

	    for (int i=0; i<n; i++)
	    {
			gameResult = play();
			if (gameResult==Board.WHITE)
				whiteWins++;
			else if (gameResult==Board.BLACK)
				blackWins++;
			else
				draws++;
		}

		System.out.println("\nTotal games: " + n);
		System.out.println("Player 1: " + player1.getName() + " (White) wins:" + whiteWins);
		System.out.println("Player 2: " + player2.getName() + " (Black) wins:" + blackWins);
		System.out.println("Draws: " + draws + "\n\n");
	}



}