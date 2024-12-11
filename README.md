# Ticketing system 

## Description
This project is a part of a coursework assignment to efficiently show the usage and application of Object-Oriented Programming and multi-threaded concepts in Java. 

This is a multi-threaded ticketing system integrated with a CLI,frontend and backend that gets the configuration inputs from the user and simulates the ticket releasing(by vendor) and ticket retreiving (by customer) according to the input provided. The system uses concurrent programming and thread safety mechanism to work efficiently without interruptions.

### Features
- Vendors can release tickets in the given specific intervals.
- Customers can retreive ticket in the given specific intervals.
- User have the ability to choose the limit of the total tickets,ticket release interval, ticket retreival interval and the limit of the ticketPool. 
- Prevents overbooking.
- Synchronized access to the ticketpool
- Producer - consumer pattern

### Tech Stack
- Backend   : SpringBoot (Rest API development)
- Frontend  : React.js
- CLI       : java
- Database  : MySQL
- API testing: Postman

### Prerequisites
- Java JDK 11 or higher
- Node.js and npm
- axios
- MySQL
- Git
