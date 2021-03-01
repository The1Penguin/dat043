package spaceinv.model;


import static spaceinv.model.SI.*;

/*
 *    A Gun for the game
 *    Can only fire one projectile at the time
 */
public class Gun extends AbstractShooter {

    public Gun() {
        super(GUN_WIDTH, GUN_HEIGHT, (GAME_WIDTH - GUN_WIDTH) / 2, GAME_HEIGHT - GUN_HEIGHT, 0, 0, PROJECTILE_SPEED);
    }
}
