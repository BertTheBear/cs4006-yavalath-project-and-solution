/** Your static evaluation function classes should inherit
 *  from this abstract class.
 */
public abstract class EvaluationFunction
{
    /** Best possible value for WHITE, i.e. for white game win */
    public static final double BEST_WHITE_VALUE =  1000000.0;

    /** Best possible value for BLACK, i.e. black game win */
    public static final double BEST_BLACK_VALUE = -1000000.0;

	/** Contains the static evalatution function implementation.
	 *  All concrete subclasses must implement.
	 *  Should return higher values for board positions better
	 *  for WHITE.
	 *  Should return lower (more negative values) for board
	 *  positions better for BLACK.
	 */
    public abstract double evaluate(Board b);
}

