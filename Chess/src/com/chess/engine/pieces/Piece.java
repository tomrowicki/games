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

    protected final PieceType pieceType;

    private final int cachedHashCode;

    Piece( final PieceType pieceType, final int piecePosition, final Alliance pieceAlliance )
    {
        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        // TODO more work to come
        this.isFirstMove = false;
        this.cachedHashCode = computeHashCode();
    }

    private int computeHashCode()
    {
        int result = pieceType.hashCode();
        result = 31 * result + pieceAlliance.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + ( isFirstMove ? 1 : 0 );
        return result;
    }

    @Override
    public boolean equals( final Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        final Piece other = (Piece) obj;
        return piecePosition == other.getPiecePosition() && pieceType == other.getPieceType()
            && pieceAlliance == other.getPieceAlliance() && isFirstMove == other.isFirstMove();
    }

    @Override
    public int hashCode()
    {
        return this.cachedHashCode;
    }

    public int getPiecePosition()
    {
        return piecePosition;
    }

    public Alliance getPieceAlliance()
    {
        return pieceAlliance;
    }

    public PieceType getPieceType()
    {
        return this.pieceType;
    }

    public boolean isFirstMove()
    {
        return this.isFirstMove;
    }

    public abstract Collection<Move> calculateLegalMoves( final Board board );

    public abstract Piece movePiece( Move move );

    public enum PieceType
    {
        PAWN( "P" )
        {
            @Override
            public boolean isKing()
            {
                return false;
            }
        },
        KNIGHT( "N" )
        {
            @Override
            public boolean isKing()
            {
                return false;
            }
        },
        BISHOP( "B" )
        {
            @Override
            public boolean isKing()
            {
                return false;
            }
        },
        ROOK( "R" )
        {
            @Override
            public boolean isKing()
            {
                return false;
            }
        },
        QUEEN( "Q" )
        {
            @Override
            public boolean isKing()
            {
                return false;
            }
        },
        KING( "K" )
        {
            @Override
            public boolean isKing()
            {
                return true;
            }
        };

        private String pieceName;

        PieceType( final String pieceName )
        {
            this.pieceName = pieceName;
        }

        @Override
        public String toString()
        {
            return this.pieceName;
        }

        public abstract boolean isKing();
    }
}
