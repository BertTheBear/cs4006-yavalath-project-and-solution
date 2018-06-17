/**
 * New unchecked exception class to be thrown in the
 * event of invalid Yavalath moves. Because it is a
 * subclass of RuntimeException, no explicit try or
 * catch clauses are needed.
 */
public class IllegalYavalathMove extends RuntimeException
{
    public IllegalYavalathMove() {}
    public IllegalYavalathMove(String message)
    {
	super(message);
    }

    public IllegalYavalathMove(Throwable tr)
    {
	super(tr);
    }

    public IllegalYavalathMove(String message, Throwable tr)
    {
	super(message, tr);
    }


}

