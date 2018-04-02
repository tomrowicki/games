package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorAttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

public class Knight extends Piece {

	private static final int[] CANDIDATE_MOVE_COORDINATES = {
			-17,
			-15,
			-10,
			-6,
			6,
			10,
			15,
			17
	};

	public Knight(final int piecePosition, final Alliance pieceAlliance) {
		super(PieceType.KNIGHT, piecePosition, pieceAlliance, true);
	}

	public Knight(final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
		super(PieceType.KNIGHT, piecePosition, pieceAlliance, isFirstMove);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		final List<Move> legalMoves = new ArrayList<Move>();

		for (final int currentCandidate : CANDIDATE_MOVE_COORDINATES) {
			final int candidateDestinationCoordinate = this.piecePosition + currentCandidate;

			if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				if (isFirstColumnExclusion(this.piecePosition, currentCandidate)
						|| isSecondColumnExclusion(this.piecePosition, currentCandidate)
						|| isSeventhColumnExclusion(this.piecePosition, currentCandidate)
						|| isEighthColumnExclusion(this.piecePosition, currentCandidate)) {
					continue;
				}

				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

				if (!candidateDestinationTile.isTileOccupied()) {
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
				} else {
					final Piece pieceAtDestination = candidateDestinationTile.getPiece();
					final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

					if (this.pieceAlliance != pieceAlliance) {
						legalMoves.add(
								new MajorAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
					}
				}
			}
		}
		return ImmutableList.copyOf(legalMoves);
	}

	@Override
	public String toString() {
		return Piece.PieceType.KNIGHT.toString();
	}

	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.INSTANCE.FIRST_COLUMN.get(currentPosition) && ((candidateOffset == -17)
				|| (candidateOffset == -10) || (candidateOffset == 6) || (candidateOffset == 15));
	}

	private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.INSTANCE.SECOND_COLUMN.get(currentPosition)
				&& ((candidateOffset == -10) || (candidateOffset == 6));
	}

	private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.INSTANCE.SEVENTH_COLUMN.get(currentPosition)
				&& ((candidateOffset == -6) || (candidateOffset == 10));
	}

	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.INSTANCE.EIGHTH_COLUMN.get(currentPosition) && ((candidateOffset == -15)
				|| (candidateOffset == -6) || (candidateOffset == 10) || (candidateOffset == 17));
	}

	@Override
	public Knight movePiece(Move move) {
		return new Knight(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
	}

	@Override
	public int locationBonus() {
		return this.pieceAlliance.knightBonus(this.piecePosition);
	}
}
