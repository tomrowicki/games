package com.tests.chess.engine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Board.Builder;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Rook;
import com.chess.engine.player.ai.RookStructureAnalyzer;

public class TestRookStructure {

	@Test
	public void test1() {
		final Board board = Board.createStandardBoard();
		assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 0);
		assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 0);
	}

	@Test
	public void test2() {
		final Builder builder = new Builder();
		// Black Layout
		builder.setPiece(new Rook(0, Alliance.BLACK));
		builder.setPiece(new King(4, Alliance.BLACK, false, false));
		// White Layout
		builder.setPiece(new Rook(63, Alliance.WHITE));
		builder.setPiece(new King(60, Alliance.WHITE, false, false));
		builder.setMoveMaker(Alliance.WHITE);
		// Set the current player
		final Board board = builder.build();
		assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 25);
		assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 25);
	}

}