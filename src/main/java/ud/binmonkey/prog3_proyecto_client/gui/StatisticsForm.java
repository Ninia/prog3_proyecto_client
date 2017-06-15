package ud.binmonkey.prog3_proyecto_client.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import ud.binmonkey.prog3_proyecto_client.mysql.MySQLUtils;
import ud.binmonkey.prog3_proyecto_client.neo4j.Neo4jUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class StatisticsForm {
    public JPanel statisticsPanel;
    public JPanel optionsPanel;
    public JButton displayButton;
    public JComboBox displayBox;
    public JTable statsTable;
    public JScrollPane statsPane;
    public JTabbedPane tabbedPane1;
    public JPanel generalPanel;
    public JPanel personalPanel;
    public JLabel timeSpent;
    public JLabel mostWatchedGenre;
    private DefaultTableModel statsModel = new DefaultTableModel();
    private MySQLUtils mySQL;
    private Neo4jUtils neo4j;

    public StatisticsForm() {

        neo4j = new Neo4jUtils();
        mySQL = new MySQLUtils();

        /* JTable Config */

        statsModel.addColumn("Title");
        statsModel.addColumn("Type");
        statsModel.addColumn("Value"); /* TODO Change this */

        statsTable.setModel(statsModel);

        /* JTable Column Sizes */
        statsTable.getColumn("Type").setMaxWidth(85);
        statsTable.getColumn("Value").setMaxWidth(65);

        statsTable.getColumn("Type").setMinWidth(85);
        statsTable.getColumn("Value").setMinWidth(65);

        /* Center Text */
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        statsTable.getColumn("Type").setCellRenderer(centerRenderer);
        statsTable.getColumn("Value").setCellRenderer(centerRenderer);

        /* Sorter Config */
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(statsModel);
        statsTable.setRowSorter(sorter);

        /* Initial Display */
        displayRatings();

        /* Listeners */
        displayBox.addActionListener(actionEvent -> {

            /* Clear Table */
            while (statsModel.getRowCount() > 0) {
                statsModel.removeRow(0);
            }

            switch (displayBox.getSelectedItem().toString()) {
                case "Ratings":
                    displayRatings();
                    break;
                case "Times Watched":
                    displayTimesWatched();
                    break;
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        StatisticsForm form = new StatisticsForm();
        JPanel panel = form.statisticsPanel;

        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    private void displayRatings() {
        for (String id : mySQL.getTitleList("user_ratings")) {

            Object[] data = {neo4j.getAttribute(id, neo4j.getType(id), "title"), neo4j.getType(id),
                    mySQL.getUserRatings(id)};
            statsModel.addRow(data);
        }
    }

    private void displayTimesWatched() {
        for (String id : mySQL.getTitleList("user_viewing_history")) {

            Object[] data = {neo4j.getAttribute(id, neo4j.getType(id), "title"), neo4j.getType(id),
                    mySQL.getTimesWatched(id)};
            statsModel.addRow(data);
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        statisticsPanel = new JPanel();
        statisticsPanel.setLayout(new BorderLayout(0, 0));
        tabbedPane1 = new JTabbedPane();
        statisticsPanel.add(tabbedPane1, BorderLayout.CENTER);
        personalPanel = new JPanel();
        personalPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Personal", personalPanel);
        timeSpent = new JLabel();
        timeSpent.setText("Label");
        personalPanel.add(timeSpent, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mostWatchedGenre = new JLabel();
        mostWatchedGenre.setText("Label");
        personalPanel.add(mostWatchedGenre, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        generalPanel = new JPanel();
        generalPanel.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("General", generalPanel);
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        generalPanel.add(optionsPanel, BorderLayout.NORTH);
        displayBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Ratings");
        defaultComboBoxModel1.addElement("Times Watched");
        displayBox.setModel(defaultComboBoxModel1);
        optionsPanel.add(displayBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        statsPane = new JScrollPane();
        generalPanel.add(statsPane, BorderLayout.CENTER);
        statsTable = new JTable();
        statsPane.setViewportView(statsTable);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return statisticsPanel;
    }
}
