/**
 * Stores a hexagonal grid coordinate.
 */
public class HexCoord
{
    protected int row;
    protected int col;

    public HexCoord(int r, int c)
    {
		row=r;
		col=c;
    }

    /** Copy constructor. */
    public HexCoord(HexCoord anOther)
    {
		this.row = anOther.row;
		this.col = anOther.col;
    }

    /** Row position getter. */
    public int getRow()
    {
		return row;
    }

    /** Column getter. */
    public int getCol()
    {
		return col;
    }

	/** Row position setter. */
    public void setRow(int r)
    {
		row = r;
    }

	/** Col position setter. */
    public void setCol(int c)
    {
		col = c;
    }



	/** Move in a West (W) direction. */
    public void moveW()
    {
	col--;
    }

	/** Move in an East (E) direction. */
    public void moveE()
    {
	col++;
    }

    /** Move in a North-East (NE) direction. */
    public void moveNW()
    {
	row--;
    }

    public void moveSE()
    {
	row++;
    }

    public void moveNE()
    {
	row--;
	col++;
    }

    public void moveSW()
    {
	row++;
	col--;
    }



    /** Gives a simple textual representation of this coordinate. */
    public String toString()
    {
	return "(" + row + ", " + col + ")";
    }


}

