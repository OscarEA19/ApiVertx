package programacion.utils;

import io.netty.util.internal.StringUtil;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

import static programacion.utils.EndpointUtils.setResponse;

public class ValidateToken {

  private static String CORRECT_VALUE_HEADER_AUTHORIZATION = "analisis";

  private static String getValueHeaderAuth(HttpServerRequest request){
    return request.getHeader("Authorization");
  }

  public static void validToken(RoutingContext routingContext){
    String value = getValueHeaderAuth(routingContext.request());
    if(!CORRECT_VALUE_HEADER_AUTHORIZATION.equals(value) || StringUtil.isNullOrEmpty(value)){
      setResponse(routingContext, 401, "Unauthorized");
    }
  }
}
