package com.betteroscar.config;

import com.rcpooley.configloader.ConfigException;
import com.rcpooley.configloader.ConfigLoader;

public class Config {

  public static Config load() throws ConfigException {
    return ConfigLoader.loadJSON(Config.class.getResourceAsStream("/config.json"), Config.class);
  }

  private MysqlConfig mysql;

  public MysqlConfig mysql() {
    return mysql;
  }
}
