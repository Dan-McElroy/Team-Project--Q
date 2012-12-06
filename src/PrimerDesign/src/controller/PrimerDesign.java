/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import view.*;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author 0901758b
 */
public class PrimerDesign {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainFrame m = new MainFrame();
        StartPanel start = new StartPanel();
        m.getContentPane().add(start);
        
        m.pack();
        m.setVisible(true);
    }
}
