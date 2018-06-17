
/**
 *  Very simple evaluation function implementation.
 *  Gives a large positive value if WHITE wins, and large
 *  negative value if BLACK wins. Otherwise returns a neutral
 *  0 value (even if the game finished in a draw).
 */
public class SimpleEvaluationFunction extends EvaluationFunction
{
    public double evaluate(Board board)
    {
	if (!board.isGameOver())
	    return 0;
	if (board.isDraw())
	    return 0;
	if (board.hasWhiteWon())
	    return BEST_WHITE_VALUE;
	return BEST_BLACK_VALUE; // BLACK has won.
    }
}

