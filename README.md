# AWESOME PIZZA
New Portal to manage Pizza Ordering and Management

### Assumption
* In this first phase, only API will be available, without GUI interaction
* Pizza chef cannot decline an order. When an order is placed, it is considered as accepted
* Customer can send more than one pizza. In this case all the sent pizzas will be enqueued
* Available pizza list is preloaded via data.sql script in resource folder

### Scalability considerations & Point of failure
* Actually the queue is not persisted. So, if we turn off the service, we might lose some pizza. To solve the problem.
is possible to add a PostConstruct fragment to load in the queue all the pizzas not already DONE, reading them from DB
* For production purpose, is suggested to change the embedded H2 in-memory DB to an instance
* Also, is suggested to disable hibernate schema creation, using specific DDL script
* In future, is possible to separate pizza chef and customer request into two microservices, allowing to scale single 
component
* Also, is possible to remove the embedded queue using a cloud one, as SQS on AWS, or a general purpose RabbitMQ

### Future Improvement
* Manage Rejection of the order
* Manage deliver of the order
* Manage Pizza Status