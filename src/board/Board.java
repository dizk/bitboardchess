package board;

import utils.Commons;

public class Board {

	private static long[] masks;

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
	 * board[Commons.Color.WHITE][Commons.PieceType.KING]. This will return the bitmap of the king.
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
	 * @return the bitmap of the board (in order words occupied squares on the board).
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

		if (-1 < index && index < 64) {
			return Board.masks[index];
		}

		return -1;

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
				str += "1";
			} else {
				str += "0";
			}

			if ((i % 8) == 0) {
				str += "\n";
			}

			bitmap = bitmap >>> 1;
		}

		return str;
	}

}
