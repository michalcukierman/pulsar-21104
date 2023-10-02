#!/bin/bash
kubectl create namespace pulsar
helm repo add streamnative https://charts.streamnative.io
helm repo update
helm install pulsar-operators streamnative/pulsar-operator --namespace pulsar
kubectl apply -f pulsar.yaml 