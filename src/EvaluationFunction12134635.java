/**
 * 
 */

/**
 * @author 12134635
 *
 */
public class EvaluationFunction12134635 extends EvaluationFunction {
	//imports -> public static final double BEST_WHITE_VALUE =  1000000.0;
	//imports -> public static final double BEST_BLACK_VALUE = -1000000.0;
	
	/*
	 * This is the method called by evalMove in order to evaluate each 
	 * move that it makes. This evaluates the scores for each board position
	 * and determines whether to use that move by selecting the move with the
	 * highest "score".
	 */
	
	/* All of these variables are declared globally in order to speed up 
	 * the processing time and reduce memory usage because the methods
	 * may be called recursively. It also saves me sending and returning them
	 * to each method.
	 */
	private int type, mytype;
	private int r, c;
	private double score;
	private Board b;
	
	public double evaluate(Board board) {
		b = board;
		score = 0;
		if (b.hasWhiteWon())
		    return 1000; // gives WHITE 1000 points
		if (b.hasBlackWon())
			return -1000; //gives BLACK 1000 points
		
		mytype = 1 + ((b.getNumberOfMovesMade() - 1) % 2);	//Checks own colour
		
		for (r = 0; r < b.getSize(); r++) {
			for (c = 0; c < b.getSize(); c++) {
				if (!(b.isValidMove(r, c))) {
					//Checks if there is a double at that location
					if(b.maxInARowAtCoord(r, c) >= 2) {
						//If there is, then it gives that colour a point
						type = b.getCell(r, c);
						score += (3 - (2 * type));
						//(3 - 2 if white (+1), 3 - 4 if black (-1))
						
						//Checks for possible victory
						winningSpace();
						
					}
				}
			}
		}
		
		
		return score;
	}
	
	/*
	 * Checks to see if there can be a move made to win the game.
	 * If there is for the other team, it will block it.
	 */
	//No need to pass parameters because they are global variables
	private void winningSpace() {
		try {
			//Check if space is empty on this side
			if (b.isValidMove(r, c-1)){
				//If space is empty, check opposite side of space
				if (b.getCell(r, c-2) == type) {
					//If opposite side is also the same colour, makes sure it's a ##-# thing
					if (b.getCell(r, c+1) == type) {
						score += 25 * (3 - (2 * type));
						//Changes the score depending on the colour because
						//(3 - 2 if white (== +1), 3 - 4 if black (== -1))
						
						score += 5 * (mytype - type);
						//This diminishes the score if YOU are the one scoring
						//This gives priority to stopping the opponent
					}
				}
			}
		}
		catch (IndexOutOfBoundsException ex){
			//This is in case it goes off of the grid.
			//I don't know if I need anything in here once it's caught.
		}
		try {
			if (b.isValidMove(r, c+1)){
				if (b.getCell(r, c+2) == type) {
					if (b.getCell(r, c-1) == type) {
						score += 25 * (3 - (2 * type));
						score += 5 * (mytype - type);
					}
				}
			}
		}
		catch (IndexOutOfBoundsException ex) {}
		try {
			if (b.isValidMove(r-1, c)){
				if (b.getCell(r-2, c) == type) {
					if (b.getCell(r+1, c) == type) {
						score += 25 * (3 - (2 * type));
						score += 5 * (mytype - type);
					}
				}
			}
		}
		catch (IndexOutOfBoundsException ex) {}
		try {
			if (b.isValidMove(r+1, c)){
				if (b.getCell(r+2, c) == type) {
					if (b.getCell(r-1, c) == type) {
						score += 25 * (3 - (2 * type));
						score += 5 * (mytype - type);
					}
				}
			}
		}
		catch (IndexOutOfBoundsException ex) {}
		try {
			if (b.isValidMove(r-1, c+1)){
				if (b.getCell(r-2, c-1) == type) {
					if (b.getCell(r+1, c-1) == type) {
						score += 25 * (3 - (2 * type));
						score += 5 * (mytype - type);
					}
				}
			}
		}
		catch (IndexOutOfBoundsException ex) {}
		try {
			if (b.isValidMove(r+1, c-1)){
				if (b.getCell(r+2, c-2) == type) {
					if (b.getCell(r-1, c+1) == type) {
						score += 25 * (3 - (2 * type));
						score += 5 * (mytype - type);
					}
				}
			}
		}
		catch (IndexOutOfBoundsException ex) {}
	}
	
	
	
	
}
