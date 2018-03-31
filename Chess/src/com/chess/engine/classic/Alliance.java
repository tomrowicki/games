package com.chess.engine.classic;

import com.chess.engine.board.BoardUtils;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;

public enum Alliance {
	WHITE {

		@Override
		public int getDirection() {
			return -1;
		}

		@Override
		public boolean isWhite() {
			return true;
		}

		@Override
		public boolean isBlack() {
			return false;
		}

		@Override
		public Player choosePlayer(final Player whitePlayer, final BlackPlayer blackPlayer) {
			return whitePlayer;
		}

		@Override
		public int getOppositeDirection() {
			return 1;
		}

		@Override
		public boolean isPawnPromotionSquare(final int position) {
			return BoardUtils.INSTANCE.FIRST_ROW.get(position);
		}
	},
	BLACK {

		@Override
		public int getDirection() {
			return 1;
		}

		@Override
		public boolean isWhite() {
			return false;
		}

		@Override
		public boolean isBlack() {
			return true;
		}

		@Override
		public Player choosePlayer(final Player whitePlayer, final BlackPlayer blackPlayer) {
			return blackPlayer;
		}

		@Override
		public int getOppositeDirection() {
			return -1;
		}

		@Override
		public boolean isPawnPromotionSquare(final int position) {
			return BoardUtils.INSTANCE.EIGHTH_ROW.get(position);
		}
	};

	public abstract int getDirection();

	public abstract int getOppositeDirection();

	public abstract boolean isWhite();

	public abstract boolean isBlack();

	public abstract boolean isPawnPromotionSquare(int position);

	public abstract Player choosePlayer(Player whitePlayer, BlackPlayer blackPlayer);
}
