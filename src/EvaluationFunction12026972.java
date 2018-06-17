public class EvaluationFunction12026972 extends EvaluationFunction
{


@Override
public double evaluate(Board b) {

int score = 0;
int mytype = 1 + ((b.getNumberOfMovesMade() - 1) % 2);
HexCoord yourMove = b.moveArray[(b.numberOfMovesMade - 1)];
int r = yourMove.row;
int c = yourMove.col;
try {
            //Check if space is empty on this side
//west
            if (b.isValidMove(r, c-1)){
            int type = b.board[r][c-1];
                //If space is empty, check opposite side of space
                if (b.getCell(r, c-2) == type) {
                    //If opposite side is also the same colour, makes sure it's a ##-# thing
                    if (b.getCell(r, c+1) == type) {
                        score += 25 * (3 - (2 * type));
                        //Changes the score depending on the colour because
                        //(3 - 2 if white (== +1), 3 - 4 if black (== -1))
                         
                        score += 5 * (mytype - type);
                        //if mytype==2(Black) -> 2 - 1 = 1 -> Will add  to your score (Bad for black)
                        //if mytype==1(White) -> 1 - 2 = -1 -> Will take from your score (Bad for white)
                        //Otherwise if it is your line, it will be 0 and give you a set score of 25
                        //This diminishes your score if they are the one scoring
                        //This gives priority to stopping the opponent
                    }
                }
            }
            // east
            if (b.isValidMove(r, c+1)){
            int type = b.board[r][c+1];
            if (b.getCell(r, c+2) == type) {
            if (b.getCell(r, c-1) == type) {
                         score += 25 * (3 - (2 * type));
                         score += 5 * (mytype - type);
            }
            }
            }
            // NW
            if (b.isValidMove(r-1, c)){
            int type = b.board[r-1][c];
            if (b.getCell(r-2, c) == type) {
            if (b.getCell(r+1, c) == type) {
                         score += 25 * (3 - (2 * type));
                         score += 5 * (mytype - type);
            }
            }
            }
            // SE
            if (b.isValidMove(r+1, c)){
            int type = b.board[r+1][c];
            if (b.getCell(r+2, c) == type) {
            if (b.getCell(r-1, c) == type) {
                         score += 25 * (3 - (2 * type));
                         score += 5 * (mytype - type);
            }
            }
            }
            // NE row--; col++;
            if (b.isValidMove(r-1, c+1)){
            int type = b.board[r-1][c+1];
            if (b.getCell(r-2, c+2) == type) {
            if (b.getCell(r+1, c-1) == type) {
                         score += 25 * (3 - (2 * type));
                         score += 5 * (mytype - type);
            }
            }
            }
           // SW row++; col--;
            if (b.isValidMove(r+1, c-1)){
            int type = b.board[r+1][c-1];
            if (b.getCell(r+2, c-2) == type) {
            if (b.getCell(r-1, c+1) == type) {
                         score += 25 * (3 - (2 * type));
                         score += 5 * (mytype - type);
            }
            }
            }
            
        }
        catch (IndexOutOfBoundsException ex){
            //This is in case it goes off of the grid.
            //I don't know if I need anything in here once it's caught.
            //It works fine like this
        }

return score;

}
}