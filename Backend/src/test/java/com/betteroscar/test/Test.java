package com.betteroscar.test;

import com.betteroscar.config.Config;
import com.betteroscar.database.Database;
import com.betteroscar.exception.DatabaseException;
import com.rcpooley.configloader.ConfigException;

public class Test {

  public static void main(String[] args) throws ConfigException {
    Config config = Config.load();
    DevDatabase db = DevDatabase.openConnection(config.mysql());
    db.resetWithTestingData();
  }
}
