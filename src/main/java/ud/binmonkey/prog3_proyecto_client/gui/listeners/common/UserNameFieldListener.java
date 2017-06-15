package ud.binmonkey.prog3_proyecto_client.gui.listeners.common;

import ud.binmonkey.prog3_proyecto_client.common.Validator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UserNameFieldListener extends KeyAdapter implements KeyListener {

    private JTextField field;
    private JLabel label;

    /**
     * Displays in @label whether name in @field is valid
     * @param field field of panel
     * @param label label of panel
     */
    public UserNameFieldListener(JTextField field, JLabel label) {
        this.field = field;
        this.label = label;
    }

    /**
     * check and display validity of username
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        super.keyTyped(keyEvent);
        if (Validator.validName(field.getText().toLowerCase())) {
            label.setForeground(Color.GREEN);
            label.setText("valid name!");
        } else {
            label.setForeground(Color.RED);
            label.setText("invalid username");
        }
    }
}
