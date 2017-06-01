package ud.binmonkey.prog3_proyecto_client.gui.listeners.signUpForm;

import ud.binmonkey.prog3_proyecto_client.gui.SignUpForm;

public abstract class SignUpListener {

    protected SignUpForm signUpForm;

    public SignUpListener(SignUpForm signUpForm) {
        this.signUpForm = signUpForm;
    }
}
