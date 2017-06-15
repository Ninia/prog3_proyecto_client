package ud.binmonkey.prog3_proyecto_client.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.json.JSONObject;
import ud.binmonkey.prog3_proyecto_client.gui.listeners.accountForm.EmailFieldListener;
import ud.binmonkey.prog3_proyecto_client.gui.listeners.accountForm.RepeatPasswordListener;
import ud.binmonkey.prog3_proyecto_client.gui.listeners.accountForm.SaveButtonListener;
import ud.binmonkey.prog3_proyecto_client.gui.listeners.common.UserNameFieldListener;
import ud.binmonkey.prog3_proyecto_client.https.HTTPSClient;

import javax.swing.*;
import java.awt.*;

public class AccountForm {
    public JPanel mainAccountPanel;
    public JLabel userNameLabel;
    public JLabel repeatPasswordLabel;
    public JLabel password;
    public JLabel displayNameLabel;
    public JTextField usernameField;
    public JPasswordField passwordField;
    public JPasswordField repeatPasswordField;
    public JTextField displayNameField;
    public JLabel emailLabel;
    public JTextField emailField;
    public JLabel preferredLangLabel;
    public JTextField preferredLangField;
    public JLabel genderLabel;
    public JTextField genderField;
    public JLabel birthdateLabel;
    public JLabel dayLabel;
    public JComboBox dayBox;
    public JLabel monthLabel;
    public JComboBox monthBox;
    public JLabel yearLabel;
    public JComboBox yearBox;
    public JLabel roleLabel;
    public JTextField roleField;
    public JLabel usernameOKLabel;
    public JLabel repeatPasswordOKLabel;
    public JLabel emailOKLabel;
    public JPanel buttonPanel;
    public JButton discardButton;
    public JButton saveButton;
    public JLabel infoLabel;
    public JSONObject userData;

    public int[] month31 = new int[]{1, 3, 5, 7, 8, 10, 12};
    public int[] month30 = new int[]{4, 6, 9, 11};

    public AccountForm() {
        userData = new JSONObject(
                new HTTPSClient().userInfo(
                        MainWindow.INSTANCE.getFrame().getUser(),
                        MainWindow.INSTANCE.getFrame().getToken())
        );

        build(userData);
        usernameField.addKeyListener(new UserNameFieldListener(usernameField, userNameLabel));
        emailField.addKeyListener(new EmailFieldListener(this));
        repeatPasswordField.addKeyListener(new RepeatPasswordListener(this));

        discardButton.addActionListener(actionEvent -> build(userData));

        saveButton.addActionListener(new SaveButtonListener(this));
    }

    /* sets day numbers depending on month, feb has 29 days if year % 4 == 0 */
    @SuppressWarnings("Duplicates")
    public void reloadDaysMonths() {
        dayBox.removeAllItems();
        if ((Integer) monthBox.getSelectedItem() == 2) {
            for (int i = 1; i < 29; i++) {
                dayBox.addItem(i);
            }
            if ((Integer) yearBox.getSelectedItem() % 4 == 0) {
                dayBox.addItem(29);
            }
        } else {
            for (int i : month30) {
                if (i == (Integer) monthBox.getSelectedItem()) {
                    for (int j = 1; j < 31; j++) {
                        dayBox.addItem(j);
                    }
                    return;
                }
            }
            for (int i : month31) {
                if (i == (Integer) monthBox.getSelectedItem()) {
                    for (int j = 1; j < 32; j++) {
                        dayBox.addItem(j);
                    }
                    return;
                }
            }
        }
    }

    public void build(JSONObject userData) {
                /* calendar settings */
        for (int i = 2000; i > 1910; i--) {
            yearBox.addItem(i);
        }
        yearBox.setSelectedIndex(0);

        for (int i = 1; i < 13; i++) {
            monthBox.addItem(i);
        }
        monthBox.setSelectedIndex(0);

        for (int i = 1; i < 32; i++) {
            dayBox.addItem(i);
        }
        dayBox.setSelectedIndex(0);

        /* compulsory */
        try {
            usernameField.setText(userData.get("username").toString());
        } catch (NullPointerException e) {
            return;
        }

        /* optional */
        try {
            emailField.setText(userData.get("email").toString());
        } catch (NullPointerException e) {
        }

        try {
            displayNameField.setText(userData.get("display_name").toString());
        } catch (NullPointerException e) {
        }

        try {
            preferredLangField.setText(userData.get("preferred_language").toString());
        } catch (NullPointerException e) {
        }

        try {
            genderField.setText(userData.get("gender").toString());
        } catch (NullPointerException e) {
        }

        try {
            roleField.setText(userData.get("role").toString());
        } catch (NullPointerException e) {
        }

        try {

            String[] date = userData.get("birth_date").toString().split("-");
            int day = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int year = Integer.parseInt(date[2]);

            yearBox.setSelectedItem(year);
            monthBox.setSelectedItem(month);
            dayBox.setSelectedItem(day);

        } catch (NullPointerException e) {
        }
    }

