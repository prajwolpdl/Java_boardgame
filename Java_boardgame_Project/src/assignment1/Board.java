package assignment1;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Board extends Prototype{
    public int size = 5;

    // 2D Array of Cells for representation of the game board
    public final Cell[][] board = new Cell[size][size];

    private Piece.Type turn;
    private Piece.Type winner;
    public AudioControl audiocontrol = new AudioControl();
    
    

    /**
     * Create a Board with the current player turn set.
     */
    public Board() {
        this.loadBoard("Boards/Starter.txt");
    }

    /**
     * Create a Board with the current player turn set and a specified board.
     * @param boardFilePath The path to the board file to import (e.g. "Boards/Starter.txt")
     */
    public Board(String boardFilePath) {
        this.loadBoard(boardFilePath);
    }

    /**
     * Creates a Board copy of the given board.
     * @param board Board to copy
     */
    public Board(Board board) {
        this.size = board.size;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                this.board[row][col] = new Cell(board.board[row][col]);
            }
        }
        this.turn = board.turn;
        this.winner = board.winner;
    }

    /**
     * @return the Piece.Type (Muskeeteer or Guard) of the current turn
     */
    public Piece.Type getTurn() {
        return turn;
    }
    
	/**
	 * 
	 * 
	 * set turn to specified Piece.Type
	 */
    
    public void setTurn(Piece.Type type) {  	
    	this.turn = type;       
    }

    /**
     * Get the cell located on the board at the given coordinate.
     * @param coordinate Coordinate to find the cell
     * @return Cell that is located at the given coordinate
     */
    public Cell getCell(Coordinate coordinate) { 
    	if ( coordinate.row >= this.size || coordinate.col >= this.size )
    		return null;
    	return this.board[coordinate.row][coordinate.col];
    }

    /**
     * @return the game winner Piece.Type (Muskeeteer or Guard) if there is one otherwise null
     */
    public Piece.Type getWinner() {
        return winner;
    }
    
    /**
     * 
     * yes
     */
    
	public void setWinner(String type) {    	
		this.winner = (type == "Musketeer"? Piece.Type.MUSKETEER :  Piece.Type.GUARD);
	 }
	
    /**
     * Gets all the Musketeer cells on the board.
     * @return List of cells
     */
    public List<Cell> getMusketeerCells() {
    		
    	List<Cell> myList = new ArrayList<Cell>();
    	for(int row = 0; row < this.size ; row++) {
    		for(int col = 0; col < this.size; col++) {	
    			Cell cell = this.getCell(new Coordinate(row,col));    						
    			if (cell.hasPiece() &&  cell.getPiece().getType() == Piece.Type.MUSKETEER){      			
    				myList.add(board[row][col]);
        		}		
    		}
    	}
		return myList;
    }
    	
    	

    /**
     * Gets all the Guard cells on the board.
     * @return List of cells
     */
    public List<Cell> getGuardCells() { 
    	
    	List<Cell> myList = new ArrayList<Cell>();
    	for(int row = 0; row < this.size ; row++) {
    		for(int col = 0; col < this.size; col++) {	
    			Cell cell = this.getCell(new Coordinate(row,col));    						
    			if (cell.hasPiece() &&  cell.getPiece().getType() == Piece.Type.GUARD){      			
    				myList.add(board[row][col]);
        		}		
    		}
    	}
    	return myList;
    }

    /**
     * Executes the given move on the board and changes turns at the end of the method.
     * @param move a valid move
     */
    public void move(Move move) {
  
    	Cell from = move.fromCell;	
    	Cell to = move.toCell;
    	this.audiocontrol.setNewFile("Sounds/mixkit-unlock-game-notification-253.wav");
    	move.subject = audiocontrol;
    	move.play();
    	Piece current_piece = from.getPiece();
    	to.setPiece(current_piece);
    	from.setPiece(null);
    	switchPlayer(); 	
    }
    
    
    /**
     * Undo the move given.
     * @param move Copy of a move that was done and needs to be undone. The move copy has the correct piece info in the
     *             from and to cell fields. Changes turns at the end of the method.
     */
    public void undoMove(Move move) { 
    	
    	int fromrow = move.fromCell.getCoordinate().row;
    	int fromcol = move.fromCell.getCoordinate().col;
    	
    	int torow = move.toCell.getCoordinate().row;
    	int tocol = move.toCell.getCoordinate().col;
    	
    	board[fromrow][fromcol] = move.fromCell;
    	board[torow][tocol] = move.toCell;
    	
    	
    	
    	switchPlayer();
    	this.audiocontrol.setNewFile("Sounds/mixkit-unlock-game-notification-253.wav");
    	move.subject = audiocontrol;
    	
    }
    
    private void switchPlayer() {
    	if (this.getTurn() == Piece.Type.GUARD)
    		this.setTurn(Piece.Type.MUSKETEER);
    	else if (this.getTurn() == Piece.Type.MUSKETEER)
		this.setTurn(Piece.Type.GUARD);
    }
    
    /**
     * Checks if the given move is valid. Things to check:
     * (1) the toCell is next to the fromCell
     * (2) the fromCell piece can move onto the toCell piece.
     * @param move a move
     * @return     True, if the move is valid, false otherwise
     */
    public Boolean isValidMove(Move move) {
  
    	Coordinate to = move.toCell.getCoordinate();
    	Coordinate from = move.fromCell.getCoordinate();
    	
    	//check if the from cell has a piece
    	if (!move.fromCell.hasPiece())
    		return false;
    	
    	// check if it is the same as turn type
    	Piece fromPiece = move.fromCell.getPiece();
    	if (!(fromPiece.getType() == this.turn))
    		return false;
    	
    	// check if adjacent
    	boolean isadjacent = false;
    	if ( (to.row - 1 == from.row || to.row + 1 == from.row) && to.col == from.col) {
    		
    	
    	
    		
    	
    		isadjacent = true;
    	}
    	else if ((to.col - 1 == from.col || to.col + 1 == from.col) && to.row == from.row)
    		isadjacent = true;
    	
    	if (!isadjacent)
    		return false;
    	
    	// check if can move based on the rules
    	return fromPiece.canMoveOnto(move.toCell);
    
    	
    }

    /**
     * Get all the possible cells that have pieces that can be moved this turn.
     * @return Cells that can be moved from the given cells
     */
    public List<Cell> getPossibleCells() {
    	List<Cell> myList = new ArrayList<Cell>();
    	if (this.turn == Piece.Type.MUSKETEER)
    		myList = this.getMusketeerCells();
    	else if (this.turn == Piece.Type.GUARD)
    		myList = this.getGuardCells();	
    	return myList;
    }

    /**
     * Get all the possible cell destinations that is possible to move to from the fromCell.
     * @param fromCell The cell that has the piece that is going to be moved
     * @return List of cells that are possible to get to
     */
    public List<Cell> getPossibleDestinations(Cell fromCell) { 
    	
    	Coordinate current = fromCell.getCoordinate();
    	List<Cell> adjacentlist = new ArrayList<Cell>();
    	if (!(current.row == 0)) {
    		Cell cell = getCell(new Coordinate(current.row - 1, current.col));
    		adjacentlist.add(cell);
    	}
    	if (!(current.row == size -1)) {
    		Cell cell = getCell(new Coordinate(current.row + 1, current.col));
    		adjacentlist.add(cell);
    	}
    	if (!(current.col == 0)) {
    		Cell cell = getCell(new Coordinate(current.row , current.col - 1));
    		adjacentlist.add(cell);
    	}
    	if (!(current.col == size -1)) {
    		Cell cell = getCell(new Coordinate(current.row , current.col + 1));
    		adjacentlist.add(cell);
    	}    	
    	return adjacentlist;
    }

    /**
     * Get all the possible moves that can be made this turn.
     * @return List of moves that can be made this turn
     */
    public List<Move> getPossibleMoves() {
    	
    	List<Move> moves = new ArrayList<Move>();
    	List<Cell> sources = this.getPossibleCells();   	
    	for (Cell source : sources) {
    		List<Cell> destinations = getPossibleDestinations(source);
    		for (Cell destination : destinations) {
    			Move mv = new Move(this,source,destination, audiocontrol);
    			if (isValidMove(mv)) {
    				moves.add(mv);
    			}
    		}
    	}   	
    	return moves;
    }
    
    /**yes
     * Checks if the game is over for the musketeers.
     * @return True, if the game is over, false otherwise.
     */
    public boolean isGameOverMusketeers() {
    	
    	List<Cell> musketeers = this.getMusketeerCells();
    	List<Integer> rowlist = new ArrayList<>();
    	List<Integer> collist = new ArrayList<>();
    	for (Cell musteteer :  musketeers) {
    		rowlist.add(musteteer.getCoordinate().row);
    		collist.add(musteteer.getCoordinate().col);
    	}
    	
    	HashSet<Integer> hashsetrows = new HashSet<Integer>(rowlist);
    	HashSet<Integer> hashsetcols = new HashSet<Integer>(collist);
    	return (hashsetrows.size() == 1 || hashsetcols.size() == 1);
    	
    }
    
    /**
     * Checks if the game is over for the guards.
     * @return True, if the game is over, false otherwise.
     */
    public boolean isGameOverGuards() {   	
    	List <Move> moves = getPossibleMoves();
    	return moves.size() == 0;
    }


    /**
     * Checks if the game is over and sets the winner if there is one.
     * @return True, if the game is over, false otherwise.
     */
    public boolean isGameOver() { 
        if(isGameOverGuards()){
        	this.setWinner("Musketeer");
        	return true;
        }
        else if(isGameOverMusketeers()){
        	this.setWinner("Guard");
        	return true;
        }
        return false;
    }

    /**
     * Saves the current board state to the boards directory
     */
    public void saveBoard() {
        String filePath = String.format("boards/%s.txt",
                new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
        File file = new File(filePath);

        try {
            file.createNewFile();
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            writer.write(turn.getType() + "\n");
            for (Cell[] row: board) {
                StringBuilder line = new StringBuilder();
                for (Cell cell: row) {
                    if (cell.getPiece() != null) {
                        line.append(cell.getPiece().getSymbol());
                    } else {
                        line.append("_");
                    }
                    line.append(" ");
                }
                writer.write(line.toString().strip() + "\n");
            }
            writer.close();
            System.out.printf("Saved board to %s.\n", filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("Failed to save board to %s.\n", filePath);
        }
    }
	
    public void saveBoard(String path) {
        File file = new File(path);

        try {
            file.createNewFile();
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            writer.write(turn.getType() + "\n");
            for (Cell[] row: board) {
                StringBuilder line = new StringBuilder();
                for (Cell cell: row) {
                    if (cell.getPiece() != null) {
                        line.append(cell.getPiece().getSymbol());
                    } else {
                        line.append("_");
                    }
                    line.append(" ");
                }
                writer.write(line.toString().strip() + "\n");
            }
            writer.close();
            System.out.printf("Saved board to %s.\n", path);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("Failed to save board to %s.\n", path);
        }
    }

    @Override
    public String toString() {
        StringBuilder boardStr = new StringBuilder("  | A B C D E\n");
        boardStr.append("--+----------\n");
        for (int i = 0; i < size; i++) {
            boardStr.append(i + 1).append(" | ");
            for (int j = 0; j < size; j++) {
                Cell cell = board[i][j];
                boardStr.append(cell).append(" ");
            }
            boardStr.append("\n");
        }
        return boardStr.toString();
    }

    /**
     * Loads a board file from a file path.
     * @param filePath The path to the board file to load (e.g. "Boards/Starter.txt")
     */
    private void loadBoard(String filePath) {
        File file = new File(filePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.printf("File at %s not found.", filePath);
            System.exit(1);
        }

        turn = Piece.Type.valueOf(scanner.nextLine().toUpperCase());

        int row = 0, col = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] pieces = line.trim().split(" ");
            for (String piece: pieces) {
                Cell cell = new Cell(new Coordinate(row, col));
                switch (piece) {
                    case "O" -> cell.setPiece(new Guard());
                    case "X" -> cell.setPiece(new Musketeer());
                    default -> cell.setPiece(null);
                }
                this.board[row][col] = cell;
                col += 1;
            }
            col = 0;
            row += 1;
        }
        scanner.close();
        System.out.printf("Loaded board from %s.\n", filePath);
    }
    
    @Override
	public Board clone() {
		return new Board(this);
	}
}
