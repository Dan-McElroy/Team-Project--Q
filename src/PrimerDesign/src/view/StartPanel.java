/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.PrimerDesign;
import javax.swing.JLabel;
import model.*;

/**
 *
 * @author 0901758b
 */
public class StartPanel extends javax.swing.JPanel {

    private String input;
    private Sequence inSequence;
    
    public String getInput(){
        return input;
    }
    
    public Sequence getInSequence(){
        return inSequence;
    }
    
    /**
     * Creates new form startPanel
     */
    public StartPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialise the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nextButton = new javax.swing.JButton();
        titleLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        instructionTextPane = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        sequenceTextArea = new javax.swing.JTextArea();

        setPreferredSize(new java.awt.Dimension(800, 600));

        nextButton.setText("Next/Go");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        titleLabel.setFont(new java.awt.Font("DejaVu Sans", 0, 24)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Primer Design Stage 1");

        instructionTextPane.setEditable(false);
        instructionTextPane.setText("Copy and Paste the sequence in the box below");
        jScrollPane2.setViewportView(instructionTextPane);

        sequenceTextArea.setColumns(20);
        sequenceTextArea.setFont(new java.awt.Font("DejaVu LGC Sans Mono", 0, 13)); // NOI18N
        sequenceTextArea.setRows(5);
        jScrollPane4.setViewportView(sequenceTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(nextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(titleLabel)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nextButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        // set input to whatever has been copied into the text area
        input = sequenceTextArea.getText();
        inSequence = new Sequence(input);
        
        // remove current panel from window
        PrimerDesign.window.remove(PrimerDesign.start);
        PrimerDesign.window.setVisible(false);
        
        // add next panel to window and display
        PrimerDesign.area = new AreaSelection();
        PrimerDesign.window.getContentPane().add(PrimerDesign.area);
        PrimerDesign.window.pack();
        PrimerDesign.window.setVisible(true);
    }//GEN-LAST:event_nextButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane instructionTextPane;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton nextButton;
    private javax.swing.JTextArea sequenceTextArea;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
