package esercitazione4_esConclusivo;
import java.util.Random;

public class Tessera {

    private boolean nord;
    private boolean est;
    private boolean sud;
    private boolean west;
    private char tesoro;

    private final String orizzontalFull   = " +------+ ";
    private final String orizzontalEmpty  = " +      + ";
    private final String orizzontalBlanks = "      ";
    private static final char tesoroNessuno = ' ';
    private static final char tesoroGrande = '@';
    private static final char tesoroPiccolo = '*';
    private static final int tesoroGrandeProb = 1;
    private static final int tesoroPiccoloProb = 3;
    private static final int punteggioTesoroGrande = 1000;
    private static final int punteggioTesoroPiccolo = 100;



    public Tessera(boolean nord, boolean est, boolean sud, boolean west) {
        this.nord = nord;
        this.est = est;
        this.sud = sud;
        this.west = west;
        this.tesoro = generaPremio();
    }

    public Tessera(){
        Random rand = new Random();

        this.nord = rand.nextBoolean();
        this.est = rand.nextBoolean();
        this.sud = rand.nextBoolean();
        this.west = rand.nextBoolean();
        this.tesoro = generaPremio();

        //elimino il problema delle tessere tutte chiuse
        if(this.nord && this.est && this.sud && this.west){
            int num = rand.nextInt(4);
            switch (num) {
                case 0 -> this.nord = false;
                case 1 -> this.est = false;
                case 2 -> this.sud = false;
                case 3 -> this.west = false;
                default -> System.out.println("Errore rigenerazione tessera tutta chiusa!");
            }
        }
    }


    public void print(){
        if(this.nord)
            System.out.print(orizzontalFull);
        else
            System.out.print(orizzontalEmpty);

        System.out.print("\n");

        for(int k = 0; k < 2; k++){
            System.out.print(" ");

            if(this.west)
                System.out.print("|");
            else
                System.out.print(" ");

            System.out.print("  " + this.tesoro + this.tesoro + "  ");

            if(this.est)
                System.out.print("|");
            else
                System.out.print(" ");

            System.out.print(" ");
            System.out.print("\n");
        }

        if(this.sud)
            System.out.print(orizzontalFull);
        else
            System.out.print(orizzontalEmpty);
        System.out.print("\n");

    }

    public Tessera rotazione9Orario(){
        boolean tmp;
        tmp = this.west;
        this.west = this.sud;
        this.sud = this.est;
        this.est = this.nord;
        this.nord = tmp;

        return this;
    }
    public Tessera rotazione90AntiOrario(){
        boolean tmp;
        tmp = this.est;
        this.est = this.sud;
        this.sud = this.west;
        this.west = this.nord;
        this.nord = tmp;

        return this;
    }
    public Tessera rotazione180(){
        boolean tmp;
        tmp = this.west;
        this.west = this.est;
        this.est = tmp;

        tmp = this.sud;
        this.sud = this.nord;
        this.nord = tmp;

        return this;
    }

    private static char generaPremio(){
        Random rand = new Random();

        int n = rand.nextInt(10);
        if(n < tesoroGrandeProb)
            return tesoroGrande;
        if(n <= tesoroPiccoloProb)
            return tesoroPiccolo;
        return tesoroNessuno;
    }

    public boolean isNord() { return nord; }
    public boolean isEst() { return est; }
    public boolean isSud() { return sud; }
    public boolean isWest() { return west; }
    public char getTesoro() { return tesoro; }

    public int ritiraCancellaPremio() {
        int p = 0;

        if(tesoro == tesoroNessuno)
            System.out.println("Non c'è nessun tesoro su questa tessera!");
        else if(tesoro == tesoroGrande)
            p = punteggioTesoroGrande;
        else if(tesoro == tesoroPiccolo)
            p = punteggioTesoroPiccolo;
        else
            System.out.println("Questa tessera ha un tesoro non compatibile!");

        tesoro = tesoroNessuno;
        return p;
    }
}