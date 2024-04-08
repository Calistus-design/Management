
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


public class displaynotes extends javax.swing.JFrame {

   
    
    
    public displaynotes() {
        initComponents();
        String notes = fetchNotesFromDatabase();
        
        // Create a JEditorPane and set it to display HTML content
JEditorPane notesEditorPane = new JEditorPane();
notesEditorPane.setContentType("text/html");
// Set the JEditorPane as non-editable
    notesEditorPane.setEditable(false);
// Format the notes content in HTML (customize this based on your styling requirements)
    String styledNotes = "<html><body><font color='blue' face='SansSerif' size='6'><b>" +notes+
                        "</b></font></body></html>";
    notesEditorPane.setText(styledNotes);


//Add the JEditorPane to jPanel18
jPanel18.add(notesEditorPane);

// Revalidate the jPanel18 to reflect the changes
jPanel18.revalidate();
    }
    
    
    
    private String fetchNotesFromDatabase() {
    String notes = null;

    // Establish the database connection
    String jdbcUrl = "jdbc:derby://localhost:1527/Unique";
    String username = "ca";
    String password = "12";

    try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
        String sql = "SELECT Notes FROM Subtopics"; // Retrieve the first row

         try (PreparedStatement ps = connection.prepareStatement(sql)) {
            //ps.setString(1, subtopic);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Clob clob = rs.getClob("Notes");
                notes = clobToString(clob);
            }
        }
    } catch (SQLException | IOException e) {
        e.printStackTrace();
        // Handle the exception appropriately
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

        jPanel12 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(0, 0));
        setPreferredSize(new java.awt.Dimension(800, 600));

        jPanel12.setLayout(new javax.swing.BoxLayout(jPanel12, javax.swing.BoxLayout.LINE_AXIS));

        jPanel18.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));
        jPanel18.add(jPanel2, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(204, 204, 204)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    
    public static void main(String args[]) {
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new displaynotes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
