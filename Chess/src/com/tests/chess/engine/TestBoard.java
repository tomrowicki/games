package com.tests.chess.engine;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.chess.engine.board.Board;
import com.chess.engine.board.Board.Builder;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.board.Tile;
import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.classic.board.*;
import com.chess.engine.classic.classic.pieces.*;
import com.chess.engine.classic.classic.player.ai.BoardEvaluator;
import com.chess.engine.classic.classic.player.ai.StandardBoardEvaluator;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;
import com.chess.engine.player.MoveTransition;
import com.chess.pgn.FenUtilities;
import com.google.common.collect.Iterables;

public class TestBoard {

	@Test
	public void initialBoard() {

		final Board board = Board.createStandardBoard();
		assertEquals(board.currentPlayer().getLegalMoves().size(), 20);
		assertEquals(board.currentPlayer().getOpponent().getLegalMoves().size(), 20);
		assertFalse(board.currentPlayer().isInCheck());
		assertFalse(board.currentPlayer().isInCheckMate());
		assertFalse(board.currentPlayer().isCastled());
		assertTrue(board.currentPlayer().isKingSideCastleCapable());
		assertTrue(board.currentPlayer().isQueenSideCastleCapable());
		assertEquals(board.currentPlayer(), board.whitePlayer());
		assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
		assertFalse(board.currentPlayer().getOpponent().isInCheck());
		assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
		assertFalse(board.currentPlayer().getOpponent().isCastled());
		assertTrue(board.currentPlayer().getOpponent().isKingSideCastleCapable());
		assertTrue(board.currentPlayer().getOpponent().isQueenSideCastleCapable());
		assertTrue(board.whitePlayer().toString().equals("White"));
		assertTrue(board.blackPlayer().toString().equals("Black"));

		final Iterable<Piece> allPieces = board.getAllPieces();
		final Iterable<Move> allMoves = Iterables.concat(board.whitePlayer().getLegalMoves(),
				board.blackPlayer().getLegalMoves());
		for (final Move move : allMoves) {
			assertFalse(move.isAttack());
			assertFalse(move.isCastlingMove());
			assertEquals(MoveUtils.exchangeScore(move), 1);
		}

		assertEquals(Iterables.size(allMoves), 40);
		assertEquals(Iterables.size(allPieces), 32);
		assertFalse(BoardUtils.isEndGame(board));
		assertFalse(BoardUtils.isThreatenedBoardImmediate(board));
		assertEquals(StandardBoardEvaluator.get().evaluate(board, 0), 0);
		assertEquals(board.getTile(35).getPiece(), null);
		assertEquals(board.getTile(35).getTileCoordinate(), 35);

	}

	@Test
	public void testPlainKingMove() {

		final Builder builder = new Builder();
		// Black Layout
		builder.setPiece(new King(Alliance.BLACK, 4, false, false));
		builder.setPiece(new Pawn(Alliance.BLACK, 12));
		// White Layout
		builder.setPiece(new Pawn(Alliance.WHITE, 52));
		builder.setPiece(new King(Alliance.WHITE, 60, false, false));
		builder.setMoveMaker(Alliance.WHITE);
		// Set the current player
		final Board board = builder.build();
		System.out.println(board);

		assertEquals(board.whitePlayer().getLegalMoves().size(), 6);
		assertEquals(board.blackPlayer().getLegalMoves().size(), 6);
		assertFalse(board.currentPlayer().isInCheck());
		assertFalse(board.currentPlayer().isInCheckMate());
		assertFalse(board.currentPlayer().getOpponent().isInCheck());
		assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
		assertEquals(board.currentPlayer(), board.whitePlayer());
		assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
		BoardEvaluator evaluator = StandardBoardEvaluator.get();
		System.out.println(evaluator.evaluate(board, 0));
		assertEquals(StandardBoardEvaluator.get().evaluate(board, 0), 0);

		final Move move = MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e1"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("f1"));

		final MoveTransition moveTransition = board.currentPlayer().makeMove(move);

		assertEquals(moveTransition.getTransitionMove(), move);
		assertEquals(moveTransition.getFromBoard(), board);
		assertEquals(moveTransition.getToBoard().currentPlayer(), moveTransition.getToBoard().blackPlayer());

		assertTrue(moveTransition.getMoveStatus().isDone());
		assertEquals(moveTransition.getToBoard().whitePlayer().getPlayerKing().getPiecePosition(), 61);
		System.out.println(moveTransition.getToBoard());

	}

