# Ticketing system 

## Description
This is a multi-threaded ticketing system integrated with a CLI, front end, and back end that gets the configuration inputs from the user and simulates ticket releasing(by vendor) and ticket retrieving (by customer) according to the input provided. The system uses concurrent programming and thread safety mechanisms to work efficiently without interruptions.

### Features
- Vendors can release tickets in given specific intervals.
- Customers can retrieve tickets in the given specific intervals.
- User can choose the limit of the total tickets, ticket release interval, ticket retreival interval and the limit of the TicketPool. 
- Prevents overbooking.
- Synchronized access to the ticket pool
- Producer - consumer pattern

### Tech Stack
- Backend  : SpringBoot (Rest API development)
- Frontend : React.js
- CLI      : java
- Database : MySQL
- API testing: Postman

### Prerequisites
- Java JDK 11 or higher
- Node.js and npm
- axios
- MySQL
- Maven
- Git
