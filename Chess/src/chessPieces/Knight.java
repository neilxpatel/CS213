package chessPieces;

import chess.Board;

/**
* class for Knight that extends Piece
* @author Yash Patel
* @author Neil Patel
*/
public class Knight extends Piece {
	/**
	 * constructor for Knight
	 * @param pieceName is the name of the Piece
	 * @param currRow is the row of the piece where the piece is starting
	 * @param currCol is the column of the piece where the piece is starting
	 */

	public Knight(String pieceName, int currRow, int currCol) {
		super(pieceName, currRow, currCol);
	}
	
	/**
	 * This method checks if the move that we are going to make for the current location 
	 * to the destination is legal according to how a Knight can move
	 * @param destRow is the row of the piece where the piece is going to land
	 * @param destCol is the column of the piece where the piece is going to land
	 * @return checks if the move is valid for the Knight
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
		
		Piece destPiece = Board.getPiece(destRow, destCol);
		Piece startPiece = Board.getPiece(currRow, currCol);
				
				//same piece cannot attack each other
		if(destPiece != null) {
			if(startPiece.getPieceName().charAt(0) == 'w' && destPiece.getPieceName().charAt(0) == 'w') {
				return false;
			}
			if(startPiece.getPieceName().charAt(0) == 'b' && destPiece.getPieceName().charAt(0) == 'b') {
				return false;
			}
		}
		
		int yDistance = Math.abs(destRow - currRow);
		int xDistance = Math.abs(destCol - currCol);
		
		if(yDistance == 1) { //knight move where it moves one up/down row
			if(xDistance == 2) { //knight goes either 2 to the left/right
				return true;
			}
			return false;
		} 
		
		if(yDistance == 2) { //knight move where it goes two up/down row
				if(xDistance == 1) { //knight goes either 1 to the left/tight
					return true; 
				}
				return false;
		}
		return false;
	}

}
