import utils.Commons;
import board.Board;
import board.Position;


public class Runner {
	
	public static void main(String[] args) {
		long[][] board = Board.initBitBoard();
		Position pos = new Position(0, 2);
		long withextraPawn = Board.getBitMap(Board.setPieceAtPosition(board, pos, Commons.PieceType.PAWN, Commons.Color.WHITE));
		
		System.out.println(Board.getString(withextraPawn));
	}

}
