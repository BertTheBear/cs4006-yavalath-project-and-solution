/**
 * This is the Minimax player that will attempt to win the game.
 * I am awful at describing my classes.
 * You know what this is.
 */

/**
 * @author 12134635
 * (That's me)
 */
import java.util.ArrayList;
import java.util.Collections;

public class MinimaxPlayer12134635ALT extends AIPlayer {

	// Rather than declare on each iteration of each method,
	// I'll declare all variables here.

	// From makeMove()
	private HexCoord bestMove; // Returned variable
	private ArrayList<HexCoord> possibleMoves = new ArrayList<HexCoord>();
	private double bestScore;
	// Score is also used in makeMove()
	private HexCoord move;
	private int movesMade;

	// From evalMove()
	private double score; // Returned variable

	// I don't know what I'm doing
	private double futureScore, bestFutureScore;
	private int ply;

	public void setPly(int number) {
		ply = number;
	}

	public int getPly() {
		return ply;
	}

	/*
	 * Constructor
	 */
	public MinimaxPlayer12134635ALT(String aName, EvaluationFunction ef) {
		super(aName, ef);
		// Calls the constructor of the superclass, which sets ef as evalFunc
		// This also calls the constructor of THAT superclass which sets aName
		// as name

		if (name.matches("")) {
			name = "Andy!";
		}
		// If he is not given a name then
		// I shall name him Andy!
		movesMade = 0;
		ply = 1;
	}

	public MinimaxPlayer12134635ALT(String aName, EvaluationFunction ef,
			int setPly) {
		super(aName, ef);
		// Calls the constructor of the superclass, which sets ef as evalFunc
		// This also calls the constructor of THAT superclass which sets aName
		// as name

		if (name.matches("")) {
			name = "Andy!";
		}
		// If he is not given a name then
		// I shall name him Andy!
		movesMade = 0;
		ply = setPly;
	}

	public HexCoord makeMove(Board b) {
		// Copied from OnePlyPlayer
		possibleMoves = b.getListOfPossibleMoves(); // get all available valid
													// moves
		if (possibleMoves.isEmpty())
			throw new IllegalYavalathMove("Can't move. Game over.");

		if (movesMade % 3 == 0)
			Collections.shuffle(possibleMoves);

		bestMove = possibleMoves.get(0);
		bestScore = evalMove(b, bestMove);
		bestFutureScore = evalManyMove(b, bestMove);

		for (int i = 1; i < possibleMoves.size(); i++) {
			move = possibleMoves.get(i); // get next move
			score = evalMove(b, move); // get its score
			futureScore = evalManyMove(b, move);
			/*
			 * The easiest way to assess the moves is to give each board state
			 * an integer value. This way the current colour does not matter,
			 * and it must simply be decided below whether to use a high score
			 * or a low score depending on the colour.
			 */
			if (colour == Board.WHITE) { // for white, larger scores are better
				if (score > bestScore) // if better score found, update best
										// score
				{
					bestScore = score;
					bestMove = move;
					bestFutureScore = futureScore;
				} else if (score == bestScore) {
					if (futureScore > bestFutureScore) // if better score found,
														// update best score
					{
						bestScore = score;
						bestMove = move;
						bestFutureScore = futureScore;
					}
				}
			} else { // for black, smaller scores are better
				if (score < bestScore) {
					bestScore = score;
					bestMove = move;
					bestFutureScore = futureScore;
				} else if (score == bestScore) {
					if (futureScore < bestFutureScore) // if better score found,
														// update best score
					{
						bestScore = score;
						bestMove = move;
						bestFutureScore = futureScore;
					}
				}
			}
		}
		movesMade++;
		return bestMove;
	}

	/*
	 * This method is called a number of times depending on the amount of
	 * possible moves remaining. It makes a move and then evaluates the score of
	 * the move. It then "un-does" the move so that the board is back to normal.
	 * It then returns the score of the move and this is then used to decide
	 * which move is "better" score-wise.
	 * 
	 * This is copied directly from OnePlyPlayer.java
	 */
	private double evalMove(Board b, HexCoord aMove) {

		b.move(aMove); // make the move
		score = evalFunc.evaluate(b); // get its score using the evaluation
										// function
		b.unmove(); // restore the original board

		return score;
	}

	private double evalManyMove(Board b, HexCoord aMove) {

		if (ply <= 1)
			return 0;
		// Temp variables
		HexCoord tempMove;
		int count = 0;
		boolean loop = true;

		// Create two players to play the next few moves.
		MinimaxPlayer12134635ALT whiteCopy = new MinimaxPlayer12134635ALT(
				"WhiteCopy", evalFunc);
		MinimaxPlayer12134635ALT blackCopy = new MinimaxPlayer12134635ALT(
				"BlackCopy", evalFunc);

		// Set correct colours
		blackCopy.setColour(Board.BLACK);
		whiteCopy.setColour(Board.WHITE);

		// Play the necessary moves.
		b.move(aMove);
		for (int x = 0; x < ply && loop; x++) {
			if (b.getNumberOfMovesMade() % 2 == 1) { // checks whose turn it is
				try { //To catch illegal moves. This will also cause the loop to end.
					tempMove = blackCopy.makeMove(b);
					b.move(tempMove);
					count++;
				} catch (IllegalYavalathMove ex) {
					loop = false;				//This will end the loop
					//I could have the loop keep running, but it would just fail on each loop anyway
				}
			} else {
				try {
					tempMove = whiteCopy.makeMove(b);
					b.move(tempMove);
					count++;
				} catch (IllegalYavalathMove ex) {
					loop = false;				
				}
			}
		}

		futureScore = evalFunc.evaluate(b);
		for (int x = 0; x < count + 1; x++) {
			b.unmove();
		}

		return futureScore;
	}

	/*
	 * I would like to take a moment to mention a flaw in the program.
	 * 
	 * Because of the encapsulation used (Protected) it is possible for my class
	 * to perform an action such as:
	 * 
	 * b.winner = colour; b.gameOver = true;
	 * 
	 * and set the winner of the board to be myself, and thus the game would end
	 * with me as the winner. This is of course cheating and I will not.
	 * 
	 * I just wanted to mention that I could have done this.
	 */
}
