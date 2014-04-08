package board;

import java.io.Serializable;

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
		long[][] bitboard = new long[2][6];

		for (int i = 0; i < 6; i++) {
			bitboard[Commons.Color.WHITE][i] = Commons.Bitmaps.WHITE_PIECES[i];
			bitboard[Commons.Color.BLACK][i] = Commons.Bitmaps.BLACK_PIECES[i];
		}

		if (Board.masks == null) {
			createMasks();
		}

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

	public static long[][] setPieceAtPosition(long[][] board, Position pos,
			int type, int color) {
		board[color][type] = board[color][type] | getMaskAtPosition(pos);
		return board;

	}

	public static int getPieceAtPosition(long[][] board, Position pos, int color) {
		for (int i = 0; i < 6; i++) {
			if ((board[color][i] & getMaskAtPosition(pos)) != 0) {
				return i;
			}
		}
		return -1;
	}

	public static long setBit(long bitmap, int index) {
		long b = bitmap | Board.masks[index];
		return b;
	}
	
	
	public static long getPieceAttacks(int pieceType, int from, long occupiedBitMap) {
		   assert(pieceType != Commons.PieceType.PAWN);
		 
		   long ts = Commons.Bitmaps.ATTACKMAP[pieceType][from];
		   for (long b = occupiedBitMap & Commons.Bitmaps.BLOCKERMAP[pieceType][from]; b != 0; b &= (b - 1)) {
		      int sq = Long.numberOfTrailingZeros(b);
		      ts &= ~Commons.Bitmaps.BEHINDMAP[from][sq];
		   }
		   return ts;
		} 
	
	
	
	//
	// CODE BELOW HERE IS NOT IN USE. IT IS FOR GENERATING BEHIND BITMAPS
	//
	

	// Behind array with behind[from][to];
	public static long[][] behind;
	public static long[][] between;
	
	/**
	 * Init the between bitmaps
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
		if (from88(s88) != -1){
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
	 * @param from in 0x88 format
	 * @param to in 0x88 format
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
