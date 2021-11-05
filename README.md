# Kafka sandbox

## Installation and setup

Download Scala binaries and extract them, then add their path to `KAFKA_HOME` env variable.
```bash
export KAFKA_HOME=/home/marko/Documents/mytools/kafka_2.13-3.0.0
```
and better yet, add to your `.bashrc` to be set every time on OS start.

## Starting

1. Zookeeper
```bash
$KAFKA_HOME/bin/zookeeper-server-start.sh \
$KAFKA_HOME/config/zookeeper.properties
```
2. Kafka
```bash
$KAFKA_HOME/bin/kafka-server-start.sh \
$KAFKA_HOME/config/server.properties 
```
3. Kafdrop
```bash
docker-compose up 
```
Now you can access the UI via http://localhost:9000/

## Commands

- Creating a topic
```bash
$KAFKA_HOME/bin/kafka-topics.sh -\
-bootstrap-server localhost:9092 \
--create \
--partitions 3 \
--replication-factor 1 \
--topic getting-started
```
- Publishing records
```bash
$KAFKA_HOME/bin/kafka-console-producer.sh \
--broker-list localhost:9092 \
--topic getting-started \
--property "parse.key=true" \
--property "key.separator=:"
```
- Consuming records
```bash
$KAFKA_HOME/bin/kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--topic getting-started \
--group cli-consumer \
--from-beginning \
--property "print.key=true" \
--property "key.separator=:" 
```
- Listing topics
```bash
$KAFKA_HOME/bin/kafka-topics.sh \
--bootstrap-server localhost:9092 \
--list --exclude-internal
```
- Describing a topic
```bash
$KAFKA_HOME/bin/kafka-topics.sh \
--bootstrap-server localhost:9092 \
--describe --topic getting-started
```
- Deleting a topic
```bash
$KAFKA_HOME/bin/kafka-topics.sh \
--bootstrap-server localhost:9092 \
--delete --topic getting-started
```
- Listing consumer groups
```bash
$KAFKA_HOME/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
```
- Describing a consumer group
```bash
$KAFKA_HOME/bin/kafka-consumer-groups.sh \
--bootstrap-server localhost:9092 \
--group cli-consumer --describe --all-topics
```
