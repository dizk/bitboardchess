import utils.Commons;
import board.Board;
import board.Position;

public class Runner {

	public static void main(String[] args) {
		long[][] board = Board.initBitBoard();

		 Board.setPieceAtPosition(board, new Position(2, 3), 1,
		 Commons.Color.WHITE);
		
		 long bitmap = Board.getPieceAttacks(Commons.PieceType.KNIGHT, 35,
		 Board.getBitMap(board));
		 
		 
		 // Printer between brett som ikke er 0
		 
		 Board.initBetween();
		
		 for (int i = 0; i < 64; i++){
			 for (int j = 0; j < 64; j++){
				 if (Board.between[i][j] != 0){
					 System.out.println(Board.getString(Board.between[i][j]));					 
				 }
			 }
		 }


	}

}
