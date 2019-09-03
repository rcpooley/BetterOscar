package com.betteroscar.database;

import com.betteroscar.exception.DatabaseException;
import com.rcpooley.configloader.ConfigException;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class VerifyDatabase {

  @BeforeClass
  public static void setup() throws ConfigException, DatabaseException {
    DatabaseUtils.setup();
  }

  @Test
  public void checkDatabaseStructureUpToDate() throws DatabaseException, SQLException, IOException {
    String structure = DatabaseUtils.getDatabaseStructure();

    String fileStructure = new String(Files.readAllBytes(DatabaseUtils.databaseFile.toPath()));

    Assert.assertEquals(structure, fileStructure);
  }

}
