package programacion.model;

public class User {
  private Integer idUser;
  private String name;
  private Integer age;
  private String email;
  private String phone;


  public User(String name, Integer age, String email, String phone) {
    this.name = name;
    this.age = age;
    this.email = email;
    this.phone = phone;
  }

  public User() {
  }

  public User(Integer idUser, String name, Integer age, String email, String phone) {
    this.idUser = idUser;
    this.name = name;
    this.age = age;
    this.email = email;
    this.phone = phone;
  }

  public Integer getIdUser() {
    return idUser;
  }

  public void setIdUser(Integer idUser) {
    this.idUser = idUser;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}

