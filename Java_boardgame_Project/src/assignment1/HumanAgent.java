package assignment1;

import java.util.Scanner;

import assignment1.Exceptions.InvalidMoveException;

public class HumanAgent extends Agent {
	private final Scanner scanner = new Scanner(System.in);

    public HumanAgent(Board board) {
        super(board);

    }
    
    public Cell inputCell(String fromto)  { 	
    	    	
    	do {
    		System.out.println("Enter which piece col [ABCDE] and row [12345] you want to move " + fromto);
	    	try {
	    		
		    	String input = scanner.next();
		    	Coordinate coordinate = Utils.parseUserMove(input);	
		    	return board.getCell(coordinate);
		    } catch (InvalidMoveException e) {
			    e.printStackTrace();
			}	
    	} while (true);		  
    }
    
    private void getInput() {
    	while (!scanner.hasNext("[ABCDE][12345]")) {
			System.out.println("Invalid option. Enter '[ABCDE]' followed by '[12345]'\n"); 
		    scanner.next();    
		}
    }
    
  
    /**
     * Asks the human for a move with from and to coordinates and makes sure its valid.
     * Create a Move object with the chosen fromCell and toCell
     * @return the valid human inputted Move
     */
    @Override
    public Move getMove() {    
    	Cell from = inputCell("From:");
	    Cell to = inputCell("To:");
    	Move move = new Move(board,from,to, board.audiocontrol);
    	
    	if(board.isValidMove(move)) {
        	return move;
    	}
    		
    	return null;
    }
}

