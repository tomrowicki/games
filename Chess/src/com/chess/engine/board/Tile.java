package com.chess.engine.board;

import java.util.HashMap;
import java.util.Map;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

public abstract class Tile
{
    // can only be set at the construction time and only be accessed by the subclasses
    protected final int tileCoordinate;

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles()
    {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<Integer, EmptyTile>();
        for ( int i = 0; i < BoardUtils.NUM_TILES; i++ )
        {
            emptyTileMap.put( i, new EmptyTile( i ) );
        }
        return ImmutableMap.copyOf( emptyTileMap );
    }

    public static Tile createTile( final int tileCoordinate, final Piece piece )
    {
        return piece != null ? new OccupiedTile( tileCoordinate, piece ) : EMPTY_TILES_CACHE.get( tileCoordinate );
    }

    private Tile( final int coord )
    {
        tileCoordinate = coord;
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    private static final class EmptyTile
        extends Tile
    {
        EmptyTile( final int coordinate )
        {
            super( coordinate );
        }

        public boolean isTileOccupied()
        {
            return false;
        }

        @Override
        public Piece getPiece()
        {
            return null;
        }
    }

    private static final class OccupiedTile
        extends Tile
    {
        private final Piece pieceOnTile;

        private OccupiedTile( final int coordinate, final Piece piece )
        {
            super( coordinate );
            pieceOnTile = piece;
        }

        public boolean isTileOccupied()
        {
            return true;
        }

        @Override
        public Piece getPiece()
        {
            return pieceOnTile;
        }
    }
}
