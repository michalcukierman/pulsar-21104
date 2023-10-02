mvn clean install -f ./pulsar-quickstart-processor

kubectl cp pulsar-quickstart-processor/target/quarkus-app pulsar/brokers-broker-1:/tmp
kubectl exec -n pulsar --stdin --tty brokers-broker-1 -- java -jar /tmp/quarkus-app/quarkus-run.jar