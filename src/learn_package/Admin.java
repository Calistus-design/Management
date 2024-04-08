/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package learn_package;

//import com.sun.jdi.connect.spi.Connection;
import java.awt.Color;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author calis
 */
public final class Admin extends javax.swing.JFrame {

    private final Map<String, Map<String, List<String>>> moduleTopicTitleMap;
    // Variable to store the type of data (Module, Topic, or Title)
    private String selectedDataType;
    private Connection con;
    String cty;
    private byte[] imageData;
    int selectedQuestionID = -1;
    String selectedItem;
    public void connect() {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Unique", "ca", "12");
        } catch (SQLException e) {
        }
    }

    public void populateComboBox() {
        try {
            try ( // Establish the database connection
                    Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Unique", "ca", "12")) {
                Statement st = con.createStatement();
                
                // Execute a query to retrieve the data
                ResultSet rs = st.executeQuery("SELECT Module FROM Modules");
                
                // Create ArrayList to store the items
                ArrayList<String> items = new ArrayList<>();
                // Iterate through the ResultSet and add items to the ArrayList
                while (rs.next()) {
                    String module = rs.getString("Module");
                    items.add(module);
                }
                
                // Convert the ArrayList to an array and set it as the data for the JComboBox
                String[] itemArray = items.toArray(String[]::new);
                JComboBox<String> comboBox = jComboBox1;
                comboBox.setModel(new javax.swing.DefaultComboBoxModel<>(itemArray));
                
                // Close the resources
                rs.close();
                st.close();
            }
        } catch (SQLException ex) {
            
        }
    }

    public void populateComboBox2() {
        try {
            try ( // Establish the database connection
                    Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Unique", "ca", "12")) {
                Statement st = con.createStatement();
                
                // Execute a query to retrieve the data
                ResultSet rs = st.executeQuery("SELECT Module FROM Modules");
                
                // Create an ArrayList to store the items
                ArrayList<String> items = new ArrayList<>();
                // Iterate through the ResultSet and add items to the ArrayList
                while (rs.next()) {
                    String module = rs.getString("Module");
                    items.add(module);
                }
                
                // Convert the ArrayList to an array and set it as the data for the JComboBox
                String[] itemArray = items.toArray(new String[0]);
                JComboBox<String> comboBox = jComboBox2; // Replace 'yourComboBox' with the actual name of your JComboBox
                comboBox.setModel(new javax.swing.DefaultComboBoxModel<>(itemArray));
                
                // Close the resources
                rs.close();
                st.close();
            }
        } catch (SQLException ex) {
            // Handle the exception appropriately
        }
    }

    public void populateTopicsComboBox(String selectedModule) {
        try {
            // Establish the database connection
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Unique", "ca", "12");
            Statement st = con.createStatement();

            // First, get the ModuleID for the selected module from the Modules table
            String moduleIDQuery = "SELECT ModuleID FROM Modules WHERE Module = '" + selectedModule + "'";
            int moduleID;
            try (ResultSet moduleIDResultSet = st.executeQuery(moduleIDQuery)) {
                moduleID = -1; // Default value in case the module is not found
                if (moduleIDResultSet.next()) {
                    moduleID = moduleIDResultSet.getInt("ModuleID");
                }
            } // Default value in case the module is not found

            if (moduleID != -1) {
                // Execute a query to retrieve the topics based on the ModuleID
                String topicsQuery = "SELECT Topic FROM Topics WHERE ModuleID = " + moduleID;
                ArrayList<String> topics;
                // Create an ArrayList to store the topics
                try (ResultSet topicsResultSet = st.executeQuery(topicsQuery)) {
                    // Create an ArrayList to store the topics
                    topics = new ArrayList<>();
                    // Iterate through the ResultSet and add topics to the ArrayList
                    while (topicsResultSet.next()) {
                        String topic = topicsResultSet.getString("Topic");
                        topics.add(topic);
                    }
                }

                // Close the resources
                st.close();
                con.close();

                // Set the topics in the second JComboBox (jComboBox2)
                jComboBox3.removeAllItems();
                // Convert the ArrayList to an array and set it as the data for the second JComboBox (topics)
                String[] topicArray = topics.toArray(String[]::new);
                jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(topicArray));
            } else {
                // Module not found, reset jComboBox2
                jComboBox3.removeAllItems();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error retrieving topics from the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
     
     public void populateComboBox4() {
    try {
        // Establish the database connection
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Unique", "ca", "12");
        Statement st = con.createStatement();

        // Execute a query to retrieve the data
        ResultSet rs = st.executeQuery("SELECT Module FROM Modules");

        // Create an ArrayList to store the items
        ArrayList<String> items = new ArrayList<>();
        // Iterate through the ResultSet and add items to the ArrayList
        while (rs.next()) {
            String module = rs.getString("Module");
            items.add(module);
        }

        // Convert the ArrayList to an array and set it as the data for the JComboBox
        String[] itemArray = items.toArray(String[]::new);
        JComboBox<String> comboBox = jComboBox4; // Replace 'yourComboBox' with the actual name of your JComboBox
        comboBox.setModel(new javax.swing.DefaultComboBoxModel<>(itemArray));
        
        // Close the resources
        rs.close();
        st.close();
        con.close();
    } catch (SQLException ex) {
        // Handle the exception appropriately
    }
}
    public void populateTopicsComboBox1(String selectedModule) {
    try {
        // Establish the database connection
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Unique", "ca", "12");
        Statement st = con.createStatement();

        // First, get the ModuleID for the selected module from the Modules table
        String moduleIDQuery = "SELECT ModuleID FROM Modules WHERE Module = '" + selectedModule + "'";
        int moduleID;
        try (ResultSet moduleIDResultSet = st.executeQuery(moduleIDQuery)) {
            moduleID = -1; // Default value in case the module is not found
            if (moduleIDResultSet.next()) {
                moduleID = moduleIDResultSet.getInt("ModuleID");
            }
        } // Default value in case the module is not found

        if (moduleID != -1) {
            // Execute a query to retrieve the topics based on the ModuleID
            String topicsQuery = "SELECT Topic FROM Topics WHERE ModuleID = " + moduleID;
            ArrayList<String> topics;
            // Create an ArrayList to store the topics
            try (ResultSet topicsResultSet = st.executeQuery(topicsQuery)) {
                // Create an ArrayList to store the topics
                topics = new ArrayList<>();
                // Iterate through the ResultSet and add topics to the ArrayList
                while (topicsResultSet.next()) {
                    String topic = topicsResultSet.getString("Topic");
                    topics.add(topic);
                }
            }

            // Close the resources
            st.close();
            con.close();

            // Set the topics in the second JComboBox (jComboBox2)
            jComboBox6.removeAllItems();
            // Convert the ArrayList to an array and set it as the data for the second JComboBox (topics)
            String[] topicArray = topics.toArray(String[]::new);
            jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(topicArray));
        } else {
            // Module not found, reset jComboBox2
            jComboBox6.removeAllItems();
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error retrieving topics from the database.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    private void getDataFromDatabase() throws SQLException {
        moduleTopicTitleMap.clear(); // Clear the existing data to avoid duplicates
        con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            // Establish the database connection
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Unique", "ca", "12");
            st = con.createStatement();

            // Fetch data from the Modules table
            String query = "SELECT * FROM Modules ORDER BY ModuleID"; // Add ORDER BY clause here
            rs = st.executeQuery(query);

            while (rs.next()) {
                String module = rs.getString("Module");
                moduleTopicTitleMap.put(module, new HashMap<>());
            }

            // Fetch data from the Topics table and populate the map
            for (String module : moduleTopicTitleMap.keySet()) {
                query = "SELECT * FROM Topics WHERE ModuleID = " + getModuleID(module) + " ORDER BY TopicID"; // Add ORDER BY clause here
                rs = st.executeQuery(query);
                Map<String, List<String>> topicTitleMap = new HashMap<>();
                while (rs.next()) {
                    String topic = rs.getString("Topic");
                    topicTitleMap.put(topic, new ArrayList<>());
                }
                moduleTopicTitleMap.put(module, topicTitleMap);
            }

            // Fetch data from the Subtopics table and populate the map
            for (String module : moduleTopicTitleMap.keySet()) {
                Map<String, List<String>> topicTitleMap = moduleTopicTitleMap.get(module);
                for (String topic : topicTitleMap.keySet()) {
                    query = "SELECT * FROM Subtopics WHERE TopicID = " + getTopicID(topic) + " ORDER BY SubID"; // Add ORDER BY clause here
                    rs = st.executeQuery(query);
                    List<String> titles = new ArrayList<>();
                    while (rs.next()) {
                        String title = rs.getString("Subtopic");
                        titles.add(title);
                    }
                    topicTitleMap.put(topic, titles);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    // Method to populate the JTree with data from the database
    private void populateJTree() {
        try {
            getDataFromDatabase();

            DefaultMutableTreeNode root = new DefaultMutableTreeNode("Modules");

            for (String module : moduleTopicTitleMap.keySet()) {
                DefaultMutableTreeNode moduleNode = new DefaultMutableTreeNode(module);

                Map<String, List<String>> topicTitleMap = moduleTopicTitleMap.get(module);
                for (String topic : topicTitleMap.keySet()) {
                    DefaultMutableTreeNode topicNode = new DefaultMutableTreeNode(topic);
                    moduleNode.add(topicNode);

                    List<String> titles = topicTitleMap.get(topic);
                    for (String title : titles) {
                        DefaultMutableTreeNode titleNode = new DefaultMutableTreeNode(title);
                        topicNode.add(titleNode);
                    }
                }

                root.add(moduleNode);
            }

            DefaultTreeModel treeModel = new DefaultTreeModel(root);
            jTree3.setModel(treeModel);
        } catch (SQLException e) {
        }
    }

    // Method to fetch data from the Users table and populate the JTable
    private void fetchUserData() {
        // Define the columns for the JTable, including the row number column
        String[] columns = {"#", "Name", "Profession", "Level of Study"};

        // Create a DefaultTableModel with the specified columns
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // Fetch data from the "Users" table and add it to the DefaultTableModel
        try ( Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Unique", "ca", "12");  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery("SELECT UserName, Profession, LevelOfStudy FROM Users")) {

            int rowNumber = 1;
            while (rs.next()) {
                String name = rs.getString("UserName");
                String profession = rs.getString("Profession");
                String levelOfStudy = rs.getString("LevelOfStudy");

                // Add the fetched data to the DefaultTableModel, including the row number
                model.addRow(new Object[]{rowNumber, name, profession, levelOfStudy});
                rowNumber++;
            }

        } catch (SQLException ex) {

        }

        // Set the DefaultTableModel as the model for the JTable
        jTable1.setModel(model);

        // Set a custom TableCellRenderer to adjust column margins
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Set left and right margins for the cells
                int leftMargin = 15;
                int rightMargin = 10;
                int topMargin = 5;
                int bottomMargin = 5;
                setBorder(BorderFactory.createEmptyBorder(topMargin, leftMargin, bottomMargin, rightMargin));

                // Adjust column widths here
                int columnWidth = 150; // Change the column width as desired
                table.getColumnModel().getColumn(column).setPreferredWidth(columnWidth);
                // Adjust row height here
                int rowHeight = 28; // Change the row height as desired
                table.setRowHeight(rowHeight);
                return c;
            }
        };
        // Set the custom renderer for all columns in the JTable
        for (int i = 0; i < jTable1.getColumnModel().getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        // Set a custom TableCellRenderer for the first column to align numbers on the left
        jTable1.getColumnModel().getColumn(0).setCellRenderer(new LeftAlignedTableCellRenderer());
        // Set a small width for the first column
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(10);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
        jTable1.getColumnModel().getColumn(0).setMinWidth(10);
    }

    // Custom TableCellRenderer to align cell contents to the left
    class LeftAlignedTableCellRenderer extends DefaultTableCellRenderer {

        public LeftAlignedTableCellRenderer() {
            setHorizontalAlignment(CENTER);
        }
    }

    public void insertNotes() {
        connect(); // ensure you have the database connection established

        try {
            String htmlContent = jEditorPane.getText(); // Retrieve HTML content from the editor pane

            String insertQuery = "INSERT INTO Subtopics (Notes) VALUES (?)";
            try (PreparedStatement preparedStatement = con.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, htmlContent);
                preparedStatement.executeUpdate();
            }
            con.close();

            JOptionPane.showMessageDialog(null, "Notes inserted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
        }
    }

    public Admin() {
        initComponents();
        jTree3.setVisible(false);
        jTextField4.setVisible(false);
        jButton4.setVisible(false);
        jButton5.setVisible(false);
        jTable1.setVisible(false);
        // Fetch data from the database and populate the components
        populateComboBox();
        populateComboBox2();
        populateComboBox4();
        fetchUserData();
        moduleTopicTitleMap = new HashMap<>();
        populateJTree();

        // Add "Select data" as the first item in jComboBox1
        jComboBox1.insertItemAt("Select Module", 0);
        jComboBox1.setSelectedIndex(0);
        jComboBox1.setEditable(false); // Disable editing

        jComboBox2.insertItemAt("Select Module", 0);
        jComboBox2.setSelectedIndex(0);
        jComboBox2.setEditable(false);

        jComboBox3.insertItemAt("Select Topic", 0);
        jComboBox3.setSelectedIndex(0);
        jComboBox3.setEditable(false);
        
        jComboBox4.insertItemAt("Select Module", 0);
        jComboBox4.setSelectedIndex(0);
        jComboBox4.setEditable(false);
        
        jComboBox5.insertItemAt("Right choice", 0);
        jComboBox5.setSelectedIndex(0);
        jComboBox5.setEditable(false);

        jComboBox6.insertItemAt("Select Topic", 0);
        jComboBox6.setSelectedIndex(0);
        jComboBox6.setEditable(false);

        // Calling the method to populate jComboBox2 based on the selected module in jComboBox1
        String selectedModule = jComboBox2.getSelectedItem().toString();
        if (!selectedModule.equals("Select Module")) {

            populateTopicsComboBox(selectedModule);
        }
        // Calling the method to populate jComboBox6 based on the selected module in jComboBox4
    String selectedModule1 = jComboBox4.getSelectedItem().toString();
    if (!selectedModule1.equals("Select Module")) {
       
        populateTopicsComboBox1(selectedModule);
    }
        
        //JEditorPane jEditorPane = new JEditorPane();
HTMLEditorKit editorKit = new HTMLEditorKit();
jEditorPane.setEditorKit(editorKit);


    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        mod = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        top = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        not = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jpmod = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField2 = new javax.swing.JTextField();
        jImageBtn = new javax.swing.JButton();
        jImageLabel = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jQuestion = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jExplanation = new javax.swing.JTextArea();
        jChoiceA = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jChoiceC = new javax.swing.JTextField();
        jChoiceD = new javax.swing.JTextField();
        jChoiceB = new javax.swing.JTextField();
        jComboBox5 = new javax.swing.JComboBox<>();
        jQnsubmitt = new javax.swing.JButton();
        jQnclear = new javax.swing.JButton();
        jComboBox7 = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        jEditorPane = new javax.swing.JEditorPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jptop = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree3 = new javax.swing.JTree();
        jTextField4 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jpnot = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(98, 154, 238));

        jPanel1.setBackground(new java.awt.Color(98, 154, 238));

        mod.setBackground(new java.awt.Color(255, 255, 255));
        mod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(98, 154, 238)));
        mod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                modMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Insert Data");

        javax.swing.GroupLayout modLayout = new javax.swing.GroupLayout(mod);
        mod.setLayout(modLayout);
        modLayout.setHorizontalGroup(
            modLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, modLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82))
        );
        modLayout.setVerticalGroup(
            modLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        top.setBackground(new java.awt.Color(190, 210, 241));
        top.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(98, 154, 238)));
        top.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                topMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Manage Details");

        javax.swing.GroupLayout topLayout = new javax.swing.GroupLayout(top);
        top.setLayout(topLayout);
        topLayout.setHorizontalGroup(
            topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        topLayout.setVerticalGroup(
            topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        not.setBackground(new java.awt.Color(190, 210, 241));
        not.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(98, 154, 238)));
        not.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                notMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Users");

        javax.swing.GroupLayout notLayout = new javax.swing.GroupLayout(not);
        not.setLayout(notLayout);
        notLayout.setHorizontalGroup(
            notLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notLayout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        notLayout.setVerticalGroup(
            notLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Admin Dashboard");

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(top, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(not, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(mod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(top, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(not, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setLayout(new javax.swing.OverlayLayout(jPanel3));

        jpmod.setBackground(new java.awt.Color(61, 98, 206));

        jTabbedPane1.setBackground(new java.awt.Color(61, 98, 206));

        jPanel2.setBackground(new java.awt.Color(61, 98, 206));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Add Module", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 16), new java.awt.Color(255, 255, 255))); // NOI18N

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(378, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jButton1)
                .addContainerGap(449, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Modules", jPanel2);

        jPanel4.setBackground(new java.awt.Color(61, 98, 206));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), "Insert Topic", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 16), new java.awt.Color(255, 255, 255))); // NOI18N

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jImageBtn.setText("choose image");
        jImageBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jImageBtnActionPerformed(evt);
            }
        });

        jImageLabel.setBackground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );

        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 148, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jImageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jImageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jImageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 229, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );

        jTabbedPane1.addTab("Topics", jPanel4);

        jPanel8.setBackground(new java.awt.Color(61, 98, 206));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Question:");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Explanation:");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Correct Choice:");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Choice A:");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Choice D:");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Choice B:");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox6ActionPerformed(evt);
            }
        });

        jQuestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jQuestionActionPerformed(evt);
            }
        });

        jExplanation.setColumns(20);
        jExplanation.setRows(5);
        jScrollPane3.setViewportView(jExplanation);

        jChoiceA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChoiceAActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Choice C:");

        jChoiceC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChoiceCActionPerformed(evt);
            }
        });

        jChoiceD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChoiceDActionPerformed(evt);
            }
        });

        jChoiceB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChoiceBActionPerformed(evt);
            }
        });

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D" }));

        jQnsubmitt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jQnsubmitt.setText("Submit");
        jQnsubmitt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jQnsubmittActionPerformed(evt);
            }
        });

        jQnclear.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jQnclear.setText("Clear");
        jQnclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jQnclearActionPerformed(evt);
            }
        });

        jComboBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jChoiceD)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3)
                .addGap(25, 25, 25))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(jQnsubmitt)
                .addGap(71, 71, 71)
                .addComponent(jQnclear, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jQuestion)
                            .addComponent(jChoiceA)
                            .addComponent(jChoiceC, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                            .addComponent(jChoiceB)))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jChoiceA, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jChoiceB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jChoiceC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jChoiceD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jQnsubmitt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jQnclear, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        jTabbedPane1.addTab("Questions", jPanel8);

        jPanel5.setBackground(new java.awt.Color(61, 98, 206));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), "Insert Sub-topic", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 16), new java.awt.Color(255, 255, 255))); // NOI18N

        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jButton3.setText("Add");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jScrollPane4.setViewportView(jEditorPane);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/font-style-bold(4).png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/color-wheel(1).png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/text-size.png"))); // NOI18N
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/focus.png"))); // NOI18N
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/stop-button.png"))); // NOI18N
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 29, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                                        .addGap(23, 23, 23))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap()))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap()))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(75, 75, 75)
                                        .addComponent(jLabel8))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121))))
        );

        jTabbedPane1.addTab("Sub-topics", jPanel5);

        javax.swing.GroupLayout jpmodLayout = new javax.swing.GroupLayout(jpmod);
        jpmod.setLayout(jpmodLayout);
        jpmodLayout.setHorizontalGroup(
            jpmodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jpmodLayout.setVerticalGroup(
            jpmodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jPanel3.add(jpmod);

        jptop.setBackground(new java.awt.Color(50, 200, 129));
        jptop.setPreferredSize(new java.awt.Dimension(568, 517));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Manage details");
        jLabel10.setToolTipText("");

        jTree3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTree3.setForeground(new java.awt.Color(50, 200, 129));
        jTree3.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree3ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTree3);

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jButton5.setText("Update");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton4.setText("Delete");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jptopLayout = new javax.swing.GroupLayout(jptop);
        jptop.setLayout(jptopLayout);
        jptopLayout.setHorizontalGroup(
            jptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jptopLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jptopLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5))
                        .addContainerGap(159, Short.MAX_VALUE))
                    .addGroup(jptopLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4))))
        );
        jptopLayout.setVerticalGroup(
            jptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jptopLayout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jptopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jptopLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(129, Short.MAX_VALUE))
                    .addGroup(jptopLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(141, 141, 141))))
        );

        jPanel3.add(jptop);

        jpnot.setBackground(new java.awt.Color(246, 118, 34));
        jpnot.setPreferredSize(new java.awt.Dimension(568, 517));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("List of users");
        jLabel11.setToolTipText("");

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Number", "Name", "Professionl", "Level of study"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(2);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(3);
        }

        javax.swing.GroupLayout jpnotLayout = new javax.swing.GroupLayout(jpnot);
        jpnot.setLayout(jpnotLayout);
        jpnotLayout.setHorizontalGroup(
            jpnotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnotLayout.createSequentialGroup()
                .addGroup(jpnotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnotLayout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnotLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnotLayout.setVerticalGroup(
            jpnotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnotLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(128, Short.MAX_VALUE))
        );

        jPanel3.add(jpnot);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 536, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 545, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void modMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_modMouseClicked
        // TODO add your handling code here:
        jpmod.setVisible(true);
        jptop.setVisible(false);
        jpnot.setVisible(false);
        mod.setBackground(Color.white);
        top.setBackground(new Color(190, 210, 241));
        not.setBackground(new Color(190, 210, 241));
    }//GEN-LAST:event_modMouseClicked

    private void topMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_topMouseClicked
        // TODO add your handling code here:
        jpmod.setVisible(false);
        jptop.setVisible(true);
        jpnot.setVisible(false);
        top.setBackground(Color.white);
        not.setBackground(new Color(190, 210, 241));
        mod.setBackground(new Color(190, 210, 241));
        jTree3.setVisible(true);
        jTextField4.setVisible(true);
        jButton4.setVisible(true);
        jButton5.setVisible(true);
    }//GEN-LAST:event_topMouseClicked

    private void notMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notMouseClicked
        // TODO add your handling code here:
        jpmod.setVisible(false);
        jptop.setVisible(false);
        jpnot.setVisible(true);
        jTable1.setVisible(true);
        not.setBackground(Color.white);
        top.setBackground(new Color(190, 210, 241));
        mod.setBackground(new Color(190, 210, 241));
    }//GEN-LAST:event_notMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {

            //String m= jTextField1.getText().trim();
            String id = jTextField1.getText().trim();
            if (!id.isEmpty()) {
                con = DriverManager.getConnection("jdbc:derby://localhost:1527/Unique", "ca", "12");
                Statement st = con.createStatement();
                // Check if the data already exists in the table
                ResultSet rs = st.executeQuery("SELECT * FROM Modules WHERE Module = '" + id + "'");
                if (rs.next()) {
                    // Data already exists, show error message
                    JOptionPane.showMessageDialog(this, "Module already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                    jTextField1.setText("");
                } else {
                    st.execute("insert into Modules(Module) values('" + id + "')");

                    JOptionPane.showMessageDialog(this, "Module inserted");
                    jTextField1.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid module name.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            populateComboBox();
            jComboBox1.insertItemAt("Select Module", 0);
            jComboBox1.setSelectedIndex(0);
            populateJTree();
        } catch (HeadlessException | SQLException e) {
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            String selectedModule = jComboBox1.getSelectedItem().toString();
            String topic = jTextField2.getText().trim();

            if (!selectedModule.isEmpty() && !topic.isEmpty() && imageData != null) {
                connect(); // Establish the database connection (you can use your existing connect() method)

                // Get the ModuleID for the selected module from the Modules table
                int moduleID = getModuleID(selectedModule);

                if (moduleID != -1) {
                    // Check if the data already exists in the table for the selected module and topic
                    String sql = "SELECT * FROM Topics WHERE ModuleID = ? AND Topic = ?";
                    try ( PreparedStatement ps = con.prepareStatement(sql)) {
                        ps.setInt(1, moduleID);
                        ps.setString(2, topic);

                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                // Data already exists for the selected module and topic, show error message
                                JOptionPane.showMessageDialog(this, "The topic for the Module already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                                jTextField2.setText("");
                            } else {
                                // Insert the data into the Topics table
                                sql = "INSERT INTO Topics (ModuleID, Topic, Image) VALUES (?, ?, ?)";
                                try ( PreparedStatement insertPs = con.prepareStatement(sql)) {
                                    insertPs.setInt(1, moduleID);
                                    insertPs.setString(2, topic);
                                    insertPs.setBytes(3, imageData); // Insert the image data as BLOB
                                    insertPs.executeUpdate();
                                }
                                
                                JOptionPane.showMessageDialog(this, "Data inserted");
                                jTextField2.setText("");
                                jImageLabel.setIcon(null); // Clear the image label after insertion
                            }
                            // populateComboBox();
                            populateComboBox2();
                            //jComboBox1.insertItemAt("Select Module", 0);
                            //jComboBox1.setSelectedIndex(0);
                            jComboBox2.insertItemAt("Select Topic", 0);
                            jComboBox2.setSelectedIndex(0);
                            
                            jComboBox3.insertItemAt("Select Subtopic", 0);
                            jComboBox3.setSelectedIndex(0);
                            populateJTree();
                        }

                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Selected module not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                con.close();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a Module, Topic, and Image.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException | SQLException e) {
            // Handle the exception appropriately
            
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        String selectedTopic = jComboBox3.getSelectedItem().toString().trim();
        String subtopic = jTextField3.getText().trim();
        String notes = jEditorPane.getText(); // Corrected name

        if (!selectedTopic.isEmpty() && !subtopic.isEmpty()) {
            try {
                // Establish the database connection
                connect(); // Ensure you have a valid database connection

                // Get the TopicID for the selected topic from the Topics table
                int topicID = getTopicID(selectedTopic);

                if (topicID != -1) {
                    // Check if the data already exists in the Subtopics table
                    String sql = "SELECT * FROM Subtopics WHERE TopicID = ? AND Subtopic = ?";
                    try ( PreparedStatement ps = con.prepareStatement(sql)) {
                        ps.setInt(1, topicID);
                        ps.setString(2, subtopic);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                // Data already exists for the selected TopicID and Subtopic, show error message
                                JOptionPane.showMessageDialog(this, "The Subtopic of that Topic already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                                jTextField3.setText("");
                            } else {
                                // Insert the data into the Subtopics table
                                sql = "INSERT INTO Subtopics (TopicID, Subtopic, Notes) VALUES (?, ?, ?)";
                                try ( PreparedStatement insertPs = con.prepareStatement(sql)) {
                                    insertPs.setInt(1, topicID);
                                    insertPs.setString(2, subtopic);
                                    insertPs.setString(3, notes);
                                    insertPs.executeUpdate();
                                }
                                
                                JOptionPane.showMessageDialog(this, "Data inserted into Subtopics");
                                jTextField3.setText("");
                                jEditorPane.setText(""); // Clear the notes field
                            }
                        }
                    }

                    // Call the populateJTree() method here after successfully inserting subtopic data
                    populateJTree();
                } else {
                    JOptionPane.showMessageDialog(this, "Selected topic not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (HeadlessException | SQLException e) {
                // Handle the exception appropriately
                
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a topic, enter a subtopic, and add notes.", "Error", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_jButton3ActionPerformed
    // Helper method to get the TopicID based on the selected topic

    private int getTopicID(String selectedTopic) {
        int topicID = -1; // Initialize to a default value in case the topic is not found
        try {
            try ( // Establish the database connection
                    Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Unique", "ca", "12"); Statement st = con.createStatement()) {
                
                // Execute a query to retrieve the TopicID for the selected topic
                String query = "SELECT TopicID FROM Topics WHERE Topic = '" + selectedTopic + "'";
                ResultSet rs = st.executeQuery(query);
                
                // Check if the topic is found and get its TopicID
                if (rs.next()) {
                    topicID = rs.getInt("TopicID");
                }
                
                // Close the resources
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
        return topicID;
    }

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        String selectedModule = jComboBox1.getSelectedItem().toString();
        if (selectedModule.equals("Select data") || selectedModule.isEmpty()) {
            jTextField2.setEnabled(false); 
        } else {
            jTextField2.setEnabled(true); 

        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        String selectedModule = jComboBox2.getSelectedItem().toString();
        if (selectedModule.equals("Select data") || selectedModule.isEmpty()) {
            jTextField3.setEnabled(false); // Disable jTextField3 when no module is selected
        } else {
            if (!selectedModule.isEmpty()) {
                jTextField3.setEnabled(true); // Enable jTextField3 when a module is selected           
                populateTopicsComboBox(selectedModule);
            }
        }

        // String selectedModule = jComboBox2.getSelectedItem().toString();

    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed

    }//GEN-LAST:event_jTextField4ActionPerformed


    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String selectedData = jTextField4.getText();
        if (!selectedData.isEmpty()) {
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this data?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                boolean dataDeleted = false; // Flag to track if data was actually deleted

                try ( Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Unique", "ca", "12")) {
                    // Check if the selected data is a module
                    boolean isModule = isModule(selectedData, conn);
                    if (isModule) {
                        dataDeleted = deleteModuleAndTopics(selectedData, conn);
                    } else {
                        // Check if the selected data is a topic
                        boolean isTopic = isTopic(selectedData, conn);
                        if (isTopic) {
                            dataDeleted = deleteTopicAndTitles(selectedData, conn);
                        } else {
                            dataDeleted = deleteTitle(selectedData, conn);
                        }
                    }

                    if (dataDeleted) {
                        JOptionPane.showMessageDialog(this, "Data deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }

                    jTextField4.setText("");
                    populateComboBox();
                    populateComboBox2();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting data from the database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select data to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton4ActionPerformed
// Helper method to check if the selected data is a module

    private boolean isModule(String data, Connection con) throws SQLException {
        try ( PreparedStatement ps = con.prepareStatement("SELECT Module FROM Modules WHERE Module = ?")) {
            ps.setString(1, data);
            try ( ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

// Helper method to check if the selected data is a topic
    private boolean isTopic(String data, Connection con) throws SQLException {
        try ( PreparedStatement ps = con.prepareStatement("SELECT Topic FROM Topics WHERE Topic = ?")) {
            ps.setString(1, data);
            try ( ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private boolean deleteModuleAndTopics(String module, Connection con) throws SQLException {
        boolean hasRelatedTopics = hasRelatedTopics(module, con);
        boolean hasRelatedSubtopics = hasRelatedSubtopics(module, con);

        if (hasRelatedTopics || hasRelatedSubtopics) {
            int option = JOptionPane.showConfirmDialog(this, "The selected module has related topics. Deleting it will also delete all related data . Are you sure you want to delete?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.NO_OPTION) {
                return false; // User chose not to delete the module and its related data, so return without deleting anything
            }
        }

        try ( PreparedStatement ps = con.prepareStatement("DELETE FROM Modules WHERE Module = ?")) {
            ps.setString(1, module);
            ps.executeUpdate();
        }

        try ( PreparedStatement ps = con.prepareStatement("DELETE FROM Topics WHERE ModuleID IN (SELECT ModuleID FROM Modules WHERE Module = ?)")) {
            ps.setString(1, module);
            ps.executeUpdate();
        }

        try ( PreparedStatement ps = con.prepareStatement("DELETE FROM Subtopics WHERE TopicID IN (SELECT TopicID FROM Topics WHERE ModuleID IN (SELECT ModuleID FROM Modules WHERE Module = ?))")) {
            ps.setString(1, module);
            ps.executeUpdate();
        }
        populateJTree();
        return true; // Data was deleted successfully

    }

    private boolean deleteTopicAndTitles(String topic, Connection con) throws SQLException {
        boolean hasRelatedSubtopics = hasRelatedSubtopics(topic, con);

        if (hasRelatedSubtopics) {
            int option = JOptionPane.showConfirmDialog(this, "The selected topic has related subtopics and notes. Deleting it will also delete all related data. Are you sure you want to delete the topic directly?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.NO_OPTION) {
                return false; // User chose not to delete the topic and its related subtopics, so return without deleting anything
            }
        }

        try ( PreparedStatement ps = con.prepareStatement("DELETE FROM Topics WHERE Topic = ?")) {
            ps.setString(1, topic);
            ps.executeUpdate();
        }

        try ( PreparedStatement ps = con.prepareStatement("DELETE FROM Subtopics WHERE TopicID IN (SELECT TopicID FROM Topics WHERE Topic = ?)")) {
            ps.setString(1, topic);
            ps.executeUpdate();
        }

        populateJTree();
        return true; // Data was deleted successfully

    }

// Helper method to delete a subtopic (title)
    private boolean deleteTitle(String title, Connection con) throws SQLException {
        try ( PreparedStatement ps = con.prepareStatement("DELETE FROM Subtopics WHERE Subtopic = ?")) {
            ps.setString(1, title);
            ps.executeUpdate();
        }

        populateJTree();
        return true;
    }
// Helper method to check if the module has related topics

    private boolean hasRelatedTopics(String module, Connection con) throws SQLException {
        try ( PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM Topics WHERE ModuleID IN (SELECT ModuleID FROM Modules WHERE Module = ?)")) {
            ps.setString(1, module);
            try ( ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

// Helper method to check if the module or topic has related subtopics
    private boolean hasRelatedSubtopics(String moduleOrTopic, Connection con) throws SQLException {
        try ( PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM Subtopics WHERE TopicID IN (SELECT TopicID FROM Topics WHERE ModuleID IN (SELECT ModuleID FROM Modules WHERE Module = ?) OR Topic = ?)")) {
            ps.setString(1, moduleOrTopic);
            ps.setString(2, moduleOrTopic);
            try ( ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }


    private void jTree3ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree3ValueChanged
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree3.getLastSelectedPathComponent();
        if (selectedNode == null) {
            return;
        }

        // Check if the selected node is the root node (depth 0)
        if (selectedNode.getLevel() == 0) {
            // Depth 0 (root node) is not allowed to be changed, show a dialog
            // JOptionPane.showMessageDialog(this, "You cannot change the root node.", "Invalid Selection", JOptionPane.WARNING_MESSAGE);
            jTextField4.setText(""); // Clear the text field to avoid confusion
            return;
        }

        Object selectedData = selectedNode.getUserObject();
        if (selectedData instanceof String string) {
            jTextField4.setText(string);

            // Determine the type of data (Module, Topic, or Title) based on the selected node's depth
            int depth = selectedNode.getLevel();

            selectedDataType = switch (depth) {
                case 1 ->
                    "Module";
                case 2 ->
                    "Topic";
                case 3 ->
                    "Title";
                default ->
                    null; // Unknown data type (handle as needed in your application)
            };
        }

    }//GEN-LAST:event_jTree3ValueChanged

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String newData = jTextField4.getText().trim();
        if (newData.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please choose the data you want to update.", "No Data Selected", JOptionPane.WARNING_MESSAGE);
            return; // No data to update, do nothing
        }

        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree3.getLastSelectedPathComponent();
        if (selectedNode == null) {
            JOptionPane.showMessageDialog(this, "Please select data from the JTree before updating.", "No Data Selected", JOptionPane.WARNING_MESSAGE);
            return; // No selection, do nothing
        }

        Object selectedData = selectedNode.getUserObject();
        if (selectedData instanceof String string) {
            int depth = selectedNode.getLevel();
            switch (depth) {
                case 1 -> // Update Module
                    updateModule(string, newData);
                case 2 -> {
                    // Update Topic
                    String moduleName = ((DefaultMutableTreeNode) selectedNode.getParent()).getUserObject().toString();
                    updateTopic(moduleName, string, newData);
                }
                case 3 -> {
                    // Update Title (Subtopic)
                    String topicName = ((DefaultMutableTreeNode) selectedNode.getParent()).getUserObject().toString();
                    String parentModuleName = ((DefaultMutableTreeNode) selectedNode.getParent().getParent()).getUserObject().toString();
                    updateTitle(parentModuleName, topicName, string, newData);
                }
                default -> {
                }
            }
            // Unknown data type (handle as needed in your application)
                    }

    }

// Helper method to update a Module
    private void updateModule(String currentModule, String newModule) {
        if (newModule.equals(currentModule)) {
            // Show a dialog indicating no changes have been made
            JOptionPane.showMessageDialog(this, "No changes have been made.", "No Changes", JOptionPane.INFORMATION_MESSAGE);
            return; // No changes, do nothing
        }
        String updateQuery = "UPDATE Modules SET Module = ? WHERE Module = ?";
        try ( Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Unique", "ca", "12");  PreparedStatement ps = con.prepareStatement(updateQuery)) {

            ps.setString(1, newModule);
            ps.setString(2, currentModule);

            ps.executeUpdate();
            con.commit(); // Commit the transaction

            JOptionPane.showMessageDialog(this, "Module updated successfully in the database.");
            jTextField4.setText("");
            populateComboBox();
            populateComboBox2();
            populateJTree(); // Refresh the JTree to update its content after updating
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating data in the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

// Helper method to update a Topic
    private void updateTopic(String moduleName, String currentTopic, String newTopic) {
        if (newTopic.equals(currentTopic)) {
            // Show a dialog indicating no changes have been made
            JOptionPane.showMessageDialog(this, "No changes have been made.", "No Changes", JOptionPane.INFORMATION_MESSAGE);
            return; // No changes, do nothing
        }
        String updateQuery = "UPDATE Topics SET Topic = ? WHERE ModuleID IN (SELECT ModuleID FROM Modules WHERE Module = ?) AND Topic = ?";
        try ( Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Unique", "ca", "12");  PreparedStatement ps = con.prepareStatement(updateQuery)) {

            ps.setString(1, newTopic);
            ps.setString(2, moduleName);
            ps.setString(3, currentTopic);

            ps.executeUpdate();
            con.commit(); // Commit the transaction

            JOptionPane.showMessageDialog(this, "Data updated successfully in the database.");
            jTextField4.setText("");
            populateComboBox();
            populateComboBox2();
            populateJTree(); // Refresh the JTree to update its content after updating
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating data in the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

// Helper method to update a Title (Subtopic)
    private void updateTitle(String moduleName, String topicName, String currentTitle, String newTitle) {
        if (newTitle.equals(currentTitle)) {
            // Show a dialog indicating no changes have been made
            JOptionPane.showMessageDialog(this, "No changes have been made.", "No Changes", JOptionPane.INFORMATION_MESSAGE);
            return; // No changes, do nothing
        }
        String updateQuery = "UPDATE Subtopics SET Subtopic = ? WHERE TopicID IN (SELECT TopicID FROM Topics WHERE ModuleID IN (SELECT ModuleID FROM Modules WHERE Module = ?) AND Topic = ?) AND Subtopic = ?";
        try ( Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Unique", "ca", "12");  PreparedStatement ps = con.prepareStatement(updateQuery)) {

            ps.setString(1, newTitle);
            ps.setString(2, moduleName);
            ps.setString(3, topicName);
            ps.setString(4, currentTitle);

            ps.executeUpdate();
            con.commit(); // Commit the transaction

            JOptionPane.showMessageDialog(this, "Data updated successfully in the database.");
            jTextField4.setText("");
            populateComboBox();
            populateComboBox2();
            populateJTree(); // Refresh the JTree to update its content after updating
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating data in the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_jButton5ActionPerformed

    private void jImageBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jImageBtnActionPerformed

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Images (JPEG, PNG)", "jpg", "jpeg", "png"));
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedImageFile = fileChooser.getSelectedFile();
                imageData = Files.readAllBytes(selectedImageFile.toPath());

                ImageIcon imageIcon = new ImageIcon(imageData);
                Image scaledImage = imageIcon.getImage().getScaledInstance(jImageLabel.getWidth(), jImageLabel.getHeight(), Image.SCALE_SMOOTH);
                jImageLabel.setIcon(new ImageIcon(scaledImage));
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }
        }


    }//GEN-LAST:event_jImageBtnActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
      
        applyFormatting("[b]", "[/b]"); // You can choose any marker here
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
         // Show a dialog or input box to get the font size     
        String input;
        while (true) {
            input = JOptionPane.showInputDialog("Enter Font Size (numeric only):");
            if (input == null || input.matches("\\d+")) {
                break; // Valid input, or the user cancels
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a numeric value.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (input != null) {
            String fontSize = input;
            applyFontSizeFormatting("[size" + fontSize + "]", "[/size" + fontSize + "]");
        }
    }//GEN-LAST:event_jLabel7MouseClicked

    
    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
                                     
    Color selectedColor = JColorChooser.showDialog(null, "Choose Text Color", Color.BLACK);
    if (selectedColor != null) {
        applyColorFormatting(selectedColor);
    }

    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
   
        // Open a file dialog to choose an image
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Now, you can insert the selected image at the current cursor position
            String imagePath = selectedFile.getAbsolutePath();
            String imageTag = "<img src='" + imagePath + "'>";
            insertHTMLAtCursor(imageTag);
        }
    

    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked

    String marker = "[Ep]"; // The marker you want to copy

    // Get the system clipboard
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    // Create a StringSelection object to hold the marker
    StringSelection selection = new StringSelection(marker);

    // Set the clipboard contents to the marker
    clipboard.setContents(selection, null);
    
    // Display a message indicating that the marker is copied next to the label
    jLabel12.setText("copied");
    jLabel12.setVisible(true);

    // Start a timer to hide the copied message after 2 seconds
    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            jLabel12.setText("");
            jLabel12.setVisible(false);
        }
    });
    timer.setRepeats(false); // Set the timer to only run once
    timer.start();

    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
                                            
    Object selectedModuleObj = jComboBox4.getSelectedItem();
    
    if (selectedModuleObj != null) {
        String selectedModule = selectedModuleObj.toString();
        
        if (!selectedModule.isEmpty() && !"Select data".equals(selectedModule)) {
            populateTopicsComboBox1(selectedModule);
        }
    }


    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jComboBox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox6ActionPerformed
                                          
    Object selectedModuleObj = jComboBox4.getSelectedItem();
    Object selectedTopicObj = jComboBox6.getSelectedItem();

    // Check if selectedModuleObj and selectedTopicObj are not null
    if (selectedModuleObj != null && selectedTopicObj != null) {
        String selectedModule = selectedModuleObj.toString();
        String selectedTopic = selectedTopicObj.toString();
        int topicID = -1;

        // Retrieve the TopicID based on the selected module and topic
        try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/Unique", "ca", "12");
             Statement statement = connection.createStatement()) {
            String query = "SELECT TopicID FROM Topics WHERE ModuleID = (SELECT ModuleID FROM Modules WHERE Module = '" + selectedModule + "') AND Topic = '" + selectedTopic + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                topicID = resultSet.getInt("TopicID");
            }
        } catch (SQLException e) {
            // Handle SQL exception
        }

        // Fetch and populate jComboBox7 with QuestionID values for the selected topic
        populateComboBox7(topicID);
    } else {
        // Handle the case where either selectedModuleObj or selectedTopicObj is null
        // You can log a message or perform any other appropriate action
    }


    }//GEN-LAST:event_jComboBox6ActionPerformed
    // Add a method to populate jComboBox7 based on the selected topic's TopicID
private void populateComboBox7(int topicID) {
    try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/Unique", "ca", "12");
         Statement statement = connection.createStatement()) {
        String query = "SELECT QuestionID FROM Questions WHERE TopicID = " + topicID;
        ResultSet resultSet = statement.executeQuery(query);

        // Clear the existing items in the combo box
        jComboBox7.removeAllItems();

        // Iterate through the result set and add items to the combo box
        while (resultSet.next()) {
            int questionID = resultSet.getInt("QuestionID");
            jComboBox7.addItem(String.valueOf(questionID));
        }
    } catch (SQLException e) {
    }
}


// Add a method to fetch and display the data for the selected QuestionID
private void displayQuestionData(int questionID) {
    // Fetch the data for the selected QuestionID
    try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/Unique", "ca", "12");
        Statement statement = connection.createStatement()) {
        String query = "SELECT QuestionText, ChoiceA, ChoiceB, ChoiceC, ChoiceD, CorrectChoice, Explanation FROM Questions WHERE QuestionID = " + questionID;
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            String questionText = resultSet.getString("QuestionText");
            String choiceA = resultSet.getString("ChoiceA");
            String choiceB = resultSet.getString("ChoiceB");
            String choiceC = resultSet.getString("ChoiceC");
            String choiceD = resultSet.getString("ChoiceD");
            String correctChoice = resultSet.getString("CorrectChoice");
            String explanation = resultSet.getString("Explanation");

            // Display the data in the text fields and combo box
            jQuestion.setText(questionText);
            jChoiceA.setText(choiceA);
            jChoiceB.setText(choiceB);
            jChoiceC.setText(choiceC);
            jChoiceD.setText(choiceD);
            jComboBox5.setSelectedItem(correctChoice);
            jExplanation.setText(explanation);
        }
    } catch (SQLException e) {
    }
}
    private void jQuestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jQuestionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jQuestionActionPerformed

    private void jChoiceAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChoiceAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jChoiceAActionPerformed

    private void jChoiceCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChoiceCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jChoiceCActionPerformed

    private void jChoiceDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChoiceDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jChoiceDActionPerformed

    private void jChoiceBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChoiceBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jChoiceBActionPerformed

    private void jQnsubmittActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jQnsubmittActionPerformed
                                             
    // Retrieve the selected module and topic
    String selectedModule = jComboBox4.getSelectedItem().toString();
    String selectedTopic = jComboBox6.getSelectedItem().toString();
    int topicID = -1;

    // Retrieve the TopicID based on the selected module and topic
    try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/Unique", "ca", "12");
        Statement statement = connection.createStatement()) {
        String query = "SELECT TopicID FROM Topics WHERE ModuleID = (SELECT ModuleID FROM Modules WHERE Module = '" + selectedModule + "') AND Topic = '" + selectedTopic + "'";
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            topicID = resultSet.getInt("TopicID");
        }
    } catch (SQLException e) {
    }

    // Retrieve the question, choices, correct choice, and explanation
    String questionText = jQuestion.getText();
    String choiceA = jChoiceA.getText();
    String choiceB = jChoiceB.getText();
    String choiceC = jChoiceC.getText();
    String choiceD = jChoiceD.getText();
    String correctChoice = jComboBox5.getSelectedItem().toString();
    String explanation = jExplanation.getText();

    // Check if any of the fields is empty
    if (questionText.isEmpty() || choiceA.isEmpty() || choiceB.isEmpty() || choiceC.isEmpty() || choiceD.isEmpty() || correctChoice.isEmpty() || explanation.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        return; // Prevent insertion if any field is empty
    }

    // Insert the question into the database
    try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/Unique", "ca", "12");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Questions (TopicID, QuestionText, ChoiceA, ChoiceB, ChoiceC, ChoiceD, CorrectChoice, Explanation) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

        preparedStatement.setInt(1, topicID);
        preparedStatement.setString(2, questionText);
        preparedStatement.setString(3, choiceA);
        preparedStatement.setString(4, choiceB);
        preparedStatement.setString(5, choiceC);
        preparedStatement.setString(6, choiceD);
        preparedStatement.setString(7, correctChoice);
        preparedStatement.setString(8, explanation);

        preparedStatement.executeUpdate();

        // Display a success message
        JOptionPane.showMessageDialog(this, "Question has been inserted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        // Clear the fields
        jQuestion.setText("");
        jChoiceA.setText("");
        jChoiceB.setText("");
        jChoiceC.setText("");
        jChoiceD.setText("");
        jComboBox5.setSelectedIndex(0); // Reset the combo box selection
        jExplanation.setText("");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, " Ensure you have selected a Module and/or the correct choice.", "Error", JOptionPane.ERROR_MESSAGE);
    }


    }//GEN-LAST:event_jQnsubmittActionPerformed

    private void jQnclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jQnclearActionPerformed
         // Clear the fields
        jQuestion.setText("");
        jChoiceA.setText("");
        jChoiceB.setText("");
        jChoiceC.setText("");
        jChoiceD.setText("");
        jComboBox5.setSelectedIndex(0); 
        jExplanation.setText("");
        jComboBox4.setSelectedIndex(0);
        jComboBox6.setSelectedIndex(0);
        
    }//GEN-LAST:event_jQnclearActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        // Retrieve the selected QuestionID from jComboBox7
      Object selectedItem = jComboBox7.getSelectedItem();
    if (selectedItem != null) {
        selectedQuestionID = (Integer) selectedItem; // Cast the selected item to an integer

        // Fetch and display the data for the selected QuestionID
        displayQuestionData(selectedQuestionID);
    }
    }//GEN-LAST:event_jComboBox7ActionPerformed
    private void insertHTMLAtCursor(String html) {
    HTMLEditorKit editorKit = (HTMLEditorKit) jEditorPane.getEditorKit();
    Document document = jEditorPane.getDocument();

    try {
        int caretPosition = jEditorPane.getCaretPosition();
        editorKit.insertHTML((HTMLDocument) document, caretPosition, html, 0, 0, HTML.Tag.SPAN);
    } catch (BadLocationException | IOException e) {
    }
}


    
    private void applyColorFormatting(Color selectedColor) {
    // Get the RGB values of the selected color
    int red = selectedColor.getRed();
    int green = selectedColor.getGreen();
    int blue = selectedColor.getBlue();
    
    // Format the color in RGB notation
    String colorMarker = String.format("[color=%d,%d,%d]", red, green, blue);
    
    // Get the selected text
    String selectedText = jEditorPane.getSelectedText();
    
    // Remove the selected text from the document
    int start = jEditorPane.getSelectionStart();
    int end = jEditorPane.getSelectionEnd();
    try {
        jEditorPane.getDocument().remove(start, end - start);
    } catch (BadLocationException e) {
    }
    
    // Insert the formatted text at the current caret position
    int caretPosition = jEditorPane.getCaretPosition();
    try {
        jEditorPane.getDocument().insertString(caretPosition, colorMarker + selectedText + "[/color]", null);
    } catch (BadLocationException e) {
    }
}
    private Color parseColorTag(String colorTag) {
    // Extract the color value from the color tag
    String colorHex = colorTag.substring(7, colorTag.length() - 1); // Remove "[color" and "]"

    try {
        // Convert the hexadecimal color representation to a Color object
        return Color.decode(colorHex);
    } catch (NumberFormatException e) {
        // Handle parsing error (e.g., invalid color format)
        return Color.BLACK; // Default color
    }
}

    
    
    private void applyFontSizeFormatting(String openTag, String closeTag) {
    String selectedText = jEditorPane.getSelectedText();
    if (selectedText != null && !selectedText.isEmpty()) {
        // Get the start and end of the selection
        int selectionStart = jEditorPane.getSelectionStart();
        int selectionEnd = jEditorPane.getSelectionEnd();
        
        // Get the existing text
        String text = jEditorPane.getText();
        
        // Insert the markers around the selected text
        String newText = text.substring(0, selectionStart) + openTag + 
                selectedText + closeTag + text.substring(selectionStart + selectedText.length());
         jEditorPane.setText(newText);
    }
}


    private void applyFormatting(String openTag, String closeTag) {
    String selectedText = jEditorPane.getSelectedText();
    if (selectedText != null && !selectedText.isEmpty()) {
        String text = jEditorPane.getText();
        int selectionStart = jEditorPane.getSelectionStart();
        int selectionEnd = jEditorPane.getSelectionEnd();
        String newText = text.substring(0, selectionStart) + openTag + selectedText + closeTag + text.substring(selectionEnd);
        jEditorPane.setText(newText);
    }
}
// Helper method to get the ModuleID based on the module name

    private int getModuleID(String moduleName) {
        int moduleID = -1; // Initialize to a default value in case the module is not found
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT ModuleID FROM Modules WHERE Module = '" + moduleName + "'");
            if (rs.next()) {
                moduleID = rs.getInt("ModuleID");
            }
            rs.close();
        } catch (SQLException e) {
        }
        return moduleID;
    }

    public static void main(String args[]) {

        // Establish the database connection
        // Admin admin = new Admin();
        // admin.connect();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JTextField jChoiceA;
    private javax.swing.JTextField jChoiceB;
    private javax.swing.JTextField jChoiceC;
    private javax.swing.JTextField jChoiceD;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JEditorPane jEditorPane;
    private javax.swing.JTextArea jExplanation;
    private javax.swing.JButton jImageBtn;
    private javax.swing.JLabel jImageLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JButton jQnclear;
    private javax.swing.JButton jQnsubmitt;
    private javax.swing.JTextField jQuestion;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTree jTree3;
    private javax.swing.JPanel jpmod;
    private javax.swing.JPanel jpnot;
    private javax.swing.JPanel jptop;
    private javax.swing.JPanel mod;
    private javax.swing.JPanel not;
    private javax.swing.JPanel top;
    // End of variables declaration//GEN-END:variables
}
