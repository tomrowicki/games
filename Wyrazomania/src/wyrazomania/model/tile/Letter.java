package wyrazomania.model.tile;

public enum Letter
{
    A( "a", 1 ),
    B( "b", 1 );

    private final String character;

    private final int points;
    
    Letter(String character, int points)
    {
        this.character = character;
        this.points = points;
    }

    public String getCharacter()
    {
        return character;
    }

    public int getPoints()
    {
        return points;
    }
}
