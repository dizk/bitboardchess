package board;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.Commons;
import board.Board;
import board.Position;


public class BoardTest {
	
	private long[][] board;
	
	/**
	 * We need a board for every test
	 */
	
	@Before
    public void setUp() {
		board = Board.initBitBoard();
    }
	
	/**
	 * We should always use a new board every time.
	 */
	
	@After
    public void tearDown() {
        board = null;
    }
	

	@Test
	public void checkInitialPlacement() {
		
		// Check if king is in his position
		assertEquals(board[Commons.Color.WHITE][0], 0x1000000000000000L);
		
		
		// Check that black king is in his position
		assertEquals(board[Commons.Color.BLACK][0], 0x0000000000000010L);
		
		
		
		
	}
	@Test
	public void testGetOccupiedBitMap() {
		long bitmap = Board.getBitMap(board);
		
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
		// Testing the two end-points
		assertEquals(63, Board.getIndexAtPosition(new Position(7, 7)));
		assertEquals(0, Board.getIndexAtPosition(new Position(0, 0)));
	}
	
	@Test
	public void testGetMaskAtPosition(){
		// Testing some positions it is only powers of two anyway ^^
		assertEquals(1L, Board.getMaskAtPosition(new Position(0, 0)));
		assertEquals(2L, Board.getMaskAtPosition(new Position(1, 0)));
		assertEquals(4L, Board.getMaskAtPosition(new Position(2, 0)));
		assertEquals(8L, Board.getMaskAtPosition(new Position(3, 0)));
		assertEquals(16L, Board.getMaskAtPosition(new Position(4, 0)));
		assertEquals(32L, Board.getMaskAtPosition(new Position(5, 0)));
		assertEquals(64L, Board.getMaskAtPosition(new Position(6, 0)));
		assertEquals(128L, Board.getMaskAtPosition(new Position(7, 0)));
		assertEquals(256L, Board.getMaskAtPosition(new Position(0, 1)));
		assertEquals(512L, Board.getMaskAtPosition(new Position(1, 1)));
	}
	
	
	
	@Test
	public void getStringDoNotChangeBitmap(){
		// Testing that the getString function does not change the bitmap.
		long bitmap = Board.getBitMap(board);
		Board.getString(bitmap);
		assertEquals(Board.getBitMap(Board.initBitBoard()), bitmap);
		
	}
	
	@Test
	public void testGetBitMapForColor(){
		long bitmap = Board.getBitMapForColor(board, Commons.Color.WHITE);
		
		// At the inital game this should be the same as all WHITE_PIECES OR'ed together.
		
		long whitemap = Commons.Bitmaps.WHITE_PIECES[0] | Commons.Bitmaps.WHITE_PIECES[1] | Commons.Bitmaps.WHITE_PIECES[2] | Commons.Bitmaps.WHITE_PIECES[3] | Commons.Bitmaps.WHITE_PIECES[4] | Commons.Bitmaps.WHITE_PIECES[5];
		
		assertEquals(whitemap, bitmap);
		
		
	}

	
	@Test
	public void testGetBitMapForType(){
		long bitmap = Board.getBitMapForType(board, Commons.PieceType.KING);
		
		// At the inital game this should be the same as all KING_PIECES OR'ed together.
		
		long kingmap = Commons.Bitmaps.BLACK_PIECES[Commons.PieceType.KING] | Commons.Bitmaps.WHITE_PIECES[Commons.PieceType.KING];
		
		assertEquals(kingmap, bitmap);
		
		bitmap = Board.getBitMapForType(board, Commons.PieceType.BISHOP);
		
		// At the inital game this should be the same as all BISHOP_PIECES OR'ed together.
		
		long bishopmap = Commons.Bitmaps.BLACK_PIECES[Commons.PieceType.BISHOP] | Commons.Bitmaps.WHITE_PIECES[Commons.PieceType.BISHOP];
		
		assertEquals(bishopmap, bitmap);
		
		
	}
	
	@Test
	public void testSetPieceAtPosition(){
		Position pos = new Position(3, 3);
		
		board = Board.setPieceAtPosition(board, pos, Commons.PieceType.PAWN, Commons.Color.WHITE);
		
		long bitmap = Board.getBitMap(board) & Board.getMaskAtPosition(pos);
		
		assertEquals((long) Math.pow(2, 27), bitmap);
		
		
	}
	
	@Test
	public void testGetPieceTypeAtPosition(){
		Position pos = new Position(2, 1);
		
		assertEquals(Commons.PieceType.PAWN, Board.getPieceAtPosition(board, pos, Commons.Color.BLACK));
		
		pos = new Position(4, 0);
		
		assertEquals(Commons.PieceType.KING, Board.getPieceAtPosition(board, pos, Commons.Color.BLACK));
		
		pos = new Position(4, 7);
		
		assertEquals(Commons.PieceType.KING, Board.getPieceAtPosition(board, pos, Commons.Color.WHITE));
		
		pos = new Position(7, 0);
		
		assertEquals(Commons.PieceType.ROOK, Board.getPieceAtPosition(board, pos, Commons.Color.BLACK));
		
		pos = new Position(7, 7);
		
		assertEquals(Commons.PieceType.ROOK, Board.getPieceAtPosition(board, pos, Commons.Color.WHITE));
		
		pos = new Position(3, 0);
		
		assertEquals(Commons.PieceType.QUEEN, Board.getPieceAtPosition(board, pos, Commons.Color.BLACK));
	
		pos = new Position(3, 7);
		
		assertEquals(Commons.PieceType.QUEEN, Board.getPieceAtPosition(board, pos, Commons.Color.WHITE));
		
		pos = new Position(5, 0);
		
		assertEquals(Commons.PieceType.BISHOP, Board.getPieceAtPosition(board, pos, Commons.Color.BLACK));
	
		pos = new Position(2, 7);
		
		assertEquals(Commons.PieceType.BISHOP, Board.getPieceAtPosition(board, pos, Commons.Color.WHITE));
	
		pos = new Position(1, 0);
		
		assertEquals(Commons.PieceType.KNIGHT, Board.getPieceAtPosition(board, pos, Commons.Color.BLACK));
		
		pos = new Position(6, 7);
		
		assertEquals(Commons.PieceType.KNIGHT, Board.getPieceAtPosition(board, pos, Commons.Color.WHITE));
	
		pos = new Position(5, 1);
		
		assertEquals(Commons.PieceType.PAWN, Board.getPieceAtPosition(board, pos, Commons.Color.BLACK));
		
		pos = new Position(2, 6);
		
		assertEquals(Commons.PieceType.PAWN, Board.getPieceAtPosition(board, pos, Commons.Color.WHITE));
		
		pos = new Position(0, 1);
		
		assertEquals(Commons.PieceType.PAWN, Board.getPieceAtPosition(board, pos, Commons.Color.BLACK));
	}
	
	
	
	
	

}
