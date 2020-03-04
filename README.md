
This is a Spring Boot RESTful application which can serve the following Account operations:

 * Creating a new account
 * Reading an account
 * Making a transfer between two accounts

How to run
----------

Build the project using gradle command:

```
./gradlew clean build
```


```

Create Credit/Debit Accounts
Sample Requests:
```
{
	"accountId": "Debit-123",
	"balance": 100.00
}

{
	"accountId": "Credit-123",
	"balance": 10.00
}
```

After that make transactions:

```
curl -i -X PUT \
   -H "Content-Type:application/json" \
   -d \
'{
  "accountFromId":"Debit-123",
  "accountToId":"Credit-123",
  "amount": "20.00"
}' \
 'http://localhost:18080/v1/accounts/transfer'
```

Deployment Readiness
--------------------
* Application yml files add as per environment. we can config our dev,test and prod environments there  

Further improvements
--------------------

* We can include database as backend rather in memory repo
* we can introduce some ORM Mapping.
* we can use transactions modules for transaction managements.
* we can add more YML's to make my microsevice more configurable 
* we can introduce global exception handler as controller advice
* For better testing we can add rest assured integration test cases
