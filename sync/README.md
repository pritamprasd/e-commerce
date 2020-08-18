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
This implementation uses REST API calls between the below services
- [apigateway](./apigateway)
   - Proxy for all requests coming into the `e-commerce` application. Plugs into the Discovery Service (Eureka in our case).
- [authservice](./authservice) 
   - A purpose built authentication server for holding users information for the `e-commerce` site.
- [cartservice](./cartservice) 
   - Independent service where `carts` related information are stored, retrieved and deleted via this service.
- [main-ui](./main-ui)
   - Front end for the `e-commerce` site. This interacts with the different services via `apigateway`.
- [productservice](./productservice)
   - Independent service where `products` related information are stored, retrieved and deleted via this service.
- [servicediscovery](./servicediscovery) 
   - A standalone Eureka Server for performing Service Discovery.
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
