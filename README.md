# oauth2
oauth2 sample
#GRANT_TYPE PASSWORD
http://localhost:8080/oauth/token?username=user_1&password=123456&grant_type=password&scope=select&client_id=client_2&client_secret=123456

#GRANT_TYPE CLIENT_CREDENTIALS
http://localhost:8080/oauth/token?grant_type=client_credentials&scope=select&client_id=client_1&client_secret=123456
