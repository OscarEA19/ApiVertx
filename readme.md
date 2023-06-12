Welcome to this project (Analisis 2, API)

Execute this curls for tests:


User:

```
Gets:
curl -v http://localhost:8888/v1/progra/user
curl -v http://localhost:8888/v1/progra/user?id=3

Post:
curl -X POST http://localhost:8888/v1/progra/user -d {\"name\":\"Maria\",\"age\":20,\"email\":\"Maria@gmail.com\",\"phone\":\"11111111\"}

Put:
curl -X PUT http://localhost:8888/v1/progra/user -d {\"idUser\":1,\"name\":\"Oscarr\",\"age\":11,\"email\":\"oscarr@gmail.com\",\"phone\":\"88888889\"}
curl -X PUT http://localhost:8888/v1/progra/user -d {\"idUser\":1,\"name\":\"Oscarr\",\"age\":11,\"email\":\"oscarrr@gmail.com\"}
curl -X PUT http://localhost:8888/v1/progra/user -d {\"idUser\":1,\"name\":\"Oscarr\",\"age\":15}
curl -X PUT http://localhost:8888/v1/progra/user -d {\"idUser\":1,\"name\":\"Oscar\"}

Delete:
curl -X DELETE http://localhost:8888/v1/progra/user?id=7


Those sample is for securty test

Curls without token:
curl -X POST http://localhost:8888/v1/progra/user -d {\"name\":\"Maria\",\"age\":20,\"email\":\"Maria@gmail.com\",\"phone\":\"11111111\"}


Curls with token:
curl -X POST http://localhost:8888/v1/progra/user -d {\"name\":\"Maria\",\"age\":20,\"email\":\"Maria@gmail.com\",\"phone\":\"11111111\"}
curl -X DELETE http://localhost:8888/v1/progra/user?id=7 -H "Authorization: 12345"
curl -X DELETE http://localhost:8888/v1/progra/user?id=7 -H "Authorization: analisis"


 ```

