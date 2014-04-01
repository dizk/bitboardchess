package pieces;

public class Commons {
	
	// The files we have
	
    public static class File {
        public static final int A = 0;
        public static final int B = 1;
        public static final int C = 2;
        public static final int D = 3;
        public static final int E = 4;
        public static final int F = 5;
        public static final int G = 6;
        public static final int H = 7;
    }
    
    // The different kind of pieces we have
    
    public static class PieceType {
        public static final int KING   = 0;
        public static final int QUEEN  = 1;
        public static final int ROOK   = 2;
        public static final int BISHOP = 3;
        public static final int KNIGHT = 4;
        public static final int PAWN   = 5;
    }
    
    // Inital positions in bitmap format
    public static class Bitmaps {
        

        public static final long[] WHITE_PIECES = {
                0x1000000000000000L, /* KING   */
                0x0800000000000000L, /* QUEEN  */
                0x8100000000000000L, /* ROOK   */
                0x2400000000000000L, /* BISHOP */
                0x4200000000000000L, /* KNIGHT */
                0x00FF000000000000L, /* PAWN   */
        };
        

        public static final long[] BLACK_PIECES = {
                0x0000000000000010L, /* KING   */
                0x0000000000000008L, /* QUEEN  */
                0x0000000000000081L, /* ROOK   */
                0x0000000000000024L, /* BISHOP */
                0x0000000000000042L, /* KNIGHT */
                0x000000000000FF00L, /* PAWN   */
        };
        
        // Ranks in bitmap
        public static final long[] RANKS = {
                0xFF00000000000000L, /* 1 */
                0x00FF000000000000L, /* 2 */
                0x0000FF0000000000L, /* 3 */
                0x000000FF00000000L, /* 4 */
                0x00000000FF000000L, /* 5 */
                0x0000000000FF0000L, /* 6 */
                0x000000000000FF00L, /* 7 */
                0x00000000000000FFL, /* 8 */
        };
        
        // Files as bitmap
        public static final long[] FILES = {
                0x0101010101010101L, /* A */
                0x0202020202020202L, /* B */
                0x0404040404040404L, /* C */
                0x0808080808080808L, /* D */
                0x1010101010101010L, /* E */
                0x2020202020202020L, /* F */
                0x4040404040404040L, /* G */
                0x8080808080808080L, /* H */
        };
    }
	
	
	// The two colors we have.
	public static class Color {
		public static final int BLACK = 0;
		public static final int WHITE = 1;
	}
	

}
