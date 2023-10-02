#!/bin/bash
# Replace with your GCP project
PROJECT=websight-dxp-development
gcloud beta container --project "$PROJECT" clusters create "cluster-3" \
    --zone "us-central1-c" \
    --machine-type "e2-standard-2" \
    --disk-size "100" \
    --num-nodes "3" \
    --node-locations "us-central1-c"