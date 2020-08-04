# Call detail records API

## About the project

One of technical tasks I got while looking for my first software developer position.\
Application made using <i><b>Java 11, Spring Boot v2.3.2, Spring Web, Spring Data JPA, H2-in-memory-database, Lombok, OpenCSV and Maven</b></i>.
 
API gives endpoints for a basic insight in call detail records, which at the moment includes:

- Get all CallDetailRecords
- Get all CallDetailRecords between given dates expressed in ISO 8601
- Get averages calls information between given dates expressed in ISO 8601
- Get only average calls cost between given dates expressed in ISO 8601
- Get only average calls duration between given dates expressed in ISO 8601
- Get call detail records ordered by duration between given dates expressed in ISO 8601

Given more time I would implement :
- Spring Security into project to protect endpoints from unauthorized access;
- Spring HATEOAS to make API RESTful;
- Swagger to design, build, document, and use RESTful web services;

Nonetheless, made a decent effort to write clean OOP code to my Date.now() best understanding, like separation of concerns and encapsulation of internal workings of the class to hide details from outside while providing a simple interface to work with a class and there should be no to little pain adding new functionality.

### Live demo deployed on Heroku 
https://aqueous-falls-91906.herokuapp.com/api/cdrs

(notice: it might take 30s to wake up Heroku container)

## How to set up the application

Open terminal and use git clone command to download the remote GitHub repository to your computer:
```
git clone https://github.com/skarware/technical-task-cdr-api.git
```
It will create a new folder with same name as GitHub repository "technical-task-cdr-api". All the project files and git data will be cloned into it. After cloning complete change directories into that new folder:
```
cd technical-task-cdr-api
```

## How to use

To launch the application run this command (uses maven wrapper from the project):
```
$ ./mvnw clean spring-boot:run
```
Or using your installed maven version:
```
$ mvn clean spring-boot:run
```
<b>For interacting with API one can use <b><i>curl</i></b> tool as in following examples:</b>

**Please note in the examples below application runs on Heroku machine: https://aqueous-falls-91906.herokuapp.com.**

*If you compiled and run application on localhost machine please use default Tomcat listening port 8080: http://localhost:8080*

To upload CSV file with CallDetailRecords (an example `cdrs.csv` file placed in project's directory):
```
curl --location --request POST 'https://aqueous-falls-91906.herokuapp.com/api/cdrs/uploadFile' \
--form 'file=@/C:/Users/Martynas/cdrs.csv'
```

Get all CallDetailRecords:
```
curl --location --request GET 'https://aqueous-falls-91906.herokuapp.com/api/cdrs'
```

Get all CallDetailRecords between given dates expressed in ISO 8601:
```
curl --location --request GET 'https://aqueous-falls-91906.herokuapp.com/api/cdrs/2020-07-08T00:00:00Z/2020-07-10T00:00:00Z'
```

Get averages calls information between given dates expressed in ISO 8601:
```
curl --location --request GET 'https://aqueous-falls-91906.herokuapp.com/api/cdrs/2020-07-08T00:00:00Z/2020-07-10T00:00:00Z/average'
```

Get only average calls cost between given dates expressed in ISO 8601:
```
curl --location --request GET 'https://aqueous-falls-91906.herokuapp.com/api/cdrs/2020-07-08T00:00:00Z/2020-07-10T00:00:00Z/average/callcost'
```

Get only average calls duration between given dates expressed in ISO 8601:
```
curl --location --request GET 'https://aqueous-falls-91906.herokuapp.com/api/cdrs/2020-07-08T00:00:00Z/2020-07-10T00:00:00Z/average/callduration'
```

Get call detail records ordered by duration between given dates expressed in ISO 8601:
```
curl --location --request GET 'https://aqueous-falls-91906.herokuapp.com/api/cdrs/2020-07-06T00:00:00Z/2020-07-15T00:00:00Z/longestcalls'
```