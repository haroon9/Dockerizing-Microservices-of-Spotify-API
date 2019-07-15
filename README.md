# Service Orchestration

## Overview

The following services have been implemented:

#### Web

Offers a basic web frontend, connecting the implemented backend services.
The service is based on the given assignment template, nothing has been changed except for the endpoint URLs of the backend services.


#### Search

Can be used to look up information about a track or artist for given search parameters.

[Find the detailed API Documentation for the **search** service here.](docs/search.md) 

This service has been implemented as standalone Jersey Application and is executed via a `Server`-main class.

#### Charts

Returns popular tracks for a given artist.

[Find the detailed API Documentation for the **charts** service here.](docs/charts.md) 

This service has been implemented as standalone Jersey Application and is executed via a `Server`-main class.

#### Images

Returns cover art for a given track.

[Find the detailed API Documentation for the **images** service here.](docs/images.md) 

This service is built as `war`-Archive and deployed using the TomEE application server.


## Deploying via docker compose

Make sure your Docker deamon is running.

From the project root, run `docker-compose up`.

The web frontend can be accessed at [http://localhost:8888](http://localhost:8888).

Example: [http://localhost:8888/?title=Bullets&artist=Kaytranada](http://localhost:8888/?title=Bullets&artist=Kaytranada)

Stop via `docker-compose down`.

The Docker Compose configuration is located at `docker-compose.yml`.

## Deploying using kubernetes / minikube

The configuration files can be found in the `kubernetes/` folder.

A shell script named `start-kube.sh` is provided, which

- (re-)starts minikube
- builds and tags the required docker images
- starts the kubernetes services by applying the configuration files in `kubernetes/`
- Prints the frontend URL to access the web service
