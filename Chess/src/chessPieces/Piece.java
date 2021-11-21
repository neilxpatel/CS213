package chessPieces;

/**
* abstract class for Piece
* @author Yash Patel
* @author Neil Patel
*/
public abstract class Piece {
	/**
	 * pieceName stores the name of the piece 
	 */
	String pieceName; 
	/**
	 * currRow holds the current row of the piece
	 */
	public int currRow;
	/** 
	 * currCol holds the current column of the piece
	 */
	public int currCol;
	/**
	 * constructor for Piece
	 * @param pieceName is the name of the Piece
	 * @param currRow is the row of the piece where the piece is starting
	 * @param currCol is the column of the piece where the piece is starting
	 */
	
	public Piece(String pieceName, int currRow, int currCol) {
		this.pieceName = pieceName;
		this.currRow = currRow;
		this.currCol = currCol;
	}
	
	/**
	 * abstract method of the piece that subclasses need to implement according to the rules of that piece
	 * @param destRow is the row of the piece where the piece is going to land
	 * @param destCol is the column of the piece where the piece is going to land
	 * @return checks if the move is valid for the piece
	 */
	public abstract boolean isValidMove(int destRow, int destCol);
	
	/**
	 * setter method to set the row of this piece
	 * @param currRow is the current row of the piece
	 */
	public void setRow(int currRow) {
		this.currRow = currRow;
	}
	
	/**
	 * setter method to set the column of this piece
	 * @param currCol is the current column of the piece
	 */
	public void setCol(int currCol) {
		this.currCol = currCol;
	}
	
	/**
	 * 
	 * @return the Piece's name
	 */
	public String getPieceName() {
		return pieceName;
	}
	
	// toString
	public String toString() {
		return this.pieceName;
	}
	

}
