import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class RowSetLesson {
   static String url = "jdbc:mysql://localhost:3306/first_lesson?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow";
   static String userName = "root";
   static String password = "root";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = getResSet();
        while (resultSet.next()){
            System.out.println(resultSet.getString("name"));
        }

        CachedRowSet rowSet = (CachedRowSet) resultSet;
        rowSet.setCommand("SELECT * FROM books WHERE price > ?");
        rowSet.setDouble(1, 30);

        rowSet.setUrl(url);
        rowSet.setUsername(userName);
        rowSet.setPassword(password);
        rowSet.execute();

        rowSet.absolute(2);
        rowSet.deleteRow();
        resultSet.beforeFirst();
        Connection connection = DriverManager.getConnection(url, userName, password);
        connection.setAutoCommit(false);
        rowSet.acceptChanges(connection);

        while (rowSet.next()){
            String name = rowSet.getString("name");
            double price = rowSet.getDouble(3);
            System.out.println(name + " " + price);
        }

    }

   static ResultSet getResSet() throws ClassNotFoundException, SQLException {
       Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection conn = DriverManager.getConnection(url, userName, password);
            Statement stat = conn.createStatement()) {
            ResultSet rs = stat.executeQuery("SELECT * FROM books");
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet crs = factory.createCachedRowSet();
            crs.populate(rs);
            return crs;
        }
   }
}
