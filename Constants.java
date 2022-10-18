package Labyrinth;

public class Constants {
    private Constants(){ }

    public enum CardinalPoint {
        NONE, NORD, EST, SUD, WEST
    }
    public final static char CHAR_NORD = 'w';
    public final static char CHAR_SUD = 's';
    public final static char CHAR_EST = 'd';
    public final static char CHAR_WEST = 'a';
    public final static char CHAR_ROTATE_CLOCKWISE = 'm';
    public final static char CHAR_ROTATE_COUNTER_CLOCKWISE = 'n';
    public final static char CHAR_INSERT = 'i';
    public final static char CHAR_PRINT_STATUS = 'p';
    public final static char CHAR_HELP = 'h';
    public static final String ORIZZONTAL_NORD_SUD_TRUE = " +------+ ";
    public static final String ORIZZONTAL_NORD_SUD_FALSE = " +      + ";
    public static final String ORIZZONTAL_SPACES = "      ";
    public static final String ORIZZONTAL_TRAP = " +  XX  + ";
    public static final String ORIZZONTAL_WEST_TRUE_EST_TRUE = " |      | ";
    public static final String ORIZZONTAL_WEST_TRUE_EST_FALSE = " |        ";
    public static final String ORIZZONTAL_WEST_FALSE_EST_TRUE = "        | ";
    public static final String ORIZZONTAL_WEST_FALSE_EST_FALSE = "          ";

    public static final char NONE_TRESURE = ' ';
    public static final char BIG_TRESURE = '@';
    public static final char SMALL_TRESURE = '*';
    public static final int ODDS_BIG_TRESURE = 1;
    public static final int ODDS_SMALL_TRESURE = 3;
    public static final int ODDS_TRAP = 1;
    public static final int POINTS_BIG_TRESURE = 1000;
    public static final int POINTS_SMALL_TRESURE = 100;
    public static final String HEAD = "  ()  ";
    public static final String BODY = " -||- ";
    public static final int DEFAULT_MAZE_DIM = 5;
    public static final int ROWS_IN_A_PIECE = 4;
    public static final int COLUMNS_IN_A_PIECE = 10;

    public static void printMatrix(char [][] m){
        for (char[] chars : m) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.print("\n");
        }
    }
}
