package spaceinv.model;

import spaceinv.model.ships.AbstractSpaceship;

import spaceinv.event.EventBus;
import spaceinv.event.ModelEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 *  SI (Space Invader) class representing the overall
 *  data and logic of the game
 *  (nothing about the look/rendering here)
 */
public class SI {

    // Default values (not to use directly). Make program adaptable
    // by letting other programmers set values if they wish.
    // If not, set default values (as a service)
    public static final int GAME_WIDTH = 500;
    public static final int GAME_HEIGHT = 500;
    public static final int LEFT_LIMIT = 50;
    public static final int RIGHT_LIMIT = 450;
    public static final int SHIP_WIDTH = 20;
    public static final int SHIP_HEIGHT = 20;
    public static final int SHIP_MAX_DX = 3;
    public static final int SHIP_MAX_DY = 2;
    public static final int GUN_WIDTH = 20;
    public static final int GUN_HEIGHT = 20;
    public static final double GUN_MAX_DX = 2;
    public static final double PROJECTILE_WIDTH = 5;
    public static final double PROJECTILE_HEIGHT = 5;
    public static final double PROJECTILE_SPEED = 2;
    public static final int GROUND_HEIGHT = 20;
    public static final int OUTER_SPACE_HEIGHT = 10;

    public static final long ONE_SEC = 1_000_000_000;
    public static final long HALF_SEC = 500_000_000;
    public static final long TENTH_SEC = 100_000_000;

    private static final Random rand = new Random();

    private final List<AbstractSpaceship> ships;
    private final Gun gun;
    private final Ground ground;
    private final OuterSpace outer_space;

    private final List<Projectile> shipBombs = new ArrayList<>();
    private final List<List<AbstractSpaceship>> formation;
    private Projectile gunProjectile;
    private int points;
    private int lives = 3;

    public SI(Gun gun, List<AbstractSpaceship> ships, List<List<AbstractSpaceship>> formation) {
        this.gun = gun;
        this.ground = new Ground();
        this.outer_space = new OuterSpace();
        this.ships = ships;
        this.formation = formation;
    }


    // Timing. All timing handled here!
    private long lastTimeForMove;
    private long lastTimeForFire;
    private int shipToMove = 0;

    // ------ Game loop (called by timer) -----------------

    public void update(long now) {
        /*
             Movement
         */
        moveGun();
        moveShip(now);
        moveProjectiles();
                
        /*
            Ships fire
         */
        randomFire(now);

        /*
             Collisions
         */
        checkGunShot();
        checkBombs();

        if (ships.size() == 0) {
            EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.HAS_WON));
        }
        if (lives == 0) {
            EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.HAS_LOST));
        }
    }

    public void randomFire(long now) {
        if (now - lastTimeForFire > ONE_SEC) {
            int shipToFire = rand.nextInt(ships.size());
            shipBombs.add(ships.get(shipToFire).fire());
            lastTimeForFire = now;
        }

    }

    public void moveGun() {
        if (!(hitRightLimit(gun) || hitLeftLimit(gun))){
            gun.move();
        }

    }

    public void moveProjectiles() {
        if (gunProjectile != null){
            gunProjectile.move();
        }
        for ( Projectile p : shipBombs) {
            p.move();
        }
    }

    public boolean hitRightLimit(AbstractShooter obj) {
        if (obj.getX() + obj.getdX() > RIGHT_LIMIT){
            return true;
        }
        return false;
    }

    public boolean hitLeftLimit(AbstractShooter obj) {
        if (obj.getX() + obj.getdX() < LEFT_LIMIT){
            return true;
        }
        return false;
    }
    public void moveShip(long now){
        if (now - lastTimeForMove > HALF_SEC) {
            for (AbstractSpaceship s : ships ) {
                s.move();
                if (hitLeftLimit(s) || hitRightLimit(s)) {
                    if (!hitLeftLimit(s)){
                        s.move().move();
                    }
                    switchDirection(s);
                    s.move().move();    // I don't know why it might have to move right but it has to
                }
            }
            lastTimeForMove = now;
        }
    }

    public void switchDirection(AbstractSpaceship s) {
        for ( AbstractSpaceship sp : ships) {
            sp.moveY();
            if (sp.getClass() == s.getClass()) {
                sp.setdX(-sp.getdX());
            }
        }
    }

    public boolean collision(AbstractPositionable obj1, AbstractPositionable obj2) {
        return (obj1.getX() < obj2.getX() + obj2.getWidth() &&
    obj1.getX() + obj1.getWidth() > obj2.getX() &&
    obj1.getY() < obj2.getY() + obj2.getHeight() &&
    obj1.getY() + obj1.getHeight() > obj2.getY());
    }
    
    public void checkGunShot() {
        if (gunProjectile != null){
            List<AbstractSpaceship> toRemove = new ArrayList<>();
            for ( AbstractSpaceship s : ships) {
                if (collision(gunProjectile, s)) {
                    toRemove.add(s);
                    points += s.getPoints();
                }   
            }
            boolean removed = ships.removeAll(toRemove);
            if (collision(gunProjectile, outer_space)) {
                removed = true;
            }

            if (removed) {
                EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.GUN_HIT_SHIP, gunProjectile));
                gunProjectile = null;
            }
        }
    }
    
    public void checkBombs() {
        List<Projectile> toRemove = new ArrayList<>();
        for ( Projectile p : shipBombs ) {
            if (collision(p, ground)) {
                toRemove.add(p);
                EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BOMB_HIT_GROUND));
            } else if (collision(p, gun)) {
                lives--;
                toRemove.add(p);
                EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.BOMB_HIT_GUN, gun));
            }
        }
        shipBombs.removeAll(toRemove);
    }

    // ---------- Interaction with GUI  -------------------------

    public void fireGun() {
        if (gunProjectile == null){
            gunProjectile = gun.fire();
            EventBus.INSTANCE.publish(new ModelEvent(ModelEvent.Type.GUN_SHOOT));
        }
    }

    public void updateX(int i) {
        gun.updateX(i, GUN_MAX_DX);
    }

    public List<Positionable> getPositionables() {
        List<Positionable> ps = new ArrayList<>();
        ps.add(gun);
        if (gunProjectile != null) {
        ps.add(gunProjectile);
        }
        ps.addAll(ships);
        ps.addAll(shipBombs);
        return ps;
    }

    public int getPoints() {
        return points;
    }

    public int getLives() {
        return lives;
    }

    public String getScreen() {
        if (lives == 0) {
            return "You lost";
        } else {
            return "You won";
        }
    }
}
