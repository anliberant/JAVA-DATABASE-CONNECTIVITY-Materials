import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

public class BlobExample {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        String url = "jdbc:mysql://localhost:3306/first_lesson?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow";
        String userName = "root";
        String password = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");

        try(Connection conn = DriverManager.getConnection(url, userName, password);
            Statement statement = conn.createStatement()){
            statement.executeUpdate("CREATE TABLE Images (name VARCHAR (15), d DATE , image BLOB)");

            PreparedStatement preparedStatement = null;
            try {
                BufferedImage image = ImageIO.read(new File("smile.jpg"));
                Blob smile = conn.createBlob();
                try (OutputStream outputStream = smile.setBinaryStream(1)){
                    ImageIO.write(image, "jpg", outputStream);
                }
                preparedStatement = conn.prepareStatement("INSERT INTO Images (name, d, image) VALUES (?, {d ?}, ?)");
                preparedStatement.setString(1, "Smile");
                preparedStatement.setDate(2,Date.valueOf("2020-03-23"));
                preparedStatement.setBlob(3, smile);
                preparedStatement.execute();

                ResultSet resultSet = null;
                try {
                    resultSet = preparedStatement.executeQuery("SELECT * FROM Images");
                    while (resultSet.next()){
                        Blob newSmile = resultSet.getBlob("image");
                        BufferedImage image1 = ImageIO.read(newSmile.getBinaryStream());
                        File outputFile = new File("saved.jpg");
                        ImageIO.write(image, "jpg", outputFile);
                    }
                } catch (SQLException e){
                    e.printStackTrace();
                } finally {
                    if (resultSet != null){
                        resultSet.close();
                    } else {
                        System.out.println("Error");
                    }
                }

            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                preparedStatement.close();
            }
        }
    }
}
