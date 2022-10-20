package Labyrinth;
import java.util.Scanner;

public class Main {
   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      Match match = new Match(in);
      match.play();
   }
}
