package Labyrinth.src;

import Labyrinth.src.exceptions.MoveNotAllowedException;
import Labyrinth.src.exceptions.NoMoreLifesException;

import java.util.Scanner;

import static Labyrinth.src.Constants.*;

public class Match {
    private int punteggio;
    private int vite;
    private int rowPos;
    private int colPos;
    private Maze maze;
    private Piece externalPiece;
    private final Scanner in;
    private int moveCounter;

    private final static char CHAR_NORD = 'w';
    private final static char CHAR_SUD = 's';
    private final static char CHAR_EST = 'd';
    private final static char CHAR_WEST = 'a';
    private final static char CHAR_ROTATE_CLOCKWISE = 'm';
    private final static char CHAR_ROTATE_COUNTER_CLOCKWISE = 'n';
    private final static char CHAR_INSERT = 'i';
    private final static char CHAR_PRINT_STATUS = 'p';
    private final static char CHAR_HELP = 'h';

    public Match(Scanner in){
        this(-1,in);
        this.setVite();
    }

    public Match(int vite, Scanner in){
        this.vite = vite;
        this.punteggio = 0;
        this.rowPos = 0;
        this.colPos = 0;
        this.maze = new Maze(DEFAULT_MAZE_DIM);
        this.externalPiece = new Piece();
        this.in = in;
        this.moveCounter = 0;
    }

    private void setVite(){
        System.out.println("Inserisci il numero di vite");
        this.vite = in.nextInt();
    }

    private void checkTreasure(){ punteggio += maze.getPiece(rowPos,colPos).withdrawDeleteTreasure(); }
    public boolean checkVictory(){ return rowPos == (maze.getDim()-1) && colPos == (maze.getDim()-1); }
    
    private void caughtTrap() {
        System.out.println("Oh no, sei passato su una trappola!");
        vite--;
        if(vite <= 0)
            throw new NoMoreLifesException("Hai finito le vite!");
    }
    public void moveEst(){
        if(maze.getPiece(rowPos,colPos).isEst() || colPos >= maze.getDim()-1 || maze.getPiece(rowPos,colPos+1).isWest()) {
            throw new MoveNotAllowedException("Movement not allowed");
        }

        if(maze.getPiece(rowPos,colPos).getTrap() == CardinalPoint.EST || maze.getPiece(rowPos,colPos+1).getTrap() == CardinalPoint.WEST){
            maze.getPiece(rowPos,colPos).revealTrap();
            maze.getPiece(rowPos,colPos+1).revealTrap();
            caughtTrap();
        }

        colPos++;

        checkTreasure();
    }
    public void moveWest(){
        if(maze.getPiece(rowPos,colPos).isWest() || colPos <= 0 || maze.getPiece(rowPos,colPos-1).isEst()){
            throw new MoveNotAllowedException("Movement not allowed");
        }

        if(maze.getPiece(rowPos,colPos).getTrap() == CardinalPoint.WEST || maze.getPiece(rowPos,colPos-1).getTrap() == CardinalPoint.EST){
            maze.getPiece(rowPos,colPos).revealTrap();
            maze.getPiece(rowPos,colPos-1).revealTrap();
            caughtTrap();
        }

        colPos--;

        checkTreasure();
    }
    public void moveSud(){
        if(maze.getPiece(rowPos,colPos).isSud() || rowPos >= maze.getDim()-1 || maze.getPiece(rowPos+1,colPos).isNord()){
            throw new MoveNotAllowedException("Movement not allowed");
        }

        if(maze.getPiece(rowPos,colPos).getTrap() == CardinalPoint.SUD || maze.getPiece(rowPos+1,colPos).getTrap() == CardinalPoint.NORD){
            maze.getPiece(rowPos,colPos).revealTrap();
            maze.getPiece(rowPos+1,colPos).revealTrap();
            caughtTrap();
        }

        rowPos++;

        checkTreasure();
    }
    public void moveNord(){
        if(maze.getPiece(rowPos,colPos).isNord() || rowPos <= 0 || maze.getPiece(rowPos-1,colPos).isSud()){
            throw new MoveNotAllowedException("Movement not allowed");
        }

        if(maze.getPiece(rowPos,colPos).getTrap() == CardinalPoint.SUD || maze.getPiece(rowPos-1,colPos).getTrap() == CardinalPoint.NORD){
            maze.getPiece(rowPos,colPos).revealTrap();
            maze.getPiece(rowPos-1,colPos).revealTrap();
            caughtTrap();
        }

        rowPos--;

        checkTreasure();
    }

