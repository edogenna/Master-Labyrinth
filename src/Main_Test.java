package Labyrinth.src;

public class Main_Test {
    public static void main(String[] args) {

        Maze maze = new Maze(5,Constants.Dimension.SMALL);
        Constants.printMatrixWithIndex(maze.getMatrixPrint(true), 4);

        System.out.println("\n\n");
        Constants.printMatrix(Constants.addIndexToMatrix(maze.getMatrixPrint(true),3,8,1,2));
    }
}
