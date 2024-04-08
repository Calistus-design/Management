
package learn_package;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Clob;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;

// Import the necessary packages


public class Notes extends javax.swing.JFrame {
    private int topicID;
    private String subtopic;
    private int currentPosition = 0; //to keep track of your current position in the notes
    private int currentPageNumber = 1; // Initialize current page number to 1
   private boolean moreNotesAvailable = true; // Initially, assume there are more notes
   private int totalNumberOfPages; 

 public Notes() {
        initComponents();
        
Font customFont = new Font("Serif", Font.BOLD, 16); 

// Set the font for the jEditorPane
jPanel18.setFont(customFont);
// Fetch the notes and display the total number of pages
        totalNumberOfPages = fetchTotalPages(subtopic);
       // jtotal.setText("Total: " + totalNumberOfPages);

 }

 private int fetchTotalPages(String subtopic) {
        String notes = fetchNotesFromDatabase(subtopic);
        // Count the number of [Ep] markers
        return notes.split("\\[Ep\\]").length;
    }
   

    public Notes(String subtopic) {
    initComponents();
    this.subtopic = subtopic;
    
    String notes = fetchNotesFromDatabase(subtopic);
    //jtotal.setText("Total: " + pageCount);
    
    if (notes != null) {
     
     //int totalNumberOfPages = countPagesInDatabase(notes);
    currentPageNumber = 1; // Initialize the current page number

    // Set the total number of pages and the current page number in the jPageNo label
    jPageNo.setText("Page " + currentPageNumber + " of " + totalNumberOfPages);
    
 
// Create a JEditorPane and set it to display HTML content
        JEditorPane notesEditorPane = new JEditorPane();
        notesEditorPane.setContentType("text/html");
   // int pageCount = fetchNotesFromDatabase(Stringsubtopic);

        notesEditorPane.setEditable(false);

        
// Replace markers
notes = notes.replaceAll("\\[b\\]", "<b>");
notes = notes.replaceAll("\\[/b\\]", "</b>");
notes = notes.replaceAll("\\[f=(.*?)\\]", "<font face=\"$1\">");
notes = notes.replaceAll("\\[/f\\]", "</font>");
notes = notes.replaceAll("\\[size(\\d+)\\]", "<font size=\"$1\">");
notes = notes.replaceAll("\\[/size(\\d+)\\]", "</font>");
notes = notes.replaceAll("\\[color=(\\d+),(\\d+),(\\d+)\\]", "<font color=\"rgb($1,$2,$3)\">");
notes = notes.replaceAll("\\[/color\\]", "</font>");

        // Combine the content with HTML tags
        String styledNotes = "<html><body>" + notes + "</body></html>";

        // Set the formatted HTML text to the JEditorPane
        notesEditorPane.setText(styledNotes);

        // Add the JEditorPane to jPanel18
        jPanel18.removeAll(); // Remove existing content
        jPanel18.add(notesEditorPane);

        // Revalidate the jPanel18 to reflect the changes
        jPanel18.revalidate();
} else {
        // Handle the case where notes is null
        System.out.println("No notes available for subtopic: " + subtopic);
    }

}




    
   private String fetchNotesFromDatabase(String subtopic) {
    String notes = null;
    //int totalNumberOfPages = 0;

    // Establish the database connection
    String jdbcUrl = "jdbc:derby://localhost:1527/Unique";
    String username = "ca";
    String password = "12";

    try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
        String sql = "SELECT Notes FROM Subtopics WHERE Subtopic = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, subtopic);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Clob clob = rs.getClob("Notes");
                notes = clobToString(clob);

                // Count the number of [Ep] markers
                totalNumberOfPages = notes.split("\\[Ep\\]").length;

                // Split the notes into segments using [Ep] as a delimiter
                String[] segments = notes.split("\\[Ep\\]");

                if (currentPosition < segments.length) {
                    // Get the next segment based on currentPosition
                    notes = segments[currentPosition];
                } else {
                    // If currentPosition is out of range, return an empty string
                    notes = "";
                }
            }
        }
    } catch (SQLException | IOException e) {
    }

    

    return notes;
}



