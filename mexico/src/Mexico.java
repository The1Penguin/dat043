
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.*;

/*
 *  The Mexico dice game
 *  See https://en.wikipedia.org/wiki/Mexico_(game)
 *
 */
public class Mexico {

    public static void main(String[] args) {
        new Mexico().program();
    }

    final Random rand = new Random();
    final Scanner sc = new Scanner(in);
    final int maxRolls = 3;      // No player may exceed this
    final int startAmount = 3;   // Money for a player. Select any
    final int mexico = 1000;     // A value greater than any other

    void program() {
        //test();            // <----------------- UNCOMMENT to test

        int pot = 0;         // What the winner will get
        Player[] players;    // The players (array of Player objects)
        Player current;      // Current player for round
        Player leader;       // Player starting the round
        int played = 0;

        players = getPlayers();
        current = getRandomPlayer(players);
        leader = current;

        out.println("Mexico Game Started");
        statusMsg(players);

       while (players.length > 1) {   // Game over when only one player left
            // ----- In ----------
            String cmd = getPlayerChoice(current);
            if ("r".equals(cmd)) {

                    // --- Process ------
                    if (current.nRolls < maxRolls && (current == leader || current.nRolls < leader.nRolls)){
                        rollDice(current);
                        roundMsg(current);
                    }
                    if (current.nRolls >= maxRolls || (current != leader && current.nRolls >= leader.nRolls)){
                        current = next(players, current);
                        played++;
                    }

            } else if ("n".equals(cmd)) {
                current = next(players, current);
                played++;
                 // Process
            } else {
                out.println("?");
            }

            if (played == players.length) {
                // --- Process -----
                played = 0;
                Player tmpLoser = getLoser(players);
                players = allRolled(players);
                pot++;
                // ----- Out --------------------
                out.println("Round done " + tmpLoser.name + " lost!");
                out.println("Next to roll is " + current.name);
            }
        }
        out.println("Game Over, winner is " + players[0].name + ". Will get " + pot + " from pot");
    }


    // ---- Game logic methods --------------

    // TODO implement and test methods (one at the time)


    int indexOf(Player[] players, Player player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == player) {
                return i;
            }
        }
        return -1;
    }

    Player getRandomPlayer(Player[] players) {
        return players[rand.nextInt(players.length)];
    }
    
    int getScore(Player player) {
        int fst = player.fstDice;
        int snd = player.secDice;
        if ((fst==2 && snd == 1) || (fst==1 && snd==2)){
            return mexico;
        } else if (fst==snd){
            return ((fst*10 + snd)*10);
        } else if (fst*10 + snd > snd*10 + fst){
            return (fst*10 + snd);
        }
        return (snd*10+fst);
    }

    Player next(Player[] players, Player current){
        int index = indexOf(players, current);
        int nextInt = (index + 1) % (players.length);
        return players[nextInt];
    }

    Player getLoser(Player[] players){
        Player lowest = players[0];
        for (int i=0; i<players.length; i++){
            if (getScore(lowest) > getScore(players[i])){
                lowest = players[i];
            }
        }
        return lowest;
    }

    Player[] removeLoser(Player[] players, Player toRemove){
        Player[] arr1 = new Player[players.length - 1];
        for (int i=0, j=0; i<players.length; i++){
            if (!(players[i] == toRemove)){
                arr1[j++] = players[i];
            }
        }
        return arr1;
    }

    Player[] clearRoundResults(Player[] players){
        for (int i=0; i < players.length; i++){
            players[i].nRolls = 0;
            players[i].fstDice = 0;
            players[i].secDice = 0;
        }
        return players;
    }

    Player[] allRolled(Player[] players){
        Player loser = getLoser(players);
        loser.amount--;
        out.println("" + loser.name + " " + loser.amount);
        if (loser.amount == 0){
            players = removeLoser(players, loser);
        } 
        players = clearRoundResults(players);
        statusMsg(players);
        return players;
    }
    
    Player rollDice(Player current){
        current.nRolls++;
        current.fstDice = rand.nextInt(6) + 1;
        current.secDice = rand.nextInt(6) + 1;
        return current;
    }

    // ---------- IO methods (nothing to do here) -----------------------

    Player[] getPlayers() {
        // Ugly for now. If using a constructor this may
        // be cleaned up.

        Player[] players = {
        new Player("Olle"),
        new Player("Fia"),
        new Player("Lisa")};
        return players;
    }

    void statusMsg(Player[] players) {
        out.print("Status: ");
        for (int i = 0; i < players.length; i++) {
            out.print(players[i].name + " " + players[i].amount + " ");
        }
        out.println();
    }

    void roundMsg(Player current) {
        out.println(current.name + " got " +
                current.fstDice + " and " + current.secDice);
    }

    String getPlayerChoice(Player player) {
        out.print("Player is " + player.name + " > ");
        return sc.nextLine();
    }

    // Possibly useful utility during development
    String toString(Player p){
        return p.name + ", " + p.amount + ", " + p.fstDice + ", "
                + p.secDice + ", " + p.nRolls;
    }

    // Class for a player
    class Player {
        String name;
        int amount;   // Start amount (money)
        int fstDice;  // Result of first dice
        int secDice;  // Result of second dice
        int nRolls;   // Current number of rolls

        public Player (String nameIn){
            name = nameIn;
            amount = startAmount;
        }
    }

    /**************************************************
     *  Testing
     *
     *  Test are logical expressions that should
     *  evaluate to true (and then be written out)
     *  No testing of IO methods
     *  Uncomment in program() to run test (only)
     ***************************************************/
    void test() {
        // A few hard coded player to use for test
        // NOTE: Possible to debug tests from here, very efficient!
        Player[] ps = {new Player("Oskar"), new Player("Kim"), new Player("Johan")};
        ps[0].fstDice = 2;
        ps[0].secDice = 6;
        ps[1].fstDice = 6;
        ps[1].secDice = 5;
        ps[2].fstDice = 1;
        ps[2].secDice = 1;


        out.println(getScore(ps[0]) == 62);
        out.println(getScore(ps[1]) == 65);
        out.println(next(ps, ps[0]) == ps[1]);
        out.println(getLoser(ps) == ps[0]);

        exit(0);
    }


}
