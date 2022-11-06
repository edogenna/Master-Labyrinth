package Labyrinth.src;

import java.util.Random;

import static Labyrinth.src.Constants.*;

public class Maze {
    private final int numberPiecesInASide;
    private Piece[][] tessere;
    private final Dimension dimOfPieces;

    private static final int DEFAULT_MAX_NUMBER_OF_PIECES_FOR_BIG = 4;

    //TRUE => CLOSED
    public Maze(int dim, Dimension dimOfPieces){
        this.numberPiecesInASide = dim;
        this.dimOfPieces = dimOfPieces;

        tessere = new Piece[dim][dim];

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                tessere[i][j] = new Piece(dimOfPieces);
            }
        }

        tessere[0][0] = generateInitPosPiece(dimOfPieces);
        tessere[dim-1][dim-1] = generateFinalPosPiece(dimOfPieces);
    }

    public Maze(int dim){
        this(dim, dim > DEFAULT_MAX_NUMBER_OF_PIECES_FOR_BIG ? Dimension.SMALL : Dimension.LARGE);
    }

    public Maze(){
        this(DEFAULT_NUMBER_OF_PIECES_IN_ROW);
    }


    public char[][] getMatrixPrint(boolean withElements){
        int rowInAPiece = dimOfPieces == Dimension.LARGE ? Piece.ROWS_IN_A_PIECE_LARGE : Piece.ROWS_IN_A_PIECE_SMALL;
        int colInAPiece = dimOfPieces == Dimension.LARGE ? Piece.COLUMNS_IN_A_PIECE_LARGE : Piece.COLUMNS_IN_A_PIECE_SMALL;

        char [][] m = new char[rowInAPiece * numberPiecesInASide][colInAPiece * numberPiecesInASide];
        for(int i = 0; i < numberPiecesInASide; i++){
            for(int j = 0; j < numberPiecesInASide; j++){
                copyMatrix(m,i*rowInAPiece,j*colInAPiece,tessere[i][j].getMatrixPrint(withElements));
            }
        }

        return m;
    }



    @SuppressWarnings("unused")
    public void printBooleanValues(){
        for(int i = 0; i < numberPiecesInASide; i++){
            for(int j = 0; j < numberPiecesInASide; j++){
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
    public int getNumberOfPiecesInASide() { return numberPiecesInASide; }
    public Dimension getDimOfPieces() { return dimOfPieces; }
    private Piece generateInitPosPiece(Dimension dimOfPieces){
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

        return new Piece(n,e,s,w,Tresure.NONE,dimOfPieces);
    }
    private Piece generateFinalPosPiece(Dimension dimOfPieces){
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

        return new Piece(n,e,s,w,dimOfPieces);
    }
}
