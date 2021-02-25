package spaceinv.model;


import static spaceinv.model.SI.*;

/*
    The ground where the Gun lives.
    Used to check if projectiles from ships have hit the ground
 */
public class Ground extends AbstractPositionable {
    public Ground(){
        super(GAME_WIDTH, OUTER_SPACE_HEIGHT, 0, GAME_HEIGHT - GAME_HEIGHT);
    }

}
