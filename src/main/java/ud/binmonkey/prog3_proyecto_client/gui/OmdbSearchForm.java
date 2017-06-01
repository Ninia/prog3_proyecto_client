package ud.binmonkey.prog3_proyecto_client.gui;

import ud.binmonkey.prog3_proyecto_client.omdb.MediaType;
import ud.binmonkey.prog3_proyecto_client.omdb.Omdb;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class OmdbSearchForm {
    private JPanel mainOmdbSearchPanel;

    private JPanel searchPanel;
    private JTextField searchText;
    private JButton searchButton;
    private JComboBox<MediaType> typeBox;

    private JPanel addPanel;
    private JTextField idText;
    private JButton addButton;

    private JTable titleTable;
    private JButton button1;
    private JToolBar Search;

    public OmdbSearchForm() {

        DefaultTableModel titleModel = new DefaultTableModel();

        titleModel.addColumn("ID");
        titleModel.addColumn("Year");
        titleModel.addColumn("Title");
        titleModel.addColumn("Type");

        titleTable.setModel(titleModel);

        searchButton.addActionListener((ActionEvent e) -> {

            MediaType type = null;

            /* Clear Table */
            while(titleModel.getRowCount() > 0) {
                titleModel.removeRow(0);
            }

            System.out.println(typeBox.getSelectedItem().toString());

            switch (typeBox.getSelectedItem().toString()) {
                case "All":
                    type = MediaType.ALL;
                    break;
                case "Movie":
                    type = MediaType.MOVIE;
                    break;
                case "Series":
                    type = MediaType.SERIES;
                    break;
                case "Episode":
                    type = MediaType.EPISODE;
                    break;
            }

            System.out.println(type);
            HashMap search = Omdb.search(searchText.getText(), type);

            for (Object id : search.keySet()) {

                Map title = (Map) search.get(id);
                Object[] data = {id , title.get("Year"), title.get("Title"), title.get("Type")};
                titleModel.addRow(data);
            }

        });


        titleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = titleTable.getSelectedRow();
                idText.setText((String) titleModel.getValueAt(row, 0));
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Search");
        frame.setContentPane(new OmdbSearchForm().mainOmdbSearchPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
