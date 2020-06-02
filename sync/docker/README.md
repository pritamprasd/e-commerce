#### This repo contains:
__env.list__ : a file that is to be included while building docker containers. This file provides Database credentials, Eureka Server host to containers. e.g.,

```shell script
sudo docker run -d -p 8080:8080 --env-file ./env.list --network="host" <docker-image-name>
```  
