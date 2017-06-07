package com.chess.engine.pieces;

import java.util.Collection;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public abstract class Piece
{
    protected final int piecePosition;

    protected final Alliance pieceAlliance;

    protected final boolean isFirstMove;

    Piece( final int piecePosition, final Alliance pieceAlliance )
    {
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        // TODO more work to come
        this.isFirstMove = false;
    }

    public int getPiecePosition()
    {
        return piecePosition;
    }

    public Alliance getPieceAlliance()
    {
        return pieceAlliance;
    }

    public boolean isFirstMove()
    {
        return this.isFirstMove;
    }

    public abstract Collection<Move> calculateLegalMoves( final Board board );
}
