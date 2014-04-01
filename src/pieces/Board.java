package pieces;

public class Board {
	
	private static long[] mask;
	
	public Board(){
		createMasks();
	}
	
	
	/**
	 * Create a mask so we can get one of the 64 squares on the board.
	 * @return
	 */
	public static void createMasks(){
		
		long[] mask = new long[64]; 
		
		for (int i = 0; i < 64; i++){
			mask[i] = 1L << i;
		}
		
		Board.mask = mask;
		
	}
	
	
	/**
	 * This will create a new board
	 * @return the board created
	 */
	
	public static long[][] initBitBoard() {
		long[][] bitboard = new long[2][6];
		
		for (int i = 0; i < 6; i++){
			bitboard[Commons.Color.WHITE][i] = Commons.Bitmaps.WHITE_PIECES[i];
			bitboard[Commons.Color.BLACK][i] = Commons.Bitmaps.BLACK_PIECES[i];
		}
		
		if (Board.mask == null){
			createMasks();
		}
		
		
		return bitboard;
	}

	/**
	 * This will return a bitmap with all positions that is occupied
	 * @param board
	 * @return
	 */
	
	
	public static long getOccupiedBitMap(long[][] board) {
		long bitmap = 0;
		for (int i = 0; i < 6; i++){
			bitmap = bitmap | board[Commons.Color.WHITE][i] | board[Commons.Color.BLACK][i];
		}
		return bitmap;
	}
	
	/**
	 * This will return the index to use in the mask array to get a mask for this position.
	 * @param position
	 * @return
	 */
	public static int getIndexAtPosition(Position position) {
        return (position.getRank() << 3) + position.getFile();
    }

}
