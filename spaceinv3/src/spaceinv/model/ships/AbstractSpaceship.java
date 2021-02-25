package spaceinv.model.ships;

import spaceinv.model.AbstractShooter;
import static spaceinv.model.SI.*;

public class AbstractSpaceship extends AbstractShooter {
    public final int points;
    public AbstractSpaceship(double x, double y, int points){
        super(SHIP_WIDTH, SHIP_HEIGHT, x, y, SHIP_MAX_DX, SHIP_MAX_DY);
        this.points = points;
    }
}
