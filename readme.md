Welcome to this project (Sistemas Operativos)

Execute this curls for tests:


User:

```
Gets:
curl -v http://localhost:8888/v1/progra/user
curl -v http://localhost:8888/v1/progra/user?id=3

Post:
curl -X POST http://localhost:8888/v1/progra/user -d {\"name\":\"Osvaldo\",\"age\":10,\"email\":\"osvaldo@gmail.com\",\"phone\":\"77777777\"}

Put:
curl -X PUT http://localhost:8888/v1/progra/user -d {\"idUser\":1,\"name\":\"Oscarr\",\"age\":11,\"email\":\"oscarr@gmail.com\",\"phone\":\"88888889\"}
curl -X PUT http://localhost:8888/v1/progra/user -d {\"idUser\":1,\"name\":\"Oscarr\",\"age\":11,\"email\":\"oscarrr@gmail.com\"}
curl -X PUT http://localhost:8888/v1/progra/user -d {\"idUser\":1,\"name\":\"Oscarr\",\"age\":15}
curl -X PUT http://localhost:8888/v1/progra/user -d {\"idUser\":1,\"name\":\"Oscar\"}

Delete:
curl -X DELETE http://localhost:8888/v1/progra/user?id=6

 ```
