package spaceinv.model;


import static spaceinv.model.SI.*;

/*
 *    A Gun for the game
 *    Can only fire one projectile at the time
 */
public class Gun implements Positionable, Shootable{
    private final double width = GUN_WIDTH;
    private final double height = GUN_HEIGHT;
    private double x = (GAME_WIDTH - GUN_WIDTH) / 2;
    private double y = GAME_HEIGHT - GUN_HEIGHT;
    private double dx;
    private double dy;

    @Override
    public double getX(){
        return x;
    }

    @Override
    public double getY(){
        return y;
    }

    public double getdX(){
        return dx;
    }
    
    @Override
    public double getWidth(){
        return width;
    }

    @Override
    public double getHeight(){
        return height;
    }
    
    public void updateX(int i){
        dx = i*GUN_MAX_DX;
    }

    public void move(){
        if (dx != 0){
            x = x+dx;
        }
    }

    @Override
    public Projectile fire(){
        return Shooter.fire(this, 1);
    }

}
