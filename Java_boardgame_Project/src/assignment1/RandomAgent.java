package assignment1;
import java.util.List;
import java.util.Random;

public class RandomAgent extends Agent {

    public RandomAgent(Board board) {
        super(board);
    }

    /**
     * Gets a valid random move the RandomAgent can do.
     * @return a valid Move that the RandomAgent can perform on the Board
     */
    @Override
    public Move getMove() {   	
        List<Move> moves = board.getPossibleMoves();
        if ( moves.size() > 0) {
        	Random rand = new Random();
        	int number = rand.nextInt(moves.size());
        	return moves.get(number);
        }
        return null;       
    }
}
