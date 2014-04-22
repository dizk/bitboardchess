import board.Board;

public class Runner {

	public static void main(String[] args) {
		long[][] board = Board.initBitBoard();

		System.out.println(Board.getString(Board.getBitMap(board)));

	}

}
