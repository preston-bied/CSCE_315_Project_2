import java.sql.*;

public class queryFunctions {

    // get all items from a table with a certain word
    public static ResultSet searchWithKeyword(Statement stmt, String colValue, String table, String keyword) {
        String sqlStatement = "SELECT " + colValue + " FROM " + table + " WITH " + colValue + " LIKE '" + keyword + "%' OR " + colValue + " LIKE '%" + keyword + "'";
        ResultSet result = stmt.executeQuery(sqlStatement);
        return result;
    }
}