	@Test
	public void testBoardConsistency() {
		final Board board = Board.createStandardBoard();
		assertEquals(board.currentPlayer(), board.whitePlayer());

		final MoveTransition t1 = board.currentPlayer().makeMove(MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e2"), BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));
		final MoveTransition t2 = t1.getToBoard().currentPlayer().makeMove(MoveFactory.createMove(t1.getToBoard(),
				BoardUtils.INSTANCE.getCoordinateAtPosition("e7"), BoardUtils.INSTANCE.getCoordinateAtPosition("e5")));

		final MoveTransition t3 = t2.getToBoard().currentPlayer().makeMove(MoveFactory.createMove(t2.getToBoard(),
				BoardUtils.INSTANCE.getCoordinateAtPosition("g1"), BoardUtils.INSTANCE.getCoordinateAtPosition("f3")));
		final MoveTransition t4 = t3.getToBoard().currentPlayer().makeMove(MoveFactory.createMove(t3.getToBoard(),
				BoardUtils.INSTANCE.getCoordinateAtPosition("d7"), BoardUtils.INSTANCE.getCoordinateAtPosition("d5")));

		final MoveTransition t5 = t4.getToBoard().currentPlayer().makeMove(MoveFactory.createMove(t4.getToBoard(),
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("d5")));
		final MoveTransition t6 = t5.getToBoard().currentPlayer().makeMove(MoveFactory.createMove(t5.getToBoard(),
				BoardUtils.INSTANCE.getCoordinateAtPosition("d8"), BoardUtils.INSTANCE.getCoordinateAtPosition("d5")));

		final MoveTransition t7 = t6.getToBoard().currentPlayer().makeMove(MoveFactory.createMove(t6.getToBoard(),
				BoardUtils.INSTANCE.getCoordinateAtPosition("f3"), BoardUtils.INSTANCE.getCoordinateAtPosition("g5")));
		final MoveTransition t8 = t7.getToBoard().currentPlayer().makeMove(MoveFactory.createMove(t7.getToBoard(),
				BoardUtils.INSTANCE.getCoordinateAtPosition("f7"), BoardUtils.INSTANCE.getCoordinateAtPosition("f6")));

		final MoveTransition t9 = t8.getToBoard().currentPlayer().makeMove(MoveFactory.createMove(t8.getToBoard(),
				BoardUtils.INSTANCE.getCoordinateAtPosition("d1"), BoardUtils.INSTANCE.getCoordinateAtPosition("h5")));
		final MoveTransition t10 = t9.getToBoard().currentPlayer().makeMove(MoveFactory.createMove(t9.getToBoard(),
				BoardUtils.INSTANCE.getCoordinateAtPosition("g7"), BoardUtils.INSTANCE.getCoordinateAtPosition("g6")));

		final MoveTransition t11 = t10.getToBoard().currentPlayer().makeMove(MoveFactory.createMove(t10.getToBoard(),
				BoardUtils.INSTANCE.getCoordinateAtPosition("h5"), BoardUtils.INSTANCE.getCoordinateAtPosition("h4")));
		final MoveTransition t12 = t11.getToBoard().currentPlayer().makeMove(MoveFactory.createMove(t11.getToBoard(),
				BoardUtils.INSTANCE.getCoordinateAtPosition("f6"), BoardUtils.INSTANCE.getCoordinateAtPosition("g5")));

		final MoveTransition t13 = t12.getToBoard().currentPlayer().makeMove(MoveFactory.createMove(t12.getToBoard(),
				BoardUtils.INSTANCE.getCoordinateAtPosition("h4"), BoardUtils.INSTANCE.getCoordinateAtPosition("g5")));
		final MoveTransition t14 = t13.getToBoard().currentPlayer().makeMove(MoveFactory.createMove(t13.getToBoard(),
				BoardUtils.INSTANCE.getCoordinateAtPosition("d5"), BoardUtils.INSTANCE.getCoordinateAtPosition("e4")));

		assertTrue(t14.getToBoard().whitePlayer().getActivePieces().size() == calculatedActivesFor(t14.getToBoard(),
				Alliance.WHITE));
		assertTrue(t14.getToBoard().blackPlayer().getActivePieces().size() == calculatedActivesFor(t14.getToBoard(),
				Alliance.BLACK));

	}

	@Test(expected = RuntimeException.class)
	public void testInvalidBoard() {

		final Builder builder = new Builder();
		// Black Layout
		builder.setPiece(new Rook(0, Alliance.BLACK));
		builder.setPiece(new Knight(1, Alliance.BLACK));
		builder.setPiece(new Bishop(2, Alliance.BLACK));
		builder.setPiece(new Queen(3, Alliance.BLACK));
		builder.setPiece(new Bishop(5, Alliance.BLACK));
		builder.setPiece(new Knight(6, Alliance.BLACK));
		builder.setPiece(new Rook(7, Alliance.BLACK));
		builder.setPiece(new Pawn(8, Alliance.BLACK));
		builder.setPiece(new Pawn(9, Alliance.BLACK));
		builder.setPiece(new Pawn(10, Alliance.BLACK));
		builder.setPiece(new Pawn(11, Alliance.BLACK));
		builder.setPiece(new Pawn(12, Alliance.BLACK));
		builder.setPiece(new Pawn(13, Alliance.BLACK));
		builder.setPiece(new Pawn(14, Alliance.BLACK));
		builder.setPiece(new Pawn(15, Alliance.BLACK));
		// White Layout
		builder.setPiece(new Pawn(48, Alliance.WHITE));
		builder.setPiece(new Pawn(49, Alliance.WHITE));
		builder.setPiece(new Pawn(50, Alliance.WHITE));
		builder.setPiece(new Pawn(51, Alliance.WHITE));
		builder.setPiece(new Pawn(52, Alliance.WHITE));
		builder.setPiece(new Pawn(53, Alliance.WHITE));
		builder.setPiece(new Pawn(54, Alliance.WHITE));
		builder.setPiece(new Pawn(55, Alliance.WHITE));
		builder.setPiece(new Rook(56, Alliance.WHITE));
		builder.setPiece(new Knight(57, Alliance.WHITE));
		builder.setPiece(new Bishop(58, Alliance.WHITE));
		builder.setPiece(new Queen(59, Alliance.WHITE));
		builder.setPiece(new Bishop(61, Alliance.WHITE));
		builder.setPiece(new Knight(62, Alliance.WHITE));
		builder.setPiece(new Rook(63, Alliance.WHITE));
		// white to move
		builder.setMoveMaker(Alliance.WHITE);
		// build the board
		builder.build();
	}

	@Test
	public void testAlgebreicNotation() {
		assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(0), "a8");
		assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(1), "b8");
		assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(2), "c8");
		assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(3), "d8");
		assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(4), "e8");
		assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(5), "f8");
		assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(6), "g8");
		assertEquals(BoardUtils.INSTANCE.getPositionAtCoordinate(7), "h8");
	}

	@Test
	public void tt() {

		final Board.Builder builder = new Board.Builder();
		// BLACK LAYOUT
		builder.setPiece(new King(Alliance.BLACK, 4, false, false));
		// WHITE LAYOUT
		builder.setPiece(new King(Alliance.WHITE, 60, false, false));
		builder.setPiece(new Bishop(61, Alliance.WHITE));
		// white to move
		builder.setMoveMaker(Alliance.WHITE);
		final Board board = builder.build();
		System.out.println(FenUtilities.createFENFromGame(board));
	}

	private static int calculatedActivesFor(final Board board, final Alliance alliance) {
		int count = 0;
		for (final Tile t : board.getGameBoard()) {
			if (t.isTileOccupied() && t.getPiece().getPieceAllegiance().equals(alliance)) {
				count++;
			}
		}
		return count;
	}

}
