package ud.binmonkey.prog3_proyecto_client.gui.listeners.accountForm;

import ud.binmonkey.prog3_proyecto_client.gui.AccountForm;
import ud.binmonkey.prog3_proyecto_client.gui.MainWindow;
import ud.binmonkey.prog3_proyecto_client.https.HTTPSClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveButtonListener implements ActionListener {

    protected AccountForm accountForm;

    public SaveButtonListener(AccountForm accountForm) {
        this.accountForm = accountForm;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            HTTPSClient client = new HTTPSClient();

            if (accountForm.passwordField.getPassword().length > 1 &&
                    accountForm.passwordField.getPassword().equals(
                            accountForm.repeatPasswordField.getPassword()
                    ))
            {
                if (client.changePassword(
                        MainWindow.INSTANCE.getFrame().getUser(),
                        MainWindow.INSTANCE.getFrame().getToken(),
                        new String(MainWindow.INSTANCE.getFrame().getPassword()),
                        new String(accountForm.passwordField.getPassword())
                ) == null) {
                    throw new Exception();
                }
            }

            if (!accountForm.displayNameField.getText().equals(accountForm.userData.get("display_name").toString())) {
                if (client.changeProperty(
                        MainWindow.INSTANCE.getFrame().getUser(),
                        "display_name",
                        accountForm.displayNameField.getText(),
                        MainWindow.INSTANCE.getFrame().getToken()
                ) == null) {
                    throw new Exception();
                }
            }

            if (!accountForm.emailField.getText().equals(accountForm.userData.get("email").toString())) {
                if (client.changeProperty(
                        MainWindow.INSTANCE.getFrame().getUser(),
                        "email",
                        accountForm.emailField.getText(),
                        MainWindow.INSTANCE.getFrame().getToken()
                ) == null) {
                    throw new Exception();
                }
            }

            if (!accountForm.preferredLangField.getText().equals(accountForm.userData.get("preferred_language").toString())) {

                if (client.changeProperty(
                        MainWindow.INSTANCE.getFrame().getUser(),
                        "preferred_language",
                        accountForm.preferredLangField.getText(),
                        MainWindow.INSTANCE.getFrame().getToken()
                ) == null) {
                    throw new Exception();
                }
            }

            if (!accountForm.genderField.getText().equals(accountForm.userData.get("gender").toString())) {
                if (client.changeProperty(
                        MainWindow.INSTANCE.getFrame().getUser(),
                        "gender",
                        accountForm.genderField.getText(),
                        MainWindow.INSTANCE.getFrame().getToken()
                ) == null) {
                    throw new Exception();
                }
            }

            if (accountForm.dayBox.getSelectedItem() != null &&
                    accountForm.monthBox.getSelectedItem() != null &&
                    accountForm.yearBox.getSelectedItem() != null) {

                String birthdate = accountForm.dayBox.getSelectedItem() + "-"
                        + accountForm.monthBox.getSelectedItem() + "-"
                        + accountForm.yearBox.getSelectedItem();

                String[] dateComponents = birthdate.split("-");

                for (int i = 0; i < dateComponents.length; i++ ) {
                    if (dateComponents[i].length() < 2) {
                        dateComponents[i] = "0" + dateComponents[i];
                    }
                }

                birthdate = dateComponents[0] + "-" + dateComponents[1] + "-" + dateComponents[2];

                if (client.changeProperty(
                        MainWindow.INSTANCE.getFrame().getUser(),
                        "birth_date",
                        birthdate,
                        MainWindow.INSTANCE.getFrame().getToken()
                ) == null) {
                    throw new Exception();
                }
            }



            accountForm.infoLabel.setText("Update successful");
        } catch (Exception e) {
            e.printStackTrace();
            accountForm.infoLabel.setText("Error updating account information");
        }
    }
}
