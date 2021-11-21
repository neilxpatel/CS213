package chessPieces;

import chess.Board;
/**
* class for Pawn that extends Piece
* @author Yash Patel
* @author Neil Patel
*/

public class Pawn extends Piece{
	/**
	 * checks if the first move is already made
	 */
	boolean firstMove = true;
	/**
	 * checks if the Pawn moves up two spaces
	 */
	public boolean doubleMove = false;
	/**
	 * constructor for Pawn
	 * @param pieceName is the name of the Piece
	 * @param currRow is the row of the piece where the piece is starting
	 * @param currCol is the column of the piece where the piece is starting
	 */
	
	public Pawn(String pieceName, int currRow, int currCol) {
		super(pieceName, currRow, currCol);
	}
	
	/**
	 * This method checks if the move that we are going to make for the current location 
	 * to the destination is legal according to how a pawn can move
	 * @param destRow is the row of the piece where the piece is going to land
	 * @param destCol is the column of the piece where the piece is going to land
	 * @return checks if the move is valid for the Pawn
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
		
		if(getPieceName().equals("wp")) {
			
			if(currRow - destRow == 1 && currCol == destCol) { //move one up
				if(Board.isNull(destRow, destCol)) {
					firstMove = false;
					return true;
				}
				return false;
			}
			if(currRow - destRow == 1 && (currCol - destCol == 1 || currCol - destCol == -1)) {// move diagonal
				if(!Board.isNull(destRow, destCol) && !(Board.getPiece(destRow, destCol).getPieceName().charAt(0) == 'w')) {
					firstMove = false;
					return true;
				}
				return false;
			}
			
			if(currRow - destRow == 2 && currCol == destCol) { //two squares forward
				//TODO Check for piece in front of a pawn before making a double move
				if(firstMove) {
					if(!Board.isNull(destRow, destCol)) {
						return false;
					}
					firstMove = false;
					doubleMove = true;
					return true;
				}
				return false;
			}
		}
		else {
			if(destRow - currRow == 1 && currCol == destCol) { //move one up
				if(Board.isNull(destRow, destCol)) {
					firstMove = false;
					return true;
				}
				return false;
			}
			if(destRow - currRow == 1 && (currCol - destCol == 1 || currCol - destCol == -1)) {// move diagonal
				if(!Board.isNull(destRow, destCol) && !(Board.getPiece(destRow, destCol).getPieceName().charAt(0) == 'b')) {
					firstMove = false;
					return true;
				}
				return false;
			}
			
			if(destRow - currRow == 2 && currCol == destCol) { //two squares forward
				if(!Board.tracePath(currRow, currCol, destRow, destCol)) {
					return false;
				}
				if(firstMove) {
					if(!Board.isNull(destRow, destCol)) {
						return false;
					}
					firstMove = false;
					doubleMove = true;
					return true;
				}
				return false;
			}
		}
		return false;
	}
	


}
