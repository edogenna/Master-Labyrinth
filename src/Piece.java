package Labyrinth.src;
import java.util.Random;
import static Labyrinth.src.Constants.*;

//@SuppressWarnings("unused")
public class Piece {
    private boolean nord;
    private boolean est;
    private boolean sud;
    private boolean west;
    private Tresure tresure;
    private CardinalPoint trap;
    private boolean trapRevealed;
    private static final char BIG_TRESURE = '@';
    private static final char SMALL_TRESURE = '*';
    private static final char CHAR_TRAP = 'X';
    private static final int ODDS_TRAP = 1;
    private static final int ODDS_BIG_TRESURE = 1;
    private static final int ODDS_SMALL_TRESURE = 3;
    private static final int POINTS_BIG_TRESURE = 1000;
    private static final int POINTS_SMALL_TRESURE = 100;
    public static final int ROWS_IN_A_PIECE = 4;
    public static final int COLUMNS_IN_A_PIECE = 10;
    private static final String HORIZONTAL_NORD_SUD_TRUE = " +------+ ";
    private static final String HORIZONTAL_NORD_SUD_FALSE = " +      + ";
    private static final String HORIZONTAL_WEST_FALSE_EST_TRUE = "        | ";
    private static final String HORIZONTAL_WEST_FALSE_EST_FALSE = "          ";

    public Piece(boolean nord, boolean est, boolean sud, boolean west, CardinalPoint cardinalTrap, Tresure tresure) {
        this.nord = nord;
        this.est = est;
        this.sud = sud;
        this.west = west;
        this.tresure = tresure;
        this.trap = cardinalTrap;
        this.trapRevealed = false;
    }

    public Piece(boolean nord, boolean est, boolean sud, boolean west) {
        this(nord,est,sud,west,generateTrap(nord, est, sud, west), generateTresure());
    }

    public Piece(boolean nord, boolean est, boolean sud, boolean west, Tresure tresure){
        this(nord,est,sud,west,generateTrap(nord, est, sud, west), tresure);
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

        this.trap = generateTrap(nord, est, sud, west);
        this.trapRevealed = false;
    }


    private static CardinalPoint generateTrap(boolean n, boolean e,boolean s,boolean w){
        Random rand = new Random();
        int noTrap = rand.nextInt(10);
        if(noTrap >= ODDS_TRAP)
            return CardinalPoint.NONE;

        int count = 0;
        if(!n) count++;
        if(!e) count++;
        if(!s) count++;
        if(!w) count++;

        int num = rand.nextInt(count);
        CardinalPoint[] ar = new CardinalPoint[count];
        count--;
        if(!n){
            ar[count] = CardinalPoint.NORD;
            count--;
        }
        if(!e){
            ar[count] = CardinalPoint.EST;
            count--;
        }
        if(!s){
            ar[count] = CardinalPoint.SUD;
            count--;
        }
        if(!w){
            ar[count] = CardinalPoint.WEST;
        }

        return ar[num];
    }

    @SuppressWarnings("unused")
    public void print(){
        if(this.nord)
            System.out.print(HORIZONTAL_NORD_SUD_TRUE);
        else
            System.out.print(HORIZONTAL_NORD_SUD_FALSE);

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
            System.out.print(HORIZONTAL_NORD_SUD_TRUE);
        else
            System.out.print(HORIZONTAL_NORD_SUD_FALSE);
        System.out.print("\n");

    }

    public void rotateClockwise(){
        boolean tmp;
        tmp = this.west;
        this.west = this.sud;
        this.sud = this.est;
        this.est = this.nord;
        this.nord = tmp;

        this.trap = rotateClockwise(this.trap);
    }
    private CardinalPoint rotateClockwise(CardinalPoint cp){
        return switch (cp){
            case NONE -> CardinalPoint.NONE;
            case NORD -> CardinalPoint.WEST;
            case EST -> CardinalPoint.NORD;
            case SUD -> CardinalPoint.EST;
            case WEST -> CardinalPoint.SUD;
        };
    }
    public void rotateCounterClockwise(){
        boolean tmp;
        tmp = this.est;
        this.est = this.sud;
        this.sud = this.west;
        this.west = this.nord;
        this.nord = tmp;

        this.trap = rotateCounterClockwise(this.trap);
    }
    private CardinalPoint rotateCounterClockwise(CardinalPoint cp){
        return switch (cp){
            case NONE -> CardinalPoint.NONE;
            case NORD -> CardinalPoint.EST;
            case EST -> CardinalPoint.SUD;
            case SUD -> CardinalPoint.WEST;
            case WEST -> CardinalPoint.NORD;
        };
    }

