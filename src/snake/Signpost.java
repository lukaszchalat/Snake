package snake;

public class Signpost 
{
    String name;
    boolean active = false;
    int x;
    int y;
    
    public Signpost(String name, int x, int y)
    {
        this.name = name;
        this.x = x;
        this.y = y;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public String getName()
    {
        return name;
    }
    
    public boolean getActive()
    {
        return active;
    }
    
    public void setActive(boolean active)
    {
        this.active = active;
    }
}
