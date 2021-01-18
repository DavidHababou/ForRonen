# RestfullCalc
restfullCalc is a restfull api to calculate arithmetic operations
upon two integers 


## Running the service
#### The service was compiled in java 11. 
Download the code and run:

```bash
mvnw spring-boot:run
```

## Usage
The service is exposed by HTTP POST endpoint. In order to run this service you should send a POST request to: http://servername:8080/calculate</br>
The request body should be a json like the following:

```json
{
    "firstNumber": 0,
    "secondNumber": 1
}
```

## Architecture
The service is written in Java spring boot, and is using RabbitMQ as backend messaging platform. 
The RabbitMQ server I used is provided on cloud by cloudamqp.com and is based on AWS IaaS and PaaS.

### Access to RabbitMQ account on the cloud
In order to see the messages transport, browse to https://www.cloudamqp.com/ and login with following credentials:

user: davidhababou@gmail.com\
pw: will be sent by whatsapp\
There is a single instance named "David Test". on the left click "RabbitMQ Manager". Under "Queues" you will see two queues. Clicking on each will show the message rates of the queue. 
