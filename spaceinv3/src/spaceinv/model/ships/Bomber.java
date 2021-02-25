package spaceinv.model.ships;
import static spaceinv.model.SI.*;
/*
 *   Type of space ship
 */
public class Bomber extends AbstractSpaceship{

    // Default value
    public static final int BOMBER_POINTS = 200;

    public Bomber(double x, double y){
        super(x, y, BOMBER_POINTS);
    }

}
