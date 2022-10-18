package Labyrinth;
import java.util.Random;
import static Labyrinth.Constants.*;

public class Piece {
    private boolean nord;
    private boolean est;
    private boolean sud;
    private boolean west;
    private char tresure;
    private CardinalPoint trap;
    private boolean trapRevealed;

    public Piece(boolean nord, boolean est, boolean sud, boolean west) {
        this.nord = nord;
        this.est = est;
        this.sud = sud;
        this.west = west;
        this.tresure = generateTresure();
        this.trap = generateTrap(nord, est, sud, west);
        this.trapRevealed = false;
    }

    public Piece(){
        Random rand = new Random();

        this.nord = rand.nextBoolean();
        this.est = rand.nextBoolean();
        this.sud = rand.nextBoolean();
        this.west = rand.nextBoolean();
        this.tresure = generateTresure();
        this.trapRevealed = false;

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


    private CardinalPoint generateTrap(boolean n, boolean e,boolean s,boolean w){
        Random rand = new Random();
        int noTrap = rand.nextInt(10);
        if(noTrap < ODDS_TRAP)
            return CardinalPoint.NONE;

        int conta = 0;
        if(!n) conta++;
        if(!e) conta++;
        if(!s) conta++;
        if(!w) conta++;

        int num = rand.nextInt(conta);
        CardinalPoint[] ar = new CardinalPoint[conta];
        conta--;
        if(!n){
            ar[conta] = CardinalPoint.NORD;
            conta--;
        }
        if(!e){
            ar[conta] = CardinalPoint.EST;
            conta--;
        }
        if(!s){
            ar[conta] = CardinalPoint.SUD;
            conta--;
        }
        if(!w){
            ar[conta] = CardinalPoint.WEST;
            conta--;
        }

        return ar[num];
    }

    public void print(){
        if(this.nord)
            System.out.print(ORIZZONTAL_NORD_SUD_TRUE);
        else
            System.out.print(ORIZZONTAL_NORD_SUD_FALSE);

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
            System.out.print(ORIZZONTAL_NORD_SUD_TRUE);
        else
            System.out.print(ORIZZONTAL_NORD_SUD_FALSE);
        System.out.print("\n");

    }

    public void rotateClockwise(){
        boolean tmp;
        tmp = this.west;
        this.west = this.sud;
        this.sud = this.est;
        this.est = this.nord;
        this.nord = tmp;
    }
    public void rotateCounterClockwise(){
        boolean tmp;
        tmp = this.est;
        this.est = this.sud;
        this.sud = this.west;
        this.west = this.nord;
        this.nord = tmp;
    }
    public void rotate180(){
        boolean tmp;
        tmp = this.west;
        this.west = this.est;
        this.est = tmp;

        tmp = this.sud;
        this.sud = this.nord;
        this.nord = tmp;
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
    public CardinalPoint getTrap() { return trap;}
    public boolean getTrapRevealed() { return trapRevealed; }

    public char[][] getMatrixPrint(){
        char[][] m = new char[ROWS_IN_A_PIECE][];
        if(nord)
            m[0] = ORIZZONTAL_NORD_SUD_TRUE.toCharArray();
        else
            m[0] = ORIZZONTAL_NORD_SUD_TRUE.toCharArray();

        for(int i = 1; i < ROWS_IN_A_PIECE-1; i++){
            if(est && west)
                m[i] = ORIZZONTAL_WEST_TRUE_EST_TRUE.toCharArray();
            else if(est)
                m[i] = ORIZZONTAL_WEST_FALSE_EST_TRUE.toCharArray();
            else if(west)
                m[i] = ORIZZONTAL_WEST_TRUE_EST_FALSE.toCharArray();
            else
                m[i] = ORIZZONTAL_WEST_FALSE_EST_FALSE.toCharArray();
        }

        if(sud)
            m[ROWS_IN_A_PIECE-1] = ORIZZONTAL_NORD_SUD_TRUE.toCharArray();
        else
            m[ROWS_IN_A_PIECE-1] = ORIZZONTAL_NORD_SUD_TRUE.toCharArray();

        return m;
    }

    public int withdrawDeleteTresure() {
        int p = 0;

        if(tresure == NONE_TRESURE) {
            //System.out.println("Non c'è nessun tesoro su questa tessera!");
        }else if(tresure == BIG_TRESURE)
            p = POINTS_BIG_TRESURE;
        else if(tresure == SMALL_TRESURE)
            p = POINTS_SMALL_TRESURE;
        else
            System.out.println("Questa tessera ha un tesoro non compatibile!");

        tresure = NONE_TRESURE;
        return p;
    }

    public void deleteTresure(){ tresure = NONE_TRESURE; }

    public void revealTrap(){ this.trapRevealed = false; }
}