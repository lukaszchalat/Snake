package snake;

import java.io.*;
import javax.swing.*;
import java.awt.*;

public class ScoreTable extends JFrame
{
    JLabel[] scoreFields;
    JPanel panel;
    int x = Toolkit.getDefaultToolkit().getScreenSize().width;
    int y = Toolkit.getDefaultToolkit().getScreenSize().height;
    
    public ScoreTable(Record record) throws FileNotFoundException, IOException
    {
        String[] content = toArray("resource/test.txt");
        Record[] records = toRecordArray(content);
        insertNewRecord(records, record.getName(), record.getPoints());
        
        FileWriter plik = new FileWriter("resource/test.txt");
        try (PrintWriter zapis = new PrintWriter(plik)) 
        {
            for(int i = 0; i < records.length; i++)
            {
                zapis.println(records[i].getNumber()+" "+records[i].getName()+" "+records[i].getPoints());
            }
            
            zapis.close();
        }
    }
    
    public void showScores() throws FileNotFoundException, IOException
    {
        String[] content = toArray("resource/test.txt");
        Record[] records = toRecordArray(content);
        
        this.setTitle("Table of Scores");
        this.setBounds((x-300)/2, (y-600)/2, 400, 400);
        scoreFields = new JLabel[10];
        
        int x = 20;
        
        for(int i = 0; i < scoreFields.length; i++)
        {
            scoreFields[i] = new JLabel();
            scoreFields[i].setBounds(20, x, 150, 30);
            x += 30; 
        }
        
        panel = new JPanel();
        panel.setLayout(null);
        
        for(int i = 0; i < scoreFields.length; i++)
        {
            scoreFields[i].setText(records[i].getNumber()+" "+records[i].getName()+" "+records[i].getPoints());
            panel.add(scoreFields[i]);
        }
        
        this.add(panel);
        this.setDefaultCloseOperation(2);
        this.setVisible(true);
    }
    
    private void insertNewRecord(Record[] records, String name, int points)
    {
        int position = -1;
        
        for(int i = 0; i < records.length; i++)
        {
            if(points > records[i].getPoints())
            {
                position = i;
                break;
            }
        }
        
        if(position >= 0)
        {
            if(position != records.length - 1)
            {
                for(int i = records.length - 1; i > position; i--)
                {
                    records[i].setName(records[i - 1].getName());
                    records[i].setPoints(records[i - 1].getPoints());
                }
            }
            
            records[position].setName(name);
            records[position].setPoints(points);
        }
    }
    
    private Record[] toRecordArray(String[] content)
    {
        int x = content.length;
        Record[] records = new Record[x];
        
        for(int i = 0; i < x; i++)
        {
            String[] buffer = content[i].split(" ");
            records[i] = new Record(Integer.parseInt(buffer[0]), buffer[1], Integer.parseInt(buffer[2]));
        }
        
        return records;
    }
    
    private String[] toArray(String file) throws FileNotFoundException
    {
        int index = 10;
        String[] fileContent = new String[index];
        
        BufferedReader odczyt = new BufferedReader(new FileReader(file));
        
        try
        {
            for(int i = 0; i < index; i++)
            {
                fileContent[i] = odczyt.readLine();
            }
            
            odczyt.close();
        }
        catch(IOException e)
        {
            e.getMessage();
        }
        
        return fileContent;
    }

}
