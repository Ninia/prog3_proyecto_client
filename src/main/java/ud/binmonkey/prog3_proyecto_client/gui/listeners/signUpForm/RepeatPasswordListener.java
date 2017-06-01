package ud.binmonkey.prog3_proyecto_client.gui.listeners.signUpForm;

import ud.binmonkey.prog3_proyecto_client.gui.SignUpForm;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class RepeatPasswordListener extends KeyAdapter {

    private SignUpForm signUpForm;

    public RepeatPasswordListener(SignUpForm signUpForm) {
        this.signUpForm = signUpForm;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        super.keyTyped(keyEvent);
        if (Arrays.equals(signUpForm.passwordField.getPassword(), signUpForm.repeatField.getPassword())) {
            signUpForm.repeatPasswordOkLabel.setText("passwords match");
        } else {
            signUpForm.repeatPasswordOkLabel.setText("passwords don't match");
        }
    }
}
