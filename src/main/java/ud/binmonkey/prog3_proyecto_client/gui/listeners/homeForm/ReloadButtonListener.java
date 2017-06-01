package ud.binmonkey.prog3_proyecto_client.gui.listeners.homeForm;

import ud.binmonkey.prog3_proyecto_client.gui.HomeForm;

import java.awt.event.ActionEvent;

public class ReloadButtonListener extends HomeFormButtonListener {

    public ReloadButtonListener(HomeForm homeForm) {
        super(homeForm);
    }

    /**
     * reload @homeForm.userFileSysTree
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        homeForm.reloadFileSysTree();
    }
}
