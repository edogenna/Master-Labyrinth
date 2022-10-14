package Labyrinth;

import Labyrinth.exceptions.MoveNotAllowedException;

import java.util.Scanner;

public class Main {
   static final char cNord = 'w';
   final static char cSud = 's';
   final static char cEst = 'd';
   final static char cWest = 'a';
   final static char cRotateClockwise = 'm';
   final static char cRotateCounterClockwise = 'n';
   final static char cInsert = 'i';
   final static char cStats = 'p';
   private static Scanner in;
   private static Maze l;
   private static Piece t;
   public static void main(String[] args) {
      in = new Scanner(System.in);
      char c;
      l = new Maze();
      t = new Piece();
      printBoard(l,t);

      while(true) {
         do {
            System.out.println("cosa vuoi fare? ");
            c = in.next().charAt(0);
         } while (!mossa(c, l, t));
         printBoard(l,t);
      }


   }

   private static boolean mossa(char c, Maze l, Piece t){
      try {
         switch (c) {
            case cNord:
               l.spostatiNord();
               break;
            case cEst:
               l.spostatiEst();
               break;
            case cSud:
               l.spostatiSud();
               break;
            case cWest:
               l.spostatiWest();
               break;
            case cRotateClockwise:
               t.rotateClockwise();
               break;
            case cRotateCounterClockwise:
               t.rotateCounterClockwise();
               break;
            case cInsert:
               return insert(l, t);
            case cStats:
               printPointsAndLF();
            default:
               return false;
         }
      }catch (MoveNotAllowedException e){
         return false;
      }

      return true;
   }

   private static boolean insert(Maze l, Piece t){
      System.out.println("inserisci il punto cardinale ");
      char c = in.next().charAt(0);
      System.out.println("inserisci la riga o colonna ");
      int i = Character.digit(in.next().charAt(0),10);
      if(i<0)  return false;

      try{
         Main.t = l.insertPiece(i,c,t);
      }catch (IllegalArgumentException e){
         System.out.println(e.getMessage());
         return false;
      }

      return true;
   }

   private static void printBoard(Maze l, Piece t){
      System.out.print("\n\n\n\n\n");

      l.print();
      System.out.print("\n\n");
      t.print();
      System.out.print("\n\n");
   }


   private static void printPointsAndLF(){
      System.out.print("\n");
      System.out.println("Life: " + l.getVite() + "  Punti: " + l.getPunteggio());

   }
}



