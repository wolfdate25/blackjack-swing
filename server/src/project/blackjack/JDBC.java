package project.blackjack;

import java.sql.*;

public class JDBC {

    public JDBC() {

        try (Connection connection = getConn();
             Statement statement = connection.createStatement()) {
            // create a database connection
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

//            statement.executeUpdate("drop table if exists person");
            // string or text
            statement.executeUpdate("create table if not exists player (name string not null primary key, password string not null, coin int not null)");
//            statement.executeUpdate("insert into person values(1, 'leo')");
//            statement.executeUpdate("insert into person values(2, 'yui')");
//            ResultSet rs = statement.executeQuery("select * from person");
//            while(rs.next())
//            {
//                 read the result set
//                System.out.println("name = " + rs.getString("name"));
//                System.out.println("id = " + rs.getInt("id"));
//            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
    }

    private Connection getConn() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:player.db");
    }

    public boolean playerLogin(String name, String password) {
        ResultSet rs;
        String sql = "SELECT * FROM player WHERE name = ?";

        try (PreparedStatement query = getConn().prepareStatement(sql)) {
            query.setString(1, name);

            rs = query.executeQuery();
            if (rs.next()) {
                if (rs.getString("password").equals(password)) {
                    return true;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public int getPlayerCoin(String name) {
        ResultSet rs;
        String sql = "SELECT * FROM player WHERE name = ?";

        try (PreparedStatement query = getConn().prepareStatement(sql)) {
            query.setString(1, name);

            rs = query.executeQuery();
            if (rs.next()) {
                return rs.getInt("coin");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public int setPlayerCoin(String name, int coin) {
        int result = 0;
        String sql = "UPDATE player SET coin = ? WHERE name = ?";

        try (PreparedStatement query = getConn().prepareStatement(sql)) {
            query.setString(2, name);
            query.setString(1, String.valueOf(coin));

            result = query.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
