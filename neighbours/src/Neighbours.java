import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.round;
import static java.lang.Math.sqrt;
import static java.lang.System.*;

/*
 *  Program to simulate segregation.
 *  See : http://nifty.stanford.edu/2014/mccown-schelling-model-segregation/
 *
 * NOTE:
 * - JavaFX first calls method init() and then method start() far below.
 * - To test uncomment call to test() first in init() method!
 *
 */
// Extends Application because of JavaFX (just accept for now)
public class Neighbours extends Application {

    class Actor {
        final Color color;        // Color an existing JavaFX class
        boolean isSatisfied;      // false by default

        Actor(Color color) {      // Constructor to initialize
            this.color = color;
        }
    }

    // Below is the *only* accepted instance variable (i.e. variables outside any method)
    // This variable may *only* be used in methods init() and updateWorld()
    Actor[][] world;              // The world is a square matrix of Actors
    final Random rand = new Random();
    // %-distribution of RED, BLUE and NONE
    double[] dist = {0.25, 0.25, 0.50};

    // This is the method called by the timer to update the world
    // (i.e move unsatisfied) approx each 1/60 sec.
    void updateWorld() {
        long start = System.currentTimeMillis();
        // % of surrounding neighbours that are like me
        double threshold = 0.7;
        int[][] indexOfNulls = checkWorld(world, threshold);
        shuffle(indexOfNulls);
        swap(indexOfNulls, world);
        long end = System.currentTimeMillis();
    }

    // This method initializes the world variable with a random distribution of Actors
    // Method automatically called by JavaFX runtime
    // That's why we must have "@Override" and "public" (just accept for now)
    @Override
    public void init() {
        // test();    // <---------------- Uncomment to TEST!
        // Number of locations (places) in world (must be a square)
        int nLocations = 90_000;   // Should also try 90 000
        Actor[] worldArr = initialize(nLocations, dist);
        shuffle(worldArr);
        world = toMatrix(worldArr);
        // Should be last
        fixScreenSize(nLocations);
    }

    // ---------------  Methods ------------------------------

    void swap(int[][] nullLocations, Actor[][] matr){
        int i = 0;
        for (int row=0; row < matr.length; row++){
            for (int col=0; col < matr[row].length; col++){
                if(world[row][col] != null && matr[row][col].isSatisfied == false){
                    int y = nullLocations[i][0];
                    int x = nullLocations[i][1];
                    Actor tmp = matr[y][x];
                    matr[y][x] = matr[row][col];
                    matr[row][col] = tmp;
                    matr[y][x].isSatisfied = true;
                    i++;
                }
            }
        }   
    }

    void isSatisfied(Actor[][] matr, int row, int col, double threshold){
        int okay = 0;
        int total = 0;
        if (matr[row][col] == null){
            return;
        }
        for (int i=row-1; row-1 <= i && i <= row+1; i++){
            for (int k=col-1; col-1 <= k && k <= col+1; k++){
                if (isValidLocation(matr.length, i, k) && matr[i][k] != null && !(i==row && k==col)){
                    if(matr[i][k].color == matr[row][col].color){
                        okay++;
                    }
                    total++;
                }
            }
        }
       if (total == 0 || ((double)okay)/total >= threshold){
            matr[row][col].isSatisfied = true;
        } else {
            matr[row][col].isSatisfied = false;
        }
    }
    
    int[][] checkWorld(Actor[][] matr, double threshold){
        int amountOfNulls = (int)((double)(matr.length * matr.length)*dist[2]);
        int[][] arr = new int[amountOfNulls][2];
        int i = 0;
        for (int row=0; row < matr.length; row++){
            for (int col=0; col < matr[row].length; col++){
                if (matr[row][col] == null){
                    int[] tmparr = {row, col};
                    arr[i] = tmparr;
                    i++;
                } else {
                    isSatisfied(matr, row, col, threshold);
                }
            }
        }
        return arr;
    }

    Actor[] initialize(int size, double[] dist){
        Actor[] arr = new Actor[size];
        for (int i=0; i < (int)(size*dist[0]); i++){
            arr[i] = new Actor(Color.RED);
        }
        for (int i=(int)round(size*dist[1]); i < (int)((dist[0]+dist[1])*size); i++){
            arr[i] = new Actor(Color.BLUE);
        }
        return arr;
    }
    
    Actor[][] toMatrix(Actor[] arr){
        int size = (int)sqrt(arr.length);
        Actor[][] matr = new Actor[size][size];
        int row = 0;
        for (int i=0; i < arr.length; i++){
            matr[row][i % size] = arr[i];
            if (i % size == size - 1){
                row++;
            }
        }
        return matr;
    }

    // Check if inside world
    boolean isValidLocation(int size, int row, int col) {
        return 0 <= row && row < size && 0 <= col && col < size;
    }

    // ----------- Utility methods -----------------

    // TODO (general method possible reusable elsewhere)
    <T> void shuffle(T[] arr){
        for (int i = arr.length; i > 1; i--){
            int j = rand.nextInt(i);
            T tmp = arr[j];
            arr[j] = arr[i-1];
            arr[i - 1] = tmp;
        }
    }

    // ------- Testing -------------------------------------

    // Here you run your tests i.e. call your logic methods
    // to see that they really work. Important!!!!
    void test() {
        // A small hard coded world for testing
        Actor[][] testWorld = new Actor[][]{
                {new Actor(Color.RED), new Actor(Color.RED), null},
                {null, new Actor(Color.BLUE), null},
                {new Actor(Color.RED), null, new Actor(Color.BLUE)}
        };
        double th = 0.5;   // Simple threshold used for testing

        int size = testWorld.length;
        out.println(isValidLocation(size, 0, 0));
        out.println(!isValidLocation(size, -1, 0));
        out.println(!isValidLocation(size, 0, 3));
        
        isSatisfied(testWorld, 0, 0, 0.5);
        out.println(testWorld[0][0].isSatisfied == true);
        isSatisfied(testWorld, 2, 2, 0.5);
        out.println(testWorld[2][2].isSatisfied == true);

        exit(0);
    }

    // ******************** NOTHING to do below this row, it's JavaFX stuff  **************

    double width = 500;   // Size for window
    double height = 500;
    final double margin = 50;
    double dotSize;

    void fixScreenSize(int nLocations) {
        // Adjust screen window
        dotSize = 9000 / nLocations;
        if (dotSize < 1) {
            dotSize = 2;
        }
        width = sqrt(nLocations) * dotSize + 2 * margin;
        height = width;
    }

    long lastUpdateTime;
    final long INTERVAL = 450_000_000;


    @Override
    public void start(Stage primaryStage) throws Exception {

        // Build a scene graph
        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        root.getChildren().addAll(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Create a timer
        AnimationTimer timer = new AnimationTimer() {
            // This method called by FX, parameter is the current time
            public void handle(long now) {
                long elapsedNanos = now - lastUpdateTime;
                if (elapsedNanos > INTERVAL) {
                    updateWorld();
                    renderWorld(gc);
                    lastUpdateTime = now;
                }
            }
        };

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation");
        primaryStage.show();

        timer.start();  // Start simulation
    }


    // Render the state of the world to the screen
    public void renderWorld(GraphicsContext g) {
        g.clearRect(0, 0, width, height);
        int size = world.length;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int x = (int) (dotSize * col + margin);
                int y = (int) (dotSize * row + margin);
                if (world[row][col] != null) {
                    g.setFill(world[row][col].color);
                    g.fillOval(x, y, dotSize, dotSize);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
