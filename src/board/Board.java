package board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utils.Commons;

public class Board implements Serializable {

	private static final long serialVersionUID = -6005457724438400528L;

	public static long[] masks;

	public Board() {
		createMasks();
	}

	/**
	 * Create a mask so we can get one of the 64 squares on the board.
	 * 
	 * @return
	 */
	public static void createMasks() {

		long[] mask = new long[64];

		for (int i = 0; i < 64; i++) {
			mask[i] = 1L << i;
		}
		Board.masks = mask;
	}

	/**
	 * This will create a new board, the board is represented with 12 longs with
	 * [color][type] from commons. So to access the white king you do
	 * board[Commons.Color.WHITE][Commons.PieceType.KING]. This will return the
	 * bitmap of the king.
	 * 
	 * @return a board in the start position
	 */

	public static long[][] initBitBoard() {
		long[][] bitboard = new long[2][7];

		for (int i = 0; i < 6; i++) {
			bitboard[Commons.Color.WHITE][i] = Commons.Bitmaps.WHITE_PIECES[i];
			bitboard[Commons.Color.BLACK][i] = Commons.Bitmaps.BLACK_PIECES[i];
		}

		if (Board.masks == null) {
			createMasks();
		}

		bitboard[Commons.Color.WHITE][6] = setBit(
				bitboard[Commons.Color.WHITE][6], 56);
		bitboard[Commons.Color.WHITE][6] = setBit(
				bitboard[Commons.Color.WHITE][6], 63);
		bitboard[Commons.Color.WHITE][6] = setBit(
				bitboard[Commons.Color.WHITE][6], 60);

		bitboard[Commons.Color.BLACK][6] = setBit(
				bitboard[Commons.Color.BLACK][6], 0);
		bitboard[Commons.Color.BLACK][6] = setBit(
				bitboard[Commons.Color.BLACK][6], 4);
		bitboard[Commons.Color.BLACK][6] = setBit(
				bitboard[Commons.Color.BLACK][6], 7);

		return bitboard;
	}

	/**
	 * This will return a bitmap with all positions that is occupied
	 * 
	 * @param board
	 * @return the bitmap of the board (in order words occupied squares on the
	 *         board).
	 */

	public static long getBitMap(long[][] board) {
		long bitmap = 0;
		for (int i = 0; i < 6; i++) {
			bitmap = bitmap | board[Commons.Color.WHITE][i]
					| board[Commons.Color.BLACK][i];
		}
		return bitmap;
	}

	/**
	 * Get bitmap for a specific type of piece
	 * 
	 * @param board
	 * @param type
	 * @return the bitmap of the type
	 */

	public static long getBitMapForType(long[][] board, int type) {
		long bitmap = 0;
		for (int i = 0; i < 6; i++) {
			bitmap = bitmap | board[Commons.Color.WHITE][type]
					| board[Commons.Color.BLACK][type];
		}
		return bitmap;
	}

	/**
	 * Get the bitmap for a specific color
	 * 
	 * @param board
	 * @param color
	 * @return
	 */

	public static long getBitMapForColor(long[][] board, int color) {
		long bitmap = 0;
		for (int i = 0; i < 6; i++) {
			bitmap = bitmap | board[color][i];
		}
		return bitmap;
	}

	/**
	 * This will return the index to use in the mask array to get a mask for
	 * this position.
	 * 
	 * @param position
	 * @return
	 */
	public static int getIndexAtPosition(Position position) {
		return (position.getRank() << 3) + position.getFile();
	}

	/**
	 * Return a mask of the position, if the position is valid.
	 * 
	 * @param The
	 *            Position you want a mask for
	 * @return the mask of the position
	 */

	public static long getMaskAtPosition(Position pos) {
		int index = getIndexAtPosition(pos);

		if (isValidIndex(index)) {
			return Board.masks[index];
		}

		return -1;

	}

	/**
	 * Check if a square index is valid
	 * 
	 * @param index
	 * @return true or false
	 */

	public static boolean isValidIndex(int index) {
		return -1 < index && index < 64;
	}

	/**
	 * Get the string representation of the bitmap
	 * 
	 * @param bitmap
	 * @return string with 0, 1 to see the bitboard.
	 */

	public static String getString(long bitmap) {
		String str = "";

		for (int i = 1; i <= 64; i++) {
			if ((bitmap & 1) == 1) {
				str += "1 ";
			} else {
				str += "0 ";
			}

			if ((i % 8) == 0) {
				str += "\n";
			}

			bitmap = bitmap >> 1;
		}

		return str;
	}

	/**
	 * Set a piece of a type and color at a square
	 * 
	 * @param board
	 *            the board
	 * @param square
	 *            the square to set
	 * @param type
	 *            the type of piece
	 * @param color
	 *            the color of the piece
	 * @return the board after setting piece
	 */

