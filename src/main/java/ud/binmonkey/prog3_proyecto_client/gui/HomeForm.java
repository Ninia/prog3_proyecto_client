package ud.binmonkey.prog3_proyecto_client.gui;

import org.json.JSONArray;
import org.json.JSONObject;
import ud.binmonkey.prog3_proyecto_client.ftp.FTPlib;
import ud.binmonkey.prog3_proyecto_client.gui.utils.UserInputDialog;
import ud.binmonkey.prog3_proyecto_client.https.HTTPSClient;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * TODO: reload tree
 * TODO: reload button
 */
public class HomeForm {
    public JPanel mainHomePanel;
    public JTree userFileSysTree;
    public JPanel activitiesPanel;
    public JButton uploadButton;
    public JButton renameButton;
    public JButton mkdirButton;
    public JScrollPane userFileSysScrollPane;
    public JButton removeButton;
    public JPanel sysButtonsPanel;
    public JPanel uploadProgressPanel;
    public JProgressBar uploadProgressBar;
    public JLabel uploadProgressLabel;

    /**
     * Default form shown when user logs in
     */
    public HomeForm() {
        $$$setupUI$$$();
        loadFileSysTree();

        /*
         * Rename file or folder selected by user to value requested in prompt
         */
        renameButton.addActionListener(actionEvent -> {
            String newFile = UserInputDialog.getInput("New name", MainWindow.INSTANCE.getFrame());

            if (newFile == null || newFile.replaceAll("\\s", "").equals("")) {
                return;
            }

            try {
                FTPlib.rename(
                        MainWindow.INSTANCE.getFrame().getUser(),
                        new String(MainWindow.INSTANCE.getFrame().getPassword()),
                        getSelectedDir(),
                        newFile,
                        true
                );
            } catch (IOException e) {
                uploadProgressLabel.setText("Error renaming file.");
            }
            reload();
        });

        /*
         * Upload a file to the selected directory
         */
        uploadButton.addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

            int result = fileChooser.showOpenDialog(new JFrame());

            if (result == JFileChooser.APPROVE_OPTION) {
                if (fileChooser.getSelectedFile().isFile()) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();

                    uploadProgressLabel.setText("Upload in progress: ");
                    uploadProgressLabel.setVisible(true);
                        /* TODO: change progress bar values */
                    uploadProgressBar.setValue(10);
                    uploadProgressBar.setVisible(true);
                    MainWindow.INSTANCE.getFrame().getContentPane().validate();
                    MainWindow.INSTANCE.getFrame().getContentPane().repaint();

                    new Thread(() -> {
                        try {
                            FTPlib.uploadFile(
                                    MainWindow.INSTANCE.getFrame().getUser(),
                                    new String(MainWindow.INSTANCE.getFrame().getPassword()),
                                    filePath,
                                    getSelectedDir()
                            );
                            uploadProgressBar.setValue(100);
                            loadFileSysTree();
                            MainWindow.INSTANCE.getFrame().validate();
                            MainWindow.INSTANCE.getFrame().repaint();
                        } catch (IOException e) {
                            uploadProgressLabel.setText("Unable to upload file.");
                            uploadProgressLabel.setVisible(true);
                        }
                    }).start();
                } else {
                    uploadProgressLabel.setText("Chosen file is a directory.");
                    uploadProgressLabel.setVisible(true);
                }
            }
            reload();
        });
        mkdirButton.addActionListener(actionEvent -> {
            try {
                FTPlib.delete(
                        MainWindow.INSTANCE.getFrame().getUser(),
                        new String(MainWindow.INSTANCE.getFrame().getPassword()),
                        getSelectedDir()
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            reload();
        });
        removeButton.addActionListener(actionEvent -> {
            String newDir = UserInputDialog.getInput("New folder name:", MainWindow.INSTANCE.getFrame());
            if (newDir == null || newDir.replaceAll("\\s", "").equals("")) {
                return;
            }
            try {
                FTPlib.mkdir(
                        MainWindow.INSTANCE.getFrame().getUser(),
                        new String(MainWindow.INSTANCE.getFrame().getPassword()),
                        getSelectedDir() + newDir
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            reload();
        });
    }

    public static void reload() {
        try {
            MainWindow.INSTANCE.getFrame().validate();
            MainWindow.INSTANCE.getFrame().repaint();
        } catch (NullPointerException e) {
        }
    }

    /**
     * Gets selected path in userFileSysTree
     *
     * @return String containing selected path
     */
    public String getSelectedDir() {

        /* FIXME: paths are returned as null */

        String path = userFileSysTree.getSelectionPath().toString();

        if (path == null || path.equals("")) {
            return null;
        }

        path = path.replaceAll("\\[", "").replaceAll("]", "");

        String[] components = path.split(",");
        components[0] = null;

        /* end of FIXME */

        path = "";

        for (String component : components) {
            if (component != null) {
                while (component.startsWith(" ")) {
                    component = component.substring(1, component.length());
                }
                path += component + "/";
            }
        }

        return path;
    }

    /**
     * Loads the ftp files of the users to @userFileSysTree
     */
    public void loadFileSysTree() {

        /* init https client */
        HTTPSClient client = new HTTPSClient();

        /* Load user files and dirs */
        JSONObject fileSys = client.parseDirResponse(client.listDir(
                MainWindow.INSTANCE.getFrame().getUser(), null, MainWindow.INSTANCE.getFrame().getToken()
        ));

        /* root node with username */
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(MainWindow.INSTANCE.getFrame().getUser());

        /* load files */
        for (Object fileName : (JSONArray) fileSys.get("files")) {
            if (fileName instanceof String) {
                DefaultMutableTreeNode file = new DefaultMutableTreeNode(fileName);
                file.setAllowsChildren(false);
                root.add(file);
            }
        }

        /* load dirs*/
        for (Object key : ((JSONObject) fileSys.get("directories")).keySet()) {
            if (key instanceof String) {
                root.add(loadNode((String) key, (JSONObject) ((JSONObject) fileSys.get("directories")).get((String) key)));
            }
        }

        userFileSysTree = new JTree(root);
        userFileSysTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        userFileSysTree.addTreeSelectionListener(treeSelectionEvent -> {
            userFileSysTree.setSelectionPath(treeSelectionEvent.getNewLeadSelectionPath());
        });
        MainWindow.INSTANCE.getFrame().validate();
        MainWindow.INSTANCE.getFrame().repaint();
    }

    /**
     * Loads a node containing information about a directory
     *
     * @param name    name of the directory
     * @param fileSys JSONObject with content of directory
     * @return node containing information about a directory
     */
    public DefaultMutableTreeNode loadNode(String name, JSONObject fileSys) {

        /* root component of this node */
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(name);

        /* load files */
        for (Object dir : ((JSONArray) fileSys.get("files"))) {
            if (dir instanceof String) {
                node.add(new DefaultMutableTreeNode(dir));
            }
        }

        /* load dirs */
        for (Object dir : ((JSONObject) fileSys.get("directories")).keySet()) {
            if (dir instanceof String) {
                node.add(loadNode((String) dir, (JSONObject) ((JSONObject) fileSys.get("directories")).get((String) dir)));
            }
        }
        return node;
    }


    private void createUIComponents() {
        /* insert manual component creation here */
        loadFileSysTree();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        mainHomePanel = new JPanel();
        mainHomePanel.setLayout(new BorderLayout(0, 0));
        activitiesPanel = new JPanel();
        activitiesPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainHomePanel.add(activitiesPanel, BorderLayout.CENTER);
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        activitiesPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        userFileSysScrollPane = new JScrollPane();
        activitiesPanel.add(userFileSysScrollPane, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        userFileSysTree.setAutoscrolls(true);
        userFileSysTree.setEditable(false);
        userFileSysTree.setFocusCycleRoot(true);
        userFileSysTree.setInheritsPopupMenu(true);
        userFileSysScrollPane.setViewportView(userFileSysTree);
        sysButtonsPanel = new JPanel();
        sysButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        activitiesPanel.add(sysButtonsPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        uploadButton = new JButton();
        uploadButton.setText("upload");
        sysButtonsPanel.add(uploadButton);
        renameButton = new JButton();
        renameButton.setText("rename");
        sysButtonsPanel.add(renameButton);
        mkdirButton = new JButton();
        mkdirButton.setText("remove");
        sysButtonsPanel.add(mkdirButton);
        removeButton = new JButton();
        removeButton.setText("create dir");
        sysButtonsPanel.add(removeButton);
        uploadProgressPanel = new JPanel();
        uploadProgressPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        activitiesPanel.add(uploadProgressPanel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        uploadProgressLabel = new JLabel();
        uploadProgressLabel.setForeground(new Color(-16729235));
        uploadProgressLabel.setText("");
        uploadProgressLabel.setVisible(false);
        uploadProgressPanel.add(uploadProgressLabel);
        uploadProgressBar = new JProgressBar();
        uploadProgressBar.setVisible(false);
        uploadProgressPanel.add(uploadProgressBar);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainHomePanel;
    }
}
