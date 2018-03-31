package com.tests.chess.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.classic.Alliance;
import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Rook;
import com.chess.engine.player.MoveTransition;
import com.google.common.collect.Sets;

public class TestPieces {

	@Test
	public void testMiddleQueenOnEmptyBoard() {
		final Board.Builder builder = new Board.Builder();
		// Black Layout
		builder.setPiece(new King(4, Alliance.BLACK, false, false));
		// White Layout
		builder.setPiece(new Queen(36, Alliance.WHITE));
		builder.setPiece(new King(60, Alliance.WHITE, false, false));
		// Set the current player
		builder.setMoveMaker(Alliance.WHITE);
		final Board board = builder.build();
		final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
		final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
		assertEquals(whiteLegals.size(), 31);
		assertEquals(blackLegals.size(), 5);
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e8"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e7"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e6"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e5"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e3"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e2"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("a4"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("b4"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("c4"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("d4"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("f4"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("g4"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("h4"))));
	}

	@Test
	public void testLegalMoveAllAvailable() {

		final Board.Builder boardBuilder = new Board.Builder();
		// Black Layout
		boardBuilder.setPiece(new King(4, Alliance.BLACK, false, false));
		boardBuilder.setPiece(new Knight(28, Alliance.BLACK));
		// White Layout
		boardBuilder.setPiece(new Knight(36, Alliance.WHITE));
		boardBuilder.setPiece(new King(60, Alliance.WHITE, false, false));
		// Set the current player
		boardBuilder.setMoveMaker(Alliance.WHITE);
		final Board board = boardBuilder.build();
		final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
		assertEquals(whiteLegals.size(), 13);
		final Move wm1 = Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("d6"));
		final Move wm2 = Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("f6"));
		final Move wm3 = Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("c5"));
		final Move wm4 = Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("g5"));
		final Move wm5 = Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("c3"));
		final Move wm6 = Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("g3"));
		final Move wm7 = Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("d2"));
		final Move wm8 = Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("f2"));

		assertTrue(whiteLegals.contains(wm1));
		assertTrue(whiteLegals.contains(wm2));
		assertTrue(whiteLegals.contains(wm3));
		assertTrue(whiteLegals.contains(wm4));
		assertTrue(whiteLegals.contains(wm5));
		assertTrue(whiteLegals.contains(wm6));
		assertTrue(whiteLegals.contains(wm7));
		assertTrue(whiteLegals.contains(wm8));

		final Board.Builder boardBuilder2 = new Board.Builder();
		// Black Layout
		boardBuilder2.setPiece(new King(4, Alliance.BLACK, false, false));
		boardBuilder2.setPiece(new Knight(28, Alliance.BLACK));
		// White Layout
		boardBuilder2.setPiece(new Knight(36, Alliance.WHITE));
		boardBuilder2.setPiece(new King(60, Alliance.WHITE, false, false));
		// Set the current player
		boardBuilder2.setMoveMaker(Alliance.BLACK);
		final Board board2 = boardBuilder2.build();
		final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();

		final Move bm1 = Move.MoveFactory.createMove(board2, BoardUtils.INSTANCE.getCoordinateAtPosition("e5"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("d7"));
		final Move bm2 = Move.MoveFactory.createMove(board2, BoardUtils.INSTANCE.getCoordinateAtPosition("e5"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("f7"));
		final Move bm3 = Move.MoveFactory.createMove(board2, BoardUtils.INSTANCE.getCoordinateAtPosition("e5"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("c6"));
		final Move bm4 = Move.MoveFactory.createMove(board2, BoardUtils.INSTANCE.getCoordinateAtPosition("e5"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("g6"));
		final Move bm5 = Move.MoveFactory.createMove(board2, BoardUtils.INSTANCE.getCoordinateAtPosition("e5"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("c4"));
		final Move bm6 = Move.MoveFactory.createMove(board2, BoardUtils.INSTANCE.getCoordinateAtPosition("e5"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("g4"));
		final Move bm7 = Move.MoveFactory.createMove(board2, BoardUtils.INSTANCE.getCoordinateAtPosition("e5"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("d3"));
		final Move bm8 = Move.MoveFactory.createMove(board2, BoardUtils.INSTANCE.getCoordinateAtPosition("e5"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("f3"));

		assertEquals(blackLegals.size(), 13);

		assertTrue(blackLegals.contains(bm1));
		assertTrue(blackLegals.contains(bm2));
		assertTrue(blackLegals.contains(bm3));
		assertTrue(blackLegals.contains(bm4));
		assertTrue(blackLegals.contains(bm5));
		assertTrue(blackLegals.contains(bm6));
		assertTrue(blackLegals.contains(bm7));
		assertTrue(blackLegals.contains(bm8));
	}

	@Test
	public void testKnightInCorners() {
		final Board.Builder boardBuilder = new Board.Builder();
		boardBuilder.setPiece(new King(4, Alliance.BLACK, false, false));
		boardBuilder.setPiece(new Knight(0, Alliance.BLACK));
		boardBuilder.setPiece(new Knight(56, Alliance.WHITE));
		boardBuilder.setPiece(new King(60, Alliance.WHITE, false, false));
		boardBuilder.setMoveMaker(Alliance.WHITE);
		final Board board = boardBuilder.build();
		final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
		final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
		assertEquals(whiteLegals.size(), 7);
		assertEquals(blackLegals.size(), 7);
		final Move wm1 = Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a1"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("b3"));
		final Move wm2 = Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a1"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("c2"));
		assertTrue(whiteLegals.contains(wm1));
		assertTrue(whiteLegals.contains(wm2));
		final Move bm1 = Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a8"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("b6"));
		final Move bm2 = Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("a8"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("c7"));
		assertTrue(blackLegals.contains(bm1));
		assertTrue(blackLegals.contains(bm2));

	}

	@Test
	public void testMiddleBishopOnEmptyBoard() {
		final Board.Builder builder = new Board.Builder();
		// Black Layout
		builder.setPiece(new King(4, Alliance.BLACK, false, false));
		// White Layout
		builder.setPiece(new Bishop(35, Alliance.WHITE));
		builder.setPiece(new King(60, Alliance.WHITE, false, false));
		// Set the current player
		builder.setMoveMaker(Alliance.WHITE);
		// build the board
		final Board board = builder.build();
		final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
		final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
		assertEquals(whiteLegals.size(), 18);
		assertEquals(blackLegals.size(), 5);
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("d4"), BoardUtils.INSTANCE.getCoordinateAtPosition("a7"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("d4"), BoardUtils.INSTANCE.getCoordinateAtPosition("b6"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("d4"), BoardUtils.INSTANCE.getCoordinateAtPosition("c5"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("d4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e3"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("d4"), BoardUtils.INSTANCE.getCoordinateAtPosition("f2"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("d4"), BoardUtils.INSTANCE.getCoordinateAtPosition("g1"))));
	}

	@Test
	public void testTopLeftBishopOnEmptyBoard() {
		Board.Builder builder = new Board.Builder();
		// Black Layout
		builder.setPiece(new King(4, Alliance.BLACK, false, false));
		// White Layout
		builder.setPiece(new Bishop(0, Alliance.WHITE));
		builder.setPiece(new King(60, Alliance.WHITE, false, false));
		// Set the current player
		builder.setMoveMaker(Alliance.WHITE);
		// build the board
		final Board board = builder.build();
		final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
		final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
		assertEquals(board.getTile(0), board.getTile(0));
		assertNotNull(board.getTile(0));
		assertEquals(whiteLegals.size(), 12);
		assertEquals(blackLegals.size(), 5);
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("a8"), BoardUtils.INSTANCE.getCoordinateAtPosition("b7"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("a8"), BoardUtils.INSTANCE.getCoordinateAtPosition("c6"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("a8"), BoardUtils.INSTANCE.getCoordinateAtPosition("d5"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("a8"), BoardUtils.INSTANCE.getCoordinateAtPosition("e4"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("a8"), BoardUtils.INSTANCE.getCoordinateAtPosition("f3"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("a8"), BoardUtils.INSTANCE.getCoordinateAtPosition("g2"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("a8"), BoardUtils.INSTANCE.getCoordinateAtPosition("h1"))));
	}

	@Test
	public void testTopRightBishopOnEmptyBoard() {
		Board.Builder builder = new Board.Builder();
		// Black Layout
		builder.setPiece(new King(4, Alliance.BLACK, false, false));
		// White Layout
		builder.setPiece(new Bishop(7, Alliance.WHITE));
		builder.setPiece(new King(60, Alliance.WHITE, false, false));
		// Set the current player
		builder.setMoveMaker(Alliance.WHITE);
		// build the board
		final Board board = builder.build();
		final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
		final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
		assertEquals(whiteLegals.size(), 12);
		assertEquals(blackLegals.size(), 5);
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("h8"), BoardUtils.INSTANCE.getCoordinateAtPosition("g7"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("h8"), BoardUtils.INSTANCE.getCoordinateAtPosition("f6"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("h8"), BoardUtils.INSTANCE.getCoordinateAtPosition("e5"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("h8"), BoardUtils.INSTANCE.getCoordinateAtPosition("d4"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("h8"), BoardUtils.INSTANCE.getCoordinateAtPosition("c3"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("h8"), BoardUtils.INSTANCE.getCoordinateAtPosition("b2"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("h8"), BoardUtils.INSTANCE.getCoordinateAtPosition("a1"))));
	}

	@Test
	public void testBottomLeftBishopOnEmptyBoard() {
		Board.Builder builder = new Board.Builder();
		// Black Layout
		builder.setPiece(new King(4, Alliance.BLACK, false, false));
		// White Layout
		builder.setPiece(new Bishop(56, Alliance.WHITE));
		builder.setPiece(new King(60, Alliance.WHITE, false, false));
		// Set the current player
		builder.setMoveMaker(Alliance.WHITE);
		// build the board
		final Board board = builder.build();
		final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
		final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
		assertEquals(whiteLegals.size(), 12);
		assertEquals(blackLegals.size(), 5);
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("a1"), BoardUtils.INSTANCE.getCoordinateAtPosition("b2"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("a1"), BoardUtils.INSTANCE.getCoordinateAtPosition("c3"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("a1"), BoardUtils.INSTANCE.getCoordinateAtPosition("d4"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("a1"), BoardUtils.INSTANCE.getCoordinateAtPosition("e5"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("a1"), BoardUtils.INSTANCE.getCoordinateAtPosition("f6"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("a1"), BoardUtils.INSTANCE.getCoordinateAtPosition("g7"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("a1"), BoardUtils.INSTANCE.getCoordinateAtPosition("h8"))));
	}

	@Test
	public void testBottomRightBishopOnEmptyBoard() {
		Board.Builder builder = new Board.Builder();
		// Black Layout
		builder.setPiece(new King(4, Alliance.BLACK, false, false));
		// White Layout
		builder.setPiece(new Bishop(63, Alliance.WHITE));
		builder.setPiece(new King(60, Alliance.WHITE, false, false));
		// Set the current player
		builder.setMoveMaker(Alliance.WHITE);
		// build the board
		final Board board = builder.build();
		final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
		final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
		assertEquals(whiteLegals.size(), 12);
		assertEquals(blackLegals.size(), 5);
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("h1"), BoardUtils.INSTANCE.getCoordinateAtPosition("g2"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("h1"), BoardUtils.INSTANCE.getCoordinateAtPosition("f3"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("h1"), BoardUtils.INSTANCE.getCoordinateAtPosition("e4"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("h1"), BoardUtils.INSTANCE.getCoordinateAtPosition("d5"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("h1"), BoardUtils.INSTANCE.getCoordinateAtPosition("c6"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("h1"), BoardUtils.INSTANCE.getCoordinateAtPosition("b7"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("h1"), BoardUtils.INSTANCE.getCoordinateAtPosition("a8"))));
	}

	@Test
	public void testMiddleRookOnEmptyBoard() {
		final Board.Builder builder = new Board.Builder();
		// Black Layout
		builder.setPiece(new King(4, Alliance.BLACK, false, false));
		// White Layout
		builder.setPiece(new Rook(36, Alliance.WHITE));
		builder.setPiece(new King(60, Alliance.WHITE, false, false));
		// Set the current player
		builder.setMoveMaker(Alliance.WHITE);
		final Board board = builder.build();
		final Collection<Move> whiteLegals = board.whitePlayer().getLegalMoves();
		final Collection<Move> blackLegals = board.blackPlayer().getLegalMoves();
		assertEquals(whiteLegals.size(), 18);
		assertEquals(blackLegals.size(), 5);
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e8"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e7"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e6"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e5"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e3"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("e2"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("a4"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("b4"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("c4"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("d4"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("f4"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("g4"))));
		assertTrue(whiteLegals.contains(Move.MoveFactory.createMove(board,
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"), BoardUtils.INSTANCE.getCoordinateAtPosition("h4"))));
	}

	@Test
	public void testPawnPromotion() {
		final Board.Builder builder = new Board.Builder();
		// Black Layout
		builder.setPiece(new Rook(3, Alliance.BLACK));
		builder.setPiece(new King(22, Alliance.BLACK, false, false));
		// White Layout
		builder.setPiece(new Pawn(15, Alliance.WHITE));
		builder.setPiece(new King(52, Alliance.WHITE, false, false));
		// Set the current player
		builder.setMoveMaker(Alliance.WHITE);
		final Board board = builder.build();
		final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("h7"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("h8"));
		final MoveTransition t1 = board.currentPlayer().makeMove(m1);
		Assert.assertTrue(t1.getMoveStatus().isDone());
		final Move m2 = Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d8"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("h8"));
		final MoveTransition t2 = t1.getToBoard().currentPlayer().makeMove(m2);
		Assert.assertTrue(t2.getMoveStatus().isDone());
		final Move m3 = Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("d2"));
		final MoveTransition t3 = board.currentPlayer().makeMove(m3);
		Assert.assertTrue(t3.getMoveStatus().isDone());
	}

	@Test
	public void testSimpleWhiteEnPassant() {
		final Board.Builder builder = new Board.Builder();
		// Black Layout
		builder.setPiece(new King(4, Alliance.BLACK, false, false));
		builder.setPiece(new Pawn(11, Alliance.BLACK));
		// White Layout
		builder.setPiece(new Pawn(52, Alliance.WHITE));
		builder.setPiece(new King(60, Alliance.WHITE, false, false));
		// Set the current player
		builder.setMoveMaker(Alliance.WHITE);
		final Board board = builder.build();
		final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"));
		final MoveTransition t1 = board.currentPlayer().makeMove(m1);
		Assert.assertTrue(t1.getMoveStatus().isDone());
		final Move m2 = Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e8"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("d8"));
		final MoveTransition t2 = t1.getToBoard().currentPlayer().makeMove(m2);
		Assert.assertTrue(t2.getMoveStatus().isDone());
		final Move m3 = Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e4"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("e5"));
		final MoveTransition t3 = t2.getToBoard().currentPlayer().makeMove(m3);
		Assert.assertTrue(t3.getMoveStatus().isDone());
		final Move m4 = Move.MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("d5"));
		final MoveTransition t4 = t3.getToBoard().currentPlayer().makeMove(m4);
		Assert.assertTrue(t4.getMoveStatus().isDone());
		final Move m5 = Move.MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e5"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("d6"));
		final MoveTransition t5 = t4.getToBoard().currentPlayer().makeMove(m5);
		Assert.assertTrue(t5.getMoveStatus().isDone());
	}

	@Test
	public void testSimpleBlackEnPassant() {
		final Board.Builder builder = new Board.Builder();
		// Black Layout
		builder.setPiece(new King(4, Alliance.BLACK, false, false));
		builder.setPiece(new Pawn(11, Alliance.BLACK));
		// White Layout
		builder.setPiece(new Pawn(52, Alliance.WHITE));
		builder.setPiece(new King(60, Alliance.WHITE, false, false));
		// Set the current player
		builder.setMoveMaker(Alliance.WHITE);
		final Board board = builder.build();
		final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e1"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("d1"));
		final MoveTransition t1 = board.currentPlayer().makeMove(m1);
		assertTrue(t1.getMoveStatus().isDone());
		final Move m2 = Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d7"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("d5"));
		final MoveTransition t2 = t1.getToBoard().currentPlayer().makeMove(m2);
		assertTrue(t2.getMoveStatus().isDone());
		final Move m3 = Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d1"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("c1"));
		final MoveTransition t3 = t2.getToBoard().currentPlayer().makeMove(m3);
		assertTrue(t3.getMoveStatus().isDone());
		final Move m4 = Move.MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d5"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("d4"));
		final MoveTransition t4 = t3.getToBoard().currentPlayer().makeMove(m4);
		assertTrue(t4.getMoveStatus().isDone());
		final Move m5 = Move.MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"));
		final MoveTransition t5 = t4.getToBoard().currentPlayer().makeMove(m5);
		assertTrue(t5.getMoveStatus().isDone());
		final Move m6 = Move.MoveFactory.createMove(t5.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("d4"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("e3"));
		final MoveTransition t6 = t5.getToBoard().currentPlayer().makeMove(m6);
		Assert.assertTrue(t6.getMoveStatus().isDone());
	}

	@Test
	public void testEnPassant2() {
		final Board board = Board.createStandardBoard();
		final Move m1 = Move.MoveFactory.createMove(board, BoardUtils.INSTANCE.getCoordinateAtPosition("e2"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("e3"));
		final MoveTransition t1 = board.currentPlayer().makeMove(m1);
		assertTrue(t1.getMoveStatus().isDone());
		final Move m2 = Move.MoveFactory.createMove(t1.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("h7"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("h5"));
		final MoveTransition t2 = t1.getToBoard().currentPlayer().makeMove(m2);
		assertTrue(t2.getMoveStatus().isDone());
		final Move m3 = Move.MoveFactory.createMove(t2.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("e3"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("e4"));
		final MoveTransition t3 = t2.getToBoard().currentPlayer().makeMove(m3);
		assertTrue(t3.getMoveStatus().isDone());
		final Move m4 = Move.MoveFactory.createMove(t3.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("h5"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("h4"));
		final MoveTransition t4 = t3.getToBoard().currentPlayer().makeMove(m4);
		assertTrue(t4.getMoveStatus().isDone());
		final Move m5 = Move.MoveFactory.createMove(t4.getToBoard(), BoardUtils.INSTANCE.getCoordinateAtPosition("g2"),
				BoardUtils.INSTANCE.getCoordinateAtPosition("g4"));
		final MoveTransition t5 = t4.getToBoard().currentPlayer().makeMove(m5);
		assertTrue(t5.getMoveStatus().isDone());
	}

	@Test
	public void testKingEquality() {
		final Board board = Board.createStandardBoard();
		final Board board2 = Board.createStandardBoard();
		assertEquals(board.getTile(60).getPiece(), board2.getTile(60).getPiece());
		assertFalse(board.getTile(60).getPiece().equals(null));
	}

	@Test
	public void testHashCode() {
		final Board board = Board.createStandardBoard();
		final Set<Piece> pieceSet = Sets.newHashSet(board.getAllPieces());
		final Set<Piece> whitePieceSet = Sets.newHashSet(board.getWhitePieces());
		final Set<Piece> blackPieceSet = Sets.newHashSet(board.getBlackPieces());
		assertTrue(pieceSet.size() == 32);
		assertTrue(whitePieceSet.size() == 16);
		assertTrue(blackPieceSet.size() == 16);
	}

}
