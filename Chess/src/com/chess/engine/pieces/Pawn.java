package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.PawnAttackMove;
import com.chess.engine.board.Move.PawnEnPassantAttackMove;
import com.chess.engine.board.Move.PawnJump;
import com.chess.engine.board.Move.PawnMove;
import com.chess.engine.board.Move.PawnPromotion;
import com.chess.engine.classic.Alliance;
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

	public Pawn(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove) {
		super(PieceType.PAWN, piecePosition, pieceAlliance, isFirstMove);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		final List<Move> legalMoves = new ArrayList<>();
		for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
			int candidateDestinationCoordinate = this.piecePosition
					+ (this.pieceAlliance.getDirection() * currentCandidateOffset);
			if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				continue;
			}
			if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
				if (this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
					legalMoves.add(new PawnPromotion(new PawnMove(board, this, candidateDestinationCoordinate),
							new Queen(candidateDestinationCoordinate, this.pieceAlliance)));
					legalMoves.add(new PawnPromotion(new PawnMove(board, this, candidateDestinationCoordinate),
							new Rook(candidateDestinationCoordinate, this.pieceAlliance)));
					legalMoves.add(new PawnPromotion(new PawnMove(board, this, candidateDestinationCoordinate),
							new Bishop(candidateDestinationCoordinate, this.pieceAlliance)));
					legalMoves.add(new PawnPromotion(new PawnMove(board, this, candidateDestinationCoordinate),
							new Knight(candidateDestinationCoordinate, this.pieceAlliance)));
				} else {
					legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate));
				}
			} else if (currentCandidateOffset == 16 && this.isFirstMove()
					&& ((BoardUtils.INSTANCE.SECOND_ROW.get(this.piecePosition) && this.pieceAlliance.isBlack())
							|| (BoardUtils.INSTANCE.SEVENTH_ROW.get(this.piecePosition)
									&& this.pieceAlliance.isWhite()))) {
				final int behindCandidateDestinationCoordinate = this.piecePosition
						+ (this.pieceAlliance.getDirection() * 8);
				if (!board.getTile(candidateDestinationCoordinate).isTileOccupied()
						&& !board.getTile(behindCandidateDestinationCoordinate).isTileOccupied()) {
					legalMoves.add(new PawnJump(board, this, candidateDestinationCoordinate));
				}
			} else if (currentCandidateOffset == 7 && !((BoardUtils.INSTANCE.EIGHTH_COLUMN.get(this.piecePosition)
					&& this.pieceAlliance.isWhite())
					|| (BoardUtils.INSTANCE.FIRST_COLUMN.get(this.piecePosition) && this.pieceAlliance.isBlack()))) {
				if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						if (this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
							legalMoves.add(new PawnPromotion(
									new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate),
									new Queen(candidateDestinationCoordinate, this.pieceAlliance)));
							legalMoves.add(new PawnPromotion(
									new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate),
									new Rook(candidateDestinationCoordinate, this.pieceAlliance)));
							legalMoves.add(new PawnPromotion(
									new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate),
									new Bishop(candidateDestinationCoordinate, this.pieceAlliance)));
							legalMoves.add(new PawnPromotion(
									new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate),
									new Knight(candidateDestinationCoordinate, this.pieceAlliance)));
						} else {
							legalMoves.add(
									new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
						}
					}
				} else if (board.getEnPassantPawn() != null && board.getEnPassantPawn()
						.getPiecePosition() == (this.piecePosition + (this.pieceAlliance.getOppositeDirection()))) {
					final Piece pieceOnCandidate = board.getEnPassantPawn();
					if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate,
								pieceOnCandidate));

					}
				}
			} else if (currentCandidateOffset == 9 && !((BoardUtils.INSTANCE.FIRST_COLUMN.get(this.piecePosition)
					&& this.pieceAlliance.isWhite())
					|| (BoardUtils.INSTANCE.EIGHTH_COLUMN.get(this.piecePosition) && this.pieceAlliance.isBlack()))) {
				if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					if (this.pieceAlliance != board.getTile(candidateDestinationCoordinate).getPiece()
							.getPieceAlliance()) {
						if (this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
							legalMoves.add(new PawnPromotion(
									new PawnAttackMove(board, this, candidateDestinationCoordinate,
											board.getTile(candidateDestinationCoordinate).getPiece()),
									new Queen(candidateDestinationCoordinate, this.pieceAlliance)));
							legalMoves.add(new PawnPromotion(
									new PawnAttackMove(board, this, candidateDestinationCoordinate,
											board.getTile(candidateDestinationCoordinate).getPiece()),
									new Rook(candidateDestinationCoordinate, this.pieceAlliance)));
							legalMoves.add(new PawnPromotion(
									new PawnAttackMove(board, this, candidateDestinationCoordinate,
											board.getTile(candidateDestinationCoordinate).getPiece()),
									new Bishop(candidateDestinationCoordinate, this.pieceAlliance)));
							legalMoves.add(new PawnPromotion(
									new PawnAttackMove(board, this, candidateDestinationCoordinate,
											board.getTile(candidateDestinationCoordinate).getPiece()),
									new Knight(candidateDestinationCoordinate, this.pieceAlliance)));
						} else {
							legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate,
									board.getTile(candidateDestinationCoordinate).getPiece()));
						}
					}
				} else if (board.getEnPassantPawn() != null && board.getEnPassantPawn()
						.getPiecePosition() == (this.piecePosition - (this.pieceAlliance.getOppositeDirection()))) {
					final Piece pieceOnCandidate = board.getEnPassantPawn();
					if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						legalMoves.add(new PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate,
								pieceOnCandidate));

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
		return new Pawn(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
	}

	public Piece getPromotionPiece() {
		return new Queen(this.piecePosition, this.pieceAlliance, false);
	}
}
