package com.chess.engine.pieces;

import java.util.Collection;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.classic.Alliance;

public abstract class Piece {

	protected final int piecePosition;

	protected final Alliance pieceAlliance;

	protected final boolean isFirstMove;

	protected final PieceType pieceType;

	private final int cachedHashCode;

	Piece(final PieceType pieceType, final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
		this.pieceType = pieceType;
		this.piecePosition = piecePosition;
		this.pieceAlliance = pieceAlliance;
		this.isFirstMove = isFirstMove;
		this.cachedHashCode = computeHashCode();
	}

	private int computeHashCode() {
		int result = pieceType.hashCode();
		result = 31 * result + pieceAlliance.hashCode();
		result = 31 * result + piecePosition;
		result = 31 * result + (isFirstMove ? 1 : 0);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Piece other = (Piece) obj;
		return piecePosition == other.getPiecePosition() && pieceType == other.getPieceType()
				&& pieceAlliance == other.getPieceAlliance() && isFirstMove == other.isFirstMove();
	}

	@Override
	public int hashCode() {
		return this.cachedHashCode;
	}

	public int getPiecePosition() {
		return piecePosition;
	}

	public Alliance getPieceAlliance() {
		return pieceAlliance;
	}

	public PieceType getPieceType() {
		return this.pieceType;
	}

	public boolean isFirstMove() {
		return this.isFirstMove;
	}

	public abstract Collection<Move> calculateLegalMoves(final Board board);

	public abstract Piece movePiece(Move move);

	public int getPieceValue() {
		return this.pieceType.getPieceValue();
	}

	public enum PieceType {
		PAWN("P", 100) {

			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}

			@Override
			public boolean isPawn() {
				return true;
			}

			@Override
			public boolean isBishop() {
				return false;
			}
		},
		KNIGHT("N", 300) {

			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}

			@Override
			public boolean isPawn() {
				return false;
			}

			@Override
			public boolean isBishop() {
				return false;
			}
		},
		BISHOP("B", 300) {

			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}

			@Override
			public boolean isPawn() {
				return false;
			}

			@Override
			public boolean isBishop() {
				return true;
			}
		},
		ROOK("R", 500) {

			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return true;
			}

			@Override
			public boolean isPawn() {
				return false;
			}

			@Override
			public boolean isBishop() {
				return false;
			}
		},
		QUEEN("Q", 900) {

			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}

			@Override
			public boolean isPawn() {
				return false;
			}

			@Override
			public boolean isBishop() {
				return false;
			}
		},
		KING("K", 10000) {

			@Override
			public boolean isKing() {
				return true;
			}

			@Override
			public boolean isRook() {
				return false;
			}

			@Override
			public boolean isPawn() {
				return false;
			}

			@Override
			public boolean isBishop() {
				return false;
			}
		};

		private String pieceName;

		private int pieceValue;

		PieceType(final String pieceName, final int pieceValue) {
			this.pieceName = pieceName;
			this.pieceValue = pieceValue;
		}

		@Override
		public String toString() {
			return this.pieceName;
		}

		public int getPieceValue() {
			return this.pieceValue;
		}

		public abstract boolean isKing();

		public abstract boolean isRook();

		public abstract boolean isPawn();

		public abstract boolean isBishop();
	}
}
