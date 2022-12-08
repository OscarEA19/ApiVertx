package programacion.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import java.util.List;
import java.util.Objects;

public class EndpointUtils {

  public static final String API_BASE = "/v1/progra";


  public static void setResponse(RoutingContext routingContext,
                                 Integer status,
                                 Object responseBody) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      if (Objects.nonNull(routingContext)) {
        HttpServerResponse httpServerResponse = routingContext.response();
        boolean ended = httpServerResponse.ended();
        if (!ended) {
          if (Objects.nonNull(responseBody)) {
            if (responseBody instanceof List) {
              httpServerResponse.setStatusCode(status)
                .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
                .end(objectMapper.writeValueAsString(responseBody));
            } else {
              httpServerResponse.setStatusCode(status)
                .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
                .end(objectMapper.writeValueAsString(responseBody));
            }
          } else {
            httpServerResponse.setStatusCode(status)
              .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
              .end();
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
