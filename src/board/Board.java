package board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
				& board[oppositeSide(side)][6]
				| Commons.Bitmaps.PAWN_ATTACKS[side][square]
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

	/**
	 * Get all valid moves from a square
	 * 
	 * @param square
	 * @param side
	 * @param board
	 * @return a list of valid moves
	 */

	public static List<Move> getValidMovesForSquare(int square, int side,
			long[][] board) {
		
		// TODO refactor

		List<Move> moves = new ArrayList<>();
		Move move = null;
		long[][] moveBoard = null;

		int type = getPieceAtSquare(board, square, side);
		long bitmap = 0;
		int squareTo = -1;

		if (type == -1) {
			return null;
		}

		switch (type) {
		case Commons.PieceType.PAWN:
			bitmap = getPawnAttacksAndMoves(square, side, board);

			// Promotion
			// White
			if (side == Commons.Color.WHITE) {
				if ((bitmap & Commons.Bitmaps.RANKS[7]) != 0) {
					while (bitmap != 0) {
						moveBoard = deepCopy2DArray(board);
						squareTo = Long.numberOfTrailingZeros(bitmap);
						move = new Move(square, squareTo,
								Commons.PieceType.ROOK);
						moveBoard = move(move, side, moveBoard);
						if (!isAttacked(getKingIndex(side, moveBoard), side,
								moveBoard)) {
							moves.add(move);
						}
						move = new Move(square, squareTo,
								Commons.PieceType.QUEEN);
						moveBoard = move(move, side, moveBoard);
						if (!isAttacked(getKingIndex(side, moveBoard), side,
								moveBoard)) {
							moves.add(move);
						}
						move = new Move(square, squareTo,
								Commons.PieceType.KNIGHT);
						moveBoard = move(move, side, moveBoard);
						if (!isAttacked(getKingIndex(side, moveBoard), side,
								moveBoard)) {
							moves.add(move);
						}
						move = new Move(square, squareTo,
								Commons.PieceType.BISHOP);
						moveBoard = move(move, side, moveBoard);
						if (!isAttacked(getKingIndex(side, moveBoard), side,
								moveBoard)) {
							moves.add(move);
						}

						bitmap &= (bitmap - 1);
					}

					return moves;
				}
			} else {
				// Black
				if ((bitmap & Commons.Bitmaps.RANKS[0]) != 0) {
					while (bitmap != 0) {
						moveBoard = deepCopy2DArray(board);
						squareTo = Long.numberOfTrailingZeros(bitmap);
						move = new Move(square, squareTo,
								Commons.PieceType.ROOK);
						moveBoard = move(move, side, moveBoard);
						if (!isAttacked(getKingIndex(side, moveBoard), side,
								moveBoard)) {
							moves.add(move);
						}
						move = new Move(square, squareTo,
								Commons.PieceType.QUEEN);
						moveBoard = move(move, side, moveBoard);
						if (!isAttacked(getKingIndex(side, moveBoard), side,
								moveBoard)) {
							moves.add(move);
						}
						move = new Move(square, squareTo,
								Commons.PieceType.KNIGHT);
						moveBoard = move(move, side, moveBoard);
						if (!isAttacked(getKingIndex(side, moveBoard), side,
								moveBoard)) {
							moves.add(move);
						}
						move = new Move(square, squareTo,
								Commons.PieceType.BISHOP);
						moveBoard = move(move, side, moveBoard);
						if (!isAttacked(getKingIndex(side, moveBoard), side,
								moveBoard)) {
							moves.add(move);
						}

						bitmap &= (bitmap - 1);
					}

					return moves;
				}
			}

			break;

		case Commons.PieceType.KING:
			bitmap = getPieceAttacks(type, square, getBitMap(board))
					& ~getBitMapForColor(board, side);

			// Check if king has moved
			if ((board[side][Commons.PieceType.KING] & board[side][6]) != 0) {
				if (side == Commons.Color.WHITE) {
					// Check if rook has moved
					if ((masks[56] & board[side][6]) != 0) {
						if ((Commons.Bitmaps.BETWEENMAP[56][60] & getBitMap(board)) == 0) {
							moves.add(new Move(60, 57, Commons.PieceType.KING,
									new Move(56, 58, Commons.PieceType.ROOK)));
						}
					}

					if ((masks[63] & board[side][6]) != 0) {
						if ((Commons.Bitmaps.BETWEENMAP[60][63] & getBitMap(board)) == 0) {
							moves.add(new Move(60, 62, Commons.PieceType.KING,
									new Move(63, 61, Commons.PieceType.ROOK)));
						}
					}
				} else {

					// Check if rook has moved
					if ((masks[0] & board[side][6]) != 0) {
						if ((Commons.Bitmaps.BETWEENMAP[0][4] & getBitMap(board)) == 0) {
							moves.add(new Move(4, 1, Commons.PieceType.KING,
									new Move(0, 2, Commons.PieceType.ROOK)));
						}
					}

					if ((masks[7] & board[side][6]) != 0) {
						if ((Commons.Bitmaps.BETWEENMAP[4][7] & getBitMap(board)) == 0) {
							moves.add(new Move(4, 6, Commons.PieceType.KING,
									new Move(7, 5, Commons.PieceType.ROOK)));
						}
					}
				}

			}

			break;

		default:
			bitmap = getPieceAttacks(type, square, getBitMap(board))
					& ~getBitMapForColor(board, side);
		}

		while (bitmap != 0) {
			moveBoard = deepCopy2DArray(board);
			squareTo = Long.numberOfTrailingZeros(bitmap);
			move = new Move(square, squareTo, type);
			moveBoard = move(move, side, moveBoard);
			if (!isAttacked(getKingIndex(side, moveBoard), side, moveBoard)) {
				moves.add(move);
			}

			bitmap &= (bitmap - 1);
		}

		return moves;
	}

	public static List<Move> getValidMovesForColor(int side, long[][] board) {
		long bitmap = getBitMapForColor(board, side);
		int square;
		List<Move> moves = new ArrayList<Move>();

		while (bitmap != 0) {
			square = Long.numberOfTrailingZeros(bitmap);
			moves.addAll(getValidMovesForSquare(square, side, board));
			bitmap &= (bitmap - 1);
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

		// Clear en passant
		if (side == Commons.Color.BLACK) {
			board[side][6] &= ~Commons.Bitmaps.RANKS[5];
		} else {
			board[side][6] &= ~Commons.Bitmaps.RANKS[2];
		}

		switch (move.getType()) {
		case Commons.PieceType.PAWN:
			// Double push
			if (Math.abs(move.getTo() - move.getFrom()) == 16) {
				if (side == Commons.Color.BLACK) {
					board[side][6] = setBit(board[side][6], move.getTo() - 8);
				} else {
					board[side][6] = setBit(board[side][6], move.getTo() + 8);
				}
			}

			// If en passant move remove the right piece
			if ((masks[move.getTo()] & board[oppositeSide(side)][6]) != 0) {
				if (side == Commons.Color.BLACK) {
					board = removePieceAtSquare(board, move.getTo() - 8);
				} else {
					board = removePieceAtSquare(board, move.getTo() + 8);
				}
			}
			break;

		case Commons.PieceType.KING:
			// Clear king has moved flag
			board[side][6] &= ~masks[move.getFrom()];
			break;

		case Commons.PieceType.ROOK:
			// Clear rook has moved flag
			board[side][6] &= ~masks[move.getFrom()];
			break;
		}

		if (move.hasExtraMove()) {
			if (move.getExtraMove().getTo() != -1) {
				move(move.getExtraMove(), side, board);
			} else {
				board = removePieceAtSquare(board, move.getExtraMove()
						.getFrom());
			}
		}

		// Set the pieces
		board = setPieceAtSquare(board, move.getTo(), move.getType(), side);
		return board;
	}

	/**
	 * Can copy a 2D array.
	 * 
	 * @param array
	 *            the 2D array to copy
	 * @return the 2D array cloned.
	 */

	public static long[][] deepCopy2DArray(long[][] array) {
		long[][] ret = new long[array.length][];
		for (int i = 0; i < array.length; i++) {
			ret[i] = Arrays.copyOf(array[i], array[i].length);
		}
		return ret;
	}
}
