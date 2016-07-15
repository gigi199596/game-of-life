/**
 * The Game of life created by gigi199596 on 15/07/16
 */
import edu.princeton.cs.algs4.StdDraw;

public class GameOfLife {
    private static final int scale = 51;
    private static Cell[][] grid = new Cell[scale][scale];

    public static void main(String[] args) throws InterruptedException {
        StdDraw.setXscale(0, scale);    //the best we can do to reduce border is to set the scale to 0
        StdDraw.setYscale(0, scale);
        int temp_sum;       //temporary sum used to compute neighbours of each case
        int x_coord;        //double raw coord converted into "int" value to fit into grid
        int y_coord;

        //fill the grid with dead cells
        for (int i = 1; i < scale; i++) {
            for (int j = 1; j< scale; j++) {
                grid[i][j] = new Cell();
            }
        }

        //this while loop allow the user to fill the grid with the mouse. To delete a cube, just click a second time on it.
        //while ENTER key is not pressed do...
        while( !StdDraw.isKeyPressed(10)){
            while(StdDraw.mousePressed()){
                x_coord = (int) Math.round(StdDraw.mouseX());
                y_coord = (int) Math.round(StdDraw.mouseY());
                if ( x_coord < scale && x_coord >= 1 && y_coord < scale && y_coord >= 1){
                    if ( grid[x_coord][y_coord].getS() == 1){
                        //we need to delete the cell
                        grid[x_coord][y_coord].setS(0);
                        grid[x_coord][y_coord].setAS(3);
                        StdDraw.setPenColor(StdDraw.WHITE);
                        StdDraw.filledRectangle(x_coord, y_coord, 0.5, 0.5);
                    }else{
                        //we create a cell
                        grid[x_coord][y_coord] = new Cell(1,1);
                        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                        StdDraw.filledRectangle(x_coord, y_coord, 0.5, 0.5);
                    }
                }
                Thread.sleep(50);    //to avoid misleading clicks
            }
        }

        //while SPACE key is not pressed do...
        while (!StdDraw.isKeyPressed(32))
        {
            // i equal x and j equal y
            for (int i = 1; i < scale; i++) {
                for (int j = 1; j < scale; j++) {

                    if ( i==1 )
                    {
                        if ( j==1 )
                        {
                            // 3 checks only: we are in the downside left corner
                            temp_sum = grid[2][1].getS() + grid [1][2].getS() + grid[2][2].getS();
                        }
                        else if ( j==scale-1 )
                        {
                            // 3 checks only: we are in the upside left corner
                            temp_sum = grid[1][scale-2].getS() + grid [2][scale-1].getS() + grid[2][scale-2].getS();
                        }
                        else
                        {
                            //we are along the left border
                            temp_sum = grid[i][j-1].getS() + grid [i][j+1].getS() + grid[i+1][j].getS() + grid[i+1][j+1].getS() + grid[i+1][j-1].getS();
                        }
                    }
                    else if ( i==scale-1 )
                    {
                        if ( j==scale-1 )
                        {
                            // 3 checks only: we are in the upside right corner
                            temp_sum = grid[scale-1][scale-2].getS() + grid [scale-2][scale-1].getS() + grid[scale-2][scale-2].getS();
                        }
                        else if ( j==1 )
                        {
                            // 3 checks only: we are in the downside right corner
                            temp_sum = grid[scale-2][1].getS() + grid [scale-1][2].getS() + grid[scale-2][2].getS();
                        }
                        else{
                            //we are along the right border
                            temp_sum = grid[i][j-1].getS() + grid [i][j+1].getS() + grid[i-1][j].getS() + grid[i-1][j+1].getS() + grid[i-1][j-1].getS();
                        }
                    }
                    else if ( j==1 )
                    {
                        //we are along the downside border
                        temp_sum = grid[i-1][j].getS() + grid [i+1][j].getS() + grid[i-1][j+1].getS() + grid[i][j+1].getS() + grid[i+1][j+1].getS();
                    }
                    else if ( j==scale-1 )
                    {
                        //we are along the upside border
                        temp_sum = grid[i-1][scale-1].getS() + grid [i+1][scale-1].getS() + grid[i-1][scale-2].getS() + grid[i][scale-2].getS() + grid[i+1][scale-2].getS();
                    }else{
                        //we test all around the current case
                        temp_sum =  grid[i-1][j+1].getS()         + grid [i][j+1].getS()    + grid[i+1][j+1].getS();
                        temp_sum += grid[i-1][j].getS()          /*current: grid[i][j]*/    + grid [i+1][j].getS();
                        temp_sum += grid[i-1][j-1].getS()         + grid [i][j-1].getS()    + grid[i+1][j-1].getS();
                    }



                    //now we check if we need to highlight the case
                    // we must not change the state in this step!
                    if (temp_sum == 3 && grid[i][j].getAS() == 3) {
                        // the cell have exactly 3 neighbours  --> living
                        grid[i][j].setNS(1);
                        StdDraw.setPenColor(StdDraw.GREEN);
                        StdDraw.filledRectangle(i, j, 0.5, 0.5);
                    }else if ((temp_sum < 2  || temp_sum > 3) && (grid[i][j].getAS() == 1 || grid[i][j].getAS() == 2)){
                        // the cell have less than 2 neighbours or more than 3 nbs --> dies
                        grid[i][j].setNS(3);
                        StdDraw.setPenColor(StdDraw.RED);
                        StdDraw.filledRectangle(i, j, 0.5, 0.5);
                    }else if (temp_sum == 2 && grid[i][j].getAS() == 1){
                        //the celle have exactly 2 nbs --> surviving
                        grid[i][j].setNS(2);
                        StdDraw.setPenColor(StdDraw.YELLOW);
                        StdDraw.filledRectangle(i, j, 0.5, 0.5);
                    }
                    else if ( grid[i][j].getAS() == 2 && grid[i][j].getNS() == 2  && temp_sum == 2){
                        //the cell is still surviving
                        grid[i][j].setNS(2);
                        StdDraw.setPenColor(StdDraw.YELLOW);
                        StdDraw.filledRectangle(i, j, 0.5, 0.5);
                    }
                }
            }
            Thread.sleep(70);

            //update all the grid
            for (int i = 1; i < scale; i++) {
                for (int j = 1; j < scale; j++) {
                    //a new cell appear
                    if (grid[i][j].getAS() == 3 && grid[i][j].getNS() == 1){
                        grid[i][j].setS(1);
                        grid[i][j].setAS(1);
                        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                        StdDraw.filledRectangle(i, j, 0.5, 0.5);
                    }
                    //erase the cell bcs she's dead
                    else if (grid[i][j].getAS() == 1 && grid[i][j].getNS() == 3){
                        grid[i][j].setS(0);
                        grid[i][j].setAS(3);
                        StdDraw.setPenColor(StdDraw.WHITE);
                        StdDraw.filledRectangle(i, j, 0.5, 0.5);
                    }
                    // the cell survive
                    else if (grid[i][j].getAS() == 1 && grid[i][j].getNS() == 2){
                        grid[i][j].setS(1);
                        grid[i][j].setAS(2);
                        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                        StdDraw.filledRectangle(i, j, 0.5, 0.5);
                    }
                    else if (grid[i][j].getAS() == 2 && grid[i][j].getNS() == 3){
                        //the surviving cell is dead
                        grid[i][j].setAS(3);
                        grid[i][j].setS(0);
                        StdDraw.setPenColor(StdDraw.WHITE);
                        StdDraw.filledRectangle(i, j, 0.5, 0.5);
                    }
                    else if(grid[i][j].getAS() == 2 && grid[i][j].getNS() == 2){
                        //still surviving
                        grid[i][j].setAS(2);
                        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                        StdDraw.filledRectangle(i, j, 0.5, 0.5);
                    }
                }
            }
            Thread.sleep(300);
        }
    }
}
