package ud.binmonkey.prog3_proyecto_client.gui.listeners.homeForm;

import ud.binmonkey.prog3_proyecto_client.ftp.FTPlib;
import ud.binmonkey.prog3_proyecto_client.gui.HomeForm;
import ud.binmonkey.prog3_proyecto_client.gui.MainWindow;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class RemoveButtonListener extends HomeFormButtonListener {

    public RemoveButtonListener(HomeForm homeForm) {
        super(homeForm);
    }

    /**
     * remove selected file or directory
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        try {
            FTPlib.delete(
                    MainWindow.INSTANCE.getFrame().getUser(),
                    new String(MainWindow.INSTANCE.getFrame().getPassword()),
                    this.getHomeForm().getSelectedDir()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.getHomeForm().reloadFileSysTree();
    }
}
