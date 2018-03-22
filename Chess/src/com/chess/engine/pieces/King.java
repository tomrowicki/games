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

public class King extends Piece {

	private static int[] CANDIDATE_MOVE_COORDINATES = {
			-9,
			-8,
			-7,
			-1,
			1,
			7,
			8,
			9
	};

	public King(final int piecePosition, final Alliance pieceAlliance) {
		super(PieceType.KING, piecePosition, pieceAlliance, true);
	}

	public King(final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
		super(PieceType.KING, piecePosition, pieceAlliance, isFirstMove);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		final List<Move> legalMoves = new ArrayList<Move>();

		for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
			final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;

			if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffset)
					|| isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
				continue;
			}

			if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
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
		return Piece.PieceType.KING.toString();
	}

	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition]
				&& (candidateOffset == -9 || candidateOffset == -1 || candidateOffset == 7);
	}

	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidatteOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition]
				&& (candidatteOffset == -7 || candidatteOffset == 1 || candidatteOffset == 9);
	}

	@Override
	public King movePiece(Move move) {
		return new King(move.getDesinationCoordinate(), move.getMovedPiece().getPieceAlliance());
	}
}
