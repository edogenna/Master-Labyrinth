package Labyrinth;

import static Labyrinth.Constants.*;

public class Main_Test {
    public static void main(String[] args){
        Piece p = new Piece(false,true,false,true,CardinalPoint.SUD,Tresure.NONE);
        p.revealTrap();
        printMatrix(p.getMatrixPrintWithElementes());
    }
}
