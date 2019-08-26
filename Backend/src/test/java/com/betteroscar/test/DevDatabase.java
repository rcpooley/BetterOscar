package com.betteroscar.test;

import com.betteroscar.config.MysqlConfig;
import com.betteroscar.database.Database;
import com.betteroscar.exception.DatabaseException;
import java.sql.SQLException;
import java.sql.Statement;

public class DevDatabase extends Database {

  public static DevDatabase openConnection(MysqlConfig config) {
    try {
      return new DevDatabase(config);
    } catch (DatabaseException e) {
      e.printStackTrace();
      return null;
    }
  }

  private DevDatabase(MysqlConfig config) throws DatabaseException {
    super(config);
  }

  public void resetWithTestingData() {
    try {
      clearData();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void clearData() throws SQLException {
    Statement s = connection.createStatement();
    String[] tableOrder = {
        "section_time",
        "section",
        "course",
        "subject",
        "term"
    };
    for (String table : tableOrder) {
      s.execute("DELETE FROM " + table);
      s.execute("ALTER TABLE " + table + " AUTO_INCREMENT = 1");
    }
  }
}
