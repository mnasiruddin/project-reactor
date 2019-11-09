## Getting Started

## Features
### Signup
Users need to be able to create an account so they can log into the application

http://localhost:8080/signup

The payload looks like this:

`{"firstName":"Joe","surname":"Bloggs","username":"joebloggs","password":"reallysecurepassword"}`

### Login

http://localhost:8080/login

The payload looks like this:

`{"username":"joebloggs","password":"reallysecurepassword"}`

The response returns `Authorization` header with a valid bearer token like this: 

`Bearer eyJ0eXAiOiJKV1aiLCJhbGciOiJIUzUxMiJ9.eyJST0xFIjpbIkdFTkVSQUxfVVNFUiJdLCJzdWIiOiI1ZDcyMzejZTBkajQ4ZjAwaDE2MGJdsfIiLCJleHAiOjE1Njg2MjkwODJ9.z4iTtfbE6gIKfxe63o9JC6bVkg9W15fWQtihD3g1uKMoEhLdC9jmw7NIwkbp6uhYJyzns_c_ZMJhLS0Fcy6WZb`

This token will be used in all web service requests that require security.

### Search Oscar Nominees

User can now search for oscar nominees

`http://localhost:8080/api/v1/oscar/search/2000`

`2000` above is the year value.

This request also contains the `Authorization` header than contains the `Bearer` token.

This get request should return the results after querying the sample dataset by the year value. This data will look something like this:

`[{"id":"5d723088153ae99a19c63312","category":"ACTOR IN A LEADING ROLE","entity":"Javier Bardem","winner":false,"year":2000},{"id":"5d723088153ae99a19c63313","category":"ACTOR IN A LEADING ROLE","entity":"Russell Crowe","winner":true,"year":2000}.......]`

### Session web service calls
The following secure web service calls are also available:

- GET /api/v1/session/isLoggedIn - returns true if user is logged in.
- GET /api/v1/session/name - returns the first name of the logged in user.

### Activity Post calls

- GET /api/v1/posts/1 - returns this posts
- GET /api/v1/posts/1?userId=1 returns posts of this user.
- GET /api/v1/posts - returns all the posts.
- GET /api/v1/posts?userId=1 - returns all the posts for this users.
- POST /api/v1/posts - returns new posted id.

## Running application

When the network starts up a container (`mongo_seed`) is started up for the sole purpose of loading data into the `oscarNominees` collection.

Now to start the network simply run `./start.sh`

To tear down the containers run `./down.sh`

import [collection](search.postman_collection.json) in postman to test API endpoints

## WIP

JWT token is not signed correctly due to which below API endpoint are still WIP

- http://localhost:8080/api/v1/session/name
- http://localhost:8080/api/v1/session/isLoggedIn
- http://localhost:8080/api/v1/oscar/search/{searchId}