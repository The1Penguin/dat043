package spaceinv.model.ships;

import static spaceinv.model.SI.*;

/*
 *   Type of space ship
 */
public class BattleCruiser extends AbstractSpaceship{
    

    // Default value
    public static final int BATTLE_CRUISER_POINTS = 100;
    public static int dx = SHIP_MAX_DX;
    public BattleCruiser(double x, double y) {
        super(x, y, dx, BATTLE_CRUISER_POINTS);
    }

}
