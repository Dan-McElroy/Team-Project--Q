/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import view.AreaSelection;
import view.PrimerSelectionPanel;
import view.StartPanel;

/**
 *
 * @author 0901758b
 */
public class PrimerDesign {
   
    public static JFrame window;
    public static StartPanel start;
    public static AreaSelection area;
    public static PrimerSelectionPanel primerSelect;
    
    /* all GUI configuration should be placed here unless specific to some class
     */
    private static void createAndShowGUI(){
        // create frame
        window = new JFrame("Primer Design");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add start panel to frame
        start = new StartPanel();
        window.getContentPane().add(start);
        
        // size the window and show it
        window.pack();
        window.setVisible(true);
    }
    
    /* The main method should not need adjusting */
    public static void main(String args[]){
        // set look and feel to same as system
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PrimerDesign.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(PrimerDesign.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PrimerDesign.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(PrimerDesign.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
