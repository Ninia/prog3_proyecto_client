package ud.binmonkey.prog3_proyecto_client.gui.listeners.homeForm;

import ud.binmonkey.prog3_proyecto_client.gui.HomeForm;

public abstract class HomeFormListener {

    private HomeForm homeForm;

    public HomeFormListener(HomeForm homeForm) {
        this.setHomeForm(homeForm);
    }

    public HomeForm getHomeForm() {
        return homeForm;
    }

    public void setHomeForm(HomeForm homeForm) {
        this.homeForm = homeForm;
    }
}
