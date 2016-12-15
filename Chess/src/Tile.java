
public abstract class Tile
{
    int tileCoordinate;
    
    public Tile (int coord)
    {
        tileCoordinate = coord;
    }
    
    public abstract boolean isTileOccupied();
    
    public abstract Piece getPiece();
    
    public static final class EmptyTile extends Tile
    {
        EmptyTile(int coordinate)
        {
            super(coordinate);
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
    
    public static final class OccupiedTile extends Tile
    {
        Piece pieceOnTile;
        
        OccupiedTile(int coordinate, Piece piece)
        {
            super(coordinate);
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
