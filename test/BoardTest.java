import static org.junit.Assert.*;

import org.junit.Test;

import pieces.Board;
import pieces.Commons;
import pieces.Position;


public class BoardTest {

	@Test
	public void checkInitialPlacement() {
		
		long[][] board = Board.initBitBoard();
		
		// Check if king is in his position
		assertEquals(board[Commons.Color.WHITE][0], 0x1000000000000000L);
		
		
		// Check that black king is in his position
		assertEquals(board[Commons.Color.BLACK][0], 0x0000000000000010L);
		
		
		
		
	}
	@Test
	public void testGetOccupiedBitMap() {
		long[][] board = Board.initBitBoard();
		
		long bitmap = Board.getOccupiedBitMap(board);
		
		// At the start this should be the same as every piece map added together
		long sum = 0;
		for(int i = 0; i < 6; i++){
			sum += board[Commons.Color.WHITE][i];
			sum += board[Commons.Color.BLACK][i];
		}
		
		assertEquals(sum, bitmap);
		
		
	}
	
	@Test
	public void testGetMaskIndexAtPosition(){
		Board.createMasks();
		
		assertEquals(63, Board.getIndexAtPosition(new Position(7, 7)));
		
		assertEquals(0, Board.getIndexAtPosition(new Position(0, 0)));
	}
	
	

}
