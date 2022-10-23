package Labyrinth;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


import static Labyrinth.Constants.*;

public class Main_Test {
    public static void main(String[] args){
        JFrame f;

        // JButton
        JButton  b1, b2;
        JLabel b;
        // Label to display text
        JLabel l;
        //b.show();

        f = new JFrame("panel");

        // Creating a label to display text
        l = new JLabel("panel label");

        // Creating a new buttons
        //b = new JLabel(new ImageIcon("tessere/0.png").getImage().getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH));
        b1 = new JButton("button2");
        b2 = new JButton("button3");

        // Creating a panel to add buttons
        JPanel p = new JPanel();

        // Adding buttons and textfield to panel
        // using add() method
        p.add(b);
        p.add(b1);
        p.add(b2);
        p.add(l);

        // setbackground of panel
        p.setBackground(Color.red);

        // Adding panel to frame
        f.add(p);

        // Setting the size of frame
        f.setSize(300, 300);

        f.show();
    }
}
