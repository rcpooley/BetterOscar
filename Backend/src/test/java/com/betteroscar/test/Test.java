package com.betteroscar.test;

import com.betteroscar.config.Config;
import com.betteroscar.database.Database;
import com.betteroscar.exception.DatabaseException;
import com.rcpooley.configloader.ConfigException;

public class Test {

  public static void main(String[] args) throws ConfigException, DatabaseException {
    Config config = Config.load();
    Database db = Database.openConnection(config.mysql());
    db.test();
  }

}
