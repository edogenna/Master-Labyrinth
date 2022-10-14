package esercitazione4_esConclusivo;

public class Labirinto{
    public final int dim = 5;
    private final String orizzontalFull   = " +------+ ";
    private final String orizzontalEmpty  = " +      + ";
    private final String orizzontalBlanks = "      ";
    private final String head = "  ()  ";
    private final String body = " -||- ";

//         +------+  +      +  +      +  +------+  +------
//         |  ()  |                      |         |  @@
//         | -||- |                      |         |  @@
//         +------+

    private Tessera[][] tessere;

    //TRUE => CLOSED
    public Labirinto() {
        tessere = new Tessera[dim][dim];

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                tessere[i][j] = new Tessera();
            }
        }
    }

    public void print(){
        for(int i = 0; i < dim; i++){
            printNord(i);
            printEstAndWest(i);
            printSud(i);
        }
    }

    private void printNord(int row){
        for(int col = 0; col < dim; col++){
            if(tessere[row][col].isNord())
                System.out.print(orizzontalFull);
            else
                System.out.print(orizzontalEmpty);
        }
        System.out.print("\n");
    }
    private void printSud(int row){
        for(int col = 0; col < dim; col++){
            if(tessere[row][col].isSud())
                System.out.print(orizzontalFull);
            else
                System.out.print(orizzontalEmpty);
        }
        System.out.print("\n");
    }
    private void printEstAndWest(int row){
        for(int h = 0; h < 2; h++){
            for(int col = 0; col < dim; col++){
                System.out.print(" ");

                if(tessere[row][col].isWest())
                    System.out.print("|");
                else
                    System.out.print(" ");

                if(rowPos == row && colPos == col){
                    if(h == 0)
                        System.out.print(head);
                    else if(h == 1)
                        System.out.print(body);
                }else
                    System.out.print("  " + tessere[row][col].getTesoro() + tessere[row][col].getTesoro() + "  ");

                if(tessere[row][col].isEst())
                    System.out.print("|");
                else
                    System.out.print(" ");

                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }

    public void printBooleanValues(){
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                System.out.println("i: " + i + " j: " + j + " " +
                        " n: " + tessere[i][j].isNord() + " e: " + tessere[i][j].isEst() +
                        " s: " + tessere[i][j].isSud() + " w: " + tessere[i][j].isWest());
            }
        }
    }

    public Tessera inserisciTessera(int index, char cardinalPoint, Tessera tesseraDaInserire){
        if(index < 0 || index >= dim || index % 2 == 0)
            throw new IllegalArgumentException(index + " is not a valid index. It must be a even number and a valid position. ");

        cardinalPoint = Character.toLowerCase(cardinalPoint);
        if(cardinalPoint != 'n' && cardinalPoint != 'e' && cardinalPoint != 's' && cardinalPoint != 'w')
            throw new IllegalArgumentException(cardinalPoint + " is not a valid cardinal point. It must be nord, est, sud or west");

        Tessera tesseraUscente = null;

        switch (cardinalPoint){
            case 'e' -> {
                tesseraUscente = tessere[index][0];
                for(int i = 0; i < dim - 1; i++)
                    tessere[index][i] = tessere[index][i+1];
                tessere[index][dim-1] = tesseraDaInserire;
            }
            case 'n' -> {
                tesseraUscente = tessere[dim-1][index];
                for(int i = dim - 1; i > 0; i--)
                    tessere[i][index] = tessere[i-1][index];
                tessere[0][index] = tesseraDaInserire;
            }
            case 's' -> {
                tesseraUscente = tessere[0][index];
                for(int i = 0; i < dim - 1; i++)
                    tessere[i][index] = tessere[i+1][index];
                tessere[dim-1][index] = tesseraDaInserire;
            }
            case 'w' -> {
                tesseraUscente = tessere[index][dim-1];
                for(int i = dim - 1; i > 0; i--)
                    tessere[index][i] = tessere[index][i-1];
                tessere[index][0] = tesseraDaInserire;
            }
        }

        return tesseraUscente;
    }

    public Tessera getTessera(int x, int y){
        return tessere[x][y];
    }




    private int punteggio;
    private int vite;
    private int rowPos;
    private int colPos;


    public boolean controllaVittoria(){ return rowPos == (dim-1) && colPos == (dim-1); }

    public int getPunteggio() {
        return punteggio;
    }
    public int getVite() {
        return vite;
    }
    public int getRowPos() { return rowPos; }
    public int getColPos() { return colPos; }

    public void spostatiEst(){
        if(tessere[rowPos][colPos].isEst() || colPos >= dim-1 || tessere[rowPos][colPos+1].isWest()){
            System.out.println("Movimento non consentito");
            return;
        }
        colPos++;
    }
    public void spostatiWest(){
        if(tessere[rowPos][colPos].isWest() || colPos <= 0 || tessere[rowPos][colPos-1].isEst()){
            System.out.println("Movimento non consentito");
            return;
        }
        colPos--;
    }
    public void spostatiSud(){
        if(tessere[rowPos][colPos].isSud() || rowPos >= dim-1 || tessere[rowPos+1][colPos].isNord()){
            System.out.println("Movimento non consentito");
            return;
        }
        rowPos--;
    }
    public void spostatiNord(){
        if(tessere[rowPos][colPos].isNord() || rowPos <= 0 || tessere[rowPos-1][colPos].isSud()){
            System.out.println("Movimento non consentito");
            return;
        }
        rowPos++;
    }

}
