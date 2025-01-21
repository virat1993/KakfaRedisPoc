A short POC of consuming from kafka and pushing on redis stream 

[//]: # (Zookeeper start command)
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

[//]: # (kafka server start command)
.\bin\windows\kafka-server-start.bat .\config\server.properties

[//]: # (creating a sample topic in kafka server)
.\kafka-topics.bat --create --topic sample_topic --zookeeper localhost:2181 --replication-factor 1 --partitions 1

[//]: # (checking topic is been created )
.\kafka-topics.bat --describe --zookeeper localhost:2181

[//]: # (producing message on kafka)
.\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic sample_topic

[//]: # (to check stream is getting created)
redis-cli XREAD COUNT 1 STREAMS mystream 0