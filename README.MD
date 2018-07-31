1. API Endpoints
The api endpint is http://18.221.14.82:8080/tasks/validateBrackets for validating brackets balance, 
and http://18.221.14.82:8080/todo for testing the three REST methods GET/POST/PATCH
The API has been deployed to a AWS EC2 instance. 

_________________________________________________________________________

2. Test

(1) Validate Brackets

To test the brackets balance, need to UrlEncode the input parameter, for example, "{[()]}" should be encoded to "%7B%5B%28%29%5D%7D". 
Try this one http://18.221.14.82:8080/tasks/validateBrackets?input=%7B%5B%28%29%5D%7D and you can get a isBalanced=true response. 
Another one can be http://18.221.14.82:8080/tasks/validateBrackets?input=%5B%5B%7B%7B%7B%5B%5D%7D%28%29%7D%7D%7B%7D%5D%5D, which should return a balanced result.
If not url encoded, the test will return 400 Bad Request. 
If the brackets are more than 50 chars, it will return ValidationError. 

The integration test endpoint provided by Auto&General can allow brackets without encoding, I am interested to know how it was achieved, 
as from my understanding, special characters have to be encoded before they can be passed in url parameter. 

When fed into the Auto&Genereal integration test like this
https://join.autogeneral.com.au/test/1.0/integrationTest?url=http://18.221.14.82:8080/

the result of this brackets test all returned 400 Bad Request, I guess this is becasue the integration test directly send brackets without 
Urlencoding. If sent with encoded brackets, I am confident they can success. 

_________________________________________________________________________

(2) /todo api
_________________________________________________________________________
POST: the posted JSON message should be in the format 
{
  "text": "something"
}


the response should be either 
{
  "id": 42,
  "text": "something",
  "isCompleted": false,
  "createdAt": "2017-10-13T01:50:58.735Z"
}

or 

{
  "details": [
    {
      "location": "params",
      "param": "text",
      "msg": "Must be between 1 and 50 chars long",
      "value": ""
    }
  ],
  "name": "ValidationError"
}

when the message length is 0 or more than 50 chars. This has passed the integration test. 
_________________________________________________________________________

GET: format is /todo/{id}, should return the object if it exists in db, otherwise should return a NotFoundError, this has passed
the integration test. However, if the id is too long, then the test will fail since it was defined as Integer and has a range limit. 
_________________________________________________________________________

PATCH: path is /todo/{id}, body should be in the format of this
{
  "text": "something",
  "isCompleted": true
}

should return the updated object if success. If text is too long, should return a validation error. if id not exist in DB, should return
a NotFoundError

these have been tested on Postman and automated tests in the project. 

If text is not present, then the test will fail since there is no message to patch.

_________________________________________________________________________

3. I found the IntegrationTest provided by Auto&General failed on its own api in some cases, please try this 
http://join.autogeneral.com.au/test/1.0/integrationTest?url=http://join.autogeneral.com.au/test/1.0 
and then review the test results. I can see some PATCH tests failed.

4. Source code
Source code were edited in NetBeans and can be cloned and tested locally from Postman or automated tests included in TestPackage. 

5. Please contact john.meng2011@gmail.com if there is any question.

