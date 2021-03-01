package spaceinv.model;

import static spaceinv.model.SI.*;

public abstract class AbstractShooter extends AbstractPositionable implements Shootable{  
    private double dx;
    private double dy;
    private double projectileSpeed;

    public AbstractShooter(double width, double height, double x, double y, double dx, double dy, double projectileSpeed){
        super(width, height, x, y);
        this.dx = dx;
        this.dy = dy;
        this.projectileSpeed = projectileSpeed;
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

    public void updateX(int i, double speed){
        setdX(i*speed);
    }

    public Projectile fire(){
        return Shooter.fire(this, projectileSpeed);
    }

    public AbstractShooter move(){
        if (getdX() != 0){
            setX(getX()+getdX());
        }
        return this;
    }
    
    public void moveY(){
        setY(getY()+getdY());
    }
}
