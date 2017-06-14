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
                client.changePassword(
                        MainWindow.INSTANCE.getFrame().getUser(),
                        MainWindow.INSTANCE.getFrame().getToken(),
                        new String(MainWindow.INSTANCE.getFrame().getPassword()),
                        new String(accountForm.passwordField.getPassword())
                );
            }

            if (!accountForm.displayNameField.getText().equals(accountForm.userData.get("display_name").toString())) {
                client.changeProperty(
                        MainWindow.INSTANCE.getFrame().getUser(),
                        "display_name",
                        accountForm.displayNameField.getText(),
                        MainWindow.INSTANCE.getFrame().getToken()
                );
            }

            if (!accountForm.emailField.getText().equals(accountForm.userData.get("email").toString())) {
                client.changeProperty(
                        MainWindow.INSTANCE.getFrame().getUser(),
                        "email",
                        accountForm.emailField.getText(),
                        MainWindow.INSTANCE.getFrame().getToken()
                );
            }

            if (!accountForm.preferredLangField.getText().equals(accountForm.userData.get("preferred_language").toString())) {
                client.changeProperty(
                        MainWindow.INSTANCE.getFrame().getUser(),
                        "preferred_language",
                        accountForm.preferredLangField.getText(),
                        MainWindow.INSTANCE.getFrame().getToken()
                );
            }

            if (!accountForm.genderField.getText().equals(accountForm.userData.get("gender").toString())) {
                client.changeProperty(
                        MainWindow.INSTANCE.getFrame().getUser(),
                        "gender",
                        accountForm.genderField.getText(),
                        MainWindow.INSTANCE.getFrame().getToken()
                );
            }

            if (accountForm.dayBox.getSelectedItem() != null &&
                    accountForm.monthBox.getSelectedItem() != null &&
                    accountForm.yearBox.getSelectedItem() != null) {

                String birthdate = accountForm.dayBox.getSelectedItem() + "-"
                        + accountForm.monthBox.getSelectedItem() + "-"
                        + accountForm.yearBox.getSelectedItem();
                System.out.println(birthdate);
            }



            accountForm.infoLabel.setText("Update successful");
        } catch (Exception e) {
            e.printStackTrace();
            accountForm.infoLabel.setText("Error updating account information");
        }
    }
}
