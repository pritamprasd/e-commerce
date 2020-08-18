# Synchronous API Services

This module is a set of independent services(supposed to connect to different databses) mostly using spring-boot security, jpa modules.
This project module should just be taken as a educational structure of spring-boot/react based projects using e-commerce platform as an example.

## Architecture
Here's a component diagram of current services:
<img src="./architecture/architecture.png" alt="architeture" />

## Techs:
- Java 8, Sprint-boot, Maven
- ReactJS 
- PostgreSQL
- docker

# Services
## A short description of services
e-com sync module uses REST API calls between the below services

### [API Gateway](./apigateway)
   Proxy for all requests coming into the `e-commerce` application. Plugs into the Discovery Service (Eureka in our case).
   It exposes the public api to external world/e-com-ui. Current implemntation has:
   1. logging every public-request to standard output
   
### [Service Discovery](./servicediscovery) 
   A standalone Eureka Server for performing Service Discovery, Currently it wraps multiple instances of services by their application.name property.
   Any service the needs to communicate with any other service needs to have a `EurekaClient` configured to get host address. e.g, have a look at [`TokenValidationFilter`](./cartservice/src/main/java/com/pritamprasad/cartservice/config/TokenValidationFilter.java)
   ``` java
       @Autowired
       private EurekaClient discoveryClient;
       ......
       ResponseEntity<User> reponse = restTemplate.getForEntity(
                discoveryClient.getNextServerFromEureka(authService, false).getHomePageUrl() + "validate/" + req.getHeader("token"),
                User.class);
   ```
### [Auth Service](./authservice) 
   A purpose built authentication server for holding users information for the `e-commerce` site. The end goal is to transform this service to a open-id/OAuth2.0 based authentication/authorization service(an interface for both internal auth system and external 3rd party Oauth providers).
   It handles below operations: 
   1. public authentication/authorization
   2. users CRUD operations
   3. token issuer
   4. token validator
   In order to use CRUD operations for user, you need to use `/users` endpoint with `Basic authentication` for the **Root** e-com user. The **Root** user-name & password need to be configured in [application.properties](./authservice/src/main/resources/application.properties) in *root.user* and *root-pass* field. You'll notice that the password is encoded with [Bcrypt](https://en.wikipedia.org/wiki/Bcrypt) encoder. To encode your passwords use below command on [spring-cli](https://search.maven.org/classic/#search%7Cga%7C1%7Ca%3A%22spring-boot-cli%22)
   ```
   spring encodepassword <your-password-in-plain-text>   
   ```
 #### Use cases:
 1. To create a new user (`POST /users`)
 2. To get token for inter-service communication. (Each service request internal/external requires token to be passed as a mode of authentication) `POST /token`
 3. To validate a token `GET /validate/{token}`
  
- [cartservice](./cartservice) 
   - Independent service where `carts` related information are stored, retrieved and deleted via this service.
- [main-ui](./main-ui)
   - Front end for the `e-commerce` site. This interacts with the different services via `apigateway`.
- [productservice](./productservice)
   - Independent service where `products` related information are stored, retrieved and deleted via this service.

- [postman](./postman) 
   - Sample JSONs and examples which can be used via Postman REST Client
   
### Service-port binding (for local development):
| Service-name       | application.name | Default Port |
| :----------------: | :--------------: | :----------: | 
| service-discovery  | eureka-server    | 8761         |
|                    |                  |              |


### How to setup local environment with minimum set of services?
Step 1: Setup a local postgres instance.

Step 2: Start servicediscovery service from sync/servicediscovery. As the name suggests this service manages host address of other services and provides a one host to call all the services.
Add your postgres username and passord in below envrionment variables:
```
DB_USER=<db-user-name>
DB_PASS=<password>
DB_HOST=<db-host-address> (normally it's localhost if you're running database in your local machine)
DB_NAME=<database-name>
EUREKA_HOST=localhost
```
After running the service you can visit eureka dashboard at : http://localhost:8761/

Step 3 :
