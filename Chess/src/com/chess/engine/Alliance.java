package com.chess.engine;

import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;

public enum Alliance
{
    WHITE
    {
        @Override
        public int getDirection()
        {
            return -1;
        }

        @Override
        public boolean isWhite()
        {
            return true;
        }

        @Override
        public boolean isBlack()
        {
            return false;
        }

        @Override
        public Player choosePlayer( final Player whitePlayer, final BlackPlayer blackPlayer )
        {
            return whitePlayer;
        }
    },
    BLACK
    {
        @Override
        public int getDirection()
        {
            return 1;
        }

        @Override
        public boolean isWhite()
        {
            return false;
        }

        @Override
        public boolean isBlack()
        {
            return true;
        }

        @Override
        public Player choosePlayer( final Player whitePlayer, final BlackPlayer blackPlayer )
        {
            return blackPlayer;
        }
    };

    public abstract int getDirection();

    public abstract boolean isWhite();

    public abstract boolean isBlack();

    public abstract Player choosePlayer( Player whitePlayer, BlackPlayer blackPlayer );
}
