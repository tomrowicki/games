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

public final class Rook extends Piece {

	private final static int[] CANDIDATE_MOVE_COORDINATES = {
			-8,
			-1,
			1,
			8
	};

	public Rook(final int piecePosition, final Alliance alliance) {
		super(PieceType.ROOK, piecePosition, alliance, true);
	}

	public Rook(final int piecePosition, final Alliance alliance, final boolean isFirstMove) {
		super(PieceType.ROOK, piecePosition, alliance, isFirstMove);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		final List<Move> legalMoves = new ArrayList<>();
		for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
			int candidateDestinationCoordinate = this.piecePosition;
			while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				if (isColumnExclusion(currentCandidateOffset, candidateDestinationCoordinate)) {
					break;
				}
				candidateDestinationCoordinate += currentCandidateOffset;
				if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
					final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
					if (!candidateDestinationTile.isTileOccupied()) {
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
					} else {
						final Piece pieceAtDestination = candidateDestinationTile.getPiece();
						final Alliance pieceAtDestinationAllegiance = pieceAtDestination.getPieceAlliance();
						if (this.pieceAlliance != pieceAtDestinationAllegiance) {
							legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate,
									pieceAtDestination));
						}
						break;
					}
				}
			}
		}
		return ImmutableList.copyOf(legalMoves);
	}

	@Override
	public int locationBonus() {
		return this.pieceAlliance.rookBonus(this.piecePosition);
	}

	@Override
	public Rook movePiece(final Move move) {
		return PieceUtils.INSTANCE.getMovedRook(move.getMovedPiece().getPieceAlliance(),
				move.getDestinationCoordinate());
	}

	@Override
	public String toString() {
		return this.pieceType.toString();
	}

	private static boolean isColumnExclusion(final int currentCandidate, final int candidateDestinationCoordinate) {
		return (BoardUtils.INSTANCE.FIRST_COLUMN.get(candidateDestinationCoordinate) && (currentCandidate == -1))
				|| (BoardUtils.INSTANCE.EIGHTH_COLUMN.get(candidateDestinationCoordinate) && (currentCandidate == 1));
	}

}