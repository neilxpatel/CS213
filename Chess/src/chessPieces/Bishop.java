package chessPieces;

import chess.Board;

/**
* class for Bishop that extends Piece
* @author Yash Patel
* @author Neil Patel
*/
public class Bishop extends Piece {
	/**
	 * constructor for Bishop
	 * @param pieceName is the name of the Piece
	 * @param currRow is the row of the piece where the piece is starting
	 * @param currCol is the column of the piece where the piece is starting
	 */

	public Bishop(String pieceName, int currRow, int currCol) {
		super(pieceName, currRow, currCol);
	}
	
	/**
	 * This method checks if the move that we are going to make for the current location 
	 * to the destination is legal according to how a Bishop can move
	 * @param destRow is the row of the piece where the piece is going to land
	 * @param destCol is the column of the piece where the piece is going to land
	 * @return checks if the move is valid for Bishop
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
		
		if(xDistance == yDistance) {
			if(!Board.tracePath(currRow, currCol, destRow, destCol)) {
				return false;
			}
			return true;
		}
		return false;
	}

}
