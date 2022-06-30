# Cinema-Rest-Service
JetBrains Academy Project

A simple Spring REST service that will help you manage a small movie theatre(show the available seats, sell and refund tickets, and display the statistics of your venue). Handle HTTP requests in controllers, create services and respond with JSON objects.

To test the application, you should use Postman

Movie theater has 9 rows with 9 seats each

Implemented the following endpoints:

* /seats handles GET requests and returns the information about the movie theatre.
  
  Response body:
      
      {
    
        "total_rows":9,
        "total_columns":9,
        "available_seats":[

        {
            "row":1,
            "column":1
        },
        {
            "row":1,
            "column":2
        },
        {
            "row":1,
            "column":3
        },

        ........

        {
            "row":9,
            "column":8
        },
        {
            "row":9,
            "column":9
        }
       ]
      }
    
* /booked handles GET requests and returns the information about booked seats

    Response body:
      
      {
          "ordered_seats": [
         {
             "row": 1,
             "column": 1,
             "price": 10
         }
       ]
      }

* /purchase endpoint that handles POST requests and marks a booked ticket as purchased.

A request should contain the following data:

* row - the row number;
* column — the column number.

Request body:

      {
        "row": 3,
        "column": 4
      }

If the purchase is successful, the response body should be as follows:

      {
        "token": "00ae15f2-1ab6-4a02-a01f-07810b42c0ee",
        "ticket: {
          "row": 5,
          "column": 7,
          "price": 8
          }
      }

The ticket price is determined by a row number. If the row number is less or equal to 4, the price cost 10. All other rows cost 8 per seat.

If the seat is taken the response body should contain the following:

      {
        "error": "The ticket has been already purchased!"
      }
      
If users pass a wrong row/column number the response body should contain the following:

      {
        "error": "The number of a row or a column is out of bounds!"
      }
      
* /return endpoint handle POST requests and allow customers to refund their tickets.

The request should have the token feature that identifies the ticket in the request body. Once you have the token, you need to identify the ticket it relates to and mark it as available. 

Request body:

      {
        "token": "e739267a-7031-4eed-a49c-65d8ac11f556"
      }
      
The response body should be as follows:

      {
        "returned_ticket": {
          "row": 1,
          "column": 1,
          "price": 10
          }
      }
      
 If you cannot identify the ticket by the token response body should contain the following:
      {
        "error": "Wrong token!"
      }
  
* /stats endpoint that will handle POST requests with URL parameters. If the URL parameters contain a password key with a super_secret value, return the movie theatre statistics in the following format:

      {
        "current_income": 0,
        "number_of_available_seats": 81,
        "number_of_purchased_tickets": 0
      }
      
If the parameters don't contain a password key or a wrong value has been passed the response body should contain the following:

      {
       "error": "The password is wrong!"
      }
    
* /change_price endpoint that will handle PUT requests with URL parameters and body. The URL parameters contain a password key with a super_secret value and price with new value

A request should contain the following data:

* row - the row number;
* column — the column number.

Request body:

      {
        "row": 3,
        "column": 4
      }
     
 If the request is successful, the response body should be as follows:
      
      {
        "Changed seat!": {
          "row":1,
          "column":2,
          "price": 100
          }
      }
 
 If the parameters don't contain a password key or a wrong value has been passed the response body should contain the following:

      {
       "error": "The password is wrong!"
      }
 If the price not valid (zero or minus) the response body should contain the following:
 
      {
        "error": "The price is not valid!"
      }

if the place has already been bought the response body should contain the following:

      {
         "error": "The seat is ordered!"
      }
