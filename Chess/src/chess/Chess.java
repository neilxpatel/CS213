package chess;

import java.util.Scanner;

/**
 * class for the main method
 * @author Neil Patel
 * @author Yash Patel
 */

public class Chess {	
	
	/**
	 * main method to run the chess game
	 * @param args arguments
	 */
	public static void main(String[] args) {
		Board.initBoard();
		boolean gameRunning = true;
		boolean isWhiteTurn = true;
		Scanner sc = new Scanner(System.in);
		Board.printBoard();
		System.out.println("\n");
		while(gameRunning) {
			if(isWhiteTurn) {
				System.out.print("White's move: ");
			}
			else {
				System.out.print("Black's move: ");
			}
			String input = sc.nextLine();
			String[] split = input.split(" ");
			
			if(split.length <= 3) {
				if(split[0].equals("resign")) {
					if(isWhiteTurn) {
						System.out.println("Black wins");
					}
					else {
						System.out.println("White wins");
					}
					gameRunning = false;
				}
				else {
					if(split.length == 3) {
						if(split[2].equals("draw?")) {
							input = sc.nextLine();
							if(input.equals("draw")) {
								gameRunning = false;
								break;
							}
						}
					}
					if(!Board.move(input, isWhiteTurn)) {
						System.out.println("Illegal move, try again");
					}
					else {
						boolean isCheck = false;
						if(isWhiteTurn) {
							if(Board.isCheck('b').size() > 0) {
								isCheck = true;
								if(Board.isCheckmate('b')) {
									System.out.println();
									Board.printBoard();
									System.out.println("\n");
									System.out.println("Checkmate \nWhite Wins");
									gameRunning = false;
									break;
								}
							}
						}
						else {
							if(Board.isCheck('w').size() > 0) {
								isCheck = true;
								if(Board.isCheckmate('w')) {
									System.out.println();
									Board.printBoard();
									System.out.println("\n");
									System.out.println("Checkmate \nBlack Wins");
									gameRunning = false;
									break;
								}
							}
						}
						System.out.println();
						Board.printBoard();
						System.out.println("\n");
						if(isCheck) {
							System.out.println("Check");
						}
						isWhiteTurn = !isWhiteTurn;
					}
				}
			}
		}
		sc.close();
		
	}

}
