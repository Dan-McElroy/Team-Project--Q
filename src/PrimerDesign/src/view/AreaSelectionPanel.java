/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.PrimerDesign;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author 0907822r
 */
public class AreaSelectionPanel extends javax.swing.JPanel {
    
    private int startTarget, endTarget;
    private String lineNums;



    public int getStartTarget() {
        return startTarget;
    }

    public void setStartTarget(int startTarget) {
        this.startTarget = startTarget;
    }

    public int getEndTarget() {
        return endTarget;
    }

    public void setEndTarget(int endTarget) {
        this.endTarget = endTarget;
    }

    
    private class AreaCaretListener implements CaretListener{
        
        @Override
        public void caretUpdate(CaretEvent e) {
            int update = 150; // number of bases required to fire update
            int fromVal = e.getMark(); 
            int toVal = e.getDot();

            fromVal = PrimerDesign.unrealIndex(fromVal) + 1;
            toVal = PrimerDesign.unrealIndex(toVal);
            if (fromVal > toVal) {
                int temp = fromVal - 1;
                fromVal = toVal + 1;
                toVal = temp;
            }
            if ((toVal - fromVal) >= update) {
                fromTextField.setText(Integer.toString(fromVal));
                toTextField.setText(Integer.toString(toVal));
            }

            String fromText = fromTextField.getText().toString();
            String toText = toTextField.getText().toString();
            if (fromText.equalsIgnoreCase(toText)) {
                fromTextField.setText(null);
                toTextField.setText(null);
            }
        }

        /*
        @Override
        public void caretUpdate(CaretEvent e) {
            if (fromTextField.getText().toString().isEmpty() && toTextField.getText().toString().isEmpty()){
                if (e.getSource().equals(oStrandTextPane) || e.getSource().equals(cStrandTextPane)) {
                    fromTextField.setText(Integer.toString(e.getMark()));
                    int numSpaces = (e.getDot() - e.getMark()) / 10;
                    toTextField.setText(Integer.toString(e.getDot() - numSpaces));
                    if (fromTextField.getText().toString().compareTo(toTextField.getText().toString()) < 0){
                        String temp = fromTextField.getText().toString();
                        fromTextField.setText(toTextField.getText());
                        toTextField.setText(temp);
                    }
                }
                if (fromTextField.getText().toString().equalsIgnoreCase(toTextField.getText().toString())) {
                    fromTextField.setText(null);
                    toTextField.setText(null);
                }
            }
        }
        */
        
    }
    
