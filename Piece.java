package Labyrinth;
import java.util.Random;
import static Labyrinth.Constants.*;

public class Piece {
    private boolean nord;
    private boolean est;
    private boolean sud;
    private boolean west;
    private char tresure;


    public Piece(boolean nord, boolean est, boolean sud, boolean west) {
        this.nord = nord;
        this.est = est;
        this.sud = sud;
        this.west = west;
        this.tresure = generateTresure();
    }

    public Piece(){
        Random rand = new Random();

        this.nord = rand.nextBoolean();
        this.est = rand.nextBoolean();
        this.sud = rand.nextBoolean();
        this.west = rand.nextBoolean();
        this.tresure = generateTresure();

        //elimino il problema delle tessere tutte chiuse
        if(this.nord && this.est && this.sud && this.west){
            int num = rand.nextInt(4);
            switch (num) {
                case 0 -> this.nord = false;
                case 1 -> this.est = false;
                case 2 -> this.sud = false;
                case 3 -> this.west = false;
                default -> System.out.println("Errore rigenerazione tessera tutta chiusa!");
            }
        }
    }


    public void print(){
        if(this.nord)
            System.out.print(ORIZZONTAL_FULL);
        else
            System.out.print(ORIZZONTAL_EMPTY);

        System.out.print("\n");

        for(int k = 0; k < 2; k++){
            System.out.print(" ");

            if(this.west)
                System.out.print("|");
            else
                System.out.print(" ");

            System.out.print("  " + this.tresure + this.tresure + "  ");

            if(this.est)
                System.out.print("|");
            else
                System.out.print(" ");

            System.out.print(" ");
            System.out.print("\n");
        }

        if(this.sud)
            System.out.print(ORIZZONTAL_FULL);
        else
            System.out.print(ORIZZONTAL_EMPTY);
        System.out.print("\n");

    }

    public Piece rotateClockwise(){
        boolean tmp;
        tmp = this.west;
        this.west = this.sud;
        this.sud = this.est;
        this.est = this.nord;
        this.nord = tmp;

        return this;
    }
    public Piece rotateCounterClockwise(){
        boolean tmp;
        tmp = this.est;
        this.est = this.sud;
        this.sud = this.west;
        this.west = this.nord;
        this.nord = tmp;

        return this;
    }
    public Piece rotate180(){
        boolean tmp;
        tmp = this.west;
        this.west = this.est;
        this.est = tmp;

        tmp = this.sud;
        this.sud = this.nord;
        this.nord = tmp;

        return this;
    }

    private static char generateTresure(){
        Random rand = new Random();

        int n = rand.nextInt(10);
        if(n < ODDS_BIG_TRESURE)
            return BIG_TRESURE;
        if(n <= ODDS_SMALL_TRESURE)
            return SMALL_TRESURE;
        return NONE_TRESURE;
    }

    public boolean isNord() { return nord; }
    public boolean isEst() { return est; }
    public boolean isSud() { return sud; }
    public boolean isWest() { return west; }
    public char getTresure() { return tresure; }

    public int withdrawDeleteTresure() {
        int p = 0;

        if(tresure == NONE_TRESURE)
            System.out.println("Non c'è nessun tesoro su questa tessera!");
        else if(tresure == BIG_TRESURE)
            p = POINTS_BIG_TRESURE;
        else if(tresure == SMALL_TRESURE)
            p = POINTS_SMALL_TRESURE;
        else
            System.out.println("Questa tessera ha un tesoro non compatibile!");

        tresure = NONE_TRESURE;
        return p;
    }
}