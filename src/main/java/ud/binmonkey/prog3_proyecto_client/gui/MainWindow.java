package ud.binmonkey.prog3_proyecto_client.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Main class containing the main frame and all the Swing elements.
 */
public enum MainWindow {
    INSTANCE;

    private MainFrame frame;

    /**
     * Main frame of the GUI
     */
    public static class MainFrame extends JFrame {

        private boolean logged;
        private String token;
        private String user;
        private char[] password;
        private JMenuBar menuBar = new MainMenuBar();
        private Component lastComponent;

        /**
         * Creates new Frame
         * Default form is @LoginForm
         */
        public MainFrame() throws HeadlessException {
            super("Client");
            this.setJMenuBar(menuBar);
            super.setContentPane(new MainPanel());
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setMinimumSize(new Dimension(800, 600));
            this.setLastComponent(new LoginForm().mainLoginPanel);
            getContentPane().add(getLastComponent(), BorderLayout.CENTER);
        }

        /**
         * Use this to change from one Form to another
         * @param panel panel to switch to
         */
        public void setForm(JPanel panel) {
            this.getContentPane().remove(lastComponent);
            this.setLastComponent(panel);
            this.getContentPane().add(panel, BorderLayout.CENTER);
            this.pack();
            this.repaint();
        }

        /*
        GETTERS AND SETTERS
         */

        public boolean isLogged() {
            return logged;
        }

        public void setLogged(boolean logged) {
            this.logged = logged;
        }

        public char[] getPassword() {
            return password;
        }

        /**
         * Access only from setSession
         * @param password password of user, will only be used to access FTP
         */
        public void setPassword(char[] password) {
            this.password = password;
        }

        public String getToken() {
            return token;
        }

        /**
         * Access only from setSession
         * @param token token assigned by server
         */
        private void setToken(String token) {
            this.token = token;
            MainMenuBar menuBar = (MainMenuBar) this.getJMenuBar();
            menuBar.getTokenItem().setText(token);
        }

        private String getUser() {
            return user;
        }

        /**
         * USE this one when login happens, @MainWindow.Logout when logout happens
         * @param user
         * @param password
         * @param token
         */
        public void setSession(String user, char[] password, String token) {
            this.setUser(user);
            this.setPassword(password);
            this.setToken(token);
        }

        /**
         * Access only from setSession
         * @param user username of user
         */
        private void setUser(String user) {
            MainMenuBar menuBar = (MainMenuBar) this.getJMenuBar();
            menuBar.getSessionMenu().setText(user);
            this.user = user;
        }

        public Component getLastComponent() {
            return lastComponent;
        }

        public void setLastComponent(Component lastComponent) {
            this.lastComponent = lastComponent;
        }
    }

    /*
    END OF @MainFrame class
     */

    /**
     * Resets window
     */
    public void Logout() {
        if (this.getFrame().isLogged()) {
            this.setFrame(new MainFrame());
            System.out.println(this.frame.getUser());
        }
    }

    /*
    GETTERS AND SETTERS
     */

    public MainFrame getFrame() {
        return frame;
    }

    public void setFrame(MainFrame frame) {
        this.frame = frame;
    }

    /**
     * Launches new window
     */
    public static void main(String[] args) {
        MainWindow.INSTANCE.setFrame(new MainFrame());
        MainFrame frame = MainWindow.INSTANCE.getFrame();
        frame.pack();
        frame.setVisible(true);
    }
}