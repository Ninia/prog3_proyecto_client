package ud.binmonkey.prog3_proyecto_client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SignUpForm {
    public JPanel mainSignUpPanel;
    public JButton LogInButton;
    public JPanel attributesPanel;
    public JPanel logInPanel;
    public JTextField usernameField;
    public JTextField displayNameField;
    public JPasswordField passwordField;
    public JTextField repeatPasswordField;
    public JLabel usernameLabel;
    public JLabel displayNameLabel;
    public JLabel passwordLabel;
    public JLabel repeatPasswordLabel;
    public JLabel compulsoryLabel;
    public JLabel optionalLabel;
    public JLabel emailLabel;
    public JTextField textField1;
    public JComboBox dayBox;
    public JComboBox monthBox;
    public JComboBox yearBox;
    public JLabel dayLabel;
    public JLabel monthLabel;
    public JLabel yearLabel;
    public JLabel genderLabel;
    public JTextField genderField;
    public JLabel usernameOKLabel;
    public JButton SignUpButton;
    public JLabel emailOKlabel;
    public JLabel passwordOKLabel;
    public JLabel repeatPasswordOkLabel;
    public JLabel displayNameInfoLabel;
    public JLabel birthDateOKLabel;
    public JPasswordField repeatField;

    public int[] month31 = new int[]{1, 3, 5, 7, 8, 10, 12};
    public int[] month30 = new int[]{4, 6, 9, 11};

    public SignUpForm() {
        try {
            MainWindow.INSTANCE.getFrame().setTitle("Sign Up");
        } catch (NullPointerException e) {
        }

        for (int i = 2000; i > 1910; i--) {
            yearBox.addItem(i);
        }
        yearBox.setSelectedIndex(0);
        /* yearBox.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null)); */

        for (int i = 1; i < 13; i++) {
            monthBox.addItem(i);
        }
        monthBox.setSelectedIndex(0);

        for (int i = 1; i < 32; i++) {
            dayBox.addItem(i);
        }
        dayBox.setSelectedIndex(0);


        LogInButton.addActionListener(actionEvent -> MainWindow.INSTANCE.getFrame().setForm(new LoginForm().mainLoginPanel));
        monthBox.addActionListener(actionEvent -> {
            if ((Integer) monthBox.getSelectedItem() == 2) {
                dayBox.removeAllItems();
                for (int i = 1; i < 29; i++) {
                    dayBox.addItem(i);
                }
                if ((Integer) yearBox.getSelectedItem() % 4 == 0) {
                    dayBox.addItem(29);
                }
            } else {
                for (int i : month30) {
                    if (i == (Integer) monthBox.getSelectedItem()) {
                        for (int j = 1; j < 30; j++) {
                            dayBox.addItem(j);
                        }
                    }
                }
                for (int i : month30) {
                    if (i == (Integer) monthBox.getSelectedItem()) {
                        for (int j = 1; j < 30; j++) {
                            dayBox.addItem(j);
                        }
                    }
                }
            }
            dayBox.setSelectedIndex(0);
        });
        yearBox.addActionListener(actionEvent -> {
            if ((Integer) monthBox.getSelectedItem() == 2) {
                dayBox.removeAllItems();
                for (int i = 1; i < 29; i++) {
                    dayBox.addItem(i);
                }
                if ((Integer) yearBox.getSelectedItem() % 4 == 0) {
                    dayBox.addItem(29);
                }
                dayBox.setSelectedIndex(0);
            } else {
                for (int i : month30) {
                    if (i == (Integer) monthBox.getSelectedItem()) {
                        for (int j = 1; j < 30; j++) {
                            dayBox.addItem(j);
                        }
                    }
                }
                for (int i : month31) {
                    if (i == (Integer) monthBox.getSelectedItem()) {
                        for (int j = 1; j < 30; j++) {
                            dayBox.addItem(j);
                        }
                    }
                }
            }
        });
        usernameField.addKeyListener(new KeyAdapter() {
        });

        repeatField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                super.keyTyped(keyEvent);
                if (repeatField.getPassword().equals(passwordField.getPassword())) {
                    repeatPasswordOkLabel.setText("passwords match");
                } else {
                    repeatPasswordOkLabel.setText("passwords don't match");
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sign Up");
        frame.setContentPane(new SignUpForm().mainSignUpPanel);
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
        mainSignUpPanel = new JPanel();
        mainSignUpPanel.setLayout(new BorderLayout(0, 0));
        logInPanel = new JPanel();
        logInPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainSignUpPanel.add(logInPanel, BorderLayout.SOUTH);
        final JLabel label1 = new JLabel();
        label1.setText("Already have an account?");
        logInPanel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        LogInButton = new JButton();
        LogInButton.setBackground(new Color(-16748629));
        LogInButton.setBorderPainted(false);
        LogInButton.setText("  Log In  ");
        logInPanel.add(LogInButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        logInPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        attributesPanel = new JPanel();
        attributesPanel.setLayout(new GridBagLayout());
        mainSignUpPanel.add(attributesPanel, BorderLayout.CENTER);
        usernameLabel = new JLabel();
        usernameLabel.setText("username");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.EAST;
        attributesPanel.add(usernameLabel, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        attributesPanel.add(spacer2, gbc);
        usernameField = new JTextField();
        usernameField.setColumns(12);
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        attributesPanel.add(usernameField, gbc);
        passwordLabel = new JLabel();
        passwordLabel.setText("password");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.EAST;
        attributesPanel.add(passwordLabel, gbc);
        passwordField = new JPasswordField();
        passwordField.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        attributesPanel.add(passwordField, gbc);
        repeatPasswordLabel = new JLabel();
        repeatPasswordLabel.setText("repeat password");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        attributesPanel.add(repeatPasswordLabel, gbc);
        compulsoryLabel = new JLabel();
        compulsoryLabel.setForeground(new Color(-9539986));
        compulsoryLabel.setText("compulsory");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        attributesPanel.add(compulsoryLabel, gbc);
        optionalLabel = new JLabel();
        optionalLabel.setForeground(new Color(-9539986));
        optionalLabel.setText("optional");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        attributesPanel.add(optionalLabel, gbc);
        displayNameField = new JTextField();
        displayNameField.setColumns(12);
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        attributesPanel.add(displayNameField, gbc);
        displayNameLabel = new JLabel();
        displayNameLabel.setText("display name");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 12;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.EAST;
        attributesPanel.add(displayNameLabel, gbc);
        emailLabel = new JLabel();
        emailLabel.setText("email");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.EAST;
        attributesPanel.add(emailLabel, gbc);
        textField1 = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        attributesPanel.add(textField1, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("birth date");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 14;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.EAST;
        attributesPanel.add(label2, gbc);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 14;
        gbc.fill = GridBagConstraints.BOTH;
        attributesPanel.add(panel1, gbc);
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
        genderLabel = new JLabel();
        genderLabel.setText("gender");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 16;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.EAST;
        attributesPanel.add(genderLabel, gbc);
        genderField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 16;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        attributesPanel.add(genderField, gbc);
        usernameOKLabel = new JLabel();
        usernameOKLabel.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        attributesPanel.add(usernameOKLabel, gbc);
        emailOKlabel = new JLabel();
        emailOKlabel.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 9;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        attributesPanel.add(emailOKlabel, gbc);
        passwordOKLabel = new JLabel();
        passwordOKLabel.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 9;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        attributesPanel.add(passwordOKLabel, gbc);
        repeatPasswordOkLabel = new JLabel();
        repeatPasswordOkLabel.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 9;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        attributesPanel.add(repeatPasswordOkLabel, gbc);
        displayNameInfoLabel = new JLabel();
        displayNameInfoLabel.setForeground(new Color(-10066330));
        displayNameInfoLabel.setText("shown instead of username");
        gbc = new GridBagConstraints();
        gbc.gridx = 9;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.WEST;
        attributesPanel.add(displayNameInfoLabel, gbc);
        birthDateOKLabel = new JLabel();
        birthDateOKLabel.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 9;
        gbc.gridy = 14;
        gbc.anchor = GridBagConstraints.WEST;
        attributesPanel.add(birthDateOKLabel, gbc);
        SignUpButton = new JButton();
        SignUpButton.setBackground(new Color(-16730824));
        SignUpButton.setText("Sign Up");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 18;
        attributesPanel.add(SignUpButton, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        attributesPanel.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        attributesPanel.add(spacer4, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        attributesPanel.add(spacer5, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        attributesPanel.add(spacer6, gbc);
        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        attributesPanel.add(spacer7, gbc);
        final JPanel spacer8 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        attributesPanel.add(spacer8, gbc);
        final JPanel spacer9 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        attributesPanel.add(spacer9, gbc);
        final JPanel spacer10 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        attributesPanel.add(spacer10, gbc);
        final JPanel spacer11 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 9;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        attributesPanel.add(spacer11, gbc);
        final JPanel spacer12 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 11;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        attributesPanel.add(spacer12, gbc);
        final JPanel spacer13 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 13;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        attributesPanel.add(spacer13, gbc);
        final JPanel spacer14 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 15;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        attributesPanel.add(spacer14, gbc);
        final JPanel spacer15 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 17;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        attributesPanel.add(spacer15, gbc);
        final JPanel spacer16 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        attributesPanel.add(spacer16, gbc);
        final JPanel spacer17 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        attributesPanel.add(spacer17, gbc);
        final JPanel spacer18 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        attributesPanel.add(spacer18, gbc);
        final JPanel spacer19 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        attributesPanel.add(spacer19, gbc);
        final JPanel spacer20 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 9;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        attributesPanel.add(spacer20, gbc);
        repeatField = new JPasswordField();
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        attributesPanel.add(repeatField, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainSignUpPanel;
    }
}
