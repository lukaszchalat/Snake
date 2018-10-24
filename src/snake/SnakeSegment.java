package snake;

import javax.swing.*;
import java.awt.*;

public class SnakeSegment extends JLabel
{
    int x;
    int y;
    final static int width = 10;
    final static int height = 10;
    String currentMove;
    
    public SnakeSegment(String move, int x, int y)
    {
        this.x = x;
        this.y = y;
        this.setOpaque(true);
        this.setBackground(Color.RED);
        this.currentMove = move;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public String getCurrentMove()
    {
        return currentMove;
    }
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    public void setCurrentMove(String move)
    {
        this.currentMove = move;
    }
}
