package gameoflife;

import java.io.*;
import java.awt.*; //needed for graphics
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.*; //needed for graphics
import static javax.swing.JFrame.EXIT_ON_CLOSE; //needed for graphics

public class GameOfLife extends JFrame {

    //FIELDS
    
    //You can try changing these variables to see what happens
    int initPreyEnergy = 40; //inital amount of energy prey has
    int initPredEnergy = 50; //inital amount of energy predator has
    boolean attract = true; //attracts prey and predators to areas where they can gain energy
    int numDays = 1000; //how many days the program will run for
    int timeBetweenDays = 100; //how many ms you wait between each day
    
    //Other variables (preferably do not change)
    int currDay = 1;
    Color dirtColor = new Color (120, 66, 18);
    Color plantColor = new Color (37, 180, 45);
    Color preyColor = new Color (227, 223, 223);
    Color predatorColor = new Color (98, 101, 103);
    int width = 1000; //width of the window in pixels
    int height = 1000;
    int borderWidth = 50;
    int numCellsX = 100; //width of the grid (in cells)
    int numCellsY = 100;
    /*
    What each integer in the cellsOn array represents:
    0 - dirt
    1 - plant
    2 - prey
    3 - predator
     */
    int cellsOn[][] = new int[numCellsY][numCellsX]; 
    int cellsOnNext[][] = new int[numCellsY][numCellsX]; 
    
    int preyEnergy[][] = new int [numCellsY][numCellsX]; 
    int predatorEnergy[][] = new int [numCellsY][numCellsX]; 
    
    int cellWidth = (width - 2 * borderWidth)/numCellsX;
     
    int labelX = width / 2;
    int labelY = borderWidth - 20;
    
    //METHODS
    //plants the first day
    public void plantFirstDay() throws IOException {
        makeEveryoneDead();
        for (int i = 0; i < numCellsY; i++) {
            for (int j = 0; j < numCellsX; j++) { 
                Random r = new Random();
                int rand = r.nextInt(4);
                //randomly fills the screen with around an equal amount of each state the cell can be in
                cellsOn[i][j] = rand;
                //sets initial prey and predator energy levels
                if (rand == 2)
                    preyEnergy[i][j] = initPreyEnergy;
                else if (rand == 3)
                    predatorEnergy[i][j] = initPredEnergy;
            }
        }
    }
    
    //Sets all cells to dead 
    public void makeEveryoneDead() {
        for (int i = 0; i < numCellsX; i++){
            for (int j = 0; j < numCellsY; j++){
                cellsOn[i][j] = 0;
            }
        }
    }
    
    //Applies the rules of The Game of Life to set the values of the cellsOnNext[][] array,
    //based on the current values in the cellsOn[][] array
    public void computeNextDay() {
        int numPlantNeighbors, numPreyNeighbors, numPredatorNeighbors;
        for (int i = 0; i < numCellsX; i++) {
            for (int j = 0; j < numCellsY; j++) {
                numPlantNeighbors = countLivingNeighbors(i, j, 1); //counts num of neighbors that are plant cells
                numPreyNeighbors = countLivingNeighbors(i, j, 2); //counts num of neighbors that are prey cells
                numPredatorNeighbors = countLivingNeighbors(i, j, 3); //counts num of neighbors that are predator cells
                //if the current cell is dirt
                if (cellsOn[i][j] == 0){
                    //if there are exactly 2 prey around, they reproduce 
                   if (numPreyNeighbors == 2)
                        cellsOnNext[i][j] = 2;
                    //if there are exactly 2 predators around, they reproduce
                    else if (numPredatorNeighbors == 2)
                        cellsOnNext[i][j] = 3;
                    //a 10% chance of the dirt turning into a plant
                    else if (Math.random() <= 0.1)
                        cellsOnNext[i][j] = 1;
                    //otherwise, the dirt stays as dirt
                    //if attract is true
                    else if (attract){
                        //if the dirt has exactly 4 plants around it, it attracts a prey
                        if (numPlantNeighbors == 4)
                            cellsOnNext[i][j] = 2;
                        //if the dirt has exactly 4 prey around it, it attracts a predator
                        else if (numPreyNeighbors == 4)
                            cellsOnNext[i][j] = 3;
                    //otherwise, the dirt stays as dirt    
                    }else
                        cellsOnNext[i][j] = 0;
                //if the current cell is a plant
                }else if (cellsOn[i][j] == 1){
                    //if there are 2 or more prey/predators around, they eat the plant
                    if (numPreyNeighbors >= 2 || numPredatorNeighbors >= 2)
                        cellsOnNext[i][j] = 0;
                    //otherwise, the plant stays as a plant
                    else
                        cellsOnNext[i][j] = 1;
                //if the current cell is prey
                }else if (cellsOn[i][j] == 2){
                    //if there are 3 or more predators, the prey dies because it's eaten
                    //dies if there are 1 or no prey around because it's too lonely :(
                    //dies if there are 5 or more prey around because of overcrowding and not
                        //enough plants for everyone to eat
                    //dies if the prey runs out of energy
                    if (numPredatorNeighbors >= 3 || numPreyNeighbors <= 1 || numPreyNeighbors >= 5 || preyEnergy[i][j] <= 0)
                        cellsOnNext[i][j] = 0;
                    //if there is more than 3 plants around, it stays as a prey and it gains energy 
                        //based on how many plants it ate
                    else if (numPlantNeighbors >= 3){
                        cellsOnNext[i][j] = 2;
                        preyEnergy[i][j] += numPlantNeighbors;
                    //otherwise, the prey stays as a prey but it loses 10% of its initial energy since it didn't
                        //eat anything that gave it energy
                    }else{
                        cellsOnNext[i][j] = 2;
                        preyEnergy[i][j] -= initPreyEnergy/10;
                    }
                //if the current cell is a predator
                }else{
                    //dies if there are 1 or no predators around because it's too lonely :(
                    //dies if there are 5 or more predators around because of overcrowding and not
                        //enough plants for everyone to eat
                    //dies if the predator runs out of energy
                    if (numPredatorNeighbors <= 1 || numPredatorNeighbors >= 5 || predatorEnergy[i][j] <= 0){
                        cellsOnNext[i][j] = 0;
                    //if they are more than 3 prey around, it stays as a predator and it gains energy
                        //based on how many prey it ate
                    }else if (numPreyNeighbors >= 3){
                        cellsOnNext[i][j] = 3;
                        predatorEnergy[i][j] += numPreyNeighbors;
                    //otherwise, the prey stays as a prey but it loses 10% of its initial energy since it didn't
                        //eat anything that gave it energy
                    }else{
                        cellsOnNext[i][j] = 3;
                        predatorEnergy[i][j] -= initPredEnergy/10;
                    }
                }
            }
        }
    }
    
