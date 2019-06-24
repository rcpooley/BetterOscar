package com.betteroscar.database;

import com.betteroscar.config.MysqlConfig;
import com.betteroscar.exception.DatabaseException;
import com.betteroscar.model.Term;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.betteroscar.database.Procedure.*;


public class Database {

  private static Map<Class<?>, Method> resultSetMethods;

  public static Database openConnection(MysqlConfig config) throws DatabaseException {
    if (resultSetMethods == null) {
      try {
        init();
      } catch (NoSuchMethodException e) {
        throw new DatabaseException("Failed to initialize ResultSet method map", e);
      }
    }

    return new Database(config);
  }

  private static void init() throws NoSuchMethodException {
    resultSetMethods = new HashMap<>();

    resultSetMethods.put(int.class, ResultSet.class.getMethod("getInt", int.class));
    resultSetMethods.put(String.class, ResultSet.class.getMethod("getString", int.class));
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

  private <T> List<T> executeProcedure(Procedure procedure) throws DatabaseException {
    try {
      // Call the stored procedure
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery("CALL " + procedure.getName() + "();");
      ResultSetMetaData meta = rs.getMetaData();

      // Get mapping of column name to index
      Map<String, Integer> columns = new HashMap<>();
      int numColumns = meta.getColumnCount();
      for (int i = 1; i <= numColumns; i++) {
        columns.put(meta.getColumnName(i), i);
      }

      // Validate that all columns are present for procedure interface
      Class<?> clazz = procedure.getResultClass();
      Method[] methods = clazz.getMethods();
      for (Method m : methods) {
        if (!columns.containsKey(m.getName())) {
          throw new DatabaseException("Expected column `" + m.getName() + "` but was not found in result set", procedure);
        }
      }

      // Load each object from result set
      List<T> ret = new ArrayList<>();
      while (rs.next()) {
        // Get value of each field
        final Map<String, Object> vals = new HashMap<>();
        for (Method m : clazz.getMethods()) {
          String name = m.getName();
          int col = columns.get(name);

          // Validate method type
          Class<?> type = m.getReturnType();
          if (!resultSetMethods.containsKey(type)) {
            throw new DatabaseException("Unrecognized method type for column `" + name + "`: " + type.getSimpleName(), procedure);
          }

          // Get the field value
          Method typeMethod = resultSetMethods.get(type);
          Object val = typeMethod.invoke(rs, col);
          vals.put(name, val);
        }

        // Create proxy interface object
        @SuppressWarnings("unchecked")
        T obj = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] {clazz}, (proxy, method, args) -> vals.get(method.getName()));

        ret.add(obj);
      }

      return ret;
    } catch (SQLException | IllegalAccessException | InvocationTargetException e) {
      throw new DatabaseException("Failed to execute stored procedure", procedure, e);
    }
  }

  public List<Term> getTerms() throws DatabaseException {
    List<GetTermsResult> terms = executeProcedure(GET_TERMS);
    return terms.stream().map(term -> new Term(term.id(), term.term_id(), term.name())).collect(Collectors.toList());
  }
}
