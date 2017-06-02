package ud.binmonkey.prog3_proyecto_client.gui.listeners.homeForm;

import ud.binmonkey.prog3_proyecto_client.ftp.FTPlib;
import ud.binmonkey.prog3_proyecto_client.gui.HomeForm;
import ud.binmonkey.prog3_proyecto_client.gui.MainWindow;
import ud.binmonkey.prog3_proyecto_client.gui.utils.UserInputDialog;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class MkdirButtonListener extends HomeFormButtonListener {

    public MkdirButtonListener(HomeForm homeForm) {
        super(homeForm);
    }

    /**
     * create specified directory
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        /* get new dir name */
        String newDir = UserInputDialog.getInput("New folder name:", MainWindow.INSTANCE.getFrame());

        /* check if name is not empty */
        if (newDir == null || newDir.replaceAll("\\s", "").equals("")) {
            return;
        }
        /* try to create directory */
        try {
            FTPlib.mkdir(
                    MainWindow.INSTANCE.getFrame().getUser(),
                    new String(MainWindow.INSTANCE.getFrame().getPassword()),
                    this.getHomeForm().getSelectedDir() + newDir
            );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println(this.getHomeForm());
            System.out.print("user: ");
            System.out.println( MainWindow.INSTANCE.getFrame().getUser());
            System.out.print("password: ");
            System.out.println(new String(MainWindow.INSTANCE.getFrame().getPassword()));
            System.out.print("selected: ");
            System.out.println(this.getHomeForm().getSelectedDir());
            System.out.print("new: ");
            System.out.println( newDir);
            System.exit(1);
        }
        this.getHomeForm().reloadFileSysTree();
    }
}
