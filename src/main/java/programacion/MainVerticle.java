package programacion;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.mysqlclient.MySQLConnectOptions;
import programacion.endpoints.UserEndpoints;
import programacion.tasks.TaskCountPeople;
import programacion.utils.MySqlUtil;

import java.net.http.HttpResponse;

public class MainVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    MySQLConnectOptions connectOptions = MySqlUtil.getConnectionOptions();
    UserEndpoints.setupEndpoints(vertx, router, connectOptions);
    TaskCountPeople.countPeople(vertx, connectOptions);
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8888)
      .onSuccess(server ->
        System.out.println(
          "HTTP server started on port " + server.actualPort()
        )
      );
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new MainVerticle());
  }
}
