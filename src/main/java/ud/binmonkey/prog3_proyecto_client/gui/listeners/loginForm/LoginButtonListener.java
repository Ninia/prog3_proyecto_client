package ud.binmonkey.prog3_proyecto_client.gui.listeners.loginForm;

import ud.binmonkey.prog3_proyecto_client.gui.HomeForm;
import ud.binmonkey.prog3_proyecto_client.gui.LoginForm;
import ud.binmonkey.prog3_proyecto_client.gui.MainWindow;
import ud.binmonkey.prog3_proyecto_client.https.HTTPSClient;
import ud.binmonkey.prog3_proyecto_client.https.Response;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginButtonListener extends LoginFormListener implements ActionListener {

    public LoginButtonListener(LoginForm loginForm) {
        super(loginForm);
    }

    /**
     * call @HTTPSClient.logIn method; if success launch @HomeForm
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        HTTPSClient client = new HTTPSClient();

        String username = this.getLoginForm().usernameField.getText().toLowerCase();
        char[] password = this.getLoginForm().passwordFiled.getPassword();

        Response response = client.login(username, new String(password));

        try {
                /* username does not exist */
            if (response.getContent().matches("Username [\\w|\\d]+ not found.")) {
                this.getLoginForm().usernameOKLabel.setForeground(Color.RED);
                this.getLoginForm().usernameOKLabel.setText("username not found");
                return;
            }
        } catch (NullPointerException e) {
            this.getLoginForm().usernameOKLabel.setForeground(Color.RED);
            this.getLoginForm().usernameOKLabel.setText("unable to log in");
            return;
        }

        String token = response.getContent();
        if (token != null) {
            this.getLoginForm().usernameOKLabel.setForeground(Color.BLUE);
            this.getLoginForm().usernameOKLabel.setText("Logged in!");
            MainWindow.INSTANCE.getFrame().setLogged(true);
            MainWindow.INSTANCE.getFrame().setSession(username, password, token);
            MainWindow.INSTANCE.getFrame().setForm(new HomeForm().mainHomePanel);
            MainWindow.INSTANCE.getFrame().getMainMenuBar().logIn();
        } else {
            this.getLoginForm().usernameOKLabel.setForeground(Color.RED);
            this.getLoginForm().usernameOKLabel.setText("unable to log in");
        }
    }
}
