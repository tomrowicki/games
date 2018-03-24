package com.tests.chess.engine;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.chess.engine.board.Board;
import com.chess.engine.classic.Alliance;
import com.chess.engine.classic.classic.player.ai.RookStructureAnalyzer;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Rook;

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
		builder.setPiece(new Rook(Alliance.BLACK, 0));
		builder.setPiece(new King(Alliance.BLACK, 4, false, false));
		// White Layout
		builder.setPiece(new Rook(Alliance.WHITE, 63));
		builder.setPiece(new King(Alliance.WHITE, 60, false, false));
		builder.setMoveMaker(Alliance.WHITE);
		// Set the current player
		final Board board = builder.build();
		assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 25);
		assertEquals(RookStructureAnalyzer.get().rookStructureScore(board, board.whitePlayer()), 25);
	}

}