package programacion.tasks;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;

import java.util.function.Function;

import static programacion.utils.MySqlUtil.initPool;

public class TaskCountPeople {

  public static void countPeople(Vertx vertx, MySQLConnectOptions connectOptions) {
    String sql = "select count(*) as total from user;";
    vertx.setPeriodic(10000, (l) -> {
      MySQLPool client = initPool(vertx, connectOptions);
      Function<RowSet<Row>, Future<Object>> futureProcesarConsulta = result -> {
        if (result.size() == 0) {
          String message = "No hay personas en la base de datos";
          System.out.println(message);
          return Future.failedFuture(message);
        } else {
          Row row = result.iterator().next();
          Integer total = row.getInteger("total");
          System.out.println("Total de persona en la base de datos: " + total);
          return Future.succeededFuture();
        }
      };
      client.query(sql)
        .execute()
        .compose(futureProcesarConsulta);
    });
  }


}
