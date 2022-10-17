package Labyrinth;

import Labyrinth.exceptions.MoveNotAllowedException;
import Labyrinth.exceptions.NoMoreLifesException;

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
      System.out.println("Inserisci il numero di vite");
      int num = in.nextInt();
      l = new Maze(num);
      t = new Piece();
      printBoard(l,t);

      boolean partitaFinita = false;

      while(!partitaFinita) {
         try {
            do {
               System.out.println("cosa vuoi fare? (digita h per aiuto)");
               c = in.next().charAt(0);
            } while (!mossa(c, l, t));
            numberoMosse++;
            printBoard(l, t);

            partitaFinita = l.controllaVittoria();
         }catch (NoMoreLifesException e){
            System.out.println("Hai perso!");
            System.out.println("Hai ottenuto " + l.getPunteggio() + " in " + numberoMosse + " mosse");
            return;
         }
      }


      System.out.println("VITTORIA!");
      System.out.println("Hai ottenuto " + l.getPunteggio() + " punti in " + numberoMosse + " mosse");
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

   private static void printHelp(){
      System.out.println("Usa w,a,s,d per muoverti nel tabellone");
      System.out.println("Per ruotare la tessera esterna in senso orario usa m e in senso antiorario usa n");
      System.out.println("Se vuoi inserire la tessera digita i");
      System.out.println("Se vuoi stampare il tuo punteggio e le vite rimanenti digita p");
      System.out.println("Il tuo obiettivo è arrivare nella tessera in basso a destra vivo");
   }
}



