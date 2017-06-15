package ud.binmonkey.prog3_proyecto_client.gui.listeners.accountForm;

import ud.binmonkey.prog3_proyecto_client.common.Validator;
import ud.binmonkey.prog3_proyecto_client.gui.AccountForm;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EmailFieldListener extends KeyAdapter {
    private AccountForm accountForm;

    public EmailFieldListener(AccountForm accountForm) {
        this.accountForm = accountForm;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        super.keyReleased(keyEvent);
        if (!Validator.validEmail(accountForm.emailField.getText())) {
            accountForm.emailOKLabel.setForeground(Color.RED);
            accountForm.emailOKLabel.setText("not a valid email");
        } else {
            accountForm.emailOKLabel.setForeground(Color.GREEN);
            accountForm.emailOKLabel.setText("valid email!");
        }
    }
}
