package ud.binmonkey.prog3_proyecto_client.gui.listeners.signUpForm;

import ud.binmonkey.prog3_proyecto_client.gui.SignUpForm;

import java.awt.event.ActionEvent;

public class MonthBoxListener extends SignUpFormActionListener {

    public MonthBoxListener(SignUpForm signUpForm) {
        super(signUpForm);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int currentDay = (Integer) this.getSignUpForm().dayBox.getSelectedItem();
        this.getSignUpForm().reloadDaysMonths();
        try {
            this.getSignUpForm().dayBox.setSelectedItem(currentDay);
        } catch (Exception e) {
            this.getSignUpForm().dayBox.setSelectedIndex(0);
        }
    }
}
