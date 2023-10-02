#!/bin/bash
mvn clean install -f ./pulsar-quickstart-producer

kubectl cp pulsar-quickstart-producer/target/quarkus-app pulsar/brokers-broker-0:/tmp
kubectl exec -n pulsar --stdin --tty brokers-broker-0 -- java -jar /tmp/quarkus-app/quarkus-run.jar