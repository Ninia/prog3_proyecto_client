package ud.binmonkey.prog3_proyecto_client.gui.listeners.signUpForm;

import ud.binmonkey.prog3_proyecto_client.gui.SignUpForm;

public abstract class SignUpListener {

    private SignUpForm signUpForm;

    public SignUpListener(SignUpForm signUpForm) {
        this.setSignUpForm(signUpForm);
    }

    public SignUpForm getSignUpForm() {
        return signUpForm;
    }

    public void setSignUpForm(SignUpForm signUpForm) {
        this.signUpForm = signUpForm;
    }
}
