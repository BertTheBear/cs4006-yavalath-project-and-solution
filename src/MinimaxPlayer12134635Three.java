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

public class MinimaxPlayer12134635Three extends AIPlayer {
	
	// Rather than declare on each iteration of each method, 
	// I'll declare all variables here.
	
	//From makeMove()
	private HexCoord bestMove; //Returned variable
	private ArrayList<HexCoord> possibleMoves = new ArrayList<HexCoord>();
	private double bestScore;
	//Score is also used in makeMove()
	private HexCoord move;
	private int movesMade;
	
	//From evalMove()
	private double score; //Returned variable

	/*
	 * Constructor
	 */
	public MinimaxPlayer12134635Three(String aName, EvaluationFunction ef) {
		super(aName, ef);
		// Calls the constructor of the superclass, which sets ef as evalFunc
		//This also calls the constructor of THAT superclass which sets aName as name
		
		if (name.matches("")) {
			name = "Andy!";
		}
		//If he is not given a name then
		//I shall name him Andy!
		movesMade = 0;
	}
	
	
	
	
	
	
	
	
	
	public HexCoord makeMove(Board b) {
		//Copied from OnePlyPlayer
		possibleMoves = b.getListOfPossibleMoves(); // get all available valid moves
		if (possibleMoves.isEmpty())
		    throw new IllegalYavalathMove("Can't move. Game over.");
		
		if (b.getNumberOfMovesMade() <= 1) {
			bestMove = new HexCoord(b.getSize() / 2, b.getSize() /2);
			return bestMove;
		}
		//if (movesMade % 3 == 0)
			Collections.shuffle(possibleMoves);
		
		bestMove = possibleMoves.get(0);
		bestScore = evalMove(b, bestMove);
		
		for (int i = 1; i < possibleMoves.size(); i++)
	    {
			move = possibleMoves.get(i); // get next move
			score = evalMove(b, move); // get its score
			/*
			 * The easiest way to assess the moves is to give each board state
			 * an integer value. This way the current colour does not matter,
			 * and it must simply be decided below whether to use a high score
			 * or a low score depending on the colour.
			 */
			if (colour==Board.WHITE) { // for white, larger scores are better
				if (score > bestScore) // if better score found, update best score
				{
				    bestScore = score;
				    bestMove = move;
				}
			}
			else { // for black, smaller scores are better
			    if (score < bestScore)
				{
				    bestScore = score;
				    bestMove = move;
				}
			}
	    }
		movesMade++;
		return bestMove;
	}
	
	
	
	
	
	
	
	/*
	 * This method is called a number of times depending on the amount of possible
	 * moves remaining. It makes a move and then evaluates the score of the move.
	 * It then "un-does" the move so that the board is back to normal.
	 * It then returns the score of the move and this is then used to decide which 
	 * move is "better" score-wise. 
	 * 
	 * This is copied directly from OnePlyPlayer.java
	 */
	private double evalMove(Board b, HexCoord aMove) {
		
		b.move(aMove); // make the move
		score = evalFunc.evaluate(b); // get its score using the evaluation function
		b.unmove(); // restore the original board

		return score;
	}
	
	/*
	 * I would like to take a moment to mention a flaw in the program.
	 * 
	 * Because of the encapsulation used (Protected) it is possible for my 
	 * class to perform an action such as:
	 *  
	 * b.winner = colour;
	 * b.gameOver = true;
	 * 
	 * and set the winner of the board to be myself, and thus the game would
	 * end with me as the winner. This is of course cheating and I will not.
	 * 
	 * I just wanted to mention that I could have done this. 
	 * 
	 */
}
