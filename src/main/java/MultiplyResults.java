import java.sql.*;

public class MultiplyResults {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/first_lesson?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow";
        String userName = "root";
        String password = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");

        try(Connection conn = DriverManager.getConnection(url, userName, password)){
            CallableStatement callableStatement = null;
            try {
                callableStatement = conn.prepareCall("{CALL tablesCount}");
                boolean hasResult = callableStatement.execute();
                ResultSet resultSet = null;
                try {
                    while (hasResult){
                        resultSet = callableStatement.getResultSet();
                        while (resultSet.next()){
                            System.out.println("Кол-во записей в таблице: " + resultSet.getInt(1));
                        }
                        hasResult = callableStatement.getMoreResults();
                    }
                } catch (SQLException e){
                    e.printStackTrace();
                } finally {
                    if (resultSet != null){
                        resultSet.close();
                    }
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                callableStatement.close();
            }
        }
    }
}
