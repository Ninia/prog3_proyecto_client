package ud.binmonkey.prog3_proyecto_client.gui.listeners.homeForm;

import ud.binmonkey.prog3_proyecto_client.gui.HomeForm;

import java.awt.event.ActionListener;

public abstract class HomeFormButtonListener extends HomeFormListener implements ActionListener{

    protected HomeForm homeForm;

    public HomeFormButtonListener(HomeForm homeForm) {
        super(homeForm);
    }
}
