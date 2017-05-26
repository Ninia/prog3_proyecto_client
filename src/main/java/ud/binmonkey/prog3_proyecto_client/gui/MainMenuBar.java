package ud.binmonkey.prog3_proyecto_client.gui;

import javax.swing.*;

/**
 * Menu bar that will be always present
 */
public class MainMenuBar extends JMenuBar{

    private JMenu sessionMenu;
    private JMenuItem tokenItem;
    private JMenuItem logoutItem;

    public MainMenuBar() {
        super();

        this.sessionMenu = new JMenu("Not logged in");
        this.tokenItem = new JMenuItem("token: none");
        this.logoutItem = new JMenuItem("Log out");

        this.sessionMenu.add(tokenItem);
        this.sessionMenu.addSeparator();
        this.sessionMenu.add(logoutItem);

        /* Listeners */
        logoutItem.addActionListener(actionEvent -> MainWindow.INSTANCE.Logout());

        this.add(sessionMenu);
    }

    public JMenu getSessionMenu() {
        return sessionMenu;
    }

    public void setSessionMenu(JMenu sessionMenu) {
        this.sessionMenu = sessionMenu;
    }

    public JMenuItem getTokenItem() {
        return tokenItem;
    }

    public void setTokenItem(JMenuItem tokenItem) {
        this.tokenItem = tokenItem;
    }

    public JMenuItem getLogoutItem() {
        return logoutItem;
    }

    public void setLogoutItem(JMenuItem logoutItem) {
        this.logoutItem = logoutItem;
    }
}
