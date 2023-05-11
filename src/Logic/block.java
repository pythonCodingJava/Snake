package Logic;

public class block {

    public int x;
    public int y;
    public int prex;
    public int prey;
    
    public block(int x, int y) {
        this.x = x;
        this.y = y;
        prex = this.x;
        prey = this.y;
    }
    
    public void Pos(int deltaX, int deltaY) {
        prex = x;
        prey = y;
        x = deltaX;
        y = deltaY;
    }
}
