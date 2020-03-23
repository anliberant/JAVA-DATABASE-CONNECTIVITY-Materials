import java.sql.*;

public class Lesson3 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/first_lesson?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow";
        String userName = "root";
        String password = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");

        try(Connection conn = DriverManager.getConnection(url, userName, password);
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
            ResultSet rs = null;
            try {
                rs = statement.executeQuery("SELECT * FROM books");
//                while (rs.next()){
//                    int id = rs.getInt(1);
//                    double price = rs.getDouble(3);
//                    if (id == 4){
//                        rs.updateString("name", "Spartacus (discount)");
//                        rs.updateDouble(3, 40);
//                        rs.updateRow();
//                    }
//                }
                if (rs.absolute(2))
                    System.out.println(rs.getString("name"));
                if (rs.previous())
                    System.out.println(rs.getString("name"));
                if (rs.last())
                    System.out.println(rs.getString("name"));
                if (rs.relative(-3)) {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    while (rs.next()){
                        for (int i = 1; i <= rsmd.getColumnCount(); i++){
                            String field = rsmd.getColumnName(i);
                            String value = rs.getString(field);
                            System.out.println(field + " : " + value + " ");
                        }
                        System.out.println("");
                    }
                }

            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                if (rs != null){
                    rs.close();
                } else {
                    System.out.println("Error");
                }
            }
        }
    }
}
