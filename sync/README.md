# Synchronous API Services

## Pre-requisite
- Java 8
- Maven
- PostgreSQL

## Services
This implementation uses REST API calls between the below services
- [apigateway](/apigateway)
   - Proxy for all requests coming into the `e-commerce` application. Plugs into the Discovery Service (Eureka in our case).
- [authservice](/authservice) 
   - A purpose built authentication server for holding users information for the `e-commerce` site.
- [cartservice](/cartservice) 
   - Independent service where `carts` related information are stored, retrieved and deleted via this service.
- [main-ui](/main-ui)
   - Front end for the `e-commerce` site. This interacts with the different services via `apigateway`.
- [productservice](/productservice)
   - Independent service where `products` related information are stored, retrieved and deleted via this service.
- [servicediscovery](/servicediscovery) 
   - A standalone Eureka Server for performing Service Discovery.
- [postman](/postman) 
   - Sample JSONs and examples which can be used via Postman REST Client

## Architecture
<img src="./architecture/architecture.png" alt="architeture" />
