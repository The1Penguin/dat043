package spaceinv.model;


import static spaceinv.model.SI.*;

/*
 *    A Gun for the game
 *    Can only fire one projectile at the time
 */
public class Gun implements Positionable{
    private double x;
    private double y;
    private double dx;
    private double dy;
    private double width;
    private double height;

    double getX(){
        return x;
    }

    double getY(){
        return y;
    }
    
    double getWidth(){
        return width;
    }

    double getHeight(){
        return height;
    }
}
