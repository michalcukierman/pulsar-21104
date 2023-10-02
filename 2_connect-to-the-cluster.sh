#!/bin/bash
# Replace with your GCP project
PROJECT=websight-dxp-development
gcloud container clusters get-credentials cluster-3 --zone us-central1-c --project $PROJECT