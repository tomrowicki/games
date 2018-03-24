package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorAttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.classic.Alliance;
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
		return BoardUtils.FIRST_COLUMN[currentPosition]
				&& (candidateOffset == -17 || candidateOffset == -10 || candidateOffset == 6 || candidateOffset == 15);
	}

	private static boolean isSecondColumnExclusion(final int currentPosition, final int candidatteOffset) {
		return BoardUtils.SECOND_COLUMN[currentPosition] && (candidatteOffset == -10 || candidatteOffset == 6);
	}

	private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidatteOffset) {
		return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidatteOffset == -6 || candidatteOffset == 10);
	}

	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidatteOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidatteOffset == -15 || candidatteOffset == -6
				|| candidatteOffset == 10 || candidatteOffset == 17);
	}

	@Override
	public Knight movePiece(Move move) {
		return new Knight(move.getDesinationCoordinate(), move.getMovedPiece().getPieceAlliance());
	}
}
