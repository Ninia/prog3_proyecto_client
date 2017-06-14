package ud.binmonkey.prog3_proyecto_client.gui.utils;

import javax.swing.*;

public class Notifier {
    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }


    public static void main(String[] args) {
        showMessage("This is a message!");
    }
}
