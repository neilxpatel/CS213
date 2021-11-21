package chessPieces;

import chess.Board;

/**
* class for King that extends Piece
* @author Yash Patel
* @author Neil Patel
*/
public class King extends Piece{
	
	/**
	 * checks if the King has moved
	 */
	public boolean moved = false;
	/**
	 * constructor for King
	 * @param pieceName is the name of the Piece
	 * @param currRow is the row of the piece where the piece is starting
	 * @param currCol is the column of the piece where the piece is starting
	 */
	
	public King(String pieceName, int currRow, int currCol) {
		super(pieceName, currRow, currCol);
	}
	
	/**
	 * This method checks if the move that we are going to make for the current location 
	 * to the destination is legal according to how a King can move
	 * @param destRow is the row of the piece where the piece is going to land
	 * @param destCol is the column of the piece where the piece is going to land
	 * @return checks if the move is valid for the King
	 */
	public boolean isValidMove(int destRow, int destCol) {
		
		if(currCol == -1 || currRow == -1 || destCol == -1 || destRow == -1) {
			return false;
		}
		if(Board.isNull(currRow,currCol)) {
			return false;
		}
		
		if(Board.isOutOfBounds(currRow,currCol) || Board.isOutOfBounds(destRow,destCol)) {
			return false;
		}
		
		if(currCol == destCol && currRow == destRow) { //piece not moved
			return false;
		}
		
		int yDistance = Math.abs(destRow - currRow);
		int xDistance = Math.abs(destCol - currCol);
		
		if(xDistance <= 1 && yDistance <= 1) { //the king only moves 1 in x or y or 1,1 in x and y for diagonal
			if(!Board.tracePath(currRow, currCol, destRow, destCol)) {
				return false;
			}
			moved = true;
			return true;
		}
		
		return false;
	}

}
