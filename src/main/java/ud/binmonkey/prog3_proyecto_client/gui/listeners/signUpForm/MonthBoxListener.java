package ud.binmonkey.prog3_proyecto_client.gui.listeners.signUpForm;

import ud.binmonkey.prog3_proyecto_client.gui.SignUpForm;

import java.awt.event.ActionEvent;

public class MonthBoxListener extends SignUpFormActionListener {

    public MonthBoxListener(SignUpForm signUpForm) {
        super(signUpForm);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int currentDay = (Integer) signUpForm.dayBox.getSelectedItem();
        signUpForm.reloadDaysMonths();
        try {
            signUpForm.dayBox.setSelectedItem(currentDay);
        } catch (Exception e) {
            signUpForm.dayBox.setSelectedIndex(0);
        }
    }
}
