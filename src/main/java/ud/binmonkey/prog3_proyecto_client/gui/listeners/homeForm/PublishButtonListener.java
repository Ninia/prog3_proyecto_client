package ud.binmonkey.prog3_proyecto_client.gui.listeners.homeForm;

import ud.binmonkey.prog3_proyecto_client.gui.HomeForm;
import ud.binmonkey.prog3_proyecto_client.gui.omdb.OmdbListSearchForm;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PublishButtonListener extends HomeFormButtonListener {

    public PublishButtonListener(HomeForm homeForm) {
        super(homeForm);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        String title = getHomeForm().getSelectedDir();
        if (title.endsWith("/")) {
            title = title.substring(0, title.length() - 1);
        }

        title = title.split("/")[title.split("/").length - 1];

        JFrame searchFrame = new JFrame("Search attributes of " + title);
        OmdbListSearchForm searchForm = new OmdbListSearchForm();
        searchForm.setFrame(searchFrame);

        /* set default name */
        searchForm.getSearchText().setText(title.split("\\(")[0]);
        /* default type is movies */
        searchForm.getTypeBox().setSelectedIndex(1);

        searchFrame.setContentPane(searchForm.getMainOmdbListPanel());
        searchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchFrame.pack();
        searchFrame.setVisible(true);
    }
}
