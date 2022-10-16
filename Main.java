package Labyrinth;

import Labyrinth.exceptions.MoveNotAllowedException;
import java.util.Scanner;
import static Labyrinth.Constants.*;

public class Main {

   private static Scanner in;
   private static Maze l;
   private static Piece t;
   private static int numberoMosse;

   public static void main(String[] args) {
      in = new Scanner(System.in);
      char c;
      l = new Maze();
      t = new Piece();
      printBoard(l,t);

      boolean partitaFinita = false;

      while(!partitaFinita) {
         do {
            System.out.println("cosa vuoi fare? ");
            c = in.next().charAt(0);
         } while (!mossa(c, l, t));
         numberoMosse++;
         printBoard(l, t);

         partitaFinita = l.controllaVittoria();
      }

      System.out.println("VITTORIA!");
      System.out.println("Hai ottenuto " + l.getPunteggio() + " in " + numberoMosse + " mosse");
   }

   private static boolean mossa(char c, Maze l, Piece t){
      try {
         switch (c) {
            case CHAR_NORD:
               l.spostatiNord();
               break;
            case CHAR_EST:
               l.spostatiEst();
               break;
            case CHAR_SUD:
               l.spostatiSud();
               break;
            case CHAR_WEST:
               l.spostatiWest();
               break;
            case CHAR_ROTATE_CLOCKWISE:
               t.rotateClockwise();
               break;
            case CHAR_ROTATE_COUNTER_CLOCKWISE:
               t.rotateCounterClockwise();
               break;
            case CHAR_INSERT:
               return insert(l, t);
            case CHAR_PRINT_STATUS:
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
      System.out.print("\n\n\n");

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



