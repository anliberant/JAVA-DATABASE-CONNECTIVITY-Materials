import java.sql.*;

public class Lesson4 {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/first_lesson?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow";
        String userName = "root";
        String password = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");

        try(Connection conn = DriverManager.getConnection(url, userName, password);
            Statement stat = conn.createStatement()){
            String createTable = "CREATE TABLE Fruit (name VARCHAR(15) NOT NULL, amount INTEGER, price DOUBLE NOT NULL, PRIMARY KEY(name))";
            String command1 = "INSERT INTO Fruit (name, amount, price) VALUES ('Apple', 200, 3.50)";
            String command2 = "INSERT INTO Fruit (name, amount, price) VALUES ('Orange', 40, 5.50)";
            String command3 = "INSERT INTO Fruit (name, amount, price) VALUES ('Lemon', 30, 5.50)";
            String command4 = "INSERT INTO Fruit (name, amount, price) VALUES ('Pineapple', 20, 7.50)";

//            conn.setAutoCommit(false);
//            stat.executeUpdate(createTable);
//            stat.executeUpdate(command1);
//            Savepoint spt = conn.setSavepoint();
//            stat.executeUpdate(command2);
//            stat.executeUpdate(command3);
//            stat.executeUpdate(command4);
//            //conn.commit();
//
//            conn.rollback(spt);
//            conn.commit();
//            conn.releaseSavepoint(spt);
            conn.setAutoCommit(true);
            stat.addBatch(createTable);
            stat.addBatch(command1);
            stat.addBatch(command2);
            stat.addBatch(command3);
            stat.addBatch(command4);
            stat.executeBatch();
        }
    }
}
