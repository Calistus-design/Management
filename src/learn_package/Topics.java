
package learn_package;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Topics extends javax.swing.JFrame {
private int moduleID; 
 


    public Topics() {
        initComponents();
        //updateTopicsPanel(moduleID); // Fetch and display the topics for the given moduleID
    }
     public void displaySubtopics(String moduleName, List<String> subtopics) {
        // Clear existing components from jPanel17
        jPanel17.removeAll();

        // Set layout for jPanel17
        jPanel17.setLayout(new BoxLayout(jPanel17, BoxLayout.Y_AXIS));
        jPanel17.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create and add JLabels for each subtopic
        for (String subtopic : subtopics) {
            JLabel subtopicLabel = new JLabel(subtopic);
            subtopicLabel.setFont(subtopicLabel.getFont().deriveFont(16.0f));
            jPanel17.add(subtopicLabel);
        }

        // Refresh jPanel17 to update the UI
        jPanel17.revalidate();
        jPanel17.repaint();
    }

    void openTopicsFrame() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
    
    
    
    
    
    
    public class TopicData {
    private final int topicID;
    private final String topic;
    private final byte[] imageBytes;

    public TopicData(int topicID, String topic, byte[] imageBytes) {
        this.topicID = topicID;
        this.topic = topic;
        this.imageBytes = imageBytes;
    }

    public int getTopicID() {
        return topicID;
    }

    public String getTopic() {
        return topic;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }
}


    // Parameterized constructor that takes the moduleID as an argument
    public Topics(int moduleID) {
        initComponents();
        this.moduleID = moduleID;
        // Fetch and display the topics for the given moduleID
        List<TopicData> topics = fetchTopicsForModule(moduleID);
        displayTopics(topics);
        showTopicsForModule(moduleID); // Call the method to display topics for the given moduleID
    }

    // Method to fetch list of topics for a given ModuleID from the database
    private List<TopicData> fetchTopicsForModule(int moduleID) {
    List<TopicData> topics = new ArrayList<>();

    try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Unique", "ca", "12");
         PreparedStatement ps = con.prepareStatement("SELECT TopicID,Topic, Image FROM Topics WHERE ModuleID = ?");
    ) {
        ps.setInt(1, moduleID);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int topicID = rs.getInt("TopicID"); // Get the TopicID from the ResultSet
            String topic = rs.getString("Topic");
            Blob imageBlob = rs.getBlob("Image");
            byte[] imageBytes = imageBlob.getBytes(1, (int) imageBlob.length());
            topics.add(new TopicData(topicID,topic, imageBytes));
        }

    } catch (SQLException ex) {
    }

    return topics;
}


   // Method to display the list of topics with resized images in the JPanel
private void displayTopics(List<TopicData> topics) {
    jPanel17.removeAll(); // Clear existing components

    if (topics.isEmpty()) {
        JLabel noTopicsLabel = new JLabel("There are no topics for this module.");
        noTopicsLabel.setFont(noTopicsLabel.getFont().deriveFont(16.0f));
        jPanel17.add(noTopicsLabel);
    } else {
        int itemsPerRow = 3; // Number of items to display per row
        int row = 0;
        int imageWidth = 100; // Set the desired width for the images
        int imageHeight = 100; // Set the desired height for the images

        jPanel17.setLayout(new GridLayout(0, itemsPerRow, 100, 100)); // 10px gap between items

        for (int i = 0; i < topics.size(); i++) {
            TopicData topicData = topics.get(i);
            String moduleName = (i + 1) + ". " + topicData.getTopic();
            byte[] imageData = topicData.getImageBytes();

             // Use a boolean to determine if the topic should be enabled or disabled
            boolean isEnabled = (i == 0); // Enable only the first topic

            ImageTopicPanel panel = new ImageTopicPanel(imageData, moduleName, topicData.getTopicID(), isEnabled, imageWidth, imageHeight);
            jPanel17.add(panel);

            row++;
        }
    }

    jPanel17.revalidate();
    jPanel17.repaint();
}

// Custom panel to hold image and topic
class ImageTopicPanel extends JPanel {
 private String topic; // Declare topic as a member variable
    private final boolean isHighlighted = false;
        private final int topicID;

