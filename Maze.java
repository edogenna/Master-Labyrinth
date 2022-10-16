package Labyrinth;
import Labyrinth.exceptions.MoveNotAllowedException;

import java.util.Random;

import static Labyrinth.Constants.*;

public class Maze {
    private int dim;
    private Piece[][] tessere;
    private int punteggio;
    private int vite;
    private int rowPos;
    private int colPos;

    //TRUE => CLOSED
    public Maze() {
        dim = DEFAULT_MAZE_DIM;
        tessere = new Piece[dim][dim];

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                tessere[i][j] = new Piece();
            }
        }

        adjustMaze();
    }
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
    public void print(){
        for(int i = 0; i < dim; i++){
            printNord(i);
            printEstAndWest(i);
            printSud(i);
        }
    }
    private void printNord(int row){
        for(int col = 0; col < dim; col++){
            if(tessere[row][col].isNord())
                System.out.print(ORIZZONTAL_FULL);
            else
                System.out.print(ORIZZONTAL_EMPTY);
        }
        System.out.print("\n");
    }
    private void printSud(int row){
        for(int col = 0; col < dim; col++){
            if(tessere[row][col].isSud())
                System.out.print(ORIZZONTAL_FULL);
            else
                System.out.print(ORIZZONTAL_EMPTY);
        }
        System.out.print("\n");
    }
    private void printEstAndWest(int row){
        for(int h = 0; h < 2; h++){
            for(int col = 0; col < dim; col++){
                System.out.print(" ");

                if(tessere[row][col].isWest())
                    System.out.print("|");
                else
                    System.out.print(" ");

                if(rowPos == row && colPos == col){
                    if(h == 0)
                        System.out.print(HEAD);
                    else if(h == 1)
                        System.out.print(BODY);
                }else
                    System.out.print("  " + tessere[row][col].getTresure() + tessere[row][col].getTresure() + "  ");

                if(tessere[row][col].isEst())
                    System.out.print("|");
                else
                    System.out.print(" ");

                System.out.print(" ");
            }
            System.out.print("\n");
        }
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

    public Piece insertPiece(int index, char cardinalPoint, Piece tesseraDaInserire){
        if(index < 0 || index >= dim || index % 2 == 0)
            throw new IllegalArgumentException(index + " is not a valid index. It must be a even number and a valid position. ");

        cardinalPoint = Character.toLowerCase(cardinalPoint);
        if(cardinalPoint != 'n' && cardinalPoint != 'e' && cardinalPoint != 's' && cardinalPoint != 'w')
            throw new IllegalArgumentException(cardinalPoint + " is not a valid cardinal point. It must be nord, est, sud or west");

        Piece tesseraUscente = null;

        switch (cardinalPoint){
            case 'e' -> {
                tesseraUscente = tessere[index][0];
                for(int i = 0; i < dim - 1; i++)
                    tessere[index][i] = tessere[index][i+1];
                tessere[index][dim-1] = tesseraDaInserire;

                if(index == rowPos) {
                    colPos--;
                    if(colPos < 0)
                        colPos = dim-1;
                }
            }
            case 'n' -> {
                tesseraUscente = tessere[dim-1][index];
                for(int i = dim - 1; i > 0; i--)
                    tessere[i][index] = tessere[i-1][index];
                tessere[0][index] = tesseraDaInserire;

                if(index == colPos) {
                    rowPos++;
                    if(rowPos >= dim)
                        rowPos = 0;
                }
            }
            case 's' -> {
                tesseraUscente = tessere[0][index];
                for(int i = 0; i < dim - 1; i++)
                    tessere[i][index] = tessere[i+1][index];
                tessere[dim-1][index] = tesseraDaInserire;

                if(index == colPos) {
                    rowPos--;
                    if(rowPos < 0)
                        rowPos = dim-1;
                }
            }
            case 'w' -> {
                tesseraUscente = tessere[index][dim-1];
                for(int i = dim - 1; i > 0; i--)
                    tessere[index][i] = tessere[index][i-1];
                tessere[index][0] = tesseraDaInserire;

                if(index == rowPos) {
                    colPos++;
                    if(colPos >= dim)
                        colPos = 0;
                }
            }
        }

        return tesseraUscente;
    }

    public Piece getTessera(int x, int y){
        return tessere[x][y];
    }

    public boolean controllaVittoria(){ return rowPos == (dim-1) && colPos == (dim-1); }
    public int getPunteggio() { return punteggio; }
    public int getVite() { return vite; }
    public int getRowPos() { return rowPos; }
    public int getColPos() { return colPos; }

    public void spostatiEst(){
        if(tessere[rowPos][colPos].isEst() || colPos >= dim-1 || tessere[rowPos][colPos+1].isWest()) {
            throw new MoveNotAllowedException("Movement not allowed");
        }
        colPos++;

        checkPremio();
    }
    public void spostatiWest(){
        if(tessere[rowPos][colPos].isWest() || colPos <= 0 || tessere[rowPos][colPos-1].isEst()){
            throw new MoveNotAllowedException("Movement not allowed");
        }
        colPos--;

        checkPremio();
    }
    public void spostatiSud(){
        if(tessere[rowPos][colPos].isSud() || rowPos >= dim-1 || tessere[rowPos+1][colPos].isNord()){
            throw new MoveNotAllowedException("Movement not allowed");
        }
        rowPos++;

        checkPremio();
    }
    public void spostatiNord(){
        if(tessere[rowPos][colPos].isNord() || rowPos <= 0 || tessere[rowPos-1][colPos].isSud()){
            throw new MoveNotAllowedException("Movement not allowed");
        }
        rowPos--;

        checkPremio();
    }

    private void checkPremio(){ punteggio += tessere[rowPos][colPos].withdrawDeleteTresure(); }

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
