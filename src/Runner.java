import utils.Commons;
import board.Board;
import board.Position;


public class Runner {
	
	public static void main(String[] args) {
		Board.initBitBoard();
		
	
		System.out.println(Board.getString(Commons.Bitmaps.BEHINDMAP[9][18]));
	}

}
