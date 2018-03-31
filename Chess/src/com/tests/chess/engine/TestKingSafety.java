package com.tests.chess.engine;

import org.junit.Test;

import com.chess.engine.board.Board;
import com.chess.engine.board.Board.Builder;
import com.chess.engine.classic.Alliance;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Pawn;

public class TestKingSafety {

	@Test
	public void test1() {
		final Builder builder = new Builder();
		// Black Layout
		builder.setPiece(new King(4, Alliance.BLACK, false, false));
		builder.setPiece(new Pawn(12, Alliance.BLACK));
		// White Layout
		builder.setPiece(new Pawn(52, Alliance.WHITE));
		builder.setPiece(new King(60, Alliance.WHITE, false, false));
		builder.setMoveMaker(Alliance.WHITE);
		// Set the current player
		@SuppressWarnings("unused")
		final Board board = builder.build();

		// assertEquals(KingSafetyAnalyzer.get().calculateKingTropism(board.whitePlayer()).tropismScore(),
		// 40);
	}

}