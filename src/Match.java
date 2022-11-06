package Labyrinth.src;

import Labyrinth.src.exceptions.MoveNotAllowedException;
import Labyrinth.src.exceptions.NoMoreLifesException;

import java.util.Scanner;

import static Labyrinth.src.Constants.*;

public class Match {
    private int points;
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

    private final static String HELP = """
            w: move nord
            s: move sud
            d: move est
            a: move west
            m: rotate clockwise
            n: rotate counter clockwise
            i: insert piece
            p: print status
            h: help
            q: quit""";
    private final static String[] STATUS = { "Punteggio: %d", "Vite: %d", "Posizione: (%d, %d)", "Numero mosse: %d"};

    public Match(Scanner in){
        this(-1,in);
        this.setVite();
    }

    public Match(int vite, Scanner in){
        this.vite = vite;
        this.points = 0;
        this.rowPos = 0;
        this.colPos = 0;
        this.maze = new Maze(DEFAULT_NUMBER_OF_PIECES_IN_ROW);
        this.externalPiece = new Piece(maze.getDimOfPieces());
        this.in = in;
        this.moveCounter = 0;
    }

    private void setVite(){
        System.out.println("Inserisci il numero di vite");
        this.vite = in.nextInt();
    }

    private void checkTreasure(){ points += maze.getPiece(rowPos,colPos).withdrawDeleteTreasure(); }
    public boolean checkVictory(){ return rowPos == (maze.getNumberOfPiecesInASide()-1) && colPos == (maze.getNumberOfPiecesInASide()-1); }