    private boolean move(char c){
        try {
            switch (c) {
                case CHAR_NORD:
                    moveNord();
                    moveCounter++;
                    break;
                case CHAR_EST:
                    moveEst();
                    moveCounter++;
                    break;
                case CHAR_SUD:
                    moveSud();
                    moveCounter++;
                    break;
                case CHAR_WEST:
                    moveWest();
                    moveCounter++;
                    break;
                case CHAR_ROTATE_CLOCKWISE:
                    externalPiece.rotateClockwise();
                    moveCounter++;
                    break;
                case CHAR_ROTATE_COUNTER_CLOCKWISE:
                    externalPiece.rotateCounterClockwise();
                    moveCounter++;
                    break;
                case CHAR_INSERT:
                    moveCounter++;
                    return insert();
                case CHAR_PRINT_STATUS:
                    printPointsAndLF();
                    break;
                case CHAR_HELP:
                    printHelp();
                    break;
                default:
                    return false;
            }
        }catch (MoveNotAllowedException e){
            return false;
        }

        return true;
    }
    private boolean insert(){
        System.out.println("inserisci il punto cardinale ");
        char c = in.next().charAt(0);
        System.out.println("inserisci la riga o colonna ");
        
        int i = Character.digit(in.next().charAt(0),10);
        if(i<0)  return false;

        try{
            insertPiece(i,c);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }
    private void printBoard(){
        char [][]matrixMaze = maze.getMatricPrintWithElements();
        matrixMaze[rowPos*4+1][colPos*10+4] = '(';
        matrixMaze[rowPos*4+1][colPos*10+5] = ')';
        matrixMaze[rowPos*4+2][colPos*10+3] = '-';
        matrixMaze[rowPos*4+2][colPos*10+4] = '|';
        matrixMaze[rowPos*4+2][colPos*10+5] = '|';
        matrixMaze[rowPos*4+2][colPos*10+6] = '-';

        System.out.print("\n\n\n");
        for(int i = 0; i < maze.getDim(); i++){
            System.out.print(" " + i + "        ");
        }
        System.out.print("\n");

        printMatrixWithIndex(matrixMaze,4);
        System.out.print("\n\n");
        printMatrix(externalPiece.getMatrixPrintWithElementes());
        System.out.print("\n\n");
    }
    private void printPointsAndLF(){
        System.out.print("\n");
        System.out.println("Life: " + vite + "  Punti: " + punteggio);

    }
    private static void printHelp(){
        System.out.println("Usa w,a,s,d per muoverti nel tabellone");
        System.out.println("Per ruotare la tessera esterna in senso orario usa m e in senso antiorario usa n");
        System.out.println("Se vuoi inserire la tessera digita i");
        System.out.println("Se vuoi stampare il tuo punteggio e le vite rimanenti digita p");
        System.out.println("Il tuo obiettivo è arrivare nella tessera in basso a destra vivo");
    }

    private void insertPiece(int index, char cardinalPoint){
        if(index < 0 || index >= maze.getDim() || index % 2 == 0)
            throw new IllegalArgumentException(index + " is not a valid index. It must be a even number and a valid position. ");

        cardinalPoint = Character.toLowerCase(cardinalPoint);
        if(cardinalPoint != 'n' && cardinalPoint != 'e' && cardinalPoint != 's' && cardinalPoint != 'w')
            throw new IllegalArgumentException(cardinalPoint + " is not a valid cardinal point. It must be nord, est, sud or west");

        switch (cardinalPoint){
            case 'e' -> {
                for(int i = maze.getDim()-1; i >= 0; i--)
                    externalPiece = maze.insertPiece(index,i, externalPiece);

                if(index == rowPos) {
                    colPos--;
                    if(colPos < 0)
                        colPos = maze.getDim()-1;
                    checkTreasure();
                }
            }
            case 'n' -> {
                for(int i = 0; i < maze.getDim(); i++)
                    externalPiece = maze.insertPiece(i,index, externalPiece);

                if(index == colPos) {
                    rowPos++;
                    if(rowPos >= maze.getDim())
                        rowPos = 0;
                    checkTreasure();
                }
            }
            case 's' -> {
                for(int i = maze.getDim()-1; i >= 0; i--)
                    externalPiece = maze.insertPiece(i,index, externalPiece);

                if(index == colPos) {
                    rowPos--;
                    if(rowPos < 0)
                        rowPos = maze.getDim()-1;
                    checkTreasure();
                }
            }
            case 'w' -> {
                for(int i = 0; i < maze.getDim(); i++)
                    externalPiece = maze.insertPiece(index,i, externalPiece);

                if(index == rowPos) {
                    colPos++;
                    if(colPos >= maze.getDim())
                        colPos = 0;
                    checkTreasure();
                }
            }
        }
    }

    public void play(){
        boolean partitaFinita = false;

        printBoard();

        char c;
        while(!partitaFinita) {
            try {
                do {
                    System.out.println("cosa vuoi fare? (digita h per aiuto)");
                    c = in.next().charAt(0);
                } while (!move(c));
                printBoard();

                partitaFinita = checkVictory();
            }catch (NoMoreLifesException e){
                System.out.println("Hai perso!");
                System.out.println("Hai ottenuto " + punteggio + " in " + moveCounter + " mosse");
                return;
            }
        }


        System.out.println("VITTORIA!");
        System.out.println("Hai ottenuto " + punteggio + " punti in " + moveCounter + " mosse");
    }
}
