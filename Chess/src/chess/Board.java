package chess;

import java.util.*;

import chessPieces.*;

/**
 * class for board
 * @author Yash Patel
 * @author Neil Patel
 */
public class Board {
	
	/**
	 * board and the references of pieces needed to make special moves like castling
	 */
	public static Piece[][] board = new Piece[8][8];
	static Piece bKing , wKing, bQueenRook,bKingRook,wQueenRook,wKingRook;
	static ArrayList<Integer> checkRow;
	static ArrayList<Integer> checkCol;
	
	
	/**
	 * Initializes the board with all the pieces of chess
	 */
	public static void initBoard() {
		for(int i = 0; i < 8; i++) {
			Piece pawn = new Pawn("bp",1,i);
			board[1][i] = pawn;
		}
		
		bQueenRook = new Rook("bR",0,0);
		board[0][0] = bQueenRook;
		board[0][1] = new Knight("bN",0,1);
		board[0][2] = new Bishop("bB",0,2);
		board[0][3] = new Queen("bQ",0,3);
		bKing = new King("bK",0,4);
		board[0][4] = bKing;
		board[0][5] = new Bishop("bB",0,5);
		board[0][6] = new Knight("bN",0,6);
		bKingRook = new Rook("bR",0,7);
		board[0][7] = bKingRook;
		
		for(int i = 0; i < 8; i++) {
			Piece pawn = new Pawn("wp",6,i);
			board[6][i] = pawn;
		}
		
		wQueenRook = new Rook("wR",7,0);
		board[7][0] =  wQueenRook;
		board[7][1] = new Knight("wN",7,1);
		board[7][2] = new Bishop("wB",7,2);
		board[7][3] = new Queen("wQ",7,3);
        wKing = new King("wK",7,4);
        board[7][4] = wKing;
		board[7][5] = new Bishop("wB",7,5);
		board[7][6] = new Knight("wN",7,6); 
		wKingRook = new Rook("wR",7,7);
		board[7][7] = wKingRook;
	}
	
