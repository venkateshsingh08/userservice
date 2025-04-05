# User Service
The User Service is responsible for user registration, authentication, token validation, and logout functionality.

### Endpoints

    Method: POST
    
    Endpoint: /signup
    
    Description: Registers a new user accepts name, email and password in payload. Also sends a message on kafka topic.


    Method: POST
    
    Endpoint: /login
    
    Description: Login an existing user, accepts email and password returns a token.


    Method: POST
    
    Endpoint: /validate
    
    Description: validates the token provided in request header.

    Method: POST
    
    Endpoint: /logout
    
    Description: logout the user.