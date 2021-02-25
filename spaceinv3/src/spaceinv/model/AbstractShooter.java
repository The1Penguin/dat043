package spaceinv.model;


import static spaceinv.model.SI.*;

/*
 *    A Gun for the game
 *    Can only fire one projectile at the time
 */
public class AbstractShooter extends AbstractPositionable implements Shootable{  
    private double dx;
    private double dy;

    public AbstractShooter(double width, double height, double x, double y, double dx, double dy){
        super(width, height, x, y);
        this.dx = dx;
        this.dy = dy;
    }

    public double getdX(){
        return dx;
    }

    public double getdY(){
        return dy;
    }
    
    public void setdX(double dx){
        this.dx = dx;
    }

    public void setdY(double dy){
        this.dy = dy;
    }

    @Override
    public Projectile fire(){
        return Shooter.fire(this, PROJECTILE_SPEED);
    }
}