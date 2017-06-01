package ud.binmonkey.prog3_proyecto_client.gui.listeners.loginForm;

import ud.binmonkey.prog3_proyecto_client.gui.LoginForm;
import ud.binmonkey.prog3_proyecto_client.gui.MainWindow;
import ud.binmonkey.prog3_proyecto_client.gui.SignUpForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccountListener extends LoginFormListener implements ActionListener {

    public CreateAccountListener(LoginForm loginForm) {
        super(loginForm);
    }

    /**
     * switch to @SignUpForm
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        MainWindow.INSTANCE.getFrame().setForm(new SignUpForm().mainSignUpPanel);
    }
}
