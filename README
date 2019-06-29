# JWT Authetion Demo (SpringBoot, SpringSecurity, JWT)

## Pre-requirements:
Install these prerequisites:

1. Install Java SDK 1.8 (https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
2. Install Maven 3.5+ (https://maven.apache.org/download.cgi)
3. Install JQ (https://stedolan.github.io/jq/)

## Step 1. Clone the project
`git clone https://github.com/duongvansang/springboot-jwt-authentication.git`

## Step 2. Run app

```
cd springboot-jwt-authentication
mvn clean spring-boot:run` to run application
```

## Step 3. Test API

### Signup new User

```
curl -H "Content-Type: application/json" http://localhost:8080/v1/user/signup -d '{"username":"user","password":"password"}' | jq

Response:
{
  "code": 200,
  "status": "OK",
  "errorCode": null,
  "msg": "User created",
  "data": null
}
```

### Login

```
curl -H "Content-Type: application/json" http://localhost:8080/login -d '{"username":"user","password":"password"}' | jq

Response:
{
  "code": 200,
  "status": "OK",
  "errorCode": null,
  "msg": "Operation succeeded",
  "data": {
    "expired_in": 14400,
    "token_type": "bearer",
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMSIsImV4cCI6MTU2MTgxMTM1NH0.KORpD3SNnPfo1q6-73bNFKSTXirzKQ0JOlx4dy7r-fo3orLLGeeRD9dk8V7IaVtWGwOHKHACbfgU-5r0j-06Yw"
  }
}
```

### Get protected resources
```
curl -H "Authorization: Bearer $(curl -H "Content-Type: application/json" http://localhost:8080/login -d '{"username":"user","password":"password"}' | jq --raw-output .'data' |jq --raw-output .'token')" localhost:8080/v1/article | jq

{
    "code": 200,
    "status": "OK",
    "errorCode": null,
    "msg": "Operation succeeded",
    "data": [
        {
            "articleId": 1,
            "title": "Spring Boot JWT Authentication",
            "category": "Java"
        },
        {
            "articleId": 2,
            "title": "Solidity cookbook",
            "category": "Blockchain"
        },
        {
            "articleId": 3,
            "title": "EVM Specfication",
            "category": "Blockchain"
        },
        {
            "articleId": 4,
            "title": "NodeJS for Beginner",
            "category": "NodeJS"
        }
    ]
}
```
