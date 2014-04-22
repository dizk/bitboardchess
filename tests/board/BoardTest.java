package board;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.Commons;
import board.Board;

public class BoardTest {

	private long[][] board;
	private Stack<long[][]> undoStack;

	/**
	 * We need a board for every test
	 */

	@Before
	public void setUp() {
		board = Board.initBitBoard();
		undoStack = new Stack<>();
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
	public void getStringDoNotChangeBitmap() {
		// Testing that the getString function does not change the bitmap.
		long bitmap = Board.getBitMap(board);
		Board.getString(bitmap);
		assertEquals(Board.getBitMap(Board.initBitBoard()), bitmap);

		// Check for consistency
		board = Board.initBitBoard();

		Board.getString(bitmap);

		assertArrayEquals(board, Board.initBitBoard());

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

		// Check for consistency
		board = Board.initBitBoard();

		Board.getBitMapForColor(board, Commons.Color.WHITE);
		Board.getBitMapForColor(board, Commons.Color.BLACK);

		assertArrayEquals(board, Board.initBitBoard());

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

		// Check for consistency
		board = Board.initBitBoard();

		Board.getBitMapForType(board, Commons.PieceType.BISHOP);
		Board.getBitMapForType(board, Commons.PieceType.KING);
		Board.getBitMapForType(board, Commons.PieceType.KNIGHT);
		Board.getBitMapForType(board, Commons.PieceType.PAWN);
		Board.getBitMapForType(board, Commons.PieceType.QUEEN);
		Board.getBitMapForType(board, Commons.PieceType.ROOK);

		assertArrayEquals(board, Board.initBitBoard());

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

		// Check for consistency
		board = Board.initBitBoard();

		Board.getPieceAtSquare(board, 62, Commons.Color.WHITE);
		Board.getPieceAtSquare(board, 8, Commons.Color.BLACK);
		Board.getPieceAtSquare(board, 50, Commons.Color.WHITE);

		assertArrayEquals(board, Board.initBitBoard());
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

		// Check for consistency
		board = Board.initBitBoard();

		for (int i = 32; i < 48; i++) {
			Board.isAttackedByPawn(i, Commons.Color.BLACK, board);
		}

		for (int i = 16; i < 32; i++) {
			Board.isAttackedByPawn(i, Commons.Color.WHITE, board);
		}

		assertArrayEquals(board, Board.initBitBoard());

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

		// Check for consistency
		board = Board.initBitBoard();

		Board.isAttackedByKnight(40, Commons.Color.BLACK, board);
		Board.isAttackedByKnight(42, Commons.Color.BLACK, board);
		Board.isAttackedByKnight(45, Commons.Color.BLACK, board);
		Board.isAttackedByKnight(47, Commons.Color.BLACK, board);

		assertArrayEquals(board, Board.initBitBoard());
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

		// Check for consistency
		board = Board.initBitBoard();

		Board.isAttackedByKing(40, Commons.Color.BLACK, board);
		Board.isAttackedByKing(42, Commons.Color.BLACK, board);
		Board.isAttackedByKing(45, Commons.Color.BLACK, board);
		Board.isAttackedByKing(47, Commons.Color.BLACK, board);

		assertArrayEquals(board, Board.initBitBoard());

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

		// Check for consistency
		board = Board.initBitBoard();

		Board.isAttackedByRook(40, Commons.Color.BLACK, board);
		Board.isAttackedByRook(42, Commons.Color.BLACK, board);
		Board.isAttackedByRook(45, Commons.Color.BLACK, board);
		Board.isAttackedByRook(47, Commons.Color.BLACK, board);

		assertArrayEquals(board, Board.initBitBoard());
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

		// Check for consistency
		board = Board.initBitBoard();

		Board.isAttackedByBishop(40, Commons.Color.BLACK, board);
		Board.isAttackedByBishop(42, Commons.Color.BLACK, board);
		Board.isAttackedByBishop(45, Commons.Color.BLACK, board);
		Board.isAttackedByBishop(47, Commons.Color.BLACK, board);

		assertArrayEquals(board, Board.initBitBoard());

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

		// Check for consistency
		board = Board.initBitBoard();

		Board.isAttackedByQueen(40, Commons.Color.BLACK, board);
		Board.isAttackedByQueen(42, Commons.Color.BLACK, board);
		Board.isAttackedByQueen(45, Commons.Color.BLACK, board);
		Board.isAttackedByQueen(47, Commons.Color.BLACK, board);

		assertArrayEquals(board, Board.initBitBoard());

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

		// Check for consistency
		board = Board.initBitBoard();

		Board.getPawnMovesFrom(40, Commons.Color.BLACK, board);
		Board.getPawnMovesFrom(42, Commons.Color.BLACK, board);
		Board.getPawnMovesFrom(45, Commons.Color.BLACK, board);
		Board.getPawnMovesFrom(47, Commons.Color.BLACK, board);

		assertArrayEquals(board, Board.initBitBoard());

	}

	@Test
	public void testPawnAttacksFrom() {

		// White

		long bitmap = 0;
		board = Board.setPieceAtSquare(board, 32, Commons.PieceType.BISHOP,
				Commons.Color.BLACK);
		board = Board.setPieceAtSquare(board, 34, Commons.PieceType.BISHOP,
				Commons.Color.BLACK);

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

		// Black en passant
		Move move = null;
		board = Board.initBitBoard();

		board = Board.setPieceAtSquare(board, 33, Commons.PieceType.PAWN,
				Commons.Color.BLACK);
		move = new Move(48, 32, Commons.PieceType.PAWN);

		board = Board.move(move, Commons.Color.WHITE, board);

		assertEquals(1, Long.bitCount(Board.getPawnAttacksFrom(33,
				Commons.Color.BLACK, board)));

		// Check for consistency
		board = Board.initBitBoard();

		Board.getPawnAttacksFrom(40, Commons.Color.BLACK, board);
		Board.getPawnAttacksFrom(42, Commons.Color.BLACK, board);
		Board.getPawnAttacksFrom(45, Commons.Color.BLACK, board);
		Board.getPawnAttacksFrom(47, Commons.Color.BLACK, board);

		assertArrayEquals(board, Board.initBitBoard());

	}

	@Test
	public void testgetPawnAttacksAndMoves() {
		long bitmap = 0;

		// White

		for (int i = 49; i < 55; i++) {
			bitmap = 0;
			board = Board.initBitBoard();
			board = Board.setPieceAtSquare(board, i - 9,
					Commons.PieceType.BISHOP, Commons.Color.BLACK);
			board = Board.setPieceAtSquare(board, i - 7,
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
			board = Board.setPieceAtSquare(board, i + 9,
					Commons.PieceType.BISHOP, Commons.Color.WHITE);
			board = Board.setPieceAtSquare(board, i + 7,
					Commons.PieceType.BISHOP, Commons.Color.WHITE);
			bitmap = Board.setBit(bitmap, i + 8);
			bitmap = Board.setBit(bitmap, i + 16);
			bitmap = Board.setBit(bitmap, i + 9);
			bitmap = Board.setBit(bitmap, i + 7);
			assertEquals(Board.getString(bitmap), Board.getString(Board
					.getPawnAttacksAndMoves(i, Commons.Color.BLACK, board)));

		}

		// Black en passant
		Move move = null;
		board = Board.initBitBoard();

		board = Board.setPieceAtSquare(board, 33, Commons.PieceType.PAWN,
				Commons.Color.BLACK);
		move = new Move(48, 32, Commons.PieceType.PAWN);

		board = Board.move(move, Commons.Color.WHITE, board);

		assertEquals(2, Long.bitCount(Board.getPawnAttacksAndMoves(33,
				Commons.Color.BLACK, board)));

		for (int i = 33; i < 39; i++) {
			board = Board.initBitBoard();

			board = Board.setPieceAtSquare(board, i, Commons.PieceType.PAWN,
					Commons.Color.BLACK);
			move = new Move(i + 15, i + 7, Commons.PieceType.PAWN);

			board = Board.move(move, Commons.Color.WHITE, board);

			move = new Move(i + 17, i + 1, Commons.PieceType.PAWN);

			board = Board.move(move, Commons.Color.WHITE, board);

			assertEquals(3, Long.bitCount(Board.getPawnAttacksAndMoves(i,
					Commons.Color.BLACK, board)));
		}

		for (int i = 33; i < 39; i++) {
			board = Board.initBitBoard();

			board = Board.setPieceAtSquare(board, i, Commons.PieceType.PAWN,
					Commons.Color.BLACK);
			move = new Move(i + 15, i - 1, Commons.PieceType.PAWN);

			board = Board.move(move, Commons.Color.WHITE, board);

			move = new Move(i + 17, i + 1, Commons.PieceType.PAWN);

			board = Board.move(move, Commons.Color.WHITE, board);

			assertEquals(2, Long.bitCount(Board.getPawnAttacksAndMoves(i,
					Commons.Color.BLACK, board)));
		}

		// White en passant

		for (int i = 25; i < 31; i++) {
			board = Board.initBitBoard();

			board = Board.setPieceAtSquare(board, i, Commons.PieceType.PAWN,
					Commons.Color.WHITE);
			move = new Move(i - 17, i - 9, Commons.PieceType.PAWN);

			board = Board.move(move, Commons.Color.BLACK, board);

			move = new Move(i - 15, i + 1, Commons.PieceType.PAWN);

			board = Board.move(move, Commons.Color.BLACK, board);

			assertEquals(3, Long.bitCount(Board.getPawnAttacksAndMoves(i,
					Commons.Color.WHITE, board)));
		}

		for (int i = 25; i < 31; i++) {
			board = Board.initBitBoard();

			board = Board.setPieceAtSquare(board, i, Commons.PieceType.PAWN,
					Commons.Color.WHITE);
			move = new Move(i - 17, i - 1, Commons.PieceType.PAWN);

			board = Board.move(move, Commons.Color.BLACK, board);

			move = new Move(i - 15, i + 1, Commons.PieceType.PAWN);

			board = Board.move(move, Commons.Color.BLACK, board);

			assertEquals(2, Long.bitCount(Board.getPawnAttacksAndMoves(i,
					Commons.Color.WHITE, board)));
		}

		// Check for consistency
		board = Board.initBitBoard();

		Board.getPawnAttacksAndMoves(40, Commons.Color.BLACK, board);
		Board.getPawnAttacksAndMoves(42, Commons.Color.BLACK, board);
		Board.getPawnAttacksAndMoves(45, Commons.Color.BLACK, board);
		Board.getPawnAttacksAndMoves(47, Commons.Color.BLACK, board);

		assertArrayEquals(board, Board.initBitBoard());

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

		board = Board.setPieceAtSquare(board, 27, Commons.PieceType.ROOK,
				Commons.Color.BLACK);

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

		board = Board.setPieceAtSquare(board, 44, Commons.PieceType.BISHOP,
				Commons.Color.BLACK);

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

		board = Board.setPieceAtSquare(board, 19, Commons.PieceType.KNIGHT,
				Commons.Color.WHITE);

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

		board = Board.setPieceAtSquare(board, 63, Commons.PieceType.KING,
				Commons.Color.BLACK);

		moves = Board.getValidMovesForSquare(63, Commons.Color.BLACK, board);

		testMoves = new ArrayList<>();
		testMoves.add(new Move(63, 54, Commons.PieceType.KING));
		testMoves.add(new Move(63, 55, Commons.PieceType.KING));
		testMoves.add(new Move(63, 62, Commons.PieceType.KING));

		assertTrue(testMoves.containsAll(moves));

		// Queen
		board = Board.initBitBoard();

		board = Board.setPieceAtSquare(board, 63, Commons.PieceType.QUEEN,
				Commons.Color.BLACK);

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

		board = Board.setPieceAtSquare(board, 36, Commons.PieceType.ROOK,
				Commons.Color.BLACK);

		board = Board.setPieceAtSquare(board, 52, Commons.PieceType.BISHOP,
				Commons.Color.WHITE);

		moves = Board.getValidMovesForSquare(52, Commons.Color.WHITE, board);

		assertTrue(moves.isEmpty());

		// Queenside castle white
		board = Board.initBitBoard();

		board = Board.removePieceAtSquare(board, 57);
		board = Board.removePieceAtSquare(board, 58);
		board = Board.removePieceAtSquare(board, 59);

		moves = Board.getValidMovesForSquare(60, Commons.Color.WHITE, board);

		assertEquals(2, moves.size());

		// Kingside castle white
		board = Board.initBitBoard();

		board = Board.removePieceAtSquare(board, 61);
		board = Board.removePieceAtSquare(board, 62);

		moves = Board.getValidMovesForSquare(60, Commons.Color.WHITE, board);

		assertEquals(2, moves.size());

		// Queenside castle black
		board = Board.initBitBoard();

		board = Board.removePieceAtSquare(board, 1);
		board = Board.removePieceAtSquare(board, 2);
		board = Board.removePieceAtSquare(board, 3);

		moves = Board.getValidMovesForSquare(4, Commons.Color.BLACK, board);

		assertEquals(2, moves.size());

		// Kingside castle black
		board = Board.initBitBoard();

		board = Board.removePieceAtSquare(board, 5);
		board = Board.removePieceAtSquare(board, 6);

		moves = Board.getValidMovesForSquare(4, Commons.Color.BLACK, board);

		assertEquals(2, moves.size());

		// Check for consistency
		board = Board.initBitBoard();

		for (int i = 0; i < 64; i++) {
			Board.getValidMovesForSquare(i, Commons.Color.WHITE, board);
		}

		assertArrayEquals(board, Board.initBitBoard());

	}

	@Test
	public void testIsSquareOccupied() {
		// Black

		for (int i = 0; i < 16; i++) {
			assertTrue(Board.isSquareOccupied(i, board));
		}

		// Nothing

		for (int i = 16; i < 48; i++) {
			assertFalse(Board.isSquareOccupied(i, board));
		}

		// White
		for (int i = 48; i < 64; i++) {
			assertTrue(Board.isSquareOccupied(i, board));
		}

		// Check for consistency
		board = Board.initBitBoard();

		for (int i = 0; i < 64; i++) {
			Board.isSquareOccupied(i, board);
		}

		assertArrayEquals(board, Board.initBitBoard());

	}

	@Test
	public void testMove() {

		board = Board.move(new Move(48, 40, Commons.PieceType.PAWN),
				Commons.Color.WHITE, board);

		assertEquals(Commons.PieceType.PAWN,
				Board.getPieceAtSquare(board, 40, Commons.Color.WHITE));
		assertFalse(Board.isSquareOccupied(48, board));

		// Testing double moves

		// Castling

		board = Board.removePieceAtSquare(board, 61);
		board = Board.removePieceAtSquare(board, 62);

		Move move = new Move(60, 62, Commons.PieceType.KING, new Move(63, 61,
				Commons.PieceType.ROOK));

		board = Board.move(move, Commons.Color.WHITE, board);

		assertEquals(Commons.PieceType.ROOK,
				Board.getPieceAtSquare(board, 61, Commons.Color.WHITE));
		assertEquals(Commons.PieceType.KING,
				Board.getPieceAtSquare(board, 62, Commons.Color.WHITE));
		assertFalse(Board.isSquareOccupied(60, board));
		assertFalse(Board.isSquareOccupied(63, board));

		// En passant
		board = Board.setPieceAtSquare(board, 33, Commons.PieceType.PAWN,
				Commons.Color.BLACK);
		move = new Move(48, 32, Commons.PieceType.PAWN);

		board = Board.move(move, Commons.Color.WHITE, board);

		move = new Move(33, 40, Commons.PieceType.PAWN);

		board = Board.move(move, Commons.Color.BLACK, board);

		assertEquals(Commons.PieceType.PAWN,
				Board.getPieceAtSquare(board, 40, Commons.Color.BLACK));
		assertFalse(Board.isSquareOccupied(33, board));
		assertFalse(Board.isSquareOccupied(32, board));
		assertFalse(Board.isSquareOccupied(48, board));

		board = Board.setPieceAtSquare(board, 27, Commons.PieceType.PAWN,
				Commons.Color.WHITE);
		move = new Move(12, 28, Commons.PieceType.PAWN);

		board = Board.move(move, Commons.Color.BLACK, board);

		move = new Move(27, 20, Commons.PieceType.PAWN);

		board = Board.move(move, Commons.Color.WHITE, board);

		assertEquals(Commons.PieceType.PAWN,
				Board.getPieceAtSquare(board, 20, Commons.Color.WHITE));
		assertFalse(Board.isSquareOccupied(28, board));
		assertFalse(Board.isSquareOccupied(27, board));
		assertFalse(Board.isSquareOccupied(12, board));

	}

	@Test
	public void speedTest() {
		board = Board.initBitBoard();

		long time = System.nanoTime();
		int plies = 6;

		perft(plies, Commons.Color.WHITE);

		System.out.printf("Used %d seconds for %d plies \n",
				((System.nanoTime() - time) / 1000000000), plies);

	}

	@Test
	public void testRemoveAtSquare() {
		board = Board.removePieceAtSquare(board, 48);
		assertFalse(Board.isSquareOccupied(48, board));

	}

	@Test
	public void testGetValidMovesForColor() {
		List<Move> moves = Board.getValidMovesForColor(Commons.Color.WHITE,
				board);
		assertEquals(20, moves.size());

		moves = Board.getValidMovesForColor(Commons.Color.BLACK, board);
		assertEquals(20, moves.size());

	}

	@Test
	public void perftTest() {
		assertEquals(1, perft(0, Commons.Color.WHITE));
		assertEquals(400, perft(2, Commons.Color.WHITE));
		assertEquals(8902, perft(3, Commons.Color.WHITE));
		assertEquals(197281, perft(4, Commons.Color.WHITE));
//		assertEquals(4865609, perft(5, Commons.Color.WHITE));
//		assertEquals(119060324, perft(6, Commons.Color.WHITE));

		// https://chessprogramming.wikispaces.com/Perft+Results Position 3

		board = new long[2][7];

		Board.setPieceAtSquare(board, 54, Commons.PieceType.PAWN,
				Commons.Color.WHITE);
		Board.setPieceAtSquare(board, 52, Commons.PieceType.PAWN,
				Commons.Color.WHITE);
		Board.setPieceAtSquare(board, 25, Commons.PieceType.PAWN,
				Commons.Color.WHITE);

		Board.setPieceAtSquare(board, 24, Commons.PieceType.KING,
				Commons.Color.WHITE);
		Board.setPieceAtSquare(board, 33, Commons.PieceType.ROOK,
				Commons.Color.WHITE);

		Board.setPieceAtSquare(board, 19, Commons.PieceType.PAWN,
				Commons.Color.BLACK);
		Board.setPieceAtSquare(board, 37, Commons.PieceType.PAWN,
				Commons.Color.BLACK);
		Board.setPieceAtSquare(board, 10, Commons.PieceType.PAWN,
				Commons.Color.BLACK);

		Board.setPieceAtSquare(board, 31, Commons.PieceType.ROOK,
				Commons.Color.BLACK);
		Board.setPieceAtSquare(board, 39, Commons.PieceType.KING,
				Commons.Color.BLACK);

		assertEquals(14, perft(1, Commons.Color.WHITE));
		assertEquals(191, perft(2, Commons.Color.WHITE));
		assertEquals(2812, perft(3, Commons.Color.WHITE));
		assertEquals(43238, perft(4, Commons.Color.WHITE));
		assertEquals(674624, perft(5, Commons.Color.WHITE));
//		assertEquals(11030083, perft(6, Commons.Color.WHITE));
//		assertEquals(178633661, perft(7, Commons.Color.WHITE));

	}

	public long perft(int depth, int side) {
		long nodes = 0;

		if (depth == 0)
			return 1;

		List<Move> moves = Board.getValidMovesForColor(side, board);
		for (Move m : moves) {
			undoStack.push(Board.deepCopy2DArray(board));
			board = Board.move(m, side, board);
			nodes += perft(depth - 1, Board.oppositeSide(side));
			board = undoStack.pop();
		}

		return nodes;
	}

}
