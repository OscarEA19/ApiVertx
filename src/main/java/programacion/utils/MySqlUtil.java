package programacion.utils;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import programacion.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static programacion.utils.EndpointUtils.setResponse;

public class MySqlUtil {

  private final static String USER = "root";
  private final static Integer PORT = 3306;
  private final static String HOST = "localhost";
  private final static String DATABASE = "datavertx";
  private final static String PASSWORD = "root";

  public static MySQLConnectOptions getConnectionOptions() throws Exception {

    List<Object> errores = new ArrayList<>();
    if (Objects.isNull(USER)) {
      errores.add(USER);
    }
    if (Objects.isNull(PORT)) {
      errores.add(PORT);
    }
    if (Objects.isNull(HOST)) {
      errores.add(HOST);
    }
    if (Objects.isNull(DATABASE)) {
      errores.add(DATABASE);
    }
    if (Objects.isNull(PASSWORD)) {
      errores.add(PASSWORD);
    }
    if (!errores.isEmpty()) {
      throw new IllegalAccessException("validaciones fallidas");
    }
    return getConnectionOptions(USER, PORT, HOST, DATABASE, PASSWORD);
  }

  public static MySQLConnectOptions getConnectionOptions(String user,
                                                         Integer port,
                                                         String host,
                                                         String database,
                                                         String password) {
    MySQLConnectOptions connectOptions = new MySQLConnectOptions()
      .setPort(port)
      .setHost(host)
      .setDatabase(database)
      .setUser(user)
      .setPassword(password);
    return connectOptions;
  }

  public static MySQLPool initPool(Vertx vertx, MySQLConnectOptions connectOptions) {
    PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
    return MySQLPool.pool(vertx, connectOptions, poolOptions);
  }


  public static void getPersonById(Integer idUserInput,
                                   RoutingContext routingContext,
                                   MySQLConnectOptions connectOptions,
                                   Vertx vertx) throws Exception {

    MySQLPool client = initPool(vertx, connectOptions);

    String sql = "select * from datavertx.user where idUser = " + idUserInput;

    Function<RowSet<Row>, Future<Object>> futureProcesandoResultado = result -> {
      if (result.size() == 0) {
        String message = "No se encontro usuario asociado a este identificador";
        setResponse(routingContext, 400, null);
        return Future.failedFuture(message);
      }

      Row row = result.iterator().next();
      Integer idUser = row.getInteger("idUser");
      String name = row.getString("name");
      Integer age = row.getInteger("age");
      String email = row.getString("email");
      String phone = row.getString("phone");
      User user = new User(idUser, name, age, email, phone);
      setResponse(routingContext, 200, user);
      return Future.succeededFuture();
    };
    client.query(sql)
      .execute()
      .compose(futureProcesandoResultado);
  }

  public static void getPeople(RoutingContext routingContext,
                               MySQLConnectOptions connectOptions,
                               Vertx vertx) throws Exception {

    MySQLPool client = initPool(vertx, connectOptions);

    String sql = "select * from datavertx.user";

    Function<RowSet<Row>, Future<Object>> futureProcesandoResultado = result -> {
      if (result.size() == 0) {
        String message = "No se encontro ningun usuario en la base de datos";
        setResponse(routingContext, 400, null);
        return Future.failedFuture(message);
      }
      List<User> userList = new ArrayList<>();
      for (Row row : result) {
        Integer idUser = row.getInteger("idUser");
        String name = row.getString("name");
        Integer age = row.getInteger("age");
        String email = row.getString("email");
        String phone = row.getString("phone");
        User user = new User(idUser, name, age, email, phone);
        userList.add(user);
      }
      if (!userList.isEmpty()) {
        setResponse(routingContext, 200, userList);
      }
      return Future.succeededFuture();
    };

    client.query(sql)
      .execute()
      .compose(futureProcesandoResultado);
  }

  public static void insertPerson(RoutingContext routingContext,
                                  MySQLConnectOptions connectOptions,
                                  User user,
                                  Vertx vertx) throws Exception {

    MySQLPool client = initPool(vertx, connectOptions);

    String name = user.getName();
    Integer age = user.getAge();
    String email = user.getEmail();
    String phone = user.getPhone();

    String sql = "insert into user (name,age,email,phone) values ('" + name + "'," + age + ",'" + email + "','" + phone + "')";
    System.out.println("SQL: " + sql);
    client.query(sql)
      .execute()
      .onSuccess(success -> {
        String message = "Usuario agregado correctamente";
        setResponse(routingContext, 200, message);
        Future.succeededFuture();
      }).onFailure(fail -> {
        String message = "Error al intentar crear un usuario";
        setResponse(routingContext, 400, message);
        Future.failedFuture(message);
      });
  }

  public static void updatePerson(RoutingContext routingContext,
                                  MySQLConnectOptions connectOptions,
                                  User user,
                                  Vertx vertx) throws Exception {
    StringBuilder stringBuilder = new StringBuilder("update user set ");
    MySQLPool client = initPool(vertx, connectOptions);

    Integer id = user.getIdUser();
    String name = user.getName();
    Integer age = user.getAge();
    String email = user.getEmail();
    String phone = user.getPhone();

    if (Objects.nonNull(name)) {
      stringBuilder.append("name = '" + name + "' ");
    }
    if (Objects.nonNull(age)) {
      stringBuilder.append(",").append("age = '" + age + "' ");
    }
    if (Objects.nonNull(email)) {
      stringBuilder.append(",").append("email = '" + email + "' ");
    }
    if (Objects.nonNull(phone)) {
      stringBuilder.append(",").append("phone = '" + phone + "'");
    }
    stringBuilder.append(" where idUser = " + id);

    String sql = stringBuilder.toString();
    System.out.println("SQL: " + sql);
    client.query(sql)
      .execute()
      .onSuccess(success -> {
        String message = "Usuario modificado correctamente";
        setResponse(routingContext, 200, message);
        Future.succeededFuture();
      }).onFailure(fail -> {
        String message = "Error al intentar modificar un usuario";
        setResponse(routingContext, 400, message);
        Future.failedFuture(message);
      });
  }

  public static void deletePersonById(Integer idUserInput,
                                      RoutingContext routingContext,
                                      MySQLConnectOptions connectOptions,
                                      Vertx vertx) throws Exception {

    MySQLPool client = initPool(vertx, connectOptions);
    String sql = "delete from user where idUser = " + idUserInput;
    client.query(sql)
      .execute()
      .onSuccess(success -> {
        String message = "Usuario eliminado correctamente";
        setResponse(routingContext, 200, message);
        Future.succeededFuture();
      }).onFailure(fail -> {
        String message = "Error al intentar eliminado un usuario";
        setResponse(routingContext, 400, message);
        Future.failedFuture(message);
      });
  }


}
