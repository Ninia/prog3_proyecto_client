package ud.binmonkey.prog3_proyecto_client.gui.listeners.homeForm;

import ud.binmonkey.prog3_proyecto_client.ftp.FTPlib;
import ud.binmonkey.prog3_proyecto_client.gui.HomeForm;
import ud.binmonkey.prog3_proyecto_client.gui.MainWindow;
import ud.binmonkey.prog3_proyecto_client.gui.utils.UserInputDialog;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class RenameButtonListener extends HomeFormButtonListener {

    public RenameButtonListener(HomeForm homeForm) {
        super(homeForm);
    }

    /**
     * Rename file or folder selected by user to value requested in prompt
     * renamed file will be in the same directory old file
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
                    /* get new name */
        String newFile = UserInputDialog.getInput("New name", MainWindow.INSTANCE.getFrame());

            /* check if name is not empty */
        if (newFile == null || newFile.replaceAll("\\s", "").equals("")) {
            return;
        }

        try {
            FTPlib.rename(
                    MainWindow.INSTANCE.getFrame().getUser(),
                    new String(MainWindow.INSTANCE.getFrame().getPassword()),
                    homeForm.getSelectedDir(),
                    newFile,
                    true
            );
        } catch (IOException e) {
            homeForm.uploadProgressLabel.setText("Error renaming file.");
        }
        homeForm.reloadFileSysTree();
    }
}