    //Overwrites the current day's 2-D array with the values from the next day's 2-D array
    public void plantNextDay() {
        for (int i = 0; i < numCellsX; i++)
            for (int j = 0; j < numCellsY; j++)
                cellsOn[i][j] = cellsOnNext[i][j];
    }
    
    //Counts the number of living cells adjacent to cell (i, j)
    //while state represents the state of the cell (dirt, plant, prey or predator)
    public int countLivingNeighbors(int i, int j, int state) {
        int begRow, begCol, endRow, endCol;
        int numNeighbours = 0;
        if (i == 0)
            begCol = i;
        else 
            begCol = i - 1;

        if (i == numCellsX - 1) 
            endCol = i;
        else 
            endCol = i + 1;
        
        if (j == 0) 
            begRow = j;
        else 
            begRow = j - 1;    

        if (j == numCellsY - 1) 
            endRow = j;
        else 
            endRow = j + 1;
        for (int c = begCol; c <= endCol; c++){
            for (int r = begRow; r <= endRow; r++){
                if (c != i || r != j)
                    if (cellsOn[c][r] == state)
                        numNeighbours++; 
            }
        }
        return numNeighbours;
    }
    
    //Makes the pause between days
    public static void sleep(int duration) {
        try {
            Thread.sleep(duration);
        } 
        catch (Exception e) {}
    }
    
    //Displays the statistics at the top of the screen
    void drawLabel(Graphics g, int state) {
        g.setColor(Color.black);
        g.fillRect(0, 0, width, borderWidth);
        g.setColor(Color.WHITE);
        g.drawString("Days: " + state, labelX, labelY);
    }
    
    //Draws the current day of living cells on the screen
    public void paint(Graphics g){
        Image img = createImage();
        g.drawImage(img,8,30,this);
    }
    
    //Draws the current day of living cells on the screen
    public Image createImage(){
        BufferedImage bufferedImage = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();
        int x, y, i, j;
        x = borderWidth;
        y = borderWidth;
        drawLabel(g, currDay);
        for (i = 0; i < numCellsX; i++) {
            for (j = 0; j < numCellsY; j++) {
                if(cellsOn[i][j] == 0)
                    g.setColor(dirtColor);
                else if (cellsOn[i][j] == 1)
                    g.setColor(plantColor);
                else if (cellsOn[i][j] == 2)
                    g.setColor(preyColor);
                else
                    g.setColor(predatorColor);
                g.fillRect(x, y, cellWidth, cellWidth);
                g.setColor(Color.black);
                g.drawRect(x, y, cellWidth, cellWidth);
                x += cellWidth;
            }
            x = borderWidth;
            y += cellWidth;
        }       
        return bufferedImage;
    }

    //Sets up the JFrame screen
    public void initializeWindow() {
        setTitle("Game of Life Simulator");
        setSize(height, width);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.black);
        setVisible(true); //calls paint() for the first time
    }
    
    //Main algorithm
    public static void main(String args[]) throws IOException {
        GameOfLife currGame = new GameOfLife();
        currGame.initializeWindow();
        currGame.plantFirstDay(); //Sets the initial day of living cells, either by reading from a file or creating them algorithmically
       for (int i = 1; i < currGame.numDays; i++) {
            currGame.computeNextDay();
            currGame.plantNextDay();
            currGame.currDay++;
            sleep(currGame.timeBetweenDays);
            currGame.repaint();  
        }        
    }     
} //end of class
