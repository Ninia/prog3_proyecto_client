package ud.binmonkey.prog3_proyecto_client.gui;

import ud.binmonkey.prog3_proyecto_client.gui.listeners.mainMenuBar.*;

import javax.swing.*;
import java.util.ArrayList;

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
    private JMenu account;

    private ArrayList<JLabel> separators = new ArrayList<>();

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

        for (int i=0; i < 5; i++) {
            separators.add(new JLabel("  |  "));
        }

        this.account = new JMenu("Account");
        this.library = new JMenu("Library");
        this.myFiles = new JMenu("My Files");
        this.omdbSearch = new JMenu("Search New");
        this.statistics = new JMenu("Statistics");

        this.account.addMenuListener(new AccountMenuListener());
        this.library.addMenuListener(new LibraryMenuListener());
        this.myFiles.addMenuListener(new MyFilesMenuListener());
        this.omdbSearch.addMenuListener(new SearchMenuListener());
        this.statistics.addMenuListener(new StatisticsMenuListener());

        this.add(separators.get(0));
        this.add(myFiles);
        this.add(separators.get(1));
        this.add(library);
        this.add(separators.get(2));
        this.add(omdbSearch);
        this.add(separators.get(3));
        this.add(statistics);
        this.add(separators.get(4));
        this.add(account);

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
        this.account.setEnabled(true);
        this.account.setVisible(true);

        for (JLabel separator: separators) {
            separator.setVisible(true);
        }
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
        this.account.setEnabled(false);
        this.account.setVisible(false);

        for (JLabel separator: separators) {
            separator.setVisible(false);
        }
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
