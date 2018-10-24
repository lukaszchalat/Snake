package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Snake extends JFrame
{
    private final int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    private int a;
    private int b;
    private final Move move = new Move();
    private Random random;
    private ArrayList<SnakeSegment> snake = new ArrayList<>();
    private ArrayList<Signpost> whereTo = new ArrayList<>();
    private SnakeSegment newSegment;
    private String currentMove = "up";
    private int x = 250;
    private int y = 400;
    private JLabel showTheScore;
    private JLabel showSpeedLevel;
    private JLabel showAddedSegments;
    private JLabel player;
    private int segments = 0;
    private int speedLevel = 1;
    private int points = 0;
    private int speed = 300;
    private boolean pause = false;
    private boolean interval = true;
    private String playerName = "Lukasz";
    
    Timer timer = new Timer(speed, new ActionListener() 
    {
        
            public void actionPerformed(ActionEvent e) 
            {
                setCurrentMovement();
                deleteUnactiveSignpost();
                addNewSegment();
                try {
                    moveTheSegments();
                } catch (IOException ex) {
                    Logger.getLogger(Snake.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                for(int i = 1; i < snake.size(); i++)
                {
                    if(snake.get(0).getX() == snake.get(i).getX() && snake.get(0).getY() == snake.get(i).getY())
                    {
                        System.out.println("End of game");
                        timer.stop();
                    }
                }
                
                for(Signpost x : whereTo)
                {
                    x.setActive(false);
                }
                
                x = snake.get(0).getX();
                y = snake.get(0).getY();
            }
            
    });
    
    Timer timer2 = new Timer(200, new ActionListener() 
    {
        public void actionPerformed(ActionEvent e) 
        {
            interval = true;
        }
        
    });
    
    public class Move implements KeyListener
    {

        @Override
        public void keyTyped(KeyEvent e) 
        {
            
        }

        @Override
        public void keyPressed(KeyEvent e) 
        {
            if(e.getKeyCode() == KeyEvent.VK_DOWN && !(currentMove.equals("up"))) 
            { 
                if(interval == true)
                {
                    currentMove = "down"; 
                    whereTo.add(new Signpost(currentMove, x, y));
                    interval = false;
                    timer2.restart();
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_UP && !(currentMove.equals("down"))) 
            {
                if(interval == true)
                {
                    currentMove = "up"; 
                    whereTo.add(new Signpost(currentMove, x, y)); 
                    interval = false;
                    timer2.restart();
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_RIGHT && !(currentMove.equals("left"))) 
            { 
                if(interval == true)
                {
                    currentMove = "right"; 
                    whereTo.add(new Signpost(currentMove, x, y)); 
                    interval = false;
                    timer2.restart();
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_LEFT && !(currentMove.equals("right"))) 
            { 
                if(interval == true)
                {
                    currentMove = "left"; 
                    whereTo.add(new Signpost(currentMove, x, y)); 
                    interval = false;
                    timer2.restart();
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                if(pause == false) { timer.stop(); pause = true; }
                else { timer.start(); pause = false; }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) 
        {
            
        }
        
    }
    
    public Snake()
    {
        super("Snake");
        this.setBounds((width-500)/2, (height-500)/2, 500, 500);
        initComponents();
        moveTheSnake();
        this.setResizable(false);
        this.setDefaultCloseOperation(3);
       
    }
    
    private void initComponents()
    {
        this.setLayout(null);
        
     
        snake.add(new SnakeSegment(currentMove, x, y));
        snake.get(0).setBounds(snake.get(0).getX(), snake.get(0).getY(), SnakeSegment.width, SnakeSegment.height);
        snake.get(0).setBackground(Color.BLACK);
        
        
        for(SnakeSegment x : snake)
        {
            this.add(x);
        }
        
        addGameInformations();
        
        generateNewSegment();
        this.addKeyListener(move);
        whereTo.add(new Signpost(currentMove, x, y));
        
    }
    
    private void addGameInformations()
    {
        showTheScore = new JLabel("Points: 0", JLabel.CENTER);
        showSpeedLevel = new JLabel("Speed level: 1", JLabel.CENTER);
        showAddedSegments = new JLabel("Segments: 0", JLabel.CENTER);
        player = new JLabel("Player: Lukasz ", JLabel.CENTER);
        showTheScore.setBounds(250, 0, 125, 50);
        showSpeedLevel.setBounds(0, 0, 125, 50);
        showAddedSegments.setBounds(125, 0, 125, 50);
        player.setBounds(375, 0, 125, 50);
        showTheScore.setOpaque(true);
        showSpeedLevel.setOpaque(true);
        showAddedSegments.setOpaque(true);
        player.setOpaque(true);
        showTheScore.setBackground(Color.GRAY);
        showSpeedLevel.setBackground(Color.GRAY);
        showAddedSegments.setBackground(Color.GRAY);
        player.setBackground(Color.GRAY);
        this.add(showTheScore);
        this.add(showSpeedLevel);
        this.add(showAddedSegments);
        this.add(player);
    }
    
    private void moveTheSnake()
    {
        timer.start();
        timer2.start();
    }
    
    public static void main(String[] args) 
    {
        new Snake().setVisible(true);
    }
    
    private void setCurrentMovement()
    {
        for(int i = 0; i < snake.size(); i++)
        {
            for(int j = 0; j < whereTo.size(); j++)
            {
                if(snake.get(i).getX() == whereTo.get(j).getX() && snake.get(i).getY() == whereTo.get(j).getY()) 
                {
                    snake.get(i).setCurrentMove(whereTo.get(j).getName());
                    whereTo.get(j).setActive(true);
                }
            }
        }
    }
    
    private void deleteUnactiveSignpost()
    {
        for(int i = 0; i < whereTo.size(); i++)
        {
            if(whereTo.get(i).getActive() == false)
            {
                whereTo.remove(i);
                i--;
            }
        }
    }
    
    private void generateNewSegment()
    {
        random = new Random();
        boolean correct = false;
        
        while(correct == false)
        {   
            a = (random.nextInt(46)+2)*10;
            b = (random.nextInt(41)+5)*10;
            correct = true;
            
            for(int i = 0; i < snake.size(); i++)
            {
                if(snake.get(i).getX() == a && snake.get(i).getY() == b) correct = false;
            }
        }
        
        newSegment = new SnakeSegment("none", a, b);
        newSegment.setBounds(newSegment.getX(), newSegment.getY(), SnakeSegment.width, SnakeSegment.height);
        this.add(newSegment);
        this.repaint();
    }
    
    private void addNewSegment()
    {
        if(snake.get(0).getX() == newSegment.getX() && snake.get(0).getY() == newSegment.getY())
        {
            int last = snake.size()-1;
            if(snake.get(last).getCurrentMove().equals("up")) { newSegment.setX(snake.get(last).getX()); newSegment.setY(snake.get(last).getY()+10); newSegment.setCurrentMove("up"); }
            if(snake.get(last).getCurrentMove().equals("down")) { newSegment.setX(snake.get(last).getX()); newSegment.setY(snake.get(last).getY()-10); newSegment.setCurrentMove("down"); }
            if(snake.get(last).getCurrentMove().equals("right")) { newSegment.setX(snake.get(last).getX()-10); newSegment.setY(snake.get(last).getY()); newSegment.setCurrentMove("right"); }
            if(snake.get(last).getCurrentMove().equals("left")) { newSegment.setX(snake.get(last).getX()+10); newSegment.setY(snake.get(last).getY()); newSegment.setCurrentMove("left"); }
            snake.add(newSegment);
            generateNewSegment();
            segments++;
            showAddedSegments.setText("Segments: "+segments);
            points = points + (speedLevel * 10);
            showTheScore.setText("Points: "+points);
            if(segments % 10 == 0 && segments != 0) { speedLevel++; setNewSpeed(); }
            showSpeedLevel.setText("Speed level: "+speedLevel);
        }
    }
    
    private void setNewSpeed()
    {
        if(speed <= 300 && speed >= 40) speed -= 10;
//        System.out.println("Speed level: "+speedLevel+" Speed: "+speed);
    }
    
    private void moveTheSegments() throws IOException
    {
        for(int i = 0; i < snake.size(); i++)
        {
            if(snake.get(i).getCurrentMove().equals("right"))
            {
                snake.get(i).setX(snake.get(i).getX()+10);
                snake.get(i).setLocation(snake.get(i).getX(), snake.get(i).getY());
                if(snake.get(i).getX() == 490) { snake.get(i).setX(0); snake.get(i).setLocation(snake.get(i).getX(), snake.get(i).getY()); }
            }
                    
            if(snake.get(i).getCurrentMove().equals("left"))
            {
                snake.get(i).setX(snake.get(i).getX()-10);
                snake.get(i).setLocation(snake.get(i).getX(), snake.get(i).getY());
                if(snake.get(i).getX() < 0) { snake.get(i).setX(480); snake.get(i).setLocation(snake.get(i).getX(), snake.get(i).getY()); }
            }
                    
            if(snake.get(i).getCurrentMove().equals("up"))
            {
                snake.get(i).setY(snake.get(i).getY()-10);
                snake.get(i).setLocation(snake.get(i).getX(), snake.get(i).getY());
                if(snake.get(i).getY() < 50) { snake.get(i).setY(460); snake.get(i).setLocation(snake.get(i).getX(), snake.get(i).getY()); }
            }
                    
            if(snake.get(i).getCurrentMove().equals("down"))
            {
                snake.get(i).setY(snake.get(i).getY()+10);
                snake.get(i).setLocation(snake.get(i).getX(), snake.get(i).getY());
                if(snake.get(i).getY() == 470) { snake.get(i).setY(50); snake.get(i).setLocation(snake.get(i).getX(), snake.get(i).getY()); }
            }
        }
                
            for(int i = 1; i < snake.size(); i++)
            {
                if(snake.get(0).getX() == snake.get(i).getX() && snake.get(0).getY() == snake.get(i).getY())
                {
                   JOptionPane.showMessageDialog(null, "End of Game");
                   Record record = new Record(playerName, points);
                   ScoreTable score = new ScoreTable(record);
                   score.showScores();
                   timer.stop();
                }
            }
    }
}
