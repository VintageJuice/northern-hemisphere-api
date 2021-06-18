# Northern-Hemisphere-Api
An api that returns countries from the northern hemisphere of the planet via ip addresses

## How To Install

* Check out the code either via clone or zip.
* The project is built using JDK 11 and Maven, and will require Maven to build.
* Running 'mvn clean install' in your directory will download dependencies, then package and install the jar.
* After the install phase a small bash script called 'post_install.sh' will run to enable you to run the service using the command nhapi.
* post_install.sh should have execute permissions even downloaded from github, but if you have strict environmental permissions set
and get a failure on executing the script after the install phase, trying running it manually with sudo, then you should be good to go.
  
* If for some reason it won't work, you can run 'mvn spring-boot:run' - it's not a single command but it will at least start the service.

## General Usage 

* You can curl the service from the command line without any credentials at localhost:8888/northcountries?ip=your_ip
* You can curl the service from the command line with credentials at localhost:8888/auth/northcountries?ip=your_ip
* You can also test the service in browser.
* TIP: if curl isn't formatting news lines, you can feed it to echo like so:
    * curl "http://localhost:8888/northcountries?ip=8.8.8.8&ip=31.23.45.67"; echo - it'll fix the command line formatting.


## Implementation

Northern Hemisphere Api runs on localhost:8888.
It is configured with extremely basic Spring Security exposing an authenticated and unauthenticated endpoint.
Both use in-memory-caching to process requests to IpApi, in order to minimise calls to the external API.
This is especially important considering their API limits you to 15 calls a minute on the batch endpoint.

__The Endpoints are:__

* __/northcountries__ - unauthenticated endpoint that returns distinct alphabetised northern hemisphere countries from a list of ip addresses
* __/auth/northcountries__ - authenticated endpoint that returns distinct alphabetised northern hemisphere countries from a list of ip addresses (admin - pass to login via curl or the ui localhost:8888/login - credentials are stored in the code currently because of time constraints, obviously this would never be done on any secure service and is just for demonstration purposes)
* __/login__ - generic login page for authenticated access.  
* __/error__ - generic error page 
* __/swagger-ui__ - swagger ui implementation of endpoints and model (still not fully configured)
* __/actuator__ - discovery endpoint for all actuator services running on the service (unfortunately there is a bug with metrics, but every other endpoint is working well)

### Other Details

* This was implemented with TDD, so the test coverage is currently 96% of classes, and 100% LOC (2000 LOC Java).
* The service will log directly to console and file, and is configured to roll the file after 10MB, so logs can't get too huge.
* Java docs are available.
* Most of the unit tests are fully mocking external services, but I left a single one as an integration test to call the real service.
* There is a logging memory appender configured, so that logs can easily be verified programmatically.
* I followed Uncle Bob's SOLID principles, and wrote my classes/methods based off of his book Clean Code. 
* Architecturally I followed Domain Driven Development and Clean Architecture - Controller, Service and IpApi layers are coupled via interface abstractions, I attempted to write this so that anything outside of the core policy in the Service layer, is just a detail that can easily be swapped for another abstraction.
* I wanted to implement the api package it as a module, but unfortunately there was not time.
* The JAR file is too large, and I would like to lighten it with maven shade, but unfortunately did not have time.
* There are error handlers implemented in order to catch any validation or format exceptions.