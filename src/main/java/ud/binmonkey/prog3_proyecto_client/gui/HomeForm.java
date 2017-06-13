package ud.binmonkey.prog3_proyecto_client.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.imgscalr.Scalr;
import org.json.JSONArray;
import org.json.JSONObject;
import ud.binmonkey.prog3_proyecto_client.common.MovieName;
import ud.binmonkey.prog3_proyecto_client.ftp.FTPlib;
import ud.binmonkey.prog3_proyecto_client.gui.listeners.homeForm.*;
import ud.binmonkey.prog3_proyecto_client.https.HTTPSClient;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

@SuppressWarnings("WeakerAccess")
public class HomeForm {
    public JPanel mainHomePanel;
    public JTree userFileSysTree;
    public JPanel activitiesPanel;
    public JButton uploadButton;
    public JButton renameButton;
    public JButton removeButton;
    public JScrollPane userFileSysScrollPane;
    public JButton mkdirButton;
    public JPanel sysButtonsPanel;
    public JPanel uploadProgressPanel;
    public JProgressBar uploadProgressBar;
    public JLabel uploadProgressLabel;
    public JButton reloadButton;
    private JLabel whoseFilesLabel;
    private JPanel moviePanel;
    private JLabel movieImageLabel;
    private JPanel movieInfoLayout;
    private JLabel movieNameLabel;
    private JLabel movieYearLabel;

    /**
     * Default form shown when user logs in
     */
    public HomeForm() {
        $$$setupUI$$$();
        loadFileSysTree();

        /*
         * Rename file or folder selected by user to value requested in prompt
         * renamed file will be in the same directory old file
         */
        renameButton.addActionListener(
            new RenameButtonListener(this)
        );

        /*
         * Upload a file to the selected directory
         */
        uploadButton.addActionListener(
            new UploadButtonListener(this)
        );

        /* remove selected file or directory */
        removeButton.addActionListener(
            new RemoveButtonListener(this)
        );

        /* create specified directory */
        mkdirButton.addActionListener(
            new MkdirButtonListener(this)
        );

        /* reload @userFileSysTree */
        reloadButton.addActionListener(
            new ReloadButtonListener(this)
        );
    }