	public static long[][] setPieceAtSquare(long[][] board, int square,
			int type, int color) {
		board[color][type] = board[color][type] | masks[square];
		return board;

	}

	/**
	 * Remove a piece at given position of type and color
	 * 
	 * @param board
	 *            the board
	 * @param square
	 *            the square to remove the piece from
	 * @param type
	 *            the type of piece
	 * @param color
	 *            the type of color
	 * @return the board after removing piece
	 */

	public static long[][] removePieceAtSquare(long[][] board, int square,
			int type, int color) {
		board[color][type] = board[color][type] & ~masks[square];
		return board;
	}

	/**
	 * Remove a piece at a given position
	 * 
	 * @param board
	 *            the board to remove the piece from
	 * @param square
	 *            the square where you want to remove the piece
	 * @return the board after removing the piece.
	 */

	public static long[][] removePieceAtSquare(long[][] board, int square) {
		for (int i = 0; i < 6; i++) {
			board[Commons.Color.BLACK][i] = board[Commons.Color.BLACK][i]
					& ~masks[square];
			board[Commons.Color.WHITE][i] = board[Commons.Color.WHITE][i]
					& ~masks[square];
		}
		return board;
	}

	/**
	 * Get a piece at a given square
	 * 
	 * @param board
	 *            the board
	 * @param square
	 *            the square to get the piece at.
	 * @param color
	 *            the color of the piece.
	 * @return the integer representing the piecetype or -1 if nothing was
	 *         found.
	 */