    @SuppressWarnings("unused")
    public void rotate180(){
        boolean tmp;
        tmp = this.west;
        this.west = this.est;
        this.est = tmp;

        tmp = this.sud;
        this.sud = this.nord;
        this.nord = tmp;
    }

    private static Tresure generateTresure(){
        Random rand = new Random();

        int n = rand.nextInt(10);
        if(n < ODDS_BIG_TRESURE)
            return Tresure.BIG;
        if(n <= ODDS_SMALL_TRESURE)
            return Tresure.SMALL;
        return Tresure.NONE;
    }

    public boolean isNord() { return nord; }
    public boolean isEst() { return est; }
    public boolean isSud() { return sud; }
    public boolean isWest() { return west; }
    public CardinalPoint getTrap() { return trap;}

    public char[][] getMatrixPrint(){
        char[][] m = new char[ROWS_IN_A_PIECE][];
        if(nord)
            m[0] = HORIZONTAL_NORD_SUD_TRUE.toCharArray();
        else
            m[0] = HORIZONTAL_NORD_SUD_FALSE.toCharArray();

        for(int i = 1; i < ROWS_IN_A_PIECE-1; i++){
            String HORIZONTAL_WEST_TRUE_EST_TRUE = " |      | ";
            String HORIZONTAL_WEST_TRUE_EST_FALSE = " |        ";
            if(est && west)
                m[i] = HORIZONTAL_WEST_TRUE_EST_TRUE.toCharArray();
            else if(est)
                m[i] = HORIZONTAL_WEST_FALSE_EST_TRUE.toCharArray();
            else if(west)
                m[i] = HORIZONTAL_WEST_TRUE_EST_FALSE.toCharArray();
            else
                m[i] = HORIZONTAL_WEST_FALSE_EST_FALSE.toCharArray();
        }

        if(sud)
            m[ROWS_IN_A_PIECE-1] = HORIZONTAL_NORD_SUD_TRUE.toCharArray();
        else
            m[ROWS_IN_A_PIECE-1] = HORIZONTAL_NORD_SUD_FALSE.toCharArray();

        return m;
    }
    public char[][] getMatrixPrintWithElementes(){
        char [][]m = getMatrixPrint();
        switch (tresure){
            case BIG -> setCentreOfPiece(m, BIG_TRESURE);
            case SMALL -> setCentreOfPiece(m, SMALL_TRESURE);
        }

        if(trapRevealed){
            switch (trap){
                case NORD -> {
                    m[0][4] = CHAR_TRAP;
                    m[0][5] = CHAR_TRAP;
                }
                case EST -> {
                    m[1][8] = CHAR_TRAP;
                    m[2][8] = CHAR_TRAP;
                }
                case SUD -> {
                    m[3][4] = CHAR_TRAP;
                    m[3][5] = CHAR_TRAP;
                }
                case WEST -> {
                    m[1][1] = CHAR_TRAP;
                    m[2][1] = CHAR_TRAP;
                }
            }
        }

        return m;
    }

    private void setCentreOfPiece(char[][] m, char c){
        m[1][4] = c;
        m[1][5] = c;
        m[2][4] = c;
        m[2][5] = c;
    }
    public int withdrawDeleteTreasure() {
        int p = 0;

        if(tresure == Tresure.BIG)
            p = POINTS_BIG_TRESURE;
        else if(tresure == Tresure.SMALL)
            p = POINTS_SMALL_TRESURE;
        else
            System.out.println("Questa tessera ha un tesoro non compatibile!");

        tresure = Tresure.NONE;
        return p;
    }
    public void revealTrap(){
        if(this.trap == CardinalPoint.NONE)
            return;
        this.trapRevealed = true;
    }
    public int getCardinalPoints(){
        int n = 0;
        if(nord) n+= 8;
        if(est) n+= 4;
        if(sud) n+= 2;
        if(west) n+= 1;

        return n;
    }
}