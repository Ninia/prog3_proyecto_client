package ud.binmonkey.prog3_proyecto_client.gui;

import ud.binmonkey.prog3_proyecto_client.gui.listeners.mainMenuBar.LibraryMenuListener;
import ud.binmonkey.prog3_proyecto_client.gui.listeners.mainMenuBar.MyFilesMenuListener;
import ud.binmonkey.prog3_proyecto_client.gui.listeners.mainMenuBar.StatisticsMenuListener;

import javax.swing.*;

/**
 * Menu bar that will be always present
 */
public class MainMenuBar extends JMenuBar{

    private JMenu sessionMenu;
    private JMenuItem tokenItem;
    private JMenuItem logoutItem;

    private JMenu library;
    private JMenu myFiles;
    private JMenu omdbSearch;
    private JMenu statistics;

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

        this.library = new JMenu("Library");
        this.myFiles = new JMenu("My Files");
        this.omdbSearch = new JMenu("Search New");
        this.statistics = new JMenu("Statistics");

        this.library.addMenuListener(new LibraryMenuListener());
        this.myFiles.addMenuListener(new MyFilesMenuListener());
        this.statistics.addMenuListener(new StatisticsMenuListener());

        this.add(new JLabel("  |  "));
        this.add(myFiles);
        this.add(new JLabel("  |  "));
        this.add(library);
        this.add(new JLabel("  |  "));
        this.add(omdbSearch);
        this.add(new JLabel("  |  "));
        this.add(statistics);

        this.logOut();

    }

    public void logIn() {
        this.library.setEnabled(true);
        this.library.setVisible(true);
        this.myFiles.setEnabled(true);
        this.myFiles.setVisible(true);
        this.omdbSearch.setEnabled(true);
        this.omdbSearch.setVisible(true);
        this.statistics.setEnabled(true);
        this.statistics.setVisible(true);
    }

    public void logOut() {
        this.library.setEnabled(false);
        this.myFiles.setEnabled(false);
        this.omdbSearch.setEnabled(false);
        this.statistics.setEnabled(false);
        this.library.setVisible(false);
        this.myFiles.setVisible(false);
        this.omdbSearch.setVisible(false);
        this.statistics.setVisible(false);
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

    public JMenu getMyFiles() {
        return myFiles;
    }

    public void setMyFiles(JMenu myFiles) {
        this.myFiles = myFiles;
    }

    public JMenu getLibrary() {
        return library;
    }

    public void setLibrary(JMenu library) {
        this.library = library;
    }

    public JMenu getOmdbSearch() {
        return omdbSearch;
    }

    public void setOmdbSearch(JMenu omdbSearch) {
        this.omdbSearch = omdbSearch;
    }

    public JMenu getStatistics() {
        return statistics;
    }

    public void setStatistics(JMenu statistics) {
        this.statistics = statistics;
    }
}
