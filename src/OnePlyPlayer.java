import java.util.ArrayList;
import java.util.Collections;

/**
 * This player looks just one move (one ply) ahead.
 * All possible moves are scored using a supplied
 * evaluation function. The move with highest
 * evaluation score is chosen.
 */
public class OnePlyPlayer extends AIPlayer
{

    public OnePlyPlayer(String aName, EvaluationFunction ef)
    {
	super(aName,ef);
    }


    /**
     * Gets the evaluation function score for the board
     * after a given move is made.
     */
    private double evalMove(Board b, HexCoord aMove)
    {
	double score;

	b.move(aMove); // make the move
	score = evalFunc.evaluate(b); // get its score using the evaluation function
	b.unmove(); // restore the original board

	return score;
    }


	/** Gives the move with highest score for the given evaluation function. */
    public HexCoord makeMove(Board b)
    {

	ArrayList<HexCoord> possibleMoves = b.getListOfPossibleMoves(); // get all available valid moves

	if (possibleMoves.isEmpty())
	    throw new IllegalYavalathMove("can't move, game over");

	Collections.shuffle(possibleMoves); // randomly shuffle list of moves, ensures if have several equally
	                                    // best moves, we pick one of these at random

    // look for move with best one-play evaluation function score

    // get score for first move in list of possible moves
	int bestMoveIndex = 0;
	HexCoord bestMove = possibleMoves.get(0);
	double bestScore = evalMove(b,bestMove);

    // iterate through remaining moves in list
	for (int i=1; i<possibleMoves.size(); i++)
	    {
		HexCoord move = possibleMoves.get(i); // get next move
		double score = evalMove(b,move); // get its score

        // which score is better depends on player colour
		if (colour==Board.WHITE) { // for white, larger scores are better
		    if (score > bestScore) // if better score found, update best score
			{
			    bestScore = score;
			    bestMove = move;
			    bestMoveIndex = i;
			}
		}
		else { // for black, smaller scores are better
		    if (score < bestScore)
			{
			    bestScore = score;
			    bestMove = move;
			    bestMoveIndex = i;
			}
		}
	    }

	return bestMove; // return move found with best evaluation score
    }


}


