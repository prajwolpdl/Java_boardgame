package assignment1;
import assignment1.Observer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import assignment1.Observable;

import assignment1.Exceptions.InvalidMoveException;
import assignment1.Piece.Type;

public class Move implements Observer {
    final Cell fromCell;
    final Cell toCell;
    private Board board;
    public boolean move_success;
    public Observable subject;
	private String waveFilePath;

    /**
     * Construct a new Move
     * A Move represents moving a Piece in fromCell to toCell
     * @param fromCell the Cell the Piece is in
     * @param toCell the Cell the Piece will move to 
     */
    public Move(Board board, final Cell fromCell, final Cell toCell, Observable subject) {
        this.fromCell = fromCell;
        this.toCell = toCell;
        this.board = board;
        this.subject = subject;
        this.subject.register(this);
    }
    @Override
	public void update() {
		// TODO Auto-generated method stub
		this.waveFilePath = 
				((AudioControl) this.subject).getNewFilePath();
		//System.out.println("Just got the new file");
	}

	public String getWaveFile() {
		return this.waveFilePath;
	}

	/**
	 * Plays an audiofile
	 */
	public void play() {
		try
		 {
		        Clip clip = AudioSystem.getClip();
		        clip.open(AudioSystem.getAudioInputStream(new File(this.waveFilePath)));
		        clip.start();
		    }
		    catch (Exception exc)
		    {
		        exc.printStackTrace(System.out);
		    }

	}

    /**
     * Create a copy of a Move
     * @param move a Move to make a copy of
     */
    public Move(Move move) {
        this.fromCell = new Cell(move.fromCell);
        this.toCell = new Cell(move.toCell);
        this.board = move.board;
    }
    
    public void runMove () throws InvalidMoveException {
    	int from_cell_row = this.fromCell.getCoordinate().row;
		int from_cell_col = this.fromCell.getCoordinate().col;
		int to_cell_row = this.toCell.getCoordinate().row;
		int to_cell_col = this.toCell.getCoordinate().col;
		Piece moving_piece = this.board.board[from_cell_row][from_cell_col].getPiece();
		if(this.board.board[from_cell_row][from_cell_col].hasPiece()) {
			if(moving_piece.canMoveOnto(this.board.board[to_cell_row][to_cell_col])) {
				this.move_success = true;
				if(this.board.getTurn() == Type.MUSKETEER && moving_piece.getType()== Type.MUSKETEER) {
					
					this.board.board[to_cell_row][to_cell_col].removePiece();
					board.board[to_cell_row][to_cell_col].setPiece(moving_piece);
					board.board[from_cell_row][from_cell_col].removePiece();
					this.board.setTurn(Type.GUARD);
				}
				else if(this.board.getTurn()== Type.GUARD && moving_piece.getType()== Type.GUARD){
					board.board[to_cell_row][to_cell_col].setPiece(moving_piece);
					board.board[from_cell_row][from_cell_col].removePiece();
					this.board.setTurn(Type.MUSKETEER);
				}
				else;
			}
			else; 
		}
		else;
	}

    
    public void undoMove(Move move) { // TODO
    	int from_cell_row = move.fromCell.getCoordinate().row;
		int from_cell_col = move.fromCell.getCoordinate().col;
		int to_cell_row = move.toCell.getCoordinate().row;
		int to_cell_col = move.toCell.getCoordinate().col;
		if (this.board.board[to_cell_row][to_cell_col].hasPiece()) {
			Piece moving_piece = this.board.board[to_cell_row][to_cell_col].getPiece();
			this.board.board[to_cell_row][to_cell_col].setPiece(null);
			this.board.board[from_cell_row][from_cell_col].setPiece(moving_piece);
			if(moving_piece.getType() == Type.MUSKETEER) {
				Piece deleted_piece = new Guard();
				this.board.board[to_cell_row][to_cell_col].setPiece(deleted_piece);
				this.board.setTurn(Type.MUSKETEER);
			}
			else this.board.setTurn(Type.GUARD);
		}else;
    }
    
    public Board getBoard() {
    	return this.board;
    }
   
    public Boolean isValidMove(Move move) { // TODO
    	Boolean next_to_check = isNextTo(move.fromCell.getCoordinate(), move.toCell.getCoordinate());
    	Boolean can_move_to_check;
    	Piece moving_piece = this.board.board[move.fromCell.getCoordinate().row][move.fromCell.getCoordinate().col].getPiece();
    	if(moving_piece == null) {
    		return false;
    	}
    	if (moving_piece.canMoveOnto(board.board[move.toCell.getCoordinate().row][move.toCell.getCoordinate().col])){
    		can_move_to_check = true;
    	}
    	else can_move_to_check = false;
    	
    	if (next_to_check == true && can_move_to_check == true) {
    		return true;
    	}
    	else return false;
    }
    /**
     * Checks if the given coordinates are either up, down, left or right from each other
     * @param fromCellCord the coordinate of fromCell
     * @param toCellCord the coordinate of toCell
     * @return     True, if the fromcell coordinate  is next to toCell coordinate
     */
    private Boolean isNextTo(Coordinate fromCellCord, Coordinate toCellCord) {
    	if (fromCellCord.row+1 == toCellCord.row && fromCellCord.col == toCellCord.col ||
    			fromCellCord.row-1 == toCellCord.row && fromCellCord.col == toCellCord.col||
    			fromCellCord.row == toCellCord.row && fromCellCord.col+1 == toCellCord.col ||
    			fromCellCord.row == toCellCord.row && fromCellCord.col-1 == toCellCord.col) {
    		
    		return true;
    		
    	}
    	else return false;
    }

    @Override
    public String toString() {
        return String.format("(%s) -> (%s)", fromCell.getCoordinate(), toCell.getCoordinate());
    }
}
