package Labyrinth.src;

public final class Constants {
    private Constants(){ }

    public enum CardinalPoint {
        NONE, NORD, EST, SUD, WEST
    }

    public enum Tresure{
        NONE, SMALL, BIG
    }
    public static int DEFAULT_MAZE_DIM = 5;

    public static void printMatrix(char [][] m){
        for (char[] chars : m) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.print("\n");
        }
    }

    public static void printMatrixWithIndex(char [][] m, int mod){
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                System.out.print(m[i][j]);
            }
            if(i%mod == 0)
                System.out.print(" " + i/mod);
            System.out.print("\n");
        }
    }


    public static void copyMatrix(char [][]dest, int xInitPos, int yInitPos, char [][]src){
        for(int i = 0; i < src.length; i++){
            System.arraycopy(src[i], 0, dest[xInitPos + i], yInitPos, src[i].length);
        }
    }
}
