/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultEditorKit;
import view.AreaSelectionPanel;
import view.TemperaturePanel;
import view.PrimerSelectionPanel;
import view.SequenceEntryPanel;
import view.StartPanel;

/**
 *
 * @author 0901758b
 */
public class PrimerDesign {
   
    public static JFrame window;
    public static StartPanel splash;
    public static SequenceEntryPanel start;
    public static AreaSelectionPanel area;
    public static PrimerSelectionPanel primerSelect;
    public static TemperaturePanel temperature;
    public static JMenuBar menu;
    
    
    private static JMenuBar createMenuBar(){
        JMenuItem menuItem = null;
        JMenuBar menuBar = new JMenuBar();
        JMenu mainMenu = new JMenu("Edit");
        mainMenu.setMnemonic(KeyEvent.VK_E);

        menuItem = new JMenuItem(new DefaultEditorKit.CopyAction());
        menuItem.setText("Copy");
        menuItem.setMnemonic(KeyEvent.VK_C);
        mainMenu.add(menuItem);

        menuItem = new JMenuItem(new DefaultEditorKit.PasteAction());
        menuItem.setText("Paste");
        menuItem.setMnemonic(KeyEvent.VK_P);
        mainMenu.add(menuItem);

        menuBar.add(mainMenu);
        return menuBar;
        
    }
    /* all GUI configuration should be placed here unless specific to some class
     */
    private static void createAndShowGUI(){
        // create frame
        window = new JFrame("Primer Design");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add start panel to frame
        splash = new StartPanel();
        window.getContentPane().add(splash);
        
        menu = createMenuBar();
        window.setJMenuBar(menu);
        
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
