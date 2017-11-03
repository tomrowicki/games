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
                final Board.Builder builder = new Board.Builder();
                for ( final Piece piece : board.currentPlayer().getActivePieces() )
                {
                    // TODO hashcode and equals methods
                    if ( !this.movedPiece.equals( piece ) )
                    {
                        builder.setPiece( piece );
                    }
                }

                for ( final Piece piece : board.currentPlayer().getOpponent().getActivePieces() )
                {
                    builder.setPiece( piece );
                }
                // FIXME move the moved piece
                builder.setPiece( null );
                builder.setMoveMaker( board.currentPlayer().getOpponent().getAlliance() );
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
