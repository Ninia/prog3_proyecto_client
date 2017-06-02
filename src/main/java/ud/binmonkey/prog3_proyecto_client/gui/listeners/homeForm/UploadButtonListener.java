package ud.binmonkey.prog3_proyecto_client.gui.listeners.homeForm;

import ud.binmonkey.prog3_proyecto_client.ftp.FTPlib;
import ud.binmonkey.prog3_proyecto_client.gui.HomeForm;
import ud.binmonkey.prog3_proyecto_client.gui.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class UploadButtonListener extends HomeFormButtonListener {

    public UploadButtonListener(HomeForm homeForm) {
        super(homeForm);
    }

    /**
     * Upload a file to the selected directory
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
                    /* select a file */
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        int result = fileChooser.showOpenDialog(new JFrame());

        if (result == JFileChooser.APPROVE_OPTION) {

                /* if it's a file (unnecessary?)*/
            if (fileChooser.getSelectedFile().isFile()) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();

                /* make upload progress display components appear */
                this.getHomeForm().uploadProgressLabel.setText("Upload in progress: ");
                this.getHomeForm().uploadProgressLabel.setVisible(true);
                /* TODO: change progress bar values */
                this.getHomeForm().uploadProgressBar.setValue(10);
                this.getHomeForm().uploadProgressBar.setVisible(true);
                MainWindow.INSTANCE.getFrame().getContentPane().validate();
                MainWindow.INSTANCE.getFrame().getContentPane().repaint();

                    /* Upload file in background */
                new Thread(() -> {
                    try {
                        FTPlib.uploadFile(
                                MainWindow.INSTANCE.getFrame().getUser(),
                                new String(MainWindow.INSTANCE.getFrame().getPassword()),
                                filePath,
                                this.getHomeForm().getSelectedDir()
                        );
                        this.getHomeForm().uploadProgressBar.setValue(100);
                        this.getHomeForm().loadFileSysTree();
                        MainWindow.INSTANCE.getFrame().validate();
                        MainWindow.INSTANCE.getFrame().repaint();
                    } catch (IOException e) {
                        this.getHomeForm().uploadProgressLabel.setText("Unable to upload file.");
                        this.getHomeForm().uploadProgressLabel.setVisible(true);
                    }
                }).start();

            } else {
                this.getHomeForm().uploadProgressLabel.setText("Chosen file is a directory.");
                this.getHomeForm().uploadProgressLabel.setVisible(true);
            }
        }
        this.getHomeForm().reloadFileSysTree();
    }
}
