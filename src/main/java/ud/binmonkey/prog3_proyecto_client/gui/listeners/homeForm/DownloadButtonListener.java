package ud.binmonkey.prog3_proyecto_client.gui.listeners.homeForm;

import ud.binmonkey.prog3_proyecto_client.ftp.FTPlib;
import ud.binmonkey.prog3_proyecto_client.gui.HomeForm;
import ud.binmonkey.prog3_proyecto_client.gui.MainWindow;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class DownloadButtonListener extends HomeFormButtonListener {
    public DownloadButtonListener(HomeForm homeForm) {
        super(homeForm);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        new java.io.File("downloads").mkdirs();
        this.getHomeForm().uploadProgressLabel.setText("Download in progress: ");
        this.getHomeForm().uploadProgressLabel.setVisible(true);
        this.getHomeForm().uploadProgressBar.setValue(10);
        this.getHomeForm().uploadProgressBar.setVisible(true);
        try {
            FTPlib.downloadFile(getHomeForm().getSelectedDir(),
                    getHomeForm().getUserFileSysTree().getSelectionPath().getLastPathComponent().toString(),
                    MainWindow.INSTANCE.getFrame().getUser(),
                    new String(MainWindow.INSTANCE.getFrame().getPassword()));
            this.getHomeForm().uploadProgressBar.setValue(100);
            this.getHomeForm().uploadProgressLabel.setText("Download was successful!");
        } catch (IOException e) {
            e.printStackTrace();
            this.getHomeForm().uploadProgressLabel.setText("Unable to download image");
            this.getHomeForm().uploadProgressBar.setValue(0);
        }
    }
}
