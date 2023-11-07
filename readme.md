# Project: Social media blog API

## Overview 
```
This project is a Java backend for a hypothetical social media app, where we must manage our users’ accounts as well as any messages that they submit to the application. The application will function as a micro-blogging or messaging app. 
```
## Features implemented 
```
This application contains working API endpoints for the following features.

-Register User
-Log In User
-Create New Message
-Retrieve All Messages
-Retrieve One Message By ID
-Retrieve All Messages By User
-Delete Message By ID 
-Update Message By ID
```

### Technologies Used: 
```
account_id integer primary key auto_increment,
username varchar(255),
password varchar(255)
```

### Getting Started

```
1) Download the source code from Github

2) Open the code in IntelliJ, Spring, or your other favorite IDE, click on the “Main” class, and use your IDE to run the program. If done correctly, the terminal will state “listening on http://localhost:8080/ 

3) Download and open the application “Postman”. You can use Postman to test each of my API endpoints. The endpoints are written out in my “SocialMediaController” class file and below

Register User: http://localhost:8080/register (post) (provide username and password)
Log In User: http://localhost:8080/login (post) (provide username and password)
Create New Message: http://localhost:8080/messages (post) (provide posted_by, message_text, and time_posted_epoch)
Retrieve All Messages: http://localhost:8080/messages (get)
Retrieve One Message By ID: http://localhost:8080/messages/{message_id} (get)
Delete Message By ID: http://localhost:8080/messages/{message_id} (delete) (provide message_id)
Update Message By ID: http://localhost:8080/messages/{message_id} (patch) (provide message_id and message)
Retrieve All Messages By User ID: http://localhost:8080/accounts/{account_id}/messages (get) (provide account_id)
```
