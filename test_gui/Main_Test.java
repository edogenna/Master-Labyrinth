package Labyrinth.test_gui;

import Labyrinth.src.*;
import javax.swing.*;
import java.awt.*;

import static Labyrinth.src.Constants.*;

public class Main_Test {
    public static void main(String[] args){
        final int DIM_IMG = 50;

        JFrame f =  new JFrame("panel");
        Maze m = new Maze();

        JLabel[][] labels = new JLabel[DEFAULT_MAZE_DIM][DEFAULT_MAZE_DIM];

        for(int i = 0; i < DEFAULT_MAZE_DIM; i++){
            for(int j = 0; j < DEFAULT_MAZE_DIM; j++) {
//                ImageIcon imageIcon = new ImageIcon("tessere/" + m.getPiece(i,j).getCardinalPoints() + ".png"); // load the image to a imageIcon
//                Image image = imageIcon.getImage(); // transform it
//                Image newimg = image.getScaledInstance(DIM_IMG, DIM_IMG, java.awt.Image.SCALE_SMOOTH);
                labels[i][j] = new JLabel(MergedIcon.resizeImage("tessere/" + m.getPiece(i,j).getCardinalPoints() + ".png", DIM_IMG));
                f.add(labels[i][j]);
            }
        }


        MergedIcon mergedIcon = new MergedIcon(new ImageIcon("tessere/" + m.getPiece(0,0).getCardinalPoints() + ".png"),
                                                new ImageIcon("tessere/stickman_trasparente_300x300.png"));

        labels[0][0].setIcon(MergedIcon.resizeImage(mergedIcon,DIM_IMG));


        f.setLayout(new GridLayout(DEFAULT_MAZE_DIM,DEFAULT_MAZE_DIM));

//        // JButton
//        JButton  b1, b2;
//        JLabel b;
//        // Label to display text
//        JLabel l;
//        //b.show();
//
//        f = new JFrame("panel");
//
//        // Creating a label to display text
//        l = new JLabel("panel label");

        // Creating a new buttons

//        ImageIcon imageIcon = new ImageIcon("tessere/1.png"); // load the image to a imageIcon
//        Image image = imageIcon.getImage(); // transform it
//        Image newimg = image.getScaledInstance(DIM, DIM,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
//
//
//        b = new JLabel(new ImageIcon(newimg));
//        b1 = new JButton("button2");
//        b2 = new JButton("button3");

        // Creating a panel to add buttons
//        JPanel p = new JPanel();
//
//        // Adding buttons and textfield to panel
//        // using add() method
//        p.add(b);
//        p.add(b1);
//        p.add(b2);
//        p.add(l);
//
//        // setbackground of panel
//        p.setBackground(Color.red);

        // Adding panel to frame
//        f.add(p);

        // Setting the size of frame
        f.setSize(300, 300);

        f.show();
    }



}
