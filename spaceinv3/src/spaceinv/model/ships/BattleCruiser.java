package spaceinv.model.ships;

import spaceinv.model.AbstractShooter;
import static spaceinv.model.SI.*;

/*
 *   Type of space ship
 */
public class BattleCruiser extends AbstractShooter{

    // Default value
    public static final int BATTLE_CRUISER_POINTS = 100;

    BattleCruiser(double x, double y){
        super(SHIP_WIDTH, SHIP_HEIGHT, x, y, SHIP_MAX_DX, SHIP_MAX_DY);
    }

}
