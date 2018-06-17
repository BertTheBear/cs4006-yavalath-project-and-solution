
/** Sets up and plays some games. */
public class Yavalath
{

    public static void main(String [] args)
    {

	SimpleEvaluationFunction sef = new SimpleEvaluationFunction();
	//Mine V
	EvaluationFunction12134635 mef = new EvaluationFunction12134635();
	
	HumanPlayer humanP = new HumanPlayer("You");
	//RandomPlayer randP = new RandomPlayer("RandomP",sef);
	OnePlyPlayer oneP = new OnePlyPlayer("OnePlyP",sef);
	//Mine V
	MinimaxPlayer12134635 thisMe = new MinimaxPlayer12134635("Regular", sef);
	MinimaxPlayer12134635ALT otherMe = new MinimaxPlayer12134635ALT("Alternate", mef);
	MinimaxPlayer12134635Three ThreeMe = new MinimaxPlayer12134635Three("33", mef);
	MinimaxPlayer12134635ALT otherOtherMe = new MinimaxPlayer12134635ALT("Minimax4", mef, 10);

	//Mine vs. AI
	/*Game game3 = new Game(7, humanP, oneP);
	game3.playNGames(20);/**/
	Game game4 = new Game(7, otherOtherMe, ThreeMe);
	game4.playNGames(20);/**/

    }
}
