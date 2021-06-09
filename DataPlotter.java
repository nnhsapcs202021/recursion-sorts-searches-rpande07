import java.awt.*;
import java.io.*;
import java.util.*;
/**
 * This DataPlotter object reads a space delimited text file of elevations
 * and plots the result as a range of greyscale images, and then calculates 
 * and plots the steepest downward path from each location in the image.
 * 
 * @rpande07 
 * @06/08/2021 
 */
public class DataPlotter
{
    private static String fileName = "Colorado";
    private static int[][] grid;
    private static DrawingPanel panel;
    private static Scanner fileReader;
    private static int rows, cols;
    
    // main method for reading elevation values and plotting data for elevation and water flow
    public static void main(String[] args) throws IOException
    {
        readValues();
        plotData();
        try {Thread.sleep(3000); } catch (Exception e){};  // pause display for 3 seconds
        plotAllPaths();
    }
    
    // reads values and displays corner values
    private static void readValues() throws IOException
    {
        fileReader = new Scanner(new File(fileName + ".dat"));
        rows = fileReader.nextInt();    // first integer in file
        cols = fileReader.nextInt();    // second integer in file

        // instantiate and initialize the instance variable grid
        grid = new int[rows][cols];
        
        // then read all of the data into the array in row major order
        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                grid[row][col] = fileReader.nextInt();
            }
        }
        System.out.println("upper left: " + grid[0][0] + "\tupper right: " + grid[0][cols-1]);
        System.out.println("lower left: " + grid[rows-1][0] + "\tlower right: " + grid[rows-1][cols-1]);
        
        fileReader.close();
    }

    // plot the altitude data read from file
    private static void plotData()
    {
        panel = new DrawingPanel(cols, rows);
        int min = 9999;
        int max = 0;
        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                if (grid[row][col] < min)
                {
                    min = grid[row][col];
                }
                if (grid[row][col] > max)
                {
                    max = grid[row][col];
                }
            }
        }
        double scaleFactor = 255.0/(double)(max-min);
        //System.out.println(scaleFactor);
        int colorValue = 0;
        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                colorValue = (int)((grid[row][col]-min) * scaleFactor);
                Color color = new Color(colorValue, colorValue, colorValue);
                panel.setPixel(col, row, color);
            }
        }
    }

    // for a given x, y value, plot the downhill path from there
    private static void plotDownhillPath(int x, int y)
    {
        int minElevation = 9999;
        int destinationX = 0;
        int destinationY = 0;
        
        for (int x1 = x-1; x1 <= x+1; x1++)
        {
            for (int y1 = y-1; y1 <= y+1; y1++)
            {
                // Check if x == x1 and y == y1 to skip over
                if (x1 == x && y1 == y)
                {
                    continue;
                }
                
                // Check if (x1, y1) is within the bounds of the grid
                if (x1 >= 0 && x1 <= rows-1 && y1 >= 0 && y1 <= cols-1)
                {
                    if (grid[x1][y1] < minElevation)
                    {
                        destinationX = x1;
                        destinationY = y1;
                        minElevation = grid[x1][y1];
                    }
                }
            }
        }
        
        if (minElevation < grid[x][y])
        {
            panel.setPixel(destinationY, destinationX, Color.blue);
            plotDownhillPath(destinationX, destinationY);
        }
    }
    
    // plots the different paths
    private static void plotAllPaths()
    {
        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                plotDownhillPath(row, col);
            }
        }
    }

}