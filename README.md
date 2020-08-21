# Employee-Service

As its name suggests, this service is responsible for handling the employees of a company. The application must expose a REST API. It should contain endpoints to:
  - Create a department
    - Id (auto-increment)
    - Name
    
 - Create an employee with the following properties:
   - Uuid (generated automatically)
   - E-mail
   - Full name (first and last name)
   - Birthday (format YYYY-MM-DD)
   - Employee’s department
   
  - Get a specific employee by uuid (response in JSON Object format)
  - Update an employee
  - Delete an employee

Whenever an employee is created, updated or deleted, an event related to this action must be pushed in Kafka. This event will be listened to by the [`event-service`](https://github.com/takeaway/bob-challenge-event-service/).

#### Restrictions

 - The `email field` is unique, i.e. _2 employees cannot have the same email._
 
# Solution README

#### Build
 - Run `mvn clean install` in the `employee-service` directory where the pom.xml resides.
 - A jar will be created in the target folder `employee-service/target`
 - Run `mvn clean install` in the `events-service` directory where the pom.xml resides.
 - A jar will be created in the target folder `events-service/target`
 - The next step is to create a docker image for both the services
 - Run `docker build -t employee-service ./` inside the `employee-service` folder where the Dockerfile resides
 - Run `docker build -t events-service ./` inside the `events-service` folder where the Dockerfile resides
 
#### Deployment
 - There is a docker-compose.yml inside the `employee-service` folder
 - This docker-compose.yml contains the following services
     - mysql
     - zookeeper
     - kafka
     - employee-service
     - events-service
 - Run `docker-compose up -d` inside the `employee-service` folder
 - Verify that the employee-service is up by hitting [`employee-service`](http://localhost:8080/swagger-ui.html#/)
 - Verify that the events-service is up by hitting [`events-service`](http://localhost:9090/swagger-ui.html#/)
 - To stop all services run `docker-compose down`
 
#### Authentication
 - There is authentication set up for all PUT/POST/DELETE requests. GET is allowed unauthenticated.
 - On executing these requests, a log-in form will pop up.
 - Username: user and Password: user will give 403, as this user does not have ADMIN role.
 - Use Username: admin and Password: admin for these requests.
 - Ideally you should be able to logout using http://localhost:8080/logout but this does not seem to work at times, so recommendation is to   	 always work in new incognito window to login.

#### Testing
 - For Employee Service, create a department first and then employee. There are validations for some fields. Email needs to be in xyc@abc format, future date is not allowed as birthdate, two employees cannot share same email address. Values that are marked required(*) needs to be provided or else it will result in bad request.
 - For Event Service, if there are no results for a given employee id output is empty list.
