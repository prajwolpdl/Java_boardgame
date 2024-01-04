package assignment1;

import java.util.ArrayList;
import java.util.List;

public class MoveOperator {
	Stack<Command> move_commands;

	public MoveOperator() {
		move_commands = new Stack<Command>();
	}
	
	public void runMove(MoveCommand command) {
		if(command.move.isValidMove(command.move)) {
			command.execute();
			this.move_commands.push(command);
	}
	}
	
	public void undoMove() {
		Command m = this.move_commands.pop();
		m.unexecute();
	}

}
