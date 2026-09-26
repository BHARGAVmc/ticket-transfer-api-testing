\# Ticket Transfer API Testing



API testing and automation project for a Spring Boot Ticket Transfer Platform.



\## Tech Stack



\- Java 23

\- JUnit 5

\- REST Assured

\- Maven

\- Postman

\- Git \& GitHub



\## What I Tested



\- User Login API

\- JWT Authentication

\- Authorization

\- Buyer Ticket APIs

\- Buyer Search APIs

\- Buyer Favorites APIs

\- Seller Ticket APIs

\- Seller Ticket Search

\- Seller Ticket Details

\- HTTP Status Codes

\- API Response Validation



\## Testing



Manual API testing was performed using Postman.



API automation was implemented using Java, JUnit 5 and REST Assured.



\## Run Tests



Make sure the backend is running on:



http://localhost:8081



Set the test credentials:



```powershell

$env:TT\\\_TEST\\\_EMAIL="your-test-email"

$env:TT\\\_TEST\\\_PASSWORD="your-test-password"


Test Result



14 tests executed successfully.



Failures: 0

Errors: 0

Skipped: 0





Project Structure

src/test/java/

└── com/tickettransfer/api/

\&#x20;   ├── auth/

\&#x20;   ├── buyer/

\&#x20;   ├── seller/

\&#x20;   ├── user/

\&#x20;   ├── config/

\&#x20;   └── utils/




