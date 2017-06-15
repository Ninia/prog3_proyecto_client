package ud.binmonkey.prog3_proyecto_client.gui.listeners.mainMenuBar;

import ud.binmonkey.prog3_proyecto_client.gui.MainWindow;
import ud.binmonkey.prog3_proyecto_client.gui.library.LibraryForm;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class LibraryMenuListener implements MenuListener {
    @Override
    public void menuSelected(MenuEvent menuEvent) {
        MainWindow.INSTANCE.getFrame().setForm(new LibraryForm().libraryPanel);
    }

    @Override
    public void menuDeselected(MenuEvent menuEvent) {

    }

    @Override
    public void menuCanceled(MenuEvent menuEvent) {

    }

}
