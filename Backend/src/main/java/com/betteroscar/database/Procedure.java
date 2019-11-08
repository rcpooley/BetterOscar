package com.betteroscar.database;

public enum Procedure {

  ADD_TERM("add_term", AddTermResult.class),
  GET_TERMS("get_terms", GetTermsResult.class);

  private String name;

  private Class<?> resultClass;

  Procedure(String name, Class<?> resultClass) {
    this.name = name;
    this.resultClass = resultClass;
  }

  public String getName() {
    return name;
  }

  public Class<?> getResultClass() {
    return resultClass;
  }
}
