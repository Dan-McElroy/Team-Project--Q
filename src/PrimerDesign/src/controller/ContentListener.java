
package controller;

import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;


/**
 *
 * @author 0901758b
 */
public class ContentListener implements DocumentListener {
    
    private JButton next;
    private JTextComponent textComp;
    
    public ContentListener(JButton button, JTextComponent textComponent){
        super();
        next = button;
        textComp = textComponent;
    }
            
    @Override
    public void insertUpdate(DocumentEvent e) {
        next.setEnabled(true);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (textComp.getText().isEmpty()) {
            next.setEnabled(false);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (textComp.getText().isEmpty()) {
            next.setEnabled(false);
        }
    }
}
