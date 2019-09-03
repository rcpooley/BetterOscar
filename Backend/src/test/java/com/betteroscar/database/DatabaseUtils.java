package com.betteroscar.database;

import com.betteroscar.config.Config;
import com.betteroscar.exception.DatabaseException;
import com.betteroscar.test.DevDatabase;
import com.rcpooley.configloader.ConfigException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class DatabaseUtils {

  public static final File databaseFile = new File("src/main/resources/database.sql");

  private static DevDatabase database;

  @BeforeClass
  public static void setup() throws ConfigException, DatabaseException {
    Config config = Config.load();

    database = new DevDatabase(config.mysql());
  }

  @Test
  public void exportDatabaseStructure() throws SQLException, DatabaseException, IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(databaseFile));
    writer.write(getDatabaseStructure());
    writer.close();
    System.out.println("Wrote to " + databaseFile.getPath());
  }

  public static String getDatabaseStructure() throws SQLException, DatabaseException {
    StringBuilder sql = new StringBuilder();
    Statement s = database.getConnection().createStatement();

    // Export tables
    List<String> tables = database.getTables();
    tables.sort(Comparator.comparingInt(DevDatabase.tableDependencyOrder::indexOf).reversed());
    for (String table : tables) {
      ResultSet rs = s.executeQuery("SHOW CREATE TABLE " + table);
      if (!rs.next()) {
        throw new DatabaseException("Failed to generate CREATE TABLE statement for table " + table);
      }
      sql.append(rs.getString(2)).append(";\n\n");
    }

    // Export procedures
    List<String> procs = database.getProcedures();
    for (String proc : procs) {
      ResultSet rs = s.executeQuery("SHOW CREATE PROCEDURE " + proc);
      if (!rs.next()) {
        throw new DatabaseException("Failed to generate CREATE PROCEDURE statement for procedure " + proc);
      }
      sql.append(rs.getString(3)).append(";\n\n");
    }

    return sql.toString();
  }
}
