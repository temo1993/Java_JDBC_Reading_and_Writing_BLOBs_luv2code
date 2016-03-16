import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

public class WriteBlobDemo {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement myStatement = null;

        FileInputStream input = null;

        try{
            // 1. GET a connection to database
            connection = DriverManager.getConnection("jdbc:mysql://localhost/demo?useSSL=false","root","123456");

            // 2. Prepare statement
            String sql = "UPDATE employees SET resume=? WHERE email='john.doe@foo.com'";
            myStatement = connection.prepareStatement(sql);

            // 3. SET parameter or resume file name
            File thePdfFIle = new File("sample_resume.pdf");
            input = new FileInputStream(thePdfFIle);
            myStatement.setBinaryStream(1, input);

            System.out.println("Reading input file: " + thePdfFIle.getAbsolutePath());

            // 4. Execute statement
            System.out.println("\nStoring resume in database: " + thePdfFIle);
            System.out.println(sql);

            myStatement.executeUpdate();

            System.out.println("\nCompleted successfully!");
        } catch (SQLException se) {
            System.out.println("SQL Exception: " + se.getMessage() + " ,error code: " + se.getErrorCode());
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found: " + fnfe.getMessage());
            fnfe.printStackTrace();
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
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
