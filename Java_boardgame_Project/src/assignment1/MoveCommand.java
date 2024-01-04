package assignment1;

import assignment1.Exceptions.InvalidMoveException;

public class MoveCommand implements Command {
	
	Move move;
	Cell fromCell;
	Cell toCell;

	public MoveCommand(Move move, Cell fromCell, Cell toCell) {
		this.move = move;
		this.fromCell = fromCell;
		this.toCell = toCell;
	}

	@Override
	public void execute() {
		try {
			this.move.runMove();
		} catch (InvalidMoveException e) {
			;
		}
		
	}
	
	public void unexecute() {
		this.move.undoMove(move);
	}
}
