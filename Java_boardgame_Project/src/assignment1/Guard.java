package assignment1;

import assignment1.Piece.Type;

public class Guard extends Piece {

    public Guard() {
        super("O", Type.GUARD);
    }

    /**
     * Returns true if the Guard can move onto the given cell.
     * @param cell Cell to check if the Guard can move onto
     * @return True, if Guard can move onto given cell, false otherwise
     */
    @Override
    public boolean canMoveOnto(Cell cell) { 
        if(!cell.hasPiece() && !(cell == null) && (cell.getCoordinate().row <= 5) && (cell.getCoordinate().col <= 5)){
        	return true;
        }
        return false;
    }
}
