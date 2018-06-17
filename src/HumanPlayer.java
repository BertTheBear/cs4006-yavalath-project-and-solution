
/** Represents Human Players. Essentially just a stub
 *  class. The isHuman method is implemented to always
 *  return false so that Game classes can tell that
 *  human input is required rather than a call to
 *  the makeMove method.
 */
public class HumanPlayer extends Player
{

    public HumanPlayer(String aName)
    {
	super(aName);
    }

    public boolean isHuman()
    {
	return true;
    }

    public HexCoord makeMove(Board b)
    {
	return null; // does nothing because should never be called
    }

}