	public static int getPieceAtSquare(long[][] board, int square, int color) {
		for (int i = 0; i < 6; i++) {
			if ((board[color][i] & masks[square]) != 0) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Set a bit at a given index.
	 * 
	 * @param bitmap
	 *            the bitmap you want to set a bit on
	 * @param index
	 *            the index you want to set a bit on.
	 * @return the new bitmap
	 */

	public static long setBit(long bitmap, int index) {
		long b = bitmap | Board.masks[index];
		return b;
	}

	/**
	 * Returns a bitmap of possible attacks from a square.
	 * 
	 * @param pieceType
	 *            the type of piece.
	 * @param from
	 *            the square the piece will attack from.
	 * @param occupiedBitMap
	 *            a bitmap of all occupied positions on the board.
	 * @return a bitmap of possible attacks. WARNING ALSO INCLUDES ATTACKS AT
	 *         YOUR OWN PIECES
	 */

	public static long getPieceAttacks(int pieceType, int from,
			long occupiedBitMap) {

		long ts = Commons.Bitmaps.ATTACKMAP[pieceType][from];
		for (long b = occupiedBitMap
				& Commons.Bitmaps.BLOCKERMAP[pieceType][from]; b != 0; b &= (b - 1)) {
			int sq = Long.numberOfTrailingZeros(b);
			ts &= ~Commons.Bitmaps.BEHINDMAP[from][sq];
		}
		return ts;
	}

	/**
	 * Find the attacks a pawn can make for a given position.
	 * 
	 * @param square
	 *            the square to attack from.
	 * @param side
	 *            the side that attacks.
	 * @param board
	 *            the board to calculate on.
	 * @return a bitmap of the attacks.
	 */

	protected static long getPawnAttacksFrom(int square, int side,
			long[][] board) {
		return Commons.Bitmaps.PAWN_ATTACKS[side][square]
				& getBitMapForColor(board, oppositeSide(side));
	}

	/**
	 * Check if a square is under attack.
	 * 
	 * @param square
	 *            the square to check.
	 * @param side
	 *            the color under attack.
	 * @param board
	 *            the board to check.
	 * @return boolean if the square is under attack or not.
	 */

	public static boolean isAttacked(int square, int side, long[][] board) {
		if (isAttackedByPawn(square, side, board)) {
			return true;
		}

		if (isAttackedByKnight(square, side, board)) {
			return true;
		}

		if (isAttackedByKing(square, side, board)) {
			return true;
		}

		if (isAttackedByRook(square, side, board)) {
			return true;
		}

		if (isAttackedByQueen(square, side, board)) {
			return true;
		}

		if (isAttackedByBishop(square, side, board)) {
			return true;
		}

		return false;
	}

	protected static boolean isAttackedByKnight(int square, int side,
			long[][] board) {
		return (board[oppositeSide(side)][Commons.PieceType.KNIGHT] & Commons.Bitmaps.ATTACKMAP[Commons.PieceType.KNIGHT][square]) != 0;
	}

	protected static boolean isAttackedByPawn(int square, int side,
			long[][] board) {
		return (board[oppositeSide(side)][Commons.PieceType.PAWN] & Commons.Bitmaps.PAWN_ATTACKS[side][square]) != 0;
	}

	protected static boolean isAttackedByKing(int square, int side,
			long[][] board) {
		return (board[oppositeSide(side)][Commons.PieceType.KING] & Commons.Bitmaps.ATTACKMAP[Commons.PieceType.KING][square]) != 0;
	}

	protected static boolean isAttackedByRook(int square, int side,
			long[][] board) {
		long bitmap = Commons.Bitmaps.ATTACKMAP[Commons.PieceType.ROOK][square]
				& board[oppositeSide(side)][Commons.PieceType.ROOK];
		for (long b = bitmap; b != 0; b &= (b - 1)) {
			int from = Long.numberOfTrailingZeros(b);
			if ((Board.getBitMap(board) & Commons.Bitmaps.BETWEENMAP[from][square]) == 0) {
				return true;
			}
		}
		return false;
	}

	protected static boolean isAttackedByBishop(int square, int side,
			long[][] board) {
		long bitmap = Commons.Bitmaps.ATTACKMAP[Commons.PieceType.BISHOP][square]
				& board[oppositeSide(side)][Commons.PieceType.BISHOP];
		for (long b = bitmap; b != 0; b &= (b - 1)) {
			int from = Long.numberOfTrailingZeros(b);
			if ((Board.getBitMap(board) & Commons.Bitmaps.BETWEENMAP[from][square]) == 0) {
				return true;
			}
		}
		return false;
	}

	protected static boolean isAttackedByQueen(int square, int side,
			long[][] board) {
		long bitmap = Commons.Bitmaps.ATTACKMAP[Commons.PieceType.QUEEN][square]
				& board[oppositeSide(side)][Commons.PieceType.QUEEN];
		for (long b = bitmap; b != 0; b &= (b - 1)) {
			int from = Long.numberOfTrailingZeros(b);
			if ((Board.getBitMap(board) & Commons.Bitmaps.BETWEENMAP[from][square]) == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the opposite side
	 * 
	 * @param side
	 * @return the other side.
	 */

	public static int oppositeSide(int side) {
		if (side == Commons.Color.WHITE) {
			return Commons.Color.BLACK;
		} else {
			return Commons.Color.WHITE;
		}
	}

	/**
	 * Get all pawn move from a square
	 * 
	 * @param square
	 *            the square to check moves from
	 * @param side
	 *            the color of the pawn
	 * @param board
	 *            the board the check
	 * @return a bitmap of the pawn
	 */

	public static long getPawnMovesFrom(int square, int side, long[][] board) {
		long bitmap = 0;
		long occupied = getBitMap(board);

		if ((masks[square] & Commons.Bitmaps.RANKS[0]) != 0) {
			return bitmap;
		}

		if ((masks[square] & Commons.Bitmaps.RANKS[7]) != 0) {
			return bitmap;
		}

		bitmap = setBit(bitmap, square);
		if (side == Commons.Color.WHITE) {
			if ((masks[square] & Commons.Bitmaps.RANKS[1]) != 0) {
				// Check if we can move forward by two.
				if ((Commons.Bitmaps.BETWEENMAP[square][square - 24] & occupied) == 0) {
					return bitmap >>> 16 | bitmap >>> 8;
				} else if ((Commons.Bitmaps.BETWEENMAP[square][square - 16] & occupied) == 0) {
					return bitmap >>> 8;
				}
				// Check if we can move forward by one.
			} else if ((masks[square - 8] & occupied) == 0) {
				return bitmap >>> 8;
			}
		} else {
			if ((masks[square] & Commons.Bitmaps.RANKS[6]) != 0) {
				// Check if we can move forward by two.
				if ((Commons.Bitmaps.BETWEENMAP[square][square + 24] & occupied) == 0) {
					return bitmap << 16 | bitmap << 8;
				} else if ((Commons.Bitmaps.BETWEENMAP[square][square + 16] & occupied) == 0) {
					return bitmap << 8;
				}
				// Check if we can move forward by one.
			} else if ((masks[square + 8] & occupied) == 0) {
				return bitmap << 8;
			}
		}
		return 0;
	}

	protected static int getKingIndex(int side, long[][] board) {
		long bitmap = board[side][Commons.PieceType.KING];
		return Long.numberOfTrailingZeros(bitmap);
	}

	protected static boolean isSquareOccupied(int square, long[][] board) {
		long bitmap = getBitMap(board);
		return (masks[square] & bitmap) != 0;

	}

	/**
	 * Get pawn attack and moves from a square
	 * 
	 * @param square
	 *            from this square
	 * @param side
	 *            of the pawn
	 * @param board
	 *            the board to check
	 * @return bitmap of the possible pawn attacks and moves
	 */

	public static long getPawnAttacksAndMoves(int square, int side,
			long[][] board) {
		return getPawnAttacksFrom(square, side, board)
				| getPawnMovesFrom(square, side, board);
	}

	public static List<Move> getValidMovesForSquare(int square, int side,
			long[][] board) {
		List<Move> moves = new ArrayList<>();
		int kingIndex = getKingIndex(side, board);
		Move move = null;
		long[][] moveBoard = null;

		int type = getPieceAtSquare(board, square, side);
		long bitmap = 0;
		int squareTo = -1;

		if (type == Commons.PieceType.PAWN) {
			bitmap = getPawnAttacksAndMoves(square, side, board);
		} else {
			bitmap = getPieceAttacks(type, square, getBitMap(board))
					& ~getBitMapForColor(board, side);
		}

		while (bitmap != 0) {
			moveBoard = board;
			squareTo = Long.numberOfTrailingZeros(bitmap);

			move = new Move(square, squareTo, type);
			moveBoard = move(move, side, moveBoard);
			if (!isAttacked(kingIndex, side, moveBoard)) {
				moves.add(move);
			}

			bitmap &= bitmap - 1;
		}

		return moves;
	}

	/**
	 * Just moves a piece not checking anything
	 * 
	 * @param move
	 *            the move to make
	 * @param side
	 *            the side that makes a move
	 * @param board
	 *            the board to make the move on.
	 * @return the board with the move done.
	 */

	public static long[][] move(Move move, int side, long[][] board) {
		// Remove the pieces
		board = removePieceAtSquare(board, move.getFrom());
		board = removePieceAtSquare(board, move.getTo());
		
		if (move.hasExtraMove()){
			if (move.getExtraMove().getTo() != -1){
				move(move.getExtraMove(), side, board);				
			} else {
				board = removePieceAtSquare(board, move.getExtraMove().getFrom());
			}
		}
		
		
		// Set the pieces
		board = setPieceAtSquare(board, move.getTo(), move.getType(), side);
		return board;
	}

	//
	// CODE BELOW HERE IS NOT IN USE. IT IS FOR GENERATING BEHIND BITMAPS
	//

	// Behind array with behind[from][to];
	public static long[][] behind;
	public static long[][] between;

	/**
	 * Init the between bitmaps
	 * 
	 * @param from
	 * @param to
	 * @return
	 */

	public static long betweenInit(int from, int to) {

		from = to88(from);
		to = to88(to);

		long b = 0;

		int inc = delta_inc(from, to);

		if (inc != 0) {
			for (int sq = from + inc; sq != to; sq += inc) {
				b = setBit(b, from88(sq));
			}
		}

		return b;
	}

	/**
	 * Init one of the behind bitmaps
	 * 
	 * @param from
	 * @param to
	 * @return
	 */

	public static long behindInit(int from, int to) {

		from = to88(from);
		to = to88(to);

		long b = 0;

		int inc = delta_inc(from, to);

		if (inc != 0) {
			for (int sq = to + inc; isValid88(sq); sq += inc) {
				b = setBit(b, from88(sq));
			}
		}

		return b;
	}

	/**
	 * Create the behind bit array
	 */
	public static void initBehind() {
		behind = new long[64][64];
		for (int i = 0; i < 64; i++) {
			for (int j = 0; j < 64; j++) {
				behind[i][j] = behindInit(i, j);
			}
		}
	}

	/**
	 * Create the behind bit array
	 */
	public static void initBetween() {
		between = new long[64][64];
		for (int i = 0; i < 64; i++) {
			for (int j = 0; j < 64; j++) {
				between[i][j] = betweenInit(i, j);
			}
		}
	}

	/**
	 * Check if it is a valid s88 square
	 * 
	 * @param s88
	 * @return
	 */
	public static boolean isValid88(int s88) {
		if (from88(s88) != -1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get the 0x88 notation from index
	 * 
	 * @param index
	 * @return
	 */

	public static int to88(int index) {
		return Commons.INDEX_TO_88[index];
	}

	/**
	 * Returns the index of a square from 0x88 notation.
	 * 
	 * @param int88
	 * @return a valid index or -1 of not.
	 */
	public static int from88(int int88) {
		for (int i = 0; i < 64; i++) {
			if (Commons.INDEX_TO_88[i] == int88) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Find the direction from an index to an index
	 * 
	 * @param from
	 *            in 0x88 format
	 * @param to
	 *            in 0x88 format
	 * @return the number to increase the index with
	 */

	public static int delta_inc(int from, int to) {
		for (int dir = 0; dir < 8; dir++) {
			int inc = Commons.SQUARE_DIRECTIONS88[dir];
			for (int sq = from + inc; isValid88(sq); sq += inc) {
				if (sq == to) {
					System.out.println("delta_inc ret: " + inc);
					return inc;
				}
			}
		}
		return 0;
	}

}
