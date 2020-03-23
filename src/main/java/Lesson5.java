import java.sql.*;

public class Lesson5 {
    static String url = "jdbc:mysql://localhost:3306/first_lesson?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow";
    static String userName = "root";
    static String password = "root";

    public static void main(String[] args) throws SQLException {
        try(Connection conn = DriverManager.getConnection(url, userName, password);
            Statement statement = conn.createStatement()){
            conn.setAutoCommit(false);
//            conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//            statement.executeUpdate("UPDATE books SET price = 100 WHERE bookId = 1");
//            new OtherTransaction().start();
//            Thread.sleep(2000);
//            conn.rollback();
            //conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            //conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
//            ResultSet rs = statement.executeQuery("SELECT * FROM books");
//            while (rs.next()){
//                System.out.println(rs.getString("name") + " " + rs.getDouble(3));
//            }
//            new OtherTransaction().start();
//            Thread.sleep(2000);
//            ResultSet rs2 = statement.executeQuery("SELECT * FROM books");
//            while (rs2.next()){
//                System.out.println(rs2.getString("name") + " " + rs2.getDouble(3));
//            }
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            ResultSet rs = statement.executeQuery("SELECT * FROM books WHERE bookId > 5");
            while (rs.next()){
                System.out.println(rs.getString("name") + " " + rs.getDouble(3));
            }
            new OtherTransaction().start();
            Thread.sleep(2000);
            ResultSet rs2 = statement.executeQuery("SELECT * FROM books WHERE bookId > 5");
            while (rs2.next()){
                System.out.println(rs2.getString("name") + " " + rs2.getDouble(3));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class OtherTransaction extends Thread {
        @Override
        public void run() {
            try(Connection conn = DriverManager.getConnection(url, userName, password);
                Statement statement = conn.createStatement()) {
                conn.setAutoCommit(false);
              //  conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

                //conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
//                ResultSet resultSet = statement.executeQuery("SELECT * FROM books");
//                while (resultSet.next()){
//                    System.out.println(resultSet.getString("name") + " " + resultSet.getDouble(3));
//                }
//                statement.executeUpdate("UPDATE Books Set price = price + 20 WHERE name = 'Solomon key'");
//                conn.commit();
                conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                statement.executeUpdate("INSERT INTO Books (name, price) VALUES ('New book', 10)");
                conn.commit();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
