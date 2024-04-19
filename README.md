
Parking API

Parking API used to control the number of parking spots, clients and the price of the application.
Authenticate user
POST
http://localhost:8080/api/v1/auth
Create a bearer token to authenticate a user


BODY PARAMS
username
string
required
password
string
required
RESPONSES

200
User authenticated


400
Invalid credentials


Get all clients
GET
http://localhost:8080/api/v1/clients
Resource to get all clients. Operation requires a bearer token to access it with ADMIN Role


QUERY PARAMS
page
integer
Page number

size
integer
Page size

sort
array of strings
Sort by field


ADD STRING
RESPONSES

200
Clients Found with success


401
Unauthorized access


403
Forbidden access

Create a new client
POST
http://localhost:8080/api/v1/clients
Resource to create a new client bound to a user. Operation requires a bearer token to access it

BODY PARAMS
name
string
required
cpf
string
required
RESPONSES

201
Client created with success


400
Invalid CPF or name


403
Forbidden access


409
Client CPF already exists

Get a client by id
GET
http://localhost:8080/api/v1/clients/{id}
Resource to get a client by id. Operation requires a bearer token to access it with ADMIN Role


PATH PARAMS
id
int64
required

RESPONSES

200
Client Found with success


401
Unauthorized access


403
Forbidden access


404
Client not found

Get client details
GET
http://localhost:8080/api/v1/clients/details
Resource to get a client details. Operation requires a bearer token to access it with CLIENT Role

RESPONSES

200
Client Found with success


401
Unauthorized access


403
Forbidden access


404
Client not found

Get user by id
GET
http://localhost:8080/api/v1/users/{id}
Operation requires a bearer token to access it

PATH PARAMS
id
int64
required

RESPONSES

200
User found


401
Unauthorized access


403
Forbidden access


404
User not found

Update user password
PUT
http://localhost:8080/api/v1/users/{id}
Operation requires a bearer token to access it


PATH PARAMS
id
int64
required
BODY PARAMS
currentPassword
string
required
newPassword
string
required
passwordConfirmation
string
required
RESPONSES

200
User password updated


400
Invalid password


401
Unauthorized access


403
Forbidden access

Get all users
GET
http://localhost:8080/api/v1/users
Operation requires a bearer token to access it and only admin users can access it

RESPONSES

200
Users found


401
Unauthorized access


403
Forbidden access

Create a new user
POST
http://localhost:8080/api/v1/users
Create a new user

BODY PARAMS
username
string
required
password
string
required
RESPONSES

201
User created


400
Invalid password or username


409
User already exists

Check out a car
PUT
http://localhost:8080/api/v1/parking/checkout/{receipt}
Resource to check a car outThe operation requires a bearer token to access it as an admin.

RECENT REQUESTS
TIME	STATUS	USER AGENT
Make a request to see history.
0 Requests This Month

PATH PARAMS
receipt
string
required
RESPONSES

200
Car checked out with success


401
Unauthorized access


403
Forbidden access


404
Receipt not found or car already checked out

Check in a car
POST
http://localhost:8080/api/v1/parking/checkin
Resource to check a car inThe operation requires a bearer token to access it as an admin.

BODY PARAMS
plateNumber
string
required
brand
string
required
model
string
required
color
string
required
clientCpf
string
required
RESPONSES

201
Car checked in with success


400
Invalid spot


401
Unauthorized access


403
Forbidden access


404
Client or free spot not found


409
Spot already occupied

Get all parking spots by client ID
GET
http://localhost:8080/api/v1/parking
Resource to get all parking spots by a client idThe operation requires a bearer token to access it as a client.

QUERY PARAMS
pageable
object
required

PAGEABLE OBJECT
RESPONSES

200
Parking spots found


401
Unauthorized access


403
Forbidden access

Get all parking spots by client CPF
GET
http://localhost:8080/api/v1/parking/cpf/{cpf}
Resource to get all parking spots by client CPFThe operation requires a bearer token to access it as an admin.

PATH PARAMS
cpf
string
required
QUERY PARAMS
pageable
object
required

PAGEABLE OBJECT
RESPONSES

200
Parking spots found


401
Unauthorized access


403
Forbidden access

Get receipt of a car
GET
http://localhost:8080/api/v1/parking/checkin/{receipt}
Resource to get the receipt of a carThe operation requires a bearer token to access it as an admin or a client.

PATH PARAMS
receipt
string
required
RESPONSES

200
Receipt found


401
Unauthorized access


404
Receipt not found or car already checked out

Create a new spot
POST
http://localhost:8080/api/v1/spots
Resource to create a new spot, operation requires a bearer token to access it as an admin

BODY PARAMS
code
string
required
spotStatus
string
required
RESPONSES

201
Spot created with success


400
Invalid code


401
Unauthorized access


403
Forbidden access


409
Spot code already exists


Get spot by code
GET
http://localhost:8080/api/v1/spots/{code}
Resource to get a spot by its code, operation requires a bearer token to access it as an admin

PATH PARAMS
code
string
required
RESPONSES

200
Spot found with success


401
Unauthorized access


403
Forbidden access


404
Spot not found

Created by João Neto
Github: Jbfaneto