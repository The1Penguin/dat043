package spaceinv.model.ships;

import static spaceinv.model.SI.*;
/*
 *   Type of space ship
 */
public class Frigate  extends AbstractSpaceship{

    // Default value
    public static final int FRIGATE_POINTS = 300;

    public Frigate(double x, double y){
        super(x, y, FRIGATE_POINTS);
    }


}
