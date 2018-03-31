package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorAttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Tile;
import com.chess.engine.classic.Alliance;
import com.google.common.collect.ImmutableList;

public class Rook extends Piece {

	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {
			-8,
			-1,
			1,
			8
	};

	public Rook(final int piecePosition, final Alliance pieceAlliance) {
		super(PieceType.ROOK, piecePosition, pieceAlliance, true);
	}

	public Rook(final int piecePosition, final Alliance pieceAlliance, final boolean isFirstPosition) {
		super(PieceType.ROOK, piecePosition, pieceAlliance, isFirstPosition);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		final List<Move> legalMoves = new ArrayList<Move>();
		for (final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES) {
			int candidateDestinationCoordinate = this.piecePosition;
			while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				if (isColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
					break;
				}

				candidateDestinationCoordinate += candidateCoordinateOffset;
				if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
					final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

					if (!candidateDestinationTile.isTileOccupied()) {
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
					} else {
						final Piece pieceAtDestination = candidateDestinationTile.getPiece();
						final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

						if (this.pieceAlliance != pieceAlliance) {
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
	public String toString() {
		return Piece.PieceType.ROOK.toString();
	}

	private static boolean isColumnExclusion(final int currentCandidate, final int candidateDestinationCoordinate) {
		return (BoardUtils.INSTANCE.FIRST_COLUMN.get(candidateDestinationCoordinate) && (currentCandidate == -1))
				|| (BoardUtils.INSTANCE.EIGHTH_COLUMN.get(candidateDestinationCoordinate) && (currentCandidate == 1));
	}

	@Override
	public Rook movePiece(Move move) {
		return new Rook(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
	}
}
