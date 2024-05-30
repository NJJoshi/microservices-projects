### Briefing about project purpose 
Here we have complete set of spring boot based microservice application where we have implemented below mentioned microservice patterns:

1. Service Discovery Pattern
2. API Gateway Pattern
3. Circuit Breaker Pattern
4. Even Driven Pattern

In order to run these projects, You will need to download Kafka Server with Zookeeper and It should be up & running before you will going to start any of these microservices into your local system.

You will need to create below mentioned kafka topic on to your locally up & running Kafka Cluster.

Topic Name: ORDER_PAYMENT_TOPIC

You can create it using below mentioned command on your windows system.

kafka-topics.bat --bootstrap-server localhost:9092 --create --topic ORDER_PAYMENT_TOPIC --partitions 3 --replication-factor 1
