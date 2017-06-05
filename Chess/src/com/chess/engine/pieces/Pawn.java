package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;

public class Pawn
    extends Piece
{
    private static int[] CANDIDATE_MOVE_COORDINATES = { 8 };

    Pawn( int piecePosition, Alliance pieceAlliance )
    {
        super( piecePosition, pieceAlliance );
    }

    @Override
    public Collection<Move> calculateLegalMoves( Board board )
    {
        final List<Move> legalMoves = new ArrayList<Move>();

        for ( final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES )
        {
            int candidateDestinationCoordinate =
                this.piecePosition + ( this.getPieceAlliance().getDirection() * currentCandidateOffset );

            if ( !BoardUtils.isValidTileCoordinate( candidateDestinationCoordinate ) )
            {
                continue;
            }

            if ( currentCandidateOffset == 8 && !board.getTile( candidateDestinationCoordinate ).isTileOccupied() )
            {
                // TODO more work to come
                legalMoves.add( new Move.MajorMove( board, this, candidateDestinationCoordinate ) );
            }
        }

        return legalMoves;
    }

}
