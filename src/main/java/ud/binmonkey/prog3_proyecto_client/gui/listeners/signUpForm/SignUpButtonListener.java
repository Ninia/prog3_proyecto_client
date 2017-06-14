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

        if (!this.getSignUpForm().equalPasswords(
                this.getSignUpForm().passwordField.getPassword(), this.getSignUpForm().repeatField.getPassword())) {
            this.getSignUpForm().repeatPasswordOkLabel.setForeground(Color.RED);
            this.getSignUpForm().repeatPasswordOkLabel.setText("passwords don't match");
            return;
        }

        if (!Validator.validEmail(this.getSignUpForm().emailField.getText())) {
            this.getSignUpForm().emailOKlabel.setForeground(Color.RED);
            this.getSignUpForm().emailOKlabel.setText("not a valid email");
            return;
        }

        String username = this.getSignUpForm().usernameField.getText();
        char[] password = this.getSignUpForm().passwordField.getPassword();
        String email = this.getSignUpForm().emailField.getText();
        String display_name = this.getSignUpForm().displayNameField.getText();
        String gender = this.getSignUpForm().genderField.getText();
        String preferred_lang = this.getSignUpForm().langTextField.getText();
        String birthdate = null;

        if (this.getSignUpForm().dayBox.getSelectedItem() != null &&
                this.getSignUpForm().monthBox.getSelectedItem() != null &&
                this.getSignUpForm().yearBox.getSelectedItem() != null) {
            birthdate = this.getSignUpForm().dayBox.getSelectedItem() + "-"
                    + this.getSignUpForm().monthBox.getSelectedItem() + "-"
                    + this.getSignUpForm().yearBox.getSelectedItem();
        }

        HTTPSClient client = new HTTPSClient();
        Response response = client.signUp(username, password, display_name, email, birthdate, gender, preferred_lang);

        /*
         * TODO: does @HTTPSClient.signUp return null even if response was provided?
         */
        if (response != null) {
            if (response.getContent().matches("Username [\\w|\\d]+ already found.")) {
                this.getSignUpForm().usernameOKLabel.setForeground(Color.RED);
                this.getSignUpForm().usernameOKLabel.setText("username already taken");
            } else {
               /* success */
                this.getSignUpForm().infoLabel.setText("Success! log in to your new account:");
            }
        } else {
            this.getSignUpForm().infoLabel.setText("unable to create account");
        }
    }
}
