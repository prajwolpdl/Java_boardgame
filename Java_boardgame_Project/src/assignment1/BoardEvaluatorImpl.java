package assignment1;

import java.util.List;

public class BoardEvaluatorImpl implements BoardEvaluator {

    /**
     * Calculates a score for the given Board
     * A higher score means the Musketeer is winning
     * A lower score means the Guard is winning
     * Add heuristics to generate a score given a Board
     * @param board Board to calculate the score of
     * @return int Score of the given Board
     */
    @Override
    public int evaluateBoard(Board board) { 
    	
        int total = board.size * board.size;   
        List<Cell> muskeeters = board.getMusketeerCells();
        List<Cell> guards = board.getGuardCells();
        int empty = total - muskeeters.size() - guards.size();
        
        return empty - board.getGuardCells().size();    
      
    }
}
