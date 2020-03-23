import java.sql.*;

public class Lesson2 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/first_lesson?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow";
        String userName = "root";
        String password = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");

        try(Connection conn = DriverManager.getConnection(url, userName, password)){
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = conn.prepareStatement("INSERT INTO books (name, price) VALUES (?, ?)");
                preparedStatement.setString(1, "Shindler's list");
                preparedStatement.setDouble(2, 32.5);
                preparedStatement.execute();

                ResultSet rs = null;
                try {
                    rs = preparedStatement.executeQuery("SELECT * FROM books");
                    while (rs.next()){
                        int id = rs.getInt(1);
                        String name = rs.getString(2);
                        double price = rs.getDouble(3);
                        System.out.println("ID: " + id + " name: " + name + " price: " + price);
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
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                preparedStatement.close();
            }
            CallableStatement callableStatement = null;
            try {
                callableStatement = conn.prepareCall("{CALL booksCount(?)}");
                callableStatement.registerOutParameter(1, Types.INTEGER);
                callableStatement.execute();
                System.out.println("Количество записей в таблице: " + callableStatement.getInt(1));
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                callableStatement.close();
            }
        }
    }
}
