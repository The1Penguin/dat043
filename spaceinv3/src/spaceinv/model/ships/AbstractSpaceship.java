package spaceinv.model.ships;

import spaceinv.model.AbstractShooter;
import static spaceinv.model.SI.*;

public class AbstractSpaceship extends AbstractShooter {
    private final int points;
    public AbstractSpaceship(double x, double y, int dx, int points){
        super(SHIP_WIDTH, SHIP_HEIGHT, x, y, dx, SHIP_MAX_DY, -PROJECTILE_SPEED);
        this.points = points;
    }

    public int getPoints(){
        return this.points;
    }
}
