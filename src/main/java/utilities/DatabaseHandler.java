package utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHandler {

    public static Connection con = null, conILS = null;

    /**
     * This method is used to connect with SCALE-IHS Database.
     */
    public static void connect() {
        try {
            // Register JDBC driver (JDBC driver name and Database URL)

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Open a connection
            /*String username = "SvcILS_read_TQA";
            String password = "Fh5OktYGoBWoAnGBqqe2";
            con = DriverManager.getConnection(
                    "jdbc:sqlserver://AG-IHSSCALET;database=IHS2019Test;", username, password);*/
            con = DriverManager.getConnection(
                    "jdbc:sqlserver://AG-IHSSCALET;database=IHS2019Test;integratedSecurity=true;");

            if (con != null) {
                System.out.println("Database connection established." + '\n');
            } else {
                System.out.println("Failed to make database connection!");
            }
        } catch (Exception e) {
            System.out.println("Exception occurred while creating the connection.");
            e.printStackTrace();
        }
    }

    public static void connectOnPrem() {
        try {
            // Register JDBC driver (JDBC driver name and Database URL)

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Open a connection
            String username = "SvcILS_read_TQA";
            String password = "Fh5OktYGoBWoAnGBqqe2";
            conILS = DriverManager.getConnection(
                    "jdbc:sqlserver://mer1-scaledbt01:1433;databaseName=ILS;", username, password);

            if (conILS != null) {
                System.out.println("Database connection established." + '\n');
            } else {
                System.out.println("Failed to make database connection!");
            }
        } catch (Exception e) {
            System.out.println("Exception occurred while creating the connection.");
            e.printStackTrace();
        }
    }


    /**
     * This method is used to close the connection of SCALE-IHS Database.
     */
    public static void close() {
        try {
            con.close();
            System.out.println("Connection closed successfully.");
        } catch (SQLException e) {
            System.out.println("Exception occurred while closing the connection.");
            e.printStackTrace();
        }
    }

    public static void closeILS() {
        try {
            conILS.close();
            System.out.println("Connection closed successfully.");
        } catch (SQLException e) {
            System.out.println("Exception occurred while closing the connection.");
            e.printStackTrace();
        }
    }

    /**
     * This method is used to fetch the result form the Database, based on the query passed.
     *
     * @param query Pass the query.
     * @return Returns the result as set of HashMaps Key and Value pair in which the Key is the Name of the Column fetched.
     */
    public static ArrayList<HashMap<String, String>> runQueryInILS(String query) {

        try {

            connectOnPrem();
            Statement stmt = conILS.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Result set generated.");

            int row = 0;
            int totalColumnsInResultSet = rs.getMetaData().getColumnCount();
            ArrayList<HashMap<String, String>> resultSetInList = new ArrayList<>();
            while (rs.next()) {
                HashMap<String, String> columnWiseDataOfCurrentRow = new HashMap<>();
                for (int i = 1; i <= totalColumnsInResultSet; i++) {
                    columnWiseDataOfCurrentRow.put(rs.getMetaData().getColumnName(i), rs.getString(i));
                }
                resultSetInList.add(columnWiseDataOfCurrentRow);
                row++;
            }

            System.out.println("Total Rows fetched:" + row);
            System.out.println("Total Columns fetched:" + totalColumnsInResultSet);
            closeILS();
            return resultSetInList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<HashMap<String, String>> runQuery(String query) {

        try {

            connect();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Result set generated.");

            int row = 0;
            int totalColumnsInResultSet = rs.getMetaData().getColumnCount();
            ArrayList<HashMap<String, String>> resultSetInList = new ArrayList<>();
            while (rs.next()) {
                HashMap<String, String> columnWiseDataOfCurrentRow = new HashMap<>();
                for (int i = 1; i <= totalColumnsInResultSet; i++) {
                    columnWiseDataOfCurrentRow.put(rs.getMetaData().getColumnName(i), rs.getString(i));
                }
                resultSetInList.add(columnWiseDataOfCurrentRow);
                row++;
            }

            System.out.println("Total Rows fetched:" + row);
            System.out.println("Total Columns fetched:" + totalColumnsInResultSet);
            close();
            return resultSetInList;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        ArrayList<HashMap<String, String>> list = runQuery("select * from [dbo].[SHIPMENT_HEADER] order by trailing_sts_date desc");

        System.out.println(list.get(0).get("CARRIER"));

    }

}