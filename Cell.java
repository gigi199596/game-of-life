/**
 * Object Class used to create cells
 */
public class Cell {
    //instance variables
    private int _state;  //0 for dead and 1 for alive
    private int _actual_status;  //1 for living, 2 for surviving and 3 for dead
    private int _next_status;

    /**
     * Default constructor invoke a dead cell
     */
    public Cell()
    {
        _state = 0;
        _actual_status = 3;
    }

    public Cell(int state, int actual_status)
    {
        _state = state;
        _actual_status = actual_status;
    }

    //getters
    public int getS(){ return _state;}
    public int getAS(){ return _actual_status;}
    public int getNS(){ return _next_status;}

    //setters
    public void setS(int state){ _state = state;}
    public void setAS(int actual){ _actual_status = actual;}
    public void setNS(int next){ _next_status = next;}
}
