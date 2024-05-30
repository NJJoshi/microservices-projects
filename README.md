# Briefing about project purpose 
Here we have complete set of spring boot based microservice application where we have implemented below mentioned microservice patterns:

1. Service Discovery Pattern
2. API Gateway Pattern
3. Circuit Breaker Pattern
4. Even Driven Pattern

## How do you run these project on your local
### Step 1: 
Clone these projects on your local and build each microservices individually using command "mvn clean install".
### Step 2:
In order to run these projects, You will need to download Kafka Server with Zookeeper and It should be up & running before you will going to start any of these microservices into your local system.
Here are few basic commands to run kafka locally on your system.
1. Go to your kafka directory and go to your bin directory inside kafka folder. If you are running it on your windows then you need to go further inside windows folder where you will going to find all kafka commands .bat files.
2.You need to make sure that your kakfa's config/zookeeper.properties and config/server.properties have below metioned properties uncommented.
  For config/zookeeper.properties
      ##### the port at which the clients will connect
      clientPort=2181
   For config/server.properties
     zookeeper.connect=localhost:2181
3. After that you need to start zookeeper. In order to start it run command: zookeeper-server-start.bat ../config/zookeeper.properties
4. After zookeeper got started, You need to start Kafka Server/Broker. To do that run this command: kafka-server-start.bat ../config/server.properties
5. You will need to create below mentioned kafka topic on to your locally up & running Kafka Cluster.
   Topic Name: ORDER_PAYMENT_TOPIC
   You can create it using below mentioned command on your windows system.
   kafka-topics.bat --bootstrap-server localhost:9092 --create --topic ORDER_PAYMENT_TOPIC --partitions 3 --replication-factor 1
6. To make sure topic has been created, run this command: kafka-topics.bat --bootstrap-server localhost:9092 --list
### Step 3:
Once Step 2 is done successfully, We should be starting up microservices into following order.
1. config-server
2. Service-discovery
3. Order Service
4. Payment Service
5. User Service
6. Gateway Service

After these steps , You should be able to run microservices on your local machine. 


