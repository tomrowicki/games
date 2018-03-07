package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.google.common.collect.ImmutableList;

public class Pawn extends Piece {

	private static int[] CANDIDATE_MOVE_COORDINATES = {
			8,
			16,
			7,
			9
	};

	public Pawn(final int piecePosition, final Alliance pieceAlliance) {
		super(PieceType.PAWN, piecePosition, pieceAlliance, true);
	}

	public Pawn(final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove) {
		super(PieceType.PAWN, piecePosition, pieceAlliance, isFirstMove);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		final List<Move> legalMoves = new ArrayList<Move>();

		for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
			final int candidateDestinationCoordinate = this.piecePosition
					+ (this.getPieceAlliance().getDirection() * currentCandidateOffset);

			if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				continue;
			}

			if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
				// TODO more work to come - deal with promotions
				legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
			} else if (currentCandidateOffset == 16 && this.isFirstMove()
					&& ((BoardUtils.SEVENTH_RANK[this.piecePosition] && this.getPieceAlliance().isBlack())
							|| (BoardUtils.SECOND_RANK[this.piecePosition] && this.getPieceAlliance().isWhite()))) {
				final int behindCandidateDestinationCoordinate = this.piecePosition
						+ (this.pieceAlliance.getDirection() * 8);
				if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied()
						&& !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					legalMoves.add(new Move.PawnJump(board, this, candidateDestinationCoordinate));
				}
			} else if (currentCandidateOffset == 7
					&& !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite())
							|| (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
				if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandiate = board.getTile(candidateDestinationCoordinate).getPiece();
					if (this.pieceAlliance != pieceOnCandiate.getPieceAlliance()) {
						legalMoves.add(
								new Move.PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandiate));
					}
				}
			} else if (currentCandidateOffset == 9
					&& ((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite())
							|| (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
				if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandiate = board.getTile(candidateDestinationCoordinate).getPiece();
					if (this.pieceAlliance != pieceOnCandiate.getPieceAlliance()) {
						legalMoves.add(
								new Move.PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandiate));
					}
				}
			}
		}

		return ImmutableList.copyOf(legalMoves);
	}

	@Override
	public String toString() {
		return Piece.PieceType.PAWN.toString();
	}

	@Override
	public Pawn movePiece(Move move) {
		return new Pawn(move.getDesinationCoordinate(), move.getMovedPiece().getPieceAlliance());
	}
}
