package Labyrinth;
import java.util.Scanner;

public class Main {
   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      System.out.println("Inserisci il numero di vite");
      int num = in.nextInt();
      Match match = new Match(num, in);
      match.play();
   }
}
