package snake;

public class Record 
{
    private int number;
    private String name;
    private int points;
    
    public Record(int number, String name, int points)
    {
        this.number = number;
        this.name = name;
        this.points = points;
    }
    
    public Record(String name, int points)
    {
        this.name = name;
        this.points = points;
    }

    public int getNumber() {
        return number;
    }
    
    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoints(int points) {
        this.points = points;
    }
     
    
     
}
