package ud.binmonkey.prog3_proyecto_client.gui;

import ud.binmonkey.prog3_proyecto_client.common.Validator;
import ud.binmonkey.prog3_proyecto_client.https.HTTPSClient;
import ud.binmonkey.prog3_proyecto_client.https.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings({"WeakerAccess", "CodeBlock2Expr"})
public class LoginForm {
    public JPanel mainLoginPanel;
    public JPanel attributesPanel;
    public JTextField usernameField;
    public JPasswordField passwordFiled;
    public JLabel usernameOKLabel;
    public JButton createAccountButton;
    public JLabel createAccountLabel;
    public JPanel createAccountPanel;
    public JButton loginButton;

    public LoginForm() {

        /* set title */
        try {
            MainWindow.INSTANCE.getFrame().setTitle("Log In");
        } catch (NullPointerException e) {
            /* Expected to happen at creation of @MainFrame */
        }

        /* check validity of username */
        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                super.keyTyped(keyEvent);
                if (Validator.validName(usernameField.getText().toLowerCase())) {
                    usernameOKLabel.setForeground(Color.GREEN);
                    usernameOKLabel.setText("valid name!");
                } else {
                    usernameOKLabel.setForeground(Color.RED);
                    usernameOKLabel.setText("invalid username");
                }
            }
        });

        /* call @HTTPSClient.logIn method; if success launch @HomeForm*/
        loginButton.addActionListener(actionEvent -> {
            HTTPSClient client = new HTTPSClient();

            String username = usernameField.getText().toLowerCase();
            char[] password = passwordFiled.getPassword();

            Response response = client.login(username, new String(password));

            try {
                /* username does not exist */
                if (response.getContent().matches("Username [\\w|\\d]+ not found.")) {
                    usernameOKLabel.setForeground(Color.RED);
                    usernameOKLabel.setText("username not found");
                    return;
                }
            } catch (NullPointerException e) {
                usernameOKLabel.setForeground(Color.RED);
                usernameOKLabel.setText("unable to log in");
                return;
            }

            String token = response.getContent();
            if (token != null) {
                usernameOKLabel.setForeground(Color.BLUE);
                usernameOKLabel.setText("Logged in!");
                MainWindow.INSTANCE.getFrame().setLogged(true);
                MainWindow.INSTANCE.getFrame().setSession(username, password, token);
                MainWindow.INSTANCE.getFrame().setForm(new HomeForm().mainHomePanel);
            } else {
                usernameOKLabel.setForeground(Color.RED);
                usernameOKLabel.setText("unable to log in");
            }
        });
        createAccountButton.addActionListener(actionEvent -> {
            MainWindow.INSTANCE.getFrame().setForm(new SignUpForm().mainSignUpPanel);
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new LoginForm().mainLoginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
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
        mainLoginPanel = new JPanel();
        mainLoginPanel.setLayout(new BorderLayout(0, 0));
        attributesPanel = new JPanel();
        attributesPanel.setLayout(new GridBagLayout());
        attributesPanel.setEnabled(true);
        attributesPanel.setOpaque(false);
        mainLoginPanel.add(attributesPanel, BorderLayout.CENTER);
        final JLabel label1 = new JLabel();
        label1.setHorizontalAlignment(4);
        label1.setText("username");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        attributesPanel.add(label1, gbc);
        usernameField = new JTextField();
        usernameField.setColumns(12);
        usernameField.setHorizontalAlignment(2);
        usernameField.setMargin(new Insets(0, 0, 0, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        attributesPanel.add(usernameField, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("password");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        attributesPanel.add(label2, gbc);
        passwordFiled = new JPasswordField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        attributesPanel.add(passwordFiled, gbc);
        usernameOKLabel = new JLabel();
        usernameOKLabel.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        attributesPanel.add(usernameOKLabel, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        attributesPanel.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        attributesPanel.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        attributesPanel.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        attributesPanel.add(spacer4, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.VERTICAL;
        attributesPanel.add(spacer5, gbc);
        loginButton = new JButton();
        loginButton.setBackground(new Color(-16748629));
        loginButton.setText("Login");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        attributesPanel.add(loginButton, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.VERTICAL;
        attributesPanel.add(spacer6, gbc);
        createAccountPanel = new JPanel();
        createAccountPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainLoginPanel.add(createAccountPanel, BorderLayout.SOUTH);
        createAccountLabel = new JLabel();
        createAccountLabel.setText("Don't have an account?");
        createAccountPanel.add(createAccountLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        createAccountButton = new JButton();
        createAccountButton.setBackground(new Color(-16730824));
        createAccountButton.setBorderPainted(false);
        createAccountButton.setContentAreaFilled(true);
        createAccountButton.setHorizontalTextPosition(0);
        createAccountButton.setText("Create one");
        createAccountPanel.add(createAccountButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer7 = new com.intellij.uiDesigner.core.Spacer();
        createAccountPanel.add(spacer7, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainLoginPanel;
    }
}
