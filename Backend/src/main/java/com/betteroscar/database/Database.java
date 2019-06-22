package com.betteroscar.database;

import com.betteroscar.config.MysqlConfig;
import com.betteroscar.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {

  public static Database openConnection(MysqlConfig config) throws DatabaseException {
    return new Database(config);
  }

  private Connection connection;

  private Database(MysqlConfig config) throws DatabaseException {
    try {
      connection = DriverManager.getConnection(
          "jdbc:mysql://" + config.getHost() + "/" + config.getDatabase(),
          config.getUsername(),
          config.getPassword()
      );
    } catch (SQLException e) {
      throw new DatabaseException("Failed to open connection", e);
    }
  }

  public void test() {
    try {
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM term");
      while (rs.next()) {
        System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