    public ImageTopicPanel(byte[] imageData, String moduleName, int topicID, boolean isEnabled, int width, int height) {
    // Assign the topicID to the member variable
    this.topicID = topicID;

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add space around the panel

    ImageIcon icon = new ImageIcon(imageData);
    Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    ImageIcon resizedIcon = new ImageIcon(image);

    JLabel imageLabel = new JLabel(resizedIcon);
    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Align the image to the center

    JLabel moduleLabel = new JLabel(moduleName);
    moduleLabel.setFont(moduleLabel.getFont().deriveFont(16.0f));
    moduleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Align the module name to the center

    add(imageLabel);
    add(moduleLabel);

    // Add MouseListener to the panel only if it is enabled
    if (isEnabled) {
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                // Change the text color to red when hovering over the image
                moduleLabel.setForeground(Color.RED);
                // Show a tooltip if the topic is disabled
            if (!isEnabled) {
                setToolTipText("Complete the first topic to unlock");
            }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                moduleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                // Reset the text color when the mouse exits the panel
                moduleLabel.setForeground(Color.BLACK);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Open the Subs JFrame when the image is clicked
                openSubsFrame(topicID);
            }
        });
    }
    if (!isEnabled) {
       // Create a JToolTip with the desired message
    JToolTip toolTip = new JToolTip();
    toolTip.setTipText("Complete the previous topic to unlock");
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                moduleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                // Change the text color to red when hovering over the image
                moduleLabel.setForeground(Color.GRAY);
                // Show a tooltip if the topic is disabled
            
                imageLabel.setToolTipText("Complete the previous topic to unlock");
            
            }

            @Override
            public void mouseExited(MouseEvent e) {
                moduleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                // Reset the text color when the mouse exits the panel
                moduleLabel.setForeground(Color.BLACK);
            }

          
        });
    }

}

    
    // Method to fetch subtopics based on the TopicID from the database
    public static List<String> fetchSubtopicsForTopic(int topicID) {
        List<String> subtopics = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Unique", "ca", "12");
             PreparedStatement ps = con.prepareStatement("SELECT Subtopic FROM Subtopics WHERE TopicID = ?");
        ) {
            ps.setInt(1, topicID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String subtopic = rs.getString("Subtopic");
                subtopics.add(subtopic);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return subtopics;
    }
    
     // Method to open the Subs JFrame
    private void openSubsFrame(int topicID) {
    Subs subsFrame = new Subs(topicID);
    List<String> subtopics = fetchSubtopicsForTopic(topicID);
    subsFrame.displaySubtopics(subtopics);
    subsFrame.setVisible(true);
      dispose();
}
    public void openTopicsFrame() {
    Topics topicsFrame = new Topics();
    topicsFrame.setVisible(true);
    dispose(); // Close the current frame (Subs.java)
}


    // Override paintComponent to draw the highlight when needed
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isHighlighted) {
            g.setColor(Color.RED); // Light blue color with transparency
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}




// Method to show the topics for the selected module in jPanel16
private void showTopicsForModule(int moduleID) {
    List<TopicData> topics = fetchTopicsForModule(moduleID);

    jPanel17.removeAll(); // Clear existing components

    int itemsPerRow = 3; // Number of items to display per row
    int row = 0;
    int imageWidth = 100; // Set the desired width for the images
    int imageHeight = 100; // Set the desired height for the images

    jPanel17.setLayout(new GridLayout(0, itemsPerRow, 10, 10)); // 10px gap between items

    for (int i = 0; i < topics.size(); i++) {
        TopicData topicData = topics.get(i);
        byte[] imageData = topicData.getImageBytes(); // Get the image data from TopicData

        // Only enable the first topic, disable others
        boolean isEnabled = (i == 0);

        ImageTopicPanel panel = new ImageTopicPanel(imageData, topicData.getTopic(), topicData.getTopicID(), isEnabled, imageWidth, imageHeight);
        jPanel17.add(panel);

        row++;
    }

    jPanel17.revalidate();
    jPanel17.repaint();
}



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel17.setLayout(new javax.swing.BoxLayout(jPanel17, javax.swing.BoxLayout.LINE_AXIS));

        jLabel19.setBackground(new java.awt.Color(204, 204, 204));
        jLabel19.setFont(new java.awt.Font("Verdana Pro Cond", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(98, 154, 238));
        jLabel19.setText("Learning Platform");
        jLabel19.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel38.setBackground(new java.awt.Color(204, 204, 204));
        jLabel38.setFont(new java.awt.Font("Verdana Pro Cond", 1, 24)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(98, 154, 238));
        jLabel38.setText("Topics");
        jLabel38.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel20.setBackground(new java.awt.Color(204, 204, 204));
        jLabel20.setFont(new java.awt.Font("Verdana Pro Cond", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(98, 154, 238));
        jLabel20.setText("Module name");
        jLabel20.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/back-arrow.png"))); // NOI18N

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cross.png"))); // NOI18N

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/line(1).png"))); // NOI18N

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/sort.png"))); // NOI18N

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/index.png"))); // NOI18N

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/contract.png"))); // NOI18N

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/line(1).png"))); // NOI18N

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/calculator.png"))); // NOI18N

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/printer.png"))); // NOI18N

        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/line(1).png"))); // NOI18N

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/topic.png"))); // NOI18N

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/file.png"))); // NOI18N

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/line(1).png"))); // NOI18N

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/replay.png"))); // NOI18N

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/bar-chart.png"))); // NOI18N

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/question.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 342, Short.MAX_VALUE)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(229, 229, 229)
                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Topics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Topics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Topics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Topics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Topics().setVisible(true);
            }
        });
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel17;
    // End of variables declaration//GEN-END:variables
}
