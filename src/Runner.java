import utils.Commons;
import board.Board;

public class Runner {

	public static void main(String[] args) {
		long[][] board = Board.initBitBoard();
		
//		System.out.println(Board.getString(board[0][Commons.PieceType.KNIGHT]));
		
		System.out.println(Board.getString(Commons.Bitmaps.BETWEENMAP[48][24]));
		
//		 long bitmap = Board.getPieceAttacks(Commons.PieceType.QUEEN, 28,
//		 Board.getBitMap(board));
//		 System.out.println(Board.getString(bitmap));
		 
		 
		 // Printer between brett som ikke er 0
		 
//		 Board.initBetween();
//		
//		 for (int i = 0; i < 64; i++){
//			 for (int j = 0; j < 64; j++){
//				 if (Board.between[i][j] != 0){
//					 System.out.println(Board.getString(Board.between[i][j]));					 
//				 }
//			 }
//		 }


	}

}
