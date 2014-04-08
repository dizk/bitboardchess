import utils.Commons;
import board.Board;
import board.Position;

public class Runner {

	public static void main(String[] args) {
		long[][] board = Board.initBitBoard();

		 Board.setPieceAtPosition(board, new Position(2, 3), 1,
		 Commons.Color.WHITE);
		
		 long bitmap = Board.pieceAttacks(Commons.PieceType.KNIGHT, 35,
		 Board.getBitMap(board));
		
		 System.out.println(Board.getString(bitmap));


	}

}
