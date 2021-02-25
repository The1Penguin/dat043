package spaceinv.model;


import static spaceinv.model.SI.*;

/*
 *    A Gun for the game
 *    Can only fire one projectile at the time
 */
public class Gun extends AbstractPositionable implements Positionable, Shootable{
    private double dx;
    private double dy;

    public Gun(){
        super(GUN_WIDTH, GUN_HEIGHT, (GAME_WIDTH - GUN_WIDTH) / 2, GAME_HEIGHT - GUN_HEIGHT);
    }

    public double getdX(){
        return dx;
    }
    
    public void updateX(int i){
        dx = i*GUN_MAX_DX;
    }

    public void move(){
        if (dx != 0){
            setX(getX()+dx);
        }
    }

    @Override
    public Projectile fire(){
        return Shooter.fire(this, 1);
    }

}
