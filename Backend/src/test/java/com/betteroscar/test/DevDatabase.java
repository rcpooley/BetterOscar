package com.betteroscar.test;

import com.betteroscar.config.MysqlConfig;
import com.betteroscar.database.Database;
import com.betteroscar.exception.DatabaseException;
import com.betteroscar.model.Term;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DevDatabase extends Database {

  public static final List<String> tableDependencyOrder = Arrays.asList(
      "section_time",
      "section",
      "course",
      "subject",
      "term"
  );

  public DevDatabase(MysqlConfig config) throws DatabaseException {
    super(config);
  }

  public void reset() throws SQLException {
    Statement s = connection.createStatement();
    for (String table : tableDependencyOrder) {
      s.execute("DELETE FROM " + table);
      s.execute("ALTER TABLE " + table + " AUTO_INCREMENT = 1");
    }
  }

  public List<String> getTables() throws SQLException {
    Statement s = connection.createStatement();
    ResultSet rs = s.executeQuery("SHOW TABLES");
    List<String> tables = new ArrayList<>();
    while (rs.next()) {
      tables.add(rs.getString(1));
    }
    return tables;
  }

  public List<String> getProcedures() throws SQLException {
    Statement s = connection.createStatement();
    ResultSet rs = s.executeQuery("SHOW PROCEDURE STATUS where db='" + config.getDatabase() + "'");
    List<String> procs = new ArrayList<>();
    while (rs.next()) {
      procs.add(rs.getString(2));
    }
    return procs;
  }

  public Connection getConnection() {
    return connection;
  }
}
