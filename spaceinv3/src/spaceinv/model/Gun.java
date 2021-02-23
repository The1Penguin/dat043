package spaceinv.model;


import static spaceinv.model.SI.*;

/*
 *    A Gun for the game
 *    Can only fire one projectile at the time
 */
public class Gun implements Positionable{
    private final double width = GUN_WIDTH;
    private final double height = GUN_HEIGHT;
    private double x = (GAME_WIDTH - GUN_WIDTH) / 2;
    private double y = GAME_HEIGHT - GUN_HEIGHT;
    private double dx;
    private double dy;

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }
    
    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }
    
    public void updateX(int i){
        dx = i*SHIP_MAX_DX;
    }

    public void move(){
        if (dx != 0){
            if (0 <= x+dx && x+dx <= GAME_WIDTH-SHIP_WIDTH){
                x = x+dx;
            }
        }

    }
}
