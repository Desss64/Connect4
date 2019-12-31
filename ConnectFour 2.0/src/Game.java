import java.util.Scanner;
public class Game {
	
	static int player1Count;
	static int player2Count;
	
	//Initializes board
	public static void initializeBoard(char [][] b){
		for(int r = 0; r < b.length; r++) {
			for(int c = 0; c < b[0].length; c++) {
				b[r][c] = ' ';
			}
		}
	}
	//Checks if user input is within boundaries of board
	public static int getInput(int boundary) {
		Scanner kb = new Scanner(System.in);
		int c;
		do {
			System.out.println("\nEnter a column number from 0 to " + (boundary-1) + ".");
		     c = kb.nextInt();
			if(c < 0 || c >= (boundary)) {
				System.out.println("Invalid spot on board");
			}
		}while(c < 0 || c >= (boundary));
		return c;
	}
	//Checks if chip can be dropped and drops it
	public static boolean tryDropChip(char[][] board, int col, char chip){
		boolean result = false;
		// Check if the column is full
		if (board[0][col] != ' '){
		System.out.println("That column is already full.");
		return false;
		}
		// Drop the piece as far as it will go starting from the bottom
		for (int row = board.length-1; row >= 0; row--){
		if (board[row][col] == ' '){
		board[row][col] = chip;
		return true;
		}
	}
		return result;
	}
	//Prints current state of board
	public static void printBoard(char[][] b) {
		for(int r = 0; r < b.length; r++) {
			System.out.print(" | ");
			for(int c = 0; c < b[0].length; c++) {
				System.out.print(b[r][c] + " | ");
			}
			System.out.println();
		}
		System.out.println("-------------------------------");
		
		int j = 1;
		System.out.print(" | ");
		for(int i = 0; i < b[0].length; i++) {
			System.out.print(j + " | ");
			j++;
		}
		System.out.println();
	}
	//Checks for winning conditions
	public static boolean checkWinnerAt(char[][] b, int row, int col, int c) {
		//Down diagonal
		if((row+3) < b.length && (col+3) < b[0].length) {
			if(b[row][col] == c && b[row+1][col+1] == c && b[row+2][col+2] == c && b[row+3][col+3] == c) {
				return true;
			}
		}
		//Up diagonal
		if((row-3) >= 0 && (col+3) < b[0].length) {
			if(b[row][col] == c && b[row-1][col+1] == c && b[row-2][col+2] == c && b[row-3][col+3] == c) {
				return true;
			}
		}
		//Horizontal
		if((col+3) < b[0].length) {
			if(b[row][col] == c && b[row][col+1] == c && b[row][col+2] == c && b[row][col+3] == c) {
				return true;
			}
		}
		//Vertical
		if((row+3) < b.length) {
			if(b[row][col] == c && b[row+1][col] == c && b[row+2][col] == c && b[row+3][col] == c) {
				return true;
			}
		}
		return false;
	}
	//Checks if board is full
	public static boolean isBoardFull(char [][] b) {
		for(int r = 0; r < b.length; r++) {
			for(int c = 0; c < b[0].length; c++) {
				if(b[r][c] == ' ') {
					return false;
				}
			}
		}
		return true;
	}
	//Checks to see which player is winner
	public static boolean checkWinner(char [][] b) {
		boolean r1, r2;
		
		for(int r = 0; r < b.length; r++) {
			for(int c = 0; c < b[0].length; c++) {
				r1 = checkWinnerAt(b, r, c, 'R');
				r2 = checkWinnerAt(b, r, c, 'Y');
				
				if(r1 == true) {
					System.out.println("Player 1 wins");
					player1Count++;
					return true;
				}
				if(r2 == true) {
					System.out.println("Player 2 wins");
					player2Count++;
					return true;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		
		char ans, currentMark;
		char [][] board = new char [6][7];
		
		System.out.println("Designate players. Player 1 has red chips and Player 2 has yellow chips.");
		currentMark = 'R';
		ans = ' ';
		
		//Sets up blank board & prints it
		initializeBoard(board);
		printBoard(board);
		boolean gameOver = false;
		int row = board.length-1;
		
		do {
		//Takes column # from user
		int col = getInput(board[0].length);
		
		//Checks if column is full
		if(tryDropChip(board, col, currentMark)) {
			//Alternates between red and yellow chips.
			if(currentMark == 'R') {
				currentMark = 'Y';
			}
			else {
				currentMark = 'R';
			}
		}
		
		printBoard(board); 
		
		//Signals a draw if board is full & no winner is found
		if(isBoardFull(board) == true) {
			System.out.println("Draw!");
		}
		
		//Checks board for winner
		if(checkWinner(board) || isBoardFull(board)) {
			System.out.println("Do you want to play again? Y/y for yes and N/n for no.");
			ans = kb.next().charAt(0);
			//If ans is yes board is reset for new game
			if(ans == 'Y' || ans == 'y') {
				initializeBoard(board);
				printBoard(board);
				currentMark = 'R';
			}
			else {
				//If ans is not yes then final score for each player is printed
				System.out.println("Player 1: " + player1Count + " Wins");
				System.out.println("Player 2: " + player2Count + " Wins");
				gameOver = true;
			}
		}
		}while(gameOver == false);		
	}
}