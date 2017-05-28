package ud.binmonkey.prog3_proyecto_client.gui.utils;

import javax.swing.*;
import java.awt.*;

public class UserInputDialog {

    private JDialog dialog;
    private JTextField inputTextField;

    private UserInputDialog(String title, JFrame frame) {
        dialog = new JDialog(frame, title, true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setMinimumSize(new Dimension(300, 120));
        init();
        dialog.setVisible(true);
    }

    public void init() {
        inputTextField = new JTextField();
        inputTextField.setColumns(12);
        JButton okButton = new JButton("OK");
        okButton.addActionListener(actionEvent -> dialog.dispose());
        dialog.setLayout(new GridLayout(2, 1, 5, 5));
        dialog.add(inputTextField);
        dialog.add(okButton);
        dialog.pack();
    }

    public void setVisible(boolean visible){
        dialog.setVisible(visible);
    }

    public static String getInput(String title, JFrame frame){
        UserInputDialog input = new UserInputDialog(title, frame);
        input.setVisible(true);
        String text = input.inputTextField.getText();
        return text;
    }
}
