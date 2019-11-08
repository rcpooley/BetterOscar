package com.betteroscar.database;

import com.betteroscar.config.Config;
import com.betteroscar.exception.DatabaseException;
import com.betteroscar.model.Term;
import com.betteroscar.test.DevDatabase;
import com.rcpooley.configloader.ConfigException;
import java.sql.SQLException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDatabase {

  private static DevDatabase database;

  @BeforeClass
  public static void setup() throws ConfigException, DatabaseException, SQLException {
    Config config = Config.load();

    database = new DevDatabase(config.mysql());

    database.reset();
  }


  @Test
  public void testAddTerm() throws DatabaseException {
    Term term = database.addTerm("term1id", "Term 1 Name");
    Assert.assertEquals(1, term.getID());
    Assert.assertEquals("term1id", term.getTermID());
    Assert.assertEquals("Term 1 Name", term.getName());
  }

}
