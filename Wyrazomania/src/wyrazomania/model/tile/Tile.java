package wyrazomania.model.tile;

public abstract class Tile
{
    // FIXME zamiast tego obiekt Field z koordynatami i bonusem
    private Integer xCoordinate = null;
    
    private Integer yCoordinate = null;
    
    private Letter letter;
    
    private int points;
    
    private String imagePath;
    
    public Tile(Letter letter, int points, String imagePath)
    {
        this.letter = letter;
        this.points = points; 
        this.imagePath = imagePath;
    }

    public Integer getxCoordinate()
    {
        return xCoordinate;
    }

    public void setxCoordinate( Integer xCoordinate )
    {
        this.xCoordinate = xCoordinate;
    }

    public Integer getyCoordinate()
    {
        return yCoordinate;
    }

    public void setyCoordinate( Integer yCoordinate )
    {
        this.yCoordinate = yCoordinate;
    }

    public Letter getLetter()
    {
        return letter;
    }

    public int getPoints()
    {
        return points;
    }

    public String getImagePath()
    {
        return imagePath;
    }
}
