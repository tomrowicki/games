package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

public class WhitePlayer
    extends Player
{

    public WhitePlayer( final Board board, final Collection<Move> whiteStandardLegalMoves,
                        final Collection<Move> blackStandardLegalMoves )
    {
        super( board, whiteStandardLegalMoves, blackStandardLegalMoves );
    }

    @Override
    public Collection<Piece> getActivePieces()
    {
        return board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance()
    {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent()
    {
        return this.board.blackPlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles( Collection<Move> playerLegals, Collection<Move> opponentsLegals )
    {
        final List<Move> kingCastles = new ArrayList<Move>();

        if ( this.playerKing.isFirstMove() && !this.isInCheck() )
        {
            // white king-side castle
            if ( !this.board.getTile( 61 ).isTileOccupied() && !this.board.getTile( 62 ).isTileOccupied() )
            {
                final Tile rookTile = this.board.getTile( 63 );
                if ( rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() )
                {
                    if ( Player.calculateAttacksOnTile( 61, opponentsLegals ).isEmpty()
                        && Player.calculateAttacksOnTile( 62, opponentsLegals ).isEmpty()
                        && rookTile.getPiece().getPieceType().isRook() )
                    {
                        // TODO add a castle move
                        kingCastles.add( null );
                    }
                }
            }
            // white queen-side castle move
            if ( !this.board.getTile( 59 ).isTileOccupied() && !this.board.getTile( 58 ).isTileOccupied()
                && !this.board.getTile( 57 ).isTileOccupied() )
            {
                final Tile rookTile = this.board.getTile( 56 );
                if ( rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() )
                {
                    // TODO add a castle move
                    kingCastles.add( null );
                }
            }
        }

        return ImmutableList.copyOf( kingCastles );
    }
}