    /* reloads @userFileSysTree */
    public void reloadFileSysTree() {
        try {
            loadFileSysTree();
            MainWindow.INSTANCE.getFrame().validate();
            MainWindow.INSTANCE.getFrame().repaint();
        } catch (NullPointerException e) {
            /* intended to happen the first time this function is called at the creation of components */
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

        if (path.startsWith("/")) {
            path = path.substring(1, path.length());
        }

        if (path.equals("")) {
            path = "/";
        }

        return path;
    }

    /**
     * Loads the ftp files of the users to @userFileSysTree
     */
    @SuppressWarnings("CodeBlock2Expr")
    public void loadFileSysTree() {

        /* init https client */
        HTTPSClient client = new HTTPSClient();

        /* Load user files and dirs */
        JSONObject fileSys;
        try {
            fileSys = client.parseDirResponse(client.listDir(
                    MainWindow.INSTANCE.getFrame().getUser(), null, MainWindow.INSTANCE.getFrame().getToken()
            ));
        } catch (Exception e) {
//            throw new HTTPException(403);
            userFileSysTree = new JTree();
            userFileSysTree.setToolTipText("Unable to retrieve file system");
            return;
        }

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

        /* init variables required to save state of JTree */
        HashMap states = null;
        boolean statesSaved = false;

        /* try to save states */
        try {
            states = saveTreeState((DefaultMutableTreeNode) userFileSysTree.getModel().getRoot());
            statesSaved = true;
        } catch (NullPointerException e) {
            /* intended to happen the first time this function is called at the creation of components */
        }

        /* reset @userFileSysTree */
        userFileSysTree = new JTree(root);
        userFileSysTree.setEditable(false);
        userFileSysTree.setToolTipText("File system of " + MainWindow.INSTANCE.getFrame().getUser());
        userFileSysTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        /*
         * TREE LISTENER
         */
        userFileSysTree.addTreeSelectionListener(treeSelectionEvent -> {

            userFileSysTree.setSelectionPath(treeSelectionEvent.getNewLeadSelectionPath());

            /*TODO: check if file is movie */
            String fileName = userFileSysTree.getSelectionPath().getLastPathComponent().toString();
            if (MovieName.matchesMovie(fileName)) {

                String name = MovieName.getName(fileName);
                String year = MovieName.getYear(fileName);
                String fullName = MovieName.removeExtension(fileName);

                movieNameLabel.setText("Name: " + name);
                movieYearLabel.setText("Year: " + year);

                File imageFile = new File("data/images/" + fullName + ".jpg");
                if (!imageFile.exists()) {
                    try {
                        FTPlib.downloadFilmImage(name, year);

                    } catch (IOException e) {
                        movieImageLabel.setText("404: No image found.");
                        return;
                    }
                }
                try {
                    BufferedImage image = ImageIO.read(imageFile);
                    image = Scalr.resize(image, 300, 425);
                    ImageIcon pic = new ImageIcon(image);
                    movieImageLabel.setIcon(pic);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                movieNameLabel.setText("");
                movieYearLabel.setText("");
                movieImageLabel.setIcon(null);
            }

        });
        if (userFileSysScrollPane != null) {
            userFileSysScrollPane.setViewportView(userFileSysTree);
            if (statesSaved) {
                loadStates((DefaultMutableTreeNode) userFileSysTree.getModel().getRoot(), states);
            }
            MainWindow.INSTANCE.getFrame().validate();
            MainWindow.INSTANCE.getFrame().repaint();
        }

    }

    /**
     * Scans JTree and stores if each node was expanded
     *
     * @param root root node of JTree
     * @return @HashMap<String nameOfNode, Boolean wasExpanded>
     */
    public HashMap saveTreeState(DefaultMutableTreeNode root) {
        HashMap<String, Boolean> states = new HashMap<>();

        Enumeration e = root.depthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            states.put(node.toString(), userFileSysTree.isExpanded(new TreePath(node.getPath())));
        }

        return states;
    }

    /**
     * Compares nodes to nodes in @states and expands the ones that were expanded
     *
     * @param root   root node of JTree
     * @param states return value of @saveStates()
     */
    public void loadStates(DefaultMutableTreeNode root, HashMap states) {

        Enumeration e = root.depthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            try {
                if ((Boolean) states.get(node.toString())) {
                    userFileSysTree.expandPath(new TreePath(node.getPath()));
                }
            } catch (NullPointerException npe) {
                /* intended to happen the first time this function is called at the creation of components */
            }
        }

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
                DefaultMutableTreeNode dirNode =
                        loadNode((String) dir, (JSONObject) ((JSONObject) fileSys.get("directories")).get((String) dir));
                dirNode.setAllowsChildren(true);
                node.add(dirNode);
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
        activitiesPanel.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainHomePanel.add(activitiesPanel, BorderLayout.CENTER);
        final Spacer spacer1 = new Spacer();
        activitiesPanel.add(spacer1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        userFileSysScrollPane = new JScrollPane();
        activitiesPanel.add(userFileSysScrollPane, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        userFileSysTree.setAutoscrolls(true);
        userFileSysTree.setEditable(false);
        userFileSysTree.setFocusCycleRoot(true);
        userFileSysTree.setInheritsPopupMenu(true);
        userFileSysScrollPane.setViewportView(userFileSysTree);
        sysButtonsPanel = new JPanel();
        sysButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        activitiesPanel.add(sysButtonsPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        mkdirButton = new JButton();
        mkdirButton.setText("create dir");
        sysButtonsPanel.add(mkdirButton);
        reloadButton = new JButton();
        reloadButton.setText("reload");
        sysButtonsPanel.add(reloadButton);
        renameButton = new JButton();
        renameButton.setText("rename");
        sysButtonsPanel.add(renameButton);
        uploadButton = new JButton();
        uploadButton.setBackground(new Color(-16752771));
        uploadButton.setText("upload");
        sysButtonsPanel.add(uploadButton);
        removeButton = new JButton();
        removeButton.setBackground(new Color(-8571609));
        removeButton.setText("remove");
        sysButtonsPanel.add(removeButton);
        uploadProgressPanel = new JPanel();
        uploadProgressPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        activitiesPanel.add(uploadProgressPanel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
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
