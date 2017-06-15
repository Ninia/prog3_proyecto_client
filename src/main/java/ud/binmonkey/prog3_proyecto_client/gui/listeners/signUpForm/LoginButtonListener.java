package ud.binmonkey.prog3_proyecto_client.gui.listeners.signUpForm;

import ud.binmonkey.prog3_proyecto_client.gui.LoginForm;
import ud.binmonkey.prog3_proyecto_client.gui.MainWindow;
import ud.binmonkey.prog3_proyecto_client.gui.SignUpForm;

import java.awt.event.ActionEvent;

public class LoginButtonListener extends SignUpFormActionListener {

    public LoginButtonListener(SignUpForm signUpForm) {
        super(signUpForm);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        MainWindow.INSTANCE.getFrame().setForm(new LoginForm().mainLoginPanel);
    }
}
