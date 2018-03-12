# (Fake) Vending Machine API

I have implemented a REST API for Vending Machines using Java Spring. I used a REST API with JSON as this is an recognised standard of API. The API can be tested in a normal web browser,
POST's may require you to download a web browser plugin for example POSTMAN. 

The following actions can be performed

```
localhost:8080 - GET: Index, view all Vending Machines in the system with all of the data
					POST: Add a new vending machine to the system
```
```
localhost:8080/{Vending Machine Name} - GET: View the details for a specific vending machine
```
```
localhost:8080/{Vending Machine Name}/coins - GET: View all the coins that vending machine currently has stored
												POST: Put a coin in the machine. (Amount is ignored as you can only put in one coin at a time)
```
```
localhost:8080/{Vending Machine Name}/coins/{coin value} - GET: View how many of a specific value coin that machine has
```
```
localhost:8080/{Vending Machine Name}/coins/refund - GET: Returns you coins of equal value to the amount that was put into the machine (Uses up larger coins first)
```
```
localhost:8080/{Vending Machine Name}/products/ - GET: List all the products for that machine
													POST: Add a new product to the vending machine
```
```
localhost:8080/{Vending Machine Name}/products/{product id} - GET: View the details for a specific product
```
```
localhost:8080/{Vending Machine Name}/products/{product id}/buy - GET: Buy 1 of that particular item deducting that amount of money from your current amount.
```

Notes:

When testing POST ensure the header "Content-Type" is set to "application/json; charset=UTF-8".
Initially there are two Vending Machines in the system their names are "Vending machine 1" and "Vending machine 2".