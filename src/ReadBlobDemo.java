import java.io.*;
import java.sql.*;

public class ReadBlobDemo {
    public static void main(String[] args) {
        Connection connection = null;
        Statement myStatement = null;
        ResultSet myResultSet = null;

        InputStream inputStream = null;
        FileOutputStream output = null;

        try{
            // 1. GET a connection to database
            connection = DriverManager.getConnection("jdbc:mysql://localhost/demo?useSSL=false","root","123456");

            // 2. Execute statement
            myStatement = connection.createStatement();
            String sql = "SELECT resume from employees WHERE email='john.doe@foo.com'";
            myResultSet = myStatement.executeQuery(sql);

            // 3. Set up a handle to the file
            File theFile = new File("resume_from_db.pdf");
            output = new FileOutputStream(theFile);

            if (myResultSet.next()) {

                inputStream = myResultSet.getBinaryStream("resume");
                System.out.println("Reading resume from database...");
                System.out.println(sql);

                byte[] buffer = new byte[1024];
                while(inputStream.read(buffer) > 0){
                    output.write(buffer);
                }

                System.out.println("\nSaved to file: " + theFile.getAbsolutePath());

                System.out.println("\nCompleted successfully!");
            }
        } catch (SQLException se) {
            System.out.println("SQL Exception: " + se.getMessage() + " ,error code: " + se.getErrorCode());
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found: " + fnfe.getMessage());
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe.getMessage());
            ioe.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (myStatement != null) {
                    myStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (myResultSet != null) {
                    myResultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