    private void caughtTrap() {
        System.out.println("Oh no, sei passato su una trappola!");
        vite--;
        if(vite <= 0)
            throw new NoMoreLifesException("Hai finito le vite!");
    }
    public void moveEst(){
        if(maze.getPiece(rowPos,colPos).isEst() || colPos >= maze.getNumberOfPiecesInASide()-1 || maze.getPiece(rowPos,colPos+1).isWest()) {
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
        if(maze.getPiece(rowPos,colPos).isSud() || rowPos >= maze.getNumberOfPiecesInASide()-1 || maze.getPiece(rowPos+1,colPos).isNord()){
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
    private void printBoardPlus(){
        char [][]matrixMaze = maze.getMatrixPrint(true);
        if(maze.getDimOfPieces() == Dimension.LARGE) {
            matrixMaze[rowPos * 4 + 1][colPos * 10 + 4] = '(';
            matrixMaze[rowPos * 4 + 1][colPos * 10 + 5] = ')';
            matrixMaze[rowPos * 4 + 2][colPos * 10 + 3] = '-';
            matrixMaze[rowPos * 4 + 2][colPos * 10 + 4] = '|';
            matrixMaze[rowPos * 4 + 2][colPos * 10 + 5] = '|';
            matrixMaze[rowPos * 4 + 2][colPos * 10 + 6] = '-';
        }else{
            matrixMaze[rowPos * 3 + 1][colPos * 8 + 3] = 'A';
            matrixMaze[rowPos * 3 + 1][colPos * 8 + 4] = 'A';
        }


        System.out.print("\n\n\n\n");

        int modRow = maze.getDimOfPieces() == Dimension.LARGE ? Piece.ROWS_IN_A_PIECE_LARGE : Piece.ROWS_IN_A_PIECE_SMALL;
        int modCol = maze.getDimOfPieces() == Dimension.LARGE ? Piece.COLUMNS_IN_A_PIECE_LARGE : Piece.COLUMNS_IN_A_PIECE_SMALL;
        int offsetRow = 1;
        int offsetCol = 3;

        char [][]matrixMazeWithIndex = addIndexToMatrix(matrixMaze,modRow,modCol,offsetRow,offsetCol);

        char [][]matrixMatch = new char[matrixMazeWithIndex.length][matrixMazeWithIndex.length + 80];
        setMatrixToChar(matrixMatch,' ');
        copyMatrix(matrixMatch,0,0,matrixMazeWithIndex);


        copyArrayInMatrix(matrixMatch,2,matrixMazeWithIndex[0].length+7,String.format(STATUS[0],points).toCharArray());
        copyArrayInMatrix(matrixMatch,3,matrixMazeWithIndex[0].length+7,String.format(STATUS[1],vite).toCharArray());
        copyArrayInMatrix(matrixMatch,4,matrixMazeWithIndex[0].length+7,String.format(STATUS[2],rowPos,colPos).toCharArray());
        copyArrayInMatrix(matrixMatch,5,matrixMazeWithIndex[0].length+7,String.format(STATUS[3],moveCounter).toCharArray());
        copyArrayInMatrix(matrixMatch,7,matrixMazeWithIndex[0].length+7,"Tessera Esterna: ".toCharArray());
        copyMatrix(matrixMatch,8,matrixMazeWithIndex[0].length+7,externalPiece.getMatrixPrint(true));
        copyArrayInMatrix(matrixMatch,matrixMatch.length - 2,matrixMazeWithIndex[0].length+7,"Cosa vuoi fare? (inserisci h per aiuto)".toCharArray());

        printMatrix(matrixMatch);

    }
    private void printPointsAndLF(){
        System.out.print("\n");
        System.out.println("Life: " + vite + "  Punti: " + points);

    }
    private static void printHelp(){
        System.out.println("Usa w,a,s,d per muoverti nel tabellone");
        System.out.println("Per ruotare la tessera esterna in senso orario usa m e in senso antiorario usa n");
        System.out.println("Se vuoi inserire la tessera digita i");
        System.out.println("Se vuoi stampare il tuo punteggio e le vite rimanenti digita p");
        System.out.println("Il tuo obiettivo è arrivare nella tessera in basso a destra vivo");
    }

    private void insertPiece(int index, char cardinalPoint){
        if(index < 0 || index >= maze.getNumberOfPiecesInASide() || index % 2 == 0)
            throw new IllegalArgumentException(index + " is not a valid index. It must be a even number and a valid position. ");

        cardinalPoint = Character.toLowerCase(cardinalPoint);
        if(cardinalPoint != 'n' && cardinalPoint != 'e' && cardinalPoint != 's' && cardinalPoint != 'w')
            throw new IllegalArgumentException(cardinalPoint + " is not a valid cardinal point. It must be nord, est, sud or west");

        switch (cardinalPoint){
            case 'e' -> {
                for(int i = maze.getNumberOfPiecesInASide()-1; i >= 0; i--)
                    externalPiece = maze.insertPiece(index,i, externalPiece);

                if(index == rowPos) {
                    colPos--;
                    if(colPos < 0)
                        colPos = maze.getNumberOfPiecesInASide()-1;
                    checkTreasure();
                }
            }
            case 'n' -> {
                for(int i = 0; i < maze.getNumberOfPiecesInASide(); i++)
                    externalPiece = maze.insertPiece(i,index, externalPiece);

                if(index == colPos) {
                    rowPos++;
                    if(rowPos >= maze.getNumberOfPiecesInASide())
                        rowPos = 0;
                    checkTreasure();
                }
            }
            case 's' -> {
                for(int i = maze.getNumberOfPiecesInASide()-1; i >= 0; i--)
                    externalPiece = maze.insertPiece(i,index, externalPiece);

                if(index == colPos) {
                    rowPos--;
                    if(rowPos < 0)
                        rowPos = maze.getNumberOfPiecesInASide()-1;
                    checkTreasure();
                }
            }
            case 'w' -> {
                for(int i = 0; i < maze.getNumberOfPiecesInASide(); i++)
                    externalPiece = maze.insertPiece(index,i, externalPiece);

                if(index == rowPos) {
                    colPos++;
                    if(colPos >= maze.getNumberOfPiecesInASide())
                        colPos = 0;
                    checkTreasure();
                }
            }
        }
    }

    public void play(){
        boolean partitaFinita = false;

        printBoardPlus();

        char c;
        while(!partitaFinita) {
            try {
                do {
                    c = in.next().charAt(0);
                } while (!move(c));
                printBoardPlus();

                partitaFinita = checkVictory();
            }catch (NoMoreLifesException e){
                System.out.println("Hai perso!");
                System.out.println("Hai ottenuto " + points + " in " + moveCounter + " mosse");
                return;
            }
        }


        System.out.println("VITTORIA!");
        System.out.println("Hai ottenuto " + points + " punti in " + moveCounter + " mosse");
    }
}
