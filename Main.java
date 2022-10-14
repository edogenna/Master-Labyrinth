package esercitazione4_esConclusivo;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner in = new Scanner(System.in);
        char c;
        Labirinto l = new Labirinto();
        Tessera t = new Tessera();
        l.print();
        System.out.print("\n\n");
        t.print();
        System.out.print("\n\n");

        for(int i = 0; i<3; i++) {
            do {
                System.out.println("inserisci il carattere per lo spostamento:");
                c = in.next().charAt(0);
            } while (!spostati(c, l));
            l.print();
        }




    }

    static boolean spostati(char c, Labirinto l){
        switch (c){
            case 'n' -> l.spostatiNord();
            case 'e' -> l.spostatiEst();
            case 's' -> l.spostatiSud();
            case 'w' -> l.spostatiWest();
            case 'x' -> { return true; }
            default -> { return false; }
        }
        return true;
    }
}



