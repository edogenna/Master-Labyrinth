package Labyrinth;

import java.util.Random;

import static Labyrinth.Constants.*;

public class Maze {
    private final int dim;
    private Piece[][] tessere;

    //TRUE => CLOSED
    public Maze(int dim) {
        this.dim = dim;
        tessere = new Piece[dim][dim];

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                tessere[i][j] = new Piece();
            }
        }

        adjustMaze();
    }

    private void adjustMaze(){
        tessere[0][0].deleteTresure();
        tessere[0][0] = generateInitPosPiece();
        tessere[dim-1][dim-1] = generateFinalPosPiece();
    }

    public char[][] getMatrixPrint(){
        char [][] m = new char[ROWS_IN_A_PIECE*dim][COLUMNS_IN_A_PIECE*dim];
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                copyMatrix(m,i*ROWS_IN_A_PIECE,j*COLUMNS_IN_A_PIECE,tessere[i][j].getMatrixPrint());
            }
        }
        return m;
    }
    public char[][] getMatricPrintWithElements(){
        char [][] m = new char[ROWS_IN_A_PIECE*dim][COLUMNS_IN_A_PIECE*dim];
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                copyMatrix(m,i*ROWS_IN_A_PIECE,j*COLUMNS_IN_A_PIECE,tessere[i][j].getMatrixPrintWithElementes());
            }
        }
        return m;
    }

    public void printBooleanValues(){
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                System.out.println("i: " + i + " j: " + j + " " +
                        " n: " + tessere[i][j].isNord() + " e: " + tessere[i][j].isEst() +
                        " s: " + tessere[i][j].isSud() + " w: " + tessere[i][j].isWest());
            }
        }
    }

    public Piece insertPiece(int x, int y, Piece tesseraDaInserire){
        Piece uscente = tessere[x][y];
        tessere[x][y] = tesseraDaInserire;
        return uscente;
    }

    public Piece getPiece(int x, int y){ return tessere[x][y]; }
    public int getDim() { return dim; }
    private Piece generateInitPosPiece(){
        Random rand = new Random();

        boolean n = rand.nextBoolean();
        boolean e = rand.nextBoolean();
        boolean s = rand.nextBoolean();
        boolean w = rand.nextBoolean();

        if(s && e){
            boolean sNew = rand.nextBoolean();
            s = sNew;
            e = !sNew;
        }

        return new Piece(n,e,s,w);
    }
    private Piece generateFinalPosPiece(){
        Random rand = new Random();

        boolean n = rand.nextBoolean();
        boolean e = rand.nextBoolean();
        boolean s = rand.nextBoolean();
        boolean w = rand.nextBoolean();

        if(n && w){
            boolean nNew = rand.nextBoolean();
            n = nNew;
            w = !nNew;
        }

        return new Piece(n,e,s,w);
    }
}