private String clobToString(Clob clob) throws SQLException, IOException {
    if (clob == null) {
        return "";
    }

    StringBuilder stringBuilder = new StringBuilder();
    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clob.getAsciiStream()))) {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
    }

    return stringBuilder.toString();
}





    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
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
        jLabelNext = new javax.swing.JLabel();
        jBack = new javax.swing.JLabel();
        jPageNo = new javax.swing.JLabel();
        jtotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel18.setLayout(new javax.swing.BoxLayout(jPanel18, javax.swing.BoxLayout.Y_AXIS));

        jLabel19.setBackground(new java.awt.Color(204, 204, 204));
        jLabel19.setFont(new java.awt.Font("Verdana Pro Cond", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(98, 154, 238));
        jLabel19.setText("Learning Platform");
        jLabel19.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel38.setBackground(new java.awt.Color(204, 204, 204));
        jLabel38.setFont(new java.awt.Font("Verdana Pro Cond", 1, 24)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(98, 154, 238));
        jLabel38.setText("       Notes");
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

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/right-arrow(2).png"))); // NOI18N
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
        });

        jLabelNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/next(1).png"))); // NOI18N
        jLabelNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelNextMouseClicked(evt);
            }
        });

        jBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/previous(1).png"))); // NOI18N
        jBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBackMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(229, 229, 229)
                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57)
                                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jBack)
                                        .addGap(27, 27, 27)
                                        .addComponent(jLabelNext))
                                    .addComponent(jPageNo, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23)))
                        .addContainerGap())))
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
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPageNo, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBack)
                            .addComponent(jLabelNext)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(14, 14, 14))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
              openNewFrame();

    }//GEN-LAST:event_jLabel33MouseClicked
    
    private void jLabelNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelNextMouseClicked
    if (moreNotesAvailable) {
        currentPageNumber++; // Increment the current page number
        String nextNotes = fetchNotesFromDatabase(subtopic);
    }
    currentPosition++; // Move to the next segment
    String nextNotes = fetchNotesFromDatabase(subtopic);

    if (!nextNotes.isEmpty()) {
        // If nextNotes is not empty, update the displayed text
        updateNotesDisplay(nextNotes);
         updatePageNumberLabel(); // Update the jPageNo label
    } else {
                    moreNotesAvailable = false;
        // Handle the case where there are no more segments
        JOptionPane.showMessageDialog(null, "End of notes.");
    }
    }//GEN-LAST:event_jLabelNextMouseClicked
     
    private void updatePageNumberLabel() {
    jPageNo.setText("Page " + currentPageNumber + " of " + totalNumberOfPages);
}
    
    private void jBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBackMouseClicked
      
    // Check if there is a previous set of text to display
    if (currentPosition > 0 && currentPageNumber > 1) {
        currentPageNumber--; // Decrement the current page number
        currentPosition--; // Move to the previous segment
        String previousNotes = fetchNotesFromDatabase(subtopic);

        if (!previousNotes.isEmpty()) {
            // If previousNotes is not empty, update the displayed text
            updateNotesDisplay(previousNotes);
            updatePageNumberLabel(); // Update the jPageNo label
        } else {
            // Handle the case where there are no more segments
            JOptionPane.showMessageDialog(null, "Start of notes.");
        }
    } else {
        // Handle the case where you are at the start of the text
        JOptionPane.showMessageDialog(null, "Start of notes.");
    }


    }//GEN-LAST:event_jBackMouseClicked
  
   



    private void updateNotesDisplay(String notes) {
    // Create a JEditorPane and set it to display HTML content
    if (notes != null) {
    JEditorPane notesEditorPane = new JEditorPane();
    notesEditorPane.setContentType("text/html");
    notesEditorPane.setEditable(false);

    // Replace markers
    notes = notes.replaceAll("\\[b\\]", "<b>");
    notes = notes.replaceAll("\\[/b\\]", "</b>");
    notes = notes.replaceAll("\\[f=(.*?)\\]", "<font face=\"$1\">");
    notes = notes.replaceAll("\\[/f\\]", "</font>");
    notes = notes.replaceAll("\\[size(\\d+)\\]", "<font size=\"$1\">");
    notes = notes.replaceAll("\\[/size(\\d+)\\]", "</font>");
    notes = notes.replaceAll("\\[color=(\\d+),(\\d+),(\\d+)\\]", "<font color=\"rgb($1,$2,$3)\">");
    notes = notes.replaceAll("\\[/color\\]", "</font>");

    // Combine the content with HTML tags
    String styledNotes = "<html><body>" + notes + "</body></html>";

    // Set the formatted HTML text to the JEditorPane
    notesEditorPane.setText(styledNotes);

    // Remove any existing components in jPanel18
    jPanel18.removeAll();

    // Add the JEditorPane to jPanel18
    jPanel18.add(notesEditorPane);

    // Revalidate jPanel18 to reflect the changes
    jPanel18.revalidate();
    } else {
        // Handle the case where notes is null
        System.out.println("No notes available");
    }
}



    private void openNewFrame() {
    test testFrame = new test(); // Create an instance of TestFrame
    testFrame.setVisible(true); // Show the new frame
    // Dispose the current frame
    dispose();
}

    
    public static void main(String args[]) {
           
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Notes().setVisible(true);
            }
        });
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jBack;
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
    private javax.swing.JLabel jLabelNext;
    private javax.swing.JLabel jPageNo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JLabel jtotal;
    // End of variables declaration//GEN-END:variables
}
