package Labyrinth;

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
   private static Labirinto l;
   private static Tessera t;
   public static void main(String[] args) {
      in = new Scanner(System.in);
      char c;
      l = new Labirinto();
      t = new Tessera();
      printBoard(l,t);

      while(true) {
         do {
            System.out.println("cosa vuoi fare? ");
            c = in.next().charAt(0);
         } while (!mossa(c, l, t));
         printBoard(l,t);
      }


   }

   private static boolean mossa(char c, Labirinto l, Tessera t){
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
            t.rotazione9Orario();
            break;
         case cRotateCounterClockwise:
            t.rotazione90AntiOrario();
            break;
         case cInsert:
            return insert(l, t);
         case cStats:
            printPointsAndLF();
         default:
            return false;
      }

      return true;
   }

   private static boolean insert(Labirinto l, Tessera t){
      System.out.println("inserisci il punto cardinale ");
      char c = in.next().charAt(0);
      System.out.println("inserisci la riga o colonna ");
      int i = Character.digit(in.next().charAt(0),10);
      if(i<0)  return false;

      try{
         Main.t = l.inserisciTessera(i,c,t);
      }catch (IllegalArgumentException e){
         System.out.println(e.getMessage());
         return false;
      }

      return true;
   }

   private static void printBoard(Labirinto l, Tessera t){
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



