package programacion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.json.Json;
import org.junit.jupiter.api.Test;
import programacion.model.User;

import java.util.List;

public class Deserealizar {


  @Test
  public void testUser() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    Integer idUser = 1;
    String name = "Hello";
    Integer age = 2;
    String email = "Hello";
    String phone = "Hello";
    User user = new User(idUser,name,age,email,phone);
    System.out.println(objectMapper.writeValueAsString(user));
  }

  @Test
  public void testUserWithList() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    Integer idUser = 1;
    String name = "Hello";
    Integer age = 2;
    String email = "Hello";
    String phone = "Hello";
    User user = new User(idUser,name,age,email,phone);
    Integer idUser2 = 1;
    String name2 = "Bye";
    Integer age2 = 2;
    String email2 = "Bye";
    String phone2 = "Bye";
    User user2 = new User(idUser2,name2,age2,email2,phone2);
    List<User> userList = List.of(user,user2);
    System.out.println(objectMapper.writeValueAsString(userList));
  }

  @Test
  public void testStringToObject() throws JsonProcessingException {
    //String body = " {\"name\":\"Osvaldo\",\"age\":10,\"email\":\"osvaldo@gmail.com\",\"phone\":\"77777777\"}";
    String body = "{name:Osvaldo,age:10,email:osvaldo@gmail.com,phone:77777777}";
    User user = new ObjectMapper().readValue(body,User.class);
    System.out.println(user.getName());
  }
}
