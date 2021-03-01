package spaceinv.model.ships;
import static spaceinv.model.SI.*;
/*
 *   Type of space ship
 */
public class Bomber extends AbstractSpaceship{

    // Default value
    public static final int BOMBER_POINTS = 200;
    public static int dx = SHIP_MAX_DX;

    public Bomber(double x, double y) {
        super(x, y, dx, BOMBER_POINTS);
    }

}
