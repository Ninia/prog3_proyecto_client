package ud.binmonkey.prog3_proyecto_client.gui.listeners.loginForm;

import ud.binmonkey.prog3_proyecto_client.gui.LoginForm;

public abstract class LoginFormListener {

    protected LoginForm loginForm;

    public LoginFormListener(LoginForm loginForm) {
        this.loginForm = loginForm;
    }
}
