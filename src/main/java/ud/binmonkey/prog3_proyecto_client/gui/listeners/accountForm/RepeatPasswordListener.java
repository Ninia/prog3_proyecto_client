package ud.binmonkey.prog3_proyecto_client.gui.listeners.accountForm;

import ud.binmonkey.prog3_proyecto_client.gui.AccountForm;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class RepeatPasswordListener extends KeyAdapter {
    private AccountForm accountForm;

    public RepeatPasswordListener(AccountForm accountForm) {
        this.accountForm = accountForm;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        super.keyTyped(keyEvent);
        if (Arrays.equals(accountForm.passwordField.getPassword(), accountForm.repeatPasswordField.getPassword())) {
            accountForm.repeatPasswordOKLabel.setText("passwords match");
            accountForm.repeatPasswordOKLabel.setForeground(Color.GREEN);
        } else {
            accountForm.repeatPasswordOKLabel.setText("passwords don't match");
            accountForm.repeatPasswordOKLabel.setForeground(Color.RED);
        }
    }
}
