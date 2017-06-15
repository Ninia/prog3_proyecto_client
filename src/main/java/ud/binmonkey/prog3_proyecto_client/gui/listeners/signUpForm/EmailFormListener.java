package ud.binmonkey.prog3_proyecto_client.gui.listeners.signUpForm;

import ud.binmonkey.prog3_proyecto_client.common.Validator;
import ud.binmonkey.prog3_proyecto_client.gui.SignUpForm;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EmailFormListener extends KeyAdapter {

    private SignUpForm signUpForm;

    public EmailFormListener(SignUpForm signUpForm) {
        this.signUpForm = signUpForm;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        super.keyReleased(keyEvent);
        if (!Validator.validEmail(signUpForm.emailField.getText())) {
            signUpForm.emailOKlabel.setForeground(Color.RED);
            signUpForm.emailOKlabel.setText("not a valid email");
        } else {
            signUpForm.emailOKlabel.setForeground(Color.GREEN);
            signUpForm.emailOKlabel.setText("valid email!");
        }
    }
}
