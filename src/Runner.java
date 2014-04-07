import board.Board;


public class Runner {
	
	public static void main(String[] args) {
		long[][] board = Board.initBitBoard();
		long bitmap = Board.getBitMap(board);
		
		System.out.println(Board.getString(bitmap));
	}

}
