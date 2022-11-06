package Labyrinth.src;

import java.util.Arrays;

public final class Constants {
    private Constants(){ }

    public enum CardinalPoint { NONE, NORD, EST, SUD, WEST }
    public enum Tresure{ NONE, SMALL, BIG }
    public enum Dimension { SMALL, LARGE }

    public static final int DEFAULT_NUMBER_OF_PIECES_IN_ROW = 5;


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


    public static void copyMatrix(char [][]dest, int rowInitPos, int colInitPos, char [][]src){
        for(int i = 0; i < src.length; i++){
            System.arraycopy(src[i], 0, dest[rowInitPos + i], colInitPos, src[i].length);
        }
    }

    public static void copyArrayInMatrix(char [][]dest, int xInitPos, int yInitPos, char []src){
        System.arraycopy(src, 0, dest[xInitPos], yInitPos, src.length);
    }

    public static void setMatrixToChar(char [][]m, char c){
        for (char[] chars : m) {
            Arrays.fill(chars, c);
        }
    }

    public static char[][] addIndexToMatrix(char [][]m, int modRow, int modCol, int offsetRow, int offsetCol){
        char [][]newM = new char[m.length + 1][m[0].length + 2];

        copyMatrix(newM,1,0,m);

        for(int i = 0; i < m[0].length + 2; i++){
            newM[0][i] = ' ';
        }

        for(int i = 0; i < m.length + 1; i++){
            newM[i][m[0].length] = ' ';
            newM[i][m[0].length + 1] = ' ';
        }

        for(int i = 0; i < m[0].length; i++){
            if(i % modCol == 0)
                newM[0][i+offsetCol] = Character.forDigit(i/modCol,10);
        }

        for(int i = 0; i < m.length; i++){
            if(i % modRow == 0)
                newM[i+offsetRow+1][m[0].length+1] = Character.forDigit(i/modRow,10);
        }

        return newM;
    }

}
