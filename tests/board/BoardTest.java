package board;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.Commons;
import board.Board;
import board.Position;

public class BoardTest {

	private static long[][] board;

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

		// At the start this should be the same as every piece map added
		// together
		long sum = 0;
		for (int i = 0; i < 6; i++) {
			sum += board[Commons.Color.WHITE][i];
			sum += board[Commons.Color.BLACK][i];
		}

		assertEquals(sum, bitmap);

	}

	@Test
	public void testGetMaskIndexAtPosition() {
		// Testing the two end-points
		assertEquals(63, Board.getIndexAtPosition(new Position(7, 7)));
		assertEquals(0, Board.getIndexAtPosition(new Position(0, 0)));
	}

	@Test
	public void testGetMaskAtPosition() {
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
	public void getStringDoNotChangeBitmap() {
		// Testing that the getString function does not change the bitmap.
		long bitmap = Board.getBitMap(board);
		Board.getString(bitmap);
		assertEquals(Board.getBitMap(Board.initBitBoard()), bitmap);

	}

	@Test
	public void testGetBitMapForColor() {
		long bitmap = Board.getBitMapForColor(board, Commons.Color.WHITE);

		// At the inital game this should be the same as all WHITE_PIECES OR'ed
		// together.

		long whitemap = Commons.Bitmaps.WHITE_PIECES[0]
				| Commons.Bitmaps.WHITE_PIECES[1]
				| Commons.Bitmaps.WHITE_PIECES[2]
				| Commons.Bitmaps.WHITE_PIECES[3]
				| Commons.Bitmaps.WHITE_PIECES[4]
				| Commons.Bitmaps.WHITE_PIECES[5];

		assertEquals(whitemap, bitmap);

	}

	@Test
	public void testGetBitMapForType() {
		long bitmap = Board.getBitMapForType(board, Commons.PieceType.KING);

		// At the inital game this should be the same as all KING_PIECES OR'ed
		// together.

		long kingmap = Commons.Bitmaps.BLACK_PIECES[Commons.PieceType.KING]
				| Commons.Bitmaps.WHITE_PIECES[Commons.PieceType.KING];

		assertEquals(kingmap, bitmap);

		bitmap = Board.getBitMapForType(board, Commons.PieceType.BISHOP);

		// At the inital game this should be the same as all BISHOP_PIECES OR'ed
		// together.

		long bishopmap = Commons.Bitmaps.BLACK_PIECES[Commons.PieceType.BISHOP]
				| Commons.Bitmaps.WHITE_PIECES[Commons.PieceType.BISHOP];

		assertEquals(bishopmap, bitmap);

	}

	@Test
	public void testSetPieceAtPosition() {

		board = Board.setPieceAtSquare(board, 27, Commons.PieceType.PAWN,
				Commons.Color.WHITE);

		long bitmap = Board.getBitMap(board) & Board.masks[27];

		assertEquals((long) Math.pow(2, 27), bitmap);

	}

	@Test
	public void testGetPieceTypeAtPosition() {

		assertEquals(Commons.PieceType.PAWN,
				Board.getPieceAtSquare(board, 10, Commons.Color.BLACK));

		assertEquals(Commons.PieceType.KING,
				Board.getPieceAtSquare(board, 4, Commons.Color.BLACK));

		assertEquals(Commons.PieceType.KING,
				Board.getPieceAtSquare(board, 60, Commons.Color.WHITE));

		assertEquals(Commons.PieceType.ROOK,
				Board.getPieceAtSquare(board, 7, Commons.Color.BLACK));

		assertEquals(Commons.PieceType.ROOK,
				Board.getPieceAtSquare(board, 63, Commons.Color.WHITE));

		assertEquals(Commons.PieceType.QUEEN,
				Board.getPieceAtSquare(board, 3, Commons.Color.BLACK));

		assertEquals(Commons.PieceType.QUEEN,
				Board.getPieceAtSquare(board, 59, Commons.Color.WHITE));

		assertEquals(Commons.PieceType.BISHOP,
				Board.getPieceAtSquare(board, 5, Commons.Color.BLACK));

		assertEquals(Commons.PieceType.BISHOP,
				Board.getPieceAtSquare(board, 58, Commons.Color.WHITE));

		assertEquals(Commons.PieceType.KNIGHT,
				Board.getPieceAtSquare(board, 1, Commons.Color.BLACK));

		assertEquals(Commons.PieceType.KNIGHT,
				Board.getPieceAtSquare(board, 62, Commons.Color.WHITE));

		assertEquals(Commons.PieceType.PAWN,
				Board.getPieceAtSquare(board, 13, Commons.Color.BLACK));

		assertEquals(Commons.PieceType.PAWN,
				Board.getPieceAtSquare(board, 50, Commons.Color.WHITE));

		assertEquals(Commons.PieceType.PAWN,
				Board.getPieceAtSquare(board, 8, Commons.Color.BLACK));
	}

	@Test
	public void testIsAttackedByPawn() {
		// Pawn
		for (int i = 16; i < 32; i++) {
			if (i < 24) {
				assertTrue(Board
						.isAttackedByPawn(i, Commons.Color.WHITE, board));
			} else {
				assertFalse(Board.isAttackedByPawn(i, Commons.Color.WHITE,
						board));
			}
		}

		for (int i = 32; i < 48; i++) {
			if (i < 40) {
				assertFalse(Board.isAttackedByPawn(i, Commons.Color.BLACK,
						board));
			} else {
				assertTrue(Board
						.isAttackedByPawn(i, Commons.Color.BLACK, board));
			}
		}

	}

	@Test
	public void testIsAttackedByKnight() {
		// Knight

		// White
		assertTrue(Board.isAttackedByKnight(16, Commons.Color.WHITE, board));
		assertTrue(Board.isAttackedByKnight(18, Commons.Color.WHITE, board));
		assertTrue(Board.isAttackedByKnight(21, Commons.Color.WHITE, board));
		assertTrue(Board.isAttackedByKnight(23, Commons.Color.WHITE, board));

		assertFalse(Board.isAttackedByKnight(17, Commons.Color.WHITE, board));
		assertFalse(Board.isAttackedByKnight(22, Commons.Color.WHITE, board));
		assertFalse(Board.isAttackedByKnight(20, Commons.Color.WHITE, board));
		assertFalse(Board.isAttackedByKnight(19, Commons.Color.WHITE, board));

		// Black
		assertTrue(Board.isAttackedByKnight(40, Commons.Color.BLACK, board));
		assertTrue(Board.isAttackedByKnight(42, Commons.Color.BLACK, board));
		assertTrue(Board.isAttackedByKnight(45, Commons.Color.BLACK, board));
		assertTrue(Board.isAttackedByKnight(47, Commons.Color.BLACK, board));

		assertFalse(Board.isAttackedByKnight(41, Commons.Color.BLACK, board));
		assertFalse(Board.isAttackedByKnight(44, Commons.Color.BLACK, board));
		assertFalse(Board.isAttackedByKnight(46, Commons.Color.BLACK, board));
		assertFalse(Board.isAttackedByKnight(43, Commons.Color.BLACK, board));
	}

	@Test
	public void testIsAttackedByKing() {

		// White
		assertTrue(Board.isAttackedByKing(5, Commons.Color.WHITE, board));
		assertTrue(Board.isAttackedByKing(13, Commons.Color.WHITE, board));
		assertTrue(Board.isAttackedByKing(12, Commons.Color.WHITE, board));
		assertTrue(Board.isAttackedByKing(11, Commons.Color.WHITE, board));
		assertTrue(Board.isAttackedByKing(3, Commons.Color.WHITE, board));

		for (int i = 16; i < 64; i++) {
			assertFalse(Board.isAttackedByKing(i, Commons.Color.WHITE, board));
		}

		// Black
		assertTrue(Board.isAttackedByKing(59, Commons.Color.BLACK, board));
		assertTrue(Board.isAttackedByKing(51, Commons.Color.BLACK, board));
		assertTrue(Board.isAttackedByKing(52, Commons.Color.BLACK, board));
		assertTrue(Board.isAttackedByKing(53, Commons.Color.BLACK, board));
		assertTrue(Board.isAttackedByKing(61, Commons.Color.BLACK, board));

		for (int i = 0; i < 51; i++) {
			assertFalse(Board.isAttackedByKing(i, Commons.Color.BLACK, board));
		}

	}

	@Test
	public void testIsAttackedByRook() {

		// White
		assertTrue(Board.isAttackedByRook(1, Commons.Color.WHITE, board));
		assertTrue(Board.isAttackedByRook(8, Commons.Color.WHITE, board));
		assertTrue(Board.isAttackedByRook(6, Commons.Color.WHITE, board));
		assertTrue(Board.isAttackedByRook(15, Commons.Color.WHITE, board));

		for (int i = 16; i < 64; i++) {
			assertFalse(Board.isAttackedByRook(i, Commons.Color.WHITE, board));
		}

		// Black
		assertTrue(Board.isAttackedByRook(48, Commons.Color.BLACK, board));
		assertTrue(Board.isAttackedByRook(57, Commons.Color.BLACK, board));
		assertTrue(Board.isAttackedByRook(62, Commons.Color.BLACK, board));
		assertTrue(Board.isAttackedByRook(55, Commons.Color.BLACK, board));

		for (int i = 0; i < 48; i++) {
			assertFalse(Board.isAttackedByRook(i, Commons.Color.BLACK, board));
		}
	}

	@Test
	public void testIsAttackedByBishop() {
		// White
		assertTrue(Board.isAttackedByBishop(9, Commons.Color.WHITE, board));
		assertTrue(Board.isAttackedByBishop(11, Commons.Color.WHITE, board));
		assertTrue(Board.isAttackedByBishop(12, Commons.Color.WHITE, board));
		assertTrue(Board.isAttackedByBishop(14, Commons.Color.WHITE, board));

		for (int i = 16; i < 64; i++) {
			assertFalse(Board.isAttackedByBishop(i, Commons.Color.WHITE, board));
		}

		// Black
		assertTrue(Board.isAttackedByBishop(49, Commons.Color.BLACK, board));
		assertTrue(Board.isAttackedByBishop(51, Commons.Color.BLACK, board));
		assertTrue(Board.isAttackedByBishop(52, Commons.Color.BLACK, board));
		assertTrue(Board.isAttackedByBishop(54, Commons.Color.BLACK, board));

		for (int i = 0; i < 48; i++) {
			assertFalse(Board.isAttackedByBishop(i, Commons.Color.BLACK, board));
		}

	}

	@Test
	public void testIsAttackedByQueen() {
		// White
		assertTrue(Board.isAttackedByQueen(2, Commons.Color.WHITE, board));
		assertTrue(Board.isAttackedByQueen(4, Commons.Color.WHITE, board));
		assertTrue(Board.isAttackedByQueen(10, Commons.Color.WHITE, board));
		assertTrue(Board.isAttackedByQueen(12, Commons.Color.WHITE, board));
		assertTrue(Board.isAttackedByQueen(11, Commons.Color.WHITE, board));

		for (int i = 16; i < 64; i++) {
			assertFalse(Board.isAttackedByQueen(i, Commons.Color.WHITE, board));
		}

		// Black
		assertTrue(Board.isAttackedByQueen(50, Commons.Color.BLACK, board));
		assertTrue(Board.isAttackedByQueen(58, Commons.Color.BLACK, board));
		assertTrue(Board.isAttackedByQueen(52, Commons.Color.BLACK, board));
		assertTrue(Board.isAttackedByQueen(60, Commons.Color.BLACK, board));
		assertTrue(Board.isAttackedByQueen(51, Commons.Color.BLACK, board));

		for (int i = 0; i < 48; i++) {
			assertFalse(Board.isAttackedByQueen(i, Commons.Color.BLACK, board));
		}

	}

	@Test
	public void testPawnMoves() {
		long bitmap = 0;
		// White

		for (int i = 48; i < 56; i++) {
			bitmap = 0;
			bitmap = Board.setBit(bitmap, i - 8);
			bitmap = Board.setBit(bitmap, i - 16);
			assertEquals(Board.getString(bitmap), Board.getString(Board
					.getPawnMovesFrom(i, Commons.Color.WHITE, board)));
		}

		for (int i = 24; i < 48; i++) {
			bitmap = 0;
			bitmap = Board.setBit(bitmap, i - 8);
			assertEquals(Board.getString(bitmap), Board.getString(Board
					.getPawnMovesFrom(i, Commons.Color.WHITE, board)));
		}

		for (int i = 0; i < 24; i++) {
			bitmap = 0;
			assertEquals(Board.getString(bitmap), Board.getString(Board
					.getPawnMovesFrom(i, Commons.Color.WHITE, board)));
		}

		// Black

		for (int i = 8; i < 16; i++) {
			bitmap = 0;
			bitmap = Board.setBit(bitmap, i + 8);
			bitmap = Board.setBit(bitmap, i + 16);
			assertEquals(Board.getString(bitmap), Board.getString(Board
					.getPawnMovesFrom(i, Commons.Color.BLACK, board)));
		}

		for (int i = 16; i < 40; i++) {
			bitmap = 0;
			bitmap = Board.setBit(bitmap, i + 8);
			assertEquals(Board.getString(bitmap), Board.getString(Board
					.getPawnMovesFrom(i, Commons.Color.BLACK, board)));
		}

		for (int i = 40; i < 64; i++) {
			bitmap = 0;
			assertEquals(Board.getString(bitmap), Board.getString(Board
					.getPawnMovesFrom(i, Commons.Color.BLACK, board)));
		}

	}

	@Test
	public void testPawnAttacksFrom() {

		// White

		long bitmap = 0;
		BoardTest.board = Board.setPieceAtSquare(board, 32,
				Commons.PieceType.BISHOP, Commons.Color.BLACK);
		BoardTest.board = Board.setPieceAtSquare(board, 34,
				Commons.PieceType.BISHOP, Commons.Color.BLACK);

		bitmap = 0;
		bitmap = Board.setBit(bitmap, 32);
		bitmap = Board.setBit(bitmap, 34);
		assertEquals(bitmap,
				Board.getPawnAttacksFrom(41, Commons.Color.WHITE, board));

		bitmap = 0;
		bitmap = Board.setBit(bitmap, 8);
		bitmap = Board.setBit(bitmap, 10);
		assertEquals(bitmap,
				Board.getPawnAttacksFrom(17, Commons.Color.WHITE, board));

		// Black

		bitmap = 0;
		bitmap = Board.setBit(bitmap, 48);
		bitmap = Board.setBit(bitmap, 50);
		assertEquals(bitmap,
				Board.getPawnAttacksFrom(41, Commons.Color.BLACK, board));

		bitmap = 0;
		bitmap = Board.setBit(bitmap, 50);
		bitmap = Board.setBit(bitmap, 52);
		assertEquals(bitmap,
				Board.getPawnAttacksFrom(43, Commons.Color.BLACK, board));

	}

	@Test
	public void testgetPawnAttacksAndMoves() {
		long bitmap = 0;

		// White

		for (int i = 49; i < 55; i++) {
			bitmap = 0;
			board = Board.initBitBoard();
			BoardTest.board = Board.setPieceAtSquare(board, i - 9,
					Commons.PieceType.BISHOP, Commons.Color.BLACK);
			BoardTest.board = Board.setPieceAtSquare(board, i - 7,
					Commons.PieceType.BISHOP, Commons.Color.BLACK);
			bitmap = Board.setBit(bitmap, i - 8);
			bitmap = Board.setBit(bitmap, i - 16);
			bitmap = Board.setBit(bitmap, i - 9);
			bitmap = Board.setBit(bitmap, i - 7);
			assertEquals(Board.getString(bitmap), Board.getString(Board
					.getPawnAttacksAndMoves(i, Commons.Color.WHITE, board)));

		}

		// Black
		for (int i = 9; i < 15; i++) {
			bitmap = 0;
			board = Board.initBitBoard();
			BoardTest.board = Board.setPieceAtSquare(board, i + 9,
					Commons.PieceType.BISHOP, Commons.Color.WHITE);
			BoardTest.board = Board.setPieceAtSquare(board, i + 7,
					Commons.PieceType.BISHOP, Commons.Color.WHITE);
			bitmap = Board.setBit(bitmap, i + 8);
			bitmap = Board.setBit(bitmap, i + 16);
			bitmap = Board.setBit(bitmap, i + 9);
			bitmap = Board.setBit(bitmap, i + 7);
			assertEquals(Board.getString(bitmap), Board.getString(Board
					.getPawnAttacksAndMoves(i, Commons.Color.BLACK, board)));

		}
	}

	@Test
	public void testGetValidMovesForSquare() {
		List<Move> moves = null;
		List<Move> testMoves;

		// Pawn
		
		moves = Board.getValidMovesForSquare(49, Commons.Color.WHITE, board);

		testMoves = new ArrayList<>();
		testMoves.add(new Move(49, 41, Commons.PieceType.PAWN));
		testMoves.add(new Move(49, 33, Commons.PieceType.PAWN));

		assertTrue(testMoves.containsAll(moves));

		
		
		// Rook
		
		board = Board.setPieceAtSquare(board, 27, Commons.PieceType.ROOK, Commons.Color.BLACK);

		moves = Board.getValidMovesForSquare(27, Commons.Color.BLACK, board);

		testMoves = new ArrayList<>();
		testMoves.add(new Move(27, 19, Commons.PieceType.ROOK));
		testMoves.add(new Move(27, 24, Commons.PieceType.ROOK));
		testMoves.add(new Move(27, 25, Commons.PieceType.ROOK));
		testMoves.add(new Move(27, 26, Commons.PieceType.ROOK));
		testMoves.add(new Move(27, 28, Commons.PieceType.ROOK));
		testMoves.add(new Move(27, 29, Commons.PieceType.ROOK));
		testMoves.add(new Move(27, 30, Commons.PieceType.ROOK));
		testMoves.add(new Move(27, 31, Commons.PieceType.ROOK));
		testMoves.add(new Move(27, 35, Commons.PieceType.ROOK));
		testMoves.add(new Move(27, 43, Commons.PieceType.ROOK));
		testMoves.add(new Move(27, 51, Commons.PieceType.ROOK));
		

		assertTrue(testMoves.containsAll(moves));


		
		// Bishop
		
		board = Board.initBitBoard();
		
		
		board = Board.setPieceAtSquare(board, 44, Commons.PieceType.BISHOP, Commons.Color.BLACK);
		
		
		moves = Board.getValidMovesForSquare(44, Commons.Color.BLACK, board);

		testMoves = new ArrayList<>();
		testMoves.add(new Move(44, 17, Commons.PieceType.BISHOP));
		testMoves.add(new Move(44, 23, Commons.PieceType.BISHOP));
		testMoves.add(new Move(44, 26, Commons.PieceType.BISHOP));
		testMoves.add(new Move(44, 30, Commons.PieceType.BISHOP));
		testMoves.add(new Move(44, 35, Commons.PieceType.BISHOP));
		testMoves.add(new Move(44, 37, Commons.PieceType.BISHOP));
		testMoves.add(new Move(44, 51, Commons.PieceType.BISHOP));
		testMoves.add(new Move(44, 53, Commons.PieceType.BISHOP));
		
		
		assertTrue(testMoves.containsAll(moves));


		
		// Knight
		board = Board.initBitBoard();
		
		
		board = Board.setPieceAtSquare(board, 19, Commons.PieceType.KNIGHT, Commons.Color.WHITE);
		
		
		moves = Board.getValidMovesForSquare(19, Commons.Color.WHITE, board);

		testMoves = new ArrayList<>();
		testMoves.add(new Move(19, 2, Commons.PieceType.KNIGHT));
		testMoves.add(new Move(19, 4, Commons.PieceType.KNIGHT));
		testMoves.add(new Move(19, 9, Commons.PieceType.KNIGHT));
		testMoves.add(new Move(19, 13, Commons.PieceType.KNIGHT));
		testMoves.add(new Move(19, 25, Commons.PieceType.KNIGHT));
		testMoves.add(new Move(19, 29, Commons.PieceType.KNIGHT));
		testMoves.add(new Move(19, 34, Commons.PieceType.KNIGHT));
		testMoves.add(new Move(19, 36, Commons.PieceType.KNIGHT));
		
		
		assertTrue(testMoves.containsAll(moves));

		
		// King
		board = Board.initBitBoard();
		
		
		board = Board.setPieceAtSquare(board, 63, Commons.PieceType.KING, Commons.Color.BLACK);
		
		
		moves = Board.getValidMovesForSquare(63, Commons.Color.BLACK, board);

		testMoves = new ArrayList<>();
		testMoves.add(new Move(63, 54, Commons.PieceType.KING));
		testMoves.add(new Move(63, 55, Commons.PieceType.KING));
		testMoves.add(new Move(63, 62, Commons.PieceType.KING));
		
		
		assertTrue(testMoves.containsAll(moves));
		
		
		// Queen
		board = Board.initBitBoard();
		
		
		board = Board.setPieceAtSquare(board, 63, Commons.PieceType.QUEEN, Commons.Color.BLACK);
		
		
		moves = Board.getValidMovesForSquare(63, Commons.Color.BLACK, board);

		testMoves = new ArrayList<>();
		testMoves.add(new Move(63, 54, Commons.PieceType.QUEEN));
		testMoves.add(new Move(63, 62, Commons.PieceType.QUEEN));
		testMoves.add(new Move(63, 55, Commons.PieceType.QUEEN));
		
		
		assertTrue(testMoves.containsAll(moves));
		
		
		// King
		board = Board.initBitBoard();
		
		moves = Board.getValidMovesForSquare(4, Commons.Color.BLACK, board);

		testMoves = new ArrayList<>();
		
		
		assertTrue(testMoves.containsAll(moves));
		
		
		// Test for check
		board = Board.initBitBoard();
		
		board = Board.removePieceAtSquare(board, 52);
		
		board = Board.setPieceAtSquare(board, 36, Commons.PieceType.ROOK, Commons.Color.BLACK);
		
		board = Board.setPieceAtSquare(board, 52, Commons.PieceType.BISHOP, Commons.Color.WHITE);
		
		moves = Board.getValidMovesForSquare(52, Commons.Color.WHITE, board);
		
		assertTrue(moves.isEmpty());
		

	}
	
	
	@Test
	public void testIsSquareOccupied(){
		// Black
		 
		for (int i = 0; i < 16; i++){
			assertTrue(Board.isSquareOccupied(i, board));
		}
		
		
		// Nothing
		
		for (int i = 16; i < 48; i++){
			assertFalse(Board.isSquareOccupied(i, board));
		}
		
		// White
		for (int i = 48; i < 64; i++){
			assertTrue(Board.isSquareOccupied(i, board));
		}
		
	}
	
	
	
	@Test
	public void testMove(){
		
		board = Board.move(new Move(48, 40, Commons.PieceType.PAWN) , Commons.Color.WHITE , board);
		
		assertEquals(Commons.PieceType.PAWN, Board.getPieceAtSquare(board, 40, Commons.Color.WHITE));
		assertFalse(Board.isSquareOccupied(48, board));
		
		
	}
	
	@Test
	public void speedTest(){
		List<Move> moves = null;
		
		long time = System.nanoTime();
		
		for (int i = 0; i < 5000000; i++){
			for (int j = 48; j < 64; j++){
				moves = Board.getValidMovesForSquare(j, Commons.Color.WHITE, board);
			}
			for (int j = 0; j < 16; j++){
				moves = Board.getValidMovesForSquare(j, Commons.Color.BLACK, board);
			}	
			
		}
		
		System.out.printf("Used %d seconds \n", ((System.nanoTime() - time) / 1000000000));
		
	}
	
	
	
	
	

}
