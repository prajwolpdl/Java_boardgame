package assignment1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class IteratorBoard implements Iterator{
	Board board;  // Changed from Cell[][] boardList
	Cell currentCell;
	Coordinate currentCoordinate; // Modify the UML diagram
	
	public IteratorBoard(Board board, Coordinate givenCoordinate) {
		// Constructor here
		this.board = board;
		this.currentCoordinate = givenCoordinate;
		this.currentCell = this.board.board[this.currentCoordinate.row][this.currentCoordinate.col];
		
	}
	
	// Iterator methods
	public Cell up() {
		Coordinate givenCellCoordinate = this.currentCell.getCoordinate();
		if (givenCellCoordinate.row < this.board.board.length && givenCellCoordinate.row >= 1) {
			return this.board.board[givenCellCoordinate.row - 1][givenCellCoordinate.col];
		}
		return null;
	}
	
	public Cell down() {
		Coordinate givenCellCoordinate = this.currentCell.getCoordinate();
		if (givenCellCoordinate.row < this.board.board.length - 1 && givenCellCoordinate.row >= 0) {
			return this.board.board[givenCellCoordinate.row + 1][givenCellCoordinate.col];
		}
		return null;
	}
	
	public Cell left() {
		Coordinate givenCellCoordinate = this.currentCell.getCoordinate();
		if (givenCellCoordinate.col < this.board.board[0].length && givenCellCoordinate.col >= 1) {
			return this.board.board[givenCellCoordinate.row][givenCellCoordinate.col - 1];
		}
		return null;
	}
	public Cell right() {
		Coordinate givenCellCoordinate = this.currentCell.getCoordinate();
		if (givenCellCoordinate.col < this.board.board[0].length - 1 && givenCellCoordinate.col >= 0) {
			return this.board.board[givenCellCoordinate.row][givenCellCoordinate.col + 1];
		}
		return null;
	}
	
	public Boolean isDone() {
		return this.currentCell == this.board.board[this.board.board.length - 1][this.board.board[0].length - 1];
	}
	
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		if (isDone() == true) {
			return false;
		}
		return true;  // by default
	}
	
	@Override
	public Cell next() {
		// TODO Auto-generated method stub
		if (this.hasNext() == true) {
			Coordinate current = this.currentCell.getCoordinate();
			// if at end of the row
			if (current.col < this.board.board[0].length - 1 && current.col >= 0) {  // check if its at end of the row:
				this.currentCell = this.board.board[current.row][current.col + 1];
				return this.currentCell;
			}else if (current.col == this.board.board[0].length - 1 && current.row < this.board.board.length - 1) {
				this.currentCell = this.board.board[current.row + 1][0];
				return this.currentCell;
			}
		}
		return null;
	}
	
	public List<String> getSuggestedCells() {
		List<Cell> listOfMusketeers = this.board.getMusketeerCells();
		listOfMusketeers.remove(this.currentCell);
		List<String> suggestedMoves = new ArrayList<String>();
		List<Cell> surroundingCells = new ArrayList<Cell>();
		// check the four moves: left, right, up and down
		Cell leftCell = this.left();
		Cell rightCell = this.right();
		Cell upCell = this.up();
		Cell downCell = this.down();
		// order of counts:
		surroundingCells.add(leftCell);
		surroundingCells.add(rightCell);
		surroundingCells.add(upCell);
		surroundingCells.add(downCell);
		
		Coordinate c1;
		
		if (this.currentCell.getPiece() != null) {
			// check if Musketeer:
			if (this.currentCell.getPiece().getType() == Piece.Type.MUSKETEER) {
				for (int i = 0; i < listOfMusketeers.size();i++) {
					if (listOfMusketeers.get(i) == this.currentCell) {
						listOfMusketeers.remove(i);
					}
				}
				boolean sameRow = false;
				boolean sameCol = false;
				if (listOfMusketeers.get(0).getCoordinate().row == listOfMusketeers.get(1).getCoordinate().row) {
					sameRow = true;
				} else if (listOfMusketeers.get(0).getCoordinate().col == listOfMusketeers.get(1).getCoordinate().col) {
					sameCol = true;
				}
				
				if(sameCol == false) {
					if (upCell != null) {
						suggestedMoves.add(upCell.getCoordinate().toString());
					}
					if (downCell != null) {
						suggestedMoves.add(downCell.getCoordinate().toString());
					}
				}
				if (sameRow == false) {
					if (leftCell != null) {
						suggestedMoves.add(leftCell.getCoordinate().toString());				
					}
					if (rightCell != null) {
						suggestedMoves.add(rightCell.getCoordinate().toString());
					}
				}
				
			}
			
			// check if guard:
			else if (this.currentCell.getPiece().getType() == Piece.Type.GUARD) {
				
				for (Cell cell: surroundingCells) {
					if (cell != null && cell.hasPiece() != true) {
						suggestedMoves.add(cell.getCoordinate().toString());
					}
				}
			}
		}
		return suggestedMoves;
	}
}
