package com.chess.engine.board;

import com.chess.engine.board.Board.Builder;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

public abstract class Move {

	protected final Board board;

	protected final Piece movedPiece;

	protected final int destinationCoordinate;

	protected final boolean isFirstMove;

	public static final Move NULL_MOVE = new NullMove();

	private Move(final Board board, final Piece movedPiece, final int destinationCoordinate) {
		this.board = board;
		this.movedPiece = movedPiece;
		this.destinationCoordinate = destinationCoordinate;
		this.isFirstMove = movedPiece.isFirstMove();
	}

	private Move(final Board board, final int destinationCoordinate) {
		this.board = board;
		this.destinationCoordinate = destinationCoordinate;
		this.movedPiece = null;
		this.isFirstMove = false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.destinationCoordinate;
		result = prime * result + this.movedPiece.hashCode();
		result = prime * result + this.movedPiece.getPiecePosition();
		return result;
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Move)) {
			return false;
		}
		final Move otherMove = (Move) other;
		return getCurrentCoordinate() == otherMove.getCurrentCoordinate()
				&& getDesinationCoordinate() == otherMove.getDesinationCoordinate()
				&& getMovedPiece().equals(otherMove.getMovedPiece());
	}

	public int getCurrentCoordinate() {
		return this.getMovedPiece().getPiecePosition();
	}

	public int getDesinationCoordinate() {
		return this.destinationCoordinate;
	}

	public Piece getMovedPiece() {
		return this.movedPiece;
	}

	public boolean isAttack() {
		return false;
	}

	public boolean isCastlingMove() {
		return false;
	}

	public Piece getAttackedPiece() {
		return null;
	}

	public Board execute() {
		final Board.Builder builder = new Board.Builder();
		for (final Piece piece : board.currentPlayer().getActivePieces()) {
			if (!this.movedPiece.equals(piece)) {
				builder.setPiece(piece);
			}
		}

		for (final Piece piece : board.currentPlayer().getOpponent().getActivePieces()) {
			builder.setPiece(piece);
		}
		builder.setPiece(this.movedPiece.movePiece(this));
		builder.setMoveMaker(board.currentPlayer().getOpponent().getAlliance());
		return builder.build();
	}

	public static final class MajorMove extends Move {

		public MajorMove(final Board board, final Piece movePiece, final int destinationCoordinate) {
			super(board, movePiece, destinationCoordinate);
		}

		@Override
		public boolean equals(final Object other) {
			return this == other || other instanceof MajorMove && super.equals(other);
		}

		@Override
		public String toString() {
			return movedPiece.getPieceType().toString()
					+ BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
		}
	}

	public static class AttackMove extends Move {

		final Piece attackedPiece;

		public AttackMove(final Board board, final Piece movePiece, final int destinationCoordinate,
				final Piece attackedPiece) {
			super(board, movePiece, destinationCoordinate);
			this.attackedPiece = attackedPiece;
		}

		@Override
		public int hashCode() {
			return this.attackedPiece.hashCode() + super.hashCode();
		}

		@Override
		public boolean equals(final Object other) {
			if (this == other) {
				return true;
			}
			if (!(other instanceof AttackMove)) {
				return false;
			}
			final AttackMove otherAttackMove = (AttackMove) other;
			return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
		}

		@Override
		public Board execute() {
			return null;
		}

		@Override
		public boolean isAttack() {
			return true;
		}

		@Override
		public Piece getAttackedPiece() {
			return this.attackedPiece;
		}
	}

	public static final class PawnMove extends Move {

		public PawnMove(final Board board, final Piece movePiece, final int destinationCoordinate) {
			super(board, movePiece, destinationCoordinate);
		}
	}

	public static class PawnAttackMove extends AttackMove {

		public PawnAttackMove(final Board board, final Piece movePiece, final int destinationCoordinate,
				final Piece attackedPiece) {
			super(board, movePiece, destinationCoordinate, attackedPiece);
		}
	}

	public static final class PawnEnPassantAttackMove extends PawnAttackMove {

		public PawnEnPassantAttackMove(final Board board, final Piece movePiece, final int destinationCoordinate,
				final Piece attackedPiece) {
			super(board, movePiece, destinationCoordinate, attackedPiece);
		}
	}

	public static final class PawnJump extends Move {

		public PawnJump(final Board board, final Piece movePiece, final int destinationCoordinate) {
			super(board, movePiece, destinationCoordinate);
		}

		@Override
		public Board execute() {
			final Builder builder = new Builder();
			for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
				if (!this.movedPiece.equals(piece)) {
					builder.setPiece(piece);
				}
			}
			for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
				builder.setPiece(piece);
			}
			final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
			builder.setPiece(movedPawn);
			builder.setEnPassantPawn(movedPawn);
			builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
			return builder.build();
		}

		@Override
		public String toString() {
			return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
		}
	}

	static abstract class CastleMove extends Move {

		protected final Rook castleRook;

		protected final int castleRookStart;

		protected final int castleRookDestination;

		public CastleMove(final Board board, final Piece movePiece, final int destinationCoordinate,
				final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
			super(board, movePiece, destinationCoordinate);
			this.castleRook = castleRook;
			this.castleRookStart = castleRookStart;
			this.castleRookDestination = castleRookDestination;
		}

		public Rook getCastleRook() {
			return this.castleRook;
		}

		@Override
		public boolean isCastlingMove() {
			return true;
		}

		@Override
		public Board execute() {
			final Builder builder = new Builder();
			for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
				if (!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)) {
					builder.setPiece(piece);
				}
			}
			for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
				builder.setPiece(piece);
			}
			builder.setPiece(this.movedPiece.movePiece(this));
			// TODO look into the first move on normal pieces
			builder.setPiece(new Rook(this.castleRookDestination, this.castleRook.getPieceAlliance()));
			builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
			return builder.build();
		}
	}

	public static final class KingSideCastleMove extends CastleMove {

		public KingSideCastleMove(final Board board, final Piece movePiece, final int destinationCoordinate,
				final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
			super(board, movePiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
		}

		@Override
		public String toString() {
			return "0-0";
		}
	}

	public static final class QueenSideCastleMove extends CastleMove {

		public QueenSideCastleMove(final Board board, final Piece movePiece, final int destinationCoordinate,
				final Rook castleRook, final int castleRookStart, final int castleRookDestination) {
			super(board, movePiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
		}

		@Override
		public String toString() {
			return "0-0-0";
		}
	}

	public static final class NullMove extends Move {

		public NullMove() {
			super(null, -1);
		}

		@Override
		public Board execute() {
			throw new RuntimeException("Cannot execute a null move!");
		}
	}

	public static class MoveFactory {

		private MoveFactory() {
			throw new RuntimeException("Not instantiable!");
		}

		public static Move createMove(final Board board, final int currentCoordinate, final int destinationCoordinate) {
			for (final Move move : board.getAllLegalMoves()) {
				if (move.getCurrentCoordinate() == currentCoordinate
						&& move.getDesinationCoordinate() == destinationCoordinate) {
					return move;
				}
			}
			return NULL_MOVE;
		}
	}
}
