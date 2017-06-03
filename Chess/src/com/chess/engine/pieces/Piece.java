package com.chess.engine.pieces;

import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public abstract class Piece
{
    protected final int position;

    protected final Alliance pieceAlliance;

    Piece( final int piecePosition, final Alliance pieceAlliance )
    {
        this.position = piecePosition;
        this.pieceAlliance = pieceAlliance;
    }

    public abstract List<Move> calculateLegalMoves( final Board board );
}
