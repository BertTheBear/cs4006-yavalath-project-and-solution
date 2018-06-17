/** Abstract superclass for non-human AI players.
 *  The constructor also requires an evaluation
 *  function instance (whether used or not).
 */
public abstract class AIPlayer extends Player
{

    protected EvaluationFunction evalFunc;

    public EvaluationFunction getEvaluationFunction()
    {
		return evalFunc;
    }

    public void setEvaluationFunction(EvaluationFunction ef)
    {
		evalFunc = ef;
    }


    public AIPlayer(String aName, EvaluationFunction ef)
    {
		super(aName);
		setEvaluationFunction(ef);
    }

    public boolean isHuman()
    {
		return false;
    }

	/** Any concrete subclass will have to implement this. */
    public abstract HexCoord makeMove(Board b);

}

