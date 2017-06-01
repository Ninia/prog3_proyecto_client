package ud.binmonkey.prog3_proyecto_client.gui.listeners.signUpForm;

import ud.binmonkey.prog3_proyecto_client.gui.SignUpForm;

import java.awt.event.ActionListener;

public abstract class SignUpFormActionListener extends SignUpListener implements ActionListener {

    public SignUpFormActionListener(SignUpForm signUpForm) {
        super(signUpForm);
    }
}
