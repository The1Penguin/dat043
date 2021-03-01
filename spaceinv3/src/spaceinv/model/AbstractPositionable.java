package spaceinv.model;

import static spaceinv.model.SI.*;

public abstract class AbstractPositionable implements Positionable{
    private final double width;
    private final double height;
    private double x;
    private double y;
    
    public AbstractPositionable(double width, double height, double x, double y){
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }
    
    @Override
    public double getX(){
        return x;
    }

    @Override
    public double getY(){
        return y;
    }
    
    @Override
    public double getWidth(){
        return width;
    }

    @Override
    public double getHeight(){
        return height;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }
}
