package ud.binmonkey.prog3_proyecto_client.gui.listeners.signUpForm;

import ud.binmonkey.prog3_proyecto_client.common.Validator;
import ud.binmonkey.prog3_proyecto_client.gui.SignUpForm;
import ud.binmonkey.prog3_proyecto_client.https.HTTPSClient;
import ud.binmonkey.prog3_proyecto_client.https.Response;

import java.awt.*;
import java.awt.event.ActionEvent;

public class SignUpButtonListener extends SignUpFormActionListener {

    public SignUpButtonListener(SignUpForm signUpForm) {
        super(signUpForm);
    }

    /**
     * check correctness of fields, call @HTTPSClient.sinUp method
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (!signUpForm.equalPasswords(
                signUpForm.passwordField.getPassword(), signUpForm.repeatField.getPassword())) {
            signUpForm.repeatPasswordOkLabel.setForeground(Color.RED);
            signUpForm.repeatPasswordOkLabel.setText("passwords don't match");
            return;
        }

        if (!Validator.validEmail(signUpForm.emailField.getText())) {
            signUpForm.emailOKlabel.setForeground(Color.RED);
            signUpForm.emailOKlabel.setText("not a valid email");
            return;
        }

        String username = signUpForm.usernameField.getText();
        char[] password = signUpForm.passwordField.getPassword();
        String email = signUpForm.emailField.getText();
        String display_name = signUpForm.displayNameField.getText();
        String gender = signUpForm.genderField.getText();
        String preferred_lang = signUpForm.langTextField.getText();
        String birthdate = null;

        if (signUpForm.dayBox.getSelectedItem() != null && signUpForm.monthBox.getSelectedItem() != null &&
                signUpForm.yearBox.getSelectedItem() != null) {
            birthdate = signUpForm.dayBox.getSelectedIndex() + ""
                    + signUpForm.monthBox.getSelectedItem() + ""
                    + signUpForm.yearBox.getSelectedItem();
        }

        HTTPSClient client = new HTTPSClient();
        Response response = client.signUp(username, password, display_name, email, birthdate, gender, preferred_lang);

        /*
         * TODO: does @HTTPSClient.signUp return null even if response was provided?
         */
        if (response != null) {
            if (response.getContent().matches("Username [\\w|\\d]+ already found.")) {
                signUpForm.usernameOKLabel.setForeground(Color.RED);
                signUpForm.usernameOKLabel.setText("username already taken");
            } else {
               /* success */
                signUpForm.infoLabel.setText("Success! log in to your new account:");
            }
        } else {
            signUpForm.infoLabel.setText("unable to create account");
        }
    }
}