    public static void main(String[] args) {

    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainAccountPanel = new JPanel();
        mainAccountPanel.setLayout(new GridLayoutManager(12, 5, new Insets(0, 0, 0, 0), -1, -1));
        userNameLabel = new JLabel();
        userNameLabel.setText("username: ");
        mainAccountPanel.add(userNameLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        password = new JLabel();
        password.setText("password: ");
        mainAccountPanel.add(password, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        repeatPasswordLabel = new JLabel();
        repeatPasswordLabel.setText("repeat password: ");
        mainAccountPanel.add(repeatPasswordLabel, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        displayNameLabel = new JLabel();
        displayNameLabel.setText("display name: ");
        mainAccountPanel.add(displayNameLabel, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        usernameField = new JTextField();
        usernameField.setEditable(false);
        mainAccountPanel.add(usernameField, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        passwordField = new JPasswordField();
        mainAccountPanel.add(passwordField, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        repeatPasswordField = new JPasswordField();
        mainAccountPanel.add(repeatPasswordField, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        displayNameField = new JTextField();
        mainAccountPanel.add(displayNameField, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        emailLabel = new JLabel();
        emailLabel.setText("email: ");
        mainAccountPanel.add(emailLabel, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        emailField = new JTextField();
        mainAccountPanel.add(emailField, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        preferredLangLabel = new JLabel();
        preferredLangLabel.setText("preferred langauge: ");
        mainAccountPanel.add(preferredLangLabel, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        preferredLangField = new JTextField();
        mainAccountPanel.add(preferredLangField, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        genderLabel = new JLabel();
        genderLabel.setText("gender: ");
        mainAccountPanel.add(genderLabel, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        genderField = new JTextField();
        mainAccountPanel.add(genderField, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        birthdateLabel = new JLabel();
        birthdateLabel.setText("birthdate: ");
        mainAccountPanel.add(birthdateLabel, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        mainAccountPanel.add(panel1, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        dayLabel = new JLabel();
        dayLabel.setText("day");
        panel1.add(dayLabel);
        dayBox = new JComboBox();
        panel1.add(dayBox);
        monthLabel = new JLabel();
        monthLabel.setText("month");
        panel1.add(monthLabel);
        monthBox = new JComboBox();
        panel1.add(monthBox);
        yearLabel = new JLabel();
        yearLabel.setText("year");
        panel1.add(yearLabel);
        yearBox = new JComboBox();
        panel1.add(yearBox);
        final Spacer spacer1 = new Spacer();
        mainAccountPanel.add(spacer1, new GridConstraints(11, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        mainAccountPanel.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        mainAccountPanel.add(spacer3, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        roleLabel = new JLabel();
        roleLabel.setText("role: ");
        mainAccountPanel.add(roleLabel, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        roleField = new JTextField();
        roleField.setEditable(false);
        roleField.setEnabled(false);
        mainAccountPanel.add(roleField, new GridConstraints(8, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        usernameOKLabel = new JLabel();
        usernameOKLabel.setText("");
        mainAccountPanel.add(usernameOKLabel, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        repeatPasswordOKLabel = new JLabel();
        repeatPasswordOKLabel.setText("");
        mainAccountPanel.add(repeatPasswordOKLabel, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        emailOKLabel = new JLabel();
        emailOKLabel.setText("");
        mainAccountPanel.add(emailOKLabel, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        mainAccountPanel.add(buttonPanel, new GridConstraints(10, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        discardButton = new JButton();
        discardButton.setText("Reset");
        buttonPanel.add(discardButton);
        saveButton = new JButton();
        saveButton.setText("Save");
        buttonPanel.add(saveButton);
        infoLabel = new JLabel();
        infoLabel.setText("");
        mainAccountPanel.add(infoLabel, new GridConstraints(9, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainAccountPanel;
    }
}
