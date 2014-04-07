package utils;

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
	
	/**
	 * Pre-calculated rook attacks for each position on the entire board.
	 */
	public static final long[] ROOK_ATTACKS = {
		0x01010101010101FEL,
		0x02020202020202FDL,
		0x04040404040404FBL,
		0x08080808080808F7L,
		0x10101010101010EFL,
		0x20202020202020DFL, // 5
		0x40404040404040BFL,
		0x808080808080807FL,
		0x010101010101FE01L,
		0x020202020202FD02L,
		0x040404040404FB04L, // 10
		0x080808080808F708L,
		0x101010101010EF10L,
		0x202020202020DF20L,
		0x404040404040BF40L,
		0x8080808080807F80L, // 15
		0x0101010101FE0101L,
		0x0202020202FD0202L,
		0x0404040404FB0404L,
		0x0808080808F70808L,
		0x1010101010EF1010L, // 20
		0x2020202020DF2020L,
		0x4040404040BF4040L,
		0x80808080807F8080L,
		0x01010101FE010101L,
		0x02020202FD020202L, // 25
		0x04040404FB040404L,
		0x08080808F7080808L,
		0x10101010EF101010L,
		0x20202020DF202020L,
		0x40404040BF404040L, // 30
		0x808080807F808080L,
		0x010101FE01010101L,
		0x020202FD02020202L,
		0x040404FB04040404L,
		0x080808F708080808L, // 35
		0x101010EF10101010L,
		0x202020DF20202020L,
		0x404040BF40404040L,
		0x8080807F80808080L,
		0x0101FE0101010101L, // 40
		0x0202FD0202020202L,
		0x0404FB0404040404L,
		0x0808F70808080808L,
		0x1010EF1010101010L,
		0x2020DF2020202020L, // 45
		0x4040BF4040404040L,
		0x80807F8080808080L,
		0x01FE010101010101L,
		0x02FD020202020202L,
		0x04FB040404040404L, // 50
		0x08F7080808080808L,
		0x10EF101010101010L,
		0x20DF202020202020L,
		0x40BF404040404040L,
		0x807F808080808080L, // 55
		0xFE01010101010101L,
		0xFD02020202020202L,
		0xFB04040404040404L,
		0xF708080808080808L,
		0xEF10101010101010L, // 60
		0xDF20202020202020L,
		0xBF40404040404040L,
		0x7F80808080808080L,
	};
	
	public static final long[] BISHOP_ATTACKS = {
		
	};
}
