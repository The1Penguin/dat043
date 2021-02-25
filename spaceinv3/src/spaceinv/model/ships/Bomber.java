package spaceinv.model.ships;

/*
 *   Type of space ship
 */
public class Bomber {

    // Default value
    public static final int BOMBER_POINTS = 200;

    Bomber(double x, double y){
        super(SHIP_WIDTH, SHIP_HEIGHT, x, y, SHIP_MAX_DX, SHIP_MAX_DY);
    }

}