	/**
	 * Prints out the board
	 */
	public static void printBoard() {
		boolean isBlack = false;
		int num = 8;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(isNull(i,j)) {
					if(isBlack) {
						System.out.print("## ");
					}
					else {
						System.out.print("   ");
					}
				}
				else {
					System.out.print(board[i][j] + " ");
				}
				if(j == 7) {
					System.out.println(num);
					num--;
				}
				isBlack = !isBlack;
			}
			isBlack = !isBlack;
		}
		for(int i = 0; i < 8; i++) {
			char letter = (char) ('a' + i) ;
			System.out.print(" " + letter + " ");
		}
	}
	
	/**
	 * This method checks for the move the user makes and checks for all special moves like castling,
	 * enpassant and promotion and makes the move. It also returns if the move is legal
	 * @param move The string provided by the user stating starting position and ending position
	 * @param isWhiteTurn boolean is true when it is white turn
	 * @return returns boolean if the user made a right move
	 */
	public static boolean move(String move, boolean isWhiteTurn) {
		
		String[] input = move.split(" ");
		
		if(input.length < 2) {
			return false;
		}
		
		int startCol = Board.getIndex(input[0].charAt(0));
		int startRow = Board.getIndex(input[0].charAt(1));
		
		int destCol = Board.getIndex(input[1].charAt(0));
		int destRow = Board.getIndex(input[1].charAt(1));
		
		if(Board.isNull(startRow, startCol)) {
			return false;
		}
		Piece startPiece = board[startRow][startCol];
		Piece prevPiece = startPiece;
		Piece destPiece = board[destRow][destCol];
		if(isWhiteTurn) {
			if(startPiece.getPieceName().charAt(0) != 'w') {
				return false;
			}
		}
		else {
			if(startPiece.getPieceName().charAt(0) != 'b') {
				return false;
			}
		}
		
		if(startPiece.getPieceName().equals("wp") || startPiece.getPieceName().equals("bp")) { //check for enpassant for pawn
			if(EnPassant(startRow,startCol,destRow,destCol)) {
				return true;
			}
		}
		
		if(startPiece.getPieceName().equals("wK") || startPiece.getPieceName().equals("bK")) { //check for castling
			int xDistance = destCol - startCol;
			if(Math.abs(xDistance) == 2) {
				if(isWhiteTurn) {
					if(isCastling('w',destRow,destCol)) {
						if(xDistance == 2) {
							board[destRow][destCol] = wKing;
							board[startRow][startCol] = null;
							board[destRow][destCol-1] = wKingRook;
							board[wKingRook.currRow][wKingRook.currCol] = null;
							if(isCheck('w').size() > 0) {
								board[destRow][destCol] = null;
								board[startRow][startCol] = wKing;
								board[destRow][destCol-1] = null;
								board[wKingRook.currRow][wKingRook.currCol] = wKingRook;
								return false;
							}
							wKing.setRow(destRow);
							wKing.setCol(destCol);
							wKingRook.setRow(destRow);
							wKingRook.setCol(destCol-1);
							return true;
						}
						else {
							board[destRow][destCol] = wKing;
							board[startRow][startCol] = null;
							board[destRow][destCol+1] = wQueenRook;
							board[wQueenRook.currRow][wQueenRook.currCol] = null;
							if(isCheck('w').size() > 0) {
								board[destRow][destCol] = null;
								board[startRow][startCol] = wKing;
								board[destRow][destCol+1] = null;
								board[wQueenRook.currRow][wQueenRook.currCol] = wQueenRook;
								return false;
							}
							wKing.setRow(destRow);
							wKing.setCol(destCol);
							wQueenRook.setRow(destRow);
							wQueenRook.setCol(destCol+1);
							return true;
						}
					}
					else {
						return false;
					}
				}
				else {
					if(isCastling('b',destRow,destCol)) {
						if(xDistance == 2) {
							board[destRow][destCol] = bKing;
							board[startRow][startCol] = null;
							board[destRow][destCol-1] = bKingRook;
							board[bKingRook.currRow][bKingRook.currCol] = null;
							if(isCheck('b').size() > 0) {
								board[destRow][destCol] = null;
								board[startRow][startCol] = bKing;
								board[destRow][destCol-1] = null;
								board[bKingRook.currRow][bKingRook.currCol] = bKingRook;
								return false;
							}
							bKing.setRow(destRow);
							bKing.setCol(destCol);
							bKingRook.setRow(destRow);
							bKingRook.setCol(destCol-1);
							return true;
						}
						else {
							board[destRow][destCol] = bKing;
							board[startRow][startCol] = null;
							board[destRow][destCol+1] = bQueenRook;
							board[bQueenRook.currRow][bQueenRook.currCol] = null;
							if(isCheck('b').size() > 0) {
								board[destRow][destCol] = null;
								board[startRow][startCol] = bKing;
								board[destRow][destCol+1] = null;
								board[bQueenRook.currRow][bQueenRook.currCol] = bQueenRook;
								return false;
							}
							bKing.setRow(destRow);
							bKing.setCol(destCol);
							bQueenRook.setRow(destRow);
							bQueenRook.setCol(destCol+1);
							return true;
						}
					}
					else {
						return false;
					}
				}
			}
		}
		
		if(!startPiece.isValidMove(destRow, destCol)) {
			return false;
		}
		if(startPiece.getPieceName().equals("bp") && destRow == 7) { //check for promotion
				if(input.length == 3) {
					switch(input[2]) {
						case "Q":
							startPiece = new Queen("bQ",destRow,destCol);
							break;
						case "N":
							startPiece = new Knight("bN",destRow,destCol);
							break;
						case "R":
							startPiece = new Rook("bR",destRow,destCol);
							break;
						case "B":
							startPiece = new Bishop("bB",destRow,destCol);
							break;
					}
				}
				else {
					startPiece = new Queen("bQ",destRow,destCol);
				}
		}
		else if(startPiece.getPieceName().equals("wp") && destRow == 0 ) {
			if(input.length == 3) {
				switch(input[2]) {
					case "Q":
						startPiece = new Queen("wQ",destRow,destCol);
						break;
					case "N":
						startPiece = new Knight("wN",destRow,destCol);
						break;
					case "R":
						startPiece = new Rook("wR",destRow,destCol);
						break;
					case "B":
						startPiece = new Bishop("wB",destRow,destCol);
						break;
				}
			}
			else {
				startPiece = new Queen("wQ",destRow,destCol);
			}
		}
		
		board[destRow][destCol] = startPiece;
		board[startRow][startCol] = null;
		startPiece.setRow(destRow);
		startPiece.setCol(destCol);
		if(isWhiteTurn) {//checks if the current move causes our king to get checked it returns false because it will be a illegal move
			if(isCheck('w').size() > 0) {
				board[startRow][startCol] = prevPiece;
				board[destRow][destCol] = destPiece;
				startPiece.setRow(startRow);
				startPiece.setCol(startCol);
				return false;
			}
		}
		else {
			if(isCheck('b').size() > 0) {
				board[startRow][startCol] = prevPiece;
				board[destRow][destCol] = destPiece;
				startPiece.setRow(startRow);
				startPiece.setCol(startCol);
				return false;
			}
		}
		return true; 
	}
	
	/**
	 * The method checks for the enpassant rules and sets the pieces appropriately if we can do enpassant
	 * @param startRow is the row of the piece where the move is initiated
	 * @param startCol is the column of the piece where the move is initiated
	 * @param destRow is the row of the piece where the piece is going to land
	 * @param destCol is the column of the piece where the piece is going to land
	 * @return returns a boolean that says if the move we made will do enpassant or not
	 */
	public static boolean EnPassant(int startRow, int startCol, int destRow, int destCol) {
		
		Piece startPiece = Board.getPiece(startRow, startCol);
		Piece destPiece = Board.getPiece(destRow, destCol);
			
			if(startPiece == null) {
				return false;
			}

			if(startPiece.getPieceName().charAt(0) == 'w') {
			
				if(startRow != 3 && destRow != 2) {
						return false;
					}
	
				if(destPiece == null && (startRow - destRow == 1) && (destCol - startCol == 1)) { //diagonal move on right side
					if(!isNull(startRow, startCol + 1) && board[startRow][startCol + 1].getPieceName().equals("bp")) {
						Pawn blackPawn = (Pawn) board[startRow][startCol + 1];
						if(blackPawn.doubleMove) {
							board[destRow][destCol] = new Pawn("wp", destRow, destCol);
							board[startRow][startCol] = null;
							board[startRow][startCol + 1] = null;
						}
						return true;
					} else {
						return false;
					}
				} else if(destPiece == null && (startRow - destRow == 1) && (startCol - destCol == 1)) { //diagonal move on left side
					if(!isNull(startRow, startCol - 1) && board[startRow][startCol - 1].getPieceName().equals("bp")) {
						Pawn blackPawn = (Pawn) board[startRow][startCol - 1];
						if(blackPawn.doubleMove) {
							board[destRow][destCol] = new Pawn("wp", destRow, destCol);
							board[startRow][startCol] = null;
							board[startRow][startCol - 1] = null;
						}
						return true;
					} else {
						return false;
					}
				}
			}
			else{
				if(startRow != 4 && destRow != 5) {
					return false;
				}
				if(destPiece == null && (destRow - startRow == 1) && (destCol - startCol == 1)) { //diagonal move on right side
					if(!isNull(startRow, startCol + 1) && board[startRow][startCol + 1].getPieceName().equals("wp")) {
						Pawn whitePawn = (Pawn) board[startRow][startCol + 1];
						if(whitePawn.doubleMove) {
							board[destRow][destCol] = new Pawn("bp", destRow, destCol);
							board[startRow][startCol] = null;
							board[startRow][startCol + 1] = null;
						}
						return true;
					} else {
						return false;
					}
				} else if(destPiece == null && (destRow - startRow == 1) && (startCol - destCol == 1)) { //diagonal move on left side
					if(!isNull(startRow, startCol - 1) && board[startRow][startCol - 1].getPieceName().equals("wp")) {
						Pawn whitePawn = (Pawn) board[startRow][startCol - 1];
						if(whitePawn.doubleMove) {
							board[destRow][destCol] = new Pawn("bp", destRow, destCol);
							board[startRow][startCol] = null;
							board[startRow][startCol - 1] = null;
						}
						return true;
					} else {
						return false;
					}
				

				}
			}
		return false;
		
	}

	/**
	 * The method checks if the colored king can castle to the king side or Queen side
	 * @param color is the color of the king that we are trying to castle
	 * @param destRow is the row of the piece where the piece is going to land
	 * @param destCol is the column of the piece where the piece is going to land
	 * @return boolean if the the colored king is allowed to castle
	 */
	public static boolean isCastling(char color, int destRow, int destCol) {
		King castlingKing;
		int xDistance;
		if(color == 'b') {
			castlingKing = (King) bKing;
			xDistance = destCol - castlingKing.currCol;
		}
		else {
			castlingKing = (King) wKing;
			xDistance = destCol - castlingKing.currCol;
		}
		if(isCheck(color).size() > 0) {
			return false;
		}
		if(castlingKing.moved) {
			return false;
		}
		if(color == 'w'){
			if(xDistance == 2) {
				if(((Rook)wKingRook).moved) {
					return false;
				}
				if(!tracePath(wKing.currRow,wKing.currCol,wKingRook.currRow,wKingRook.currCol-1)) {
					return false;
				}
				return true;
			}
			else if(xDistance == -2) {
				if(((Rook)wQueenRook).moved) {
					return false;
				}
				if(!tracePath(wKing.currRow,wKing.currCol,wQueenRook.currRow,wQueenRook.currCol+1)) {
					return false;
				}
				return true;
			}
		}
		else {
			if(xDistance == 2) {
				if(((Rook)bKingRook).moved) {
					return false;
				}
				if(!tracePath(bKing.currRow,bKing.currCol,bKingRook.currRow,bKingRook.currCol-1)) {
					return false;
				}
				return true;
			}
			else if(xDistance == -2) {
				if(((Rook)bQueenRook).moved) {
					return false;
				}
				if(!tracePath(bKing.currRow,bKing.currCol,bQueenRook.currRow,bQueenRook.currCol+1)) {
					return false;
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method checks is the colored king that is given is checked by opponents piece
	 * @param color is the color of the king we are checking if it is getting checked by the opponent
	 * @return ArrayList of the pieces checking the colored king provided.
	 */
	public static ArrayList<Piece> isCheck(char color) {
		ArrayList<Piece> ret = new ArrayList<>();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(!isNull(i,j) && board[i][j].getPieceName().charAt(0) != color) {
					if(color == 'w') {
						if(board[i][j].isValidMove(wKing.currRow, wKing.currCol)) {
							ret.add(board[i][j]);
						}
					}
					else {
						if(board[i][j].isValidMove(bKing.currRow, bKing.currCol)) {
							ret.add(board[i][j]);
						}
					}
				}
			}
		}
		return ret;
	}
	
	/**
	 * This method checks if the color of the king provided is checkmated or not
	 * @param color is the color of the king that we want to check if it is getting checkmated
	 * @return if there are possible moves to get out of checkmate it returns false
	 */
	public static boolean isCheckmate(char color) {
		King currKing;
		if(color == 'w') {
			currKing = (King) wKing;
		}
		else {
			currKing = (King) bKing;
		}
		int kingsPrevRow = currKing.currRow;;
		int kingsPrevCol = currKing.currCol;
		for(int i = kingsPrevRow - 1; i <= kingsPrevRow + 1; i++) {
			for(int j = kingsPrevCol - 1; j <= kingsPrevCol + 1; j++) {
				Piece dest;
				if(!isOutOfBounds(i,j)) {
					dest = board[i][j];
					if(currKing.isValidMove(i, j)) {
						board[i][j] = currKing;
						board[currKing.currRow][currKing.currCol] = null;
						currKing.setRow(i);
						currKing.setCol(j);
						if(isCheck(color).size() == 0) {
							board[kingsPrevRow][kingsPrevCol] = currKing;
							board[i][j] = dest;
							currKing.setRow(kingsPrevRow);
							currKing.setCol(kingsPrevCol);
							return false;
						}
						else {
							board[kingsPrevRow][kingsPrevCol] = currKing;
							board[i][j] = dest;
							currKing.setRow(kingsPrevRow);
							currKing.setCol(kingsPrevCol);
						}
					}
				}
			}
		}
		
		ArrayList<Piece> checkPiece = isCheck(color);
		if(checkPiece.size() ==  1) {
			getCheckPos(checkPiece.get(0).currRow,checkPiece.get(0).currCol,currKing.currRow,currKing.currCol);
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					if(!isNull(i,j) && board[i][j].getPieceName().charAt(0) == color && board[i][j].getPieceName().charAt(1) != 'K') {
						for(int k = 0; k < checkRow.size(); k++) {
							if(board[i][j].isValidMove(checkRow.get(k), checkCol.get(k))) {
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}
	/**
	 * By taking the starting position of the piece checking the king at the destination the method puts the path (row and column)
	 * of the piece checking in the arraylist for checkmate
	 * @param startRow is the row of the piece where the piece is going to land
	 * @param startCol is the column of the piece where the piece is going to land
	 * @param destRow is the row of the piece where the piece is going to land
	 * @param destCol is the column of the piece where the piece is going to land
	 */
	public static void getCheckPos(int startRow, int startCol, int destRow, int destCol) {
		checkRow = new ArrayList<>();
		checkCol = new ArrayList<>();
		
		int yDiff = Math.abs(destRow - startRow);
		int xDiff = Math.abs(destCol - startCol);
		
		int xIncrement = 0;
		int yIncrement = 0;
		
		if(destRow - startRow >= 1) {
			yIncrement = 1;
		}
		else if(destRow - startRow <= -1) {
			yIncrement = -1;
		}
		else {
			yIncrement = 0;
		}
		
		if(destCol - startCol >= 1) {
			xIncrement = 1;
		}
		else if(destCol - startCol <= -1) {
			xIncrement = -1;
		}
		else {
			xIncrement = 0;
		}
		
		int dist = Math.max(xDiff, yDiff);
		
		for(int i = 0; i < dist; i++) {
			checkRow.add(startRow);
			checkCol.add(startCol);
			startRow += yIncrement;
			startCol += xIncrement;
		}
	}
	/**
	 * converts the user inputed string to indexes
	 * @param c char from the user move
	 * @return returns index form of the char move given to the method else returns -1 to show the char is wrong
	 */
	public static int getIndex(char c) {
		switch (c) {
			case 'a': case '8': return 0;
			case 'b': case '7': return 1;
			case 'c': case '6': return 2;
			case 'd': case '5': return 3;
			case 'e': case '4': return 4;
			case 'f': case '3': return 5;
			case 'g': case '2': return 6;
			case 'h': case '1': return 7;
			default : return -1;
		}
	}
	
	/**
	 * @param row is the row in the board
	 * @param col is the column in the board
	 * @return if the position is null it returns true
	 */
	public static boolean isNull(int row, int col) {
		if(board[row][col] == null) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param row is row in the board
	 * @param col is the column in the board
	 * @return if the index given is outofbounds of board it returns true
	 */
	public static boolean isOutOfBounds(int row, int col) {
		if(row < 0 || row > 7 || col < 0 || col > 7) {
			return true;
		}
		return false;
	}
	/**
	 * traces the path that a piece will take to check if there are no pieces in the path that the piece has to jump over
	 * @param startRow is the row of the piece where the piece is going to land
	 * @param startCol is the column of the piece where the piece is going to land
	 * @param destRow is the row of the piece where the piece is going to land
	 * @param destCol is the column of the piece where the piece is going to land
	 * @return is the path to the destination is clear
	 */
	public static boolean tracePath(int startRow, int startCol, int destRow, int destCol) {
		Piece currPiece = board[startRow][startCol];
		
		int yDiff = Math.abs(destRow - startRow);
		int xDiff = Math.abs(destCol - startCol);
		
		int xIncrement = 0;
		int yIncrement = 0;
		
		if(destRow - startRow >= 1) {
			yIncrement = 1;
		}
		else if(destRow - startRow <= -1) {
			yIncrement = -1;
		}
		else {
			yIncrement = 0;
		}
		
		if(destCol - startCol >= 1) {
			xIncrement = 1;
		}
		else if(destCol - startCol <= -1) {
			xIncrement = -1;
		}
		else {
			xIncrement = 0;
		}
		
		int dist = Math.max(xDiff, yDiff);
		
		for(int i = 0; i < dist; i++) {
			startRow += yIncrement;
			startCol += xIncrement;
			if(!Board.isNull(startRow, startCol)) {
				
				if(currPiece.getPieceName().charAt(0) == board[startRow][startCol].getPieceName().charAt(0)) {
					return false;
				}
				
				if(dist - i > 1) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * @param row is the row in the board
	 * @param col is the column in the board
	 * @return returns Piece at given index either Piece or null
	 */
	public static Piece getPiece(int row, int col) {
		return board[row][col];
	}
}
