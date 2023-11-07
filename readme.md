# Project: Social media blog API

## Overview 
```
This project is a Java backend for a hypothetical social media app, where we must manage our users’ accounts as well as any messages that they submit to the application. The application will function as a micro-blogging or messaging app. 
```
## Features implemented 
```
This application contains working API endpoints for the following features:

Register User (http://localhost:8080/register (post))
Log In User (http://localhost:8080/login (post))
Create New Message (http://localhost:8080/messages (post))
Retrieve All Messages (http://localhost:8080/messages (get))
Retrieve One Message By ID (http://localhost:8080/messages/{message_id} (get))
Retrieve All Messages By User (http://localhost:8080/accounts/{account_id}/messages (get))
Delete Message By ID (http://localhost:8080/messages/{message_id} (delete))
Update Message By ID (http://localhost:8080/messages/{message_id} (patch))
```

### Technologies Used: 
```
Java
SQL
Javalin
JDBC
```

### Getting Started

```
1) Download the source code from Github

2) Open the code in IntelliJ, Spring, or your other favorite IDE, click on the “Main” class, and use your IDE to run the program. If done correctly, the terminal will state “listening on http://localhost:8080/ 

3) Download and open the application “Postman”. You can use Postman to test each of my API endpoints written above. 

