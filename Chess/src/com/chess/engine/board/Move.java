package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move
{
    final Board board;

    final Piece movedPiece;

    final int destinationCoordinate;

    Move( final Board board, final Piece movePiece, final int destinationCoordinate )
    {
        this.board = board;
        this.movedPiece = movePiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    public static final class MajorMove
        extends Move
    {
        public MajorMove( final Board board, final Piece movePiece, final int destinationCoordinate )
        {
            super( board, movePiece, destinationCoordinate );
        }

        public static final class AttackMove
            extends Move
        {
            final Piece attackedPiece;

            public AttackMove( final Board board, final Piece movePiece, final int destinationCoordinate,
                               final Piece attackedPiece )
            {
                super( board, movePiece, destinationCoordinate );
                this.attackedPiece = attackedPiece;
            }

            @Override
            public Board execute()
            {
                // TODO Auto-generated method stub
                return null;
            }
        }

        @Override
        public Board execute()
        {
            // TODO Auto-generated method stub
            return null;
        }
    }

    public int getDesinationCoordinate()
    {
        return this.destinationCoordinate;
    }

    public abstract Board execute();
}