    /**
     * Creates new form areaSelection
     */
    public AreaSelectionPanel() {
        initComponents();
        //high = sequenceTextArea.getHighlighter();
        //from = 0;
        //to = 0;
        StyleContext sc = new StyleContext();
        final DefaultStyledDocument oDoc = new DefaultStyledDocument(sc);
        final DefaultStyledDocument cDoc = new DefaultStyledDocument(sc);
//        final DefaultStyledDocument bDoc = new DefaultStyledDocument(sc);
        
        Style defaultStyle = sc.getStyle(StyleContext.DEFAULT_STYLE);
        final Style mainStyle = sc.addStyle("MainStyle", defaultStyle);
        StyleConstants.setFontFamily(mainStyle, "monospaced");
        
//        Style complementaryStyle = sc.addStyle("ComplementaryStyle", defaultStyle);
//        StyleConstants.setFontFamily(complementaryStyle, "monospaced");
//        StyleConstants.setForeground(complementaryStyle, Color.BLUE);
//        
//        Style originalStyle = sc.addStyle("OriginalStyle", defaultStyle);
//        StyleConstants.setFontFamily(originalStyle, "monospaced");
//        StyleConstants.setForeground(originalStyle, Color.ORANGE);
        
        oDoc.setLogicalStyle(0, mainStyle);
        cDoc.setLogicalStyle(0, mainStyle);
//        bDoc.setLogicalStyle(0, originalStyle);
        
        try {
            // Add the text to the document
            oDoc.insertString(0, PrimerDesign.start.getInSequence().toString('o', 10, 70), null);
            cDoc.insertString(0, PrimerDesign.start.getInSequence().toString('c', 10, 70), null);
//            bDoc.insertString(0, PrimerDesign.start.getInSequence().toString('b', 10, 70), null);
        } catch (BadLocationException ex) {
            Logger.getLogger(PrimerSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        oStrandTextPane.setDocument(oDoc);
        cStrandTextPane.setDocument(cDoc);
//        bStrandTextPane.setDocument(bDoc);
        
        // Section for colouring the complementary strand
//        int colourStart = 0;
//        while(colourStart <= bDoc.getLength()){
//            
//            if((colourStart + 154) > bDoc.getLength()){
//                bDoc.setCharacterAttributes(colourStart, 77 -(154 - (bDoc.getLength() - colourStart))/2, complementaryStyle, false);
//            }
//            else{
//                bDoc.setCharacterAttributes(colourStart, 77, complementaryStyle, false);
//            }
//            
//            colourStart += 154;
//        }
//        
        lineNums = "";
//        doubleLineNums = "";
        int x = 1;
        for(int i = 0; x < PrimerDesign.start.getInSequence().length(); i++){
            lineNums += x + "\n";
//            doubleLineNums += x + "\n\n";
            x += 70;
        }
        lineNumberTextArea.setText(lineNums);
        lineNumberTextArea.setCaretPosition(0);
        
        lineAreaScroll.getVerticalScrollBar().setModel(
                oStrandScroll.getVerticalScrollBar().getModel());
        
        CaretListener caretListener = new AreaCaretListener();
        oStrandTextPane.addCaretListener(caretListener);
        cStrandTextPane.addCaretListener(caretListener);
        
        displayTabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                updateLineNums(displayTabbedPane.getSelectedIndex());
            }
        });
        
    }
    
    public void updateLineNums(int tab){
        
        if (tab == 0){
            lineNumberTextArea.setText(lineNums);
            lineNumberTextArea.setCaretPosition(0);
            lineAreaScroll.getVerticalScrollBar().setModel(
                    oStrandScroll.getVerticalScrollBar().getModel());
        }else if (tab == 1){
            lineNumberTextArea.setText(lineNums);
            lineNumberTextArea.setCaretPosition(0);
            lineAreaScroll.getVerticalScrollBar().setModel(
                    cStrandScroll.getVerticalScrollBar().getModel());
        } //else {
//            lineNumberTextArea.setText(doubleLineNums);
//            lineNumberTextArea.setCaretPosition(0);
//            lineAreaScroll.getVerticalScrollBar().setModel(
//                    bStrandScroll.getVerticalScrollBar().getModel());
//        }
    }

    /**
     * This method is called from within the constructor to initialise the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        instructionTextPane = new javax.swing.JTextPane();
        nextButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        fromLabel = new javax.swing.JLabel();
        toLabel = new javax.swing.JLabel();
        toTextField = new javax.swing.JTextField();
        fromTextField = new javax.swing.JTextField();
        lineAreaScroll = new javax.swing.JScrollPane();
        lineNumberTextArea = new javax.swing.JTextArea();
        displayTabbedPane = new javax.swing.JTabbedPane();
        oStrandScroll = new javax.swing.JScrollPane();
        oStrandTextPane = new javax.swing.JTextPane();
        cStrandScroll = new javax.swing.JScrollPane();
        cStrandTextPane = new javax.swing.JTextPane();

        setPreferredSize(new java.awt.Dimension(800, 600));

        titleLabel.setFont(new java.awt.Font("DejaVu Sans", 0, 24)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Target Sequence Selection");

        instructionTextPane.setEditable(false);
        instructionTextPane.setFont(new java.awt.Font("DejaVu Sans", 0, 14)); // NOI18N
        instructionTextPane.setText("Specify the region to be amplified by PCR. Indicate the region using the numbers on the left hand side and write these in the boxes below.");
        jScrollPane2.setViewportView(instructionTextPane);

        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        fromLabel.setText("From:");

        toLabel.setText("To:");

        lineAreaScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        lineAreaScroll.setPreferredSize(new java.awt.Dimension(7, 70));

        lineNumberTextArea.setEditable(false);
        lineNumberTextArea.setColumns(1);
        lineNumberTextArea.setFont(new java.awt.Font("Lucida Sans Typewriter", 0, 11)); // NOI18N
        lineNumberTextArea.setRows(5);
        lineNumberTextArea.setTabSize(4);
        lineAreaScroll.setViewportView(lineNumberTextArea);

        displayTabbedPane.setBackground(new java.awt.Color(254, 254, 254));

        oStrandTextPane.setEditable(false);
        oStrandTextPane.setBackground(new java.awt.Color(254, 254, 254));
        oStrandTextPane.setFont(new java.awt.Font("Lucida Sans Typewriter", 0, 13)); // NOI18N
        oStrandScroll.setViewportView(oStrandTextPane);

        displayTabbedPane.addTab("DNA Sequence", oStrandScroll);

        cStrandTextPane.setEditable(false);
        cStrandTextPane.setBackground(new java.awt.Color(254, 254, 254));
        cStrandTextPane.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 13)); // NOI18N
        cStrandScroll.setViewportView(cStrandTextPane);

        displayTabbedPane.addTab("Complementary", cStrandScroll);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 610, Short.MAX_VALUE)
                        .addComponent(nextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(233, 233, 233)
                                .addComponent(fromLabel)
                                .addGap(12, 12, 12)
                                .addComponent(fromTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71)
                                .addComponent(toLabel)
                                .addGap(12, 12, 12)
                                .addComponent(toTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(12, 12, 12))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lineAreaScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(displayTabbedPane)))
                .addGap(12, 12, 12))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(titleLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(fromTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(toTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(toLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fromLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(displayTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                        .addGap(6, 6, 6))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(lineAreaScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(backButton)
                    .addComponent(nextButton))
                .addGap(12, 12, 12))
        );
    }// </editor-fold>//GEN-END:initComponents

    
    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        try {
        startTarget = Integer.parseInt(fromTextField.getText());
        endTarget = Integer.parseInt(toTextField.getText());
        // At this point, end target exists.
        
        if (startTarget < 1 || endTarget < 1)
            throw new NumberFormatException();
        if (endTarget >
                PrimerDesign.start.getInSequence().getOStrand().length())
            throw new OverShootException();     //THROWN INCORRECTLY example: length 430, 34-391 safe, 34-392 not from doesn't affect
        if ((endTarget - startTarget + 1) < 80)
            throw new LowCountException();
        if ((endTarget - startTarget + 1) > 1400)
            throw new HighCountException();
        if (PrimerDesign.start.getInSequence().containsN(startTarget, endTarget))
            throw new NException();

        PrimerDesign.start.getInSequence().start = startTarget-1;
        PrimerDesign.start.getInSequence().end = endTarget;
        
        PrimerDesign.window.remove(PrimerDesign.area);
        PrimerDesign.window.setVisible(false);

        PrimerDesign.primerSelect = new PrimerSelectionPanel();
        PrimerDesign.window.getContentPane().add(PrimerDesign.primerSelect);
        PrimerDesign.window.pack();
        PrimerDesign.window.setVisible(true);
        
        } catch(NumberFormatException e) {
            NumberFormatErrorBox nfeb = new NumberFormatErrorBox(PrimerDesign.window, true);
            nfeb.setLocation(215, 138);
            nfeb.setVisible(true);
        } catch(OverShootException e1) {
            OverShootBox osb = new OverShootBox(PrimerDesign.window, true);
            osb.setLocation(215,138);
            osb.setVisible(true);
        } catch(LowCountException e2) {
            LowCountErrorBox lceb = new LowCountErrorBox(PrimerDesign.window, true);
            lceb.setLocation(215, 138);
            lceb.setVisible(true);
        } catch(HighCountException e3) {
            HighCountErrorBox hceb = new HighCountErrorBox(PrimerDesign.window, true);
            hceb.setLocation(215, 138);
            hceb.setVisible(true);
        } catch(NException e4) {
            NSequenceBox nsb = new NSequenceBox(PrimerDesign.window, true);
            nsb.setLocation(215, 138);
            nsb.setVisible(true);
        }
        
    }//GEN-LAST:event_nextButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        PrimerDesign.window.remove(PrimerDesign.area);
        PrimerDesign.window.setVisible(false);
        
        PrimerDesign.window.getContentPane().add(PrimerDesign.start);
        PrimerDesign.window.pack();
        PrimerDesign.window.setVisible(true);
    }//GEN-LAST:event_backButtonActionPerformed

    /* this was a mistake, need to make some sort of keyeventlistener class
    public void keyTyped(KeyEvent e) {
        System.err.println("key event detected:");
        if (e.getSource().equals(fromTextField)) {
            from = Integer.parseInt(fromTextField.getText());
            if (to == 0) {
                to = from + 1;
            }
            try {
                high.addHighlight(from, to, DefaultHighlighter.DefaultPainter);
            } catch (BadLocationException ex) {
                Logger.getLogger(AreaSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource().equals(toTextField)) {
            to = Integer.parseInt(fromTextField.getText());
            try {
                high.addHighlight(from, to, DefaultHighlighter.DefaultPainter);
            } catch (BadLocationException ex) {
                Logger.getLogger(AreaSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JScrollPane cStrandScroll;
    private javax.swing.JTextPane cStrandTextPane;
    private javax.swing.JTabbedPane displayTabbedPane;
    private javax.swing.JLabel fromLabel;
    private javax.swing.JTextField fromTextField;
    private javax.swing.JTextPane instructionTextPane;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane lineAreaScroll;
    private javax.swing.JTextArea lineNumberTextArea;
    private javax.swing.JButton nextButton;
    private javax.swing.JScrollPane oStrandScroll;
    private javax.swing.JTextPane oStrandTextPane;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel toLabel;
    private javax.swing.JTextField toTextField;
    // End of variables declaration//GEN-END:variables
}
