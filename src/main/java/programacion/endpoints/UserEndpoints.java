package programacion.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RequestBody;
import io.vertx.ext.web.Router;
import io.vertx.mysqlclient.MySQLConnectOptions;
import programacion.model.User;
import programacion.utils.MySqlUtil;
import programacion.utils.ValidateToken;

import java.io.DataInput;
import java.util.Objects;

import static programacion.utils.EndpointUtils.API_BASE;
import static programacion.utils.EndpointUtils.setResponse;

public class UserEndpoints {

  private static final String API_USER = API_BASE + "/user";

  public static void setupEndpoints(Vertx vertx,
                                    Router router,
                                    MySQLConnectOptions connectOptions) {


    router.get(API_USER)
      .produces("application/json")
      .handler(routingContext -> {
        ValidateToken.validToken(routingContext);
        try {
          HttpServerRequest request = routingContext.request();
          String id = request.getParam("id");
          if (Objects.isNull(id)) {
            MySqlUtil.getPeople(routingContext, connectOptions, vertx);
          } else {
            Integer userId = Integer.valueOf(id);
            MySqlUtil.getPersonById(userId, routingContext, connectOptions, vertx);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    router.post(API_USER)
      .produces("application/json")
      .handler(routingContext -> {
        ValidateToken.validToken(routingContext);
        try {
          RequestBody requestBody = routingContext.body();
          String body = requestBody.asString();
          User user = new ObjectMapper().readValue(body, User.class);
          MySqlUtil.insertPerson(routingContext, connectOptions, user, vertx);
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    router.put()
      .produces("application/json")
      .handler(routingContext -> {
        ValidateToken.validToken(routingContext);
        try {
          RequestBody requestBody = routingContext.body();
          String body = requestBody.asString();
          User user = new ObjectMapper().readValue(body, User.class);
          MySqlUtil.updatePerson(routingContext, connectOptions, user, vertx);
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    router.delete()
      .produces("application/json")
      .handler(routingContext -> {
        ValidateToken.validToken(routingContext);
        try {
          HttpServerRequest request = routingContext.request();
          String id = request.getParam("id");
          if (Objects.isNull(id)) {
            String message = "No se digito el identificador del usuario a eliminar";
            setResponse(routingContext, 400, message);
            return;
          }
          Integer userId = Integer.valueOf(id);
          MySqlUtil.deletePersonById(userId, routingContext, connectOptions, vertx);
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
  }


}
