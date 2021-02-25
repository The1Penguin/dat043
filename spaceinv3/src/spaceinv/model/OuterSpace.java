package spaceinv.model;

import static spaceinv.model.SI.GAME_WIDTH;
import static spaceinv.model.SI.OUTER_SPACE_HEIGHT;

/*
    Used to check if projectiles from gun have left our world
    Non visible class
 */
public class OuterSpace extends AbstractPositionable{
    public OuterSpace(){
        super(GAME_WIDTH, OUTER_SPACE_HEIGHT, 0, 0);
    }
}
