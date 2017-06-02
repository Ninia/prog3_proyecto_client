package ud.binmonkey.prog3_proyecto_client.gui.listeners.loginForm;

import ud.binmonkey.prog3_proyecto_client.gui.LoginForm;

public abstract class LoginFormListener {

    private LoginForm loginForm;

    public LoginFormListener(LoginForm loginForm) {
        this.setLoginForm(loginForm);
    }

    public LoginForm getLoginForm() {
        return loginForm;
    }

    public void setLoginForm(LoginForm loginForm) {
        this.loginForm = loginForm;
    }
}
