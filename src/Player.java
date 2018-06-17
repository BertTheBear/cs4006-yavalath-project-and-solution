
/** Abstract base player class. */
public abstract class Player
{

    protected String name;

    public String getName()
    {
	return name;
    }


    public Player(String aName)
    {
	name = aName;
    }

	/** Used to distinguish between Human players,
	 *  where move coordinates must be gotten from
	 *  the console or other input device, and AI Players
	 *  which automatically calculate their own moves. */
    public abstract boolean isHuman();

	/** All player subclasses must implement this method.
	 *  For Human player classes the implementation should
	 *  just be a stub, i.e. return null, because it will never
	 *  be called.
	 *  However, for AI players, this method should calculate
	 *  and return the next move to make given the board b.
	 */
    public abstract HexCoord makeMove(Board b);

    protected int colour;

    public int getColour()
    {
	return colour;
    }

    public void setColour(int aColour)
    {
	if (aColour!=Board.WHITE && aColour!=Board.BLACK)
	    throw new IllegalArgumentException("player colour can only be WHITE(1) or BLACK(2)");
	colour = aColour;
    }


}

